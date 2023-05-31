package com.yp.BloodBankApplication.Entity;

import com.yp.BloodBankApplication.Enums.BloodGroup;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SupplyReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int supplyId;

    private BloodGroup bloodGroup;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hospitalId")
    private Hospital hospital;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bloodBankId")
    private BloodBank bloodBank;

    private int quantity;

    private LocalDateTime dateTime;
}
