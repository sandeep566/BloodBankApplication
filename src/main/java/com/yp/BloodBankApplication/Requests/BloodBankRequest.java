package com.yp.BloodBankApplication.Requests;

import com.yp.BloodBankApplication.Entity.BloodGroupDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * Represents a request object for transferring data related to a blood bank.
 */
@Data
@NoArgsConstructor
public class BloodBankRequest {


    /**
     * The ID of the blood bank.
     */
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
    private long phoneNo;

    /**
     * The email address of the blood bank.
     */
    private String mailAddress;

    /**
     * The password of the blood bank
     */
    private String password;

}
