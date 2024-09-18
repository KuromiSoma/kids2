package com.kdc.web.webauthconfig;

/**
 * 管理レベル別ユーザクラス
 */
public class AuthUser {
	private boolean changed;
	private String userId;
	private String userName;
	private String authLevel;
	private String authCheck;
	private String telephoneNumber;
	

	/**
	 * @return changed
	 */
	public boolean isChanged() {
		return changed;
	}

	/**
	 * @param changed
	 *            セットする changed
	 */
	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	/**
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            セットする userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            セットする userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return authLevel
	 */
	public String getAuthLevel() {
		return authLevel;
	}

	/**
	 * @param authLevel
	 *            セットする authLevel
	 */
	public void setAuthLevel(String authLevel) {
		this.authLevel = authLevel;
	}

	/**
	 * @return authCheck
	 */
	public String getAuthCheck() {
		return authCheck;
	}

	/**
	 * @param authCheck セットする authCheck
	 */
	public void setAuthCheck(String authCheck) {
		this.authCheck = authCheck;
	}

	/**
	 * @return telephoneNumber
	 */
	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	/**
	 * @param telephoneNumber セットする telephoneNumber
	 */
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	
	
	
	
}