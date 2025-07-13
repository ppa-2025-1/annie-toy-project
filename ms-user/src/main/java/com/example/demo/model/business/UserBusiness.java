package com.example.demo.model.business;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.dto.NewUser;
import com.example.demo.model.entity.Profile;
import com.example.demo.model.entity.Role;
import com.example.demo.model.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@Business
public class UserBusiness {
    
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private Set<String> defaultRoles;
    private IChamados chamados;
    private INotification notification;

    public UserBusiness(
            IChamados chamados,
            INotification notification,
            UserRepository userRepository, 
            RoleRepository roleRepository,
            @Value("${app.user.default.roles}") Set<String> defaultRoles) {

        this.chamados = chamados;
        this.notification = notification;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;   
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.defaultRoles = defaultRoles;
    }


    public void criarUsuario(NewUser newUser) {
        if (!newUser.email().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Email não é válido");
        }

        if (!newUser.password().matches("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$")) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 8 caracteres e conter pelo menos uma letra e um número");
        }
        
        userRepository.findByEmail(newUser.email())
            .ifPresent(user -> {
                throw new IllegalArgumentException("Usuário com o email " + newUser.email() + " já existe");
            });

        userRepository.findByHandle(newUser.handle())
            .ifPresent(user -> {
                throw new IllegalArgumentException("Usuário com o nome " + newUser.handle() + " já existe");
            });

        User user = new User();
        
        user.setEmail(newUser.email());
        user.setHandle(newUser.handle() != null ? newUser.handle() : generateHandle(newUser.email()));
        user.setPassword(passwordEncoder.encode(newUser.password()));
        
        Set<Role> roles = new HashSet<>();
        
        roles.addAll(roleRepository.findByNameIn(defaultRoles));

        Set<Role> additionalRoles = roleRepository.findByNameIn(newUser.roles());
        if (additionalRoles.size() != newUser.roles().size()) {
            throw new IllegalArgumentException("Alguns papéis não existem");
        }

        if (roles.isEmpty()) {
            throw new IllegalArgumentException("O usuário deve ter pelo menos um papel");
        }

        user.setRoles(roles);

        Profile profile = new Profile();
        
        profile.setName(newUser.name());
        profile.setCompany(newUser.company());
        profile.setType(newUser.type() != null ? newUser.type() : Profile.AccountType.FREE);

        profile.setUser(user);
        user.setProfile(profile);

        userRepository.save(user); 

        chamados.create(user.getHandle(), user.getId());

        notification.send( // AGENDADO (BACKGROUND)
            user.getEmail(),
            "Sua conta foi criada",
            "Parabéns, sua conta foi criada com sucesso. Bem-vindo a bordo do nosso espetacular serviço de usuários. lorem ipsum dolor nocet");
    }

    private String generateHandle(String email) {
        String[] parts = email.split("@");
        String handle = parts[0];
        int i = 1;
        while (userRepository.existsByHandle(handle)) {
            handle = parts[0] + i++;
        }
        return handle;
    }

}
