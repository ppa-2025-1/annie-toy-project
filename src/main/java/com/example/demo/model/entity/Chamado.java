package com.example.demo.model.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import com.example.demo.model.enums.SituacoesChamado;

@Entity
@Table(name = "chamados")
public class Chamado extends BaseEntity {

    @Column(nullable = false, length = 255)
    private String acao;

    @Column(nullable = false, length = 255)
    private String objeto;

    @Column(nullable = false, length = 255)
    private String detalhamento;
    
    @Column(nullable = false, length = 255)
    private SituacoesChamado situacao;
    
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private User user;

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public String getDetalhamento() {
        return detalhamento;
    }

    public void setDetalhamento(String detalhamento) {
        this.detalhamento = detalhamento;
    }

    public SituacoesChamado getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacoesChamado situacao) {
        this.situacao = situacao;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
