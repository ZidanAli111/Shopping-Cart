package com.ielts.cmds.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
class ORSLocationChangedDistBCTest {

    @InjectMocks private ORSLocationChangedDistBC locationChangedDistBC;

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
    void getPartnerCodeConstants_thenReturnBC() {
        assertEquals(DistORSConstants.BC, locationChangedDistBC.getPartnerCodeConstants());
    }

    @Test
    void getApplicationName_thenReturnBC() {
        assertEquals(DistORSConstants.ORS_LOCATIONCHANGED_DIST_BC, locationChangedDistBC.getApplicationName());
    }

    @Test
    @SneakyThrows
    void setAdditionalHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        locationChangedDistBC.setAdditionalHttpHeaders(httpHeaders);
        assertNotNull(httpHeaders.get(DistORSConstants.USER_AGENT_KEY));
    }
}
