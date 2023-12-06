package com.rsl.risingkashmir.entiry;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "user_passwd")
    private String password;

    @Column(name = "user_email")
    private String email;

//    The @ElementCollection and @CollectionTable annotations specify that the roles field is a collection of values stored in a separate table, with a foreign key relationship to the users table.
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "user_role")
    private Set<String> roles;
}
