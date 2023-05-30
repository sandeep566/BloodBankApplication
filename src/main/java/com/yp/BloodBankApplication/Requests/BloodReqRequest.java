package com.yp.BloodBankApplication.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodReqRequest {

    private String name;
    private int age;
    private String hospitalName;
}
