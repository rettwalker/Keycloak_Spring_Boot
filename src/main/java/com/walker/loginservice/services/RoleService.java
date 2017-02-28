package com.walker.loginservice.services;

import com.walker.loginservice.domain.Permission;
import com.walker.loginservice.domain.Role;
import com.walker.loginservice.exceptions.RoleAlreadyExistsException;
import com.walker.loginservice.exceptions.RoleNotFoundException;
import com.walker.loginservice.repository.PermissionRepository;
import com.walker.loginservice.repository.RoleRepository;
import com.walker.loginservice.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by rettwalker on 2/10/17.
 */
@Service
public class RoleService {

    private final Logger log = LoggerFactory.getLogger(RoleService.class);

    private RoleRepository roleRepository;

    private PermissionRepository permissionRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository){
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public Role addNewRole(Role newRole) throws RoleAlreadyExistsException {
        Role role;
        role = roleRepository.findOneByName(newRole.getName());
        if(role == null){
            role = newRole;
            role.setName(newRole.getName());
            role.setPermissions(newRole.getPermissions());
            role = roleRepository.save(role);
        } else {
            throw new RoleAlreadyExistsException(Constants.ROLE_ALREADY_EXISTS);
        }
        return role;
    }

    public List<Role> findAll(){
        return roleRepository.findAll();
    }

    public void deleteRole(Long id) {
        roleRepository.delete(id);
    }

    public Role getRoleById(Long id) throws RoleNotFoundException {
        Role role = roleRepository.findOne(id);
        if(role == null){
            throw new RoleNotFoundException("Role with ID "+id+" does not exist");
        }
        return role;
    }

    public Role getRoleByName(String name) throws RoleNotFoundException {
        Role role = roleRepository.findOneByName(name);

        if(role == null ){
            throw new RoleNotFoundException("Role named "+ name + " was not found");
        }

        return role;
    }

    public Role addPermissionToRole(Permission permission, Long id) throws RoleNotFoundException {
        Role role = roleRepository.findOne(id);
        if(role != null ){
            role.getPermissions().add(permission);

            role = roleRepository.save(role);

        } else {
            throw new RoleNotFoundException("Role with ID: "+id+" not found");
        }
        return role;
    }

    public Role removePermissionFromRole(Long id, Long permissionId) throws RoleNotFoundException {

        Role role = roleRepository.findOne(id);
        if(role != null ){
            Permission permission = permissionRepository.findOne(permissionId);
            role.getPermissions().remove(permission);

            role = roleRepository.save(role);
        } else {
            throw new RoleNotFoundException("Role with ID: "+id+" not found");
        }

        return role;
    }
}
