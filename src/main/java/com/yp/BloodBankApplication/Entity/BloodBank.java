package com.yp.BloodBankApplication.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yp.BloodBankApplication.Enums.BloodGroup;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
import java.util.Map;

/**
 * Represents a Blood Bank entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BloodBank {

    /**
     * The unique identifier for the blood bank.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bloodBankId;

    /**
     * The name of the blood bank.
     */
    private String bloodBankName;


    /**
     * The address of the blood bank.
     */
    private String address;

    /**
     * The phone number of the blood bank.
     */
    private long phoneNumber;


    /**
     * The list of donors associated with the blood bank.
     */
    @OneToMany(mappedBy = "bloodBank", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"bloodBank","hibernateLazyInitializer"})
    private List<Donor> donorList ;



    /**
     * The email address of the blood bank.
     */
    private String mailAddress;


    /**
     * The password of the blood bank
     */
    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password should be at least 8 characters long")
    @Size(max = 60, message = "Password should be less than 60 characters")
    private String password;



    /**
     * The map containing blood group quantities available in the blood bank.
     */
    @ElementCollection
    @CollectionTable(name = "blood_bank_blood_group_details",
            joinColumns = @JoinColumn(name = "blood_bank_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "blood_group_quantity")
    private Map<BloodGroup,Integer> bloodGroups;
}
