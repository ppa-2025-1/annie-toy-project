package com.example.demo.model.business;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.dto.NewChamado;
import com.example.demo.model.entity.Chamado;
import com.example.demo.model.entity.User;
import com.example.demo.model.enums.SituacoesChamado;
import com.example.demo.repository.ChamadoRepository;
import com.example.demo.repository.UserRepository;

@Business
public class ChamadoBusiness {
    private SituacoesChamado SITUACAO_INICIAL = SituacoesChamado.NOVO;
    
    private ChamadoRepository chamadoRepository;
    private UserRepository userRepository;

    public ChamadoBusiness(
            UserRepository userRepository, 
            ChamadoRepository chamadoRepository) {

        this.chamadoRepository = chamadoRepository;   
        this.userRepository = userRepository;
    }


    public void criarChamado(NewChamado newChamado) {
        Chamado chamado = new Chamado();
        Optional<User> usuarioCriador = userRepository.findById(newChamado.user());

        if (usuarioCriador.isEmpty()) {
            throw new IllegalArgumentException("O usuário criando o chamado não existe!");
        }
        
        chamado.setAcao(newChamado.acao());
        chamado.setObjeto(newChamado.objeto());
        chamado.setDetalhamento(newChamado.detalhamento());
        chamado.setUser(usuarioCriador.get());
        chamado.setSituacao(SITUACAO_INICIAL);

        chamadoRepository.save(chamado); 
    }

    public Chamado getChamado(Integer id) {
        return chamadoRepository.findById(id).get();
    }

    public List<Chamado> getAllChamados() {
        return chamadoRepository.findAll();
    }

    public void alterarSituacao(Integer chamadoId, SituacoesChamado novaSituacao) {
        Chamado chamado = getChamado(chamadoId);
        if (chamado == null) {
            throw new IllegalArgumentException("O chamado não existe!");
        }

        SituacoesChamado situacaoAtual = chamado.getSituacao();

        if (!trocaSituacaoValida(situacaoAtual, novaSituacao)) {
            throw new IllegalArgumentException("Troca de situação inválida!");
        }

        chamado.setSituacao(novaSituacao);
        chamadoRepository.save(chamado);
    }

    public void criarChamadoNovoUser(User user) {
        String ACAO_PADRAO = "CRIAR"; 
        String OBJETO_PADRAO = "E-MAIL"; 
        String DETALHAMENTO_PADRAO = user.getHandle() + "@tads.rg.ifrs.edu.br\nCriar e-mail para novo usuário.";  
        Integer userId = user.getId();

        NewChamado newChamado = new NewChamado(ACAO_PADRAO, OBJETO_PADRAO, DETALHAMENTO_PADRAO, userId);
        criarChamado(newChamado);
    }

    private boolean trocaSituacaoValida(SituacoesChamado situacaoAntiga, SituacoesChamado situacaoNova) {
        switch (situacaoNova) {
            case RESOLVIDO:
                if (situacaoAntiga.equals(SituacoesChamado.ANDAMENTO)) {
                    return true;
                }
                break;
            case ANDAMENTO:
                if (situacaoAntiga.equals(SituacoesChamado.NOVO)) {
                    return true;
                }
                break;
            case CANCELADO: 
                if (situacaoAntiga.equals(SituacoesChamado.NOVO) || situacaoAntiga.equals(SituacoesChamado.ANDAMENTO)) {
                    return true;
                }
                break;
            default:
                return false;
        }

        return false;
    }
}
