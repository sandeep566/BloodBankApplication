package com.yp.BloodBankApplication.Repository;

import com.yp.BloodBankApplication.Entity.SupplyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



/**
 * Repository interface for accessing SupplyReport entities in the database.
 */
@Repository
public interface SupplyRepository extends JpaRepository<SupplyReport , Integer> {
}
