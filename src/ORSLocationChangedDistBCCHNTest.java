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
class ORSLocationChangedDistBCCHNTest {

    @InjectMocks private ORSLocationChangedDistBCCHN locationChangedDistBCCHN;

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
    void getPartnerCodeConstants_thenReturnBCCHN() {
        assertEquals(DistORSConstants.BC_CHN, locationChangedDistBCCHN.getPartnerCodeConstants());
    }

    @Test
    void getApplicationName_thenReturnBCCHN() {
        assertEquals(DistORSConstants.ORS_LOCATIONCHANGED_DIST_BCCHN, locationChangedDistBCCHN.getApplicationName());
    }

    @Test
    @SneakyThrows
    void setAdditionalHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        locationChangedDistBCCHN.setAdditionalHttpHeaders(httpHeaders);
        assertNull(httpHeaders.get(DistORSConstants.USER_AGENT_KEY));
    }
}
