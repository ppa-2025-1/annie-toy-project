package com.example.demo.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ChamadoSituacao;
import com.example.demo.dto.NewChamado;
import com.example.demo.model.business.ChamadoBusiness;
import com.example.demo.model.entity.Chamado;
import com.example.demo.repository.ChamadoRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/chamados")
public class ChamadoController extends AbstractController {

    private final ChamadoRepository chamadoRepository;
    private final ChamadoBusiness chamadoBusiness;

    public ChamadoController(ChamadoRepository chamadoRepository,
                ChamadoBusiness chamadoBusiness) {
        this.chamadoRepository = chamadoRepository;
        this.chamadoBusiness = chamadoBusiness;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createNewChamado(
        @Valid
        @RequestBody
        NewChamado newChamado) {

        chamadoBusiness.criarChamado(newChamado);

    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Chamado>> getChamados() {
        return ResponseEntity.ok(chamadoBusiness.getAllChamados());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Chamado> getChamado(@PathVariable Integer id) {
        return ResponseEntity.ok(chamadoBusiness.getChamado(id));
    }

    @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public void changeChamadoStatus(
        @Valid
        @RequestBody
        ChamadoSituacao chamadoSituacao, @PathVariable Integer id) {
            System.out.println(chamadoSituacao.situacao()+"\n\n\n\n\n\n\n\n\n\n");

        chamadoBusiness.alterarSituacao(id, chamadoSituacao.situacao());
    }
}
