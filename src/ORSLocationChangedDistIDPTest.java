package com.ielts.cmds.integration;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;

import software.amazon.awssdk.services.sns.SnsClient;
import com.ielts.cmds.integration.constants.DistORSConstants;
import com.ielts.cmds.v2.serialization.lambda.config.SNSClientConfig;

import lombok.SneakyThrows;

@ExtendWith(MockitoExtension.class)
class ORSLocationChangedDistIDPTest {

    @InjectMocks private ORSLocationChangedDistIDP locationChangedDistIDP;

    private static MockedStatic<SNSClientConfig> snsClientConfig;

    @Mock private SnsClient snsClient;

    @BeforeAll
    static void beforeClass() {
        snsClientConfig = Mockito.mockStatic(SNSClientConfig.class);
    }

    @AfterAll
    static void afterClass() {
        Mockito.framework().clearInlineMocks();
    }

    @BeforeEach
    void setup() {
        snsClientConfig.when(SNSClientConfig::getSNSClient).thenReturn(snsClient);
    }


    @Test
    void getPartnerCodeConstants_thenReturnIDP() {
        assertEquals(DistORSConstants.IDP, locationChangedDistIDP.getPartnerCodeConstants());
    }

    @Test
    void getApplicationName_thenReturnIDP() {
        assertEquals(DistORSConstants.ORS_LOCATIONCHANGED_DIST_IDP, locationChangedDistIDP.getApplicationName());
    }

    @Test
    @SneakyThrows
    void setAdditionalHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        locationChangedDistIDP.setAdditionalHttpHeaders(httpHeaders);
        assertNull(httpHeaders.get(DistORSConstants.USER_AGENT_KEY));
    }
}
