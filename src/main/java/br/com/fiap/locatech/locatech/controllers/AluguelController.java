package br.com.fiap.locatech.locatech.controllers;

import br.com.fiap.locatech.locatech.dtos.AluguelRequestDTO;
import br.com.fiap.locatech.locatech.entities.Aluguel;
import br.com.fiap.locatech.locatech.services.AluguelService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alugueis")
public class AluguelController {

    private static final Logger logger = LoggerFactory.getLogger(AluguelController.class);

    private final AluguelService aluguelService;

    public AluguelController(AluguelService aluguelService) {
        this.aluguelService = aluguelService;
    }

    // http://localhost:8080/alugueis?page=1&size=10
    @GetMapping
    public ResponseEntity<List<Aluguel>> findAllAlugueis(@RequestParam("page") int page,
                                                         @RequestParam("size") int size) {
        logger.info("Foi acessado o endpoint de alugueis /alugueis");
        var alugueis = aluguelService.findAllAlugueis(page, size);
        return ResponseEntity.ok(alugueis);
    }

    // http://localhost:8080/alugueis/1
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Aluguel>> findAluguel(
            @PathVariable("id") Long id
    ) {
        logger.info("/alugueis/" + id);
        var aluguel = aluguelService.findAluguelById(id);
        return ResponseEntity.ok(aluguel);
    }

    @PostMapping
    public ResponseEntity<Void> saveAluguel(
            @Valid @RequestBody AluguelRequestDTO aluguel
    ) {
        logger.info("POST -> /alugueis");
        aluguelService.saveAluguel(aluguel);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAluguel(
            @RequestBody Aluguel aluguel,
            @PathVariable("id") Long id
    ) {
        logger.info("PUT -> /alugueis/" + id);
        aluguelService.updateAluguel(aluguel, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAluguel(
            @PathVariable("id") Long id
    ) {
        logger.info("DELETE -> /alugueis/" + id);
        aluguelService.deleteAluguel(id);
        return ResponseEntity.ok().build();
    }

}
