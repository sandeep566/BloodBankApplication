package com.yp.BloodBankApplication.ServiceTests;

import com.yp.BloodBankApplication.Entity.BloodBank;
import com.yp.BloodBankApplication.Entity.BloodRequest;
import com.yp.BloodBankApplication.Entity.SupplyReport;
import com.yp.BloodBankApplication.Enums.BloodGroup;
import com.yp.BloodBankApplication.Exception.*;
import com.yp.BloodBankApplication.Repository.BloodBankRepository;
import com.yp.BloodBankApplication.Repository.BloodRequestRepository;
import com.yp.BloodBankApplication.Repository.SupplyRepository;
import com.yp.BloodBankApplication.Services.SupplyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SupplyServiceTest {

    @Mock
    private SupplyRepository supplyRepository;

    @Mock
    private BloodBankRepository bloodBankRepository;

    @Mock
    private BloodRequestRepository bloodRequestRepository;

    @InjectMocks
    private SupplyService supplyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddSupplyReport() {
        int bloodRequestId = 1;
        int bloodBankId = 1;
        BloodGroup bloodGroup = BloodGroup.A_POSITIVE;
        int quantity = 10;

        BloodRequest bloodRequest = new BloodRequest();
        bloodRequest.setBloodRequestId(bloodRequestId);
        bloodRequest.setBloodGroup(bloodGroup);
        bloodRequest.setQuantity(quantity);

        BloodBank bloodBank = new BloodBank();
        bloodBank.setBloodBankId(bloodBankId);
        Map<BloodGroup, Integer> bloodGroups = new HashMap<>();
        bloodGroups.put(bloodGroup, quantity + 5); // Blood quantity is more than the requested quantity
        bloodBank.setBloodGroups(bloodGroups);

        when(bloodRequestRepository.findById(bloodRequestId)).thenReturn(Optional.of(bloodRequest));
        when(bloodBankRepository.findById(bloodBankId)).thenReturn(Optional.of(bloodBank));
        when(supplyRepository.save(any(SupplyReport.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SupplyReport supplyReport = supplyService.addSupplyReport(bloodRequestId, bloodBankId);

        assertNotNull(supplyReport);
        assertEquals(bloodGroup, supplyReport.getBloodGroup());
        assertEquals(quantity, supplyReport.getQuantity());
        assertEquals(bloodBank, supplyReport.getBloodBank());
        assertTrue(bloodRequest.isSupplied());
        verify(supplyRepository, times(1)).save(any(SupplyReport.class));
        verify(bloodBankRepository, times(1)).save(bloodBank);
        verify(bloodRequestRepository, times(1)).save(bloodRequest);
    }

    @Test
    public void testAddSupplyReportBloodNotSufficientException() {
        int bloodRequestId = 1;
        int bloodBankId = 1;
        BloodGroup bloodGroup = BloodGroup.A_POSITIVE;
        int quantity = 10;

        BloodRequest bloodRequest = new BloodRequest();
        bloodRequest.setBloodRequestId(bloodRequestId);
        bloodRequest.setBloodGroup(bloodGroup);
        bloodRequest.setQuantity(quantity);

        BloodBank bloodBank = new BloodBank();
        bloodBank.setBloodBankId(bloodBankId);
        Map<BloodGroup, Integer> bloodGroups = new HashMap<>();
        bloodGroups.put(bloodGroup, quantity - 5); // Blood quantity is less than the requested quantity
        bloodBank.setBloodGroups(bloodGroups);

        when(bloodRequestRepository.findById(bloodRequestId)).thenReturn(Optional.of(bloodRequest));
        when(bloodBankRepository.findById(bloodBankId)).thenReturn(Optional.of(bloodBank));

        assertThrows(BloodNotSufficientException.class,
                () -> supplyService.addSupplyReport(bloodRequestId, bloodBankId));

        verify(supplyRepository, never()).save(any(SupplyReport.class));
        verify(bloodBankRepository, never()).save(bloodBank);
        verify(bloodRequestRepository, never()).save(bloodRequest);
    }

    @Test
    public void testAddSupplyReportBloodRequestNotFoundException() {
        int bloodRequestId = 1;
        int bloodBankId = 1;

        when(bloodRequestRepository.findById(bloodRequestId)).thenReturn(Optional.empty());
        when(bloodBankRepository.findById(bloodBankId)).thenReturn(Optional.of(new BloodBank()));

        assertThrows(BloodRequestNotFoundException.class,
                () -> supplyService.addSupplyReport(bloodRequestId, bloodBankId));

        verify(supplyRepository, never()).save(any(SupplyReport.class));
        verify(bloodBankRepository, never()).save(any(BloodBank.class));
        verify(bloodRequestRepository, never()).save(any(BloodRequest.class));
    }

    @Test
    public void testAddSupplyReportBloodBankNotFoundException() {
        int bloodRequestId = 1;
        int bloodBankId = 1;

        when(bloodRequestRepository.findById(bloodRequestId)).thenReturn(Optional.of(new BloodRequest()));
        when(bloodBankRepository.findById(bloodBankId)).thenReturn(Optional.empty());

        assertThrows(BloodBankNotFoundException.class,
                () -> supplyService.addSupplyReport(bloodRequestId, bloodBankId));

        verify(supplyRepository, never()).save(any(SupplyReport.class));
        verify(bloodBankRepository, never()).save(any(BloodBank.class));
        verify(bloodRequestRepository, never()).save(any(BloodRequest.class));
    }

    @Test
    public void testDeleteSupplyReport() {
        int supplyId = 1;

        when(supplyRepository.findById(supplyId)).thenReturn(Optional.of(new SupplyReport()));

        String result = supplyService.deleteSupplyReport(supplyId);

        assertEquals("supply report deleted", result);
        verify(supplyRepository, times(1)).deleteById(supplyId);
    }

    @Test
    public void testDeleteSupplyReportReportNotFoundException() {
        int supplyId = 1;

        when(supplyRepository.findById(supplyId)).thenReturn(Optional.empty());

        assertThrows(ReportNotFoundException.class,
                () -> supplyService.deleteSupplyReport(supplyId));

        verify(supplyRepository, never()).deleteById(supplyId);
    }
}

