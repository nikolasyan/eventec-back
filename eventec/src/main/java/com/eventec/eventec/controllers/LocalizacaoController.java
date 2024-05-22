package com.eventec.eventec.controllers;
import com.eventec.eventec.models.Endereco;
import com.eventec.eventec.services.EventItemService;
import com.eventec.eventec.services.LocalizacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LocalizacaoController {

    @Autowired
    private EventItemService eventItemService;

    @Autowired
    private LocalizacaoService localizacaoService;

    @GetMapping("/endereco/{cep}")
    public Endereco getEnderecoByCep(@PathVariable String cep) {
        return localizacaoService.buscarEnderecoPorCep(cep);
    }
}