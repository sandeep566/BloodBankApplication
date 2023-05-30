package com.yp.BloodBankApplication.Entity;

import com.yp.BloodBankApplication.Enums.BloodGroup;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BloodGroupDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bloodGroupId;

    private BloodGroup bloodGroup;

    private double quantity;
}
