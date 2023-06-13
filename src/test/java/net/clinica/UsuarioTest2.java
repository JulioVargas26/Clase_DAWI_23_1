package net.clinica;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import net.clinica.dao.ClienteRepository;
import net.clinica.entity.Cliente;
import net.clinica.entity.Medicamento;
import net.clinica.entity.TipoMedicamento;
import net.clinica.services.MedicamentoServices;

@SpringBootTest
class UsuarioTest2 {
	@Autowired
	private ClienteRepository repo;
	
	@Test
	void contextLoads() {
		List<Cliente> lista=repo.findByPaternoStartingWith("a");
		for(Cliente a:lista)
			System.out.println("ppp : "+a.getPaterno());
	}

}










