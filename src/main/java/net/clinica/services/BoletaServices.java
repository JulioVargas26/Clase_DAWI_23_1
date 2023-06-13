package net.clinica.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.clinica.dao.BoletaRepository;
import net.clinica.dao.MedicamentoHasBoletaRepository;
import net.clinica.dao.MedicamentoRepository;
import net.clinica.entity.Boleta;
import net.clinica.entity.MedicamentoHasBoleta;
import net.clinica.entity.MedicamentoHasBoletaPK;

@Service
public class BoletaServices {
	@Autowired
	private BoletaRepository repoBol;
	
	@Autowired
	private MedicamentoHasBoletaRepository repoDetalle;
	
	@Transactional
	public void registrarBoleta(Boleta bean) {
		try {
			//grabar Boleta "cabecera"
			repoBol.save(bean);
			//grabar MedicamentoHasBoleta "detalle"
			for(MedicamentoHasBoleta mhb:bean.getListaMedicamentoHasBoleta()) {
				//crear objeto de la clase MedicamentoHasBoletaPK
				MedicamentoHasBoletaPK pk=new MedicamentoHasBoletaPK();
				//setear
				pk.setNumeroBoleta(bean.getNumeroBoleta());
				pk.setCodigoMedicamento(mhb.getMedicamento().getCodigo());
				//enviar pk a mhb
				mhb.setPk(pk);
				repoDetalle.save(mhb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
