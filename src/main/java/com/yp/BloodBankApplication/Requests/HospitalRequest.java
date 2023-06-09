package com.yp.BloodBankApplication.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HospitalRequest {

    private int hospitalId;
    private String hospitalName;
    private String address;
    private long phoneNo;
    private String email;
    private String password;
}
