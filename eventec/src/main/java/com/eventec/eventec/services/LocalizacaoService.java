package com.eventec.eventec.services;
import com.eventec.eventec.models.Endereco;
import com.eventec.eventec.repositories.CepRepository;
import org.springframework.stereotype.Service;

@Service
public class LocalizacaoService {

    private CepRepository cepRepository;

    public LocalizacaoService(CepRepository cepRepository) {
        this.cepRepository = cepRepository;
    }

    public Endereco buscarEnderecoPorCep(String cep) {
        return cepRepository.findByCep(cep);
    }

    }
