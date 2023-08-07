package com.yp.BloodBankApplication.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yp.BloodBankApplication.Enums.BloodGroup;
import com.yp.BloodBankApplication.Enums.Priority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents a blood request made by a patient to a blood bank.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloodRequest {




    /**
     * The unique identifier for the blood request.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bloodRequestId;


    /**
     * The name of the patient making the blood request.
     */
    private String patientName;

    /**
     * The age of the patient making the blood request.
     */
    private int age;



    /**
     * The required blood group for the request.
     */
    private BloodGroup bloodGroup;



    /**
     * The priority of the blood request.
     */
    private Priority priority;


    /**
     * The hospital associated with the blood request.
     */
    @ManyToOne(targetEntity = Hospital.class)
    @JoinColumn(name = "hospital_request_id")
    @JsonIgnoreProperties(value = {"bloodRequests" , "hibernateLazyInitializer"})
    private Hospital hospital;


    /**
     * The quantity of blood requested.
     */
    private int quantity;

    /**
     * Indicates whether the blood request has been supplied or not.
     */
    private boolean isSupplied;


}
