package com.github.juliherms.multitenancy.repository;

import com.github.juliherms.multitenancy.model.Funcionario;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends ReactiveCrudRepository<Funcionario, Long> {
}
