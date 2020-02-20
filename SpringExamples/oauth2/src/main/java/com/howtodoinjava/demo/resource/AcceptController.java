package com.howtodoinjava.demo.resource;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.LogRecord;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.View;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
<<<<<<< HEAD
=======
import org.springframework.security.core.userdetails.User;
<<<<<<< HEAD

=======
>>>>>>> 1353211f2eadaad73d9114afc6e86e8c44f7bf60
import org.springframework.security.oauth2.common.exceptions.UnsupportedResponseTypeException;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
>>>>>>> 6f97e768786fa57dd0034028740731908308c0a7
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;


@Controller
public class AcceptController {
	
	final String AUTHORIZATION_REQUEST_ATTR_NAME = "authorizationRequest";
	final String redirectConsentPage = "http://localhost:8082/consentPage";

/*
	@Resource(name="authenticationManager")
	private AuthenticationManager authManager;
*/

	@RequestMapping("/acceptPage")
	public String acceptPage(HttpServletRequest req) {

		/*	String user="volante";
			String pass="123456";

			UsernamePasswordAuthenticationToken authReq
					= new UsernamePasswordAuthenticationToken(user, pass);
			Authentication auth = authManager.authenticate(authReq);

			SecurityContext sc = SecurityContextHolder.getContext();
			sc.setAuthentication(auth);
			HttpSession session = req.getSession(true);
			session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);*/

	    return "redirect:" + redirectConsentPage;
	}
	
<<<<<<< HEAD
//	@RequestMapping("/authorize-success")
//	public void authCode() {
//
//		Map<String, String> approvalParameters = new HashMap<String, String>();
//		approvalParameters.put("user_oauth_approval", "true");
//		approvalParameters.put("scope.read_profile_info", "true");
//		approvalParameters.put("authorize", "Authorize");
//
//		Set<String> scope = new HashSet<String>();
//		scope.add("read_profile_info");
//
//		Map<String, String> requestParameters = new HashMap<String, String>();
//		requestParameters.put("response_type", "code");
//		requestParameters.put("client_id", "clientapp");
//		requestParameters.put("scope", "read_profile_info");
//
//		Set<String> responseTypes = new HashSet<String>();
//		responseTypes.add("code");
//
//		Set<String> resourceIds = new HashSet<String>();
//		resourceIds.add("oauth2-resource");
//
//		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
//		SimpleGrantedAuthority simpleAuth = new SimpleGrantedAuthority("READ_ONLY_CLIENT");
//		authorities.add(simpleAuth);
//
//		AuthorizationRequest authReq = new AuthorizationRequest(requestParameters, approvalParameters, "clientapp", scope, resourceIds, authorities, true, "null", "https://www.volantetech.com/",
//		        responseTypes);
//		Map<String,Serializable> extensions = new HashMap<String,Serializable>();
//		authReq.setExtensions(extensions);
//		authReq.setApprovalParameters(approvalParameters);
//
//		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
//
//		Set<GrantedAuthority> authoritiesPrincipal = new HashSet<GrantedAuthority>();
//		authoritiesPrincipal.add(simpleGrantedAuthority);
//		User user = new User("volante", "123456", true, true, true, true, authoritiesPrincipal);
//
//		UsernamePasswordAuthenticationToken authUser = new UsernamePasswordAuthenticationToken(user, null, authoritiesPrincipal);
//
//		CustomAuthorizationEndpoint authorizationCodeResponse = new CustomAuthorizationEndpoint();
//		return authorizationCodeResponse.getAuthorizationCodeResponse(authReq, (Authentication) authUser);
//
//	}
=======
	@RequestMapping("/authorize-success")
	public View authCode() {
		
		Map<String, String> approvalParameters = new HashMap<String, String>();
		approvalParameters.put("user_oauth_approval", "true");
		approvalParameters.put("scope.read_profile_info", "true");
		approvalParameters.put("authorize", "Authorize");
		//authReq.setApprovalParameters(approvalParameters);
		
		//authReq.setRedirectUri("https://www.volantetech.com/");
		//authReq.setClientId("clientapp");
		
		Set<String> scope = new HashSet<String>();
		//authReq.setScope(listScope);
		scope.add("read_profile_info");
		
		Map<String, String> requestParameters = new HashMap<String, String>();
		requestParameters.put("response_type", "code");
		requestParameters.put("client_id", "clientapp");
		requestParameters.put("scope", "read_profile_info");
		//authReq.setRequestParameters(requestParameters);
		
		Set<String> responseTypes = new HashSet<String>();
		responseTypes.add("code");
		//authReq.setResponseTypes(responseTypes);
		
		Set<String> resourceIds = new HashSet<String>();
		resourceIds.add("oauth2-resource");
		//authReq.setResourceIds(resourceIds);
		
		//authReq.setApproved(true);
		
		//Map<String,Serializable> extensions = new HashMap<String,Serializable>();
		//authReq.setExtensions(extensions);
		
		//String state;
		//authReq.setState("null");
		
		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>(); 
		SimpleGrantedAuthority simpleAuth = new SimpleGrantedAuthority("READ_ONLY_CLIENT");
		authorities.add(simpleAuth);
		//authReq.setAuthorities(authorities);
		
		AuthorizationRequest authReq = new AuthorizationRequest(requestParameters, approvalParameters, "clientapp", scope, resourceIds, authorities, true, "null", "https://www.volantetech.com/",
		        responseTypes);
		Map<String,Serializable> extensions = new HashMap<String,Serializable>();
		authReq.setExtensions(extensions);
		
		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
		
		Set<GrantedAuthority> authoritiesPrincipal = new HashSet<GrantedAuthority>();
		authoritiesPrincipal.add(simpleGrantedAuthority);
		User user = new User("volante", "123456", true, true, true, true, authoritiesPrincipal);
		
		UsernamePasswordAuthenticationToken authUser = new UsernamePasswordAuthenticationToken(user, null, authoritiesPrincipal);
		
<<<<<<< HEAD
		Set<String> responseTypes = new HashSet<String>();
		responseTypes.add("code");
		authReq.setResponseTypes(responseTypes);
		
		Set<String> resourceIds = new HashSet<String>();
		resourceIds.add("oauth2-resource");
		authReq.setResourceIds(resourceIds);
		
		authReq.setApproved(true);
		
		Map<String,Serializable> extensions = new HashMap<String,Serializable>();
		authReq.setExtensions(extensions);
		
		//String state;
		authReq.setState("null");
		
		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>(); 
		SimpleGrantedAuthority simpleAuth = new SimpleGrantedAuthority("READ_ONLY_CLIENT");
		authorities.add(simpleAuth);
		authReq.setAuthorities(authorities);
		
		Authentication authUser = null;
		authUser.setAuthenticated(true);
=======
		//org.springframework.security.core.userdetails.User
		//Authentication authUser = null;
>>>>>>> 1353211f2eadaad73d9114afc6e86e8c44f7bf60
		
		
		return AuthorizationEndpoint.getAuthorizationCodeResponse(authReq, (Authentication) authUser);
		
	}
	
>>>>>>> 6f97e768786fa57dd0034028740731908308c0a7
}
