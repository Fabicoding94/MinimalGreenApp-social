package com.fabicoding94.MinimalGreenApp.controllers;

import com.fabicoding94.MinimalGreenApp.entities.user.Role;
import com.fabicoding94.MinimalGreenApp.entities.user.RoleType;
import com.fabicoding94.MinimalGreenApp.services.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/roles")
public class RoleController {


    @Autowired
    private RoleService rs;

    //------------------------- Get ---------------------------

    // GET ALL
    @GetMapping("")
    //@PreAuthorize( "hasRole('ADMIN')" )
    public List<Role> getAllRoles() {
        return rs.getAll();
    }

    // GET ALL PAGEABLE
    @GetMapping("/pageable")
    public ResponseEntity<Page<Role>> getAllRolesPageable(Pageable p) {
        Page<Role> findAll = rs.getAllPaginate(p);
        if (findAll.hasContent()) {
            return new ResponseEntity<>(findAll, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // GET BY ID
    @GetMapping("/{id}")
    @PreAuthorize( "hasRole('ADMIN')" )
    public ResponseEntity<Role> readById(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(rs.getById(id), HttpStatus.OK);
    }
//---------------------------- Post --------------------------------

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Role saveRole(
            @RequestParam(value="roleType",required=false) RoleType roleType
    ) {
        Role role = Role.builder()
                .roleType(roleType)
                .build();


        return rs.save(role);
    }

//---------------------------- Put ---------------------------------


    // UPDATE
    @PutMapping("")
    //@PreAuthorize( "hasRole('ADMIN')" )
    public void update(@RequestBody Role role) {
        try {
            rs.save(role);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
    //-------------------------- Delete -------------------------------
    // DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@PathVariable Long id) {
        try {
            rs.deleteById(id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
