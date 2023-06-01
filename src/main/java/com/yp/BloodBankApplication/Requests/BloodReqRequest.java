package com.yp.BloodBankApplication.Requests;

import com.yp.BloodBankApplication.Enums.BloodGroup;
import com.yp.BloodBankApplication.Enums.Priority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodReqRequest {

    private int id;
    private String name;
    private int age;
//    private BloodGroup bloodGroup;
//    private Priority priority;
    private int quantity;
}
