package com.flexbank.ws.entity;

public enum RoleType {
    ROLE_ADMIN(1), ROLE_CUSTOMER(2);

    private Integer roleId;

    RoleType(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getRoleId() {
        return roleId;
    }
}
