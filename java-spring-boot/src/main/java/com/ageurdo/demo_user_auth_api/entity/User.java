package com.ageurdo.demo_user_auth_api.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Remove;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String cpf;

    @Column(length = 200)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private Role role = Role.ROLE_USER;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime dateOfBirth;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String number;

    @Column(nullable = true)
    private String complement;

    @Column(nullable = false)
    private String neighborhood;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private RecordStatus status;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createdBy = "System";

    @LastModifiedDate
    @Column(nullable = true)
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(nullable = true)
    private String updatedBy;

    @Column(nullable = true)
    private LocalDateTime deletedAt;

    @Column(nullable = true)
    private String deletedBy;

    public enum RecordStatus {
        ACTIVE,
        REMOVED
    }

    public enum Role {
        ROLE_ADMIN,
        ROLE_USER
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                '}';
    }
}




