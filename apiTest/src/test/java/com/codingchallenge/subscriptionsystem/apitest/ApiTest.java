package com.codingchallenge.subscriptionsystem.apitest;


import com.codingchallenge.subscriptionsystem.common.messages.api.ApiCreateSubscriptionRequest;
import com.codingchallenge.subscriptionsystem.common.messages.api.ApiCreateSubscriptionResponse;
import com.codingchallenge.subscriptionsystem.common.model.IUser;
import com.codingchallenge.subscriptionsystem.common.model.Newsletter;
import com.codingchallenge.subscriptionsystem.common.model.User;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ApiTest {

    private HttpHeaders headersBasicUser;
    private HttpHeaders headersFrontendUser;

    private final static String URL_ENDPOINT = "http://localhost:8080/api/subscriptions";

    private static HttpHeaders createHeaders(String username, String password){
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }

    @Before
    public void init(){
        headersBasicUser = createHeaders("BASIC_USER", "BASIC_USER");
        headersFrontendUser = createHeaders("FRONTEND_USER", "FRONTEND_USER");
    }

    private static ResponseEntity<ApiCreateSubscriptionResponse> postRequestWithNoHeaders(ApiCreateSubscriptionRequest apiCreateSubscriptionRequest, HttpHeaders headers){
        return new RestTemplate().exchange
                (URL_ENDPOINT, HttpMethod.POST, new HttpEntity(apiCreateSubscriptionRequest,headers), ApiCreateSubscriptionResponse.class);
    }

    private static ResponseEntity<ApiCreateSubscriptionResponse> postRequestWithNoHeaders(ApiCreateSubscriptionRequest apiCreateSubscriptionRequest){
        return new RestTemplate().exchange
                (URL_ENDPOINT, HttpMethod.POST, new HttpEntity(apiCreateSubscriptionRequest), ApiCreateSubscriptionResponse.class);
    }

    private static User buildUser(String email, String firstName) {
        return new User(email, new Date(), true, firstName, IUser.Gender.MALE);
    }

    private static Newsletter buildNewsletter(String newsletterId) {
        return new Newsletter(newsletterId, "newsleter 1");
    }

    @Test(timeout = 15000, expected=HttpClientErrorException.class)
    public void createOneDuplicatedUserEmailNewsletterId(){
        ResponseEntity<ApiCreateSubscriptionResponse> apiCreateSubscriptionResponse =
                postRequestWithNoHeaders(new ApiCreateSubscriptionRequest(buildUser("user01@gmail.com", "User 01"),
                        buildNewsletter("newsletter01")), headersFrontendUser);
        assertEquals(apiCreateSubscriptionResponse.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(apiCreateSubscriptionResponse.getBody());
    }

    @Test(timeout = 15000, expected=HttpServerErrorException.class)
    public void createOneNonExistentNewsletter(){
        ResponseEntity<ApiCreateSubscriptionResponse> apiCreateSubscriptionResponse =
                postRequestWithNoHeaders(new ApiCreateSubscriptionRequest(buildUser("user01@gmail.com", "User 01"),
                        buildNewsletter("newsletter01xx")), headersFrontendUser);
        assertEquals(apiCreateSubscriptionResponse.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(apiCreateSubscriptionResponse.getBody());
    }

    @Test(timeout = 15000, expected=HttpClientErrorException.class)
    public void createOneInvalidInput_NotValidEmail(){
        ResponseEntity<ApiCreateSubscriptionResponse> apiCreateSubscriptionResponse =
                postRequestWithNoHeaders(new ApiCreateSubscriptionRequest(buildUser("user01gmail.com", "User 01"),
                        buildNewsletter("newsletter01")), headersFrontendUser);
        assertEquals(apiCreateSubscriptionResponse.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(apiCreateSubscriptionResponse.getBody());
    }

    @Test(timeout = 15000, expected=HttpClientErrorException.class)
    public void createOneInvalidInput_EmptyNewsletterId(){
        ResponseEntity<ApiCreateSubscriptionResponse> apiCreateSubscriptionResponse =
                postRequestWithNoHeaders(new ApiCreateSubscriptionRequest(buildUser("user01gmail.com", "User 01"),
                        buildNewsletter("")), headersFrontendUser);
        assertEquals(apiCreateSubscriptionResponse.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(apiCreateSubscriptionResponse.getBody());
    }

    @Test(timeout = 15000, expected=HttpClientErrorException.class)
    public void createOneInvalidInput3_emptyUserEmail(){
        ResponseEntity<ApiCreateSubscriptionResponse> apiCreateSubscriptionResponse =
                postRequestWithNoHeaders(new ApiCreateSubscriptionRequest(buildUser("", "User 01"),
                        buildNewsletter("user01@gmail.com")), headersFrontendUser);
        assertEquals(apiCreateSubscriptionResponse.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(apiCreateSubscriptionResponse.getBody());
    }

    @Test(timeout = 15000)
    public void createOneNew(){
        ResponseEntity<ApiCreateSubscriptionResponse> apiCreateSubscriptionResponse =
                postRequestWithNoHeaders(new ApiCreateSubscriptionRequest(buildUser("usernew"+String.valueOf(new Random().nextInt(9999))+"@gmail.com", "User 01"),
                        buildNewsletter("newsletter01")), headersFrontendUser);
        assertEquals(apiCreateSubscriptionResponse.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(apiCreateSubscriptionResponse.getBody());
    }

    @Test(timeout = 15000, expected = HttpClientErrorException.class)
    public void createWithoutHeaders(){
        ResponseEntity<ApiCreateSubscriptionResponse> apiCreateSubscriptionResponse =
                postRequestWithNoHeaders(new ApiCreateSubscriptionRequest(buildUser("usernew"+String.valueOf(new Random().nextInt(9999))+"@gmail.com", "User 01"),
                        buildNewsletter("newsletter01")));
        assertEquals(apiCreateSubscriptionResponse.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(apiCreateSubscriptionResponse.getBody());
    }

    @Test(timeout = 15000, expected = HttpClientErrorException.class)
    public void createWithBasicUser(){
        ResponseEntity<ApiCreateSubscriptionResponse> apiCreateSubscriptionResponse =
                postRequestWithNoHeaders(new ApiCreateSubscriptionRequest(buildUser("usernew"+String.valueOf(new Random().nextInt(9999))+"@gmail.com", "User 01"),
                        buildNewsletter("newsletter01")), headersBasicUser);
        assertEquals(apiCreateSubscriptionResponse.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(apiCreateSubscriptionResponse.getBody());
    }

}
