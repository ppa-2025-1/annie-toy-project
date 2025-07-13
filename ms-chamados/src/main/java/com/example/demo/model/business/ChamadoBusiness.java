package com.example.demo.model.business;

import java.util.List;
import java.util.Optional;

import com.example.demo.dto.NewChamado;
import com.example.demo.dto.NewChamadoNewUser;
import com.example.demo.model.entity.Chamado;
import com.example.demo.repository.ChamadoRepository;

@Business
public class ChamadoBusiness {
    private final String SITUACAO_NOVO = "NOVO";
    private final String SITUACAO_ANDAMENTO = "ANDAMENTO";
    private final String SITUACAO_RESOLVIDO = "RESOLVIDO";
    private final String SITUACAO_CANCELADO = "CANCELADO";

    private final String SITUACAO_INICIAL = SITUACAO_NOVO;
    
    private ChamadoRepository chamadoRepository;

    public ChamadoBusiness(
            ChamadoRepository chamadoRepository) {

        this.chamadoRepository = chamadoRepository; 
    }


    public void criarChamado(NewChamado newChamado) {
        Chamado chamado = new Chamado();

        chamado.setAcao(newChamado.acao());
        chamado.setObjeto(newChamado.objeto());
        chamado.setDetalhamento(newChamado.detalhamento());
        chamado.setUserId(newChamado.userId());
        chamado.setSituacao(SITUACAO_INICIAL);

        chamadoRepository.save(chamado); 
    }

    public Chamado getChamado(Integer id) {
        return chamadoRepository.findById(id).get();
    }

    public List<Chamado> getAllChamados() {
        return chamadoRepository.findAll();
    }

    public void alterarSituacao(Integer chamadoId, String novaSituacao) {
        Chamado chamado = getChamado(chamadoId);
        if (chamado == null) {
            throw new IllegalArgumentException("O chamado não existe!");
        }

        String situacaoAtual = chamado.getSituacao();

        if (!trocaSituacaoValida(situacaoAtual, novaSituacao)) {
            throw new IllegalArgumentException("Troca de situação inválida!");
        }

        chamado.setSituacao(novaSituacao);
        chamadoRepository.save(chamado);
    }

    public void criarChamadoNovoUser(NewChamadoNewUser newChamadoNewUser) {
        String ACAO_PADRAO = "CRIAR"; 
        String OBJETO_PADRAO = "E-MAIL"; 
        String DETALHAMENTO_PADRAO = newChamadoNewUser.userHandle() + "@tads.rg.ifrs.edu.br\nCriar e-mail para novo usuário.";  
        Integer userId = newChamadoNewUser.userId();

        NewChamado newChamado = new NewChamado(ACAO_PADRAO, OBJETO_PADRAO, DETALHAMENTO_PADRAO, userId);
        criarChamado(newChamado);
    }

    private boolean trocaSituacaoValida(String situacaoAntiga, String situacaoNova) {
        switch (situacaoNova) {
            case SITUACAO_RESOLVIDO:
                if (situacaoAntiga.equals(SITUACAO_ANDAMENTO)) {
                    return true;
                }
                break;
            case SITUACAO_ANDAMENTO:
                if (situacaoAntiga.equals(SITUACAO_NOVO)) {
                    return true;
                }
                break;
            case SITUACAO_CANCELADO: 
                if (situacaoAntiga.equals(SITUACAO_NOVO) || situacaoAntiga.equals(SITUACAO_ANDAMENTO)) {
                    return true;
                }
                break;
            default:
                return false;
        }

        return false;
    }
}