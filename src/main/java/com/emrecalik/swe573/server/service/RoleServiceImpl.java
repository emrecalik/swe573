package com.emrecalik.swe573.server.service;

import com.emrecalik.swe573.server.domain.Role;
import com.emrecalik.swe573.server.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleByRoleName(Role.RoleName roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
