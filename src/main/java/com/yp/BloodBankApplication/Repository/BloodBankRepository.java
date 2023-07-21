package com.yp.BloodBankApplication.Repository;

import com.yp.BloodBankApplication.Entity.BloodBank;
import com.yp.BloodBankApplication.Requests.BloodBankUpdateRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Repository interface for accessing BloodBank entities in the database.
 */
@Repository
public interface BloodBankRepository extends JpaRepository<BloodBank , Integer> {
    Optional<Object> findByPhoneNumber(long phoneNumber);

    Optional<BloodBank> findByMailAddress(String mailAddress);
}
