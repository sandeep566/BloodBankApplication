package com.yp.BloodBankApplication.Repository;

import com.yp.BloodBankApplication.Entity.BloodBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodBankRepository extends JpaRepository<BloodBank , Integer> {
}
