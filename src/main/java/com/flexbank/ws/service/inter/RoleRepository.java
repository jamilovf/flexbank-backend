package com.flexbank.ws.service.inter;

import com.flexbank.ws.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
