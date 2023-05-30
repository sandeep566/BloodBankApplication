package com.yp.BloodBankApplication.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int BloodRequestId;

    private String patientName;

    private int age;

    @ManyToOne(targetEntity = Hospital.class)
    @JoinColumn(name = "hospital_request_id")
    @JsonIgnoreProperties(value = {"bloodRequests" , "hibernateLazyInitializer"})
    private Hospital hospital;


}
