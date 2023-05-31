package com.yp.BloodBankApplication.Requests;

import com.yp.BloodBankApplication.Enums.BloodGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonorRequest {

    private int donorId;

    private String donorName;

    private int age;

    private String address;

    private long phoneNo;

//    private BloodGroup bloodGroup;

//    private List<BloodGroup> bloodGroupList;

    private int donationQuantity;
}
