package com.yp.BloodBankApplication.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;



/**
 * Represents a hospital entity.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hospital {


    /**
     * The unique identifier for the hospital.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hospitalId;


    /**
     * The name of the hospital.
     */
    private String hospitalName;



    /**
     * The address of the hospital.
     */
    private String address;


    @Column(unique=true)
    @Email(message = "Invalid email")
    @NotNull(message = "Email should not be null")
    private String email;


    /**
     * The phone number of the hospital.
     */
    @Column(unique=true)
    @NotNull(message = "Mobile number cannot be null")
    @Min(value = 1000000000L, message = "Mobile number should be of 10 digits")
    @Max(value = 9999999999L, message = "Mobile number should be of 10 digits")
    private long phoneNo;

    /**
     * The list of blood requests associated with the hospital.
     */
    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"hospital","hibernateLazyInitializer"})
    private List<BloodRequest> bloodRequests = new ArrayList<>();
}
