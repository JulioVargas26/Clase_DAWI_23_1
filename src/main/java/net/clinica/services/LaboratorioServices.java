package net.clinica.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.clinica.dao.LaboratorioRepository;
import net.clinica.dao.TipoRepository;
import net.clinica.entity.Laboratorio;
import net.clinica.entity.TipoMedicamento;

@Service
public class LaboratorioServices {
	@Autowired
	private LaboratorioRepository repo;
	
	public List<Laboratorio> listarTodos(){
		return repo.findAll();
	}
	
}
