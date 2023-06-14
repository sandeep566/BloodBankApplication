package com.yp.BloodBankApplication.Repository;

import com.yp.BloodBankApplication.Entity.BloodBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Repository interface for accessing BloodBank entities in the database.
 */
@Repository
public interface BloodBankRepository extends JpaRepository<BloodBank , Integer> {
    Optional<Object> findByPhoneNumber(long phoneNumber);
}
