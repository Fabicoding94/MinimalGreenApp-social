package com.fabicoding94.MinimalGreenApp.repositories;


import com.fabicoding94.MinimalGreenApp.entities.user.Role;
import com.fabicoding94.MinimalGreenApp.entities.user.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleType(RoleType roleType);


}
