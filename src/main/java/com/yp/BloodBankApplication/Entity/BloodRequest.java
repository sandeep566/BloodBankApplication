package com.yp.BloodBankApplication.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yp.BloodBankApplication.Enums.BloodGroup;
import com.yp.BloodBankApplication.Enums.IsSupplied;
import com.yp.BloodBankApplication.Enums.Priority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloodRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int BloodRequestId;

    private String patientName;

    private int age;

    private BloodGroup bloodGroup;

    private Priority priority;

    @ManyToOne(targetEntity = Hospital.class)
    @JoinColumn(name = "hospital_request_id")
    @JsonIgnoreProperties(value = {"bloodRequests" , "hibernateLazyInitializer"})
    private Hospital hospital;

    private int quantity;

    private IsSupplied isSupplied;


}
