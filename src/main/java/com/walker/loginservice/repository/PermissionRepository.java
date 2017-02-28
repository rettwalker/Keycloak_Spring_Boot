package com.walker.loginservice.repository;

import com.walker.loginservice.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jmw0705 on 2/15/17.
 */
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
