package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.entity.Chamado;
import com.example.demo.model.entity.User;

public interface ChamadoRepository extends BaseRepository<Chamado, Integer> {

    List<Chamado> findByUser(User user);
    // This interface will automatically provide CRUD operations for the User entity
    // You can add custom query methods here if needed
}
