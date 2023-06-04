package ru.oorzhak.socialnetwork.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Role {
    private static final String ROLE_PREFIX = "ROLE_";
    private static final String ADMIN = "ADMIN";
    private static final String USER = "USER";
    @Transient
    public static final Role ADMIN_ROLE = new Role(1L, ROLE_PREFIX + ADMIN);
    @Transient
    public static final Role USER_ROLE = new Role(2L, ROLE_PREFIX + USER);

    @Id
    private Long id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id.equals(role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
