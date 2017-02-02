/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.helloworld;

import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;

import java.io.IOException;

import javax.inject.Named;

// [START header]
/** An endpoint class we are exposing */
@Api(name = "gcsgatekeeper",
    version = "v1",
    namespace = @ApiNamespace(ownerDomain = "helloworld.example.com",
        ownerName = "helloworld.example.com",
        packagePath = ""),
    clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID},
    audiences = {Constants.ANDROID_AUDIENCE})
// [END header]

public class GCSGatekeeper {

  // [START sessionuris]
  /** A simple endpoint method that creates the session URI to be used in PUT
      requests to upload data. */
  @ApiMethod(
	     name = "sessionuris",
	     httpMethod = ApiMethod.HttpMethod.POST
	     )
  public SessionUri sessionuris(@Named("file") String file) {
    return new SessionUri(file);
  }
  //[END signeduris]

}
