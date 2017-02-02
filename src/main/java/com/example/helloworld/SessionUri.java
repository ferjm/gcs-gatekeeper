package com.example.helloworld;

import com.google.appengine.api.appidentity.AppIdentityService;
import com.google.appengine.api.appidentity.AppIdentityServiceFactory;

import java.io.DataOutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;

public class SessionUri {
    private final String BACKET_NAME = "bucket-takeafile-com";

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
	
	URL url = new URL("https://www.googleapis.com/upload/storage/v1/b/" + BACKET_NAME + "/o?uploadType=resumable");
	HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	
	connection.setRequestMethod("POST");
	connection.addRequestProperty("Authorization", "Bearer " + accessToken.getAccessToken());
	connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

	String data = "{\"name\":\""+ mFile + "\"}";
	connection.setDoOutput(true);
	DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
	dataOutputStream.writeBytes(data);
	dataOutputStream.flush();
	dataOutputStream.close();
	
	String stringResponse = "";
	switch (connection.getResponseCode()) {
	case HttpURLConnection.HTTP_OK:
	    stringResponse = connection.getHeaderField("Location");
	    break;
	default:
	    break;
	}
	
	return stringResponse;
    }
}
