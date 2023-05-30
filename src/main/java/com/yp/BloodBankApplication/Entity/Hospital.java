package com.yp.BloodBankApplication.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int hospitalId;

    private String hospitalName;

    private String address;

    private long phoneNo;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"hospital","hibernateLazyInitializer"})
    private List<BloodRequest> bloodRequests = new ArrayList<>();
}
