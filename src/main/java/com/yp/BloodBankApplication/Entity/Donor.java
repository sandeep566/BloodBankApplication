package com.yp.BloodBankApplication.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yp.BloodBankApplication.Enums.BloodGroup;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Donor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int donorId;

    private String donorName;

    private int age;

    private String address;

    private long phoneNo;

    private BloodGroup bloodGroup;

    private List<BloodGroup> bloodGroupsMatch;

    @ManyToOne(targetEntity = BloodBank.class)
    @JoinColumn(name = "Blood_Bank_id")
    @JsonIgnoreProperties(value = {"donorList" , "hibernateLazyInitializer"})
    private BloodBank bloodBank;

    private double donationQuantity;
}
