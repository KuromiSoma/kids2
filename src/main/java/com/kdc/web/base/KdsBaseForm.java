package com.kdc.web.base;


/**
 * Form基底クラス
 */
public abstract class KdsBaseForm {
   
	private String loginId;

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

}