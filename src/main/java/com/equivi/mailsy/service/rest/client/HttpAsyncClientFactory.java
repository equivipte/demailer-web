package com.equivi.mailsy.service.rest.client;


import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.stereotype.Component;


@Component
public class HttpAsyncClientFactory {

    public HttpComponentsAsyncClientHttpRequestFactory getHttpComponentAsyncFactory(String userName, String password) {
        Credentials apiCredentials = new UsernamePasswordCredentials(userName, password);

        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(AuthScope.ANY, apiCredentials);

        HttpAsyncClientBuilder httpAsyncClientBuilder = HttpAsyncClientBuilder.create();
        httpAsyncClientBuilder.setDefaultCredentialsProvider(credsProvider);

        CloseableHttpAsyncClient httpAsyncClient = httpAsyncClientBuilder.build();

        return new HttpComponentsAsyncClientHttpRequestFactory(httpAsyncClient);
    }

}
