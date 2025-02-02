package com.ielts.cmds.integration.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;

import com.ielts.cmds.api.evt_112.LocationAddress;
import com.ielts.cmds.api.evt_112.LocationChanged;
import com.ielts.cmds.api.evt_112.LocationProduct;
import com.ielts.cmds.ors.common.integration.int455.AddressV1;
import com.ielts.cmds.ors.common.integration.int455.LocationV1;
import com.ielts.cmds.ors.common.integration.int455.ProductV1;

/** This class is used to map incoming event to appropriate API request body */
public class EventMapper {

    /**
     * Maps input event to booking response specific to request api body
     *
     * @param locationChanged
     * @return
     */
    public LocationV1 mapLocationResponse(final LocationChanged locationChanged) {
        final LocationV1 orsLocationChangedResponse = new LocationV1();
        orsLocationChangedResponse.setLocationUuid(locationChanged.getLocationUuid());
        orsLocationChangedResponse.setParentLocationUuid(locationChanged.getParentLocationUuid().toString());
        if(StringUtils.isNotEmpty(locationChanged.getLocationTypeCode())) {
            orsLocationChangedResponse.setLocationTypeCode(LocationV1.LocationTypeCodeEnum.valueOf(locationChanged.getLocationTypeCode()));
        }
        orsLocationChangedResponse.setLocationName(locationChanged.getLocationName());
        orsLocationChangedResponse.setExternalLocationUuid(locationChanged.getExternalLocationUuid());
        orsLocationChangedResponse.setExternalParentLocationUuid(locationChanged.getExternalParentLocationUuid());
        if(StringUtils.isNotEmpty(locationChanged.getStatus())) {
            orsLocationChangedResponse.setStatus(LocationV1.StatusEnum.valueOf(locationChanged.getStatus()));
        }
        orsLocationChangedResponse.setTestCentreNumber(locationChanged.getTestCentreNumber());
        orsLocationChangedResponse.setTestCentreAdministratorUuid(locationChanged.getTestCentreAdministratorUuid());
        orsLocationChangedResponse.setApprovedDate(locationChanged.getApprovedDate());
        orsLocationChangedResponse.setWebsiteUrl(locationChanged.getWebsiteURL());
        orsLocationChangedResponse.setEligibleForOfflineTesting(locationChanged.getEligibleForOfflineTesting());
        orsLocationChangedResponse.setTimezoneName(locationChanged.getTimezoneName());
        orsLocationChangedResponse.setAddresses(populateLocationChangedAddress(locationChanged));
        orsLocationChangedResponse.setApprovedProducts(populateLocationChangedApprovedProducts(locationChanged));
        return orsLocationChangedResponse;
    }

    /**
     * creates locationChangedAddressResponses response
     * Mapping LocationChangedV1 to LocationChangedAddressResponse
     */
    List<AddressV1> populateLocationChangedAddress(final LocationChanged location) {
        final List<AddressV1> locationChangedAddressResponses = new ArrayList<>();
        final Optional<List<LocationAddress>> optLocationChangedAddresses =
                Optional.ofNullable(location.getAddresses());
        optLocationChangedAddresses.ifPresent(
                locationChangedAddresses ->
                        locationChangedAddresses
                                .stream()
                                .forEach(
                                        locationChangedAddress -> {
                                            final AddressV1 locationChangedAddressResponse = new AddressV1();
                                            locationChangedAddressResponse.setAddressUuid(locationChangedAddress.getLocationAddressUuid());
                                            locationChangedAddressResponse.setAddressTypeUuid(locationChangedAddress.getAddressTypeUuid());
                                            locationChangedAddressResponse.setAddressLine1(locationChangedAddress.getAddressLine1());
                                            locationChangedAddressResponse.setAddressLine2(locationChangedAddress.getAddressLine2());
                                            locationChangedAddressResponse.setAddressLine3(locationChangedAddress.getAddressLine3());
                                            locationChangedAddressResponse.setAddressLine4(locationChangedAddress.getAddressLine4());
                                            locationChangedAddressResponse.setCity(locationChangedAddress.getCity());
                                            locationChangedAddressResponse.setTerritoryUuid(locationChangedAddress.getTerritoryUuid());
                                            locationChangedAddressResponse.setPostalCode(locationChangedAddress.getPostalCode());
                                            locationChangedAddressResponse.setCountryUuid(locationChangedAddress.getCountryUuid().toString());
                                            locationChangedAddressResponse.setEmail(locationChangedAddress.getEmail());
                                            locationChangedAddressResponse.setPhone(locationChangedAddress.getPrimaryPhone());
                                            locationChangedAddressResponses.add(locationChangedAddressResponse);
                                        })
        );
        return locationChangedAddressResponses;
    }

    /**
     * creates locationChangedApprovedProductsResponses response
     * Mapping LocationChangedV1 to LocationChangedApprovedProductsResponse
     */
    List<ProductV1> populateLocationChangedApprovedProducts(final LocationChanged location) {
        final List<ProductV1> locationChangedApprovedProductsResponses = new ArrayList<>();
        final Optional<List<LocationProduct>> optLocationChangedApprovedProducts =
                Optional.ofNullable(location.getApprovedProducts());
        optLocationChangedApprovedProducts.ifPresent(
                locationChangedApprovedProducts ->
                        locationChangedApprovedProducts
                                .stream()
                                .forEach(
                                        locationChangedApprovedProduct -> {
                                            final ProductV1 locationChangedApprovedProductsResponse = new ProductV1();
                                            locationChangedApprovedProductsResponse.setProductUuid(locationChangedApprovedProduct.getProductUuid());
                                            locationChangedApprovedProductsResponse.setLocationProductUuid(locationChangedApprovedProduct.getLocationProductUuid().toString());
                                            locationChangedApprovedProductsResponse.setEffectiveFromDate(locationChangedApprovedProduct.getEffectiveFromDate());
                                            locationChangedApprovedProductsResponse.setEffectiveToDate(locationChangedApprovedProduct.getEffectiveToDate());
                                            locationChangedApprovedProductsResponses.add(locationChangedApprovedProductsResponse);
                                        })
        );
        return locationChangedApprovedProductsResponses;
    }
}
