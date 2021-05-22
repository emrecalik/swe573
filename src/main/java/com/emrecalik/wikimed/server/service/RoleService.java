package com.emrecalik.wikimed.server.service;

import com.emrecalik.wikimed.server.domain.Role;

public interface RoleService {
    Role getRoleByRoleName(Role.RoleName roleName);
}
