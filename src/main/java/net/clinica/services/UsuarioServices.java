package net.clinica.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.clinica.dao.UsuarioRepository;
import net.clinica.entity.Enlace;
import net.clinica.entity.Usuario;

@Service
public class UsuarioServices {
	@Autowired
	private UsuarioRepository repo;
	
	public Usuario validarSesion(String vLogin) {
		return repo.iniciarSesion(vLogin);
	}	
	public List<Enlace> enlacesDelUsuario(int codRol){
		return repo.traerEnlacesDelUsuario(codRol);
	}
}






