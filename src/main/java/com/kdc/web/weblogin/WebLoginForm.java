package com.kdc.web.weblogin;

public class WebLoginForm {

	private String loginId;
	private String loginPass;
	private boolean loginErrorFlg;
	
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
	 * @return loginPass
	 */
	public String getLoginPass() {
		return loginPass;
	}
	/**
	 * @param loginPass セットする loginPass
	 */
	public void setLoginPass(String loginPass) {
		this.loginPass = loginPass;
	}
	/**
	 * @return loginErrorFlg
	 */
	public boolean isLoginErrorFlg() {
		return loginErrorFlg;
	}
	/**
	 * @param loginErrorFlg セットする loginErrorFlg
	 */
	public void setLoginErrorFlg(boolean loginErrorFlg) {
		this.loginErrorFlg = loginErrorFlg;
	}

}