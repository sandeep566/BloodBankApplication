package com.yp.BloodBankApplication.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yp.BloodBankApplication.Enums.BloodGroup;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BloodBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bloodBankId;

    private String bloodBankName;

    private String address;

    private long phoneNumber;

    @OneToMany(mappedBy = "bloodBank", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"bloodBank","hibernateLazyInitializer"})
    private List<Donor> donorList ;

    private String mailAddress;

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "BLOOD_BANK_ID",nullable = false)
//    private List<BloodGroupDetails> bloodGroupDetailsList;


    @ElementCollection
    @CollectionTable(name = "blood_bank_blood_group_details",
            joinColumns = @JoinColumn(name = "blood_bank_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "blood_group_quantity")
    private Map<BloodGroup,Integer> bloodGroupIntegerMap;
}
