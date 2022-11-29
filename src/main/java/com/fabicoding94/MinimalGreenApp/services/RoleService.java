package com.fabicoding94.MinimalGreenApp.services;

import com.fabicoding94.MinimalGreenApp.entities.Role;
import com.fabicoding94.MinimalGreenApp.entities.RoleType;
import com.fabicoding94.MinimalGreenApp.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository repository;

    // GET BY ID
    public Role getById(Long id) throws Exception {
        Optional<Role> ba = repository.findById(id);
        if ( ba.isEmpty() )
            throw new Exception("Role not available");
        return ba.get();
    }

    // GET BY ROLE
    public Role getByRole( RoleType roleType) throws Exception {
        Optional<Role> ba = repository.findByRoleType(roleType);
        if ( ba.isEmpty() )
            throw new Exception("Role not available");
        return ba.get();
    }

    // GET ALL PAGEABLE
    public Page<Role> getAllPaginate(Pageable p) {
        return repository.findAll(p);
    }

    // GET ALL
    public List<Role> getAll() {
        return repository.findAll();
    }

    // CREATE
    public Role save( Role x) {
        return repository.save(x);
    }

    // DELETE
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
