package net.clinica.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import net.clinica.entity.Laboratorio;
import net.clinica.entity.TipoMedicamento;

public interface LaboratorioRepository extends JpaRepository<Laboratorio, Integer>{

}
