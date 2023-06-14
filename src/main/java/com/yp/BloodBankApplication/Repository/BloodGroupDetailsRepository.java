package com.yp.BloodBankApplication.Repository;

import com.yp.BloodBankApplication.Entity.BloodGroupDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for accessing BloodGroupDetails entities in the database.
 */
@Repository
public interface BloodGroupDetailsRepository extends JpaRepository<BloodGroupDetails , Integer> {
}
