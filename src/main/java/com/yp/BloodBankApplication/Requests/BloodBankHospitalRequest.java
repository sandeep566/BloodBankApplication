package com.yp.BloodBankApplication.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents a request object for transferring data related to a blood bank and hospital.
 */
@Data
@NoArgsConstructor
public class BloodBankHospitalRequest {

    /**
     * The ID of the blood bank or hospital.
     */
    private int id;

    /**
     * The name of the patient.
     */
    private String name;

    /**
     * The age associated with the request.
     */
    private int age;

    /**
     * The quantity associated with the request.
     */
    private int quantity;

}
