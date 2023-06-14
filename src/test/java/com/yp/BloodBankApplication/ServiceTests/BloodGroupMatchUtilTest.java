package com.yp.BloodBankApplication.ServiceTests;


import com.yp.BloodBankApplication.Enums.BloodGroup;
import com.yp.BloodBankApplication.Utility.BloodBankUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BloodGroupMatchUtilTest {

    @Test
    public void testGetSuitableBloodGroups_APositive() {
        BloodGroup bloodGroup = BloodGroup.A_POSITIVE;
        List<BloodGroup> expectedSuitableBloodGroups = Arrays.asList(BloodGroup.A_POSITIVE, BloodGroup.AB_POSITIVE);

        List<BloodGroup> actualSuitableBloodGroups = BloodBankUtil.getSuitableBloodGroups(bloodGroup);

        Assertions.assertEquals(expectedSuitableBloodGroups, actualSuitableBloodGroups);
    }

    @Test
    public void testGetSuitableBloodGroups_BPositive() {
        BloodGroup bloodGroup = BloodGroup.B_POSITIVE;
        List<BloodGroup> expectedSuitableBloodGroups = Arrays.asList(BloodGroup.B_POSITIVE, BloodGroup.AB_POSITIVE);

        List<BloodGroup> actualSuitableBloodGroups = BloodBankUtil.getSuitableBloodGroups(bloodGroup);

        Assertions.assertEquals(expectedSuitableBloodGroups, actualSuitableBloodGroups);
    }

    @Test
    public void testGetSuitableBloodGroups_OPositive() {
        BloodGroup bloodGroup = BloodGroup.O_POSITIVE;
        List<BloodGroup> expectedSuitableBloodGroups = Arrays.asList(
                BloodGroup.O_POSITIVE, BloodGroup.A_POSITIVE, BloodGroup.B_POSITIVE, BloodGroup.AB_POSITIVE
        );

        List<BloodGroup> actualSuitableBloodGroups = BloodBankUtil.getSuitableBloodGroups(bloodGroup);

        Assertions.assertEquals(expectedSuitableBloodGroups, actualSuitableBloodGroups);
    }

    @Test
    public void testGetSuitableBloodGroups_ABPositive() {
        BloodGroup bloodGroup = BloodGroup.AB_POSITIVE;
        List<BloodGroup> expectedSuitableBloodGroups = Collections.singletonList(BloodGroup.AB_POSITIVE);

        List<BloodGroup> actualSuitableBloodGroups = BloodBankUtil.getSuitableBloodGroups(bloodGroup);

        Assertions.assertEquals(expectedSuitableBloodGroups, actualSuitableBloodGroups);
    }

    @Test
    public void testGetSuitableBloodGroups_ANegative() {
        BloodGroup bloodGroup = BloodGroup.A_NEGATIVE;
        List<BloodGroup> expectedSuitableBloodGroups = Arrays.asList(
                BloodGroup.A_POSITIVE, BloodGroup.A_NEGATIVE, BloodGroup.AB_POSITIVE, BloodGroup.AB_NEGATIVE
        );

        List<BloodGroup> actualSuitableBloodGroups = BloodBankUtil.getSuitableBloodGroups(bloodGroup);

        Assertions.assertEquals(expectedSuitableBloodGroups, actualSuitableBloodGroups);
    }

    @Test
    public void testGetSuitableBloodGroups_ONegative() {
        BloodGroup bloodGroup = BloodGroup.O_NEGATIVE;
        List<BloodGroup> expectedSuitableBloodGroups = Arrays.asList(
                BloodGroup.values()
        );

        List<BloodGroup> actualSuitableBloodGroups = BloodBankUtil.getSuitableBloodGroups(bloodGroup);

        Assertions.assertEquals(expectedSuitableBloodGroups, actualSuitableBloodGroups);
    }

    @Test
    public void testGetSuitableBloodGroups_BNegative() {
        BloodGroup bloodGroup = BloodGroup.B_NEGATIVE;
        List<BloodGroup> expectedSuitableBloodGroups = Arrays.asList(
                BloodGroup.B_POSITIVE, BloodGroup.B_NEGATIVE, BloodGroup.AB_POSITIVE, BloodGroup.AB_NEGATIVE
        );

        List<BloodGroup> actualSuitableBloodGroups = BloodBankUtil.getSuitableBloodGroups(bloodGroup);

        Assertions.assertEquals(expectedSuitableBloodGroups, actualSuitableBloodGroups);
    }

    @Test
    public void testGetSuitableBloodGroups_ABNegative() {
        BloodGroup bloodGroup = BloodGroup.AB_NEGATIVE;
        List<BloodGroup> expectedSuitableBloodGroups = Arrays.asList(
                BloodGroup.AB_POSITIVE, BloodGroup.AB_NEGATIVE
        );

        List<BloodGroup> actualSuitableBloodGroups = BloodBankUtil.getSuitableBloodGroups(bloodGroup);

        Assertions.assertEquals(expectedSuitableBloodGroups, actualSuitableBloodGroups);
    }

}

