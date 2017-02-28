package com.walker.loginservice.repository;


import com.walker.loginservice.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rettwalker on 2/10/17.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findOneByName(String name);
}
