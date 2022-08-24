package com.github.juliherms.multitenancy.controller;

import com.github.juliherms.multitenancy.dto.FuncionarioDTO;
import com.github.juliherms.multitenancy.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class FuncionarioController {
    private final FuncionarioService service;

    @Autowired
    public FuncionarioController(FuncionarioService service) {
        this.service = service;
    }

    @GetMapping("/funcionarios")
    public Flux<FuncionarioDTO> listar() {
        return service.listar();
    }

    @GetMapping("/funcionarios/{id}")
    public Mono<FuncionarioDTO> bucarPorId(@PathVariable("id") long id) {
        return service.buscarPorId(id);
    }

    @PostMapping("/funcionarios")
    public Mono<FuncionarioDTO> criar(@RequestBody FuncionarioDTO funcionarioDTO) {
        return service.criar(funcionarioDTO);
    }

    @DeleteMapping("/customers/{id}")
    public Mono<Void> deletar(@PathVariable("id") long id) {
        return service.deletar(id);
    }
}
