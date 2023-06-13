package net.clinica.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.clinica.entity.TipoMedicamento;

public interface TipoRepository extends JpaRepository<TipoMedicamento, Integer>{
	//hql--->select
	//select *from tb_tipo_medicamento where cod_lab=?
	@Query("select tm from TipoMedicamento tm where tm.laboratorio.codigo=?1")
	public List<TipoMedicamento> findAllByLaboratorio(Integer codLab);
	
}
