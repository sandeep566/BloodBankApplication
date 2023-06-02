package com.yp.BloodBankApplication.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BloodBankHospitalRequest {

    private int id;
    private String name;
    private int age;
//    private BloodGroup bloodGroup;
//    private Priority priority;
    private int quantity;

}
