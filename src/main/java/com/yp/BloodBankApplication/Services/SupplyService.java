package com.yp.BloodBankApplication.Services;

import com.yp.BloodBankApplication.Entity.BloodBank;
import com.yp.BloodBankApplication.Entity.BloodRequest;
import com.yp.BloodBankApplication.Entity.SupplyReport;
import com.yp.BloodBankApplication.Enums.BloodGroup;
import com.yp.BloodBankApplication.Exception.*;
import com.yp.BloodBankApplication.Repository.BloodBankRepository;
import com.yp.BloodBankApplication.Repository.BloodRequestRepository;
import com.yp.BloodBankApplication.Repository.HospitalRepository;
import com.yp.BloodBankApplication.Repository.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * Service class that handles operations related to supply reports in the Blood Bank Application.
 *
 * java.util.Map: Used in the BloodBank entity to store blood groups and their quantities.
 * These collections are utilized to manage and store relevant data within the supply service of the blood bank application.
 *
 */

@Service
public class SupplyService {

    @Autowired
    private SupplyRepository supplyRepository;

    @Autowired
    private BloodBankRepository bloodBankRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private BloodRequestRepository bloodRequestRepository;



    /**
     * Adds a new supply report based on the given blood request and blood bank.
     *
     * @param bloodRequestId The ID of the blood request.
     * @param bloodBankId    The ID of the blood bank.
     * @return The created supply report.
     * @throws BloodNotSufficientException   If the blood quantity is not sufficient in the blood bank.
     * @throws BloodRequestNotFoundException If the blood request is not found.
     * @throws BloodBankNotFoundException   If the blood bank is not found.
     */
    public SupplyReport addSupplyReport( int bloodRequestId,
                                         int bloodBankId){
        Optional<BloodBank> bloodBank = bloodBankRepository.findById(bloodBankId);
        Optional<BloodRequest> optionalBloodRequest = bloodRequestRepository.findById(bloodRequestId);
        if(bloodBank.isPresent()){
            if(optionalBloodRequest.isPresent()){
                BloodRequest bloodRequest = optionalBloodRequest.get();
                SupplyReport supplyReport = new SupplyReport(0,
                        bloodRequest.getBloodGroup(), bloodRequest.getHospital(), bloodBank.get(),
                        bloodRequest.getQuantity(), LocalDateTime.now());
                Map<BloodGroup,Integer> bloodGroups = bloodBank.get().getBloodGroups();
                if(bloodRequest.getQuantity() <= bloodGroups.get(bloodRequest.getBloodGroup())){
                    bloodGroups.put(bloodRequest.getBloodGroup(),bloodGroups.get(bloodRequest.getBloodGroup()) - bloodRequest.getQuantity());
                    bloodBank.get().setBloodGroups(bloodGroups);
                    bloodBankRepository.save(bloodBank.get());
                    bloodRequest.setSupplied(true);
                    bloodRequestRepository.save(bloodRequest);
                    return supplyRepository.save(supplyReport);
                }
                throw new BloodNotSufficientException("Blood Not Sufficient in Blood Bank");
            }
            throw new BloodRequestNotFoundException("Request not found");
        }
        throw new BloodBankNotFoundException("BloodBank not found");
    }

    /**
     * Deletes a supply report with the given ID.
     *
     * @param supplyId The ID of the supply report to delete.
     * @return A message indicating the deletion status.
     * @throws ReportNotFoundException If the supply report is not found.
     */
    public String deleteSupplyReport(int supplyId){
        Optional<SupplyReport> supplyReport = supplyRepository.findById(supplyId);
        if (supplyReport.isPresent()){
            supplyRepository.deleteById(supplyId);
            return "supply report deleted";
        }
        throw new ReportNotFoundException("Supply report not found");
    }


    public List<SupplyReport> getAllSupplyReportsByBloodBank(int bloodBankId){
        return supplyRepository.findAllByBloodBank_BloodBankId(bloodBankId);
    }

}
