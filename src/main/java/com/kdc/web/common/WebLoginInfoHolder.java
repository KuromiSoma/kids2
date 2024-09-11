package com.kdc.web.common;

import org.springframework.stereotype.Component;

@Component
public class WebLoginInfoHolder {

	private String loginId;
	private String sessionId;
	
	/**
	 * @return loginId
	 */
	public String getLoginId() {
		return loginId;
	}
	/**
	 * @param loginId セットする loginId
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	/**
	 * @return sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}
	/**
	 * @param sessionId セットする sessionId
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}
