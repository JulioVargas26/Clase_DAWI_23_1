package net.clinica.controller;

import java.io.File;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.clinica.entity.Boleta;
import net.clinica.entity.Cliente;
import net.clinica.entity.Detalle;
import net.clinica.entity.Medicamento;
import net.clinica.entity.MedicamentoHasBoleta;
import net.clinica.entity.Usuario;
import net.clinica.services.BoletaServices;
import net.clinica.services.ClienteServices;
import net.clinica.services.MedicamentoServices;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping("/boleta")
public class BoletaController {
	@Autowired
	private ClienteServices serCliente;
	@Autowired
	private MedicamentoServices serMedicamento;
	@Autowired
	private BoletaServices serBoleta;
	
	@RequestMapping("/lista")
	public String lista() {
		
		return "boleta";
	}
	
	@RequestMapping("/listaClientesJSON")
	@ResponseBody
	public List<Cliente> listaClientesJSON(@RequestParam("apellido") String ape) {
		return serCliente.listaClientesPorPaterno(ape);
	}
	@RequestMapping("/listaMedicamentosJSON")
	@ResponseBody
	public List<Medicamento> listaMedicamentosJSON(@RequestParam("nombre") String nom) {
		return serMedicamento.listarPorNombre(nom);
	}
	
	@RequestMapping("/adicionar")
	@ResponseBody
	public List<Detalle> adicionar(@RequestParam("codigo") int cod,
							@RequestParam("descripcion") String des,
							@RequestParam("precio") double pre,
							@RequestParam("cantidad") int can,HttpSession session) {
		//declarar un arreglo de objetos de la clase Detalle
		List<Detalle> lista=null;
		try {
			//validar si existe el atributo de tipo sesión "data"		
			if(session.getAttribute("data")==null)//no existeeeeeeeeeeeeeeeeeeeee
				//crear el arrego lista
				lista=new ArrayList<Detalle>();
			else//si existe babyyyyyyyyyyyyyyyyyyyyyyyy
				//recuperar el atributo "data" y guardarlo en lista
				lista=(List<Detalle>) session.getAttribute("data");
				
			//crear objeto de la clase Detalle y guardar los valores de los parámetros
			Detalle det=new Detalle();
			det.setCodigo(cod);
			det.setDescripcion(des);
			det.setPrecio(pre);
			det.setCantidad(can);
			//adicionar objeto "det" dentro del arreglo "lista"
			lista.add(det);
			//crear atributo "data"
			session.setAttribute("data", lista);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	@RequestMapping("/grabar")
	public String grabar(@RequestParam("fecha") String fec,
						@RequestParam("cliente") String clie,
						@SessionAttribute("CODIGOUSUARIO") int codUsu,
						HttpSession session,
					    RedirectAttributes redirect) {
		try {
			//crear objeto de la entidad Boleta
			//cabecera
			Boleta bol=new Boleta();
			bol.setFechaEmision(new SimpleDateFormat("yyyy-MM-dd").parse(fec));
			bol.setMonto(1233);
			String sep[]=clie.split("-");
			Cliente cli=new Cliente(Integer.parseInt(sep[0]));
			bol.setCliente(cli);
			Usuario usu=new Usuario(codUsu);
			bol.setUsuario(usu);
			//detalle
			//crear un arreglo de objetos de la clase MedicamentoHasBoleta
			List<MedicamentoHasBoleta> detalle=new 
											ArrayList<MedicamentoHasBoleta>();
			//recuperar el atributo de tipo sesión "data"
			List<Detalle> info=(List<Detalle>) session.getAttribute("data");
			//bucle para realizar recorrido dobre info
			for(Detalle d:info) {
				//crear objeto de la clase MedicamentoHasBoleta y setear 
				//los valores del objeto "d"
				MedicamentoHasBoleta mhb=new MedicamentoHasBoleta();
				Medicamento m=new Medicamento(d.getCodigo());
				mhb.setMedicamento(m);
				mhb.setPrecio(d.getPrecio());
				mhb.setCantidad(d.getCantidad());
				//arreglo
				detalle.add(mhb);
			}
			//adicionar "detalle" dentro del objeto "bol"
			bol.setListaMedicamentoHasBoleta(detalle);
			//grabaaaaaaaaaa
			serBoleta.registrarBoleta(bol);
			//
			info.clear();
			session.setAttribute("data", info);
			redirect.addFlashAttribute("MENSAJE","Boleta registrada");
		} catch (Exception e) {
			redirect.addFlashAttribute("MENSAJE","Error en boleta");
			e.printStackTrace();
		}
		return "redirect:/boleta/lista";
	}
	
	@RequestMapping("/reporteCliente")
	public void reporteCliente(HttpServletResponse response) {
		try {
			//invocar al método listarTodos
			List<Cliente> lista=serCliente.listaClientesPorPaterno("");
			//acceder al reporte "reporteCliente.jrxml"
			File file=ResourceUtils.getFile("classpath:reporteCliente.jrxml");
			//crear objeto de la clase JasperReport y manipular el objeto file
			JasperReport jasper=JasperCompileManager.compileReport(file.getAbsolutePath());
			//origen de datos "manipular lista"
			JRBeanCollectionDataSource origen=new JRBeanCollectionDataSource(lista);
			//crear reporte
			JasperPrint jasperPrint=JasperFillManager.fillReport(jasper,null,origen);
			//salida del reporte en formato PDF
			response.setContentType("application/pdf");
			//
			OutputStream salida=response.getOutputStream();
			//exportar a pdf
			JasperExportManager.exportReportToPdfStream(jasperPrint,salida);		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}










