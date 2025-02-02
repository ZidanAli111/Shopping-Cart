package com.ielts.cmds.integration;
import static com.ielts.cmds.integration.constants.DistORSConstants.CORRELATIONID;
import static com.ielts.cmds.integration.constants.DistORSConstants.EVENT_DATE_TIME;
import static com.ielts.cmds.integration.constants.DistORSConstants.PARTNER_CODE;
import static com.ielts.cmds.integration.constants.DistORSConstants.TRANSACTIONID;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import software.amazon.awssdk.services.sns.SnsClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ielts.cmds.api.evt_112.LocationChanged;
import com.ielts.cmds.integration.mapper.EventMapper;
import com.ielts.cmds.ors.common.integration.int455.LocationV1;
import com.ielts.cmds.v2.security.clients.AuthenticationClient;
import com.ielts.cmds.v2.security.exception.InvalidClientException;
import com.ielts.cmds.v2.security.exception.TokenNotReceivedException;
import com.ielts.cmds.v2.security.factory.EnvironmentAwareAuthenticationClientFactory;
import com.ielts.cmds.v2.serialization.lambda.config.SNSClientConfig;
import com.ielts.cmds.v2.serialization.lambda.utils.ThreadLocalHeaderContext;

import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SystemStubsExtension.class)
class AbstractORSLocationChangedDistTest {

    private AbstractORSLocationChangedDist orsLocationChangedDist;

    @Mock private RestTemplate restTemplate;
    @Mock private EventMapper eventMapper;
    @Mock private EnvironmentAwareAuthenticationClientFactory authenticationFactory;

    @Mock private AuthenticationClient authenticationClient;

    @Mock private SnsClient snsClient;

    private LocationChanged locationChanged;

    private LocationV1 location;

    private static MockedStatic<SNSClientConfig> snsClientConfig;

    private String externalUrl = "test.url";

    private HttpHeaders httpHeaders;

    @Captor private ArgumentCaptor<HttpEntity<LocationV1>> entity;

    @SystemStub private EnvironmentVariables environmentVariables;

    @BeforeAll
    static void beforeClass() {
        snsClientConfig = Mockito.mockStatic(SNSClientConfig.class);
    }

    @AfterAll
    static void afterClass() {
        Mockito.framework().clearInlineMocks();
    }

    @BeforeEach
    void setUp() {
        SQSEventBodySetup.setHeaderContext();
        httpHeaders = SQSEventBodySetup.getHttpHeaders();
        environmentVariables.set("callback_url", externalUrl);
        AbstractORSLocationChangedDist orsLocationChangedDistMock =  new AbstractORSLocationChangedDist() {
            @Override
            protected String getPartnerCodeConstants() {
                return "test";
            }

            @Override
            protected String getApplicationName() {
                return "locationchanged-dist";
            }

            @Override
            protected void setAdditionalHttpHeaders(HttpHeaders httpHeaders) {
            }
        };
        snsClientConfig.when(SNSClientConfig::getSNSClient).thenReturn(snsClient);
        ReflectionTestUtils.setField(orsLocationChangedDistMock,"authenticationClientFactory",authenticationFactory);
        ReflectionTestUtils.setField(orsLocationChangedDistMock,"authenticationClient",authenticationClient);
        locationChanged = SQSEventBodySetup.getLocationChanged();
        orsLocationChangedDist = spy(orsLocationChangedDistMock);
    }

    @Test
    void processIsCalled_ExpectNoException() {
        doReturn(eventMapper).when(orsLocationChangedDist).getEventMapper();
        doReturn(location).when(eventMapper).mapLocationResponse(locationChanged);
        doNothing().when(orsLocationChangedDist).putRequestToExternalAPI(location, externalUrl);
        assertDoesNotThrow(()->orsLocationChangedDist.processRequest(locationChanged));
    }

    @Test
    void processIsCalledWithInvalidPartnerCode_ExpectNoException() {
        ThreadLocalHeaderContext.getContext().setPartnerCode("invalid");
        assertDoesNotThrow(()->orsLocationChangedDist.processRequest(locationChanged));
        verify(orsLocationChangedDist, never()).putRequestToExternalAPI(location, externalUrl);
        verify(orsLocationChangedDist, never()).getEventMapper();
        verify(eventMapper, never()).mapLocationResponse(locationChanged);
    }

    @Test
    void getEventMapper_ExpectNonNullObject() {
        assertNotNull(orsLocationChangedDist.getEventMapper());
    }

    @Test
    void postRequestToExternalAPIIsCalledOk_ExpectRestCallWithOk() throws JsonProcessingException, CertificateException, KeyStoreException, InvalidClientException, TokenNotReceivedException {
        doNothing().when(orsLocationChangedDist).getAuthenticationClient();
        doReturn(restTemplate).when(authenticationClient).getRestTemplate();
        doReturn(httpHeaders).when(orsLocationChangedDist).getHttpHeaders();
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.OK);
        doReturn(response).when(restTemplate).exchange(ArgumentMatchers.eq(externalUrl),ArgumentMatchers.eq(HttpMethod.PUT),
                ArgumentMatchers.any(), ArgumentMatchers.eq(String.class));
        assertDoesNotThrow(()->orsLocationChangedDist.putRequestToExternalAPI(location, externalUrl));
        verify(restTemplate).exchange(ArgumentMatchers.eq(externalUrl),ArgumentMatchers.eq(HttpMethod.PUT),
                entity.capture(), ArgumentMatchers.eq(String.class));
        HttpEntity<LocationV1> actual = entity.getValue();
        assertEquals(httpHeaders, actual.getHeaders());
        assertEquals(location, actual.getBody());
    }

    @Test
    void postRequestToExternalAPIIsCalledServerError_ExpectRestCallWithException()
            throws JsonProcessingException, CertificateException, KeyStoreException,
            InvalidClientException, TokenNotReceivedException {
        doNothing().when(orsLocationChangedDist).getAuthenticationClient();
        doReturn(restTemplate).when(authenticationClient).getRestTemplate();
        doReturn(httpHeaders).when(orsLocationChangedDist).getHttpHeaders();
        doThrow(HttpServerErrorException.class).when(restTemplate).exchange(ArgumentMatchers.eq(externalUrl),ArgumentMatchers.eq(HttpMethod.PUT),
                ArgumentMatchers.any(), ArgumentMatchers.eq(String.class));
        assertThrows(HttpServerErrorException.class, ()->orsLocationChangedDist.putRequestToExternalAPI(location, externalUrl));
        verify(restTemplate).exchange(ArgumentMatchers.eq(externalUrl),ArgumentMatchers.eq(HttpMethod.PUT),
                entity.capture(), ArgumentMatchers.eq(String.class));
        HttpEntity<LocationV1> actual = entity.getValue();
        assertEquals(httpHeaders, actual.getHeaders());
        assertEquals(location, actual.getBody());
    }

    @Test
    void postRequestToExternalAPIIsCalledBadRequest_ExpectRestCallWithNoException()
            throws JsonProcessingException, CertificateException, KeyStoreException,
            InvalidClientException, TokenNotReceivedException {
        doNothing().when(orsLocationChangedDist).getAuthenticationClient();
        doReturn(restTemplate).when(authenticationClient).getRestTemplate();
        doReturn(httpHeaders).when(orsLocationChangedDist).getHttpHeaders();
        doThrow(HttpClientErrorException.class).when(restTemplate).exchange(ArgumentMatchers.eq(externalUrl),ArgumentMatchers.eq(HttpMethod.PUT),
                ArgumentMatchers.any(), ArgumentMatchers.eq(String.class));
        assertDoesNotThrow(()->orsLocationChangedDist.putRequestToExternalAPI(location, externalUrl));
        verify(restTemplate).exchange(ArgumentMatchers.eq(externalUrl),ArgumentMatchers.eq(HttpMethod.PUT),
                entity.capture(), ArgumentMatchers.eq(String.class));
        HttpEntity<LocationV1> actual = entity.getValue();
        assertEquals(httpHeaders, actual.getHeaders());
        assertEquals(location, actual.getBody());
    }

    @ParameterizedTest
    @MethodSource("getTestDataForAuthenticationClientFactoryException")
    void postRequestToExternalAPIIsCalledWithException_ExpectNoRestCallAndNoException(Class<Exception> exception) throws JsonProcessingException, CertificateException, KeyStoreException, InvalidClientException {
        doThrow(exception).when(orsLocationChangedDist).getAuthenticationClient();
        assertDoesNotThrow(()->orsLocationChangedDist.putRequestToExternalAPI(location, externalUrl));
        verify(restTemplate, never()).exchange(ArgumentMatchers.eq(externalUrl),ArgumentMatchers.eq(HttpMethod.PUT),
                entity.capture(), ArgumentMatchers.eq(String.class));

    }

    @ParameterizedTest
    @MethodSource("getTestDataForAuthenticationClientAccessTokenException")
    void postRequestToExternalAPIIsCalledWithAuthenticationClientAccessTokenException_ExpectNoRestCallAndNoException(
            Class<Exception> exception)
            throws JsonProcessingException, CertificateException, KeyStoreException,
            InvalidClientException, TokenNotReceivedException {
        doNothing().when(orsLocationChangedDist).getAuthenticationClient();
        doThrow(exception).when(orsLocationChangedDist).getHttpHeaders();
        assertDoesNotThrow(()->orsLocationChangedDist.putRequestToExternalAPI(location, externalUrl));
        verify(restTemplate, never()).exchange(ArgumentMatchers.eq(externalUrl),ArgumentMatchers.eq(HttpMethod.PUT),
                entity.capture(), ArgumentMatchers.eq(String.class));
    }

    @Test
    void getAuthenticationClientWithObjectSet_DoNotExpectCall() throws JsonProcessingException, CertificateException, KeyStoreException, InvalidClientException {
        ReflectionTestUtils.setField(orsLocationChangedDist,"authenticationClient",authenticationClient);
        orsLocationChangedDist.getAuthenticationClient();
        verify(authenticationFactory, never()).getAuthenticationClient("test");
    }

    @Test
    void getAuthenticationClientWithoutObjectSet_DoNotExpectCall() throws JsonProcessingException, CertificateException, KeyStoreException, InvalidClientException {
        ReflectionTestUtils.setField(orsLocationChangedDist,"authenticationClient",null);
        orsLocationChangedDist.getAuthenticationClient();
        verify(authenticationFactory).getAuthenticationClient("test");
    }

    @Test
    void getNonNullUuidStringWithNullUuid_ExpectEmptyString() {
        assertEquals("", orsLocationChangedDist.getNonNullUuidString(null));
    }

    @Test
    void getNonNullUuidStringWithNonNullUuid_ExpectUUIDString() {
        UUID uuid = UUID.randomUUID();
        assertEquals(uuid.toString(), orsLocationChangedDist.getNonNullUuidString(uuid));
    }

    @Test
    void getNonNullDateTimeStringWithNullDateTime_ExpectEmptyString() {
        assertEquals("", orsLocationChangedDist.getNonNullDateTimeString(null));
    }

    @Test
    void getNonNullDateTimeStringWithNullDateTime_ExpectDateTimeString() {
        LocalDateTime dateTime = LocalDateTime.now(ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        assertEquals(dateTime.format(formatter), orsLocationChangedDist.getNonNullDateTimeString(dateTime));
    }

    @Test
    void getTopicName_ExpectNull() {
        assertNull(orsLocationChangedDist.getTopicName());
    }

    @Test
    void getHttpHeaders_ExpectHeadersToBeSet() throws JsonProcessingException, CertificateException, KeyStoreException, TokenNotReceivedException {
        doReturn("token").when(authenticationClient).getAccessToken();
        doReturn("Authorization").when(authenticationClient).getAuthorizationHeaderName();
        HttpHeaders actual = orsLocationChangedDist.getHttpHeaders();
        assertEquals(ThreadLocalHeaderContext.getContext().getTransactionId().toString(), actual.get(TRANSACTIONID).get(0));
        assertEquals(ThreadLocalHeaderContext.getContext().getCorrelationId().toString(), actual.get(CORRELATIONID).get(0));
        assertEquals(ThreadLocalHeaderContext.getContext().getPartnerCode(), actual.get(PARTNER_CODE).get(0));
        assertEquals("", actual.get(EVENT_DATE_TIME).get(0));
        assertEquals("token", actual.get("Authorization").get(0));
    }



    static Stream<Arguments> getTestDataForAuthenticationClientFactoryException() {
        return Stream.of(
                Arguments.of(JsonProcessingException.class),
                Arguments.of(InvalidClientException.class),
                Arguments.of(CertificateException.class),
                Arguments.of(KeyStoreException.class));
    }


    static Stream<Arguments> getTestDataForAuthenticationClientAccessTokenException() {
        return Stream.of(
                Arguments.of(JsonProcessingException.class),
                Arguments.of(CertificateException.class),
                Arguments.of(KeyStoreException.class),
                Arguments.of(TokenNotReceivedException.class));
    }



}
