//package com.upside.api.service;
//
//import javax.naming.CommunicationException;
//
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.LinkedMultiValueMap;
//
//import com.nimbusds.oauth2.sdk.token.AccessToken;
//
//public class ProviderService {
//
//	public AccessToken getAccessToken(String code, String provider) {
//	    HttpHeaders httpHeaders = new HttpHeaders();
//	    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//	    OAuthRequest oAuthRequest = oAuthRequestFactory.getRequest(code, provider);
//	    HttpEntity<LinkedMultiValueMap<String, String>> request = new HttpEntity<>(oAuthRequest.getMap(), httpHeaders);
//
//	    ResponseEntity<String> response = restTemplate.postForEntity(oAuthRequest.getUrl(), request, String.class);
//	    try {
//	        if (response.getStatusCode() == HttpStatus.OK) {
//	            return gson.fromJson(response.getBody(), AccessToken.class);
//	        }
//	    } catch (Exception e) {
//	        throw new CommunicationException();
//	    }
//	    throw new CommunicationException();
//	}
//}
