package com.douglas.project.cep_user.repository;

import com.douglas.project.cep_user.model.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
}
