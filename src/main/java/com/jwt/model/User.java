package com.jwt.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
   @Id
    String userName;
    String password;
    String email;
    String role;

    @OneToOne(mappedBy = "user")
    @JsonManagedReference
    private RefreshToken refreshToken;
}

