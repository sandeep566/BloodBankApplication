package com.yp.BloodBankApplication.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospitalUpdateRequest {

    private int hospitalId;

    private String hospitalName;

    private String address;

    private long phoneNo;
}
