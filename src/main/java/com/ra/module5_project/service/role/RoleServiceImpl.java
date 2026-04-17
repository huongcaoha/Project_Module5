package com.ra.module5_project.service.role;

import com.ra.module5_project.model.entity.Role;
import com.ra.module5_project.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository ;
    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findByName(String roleName) {
        return roleRepository.findRoleByRoleName(roleName);
    }

    @Override
    public void initialRole() {
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) {
            Role roleAdmin = new Role();
            roleAdmin.setRoleName("ADMIN");
            Role roleUser = new Role();
            roleUser.setRoleName("USER");
            roles.add(roleAdmin);
            roles.add(roleUser);
            roleRepository.saveAll(roles);
        }
    }
}
