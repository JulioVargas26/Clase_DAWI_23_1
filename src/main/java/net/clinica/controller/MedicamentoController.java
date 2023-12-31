package net.clinica.controller;

import java.io.File;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletResponse;
import net.clinica.entity.Cliente;
import net.clinica.entity.Medicamento;
import net.clinica.entity.TipoMedicamento;
import net.clinica.services.LaboratorioServices;
import net.clinica.services.MedicamentoServices;
import net.clinica.services.TipoServices;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
//ruta o URL para acceder a la clase "Controlador" desde la vista
@RequestMapping("/medicamento")
public class MedicamentoController {
	@Autowired
	private MedicamentoServices serMed;
	
	@Autowired
	private TipoServices serTipo;
	
	@Autowired
	private LaboratorioServices serLab;
	
	
	@RequestMapping("/lista")
	public String index(Model model) {
		//crear atributos
		model.addAttribute("medicamentos", serMed.listarTodos());
		model.addAttribute("laboratorios",serLab.listarTodos());
		
		return "medicamento";
	}
	
	//ruta (URL) para grabar
	@RequestMapping("/grabar")
	public String grabar(@RequestParam("codigo") Integer cod,
						 @RequestParam("nombre") String nom,
						 @RequestParam("descripcion") String des,
						 @RequestParam("precio") double pre,
						 @RequestParam("stock") int sto,
						 @RequestParam("fecha") String fec,
						 @RequestParam("tipo") int codTipo,
						 RedirectAttributes redirect) {
		try {
			//crear objeto de la entidad Medicamento
			Medicamento m=new Medicamento();
			//setear atributos del objeto "m"
			m.setNombre(nom);
			m.setDescripcion(des);
			m.setPrecio(pre);
			m.setStock(sto);
			m.setFecha(LocalDate.parse(fec));
			//crear objeto de la entidad TipoMedicamento
			TipoMedicamento tm=new TipoMedicamento();
			//setear codigo
			tm.setCodigo(codTipo);
			//enviar objeto "tm" al objeto "m"
			m.setTipo(tm);
			
			if(cod==0) {			
				serMed.registrar(m);
				//atributo
				redirect.addFlashAttribute("MENSAJE","Medicamento registrado");
			}
			else {
				m.setCodigo(cod);
				serMed.actualizar(m);
				//atributo
				redirect.addFlashAttribute("MENSAJE","Medicamento actualizado");
			}
			
		} catch (Exception e) {
			redirect.addFlashAttribute("MENSAJE","Error en el registro");
			e.printStackTrace();
		}
		return "redirect:/medicamento/lista";
	}
	//ruta (URL) para buscar por código
	@RequestMapping("/buscar")
	@ResponseBody //genera JSON
	public Medicamento buscarPorCodigo(@RequestParam("codigo") Integer cod) {
		return serMed.buscarPorID(cod);
		
	}
	//ruta (URL) para eliminar por código
	@RequestMapping("/eliminar")
	public String eliminarPorCodigo(@RequestParam("codigo") Integer cod,
			 						RedirectAttributes redirect) {
		serMed.eliminarPorID(cod);
		redirect.addFlashAttribute("MENSAJE","Medicamento eliminado");
		return "redirect:/medicamento/lista";
	}
	
	//ruta (URL) para listar todos los tipos de medicamentos según el código del laboaratorio
	@RequestMapping("/listarPorLaboratorio")
	@ResponseBody //genera JSON
	public List<TipoMedicamento> listarPorLaboratorio(@RequestParam("codigo") Integer cod) {
		return serTipo.listarPorLaboratorio(cod);
		
	}
	
	@RequestMapping("/reporteMedicamento")
	public void reporteCliente(HttpServletResponse response) {
		try {
			//invocar al método listarTodos
			List<Medicamento> lista=serMed.listarTodos();
			//acceder al reporte "reporteCliente.jrxml"
			File file=ResourceUtils.getFile("classpath:ReporteMedicamento.jrxml");
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


















