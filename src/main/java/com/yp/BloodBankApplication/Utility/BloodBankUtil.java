package com.yp.BloodBankApplication.Utility;

import com.yp.BloodBankApplication.Entity.BloodBank;
import com.yp.BloodBankApplication.Entity.BloodRequest;
import com.yp.BloodBankApplication.Entity.Donor;
import com.yp.BloodBankApplication.Entity.Hospital;
import com.yp.BloodBankApplication.Enums.BloodGroup;
import com.yp.BloodBankApplication.Requests.BloodBankHospitalRequest;
import com.yp.BloodBankApplication.Requests.BloodBankRequest;
import com.yp.BloodBankApplication.Requests.DonorRequest;
import com.yp.BloodBankApplication.Requests.HospitalRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BloodBankUtil {

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

    public static Donor mapToDonor(Donor donor, DonorRequest donorRequest){
        donor.setDonorName(donorRequest.getDonorName());
        donor.setAddress(donorRequest.getAddress());
        donor.setAge(donorRequest.getAge());
        donor.setDonationQuantity(donorRequest.getDonationQuantity());
        return donor;
    }

    public static Hospital mapToHospital(Hospital hospital, HospitalRequest hospitalRequest){
        hospital.setHospitalName(hospitalRequest.getHospitalName());
        hospital.setAddress(hospitalRequest.getAddress());
        hospital.setPhoneNo(hospitalRequest.getPhoneNo());
        return hospital;
    }


    /**
     * Used to map bloodBankRequest to Bloodbank
     */

    public static BloodBank mapToBloodBank(BloodBank bloodBank, BloodBankRequest bloodBankRequest){
        bloodBank.setBloodBankName(bloodBankRequest.getBloodBankName());
        bloodBank.setAddress(bloodBankRequest.getAddress());
        bloodBank.setPhoneNumber(bloodBankRequest.getPhoneNo());
        bloodBank.setMailAddress(bloodBankRequest.getMailAddress());
        return bloodBank;
    }

    /**
     *
     * Used to map bloodBankHospitalRequest to bloodRequest
     * @param bloodRequest
     * @param bloodBankHospitalRequest
     * @return BloodRequest
     */
    public static BloodRequest mapToBloodRequest(BloodRequest bloodRequest, BloodBankHospitalRequest bloodBankHospitalRequest){
        bloodRequest.setPatientName(bloodBankHospitalRequest.getName());
        bloodRequest.setAge(bloodBankHospitalRequest.getAge());
        bloodRequest.setQuantity(bloodBankHospitalRequest.getQuantity());
        return bloodRequest;
    }

}
