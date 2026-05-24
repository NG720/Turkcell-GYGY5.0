package com.library.cqrs.entity;

import com.library.cqrs.core.security.Role;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "KULLANICI")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "KULLANICI_ROLLER", joinColumns = @JoinColumn(name = "kullanici_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "rol")
    private List<Role> roles;
}
