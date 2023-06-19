package com.yp.BloodBankApplication.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yp.BloodBankApplication.Enums.BloodGroup;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigInteger;
import java.util.List;

/**
 * Represents a blood donor.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Donor {

    /**
     * The unique identifier for the donor.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int donorId;


    /**
     * The name of the donor.
     */
    private String donorName;


    /**
     * The age of the donor.
     */
    private int age;


    /**
     * The aadhar no of donor
     */
    @Column(unique=true)
    private BigInteger aadhaarNo;


    /**
     * The address of the donor.
     */
    private String address;

    /**
     * The phone number of the donor.
     */
    private long phoneNo;

    /**
     * The blood group of the donor.
     */
    private BloodGroup bloodGroup;

    /**
     * The list of blood groups that the donor can match.
     */
    private List<BloodGroup> bloodGroupsMatch;

    /**
     * The blood bank associated with the donor.
     */
    @ManyToOne(targetEntity = BloodBank.class)
    @JoinColumn(name = "Blood_Bank_id")
    @JsonIgnoreProperties(value = {"donorList" , "hibernateLazyInitializer"})
    private BloodBank bloodBank;


    /**
     * The quantity of blood donated by the donor.
     */
    private int donationQuantity;
}
