package net.clinica.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import net.clinica.entity.MedicamentoHasBoleta;

public interface MedicamentoHasBoletaRepository extends JpaRepository<MedicamentoHasBoleta, Integer> {

}
