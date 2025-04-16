package com.coworking.management_user.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class  User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(name = "first_name",length = 50,nullable = false)
    private String firstName;
    @Column(name = "last_name",length = 50,nullable = false)
    private String lastName;
    @NotNull
    @Column(unique = true,length = 100,nullable = false)
    private String email;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date accountCreated;
    //para oauth
    @Column(name = "profile_image_url")
    private String profileImageUrl;
    private Boolean statusOauthEnabled;
}
