package com.kdc.mybatis.domain.web.webauthconfig;

public class UserInfoDomain {

	private String userid;
	private String username;
	private String authlevel;
	private String telephonenumber;

	/**
	 * @return userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param userid
	 *            セットする userid
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            セットする username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return authlevel
	 */
	public String getAuthlevel() {
		return authlevel;
	}

	/**
	 * @param authlevel
	 *            セットする authlevel
	 */
	public void setAuthlevel(String authlevel) {
		this.authlevel = authlevel;
	}

	/**
	 * @return telephonenumber
	 */
	public String getTelephonenumber() {
		return telephonenumber;
	}

	/**
	 * @param telephonenumber
	 *            セットする telephonenumber
	 */
	public void setTelephonenumber(String telephonenumber) {
		this.telephonenumber = telephonenumber;
	}

}
