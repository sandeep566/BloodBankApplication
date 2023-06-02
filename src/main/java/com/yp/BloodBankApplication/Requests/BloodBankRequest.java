package com.yp.BloodBankApplication.Requests;

import com.yp.BloodBankApplication.Entity.BloodGroupDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BloodBankRequest {

    private int bloodBankId;

    private String bloodBankName;

    private String address;

    private long phoneNo;

    private String mailAddress;

}
