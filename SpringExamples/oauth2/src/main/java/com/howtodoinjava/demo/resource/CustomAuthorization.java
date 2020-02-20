package com.howtodoinjava.demo.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpointHandlerMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.support.SimpleSessionStatus;
import org.springframework.web.servlet.View;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.security.Principal;
import java.util.*;

@Controller
public class CustomAuthorization {

    static final String ORIGINAL_AUTHORIZATION_REQUEST_ATTR_NAME = "org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint.ORIGINAL_AUTHORIZATION_REQUEST";

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    AuthorizationServerEndpointsConfiguration asec;


    @Bean
    @Order(value = Ordered.HIGHEST_PRECEDENCE)
    @Primary
    public AuthorizationEndpoint authorizationEndpoint () throws Exception{

        AuthorizationEndpoint authorizationEndpoint = new AuthorizationEndpoint();
        FrameworkEndpointHandlerMapping mapping = asec.getEndpointsConfigurer().getFrameworkEndpointHandlerMapping();
        authorizationEndpoint.setUserApprovalPage(extractPath(mapping, "/oauth/confirm_access"));
        authorizationEndpoint.setProviderExceptionHandler(asec.getEndpointsConfigurer().getExceptionTranslator());
        authorizationEndpoint.setErrorPage(extractPath(mapping, "/oauth/error"));
        authorizationEndpoint.setTokenGranter(asec.getEndpointsConfigurer().getTokenGranter());
        authorizationEndpoint.setClientDetailsService(clientDetailsService);
        authorizationEndpoint.setAuthorizationCodeServices(asec.getEndpointsConfigurer().getAuthorizationCodeServices());
        authorizationEndpoint.setOAuth2RequestFactory(asec.getEndpointsConfigurer().getOAuth2RequestFactory());
        authorizationEndpoint.setOAuth2RequestValidator(asec.getEndpointsConfigurer().getOAuth2RequestValidator());
        authorizationEndpoint.setUserApprovalHandler(asec.getEndpointsConfigurer().getUserApprovalHandler());

        return authorizationEndpoint;
    }

    private String extractPath(FrameworkEndpointHandlerMapping mapping, String page) {
        String path = mapping.getPath(page);
        if (path.contains(":")) {
            return path;
        }
        return "forward:" + path;
    }

    @RequestMapping("/custom")
    public void authorize() throws Exception {
        Map<String, String> approvalParameters = new HashMap<String, String>();
        approvalParameters.put("user_oauth_approval", "true");
		approvalParameters.put("scope.read_profile_info", "true");
		approvalParameters.put("authorize", "Authorize");

//        Set<String> scope = new HashSet<String>();
//        scope.add("read_profile_info");
//        Map<String, String> requestParameters = new HashMap<String, String>();
//        requestParameters.put("response_type", "code");
//        requestParameters.put("client_id", "clientapp");
//        requestParameters.put("scope", "read_profile_info");
//        Set<String> responseTypes = new HashSet<String>();
//        responseTypes.add("code");
//        Set<String> resourceIds = new HashSet<String>();
//        resourceIds.add("oauth2-resource");
//        Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
//        SimpleGrantedAuthority simpleAuth = new SimpleGrantedAuthority("READ_ONLY_CLIENT");
//        authorities.add(simpleAuth);
//        AuthorizationRequest authReq = new AuthorizationRequest(requestParameters, approvalParameters, "clientapp", scope, resourceIds, authorities, true, "null", "https://www.volantetech.com/",
//                responseTypes);
//        Map<String, Serializable> extensions = new HashMap<String,Serializable>();
//        authReq.setExtensions(extensions);
//        authReq.setApprovalParameters(approvalParameters);

        String state = "null";
        Set<String> responseTypes = new HashSet<String>();
        responseTypes.add("code");
        Set<String> resourceIds = new HashSet<String>();
        resourceIds.add("oauth2-resource");
        Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        SimpleGrantedAuthority simpleAuth = new SimpleGrantedAuthority("READ_ONLY_CLIENT");
        authorities.add(simpleAuth);
        boolean approved = false;
        String redirectUri = "https://www.volantetech.com/";
        Map<String, Serializable> extensions = new HashMap<String,Serializable>();
        String clientId = "clientapp";
        Set<String> scope = new HashSet<String>();
        scope.add("read_profile_info");
        Map<String, String> requestParameters = new HashMap<String, String>();
        requestParameters.put("response_type", "code");
        requestParameters.put("client_id", "clientapp");
        requestParameters.put("scope", "read_profile_info");
        AuthorizationRequest authorizationRequest = new AuthorizationRequest(requestParameters, approvalParameters, clientId, scope, resourceIds, authorities, approved, state, redirectUri, responseTypes);
        authorizationRequest.setExtensions(extensions);

        BindingAwareModelMap model = new BindingAwareModelMap();
        model.put("authorizationRequest", authorizationRequest);
        model.put(ORIGINAL_AUTHORIZATION_REQUEST_ATTR_NAME, unmodifiableMap(authorizationRequest));

        SimpleSessionStatus sessionStatus = new SimpleSessionStatus();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
        Set<GrantedAuthority> authoritiesPrincipal = new HashSet<GrantedAuthority>();
        authoritiesPrincipal.add(simpleGrantedAuthority);
        User user = new User("volante", "123456", true, true, true, true, authoritiesPrincipal);
		UsernamePasswordAuthenticationToken authUser = new UsernamePasswordAuthenticationToken(user, null, authoritiesPrincipal);

		AuthorizationEndpoint authorizationEndpoint = authorizationEndpoint();
        //authorizationEndpoint.authorize(model, approvalParameters, sessionStatus, authUser);
        authorizationEndpoint.approveOrDeny(approvalParameters, model, sessionStatus, authUser);
    }

    Map<String, Object> unmodifiableMap(AuthorizationRequest authorizationRequest) {
        Map<String, Object> authorizationRequestMap = new HashMap<String, Object>();

        authorizationRequestMap.put(OAuth2Utils.CLIENT_ID, authorizationRequest.getClientId());
        authorizationRequestMap.put(OAuth2Utils.STATE, authorizationRequest.getState());
        authorizationRequestMap.put(OAuth2Utils.REDIRECT_URI, authorizationRequest.getRedirectUri());
        if (authorizationRequest.getResponseTypes() != null) {
            authorizationRequestMap.put(OAuth2Utils.RESPONSE_TYPE,
                    Collections.unmodifiableSet(new HashSet<String>(authorizationRequest.getResponseTypes())));
        }
        if (authorizationRequest.getScope() != null) {
            authorizationRequestMap.put(OAuth2Utils.SCOPE,
                    Collections.unmodifiableSet(new HashSet<String>(authorizationRequest.getScope())));
        }
        authorizationRequestMap.put("approved", authorizationRequest.isApproved());
        if (authorizationRequest.getResourceIds() != null) {
            authorizationRequestMap.put("resourceIds",
                    Collections.unmodifiableSet(new HashSet<String>(authorizationRequest.getResourceIds())));
        }
        if (authorizationRequest.getAuthorities() != null) {
            authorizationRequestMap.put("authorities",
                    Collections.unmodifiableSet(new HashSet<GrantedAuthority>(authorizationRequest.getAuthorities())));
        }

        return Collections.unmodifiableMap(authorizationRequestMap);
    }

}
