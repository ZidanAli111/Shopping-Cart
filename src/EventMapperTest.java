package com.ielts.cmds.integration.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ielts.cmds.api.evt_112.LocationAddress;
import com.ielts.cmds.api.evt_112.LocationChanged;
import com.ielts.cmds.api.evt_112.LocationProduct;
import com.ielts.cmds.integration.SQSEventBodySetup;
import com.ielts.cmds.ors.common.integration.int455.AddressV1;
import com.ielts.cmds.ors.common.integration.int455.LocationV1;
import com.ielts.cmds.ors.common.integration.int455.ProductV1;

import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SystemStubsExtension.class)
class EventMapperTest {

    @Spy @InjectMocks private EventMapper eventMapper;

    @SystemStub private EnvironmentVariables env;

    private LocationChanged locationChanged;

    @BeforeEach
    void setUp() {
        locationChanged = SQSEventBodySetup.getLocationChanged();
    }

    @Test
    void mapLocationResponse_ExpectMappedLocation() {
        LocationV1 actual = eventMapper.mapLocationResponse(locationChanged);
        assertEquals(locationChanged.getLocationUuid(), actual.getLocationUuid());
        assertEquals(locationChanged.getParentLocationUuid().toString(), actual.getParentLocationUuid());
        assertEquals(locationChanged.getLocationName(), actual.getLocationName());
        assertEquals(locationChanged.getLocationTypeCode(), actual.getLocationTypeCode().toString());
        assertEquals(locationChanged.getStatus(), actual.getStatus().toString());
        assertEquals(locationChanged.getApprovedDate(), actual.getApprovedDate());
        assertEquals(locationChanged.getEligibleForOfflineTesting(), actual.getEligibleForOfflineTesting());
        assertEquals(locationChanged.getExternalLocationUuid(), actual.getExternalLocationUuid());
        assertEquals(locationChanged.getExternalParentLocationUuid(), actual.getExternalParentLocationUuid());
        assertEquals(locationChanged.getTestCentreAdministratorUuid(), actual.getTestCentreAdministratorUuid());
        assertEquals(locationChanged.getTestCentreNumber(), actual.getTestCentreNumber());
        assertEquals(locationChanged.getTimezoneName(), actual.getTimezoneName());
        assertEquals(locationChanged.getWebsiteURL(), actual.getWebsiteUrl());
    }

    @Test
    void mapLocationResponseWithNullStatusAndType_ExpectMappedLocation() {
        locationChanged.setStatus(null);
        locationChanged.setLocationTypeCode(null);
        LocationV1 actual = eventMapper.mapLocationResponse(locationChanged);
        assertNull(actual.getStatus());
        assertNull(actual.getLocationTypeCode());
        assertEquals(locationChanged.getLocationUuid(), actual.getLocationUuid());
        assertEquals(locationChanged.getParentLocationUuid().toString(), actual.getParentLocationUuid());
        assertEquals(locationChanged.getLocationName(), actual.getLocationName());
        assertEquals(locationChanged.getApprovedDate(), actual.getApprovedDate());
        assertEquals(locationChanged.getEligibleForOfflineTesting(), actual.getEligibleForOfflineTesting());
        assertEquals(locationChanged.getExternalLocationUuid(), actual.getExternalLocationUuid());
        assertEquals(locationChanged.getExternalParentLocationUuid(), actual.getExternalParentLocationUuid());
        assertEquals(locationChanged.getTestCentreAdministratorUuid(), actual.getTestCentreAdministratorUuid());
        assertEquals(locationChanged.getTestCentreNumber(), actual.getTestCentreNumber());
        assertEquals(locationChanged.getTimezoneName(), actual.getTimezoneName());
        assertEquals(locationChanged.getWebsiteURL(), actual.getWebsiteUrl());
    }

    @Test
    void populateLocationChangedAddress_ExpectMappedAddress() {
        List<AddressV1> actual = eventMapper.populateLocationChangedAddress(locationChanged);
        LocationAddress expected = locationChanged.getAddresses().get(0);
        assertEquals(1, actual.size());
        AddressV1 actualAddress = actual.get(0);
        assertEquals(expected.getLocationAddressUuid(), actualAddress.getAddressUuid());
        assertEquals(expected.getAddressTypeUuid(), actualAddress.getAddressTypeUuid());
        assertEquals(expected.getAddressLine1(), actualAddress.getAddressLine1());
        assertEquals(expected.getAddressLine2(), actualAddress.getAddressLine2());
        assertEquals(expected.getAddressLine3(), actualAddress.getAddressLine3());
        assertEquals(expected.getAddressLine4(), actualAddress.getAddressLine4());
        assertEquals(expected.getCity(), actualAddress.getCity());
        assertEquals(expected.getTerritoryUuid(), actualAddress.getTerritoryUuid());
        assertEquals(expected.getCountryUuid().toString(), actualAddress.getCountryUuid());
        assertEquals(expected.getPostalCode(), actualAddress.getPostalCode());
        assertEquals(expected.getEmail(), actualAddress.getEmail());
        assertEquals(expected.getPrimaryPhone(), actualAddress.getPhone());
    }

    @Test
    void mapLocationResponse_ExpectMappedProducts() {
        List<ProductV1> actual = eventMapper.populateLocationChangedApprovedProducts(locationChanged);
        LocationProduct expected = locationChanged.getApprovedProducts().get(0);
        assertEquals(1, actual.size());
        ProductV1 actualProduct = actual.get(0);
        assertEquals(expected.getProductUuid(), actualProduct.getProductUuid());
        assertEquals(expected.getLocationProductUuid().toString(), actualProduct.getLocationProductUuid());
        assertEquals(expected.getEffectiveFromDate(), actualProduct.getEffectiveFromDate());
        assertEquals(expected.getEffectiveToDate(), actualProduct.getEffectiveToDate());

    }


}
