package br.com.aco.carros;

import br.com.aco.carros.api.exception.ObjectNotFoundException;
import br.com.aco.carros.domain.Carro;
import br.com.aco.carros.domain.CarroService;
import br.com.aco.carros.domain.dto.CarroDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static junit.framework.TestCase.*;

@SpringBootTest
class CarrosApplicationTests {

	@Autowired
	private CarroService carroService;

	@Test
	void testeSave() {
		Carro carro = new Carro();
		carro.setNome("Ferrari");
		carro.setTipo("esportivos");

		CarroDTO carroDTO = carroService.insert(carro);
		assertNotNull(carroDTO);

		Long id = carroDTO.getId();
		assertNotNull(id);

		//Buscar o objeto
		CarroDTO optionalCarroDTO = carroService.getCarroById(id);
		assertNotNull(optionalCarroDTO);

		assertEquals("Ferrari",carroDTO.getNome());
		assertEquals("esportivos",carroDTO.getTipo());

		//Deletar o objeto
		carroService.delete(id);

		//Verificar se deletou
		try {
			assertNotNull(carroService.getCarroById(id));
			fail("O Carro Não foi Excluido");
		}catch (ObjectNotFoundException e){
			// OK

		}


	}

	@Test
	void testeLista() {

		List<CarroDTO> carroDTOList =  carroService.getCarros();
		assertEquals(30,carroDTOList.size());
	}

	@Test
	void testeGet() {

		CarroDTO carroOp = carroService.getCarroById(11L);
		assertNotNull(carroOp);

		assertEquals("Ferrari FF",carroOp.getNome());
	}

	@Test
	void testeListaPorTipo() {

		assertEquals(10, carroService.getCarroByTipo("classicos").size());
		assertEquals(10, carroService.getCarroByTipo("esportivos").size());
		assertEquals(10, carroService.getCarroByTipo("luxo").size());

		assertEquals(0, carroService.getCarroByTipo("x").size());
	}

}
