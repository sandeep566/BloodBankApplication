package com.yp.BloodBankApplication.Repository;

import com.yp.BloodBankApplication.Entity.BloodRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for accessing BloodRequest entities in the database.
 */
@Repository
public interface BloodRequestRepository extends JpaRepository<BloodRequest,Integer> {

    @Query("SELECT br FROM BloodRequest br WHERE br.hospital.hospitalId = :hospitalRequestId")
    Page<BloodRequest>  findAllByHospitalRequestId(Pageable pageable, int hospitalRequestId);
}
