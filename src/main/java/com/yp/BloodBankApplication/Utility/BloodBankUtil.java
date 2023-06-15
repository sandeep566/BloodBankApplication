package com.yp.BloodBankApplication.Utility;

import com.yp.BloodBankApplication.Entity.*;
import com.yp.BloodBankApplication.Enums.BloodGroup;
import com.yp.BloodBankApplication.Requests.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The {@code BloodBankUtil} class provides utility methods for blood bank operations.
 */
public class BloodBankUtil {

    /**
     * Retrieves a list of suitable blood groups based on the given blood group.
     *
     * @param bloodGroup the blood group for which to retrieve suitable blood groups
     * @return a list of suitable blood groups
     */
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


    /**
     * Maps the properties of a {@link DonorRequest} to a {@link Donor} entity.
     *
     * @param donor         the donor entity to be updated
     * @param donorRequest  the donor request containing the updated properties
     * @return the updated donor entity
     */
    public static Donor mapToDonor(Donor donor, DonorRequest donorRequest){
        donor.setDonorName(donorRequest.getDonorName());
        donor.setAddress(donorRequest.getAddress());
        donor.setAge(donorRequest.getAge());
        donor.setAadhaarNo(donorRequest.getAadharNo());
        donor.setDonationQuantity(donorRequest.getDonationQuantity());
        return donor;
    }

    /**
     * Maps the properties of a {@link HospitalRequest} to a {@link Hospital} entity.
     *
     * @param hospital         the hospital entity to be updated
     * @param hospitalRequest  the hospital request containing the updated properties
     * @return the updated hospital entity
     */
    public static Hospital mapToHospital(Hospital hospital, HospitalRequest hospitalRequest){
        hospital.setHospitalName(hospitalRequest.getHospitalName());
        hospital.setAddress(hospitalRequest.getAddress());
        hospital.setPhoneNo(hospitalRequest.getPhoneNo());
        return hospital;
    }




    /**
     * Maps the properties of a {@link BloodBankRequest} to a {@link BloodBank} entity.
     *
     * @param bloodBank         the blood bank entity to be updated
     * @param bloodBankRequest  the blood bank request containing the updated properties
     * @return the updated blood bank entity
     */
    public static BloodBank mapToBloodBank(BloodBank bloodBank, BloodBankRequest bloodBankRequest){
        bloodBank.setBloodBankName(bloodBankRequest.getBloodBankName());
        bloodBank.setAddress(bloodBankRequest.getAddress());
        bloodBank.setPhoneNumber(bloodBankRequest.getPhoneNo());
        bloodBank.setMailAddress(bloodBankRequest.getMailAddress());
        return bloodBank;
    }



    /**
     * Maps the properties from a {@link BloodBankUpdateRequest} to update a {@link BloodBank} entity.
     *
     * @param bloodBank              the blood bank entity to be updated
     * @param bloodBankUpdateRequest the request containing the updated properties
     * @return the updated blood bank entity
     */
    public static BloodBank mapToBloodBankUpdate(BloodBank bloodBank,BloodBankUpdateRequest bloodBankUpdateRequest){
        bloodBank.setBloodBankName(bloodBankUpdateRequest.getBloodBankName());
        bloodBank.setAddress(bloodBankUpdateRequest.getAddress());
        bloodBank.setPhoneNumber(bloodBankUpdateRequest.getPhoneNo());
        return bloodBank;
    }


    /**
     * Maps the properties from a {@link HospitalUpdateRequest} to update a {@link Hospital} entity.
     *
     * @param hospital               the hospital entity to be updated
     * @param hospitalUpdateRequest  the request containing the updated properties
     * @return the updated hospital entity
     */

    public static Hospital mapToHospitalUpdate(Hospital hospital,HospitalUpdateRequest hospitalUpdateRequest){
        hospital.setHospitalName(hospitalUpdateRequest.getHospitalName());
        hospital.setAddress(hospitalUpdateRequest.getAddress());
        hospital.setPhoneNo(hospitalUpdateRequest.getPhoneNo());
        return hospital;
    }
    /**
     *
     * Used to map bloodBankHospitalRequest to bloodRequest
     * @param bloodBankHospitalRequest The request body with id,name,age,quantity
     * @param bloodRequest     The requestbody of blood request
     * @return BloodRequest
     */
    public static BloodRequest mapToBloodRequest(BloodRequest bloodRequest, BloodBankHospitalRequest bloodBankHospitalRequest){
        bloodRequest.setPatientName(bloodBankHospitalRequest.getName());
        bloodRequest.setAge(bloodBankHospitalRequest.getAge());
        bloodRequest.setQuantity(bloodBankHospitalRequest.getQuantity());
        return bloodRequest;
    }



    /**
     * Maps the properties of a {@link BloodBankRequest} to a {@link User} entity for a blood bank.
     *
     * @param bloodBankRequest   the blood bank request containing the user properties
     * @param passwordEncoder    the password encoder for encoding the user's password
     * @return the user entity for the blood bank
     */
    public static User mapBloodBankToUser(BloodBankRequest bloodBankRequest, PasswordEncoder passwordEncoder){
        return new User(bloodBankRequest.getBloodBankId(), bloodBankRequest.getMailAddress(), passwordEncoder.encode(bloodBankRequest.getPassword()), "ROLE_ADMIN");
    }


    /**
     * Maps the properties of a {@link HospitalRequest} to a {@link User} entity for a hospital.
     *
     * @param hospitalRequest    the hospital request containing the user properties
     * @param passwordEncoder    the password encoder for encoding the user's password
     * @return the user entity for the hospital
     */
    public static User mapHospitalToUser(HospitalRequest hospitalRequest,PasswordEncoder passwordEncoder){
        return new User(hospitalRequest.getHospitalId(), hospitalRequest.getEmail(), passwordEncoder.encode(hospitalRequest.getPassword()), "ROLE_USER");
    }

}
