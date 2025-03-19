package com.douglas.project.cep_user.controller;

import com.douglas.project.cep_user.model.entity.Pessoa;
import com.douglas.project.cep_user.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    private final PessoaService pessoaService;

    @Autowired
    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping("/buscar")
    public List<Pessoa> listAll() {
        return pessoaService.listAll();
    }

    @GetMapping("/buscar/{id}")
    public Pessoa getById(@PathVariable Long id) {
        return pessoaService.getById(id);
    }

    @PostMapping("/salvar")
    public ResponseEntity<?> create(@RequestBody Pessoa pessoa) {
        try {
            Pessoa novaPessoa = pessoaService.create(pessoa);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaPessoa);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e ) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Dados inválidos.");
        }
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<String> delete(@RequestBody Pessoa pessoa) {
         boolean pessoaDeletada = pessoaService.delete(pessoa.getId());

         if (pessoaDeletada) {
             return ResponseEntity.ok("Pessoa deletada com sucesso.");
         } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa com id: " + pessoa.getId() + " não encontrada.");
         }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Pessoa> update(@PathVariable Long id, @RequestBody Pessoa novaPessoa) {
        Pessoa pessoaAtualizada = pessoaService.update(id, novaPessoa);
        return ResponseEntity.ok(pessoaAtualizada);
    }


}
