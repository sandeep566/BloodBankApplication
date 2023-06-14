package com.yp.BloodBankApplication.Entity;

import com.yp.BloodBankApplication.Enums.BloodGroup;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * Represents a supply report for blood donation from a blood bank to a hospital.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SupplyReport {

    /**
     * The unique identifier for the supply report.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int supplyId;

    /**
     * The blood group associated with the supply report.
     */
    private BloodGroup bloodGroup;


    /**
     * The hospital receiving the blood supply.
     */
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "hospitalId")
    private Hospital hospital;

    /**
     * The blood bank providing the blood supply.
     */
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bloodBankId")
    private BloodBank bloodBank;


    /**
     * The quantity of blood supplied.
     */
    private int quantity;

    /**
     * The date and time of the supply report.
     */
    private LocalDateTime dateTime;
}
