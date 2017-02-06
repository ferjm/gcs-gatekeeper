package com.example.helloworld;

import com.google.appengine.api.appidentity.AppIdentityService;
import com.google.appengine.api.appidentity.AppIdentityServiceFactory;

import com.google.apphosting.api.ApiProxy;

import java.io.DataOutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;

public class SessionUri {
    private final String BUCKET_NAME = "bucket-takeafile-com";

    private final AppIdentityService mAppIdentity;
    private String mFile;

    public SessionUri(String file) {
        mAppIdentity = AppIdentityServiceFactory.getAppIdentityService();
        mFile = file;
    }

    public String getData() throws Exception {
        ArrayList<String> scopes = new ArrayList<String>();
        scopes.add("https://www.googleapis.com/auth/devstorage.read_write");
        AppIdentityService.GetAccessTokenResult accessToken = mAppIdentity.getAccessToken(scopes);

        ApiProxy.Environment env = ApiProxy.getCurrentEnvironment();
        String origin = "" + env.getAttributes().get("com.google.appengine.runtime.default_version_hostname");

        URL url = new URL("https://storage.googleapis.com/" + BUCKET_NAME + "/" + this.mFile);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Origin", origin);
        connection.addRequestProperty("Authorization", "Bearer " + accessToken.getAccessToken());
        connection.setRequestProperty("Content-Lenght", "0");
        connection.setRequestProperty("x-goog-resumable", "start");

        String stringResponse = "";
        switch (connection.getResponseCode()) {
        case HttpURLConnection.HTTP_CREATED:
            stringResponse = connection.getHeaderField("Location");
            break;
        default:
            stringResponse = Integer.toString(connection.getResponseCode());
            break;
        }

        return stringResponse;
    }
}
