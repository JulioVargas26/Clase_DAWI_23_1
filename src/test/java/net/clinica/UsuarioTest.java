package net.clinica;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import net.clinica.entity.Medicamento;
import net.clinica.entity.TipoMedicamento;
import net.clinica.services.MedicamentoServices;

@SpringBootTest
class UsuarioTest {
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Test
	void contextLoads() {
		String clave=encoder.encode("123");
		System.out.println("Clave es : "+clave);
	}

}










