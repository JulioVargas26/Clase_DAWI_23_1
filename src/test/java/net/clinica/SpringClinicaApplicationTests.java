package net.clinica;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.clinica.entity.Medicamento;
import net.clinica.entity.TipoMedicamento;
import net.clinica.services.MedicamentoServices;

@SpringBootTest
class SpringClinicaApplicationTests {
	@Autowired
	private MedicamentoServices servicio;
	
	@Test
	void contextLoads() {
		try {
			//crear objeto de la entidad Medicamento
			Medicamento m=new Medicamento();
			//setear atributos del objeto "m"
			m.setNombre("Prueba 1");
			m.setDescripcion("Data");
			m.setPrecio(15.6);
			m.setStock(19);
			m.setFecha(LocalDate.now());
			//crear objeto de la entidad TipoMedicamento
			TipoMedicamento tm=new TipoMedicamento();
			//setear codigo
			tm.setCodigo(3);
			//enviar objeto "tm" al objeto "m"
			m.setTipo(tm);
			servicio.registrar(m);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}










