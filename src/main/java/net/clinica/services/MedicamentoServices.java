package net.clinica.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.clinica.dao.MedicamentoRepository;
import net.clinica.entity.Medicamento;

@Service
public class MedicamentoServices {
	@Autowired
	private MedicamentoRepository repo;
	
	public void registrar(Medicamento m) {
		repo.save(m);
	}
	public void actualizar(Medicamento m) {
		repo.save(m);
	}
	public void eliminarPorID(Integer cod) {
		repo.deleteById(cod);
	}
	public Medicamento buscarPorID(Integer cod) {
		return repo.findById(cod).orElse(null);
	}
	public List<Medicamento> listarTodos(){
		return repo.findAll();
	}
	public List<Medicamento> listarPorNombre(String nom){
		return repo.findByNombreStartingWith(nom);
	}
}
