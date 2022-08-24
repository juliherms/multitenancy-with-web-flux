package com.github.juliherms.multitenancy.service;

import com.github.juliherms.multitenancy.converter.FuncionarioConverter;
import com.github.juliherms.multitenancy.dto.FuncionarioDTO;
import com.github.juliherms.multitenancy.model.Funcionario;
import com.github.juliherms.multitenancy.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    @Autowired
    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public Flux<FuncionarioDTO> listar(){
        return funcionarioRepository.findAll().map(FuncionarioConverter::convert);
    }

    public Mono<FuncionarioDTO> buscarPorId(long id) {
        return funcionarioRepository.findById(id).map(FuncionarioConverter::convert);
    }

    public Mono<FuncionarioDTO> criar(FuncionarioDTO funcionarioDTO) {
        Funcionario source = FuncionarioConverter.convert(funcionarioDTO);
        return funcionarioRepository
                .save(source)
                .map(FuncionarioConverter::convert);
    }

    public Mono<Void> deletar(long id) {
        return funcionarioRepository.deleteById(id);
    }
}
