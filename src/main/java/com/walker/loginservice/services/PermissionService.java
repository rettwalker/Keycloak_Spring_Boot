package com.walker.loginservice.services;

import com.walker.loginservice.domain.Permission;
import com.walker.loginservice.exceptions.PermissionNotFoundException;
import com.walker.loginservice.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jmw0705 on 2/15/17.
 */
@Service
public class PermissionService {

    private PermissionRepository permissionRepository;

    @Autowired
    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }


    public List<Permission> findAllPermissions() {
        return permissionRepository.findAll();
    }

    public Permission findPermissionById(Long id) throws PermissionNotFoundException {
        Permission permission = permissionRepository.findOne(id);
        if(permission == null){
            throw new PermissionNotFoundException("Permission with ID "+id+" not found");
        }
        return permission;
    }
}
