package com.yp.BloodBankApplication.Repository;

import com.yp.BloodBankApplication.Entity.BloodRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodRequestRepository extends JpaRepository<BloodRequest,Integer> {
}
