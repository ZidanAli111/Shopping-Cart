package com.ielts.cmds.integration;

import org.springframework.http.HttpHeaders;

import com.ielts.cmds.integration.constants.DistORSConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ORSLocationChangedDistBCCHN extends AbstractORSLocationChangedDist{
    @Override
    protected String getPartnerCodeConstants() {
        return DistORSConstants.BC_CHN;
    }

    @Override
    protected String getApplicationName() {
        return DistORSConstants.ORS_LOCATIONCHANGED_DIST_BCCHN;
    }

    @Override
    protected void setAdditionalHttpHeaders(HttpHeaders httpHeaders) {
        log.debug("Not modifying httpheaders for partner : {}", httpHeaders.get(DistORSConstants.PARTNER_CODE));

    }
}
