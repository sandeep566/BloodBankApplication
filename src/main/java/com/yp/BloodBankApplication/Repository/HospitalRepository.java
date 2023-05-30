package com.yp.BloodBankApplication.Repository;

import com.yp.BloodBankApplication.Entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital,Integer> {


    Optional<Hospital> findByHospitalName(String hospitalName);
}
