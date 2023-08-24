package com.yp.BloodBankApplication.ServiceTests;

import com.yp.BloodBankApplication.Entity.BloodBank;
import com.yp.BloodBankApplication.Entity.Donor;
import com.yp.BloodBankApplication.Enums.BloodGroup;
import com.yp.BloodBankApplication.Exception.BloodBankNotFoundException;
import com.yp.BloodBankApplication.Exception.DonorNotFoundException;
import com.yp.BloodBankApplication.Repository.BloodBankRepository;
import com.yp.BloodBankApplication.Repository.DonorRepository;
import com.yp.BloodBankApplication.Requests.DonorRequest;
import com.yp.BloodBankApplication.Services.DonorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DonorServiceTest {

    @Mock
    private DonorRepository donorRepository;

    @Mock
    private BloodBankRepository bloodBankRepository;

    @InjectMocks
    private DonorService donorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerDonor_shouldRegisterDonorAndUpdateBloodBank() {
        // Arrange
        BloodGroup bloodGroup = BloodGroup.A_POSITIVE;
        int bloodBankId = 1;
        int donationQuantity = 100;

        BloodBank bloodBank = new BloodBank();
        bloodBank.setBloodBankId(bloodBankId);
        Map<BloodGroup, Integer> bloodGroups = new HashMap<>();
        bloodGroups.put(bloodGroup, 0);
        bloodBank.setBloodGroups(bloodGroups);

        DonorRequest donorRequest = new DonorRequest();
        donorRequest.setDonorId(1);
        donorRequest.setDonorName("John");
        donorRequest.setAge(30);
        donorRequest.setAddress("123 Main St");
        donorRequest.setPhoneNo(5551234);
        donorRequest.setDonationQuantity(donationQuantity);

        when(bloodBankRepository.findById(bloodBankId)).thenReturn(Optional.of(bloodBank));
        when(donorRepository.save(any(Donor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Donor registeredDonor = donorService.registerDonor(donorRequest, bloodBankId, bloodGroup);

        // Assert
        Assertions.assertEquals(donorRequest.getDonorId(), registeredDonor.getDonorId());
        Assertions.assertEquals(donorRequest.getDonorName(), registeredDonor.getDonorName());
        Assertions.assertEquals(donorRequest.getAge(), registeredDonor.getAge());
        Assertions.assertEquals(donorRequest.getAddress(), registeredDonor.getAddress());
        Assertions.assertEquals(donorRequest.getPhoneNo(), registeredDonor.getPhoneNo());
        Assertions.assertEquals(bloodGroup, registeredDonor.getBloodGroup());
        Assertions.assertEquals(donationQuantity, registeredDonor.getDonationQuantity());

        verify(bloodBankRepository, times(1)).findById(bloodBankId);
        verify(donorRepository, times(1)).save(any(Donor.class));
    }

    @Test
    void registerDonor_shouldThrowBloodBankNotFoundException() {
        // Arrange
        int bloodBankId = 1;
        BloodGroup bloodGroup = BloodGroup.A_POSITIVE;

        when(bloodBankRepository.findById(bloodBankId)).thenReturn(Optional.empty());

        DonorRequest donorRequest = new DonorRequest();

        // Act and Assert
        Assertions.assertThrows(BloodBankNotFoundException.class, () -> donorService.registerDonor(donorRequest, bloodBankId, bloodGroup));

        verify(bloodBankRepository, times(1)).findById(bloodBankId);
        verify(donorRepository, never()).save(any(Donor.class));
    }

//    @Test
//    public void testUpdateDonor() {
//        // Create a sample donorRequest and bloodGroup
//        DonorRequest donorRequest = new DonorRequest(/* initialize with necessary values */);
//        BloodGroup bloodGroup = BloodGroup.AB_POSITIVE; // Change to your desired BloodGroup
//
//        // Create a sample donor and bloodBank
//        Donor donor = new Donor(/* initialize with necessary values */);
//        BloodBank bloodBank = new BloodBank(/* initialize with necessary values */);
//
//        // Mock repository responses
//        when(donorRepository.findById(any())).thenReturn(Optional.of(donor));
//        when(donorRepository.findBloodBankIdByDonorId(any())).thenReturn(123);
//        when(bloodBankRepository.findById(any())).thenReturn(Optional.of(bloodBank));
//
//        // Invoke the method
//        String result = donorService.updateDonor(donorRequest, bloodGroup);
//
//        // Verify repository method invocations
//        verify(donorRepository, times(1)).findById(anyInt());
//        verify(donorRepository, times(1)).findBloodBankIdByDonorId(anyInt());
//        verify(bloodBankRepository, times(1)).findById(anyInt());
//        verify(donorRepository, times(1)).save(any(Donor.class));
//        verify(bloodBankRepository, times(1)).save(any(BloodBank.class));
//
//        // Assertions
//        Assertions.assertEquals("Donor Updated", result);
//        // Add more assertions if needed
//    }

    @Test
    void updateDonor_shouldThrowDonorNotFoundException() {
        // Arrange
        int donorId = 1;
        BloodGroup bloodGroup = BloodGroup.AB_POSITIVE;

        when(donorRepository.findById(donorId)).thenReturn(Optional.empty());

        DonorRequest donorRequest = new DonorRequest();
        donorRequest.setDonorId(donorId);

        // Act and Assert
        Assertions.assertThrows(DonorNotFoundException.class, () -> donorService.updateDonor(donorRequest, bloodGroup));

        verify(donorRepository, times(1)).findById(donorId);
        verify(donorRepository, never()).findBloodBankIdByDonorId(anyInt());
        verify(bloodBankRepository, never()).findById(anyInt());
        verify(donorRepository, never()).save(any(Donor.class));
        verify(bloodBankRepository, never()).save(any(BloodBank.class));
    }

    @Test
    public void testViewDonorById() {
        // Mock data
        int donorId = 1;
        Donor donor = new Donor();
        donor.setDonorId(donorId);
        donor.setDonorName("John Doe");
        donor.setAge(30);
        donor.setAddress("123 Main St");
        donor.setPhoneNo(1234567890);

        // Mock repository method
        Mockito.when(donorRepository.findById(donorId)).thenReturn(Optional.of(donor));

        // Invoke the service method
        Donor result = donorService.viewDonorById(donorId);

        // Verify the result
        Assertions.assertEquals(donorId, result.getDonorId());
        Assertions.assertEquals("John Doe", result.getDonorName());
        Assertions.assertEquals(30, result.getAge());
        Assertions.assertEquals("123 Main St", result.getAddress());
        Assertions.assertEquals(1234567890, result.getPhoneNo());
    }

    @Test
    public void testViewDonorById_DonorNotFound() {
        // Mock data
        int donorId = 1;

        // Mock repository method
        Mockito.when(donorRepository.findById(donorId)).thenReturn(Optional.empty());

        // Invoke the service method and verify the exception
        Assertions.assertThrows(DonorNotFoundException.class, () -> donorService.viewDonorById(donorId));
    }

    @Test
    public void testGetAllDonors() {
        // Mock data
        Donor donor1 = new Donor();
        donor1.setDonorId(1);
        donor1.setDonorName("John Doe");
        donor1.setAge(30);
        donor1.setAddress("123 Main St");
        donor1.setPhoneNo(1234567890);

        Donor donor2 = new Donor();
        donor2.setDonorId(2);
        donor2.setDonorName("Jane Smith");
        donor2.setAge(25);
        donor2.setAddress("456 Elm St");
        donor2.setPhoneNo(987654321);

        List<Donor> expectedDonors = Arrays.asList(donor1, donor2);

        // Mock repository method
        Mockito.when(donorRepository.findAll()).thenReturn(expectedDonors);

        // Invoke the service method
        List<Donor> result = donorService.getAllDonors();

        // Verify the result
        Assertions.assertEquals(expectedDonors.size(), result.size());
        Assertions.assertEquals(expectedDonors.get(0).getDonorId(), result.get(0).getDonorId());
        Assertions.assertEquals(expectedDonors.get(1).getDonorName(), result.get(1).getDonorName());
    }


    @Test
    public void testGetAllDonorsByBloodGroup() {
        // Mock data
        Donor donor1 = new Donor();
        donor1.setDonorId(1);
        donor1.setDonorName("John Doe");
        donor1.setAge(30);
        donor1.setAddress("123 Main St");
        donor1.setPhoneNo(1234567890);
        donor1.setBloodGroup(BloodGroup.A_POSITIVE);

        Donor donor2 = new Donor();
        donor2.setDonorId(2);
        donor2.setDonorName("Jane Smith");
        donor2.setAge(25);
        donor2.setAddress("456 Elm St");
        donor2.setPhoneNo(987654321);
        donor2.setBloodGroup(BloodGroup.B_POSITIVE);

        Donor donor3 = new Donor();
        donor3.setDonorId(3);
        donor3.setDonorName("David Johnson");
        donor3.setAge(35);
        donor3.setAddress("789 Oak St");
        donor3.setPhoneNo(456789012);
        donor3.setBloodGroup(BloodGroup.A_POSITIVE);

        List<Donor> allDonors = Arrays.asList(donor1, donor2, donor3);

        // Mock repository method
        Mockito.when(donorRepository.findAll()).thenReturn(allDonors);

        // Invoke the service method
        List<Donor> result = donorService.getAllDonorsByBloodGroup(BloodGroup.A_POSITIVE);

        // Verify the result
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(donor1.getDonorId(), result.get(0).getDonorId());
        Assertions.assertEquals(donor3.getDonorId(), result.get(1).getDonorId());
    }

    @Test
    public void testDeleteDonor() {
        // Mock data
        int donorId = 1;
        Donor donor = new Donor();
        donor.setDonorId(donorId);
        donor.setDonorName("John Doe");
        donor.setAge(30);
        donor.setAddress("123 Main St");
        donor.setPhoneNo(1234567890);

        // Mock repository method
        Mockito.when(donorRepository.findById(donorId)).thenReturn(Optional.of(donor));

        // Invoke the service method
        String result = donorService.deleteDonor(donorId);

        // Verify the result
        Assertions.assertEquals("Donor Deleted", result);
        Mockito.verify(donorRepository, Mockito.times(1)).deleteById(donorId);
    }

    @Test
    public void testDeleteDonor_NotFound() {
        // Mock data
        int donorId = 1;

        // Mock repository method
        Mockito.when(donorRepository.findById(donorId)).thenReturn(Optional.empty());

        // Invoke the service method and assert the exception
        Assertions.assertThrows(DonorNotFoundException.class, () -> donorService.deleteDonor(donorId));
        Mockito.verify(donorRepository, Mockito.times(0)).deleteById(donorId);
    }

    @Test
    public void testViewDonorsByAge() {
        // Mock data
        int age = 30;
        List<Donor> donors = new ArrayList<>();
        donors.add(createDonor(1, "John Doe", age, "123 Main St", 1234567890));
        donors.add(createDonor(2, "Jane Smith", age, "456 Elm St", 987654321));

        // Mock repository method
        Mockito.when(donorRepository.findAll()).thenReturn(donors);

        // Invoke the service method
        List<Donor> result = donorService.viewDonorsByAge(age);

        // Verify the result
        Assertions.assertEquals(2, result.size());
        for (Donor donor : result) {
            Assertions.assertEquals(age, donor.getAge());
        }
    }

    @Test
    public void testViewDonorsByAge_NoMatchingDonors() {
        // Mock data
        int age = 30;
        List<Donor> donors = new ArrayList<>();
        donors.add(createDonor(1, "John Doe", 25, "123 Main St", 123456789));
        donors.add(createDonor(2, "Jane Smith", 35, "456 Elm St", 987654321));

        // Mock repository method
        Mockito.when(donorRepository.findAll()).thenReturn(donors);

        // Invoke the service method
        List<Donor> result = donorService.viewDonorsByAge(age);

        // Verify the result
        Assertions.assertTrue(result.isEmpty());
    }

    private Donor createDonor(int donorId, String donorName, int age, String address, long phoneNo) {
        Donor donor = new Donor();
        donor.setDonorId(donorId);
        donor.setDonorName(donorName);
        donor.setAge(age);
        donor.setAddress(address);
        donor.setPhoneNo(phoneNo);
        return donor;
    }

    @Test
    public void testViewDonorsBySuitableBloodGroup() {
        List<BloodGroup> bloodGroups = new ArrayList<>();
        bloodGroups.add(BloodGroup.A_POSITIVE);
        bloodGroups.add(BloodGroup.B_POSITIVE);

        // Create a list of sample donors
        List<Donor> donors = new ArrayList<>();
        // Add sample donors to the list

        Mockito.when(donorRepository.findAll()).thenReturn(donors);

        List<Donor> expectedDonors = new ArrayList<>();
        // Add the expected donors to the list based on the provided blood groups

        List<Donor> actualDonors = donorService.viewDonorsBySuitableBloodGroup(bloodGroups);

        Assertions.assertEquals(expectedDonors, actualDonors);
        Mockito.verify(donorRepository, Mockito.times(1)).findAll();
    }


}
