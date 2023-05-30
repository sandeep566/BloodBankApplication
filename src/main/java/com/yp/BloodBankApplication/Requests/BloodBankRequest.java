package com.yp.BloodBankApplication.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodBankRequest {

    private int bloodBankId;

    private String bloodBankName;

    private String address;

    private long phoneNo;

    private String mailAddress;
}
