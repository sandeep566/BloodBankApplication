package com.yp.BloodBankApplication.Services;

import com.yp.BloodBankApplication.Entity.BloodBank;
import com.yp.BloodBankApplication.Entity.BloodRequest;
import com.yp.BloodBankApplication.Entity.Donor;
import com.yp.BloodBankApplication.Enums.BloodGroup;
import com.yp.BloodBankApplication.Exception.AadharAlreadyExistException;
import com.yp.BloodBankApplication.Exception.BloodBankNotFoundException;
import com.yp.BloodBankApplication.Exception.DonorNotFoundException;
import com.yp.BloodBankApplication.Repository.BloodBankRepository;
import com.yp.BloodBankApplication.Repository.DonorRepository;
import com.yp.BloodBankApplication.Requests.DonorRequest;
import com.yp.BloodBankApplication.Utility.BloodBankUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

import static com.yp.BloodBankApplication.Utility.BloodBankUtil.mapToDonor;

/**
 * This class provides services related to donors.
 *
 * Collections used
 * java.util.List: Used in several places to store lists of donors and blood groups.
 * java.util.Map: Used in the BloodBank entity to store blood groups and their quantities.
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
        Optional<BloodBank> optionalBloodBank = bloodBankRepository.findById(bloodBankId);
        if(optionalBloodBank.isPresent()){
            if(! isAadharPresent(donorRequest.getAadharNo())){
                BloodBank bloodBank = optionalBloodBank.get();
                bloodBank.getBloodGroups().put(bloodGroup,bloodBank.getBloodGroups().get(bloodGroup) + donorRequest.getDonationQuantity());
                List<BloodGroup> suitableBloodGroups = BloodBankUtil.getSuitableBloodGroups(bloodGroup);
                Donor donor = new Donor(donorRequest.getDonorId(), donorRequest.getDonorName(),donorRequest.getAge(),donorRequest.getAadharNo() ,donorRequest.getAddress(), donorRequest.getPhoneNo(), bloodGroup,suitableBloodGroups,bloodBank, donorRequest.getDonationQuantity());
                return donorRepository.save(donor);
            }
            throw new AadharAlreadyExistException("AadharNo already present");
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
            assert bloodBank != null;
            Map<BloodGroup,Integer> bloodGroups = bloodBank.getBloodGroups();
            Donor donor = mapToDonor( optionalDonor.get() , donorRequest);
            List<BloodGroup> suitableBloodGroups = BloodBankUtil.getSuitableBloodGroups(bloodGroup);
            donor.setBloodGroupsMatch(suitableBloodGroups);
            int bloodBankQuantity = bloodGroups.getOrDefault(donor.getBloodGroup(), 0) - donor.getDonationQuantity();
            if(bloodBankQuantity > 0){
                bloodGroups.put(donor.getBloodGroup(),bloodBankQuantity);
            }
            else{
                bloodGroups.put(donor.getBloodGroup(),0);
            }
            donor.setBloodGroup(bloodGroup);
            bloodGroups.put(bloodGroup,bloodGroups.get(bloodGroup) + donorRequest.getDonationQuantity());
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


    /**
     * Deletes a blood donor with the specified donor ID.
     *
     * @param donorId The ID of the donor to be deleted.
     * @return A success message if the donor is deleted successfully.
     * @throws DonorNotFoundException If the donor with the specified ID is not found.
     */
    public String deleteDonor(int donorId){
        Optional<Donor> donor = donorRepository.findById(donorId);
        if(donor.isPresent()){
            donorRepository.deleteById(donorId);
            return "Donor Deleted";
        }
        throw new DonorNotFoundException("Donor not found");
    }


    /**
     * Retrieves a list of blood donors with the specified age.
     *
     * @param age The age to filter the donors by.
     * @return A list of donors with the specified age.
     */
    public List<Donor> viewDonorsByAge(int age){
        List<Donor> donors = donorRepository.findAll();
        return donors.stream().filter(donor -> donor.getAge() == age).toList();
    }


    /**
     * Retrieves a list of blood donors with suitable blood groups.
     *
     * @param bloodGroups The list of blood groups to filter the donors by.
     * @return A list of donors with blood groups matching all the specified blood groups.
     */
    public List<Donor> viewDonorsBySuitableBloodGroup(List<BloodGroup> bloodGroups){
        List<Donor> donors = donorRepository.findAll();
        return donors.stream().filter(donor -> new HashSet<>(donor.getBloodGroupsMatch()).containsAll(bloodGroups)).toList();
    }

    public boolean isAadharPresent(BigInteger aadharNo){
        Optional<Donor> donor = donorRepository.findByAadhaarNo(aadharNo);
        return donor.isPresent();
    }


}
