package com.yp.BloodBankApplication.Services;

import com.yp.BloodBankApplication.Entity.BloodBank;
import com.yp.BloodBankApplication.Entity.Donor;
import com.yp.BloodBankApplication.Enums.BloodGroup;
import com.yp.BloodBankApplication.Exception.BloodBankAlreadyPresentException;
import com.yp.BloodBankApplication.Exception.BloodBankNotFoundException;
import com.yp.BloodBankApplication.Repository.BloodBankRepository;
import com.yp.BloodBankApplication.Repository.UserRepository;
import com.yp.BloodBankApplication.Requests.BloodBankRequest;
import com.yp.BloodBankApplication.Requests.BloodBankUpdateRequest;
import com.yp.BloodBankApplication.Utility.BloodBankUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;



/**
 * Service class for managing blood banks and their operations.
 */
@Service
public class BloodBankService {

    /**
     * Collections used
     *
     * List<BloodGroup> bloodGroups: Holds the values of the BloodGroup enum.
     *
     * Map<BloodGroup, Integer> bloodGroupIntegerMap: Stores blood groups and their quantities in the blood bank.
     *
     * List<Donor> donorList: Stores the donors associated with a specific blood bank.
     */

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BloodBankRepository bloodBankRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;





    /**
     * Registers a new blood bank.
     *
     * @param bloodBankRequest The details of the blood bank to be registered.
     * @return The registered blood bank.
     */
    public BloodBank registerBloodBank(BloodBankRequest bloodBankRequest){
        List<BloodGroup> bloodGroups = Stream.of(BloodGroup.values()).toList();
        Map<BloodGroup,Integer> bloodGroupIntegerMap = new HashMap<>();
        for(BloodGroup bg : bloodGroups){
            bloodGroupIntegerMap.put(bg,0);
        }
        if( ! isUsernamePresent(bloodBankRequest.getMailAddress())){
            if( ! isPhoneNumberPresent(bloodBankRequest.getPhoneNo())){
                BloodBank bloodBank = new BloodBank(bloodBankRequest.getBloodBankId(), bloodBankRequest.getBloodBankName(), bloodBankRequest.getAddress(), bloodBankRequest.getPhoneNo(),new ArrayList<>(),bloodBankRequest.getMailAddress(),bloodGroupIntegerMap);
                userRepository.save(BloodBankUtil.mapBloodBankToUser(bloodBankRequest,passwordEncoder));
                return bloodBankRepository.save(bloodBank);
            }
            throw new BloodBankAlreadyPresentException("PhoneNumber already present");
        }
        throw new BloodBankAlreadyPresentException("Username already present");

    }

    /**
     * Updates an existing blood bank.
     *
     * @param bloodBankUpdateRequest The updated details of the blood bank.
     * @return The updated blood bank.
     * @throws BloodBankNotFoundException If the blood bank is not found.
     */


    public BloodBank updateBloodBank(BloodBankUpdateRequest bloodBankUpdateRequest){
        BloodBank bloodBank = bloodBankRepository.findById(bloodBankUpdateRequest.getBloodBankId()).orElse(null);
        if(bloodBank != null){
            return bloodBankRepository.save(BloodBankUtil.mapToBloodBankUpdate(bloodBank,bloodBankUpdateRequest));
        }
        throw new BloodBankNotFoundException("Blood bank not found");
    }

    /**
     * Retrieves a blood bank by its ID.
     *
     * @param id The ID of the blood bank to retrieve.
     * @return The blood bank with the specified ID.
     * @throws BloodBankNotFoundException If the blood bank is not found.
     */

    public BloodBank viewBloodBank(int id){
        Optional<BloodBank> bank = bloodBankRepository.findById(id);
        if(bank.isPresent()){
            return bank.get();
        }
        throw new BloodBankNotFoundException("Blood Bank Not Found");
    }

    /**
     * Retrieves all blood banks.
     *
     * @return A list of all blood banks.
     */

    public List<BloodBank> viewAllBloodBanks(){
        return bloodBankRepository.findAll();
    }


    /**
     * Deletes a blood bank along with user by its ID .
     *
     * @param id The ID of the blood bank to delete.
     * @return A message indicating the deletion status.
     * @throws BloodBankNotFoundException If the blood bank is not found.
     */
    public String deleteBloodBank(int id){
        Optional<BloodBank> bloodBank = bloodBankRepository.findById(id);
        if(bloodBank.isPresent()){
            bloodBankRepository.deleteById(id);
            userRepository.deleteById(id);
            return "Blood Bank deleted";
        }
        throw new BloodBankNotFoundException("Blood Bank Not Found");
    }

    /**
     * Retrieves all donors associated with a specific blood bank.
     *
     * @param bloodBankId The ID of the blood bank.
     * @return A list of all donors associated with the blood bank.
     * @throws BloodBankNotFoundException If the blood bank is not found.
     */

    public List<Donor> getAllDonors(int bloodBankId){
        Optional<BloodBank> bloodBank = bloodBankRepository.findById(bloodBankId);
        if(bloodBank.isPresent()){
            return bloodBank.get().getDonorList();
        }
        throw new BloodBankNotFoundException("Blood Bank Not Found");
    }

    /**
     * Retrieves the blood groups and their quantities available in a blood bank.
     *
     * @param bloodBankId The ID of the blood bank.
     * @return A map containing the blood groups and their corresponding quantities.
     * @throws BloodBankNotFoundException If the blood bank is not found.
     */


    public Map<BloodGroup,Integer> viewBloodGroupAndQuantity(int bloodBankId){
        Optional<BloodBank> bloodBank = bloodBankRepository.findById(bloodBankId);
        if(bloodBank.isPresent()){
            return bloodBank.get().getBloodGroups();
        }
        throw new BloodBankNotFoundException("Blood Bank Not Found");
    }


    /**
     * Retrieves the count of donors for a specific blood bank.
     *
     * @param bloodBankId the ID of the blood bank to retrieve the count of donors for
     * @return the count of donors for the specified blood bank
     * @throws BloodBankNotFoundException if the blood bank with the specified ID is not found
     */
    public int getCountOfDonors(int bloodBankId) {
        Optional<BloodBank> bloodBank = bloodBankRepository.findById(bloodBankId);
        if(bloodBank.isPresent()){
            return bloodBank.get().getDonorList().size();
        }
        throw new BloodBankNotFoundException("Blood Bank Not Found");
    }


    /**
     * Retrieves the count of blood collected for a specific blood bank.
     *
     * @param bloodBankId the ID of the blood bank to retrieve the count of blood collected for
     * @return the count of blood collected for the specified blood bank
     * @throws BloodBankNotFoundException if the blood bank with the specified ID is not found
     */

    public int getCountOfBloodCollected(int bloodBankId){
        Optional<BloodBank> bloodBank = bloodBankRepository.findById(bloodBankId);
        if(bloodBank.isPresent()){
            Collection<Integer> values = bloodBank.get().getBloodGroups().values();
            return values.stream().reduce(0, Integer::sum);
        }
        throw new BloodBankNotFoundException("Blood Bank not found");
    }



    /**
     * Checks if a username exists in the user repository.
     *
     * @param username the username to check
     * @return true if the username exists, false otherwise
     */
    public boolean isUsernamePresent(String username){
        return userRepository.findByUserName(username).isPresent();
    }


    /**
     * Checks if a phone number exists in the blood bank repository.
     *
     * @param phoneNumber the phone number to check
     * @return true if the phone number exists, false otherwise
     */
    private boolean isPhoneNumberPresent(long phoneNumber){
        return bloodBankRepository.findByPhoneNumber(phoneNumber).isPresent();
    }


}
