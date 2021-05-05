package com.emrecalik.swe573.server.service;

import com.emrecalik.swe573.server.domain.Role;

public interface RoleService {
    Role getRoleByRoleName(Role.RoleName roleName);
}
