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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class DonorService {

    @Autowired
    private DonorRepository donorRepository;

    @Autowired
    private BloodBankRepository bloodBankRepository;

    public Donor registerDonor(DonorRequest donorRequest, int bloodBankId){
        BloodBank bloodBank = bloodBankRepository.findById(bloodBankId).orElse(null);
        if(bloodBank != null){
                bloodBank.getBloodGroups()
                        .put(donorRequest.getBloodGroup(),bloodBank.getBloodGroups()
                                .get(donorRequest.getBloodGroup()) + donorRequest.getDonationQuantity());

                Donor donor = new Donor(donorRequest.getDonorId(), donorRequest.getDonorName(),donorRequest.getAge(),
                        donorRequest.getAddress(), donorRequest.getPhoneNo(),
                        donorRequest.getBloodGroup(),donorRequest.getBloodGroupList(),bloodBank,
                        donorRequest.getDonationQuantity());

                return donorRepository.save(donor);
        }else{
            throw new BloodBankNotFoundException("Blood Bank Not Found");
        }

    }

    public Donor updateDonor(DonorRequest donorRequest){
        Optional<Donor> optionalDonor = donorRepository.findById(donorRequest.getDonorId());

        if(optionalDonor.isPresent()){
            int bloodBankId = donorRepository.findBloodBankIdByDonorId(donorRequest.getDonorId());
            BloodBank bloodBank = bloodBankRepository.findById(bloodBankId).orElse(null);
            Map<BloodGroup,Integer> bloodGroups = bloodBank.getBloodGroups();
            Donor donor = optionalDonor.get();
            donor.setDonorName(donorRequest.getDonorName());
            donor.setAddress(donorRequest.getAddress());
            donor.setAge(donorRequest.getAge());
            donor.setBloodGroup(donorRequest.getBloodGroup());
            donor.setBloodGroupsMatch(donorRequest.getBloodGroupList());
            int bloodBankQuantity = bloodGroups.get(donor.getBloodGroup()) - donor.getDonationQuantity();
            donor.setDonationQuantity(donorRequest.getDonationQuantity());
            bloodGroups.put(donorRequest.getBloodGroup(),bloodBankQuantity + donorRequest.getDonationQuantity());
            System.out.println(bloodBankQuantity);
            bloodBank.setBloodGroups(bloodGroups);
            bloodBankRepository.save(bloodBank);
            return donorRepository.save(donor);
        }
        else{
            throw new DonorNotFoundException("Donor not found");
        }
    }

    public Donor viewDonorById(int donorId){
        Optional<Donor> donor = donorRepository.findById(donorId);
        if(donor.isPresent()){
            return donor.get();
        }
        throw new DonorNotFoundException("Donor not found");
    }

    public List<Donor> getAllDonors(){
        return donorRepository.findAll();
    }

    public List<Donor> getAllDonorsByBloodGroup(BloodGroup bloodGroup){
        List<Donor> donors = donorRepository.findAll();
        return donors.stream().filter(donor -> donor.getBloodGroup().equals(bloodGroup)).collect(Collectors.toList());
    }
}
