package net.clinica.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.clinica.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	//@Query("select c from Cliente c where c.paterno like ?1")
	public List<Cliente> findByPaternoStartingWith(String ape);
}
