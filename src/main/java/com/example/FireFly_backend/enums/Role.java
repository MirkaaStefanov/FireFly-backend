package com.example.FireFly_backend.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.FireFly_backend.enums.Permission.USER_READ;
import static com.example.FireFly_backend.enums.Permission.*;



/**
 * Enumeration representing different roles in the application.
 * Each role has a set of associated permissions.
 */
@Getter
@RequiredArgsConstructor
public enum Role {


    USER(
            Set.of(
                    USER_READ,
                    USER_UPDATE,
                    USER_CREATE,
                    USER_DELETE
            )
    ),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    USER_READ,
                    USER_UPDATE,
                    USER_CREATE,
                    USER_DELETE
            )
    ),
    METALIST(

            Set.of(
                    USER_READ,
                    USER_UPDATE, // Decide if they need update/create/delete
                    USER_CREATE,
                    USER_DELETE
                    // Add other specific permissions like VIEW_METAL_STOCK, UPDATE_METAL_PRODUCT etc.
            )
    ),
    WOODER(
            // TODO: IMPORTANT - Define the specific permissions for the WOODER role.
            // Assign the correct Permission enum values here.
            // Placeholder:
            Set.of(
                    USER_READ,
                    USER_UPDATE,
                    USER_CREATE,
                    USER_DELETE
                    // Add other specific permissions like VIEW_WOOD_STOCK, CREATE_WOOD_ORDER etc.
            )
    ),
    SEWER(
            // TODO: IMPORTANT - Define the specific permissions for the SEWER role.
            // Assign the correct Permission enum values here.
            // Placeholder:
            Set.of(
                    USER_READ,
                    USER_UPDATE,
                    USER_CREATE,
                    USER_DELETE
                    // Add other specific permissions like VIEW_FABRIC_STOCK, UPDATE_SEWING_TASK etc.
            )
    );

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}

