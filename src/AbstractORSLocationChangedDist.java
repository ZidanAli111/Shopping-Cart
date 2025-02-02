package com.ielts.cmds.integration;

import static com.ielts.cmds.integration.constants.DistORSConstants.CALLBACK_URL;
import static com.ielts.cmds.integration.constants.DistORSConstants.CORRELATIONID;
import static com.ielts.cmds.integration.constants.DistORSConstants.EVENT_DATE_TIME;
import static com.ielts.cmds.integration.constants.DistORSConstants.PARTNER_CODE;
import static com.ielts.cmds.integration.constants.DistORSConstants.TRANSACTIONID;

import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ielts.cmds.api.evt_112.LocationChanged;
import com.ielts.cmds.integration.mapper.EventMapper;
import com.ielts.cmds.ors.common.integration.int455.LocationV1;
import com.ielts.cmds.v2.security.clients.AuthenticationClient;
import com.ielts.cmds.v2.security.exception.InvalidClientException;
import com.ielts.cmds.v2.security.exception.TokenNotReceivedException;
import com.ielts.cmds.v2.security.factory.EnvironmentAwareAuthenticationClientFactory;
import com.ielts.cmds.v2.serialization.lambda.AbstractLambda;
import com.ielts.cmds.v2.serialization.lambda.config.DisablePublishToTopic;
import com.ielts.cmds.v2.serialization.lambda.config.ExternalOutputType;
import com.ielts.cmds.v2.serialization.lambda.utils.HeaderContext;
import com.ielts.cmds.v2.serialization.lambda.utils.ThreadLocalHeaderContext;

import lombok.extern.slf4j.Slf4j;

/**
 * This class serves the purpose of handling ors ds response to External system(ORS - British
 * Council) It reads booking events specific to british council and posts it back on callbackURL
 */

@Slf4j
@DisablePublishToTopic
public abstract class AbstractORSLocationChangedDist extends AbstractLambda<LocationChanged, ExternalOutputType> {

    private final EnvironmentAwareAuthenticationClientFactory authenticationClientFactory;

    private AuthenticationClient authenticationClient;

    protected AbstractORSLocationChangedDist() {
        this.authenticationClientFactory = new EnvironmentAwareAuthenticationClientFactory();
    }

    @Override
    protected ExternalOutputType processRequest(LocationChanged locationChanged) {

        if (ThreadLocalHeaderContext.getContext().getPartnerCode().equals(getPartnerCodeConstants())) {
            final EventMapper eventMapper = getEventMapper();
            LocationV1 response = eventMapper.mapLocationResponse(locationChanged);
            log.debug("ORSLocationChangedResponse before RestTemplate call: {} ", response);
            putRequestToExternalAPI(response, System.getenv(CALLBACK_URL));
        } else {
            log.warn("Incoming partnerCode:{} does not match expected partner code:{}",
                    ThreadLocalHeaderContext.getContext().getPartnerCode(), getPartnerCodeConstants());
        }
        return null;
    }

    @Override
    protected String getTopicName() {
        return null;
    }

    void putRequestToExternalAPI(
            final LocationV1 resBody, final String externalUrl) {
        try {
            getAuthenticationClient();
            final HttpHeaders eventHeaders = getHttpHeaders();
            final HttpEntity<LocationV1> eventEntity = new HttpEntity<>(resBody, eventHeaders);
            log.debug("ExternalUrl : {} ; eventEntity : {} ; eventHeader : {}", externalUrl, eventEntity, eventHeaders);
            final ResponseEntity<String> response = authenticationClient.getRestTemplate().exchange(externalUrl, HttpMethod.PUT, eventEntity, String.class);
            log.trace("Response Code: {}" ,response.getStatusCode());
            log.info("Message processed successfully");

        } catch(HttpClientErrorException | JsonProcessingException| CertificateException| KeyStoreException| TokenNotReceivedException | InvalidClientException e){
            log.warn("Client Exception on processing event :", e);
        }
    }
    EventMapper getEventMapper() {
        return new EventMapper();
    }


    /**
     * constructs httpheader based on provided eventheader
     * @param eventHeader
     * @return
     * @throws TokenNotReceivedException
     * @throws KeyStoreException
     * @throws CertificateException
     * @throws JsonProcessingException
     * @throws Exception
     */
    HttpHeaders getHttpHeaders() throws JsonProcessingException, CertificateException, KeyStoreException, TokenNotReceivedException {
        final HeaderContext eventHeader = ThreadLocalHeaderContext.getContext();
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set(TRANSACTIONID, getNonNullUuidString(eventHeader.getTransactionId()));
        httpHeaders.set(CORRELATIONID, getNonNullUuidString(eventHeader.getCorrelationId()));
        httpHeaders.set(PARTNER_CODE, eventHeader.getPartnerCode());
        httpHeaders.set(
                EVENT_DATE_TIME, getNonNullDateTimeString(eventHeader.getEventDateTime()));
        httpHeaders.set(authenticationClient.getAuthorizationHeaderName(), authenticationClient.getAccessToken());
        setAdditionalHttpHeaders(httpHeaders);
        return httpHeaders;
    }

    public void getAuthenticationClient() throws InvalidClientException, CertificateException, KeyStoreException, JsonProcessingException {
        if (authenticationClient == null) {
            authenticationClient = authenticationClientFactory.getAuthenticationClient(ThreadLocalHeaderContext.getContext().getPartnerCode());
        }
    }

    String getNonNullUuidString(UUID uuid) {
        if(uuid != null) {
            return uuid.toString();
        }
        else {
            return "";
        }
    }

    String getNonNullDateTimeString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        if(dateTime != null) {
            return dateTime.format(formatter);
        } else {
            return "";
        }
    }

    protected abstract void setAdditionalHttpHeaders(HttpHeaders httpHeaders);

    protected abstract String getPartnerCodeConstants();

    protected abstract String getApplicationName();
}