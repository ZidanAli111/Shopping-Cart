package com.ielts.cmds.integration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;

import com.ielts.cmds.api.evt_112.LocationAddress;
import com.ielts.cmds.api.evt_112.LocationChanged;
import com.ielts.cmds.api.evt_112.LocationProduct;
import com.ielts.cmds.v2.serialization.lambda.utils.HeaderContext;
import com.ielts.cmds.v2.serialization.lambda.utils.ThreadLocalHeaderContext;

public class SQSEventBodySetup {


    public static LocationChanged getLocationChanged() {
        LocationChanged locationChanged = new LocationChanged();
        locationChanged.setLocationUuid(UUID.fromString("cef4fcb1-2bd2-51f3-889d-abd47d775565"));
        locationChanged.setParentLocationUuid(UUID.fromString("e8958327-9917-4af2-91ce-7c6cd0c19837"));
        locationChanged.setPartnerCode(null);
        locationChanged.setLocationTypeCode("TEST_CENTRE");
        locationChanged.setLocationName("Name");
        locationChanged.setExternalLocationUuid(UUID.fromString("cef4fcb1-2bd2-51f3-889d-abd47d775565"));
        locationChanged.setExternalParentLocationUuid(UUID.fromString("e8958327-9917-4af2-91ce-7c6cd0c19837"));
        locationChanged.setStatus("ACTIVE");
        locationChanged.setTestCentreNumber("AU241");
        locationChanged.setTestCentreAdministratorUuid(UUID.fromString("b32a82a0-693c-4dfd-824f-962784f8bf7d"));
        locationChanged.setApprovedDate("2020-04-30");
        locationChanged.setEligibleForOfflineTesting(false);
        locationChanged.setTimezoneName("UTC");
        locationChanged.setWebsiteURL("https://www.cambridgeassessment.org.uk/");

        LocationAddress locationAddress = new LocationAddress();
        locationAddress.setLocationAddressUuid(UUID.fromString("cef4fcb1-2bd2-51f3-889d-abd47d775565"));
        locationAddress.setAddressTypeUuid(UUID.fromString("fc7663b9-b4cd-458a-bc1e-59e601888046"));
        locationAddress.setAddressLine1("GPO BOX 1515");
        locationAddress.setAddressLine2("");
        locationAddress.setAddressLine3("");
        locationAddress.setAddressLine4("");
        locationAddress.setCity("Melbourne");
        locationAddress.setTerritoryUuid(UUID.fromString("75d648f4-1051-4f81-b135-67fb2abb4054"));
        locationAddress.setPostalCode("3000");
        locationAddress.setCountryIso3Code("AUS");
        locationAddress.setCountryUuid(UUID.fromString("38cfc88f-fd8d-4f77-8e76-6999db026bc9"));
        locationAddress.setEmail("info@cambridgeassessment.org.uk");
        locationAddress.setPrimaryPhone("+44 (0)1223 553311");
        locationAddress.setSecondaryPhone("+44 (0)1223 553311");
        List<LocationAddress> locationAddressList = new ArrayList<>();
        locationAddressList.add(locationAddress);
        locationChanged.setAddresses(locationAddressList);

        LocationProduct locationApprovedProduct = new LocationProduct();
        locationApprovedProduct.setLocationProductUuid(UUID.fromString("38cfc88f-fd8d-4f77-8e76-6999db026bc9"));
        locationApprovedProduct.setProductUuid(UUID.fromString("fc7663b9-b4cd-458a-bc1e-59e601888046"));
        locationApprovedProduct.setEffectiveFromDate(LocalDate.parse("2020-06-01"));
        locationApprovedProduct.setEffectiveToDate(LocalDate.parse("2020-09-02"));
        List<LocationProduct> locationApprovedProductList = new ArrayList<>();
        locationApprovedProductList.add(locationApprovedProduct);
        locationChanged.setApprovedProducts(locationApprovedProductList);


        return locationChanged;
    }

    public static void setHeaderContext() {
        HeaderContext context = new HeaderContext();
        context.setCorrelationId(UUID.randomUUID());
        context.setTransactionId(UUID.randomUUID());
        context.setPartnerCode("test");
        ThreadLocalHeaderContext.setContext(context);

    }

    public static HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        return httpHeaders;

    }

}
