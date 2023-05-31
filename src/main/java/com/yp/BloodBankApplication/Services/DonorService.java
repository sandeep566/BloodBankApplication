package com.yp.BloodBankApplication.Services;

import com.yp.BloodBankApplication.Entity.BloodBank;
import com.yp.BloodBankApplication.Entity.Donor;
import com.yp.BloodBankApplication.Enums.BloodGroup;
import com.yp.BloodBankApplication.Exception.BloodBankNotFoundException;
import com.yp.BloodBankApplication.Exception.DonorNotFoundException;
import com.yp.BloodBankApplication.Repository.BloodBankRepository;
import com.yp.BloodBankApplication.Repository.DonorRepository;
import com.yp.BloodBankApplication.Requests.DonorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class provides services related to donors.
 */
@Service
public class DonorService {

    @Autowired
    private DonorRepository donorRepository;

    @Autowired
    private BloodBankRepository bloodBankRepository;

    /**
     * Registers a new donor and updates the blood bank's blood group quantities accordingly.
     *
     * @param donorRequest  The donor request containing the donor information.
     * @param bloodBankId   The ID of the blood bank where the donor is registered.
     * @param bloodGroup    The blood group of the donor.
     * @return The registered donor.
     * @throws BloodBankNotFoundException if the blood bank with the given ID is not found.
     */

    public Donor registerDonor(DonorRequest donorRequest, int bloodBankId, BloodGroup bloodGroup){
        BloodBank bloodBank = bloodBankRepository.findById(bloodBankId).orElse(null);
        if(bloodBank != null){
                bloodBank.getBloodGroups()
                        .put(bloodGroup,bloodBank.getBloodGroups()
                                .get(bloodGroup) + donorRequest.getDonationQuantity());

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

                Donor donor = new Donor(donorRequest.getDonorId(), donorRequest.getDonorName(),donorRequest.getAge(),
                        donorRequest.getAddress(), donorRequest.getPhoneNo(),
                        bloodGroup,suitableBloodGroups,bloodBank,
                        donorRequest.getDonationQuantity());

                return donorRepository.save(donor);
        }else{
            throw new BloodBankNotFoundException("Blood Bank Not Found");
        }

    }

    /**
     * Updates the information and blood group of an existing donor and adjusts the blood bank's blood group quantities accordingly.
     *
     * @param donorRequest  The updated donor information.
     * @param bloodGroup    The new blood group of the donor.
     * @return The updated donor.
     * @throws DonorNotFoundException    if the donor with the given ID is not found.
     */

    public Donor updateDonor(DonorRequest donorRequest,BloodGroup bloodGroup){
        Optional<Donor> optionalDonor = donorRepository.findById(donorRequest.getDonorId());

        if(optionalDonor.isPresent()){
            int bloodBankId = donorRepository.findBloodBankIdByDonorId(donorRequest.getDonorId());
            BloodBank bloodBank = bloodBankRepository.findById(bloodBankId).orElse(null);
            Map<BloodGroup,Integer> bloodGroups = bloodBank.getBloodGroups();
            Donor donor = optionalDonor.get();
            donor.setDonorName(donorRequest.getDonorName());
            donor.setAddress(donorRequest.getAddress());
            donor.setAge(donorRequest.getAge());
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
            donor.setBloodGroupsMatch(suitableBloodGroups);
            int bloodBankQuantity = bloodGroups.get(donor.getBloodGroup()) - donor.getDonationQuantity();
            bloodGroups.put(donor.getBloodGroup(),bloodBankQuantity);
            donor.setDonationQuantity(donorRequest.getDonationQuantity());
            donor.setBloodGroup(bloodGroup);
            bloodGroups.put(bloodGroup,bloodGroups.get(bloodGroup) + donorRequest.getDonationQuantity());
            System.out.println(bloodBankQuantity);
            bloodBank.setBloodGroups(bloodGroups);
            bloodBankRepository.save(bloodBank);
            return donorRepository.save(donor);
        }
        else{
            throw new DonorNotFoundException("Donor not found");
        }
    }


    /**
     * Retrieves a donor by their ID.
     *
     * @param donorId   The ID of the donor to retrieve.
     * @return The donor with the specified ID.
     * @throws DonorNotFoundException    if the donor with the given ID is not found.
     */
    public Donor viewDonorById(int donorId){
        Optional<Donor> donor = donorRepository.findById(donorId);
        if(donor.isPresent()){
            return donor.get();
        }
        throw new DonorNotFoundException("Donor not found");
    }


    /**
     * Retrieves a list of all donors.
     *
     * @return A list of all donors.
     */

    public List<Donor> getAllDonors(){
        return donorRepository.findAll();
    }


    /**
     * Retrieves a list of all donors with the specified blood group.
     *
     * @param bloodGroup    The blood group to filter the donors by.
     * @return A list of donors with the specified blood group.
     */
    public List<Donor> getAllDonorsByBloodGroup(BloodGroup bloodGroup){
        List<Donor> donors = donorRepository.findAll();
        return donors.stream().filter(donor -> donor.getBloodGroup().equals(bloodGroup)).collect(Collectors.toList());
    }
}
