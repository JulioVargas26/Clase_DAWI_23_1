package net.clinica.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import net.clinica.entity.Enlace;
import net.clinica.entity.Usuario;
import net.clinica.services.UsuarioServices;

@SessionAttributes({"datosUsuario","ENLACES","CODIGOUSUARIO"})
@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	private UsuarioServices servicio;
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	@RequestMapping("/intranet")
	public String intranet(Authentication auth, Model model) {
		String vLogin=auth.getName();
		//invocar al método validarSesion
		Usuario bean=servicio.validarSesion(vLogin);
		//invocar al método enlacesDelUsuario
		List<Enlace> lista=servicio.enlacesDelUsuario(bean.getRol().getCodigo());
		//asignar valor a los atributos de tipo sesión
		model.addAttribute("datosUsuario",bean.getApellido()+
					" "+bean.getNombre());
		model.addAttribute("ENLACES",lista);
		model.addAttribute("CODIGOUSUARIO", bean.getCodigo());
		return "intranet";
	}
}
