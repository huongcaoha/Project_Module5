package com.ra.module5_project.service;

import com.ra.module5_project.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitialService {
    @Autowired
    private RoleService roleService;

    public void initialApplication(){
        roleService.initialRole();
    }

}
