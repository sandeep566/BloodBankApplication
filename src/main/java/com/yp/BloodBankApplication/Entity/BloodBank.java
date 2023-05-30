package com.yp.BloodBankApplication.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BloodBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bloodBankId;

    private String bloodBankName;

    private String address;

    private long phoneNumber;

    @OneToMany(mappedBy = "bloodBank", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"bloodBank","hibernateLazyInitializer"})
    private List<Donor> donorList ;

    private String mailAddress;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "BLOOD_BANK_ID",nullable = false)
    private List<BloodGroupDetails> bloodGroupDetailsList;
}
