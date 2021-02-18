package br.com.aco.carros.domain;

import br.com.aco.carros.api.exception.ObjectNotFoundException;
import br.com.aco.carros.domain.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarroService {

    @Autowired
    private CarroRepository carroRepository;

    public List<CarroDTO> getCarros() {

//        List<Carro> cars = carroRepository.findAll();
//        List<CarroDTO> carrotDTOList = new ArrayList<>();
//        for (Carro car : cars) {
//            carrotDTOList.add(new CarroDTO(car));
//        }

        return carroRepository.findAll().stream().map(CarroDTO::new).collect(Collectors.toList());
    }

    public CarroDTO getCarroById(Long id) {
        return carroRepository.findById(id).map(CarroDTO::new).orElseThrow(() -> new ObjectNotFoundException("Carro não encontrado"));
    }

    public List<CarroDTO> getCarroByTipo(String tipo) {
        return carroRepository.findByTipo(tipo).stream().map(CarroDTO::new).collect(Collectors.toList());
    }

/*
    public List<Carro> getCarrosFake() {
        List<Carro> carros = new ArrayList<>();


        carros.add(new Carro(1L, "Fusca","Esporte"));
        carros.add(new Carro(2L, "Brasilia"));
        carros.add(new Carro(3L, "Chevette"));

        return carros;

    }
*/

    public CarroDTO insert(Carro carro) {
        Assert.isNull(carro.getId(), "Não foi possível inserir o registro");

        return new CarroDTO(carroRepository.save(carro));

    }

   public CarroDTO update(Carro carro, Long id) {
        Assert.notNull(id, "Não foi possível atualizar o registro");

        //Busca o caroo no banco de dados
        Optional<Carro> optionalCarro = carroRepository.findById(id);
        if (optionalCarro.isPresent()) {
            Carro db = optionalCarro.get();
            //Copiar as propriedades
            db.setNome(carro.getNome());
            db.setTipo(carro.getTipo());
            System.out.println("Carro id " + db.getId());

            //Atualiza o carro
            carroRepository.save(db);
            return new CarroDTO(carro);
        } else {
            throw new RuntimeException("Não foi possível atualizar o registro");
        }

    }

    public void delete(Long id) {
            carroRepository.deleteById(id);

    }
}