package etu.bassem.facebook;

import com.restfb.DefaultFacebookClient;
import com.restfb.scope.ScopeBuilder;
import com.restfb.scope.UserDataPermissions;

public class LoggedInFacebookClient extends DefaultFacebookClient {

	private LoggedInFacebookClient client;

	public LoggedInFacebookClient(String appId, String appSecret) {
		
		
		ScopeBuilder scopeBuilder = new ScopeBuilder();
		scopeBuilder.addPermission(UserDataPermissions.USER_BIRTHDAY);
		scopeBuilder.addPermission(UserDataPermissions.USER_ABOUT_ME);
		
		AccessToken accessToken = this.obtainAppAccessToken(appId, appSecret);
		this.accessToken = accessToken.getAccessToken();
		this.client = this;
	}

}