package com.douglas.project.cep_user.service;

import com.douglas.project.cep_user.client.Endereco;
import com.douglas.project.cep_user.client.ViaCepClient;
import com.douglas.project.cep_user.model.entity.Pessoa;
import com.douglas.project.cep_user.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final ViaCepClient viaCepClient;

    @Autowired
    public PessoaService (PessoaRepository pessoaRepository , ViaCepClient viaCepClient) {
        this.pessoaRepository = pessoaRepository;
        this.viaCepClient = viaCepClient;
    }


    public List<Pessoa> listAll() {
        return pessoaRepository.findAll();
    }

    
    public Pessoa getById(Long id) {
        return pessoaRepository.findById(id).orElseThrow(() -> new RuntimeException("Pessoa não encontrada."));
    }


    public Pessoa create(Pessoa pessoa) {
        if (pessoa.getCep() != null || pessoa.getCep().trim().isEmpty()) {

            Endereco endereco = viaCepClient.buscaEndereco(pessoa.getCep());

            if (endereco.cep() != null) {
                pessoa.setCep(endereco.cep());
                pessoa.setLogradouro(endereco.logradouro());
                pessoa.setBairro(endereco.bairro());
                pessoa.setLocalidade(endereco.localidade());
                pessoa.setEstado(endereco.estado());
                pessoa.setUf(endereco.uf());
            } else { throw new RuntimeException("Cep não encontrado.");}
        } else {
            throw new IllegalArgumentException("Endereço ou CEP não fornecido.");
        }
        pessoa = pessoaRepository.save(pessoa);
        return pessoa;
    }


    public boolean delete(Long id) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        if (pessoa.isPresent()) {
            pessoaRepository.deleteById(id);
            return true;
        } else { return false; }
    }


    public Pessoa update(Long id, Pessoa novaPessoa) {
        Pessoa pessoaExistente = getById(id);

        pessoaExistente.setNome(novaPessoa.getNome());
        pessoaExistente.setTelefone(novaPessoa.getTelefone());
        pessoaExistente.setCep(novaPessoa.getCep());

        if (pessoaExistente.getCep() != null) {
            Endereco novoEndereco = viaCepClient.buscaEndereco(novaPessoa.getCep());
                pessoaExistente.setCep(novoEndereco.cep());
                pessoaExistente.setLogradouro(novoEndereco.logradouro());
                pessoaExistente.setBairro(novoEndereco.bairro());
                pessoaExistente.setLocalidade(novoEndereco.localidade());
                pessoaExistente.setEstado(novoEndereco.estado());
                pessoaExistente.setUf(novoEndereco.uf());
        }
        Pessoa pessoaAtualizada = pessoaRepository.save(pessoaExistente);
        return pessoaAtualizada;
    }


}
