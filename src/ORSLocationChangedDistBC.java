package com.ielts.cmds.integration;

import static com.ielts.cmds.integration.constants.DistORSConstants.USER_AGENT;
import static com.ielts.cmds.integration.constants.DistORSConstants.USER_AGENT_KEY;

import org.springframework.http.HttpHeaders;

import com.ielts.cmds.integration.constants.DistORSConstants;

public class ORSLocationChangedDistBC extends AbstractORSLocationChangedDist {


    @Override
    protected String getPartnerCodeConstants() {
        return DistORSConstants.BC;
    }

    @Override
    protected String getApplicationName() {
        return DistORSConstants.ORS_LOCATIONCHANGED_DIST_BC;
    }

    @Override
    protected void setAdditionalHttpHeaders(HttpHeaders httpHeaders) {
        httpHeaders.set(USER_AGENT_KEY, System.getenv(USER_AGENT));

    }
}
