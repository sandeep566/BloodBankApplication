/**
 * The {@code User} entity class represents a user in the Blood Bank Application.
 */
package com.yp.BloodBankApplication.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The {@code User} entity class represents a user in the Blood Bank Application.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_info")
public class User {

    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    /**
     * The username of the user.
     */
    @Column(length = 30,unique = true)
    @NotBlank(message = "Username can not be blank")
    private String userName;


    /**
     * The password of the user.
     */
    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password should be at least 8 characters long")
    @Size(max = 60, message = "Password should be less than 60 characters")
    private String userPassword;

    /**
     * The role of the user.
     */
    @NotBlank(message = "Role can not be blank")
    private String role;

}