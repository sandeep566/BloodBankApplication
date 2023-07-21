package com.yp.BloodBankApplication.Requests;

import com.yp.BloodBankApplication.Enums.BloodGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
@NoArgsConstructor
public class DonorRequest {

    /**
     * The ID of the donor.
     */
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
     * The aadhaar number of donor
     */
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
     * The quantity of blood donated by the donor.
     */
    private int donationQuantity;
}
