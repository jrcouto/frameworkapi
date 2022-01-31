package br.com.framework.api.token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken>{

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		// TODO Auto-generated method stub
		return returnType.getMethod().getName().equals("postAccessToken");
	}

	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType,
			MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {
		// TODO Auto-generated method stub
		
		HttpServletRequest req = ((ServletServerHttpRequest)request).getServletRequest();
		HttpServletResponse resp = ((ServletServerHttpResponse)response).getServletResponse();
		
		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) body;
		
		String refreshToken = body.getRefreshToken().getValue();
		addRefreshTokenOnCookie(refreshToken, req, resp); //Adiciona o access token em um cookie
		removeRefreshTokenFromBody(token); //Remove o access token do resultado da resposa ta requisição
		
		return body;
	}
	
	private void removeRefreshTokenFromBody(DefaultOAuth2AccessToken token) {
		token.setRefreshToken(null);
	}

	private void addRefreshTokenOnCookie(String refreshToken, HttpServletRequest req, HttpServletResponse resp) {
		Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setSecure(false); //Quando https, setar como true
		
		refreshTokenCookie.setPath(req.getContextPath()+"/oauth/token");
		refreshTokenCookie.setMaxAge(259200);
		resp.addCookie(refreshTokenCookie);
		
		
	}
	
}
