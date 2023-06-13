package net.clinica.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.clinica.dao.ClienteRepository;
import net.clinica.entity.Cliente;

@Service
public class ClienteServices {
	@Autowired
	private ClienteRepository repo;
	
	public List<Cliente> listaClientesPorPaterno(String pat){
		return repo.findByPaternoStartingWith(pat);
	}
	
}
