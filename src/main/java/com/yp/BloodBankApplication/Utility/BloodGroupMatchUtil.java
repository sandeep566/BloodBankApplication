package com.yp.BloodBankApplication.Utility;

import com.yp.BloodBankApplication.Enums.BloodGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BloodGroupMatchUtil {

    public static List<BloodGroup> getSuitableBloodGroups(BloodGroup bloodGroup){
        List<BloodGroup> suitableBloodGroups = new ArrayList<>();

        switch (bloodGroup){
            case A_POSITIVE -> Collections.addAll(suitableBloodGroups,BloodGroup.A_POSITIVE,BloodGroup.AB_POSITIVE);
            case B_POSITIVE -> Collections.addAll(suitableBloodGroups,BloodGroup.B_POSITIVE,BloodGroup.AB_POSITIVE);
            case O_POSITIVE -> Collections.addAll(suitableBloodGroups,BloodGroup.O_POSITIVE,BloodGroup.A_POSITIVE,BloodGroup.B_POSITIVE,BloodGroup.AB_POSITIVE);
            case AB_POSITIVE -> Collections.addAll(suitableBloodGroups,BloodGroup.AB_POSITIVE);
            case A_NEGATIVE -> Collections.addAll(suitableBloodGroups,BloodGroup.A_POSITIVE,BloodGroup.A_NEGATIVE,BloodGroup.AB_POSITIVE,BloodGroup.AB_NEGATIVE);
            case O_NEGATIVE -> Collections.addAll(suitableBloodGroups,BloodGroup.values());
            case B_NEGATIVE -> Collections.addAll(suitableBloodGroups,BloodGroup.B_POSITIVE,BloodGroup.B_NEGATIVE,BloodGroup.AB_POSITIVE,BloodGroup.AB_NEGATIVE);
            case AB_NEGATIVE -> Collections.addAll(suitableBloodGroups,BloodGroup.AB_POSITIVE,BloodGroup.AB_NEGATIVE);
            default -> System.exit(0);
        }
        return suitableBloodGroups;
    }
}
