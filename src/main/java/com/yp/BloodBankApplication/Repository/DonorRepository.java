package com.yp.BloodBankApplication.Repository;

import com.yp.BloodBankApplication.Entity.Donor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

/**
 * Repository interface for accessing Donor entities in the database.
 */
@Repository
public interface DonorRepository extends JpaRepository<Donor , Integer> {

    /**
     * Custom query to find the blood bank ID by donor ID.
     *
     * @param donorId the ID of the donor
     * @return the blood bank ID associated with the donor
     */
    @Query("SELECT d.bloodBank.bloodBankId FROM Donor d WHERE d.donorId = :donorId")
    int findBloodBankIdByDonorId(int donorId);


    Optional<Donor> findByAadhaarNo(BigInteger aadharNo);

    @Query("SELECT donor from Donor donor WHERE donor.bloodBank.bloodBankId = :bloodBankId")
    Page<Donor> findAllByBloodBankId(Pageable pageable,int bloodBankId);
}
