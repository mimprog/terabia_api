package com.terabia.terabia.models;


import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum Role {

    /*USER(Collections.emptySet()),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_CREATE,
                    ADMIN_DELETE,
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_CREATE,
                    MANAGER_DELETE
            )
    ),
    MANAGER(
            Set.of(
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_CREATE,
                    MANAGER_DELETE
            )
    ),

    SUPPLIER(
            Set.of(
                    SUPPLIER_READ
            )
    );*/

    USER,
    MANAGER,
    SUPPLIER,
    ADMIN;

    /*@Getter
    private final Set <Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }*/

}
