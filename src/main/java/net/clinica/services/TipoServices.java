package net.clinica.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.clinica.dao.TipoRepository;
import net.clinica.entity.TipoMedicamento;

@Service
public class TipoServices {
	@Autowired
	private TipoRepository repo;
	
	public List<TipoMedicamento> listarTodos(){
		return repo.findAll();
	}
	
	public List<TipoMedicamento> listarPorLaboratorio(Integer codLab){
		return repo.findAllByLaboratorio(codLab);
	}
	
}
