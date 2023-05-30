package com.yp.BloodBankApplication.Repository;

import com.yp.BloodBankApplication.Entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonorRepository extends JpaRepository<Donor , Integer> {
}
