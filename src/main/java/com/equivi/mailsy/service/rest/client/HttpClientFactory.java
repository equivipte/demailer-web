package com.equivi.mailsy.service.rest.client;


import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;


@Component
public class HttpClientFactory {

    public HttpComponentsClientHttpRequestFactory getHttpComponentFactory(String userName, String password) {
        Credentials apiCredentials = new UsernamePasswordCredentials(userName, password);

        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(AuthScope.ANY, apiCredentials);

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setDefaultCredentialsProvider(credsProvider);

        HttpClient httpAsyncClient = httpClientBuilder.build();

        return new HttpComponentsClientHttpRequestFactory(httpAsyncClient);
    }

}
