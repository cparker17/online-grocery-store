package com.parker.usermicroservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles role;

    public Role(Roles role) {
        this.role = role;
    }

    @JsonIgnore
    public String getAuthority() {
        return role.name();
    }

    public enum Roles {
        ROLE_USER,
        ROLE_ADMIN
    }
}
