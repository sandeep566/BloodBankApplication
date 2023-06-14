package com.yp.BloodBankApplication.Entity;

import com.yp.BloodBankApplication.Enums.BloodGroup;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents the details of a specific blood group in a blood bank.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BloodGroupDetails {

    /**
     * The unique identifier for the blood group details.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bloodGroupId;


    /**
     * The blood group type.
     */
    private BloodGroup bloodGroup;


    /**
     * The quantity of blood available for the blood group.
     */
    private double quantity;
}
