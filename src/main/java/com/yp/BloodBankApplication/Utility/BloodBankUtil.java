package com.yp.BloodBankApplication.Utility;

import com.yp.BloodBankApplication.Entity.*;
import com.yp.BloodBankApplication.Enums.BloodGroup;
import com.yp.BloodBankApplication.Requests.*;
import org.springframework.security.crypto.password.PasswordEncoder;

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
        }
        return suitableBloodGroups;
    }

    public static Donor mapToDonor(Donor donor, DonorRequest donorRequest){
        donor.setDonorName(donorRequest.getDonorName());
        donor.setAddress(donorRequest.getAddress());
        donor.setAge(donorRequest.getAge());
        donor.setAadhaarNo(donorRequest.getAadharNo());
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
        bloodBank.setPassword(bloodBankRequest.getPassword());
        return bloodBank;
    }


    public static BloodBank mapToBloodBankUpdate(BloodBank bloodBank,BloodBankUpdateRequest bloodBankUpdateRequest){
        bloodBank.setBloodBankName(bloodBankUpdateRequest.getBloodBankName());
        bloodBank.setAddress(bloodBankUpdateRequest.getAddress());
        bloodBank.setPhoneNumber(bloodBankUpdateRequest.getPhoneNo());
        return bloodBank;
    }

    public static Hospital mapToHospitalUpdate(Hospital hospital,HospitalUpdateRequest hospitalUpdateRequest){
        hospital.setHospitalName(hospitalUpdateRequest.getHospitalName());
        hospital.setAddress(hospitalUpdateRequest.getAddress());
        hospital.setPhoneNo(hospitalUpdateRequest.getPhoneNo());
        return hospital;
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

    public static User mapBloodBankToUser(BloodBankRequest bloodBankRequest, PasswordEncoder passwordEncoder){
        User user = new User(bloodBankRequest.getBloodBankId(), bloodBankRequest.getMailAddress(), passwordEncoder.encode(bloodBankRequest.getPassword()), "ROLE_ADMIN");
        return user;
    }

    public static User mapHospitalToUser(HospitalRequest hospitalRequest,PasswordEncoder passwordEncoder){
        User user = new User(hospitalRequest.getHospitalId(), hospitalRequest.getEmail(), passwordEncoder.encode(hospitalRequest.getPassword()), "ROLE_USER");
        return user;
    }

}
