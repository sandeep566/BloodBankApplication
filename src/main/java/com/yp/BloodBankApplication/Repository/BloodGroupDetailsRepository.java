package com.yp.BloodBankApplication.Repository;

import com.yp.BloodBankApplication.Entity.BloodGroupDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodGroupDetailsRepository extends JpaRepository<BloodGroupDetails , Integer> {
}
