package br.com.fiap.locatech.locatech.services;

import br.com.fiap.locatech.locatech.dtos.AluguelRequestDTO;
import br.com.fiap.locatech.locatech.entities.Aluguel;
import br.com.fiap.locatech.locatech.repositories.AluguelRepository;
import br.com.fiap.locatech.locatech.repositories.AluguelRepositoryImp;
import br.com.fiap.locatech.locatech.repositories.VeiculoRepository;
import br.com.fiap.locatech.locatech.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AluguelService {

    private final AluguelRepository aluguelRepository;
    private final VeiculoRepository veiculoRepository;
    private final AluguelRepositoryImp aluguelRepositoryImp;

    public AluguelService(AluguelRepository aluguelRepository, AluguelRepositoryImp aluguelRepositoryImp, VeiculoRepository veiculoRepository) {
        this.aluguelRepository = aluguelRepository;
        this.aluguelRepositoryImp = aluguelRepositoryImp;
        this.veiculoRepository = veiculoRepository;
    }

    public Optional<Aluguel> findAluguelById(Long id) {
        return Optional.ofNullable(aluguelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluguel não encontrado com o id: " + id)));
    }

    public List<Aluguel> findAllAlugueis(int page, int size) {
        int offset = (page - 1) * size;
        return aluguelRepository.findAll(size, offset);
    }

    public void saveAluguel(AluguelRequestDTO aluguel) {
        var aluguelEntity = calculaAluguel(aluguel);
        var save = aluguelRepository.save(aluguelEntity);
        Assert.state(save == 1, "Erro ao salvar aluguel " + aluguel.pessoaId());
    }

    public void updateAluguel(Aluguel aluguel, Long id) {
        var update = aluguelRepository.update(aluguel, id);
        if (update == 0) {
            throw new RuntimeException("Aluguel não encontrado com o id: " + id);
        }
    }

    public void deleteAluguel(Long id) {
        var delete = aluguelRepository.delete(id);
        if (delete == 0) {
            throw new RuntimeException("Aluguel não encontrada com o id: " + id);
        }
    }

    private Aluguel calculaAluguel(AluguelRequestDTO aluguelRequestDTO) {
        var veiculo = veiculoRepository.findById(aluguelRequestDTO.veiculoId())
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado com o id: " + aluguelRequestDTO.veiculoId()));

        var quantidadeDias = BigDecimal.valueOf(aluguelRequestDTO.dataFim().getDayOfYear() - aluguelRequestDTO.dataInicio().getDayOfYear());

        var valor = veiculo.getValorDiaria().multiply(quantidadeDias);

        return new Aluguel(aluguelRequestDTO, valor);
    }

}
