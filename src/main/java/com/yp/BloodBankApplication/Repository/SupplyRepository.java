package com.yp.BloodBankApplication.Repository;

import com.yp.BloodBankApplication.Entity.SupplyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Repository interface for accessing SupplyReport entities in the database.
 */
@Repository
public interface SupplyRepository extends JpaRepository<SupplyReport , Integer> {
    List<SupplyReport> findAllByBloodBank_BloodBankId(int bloodBankId);
}
