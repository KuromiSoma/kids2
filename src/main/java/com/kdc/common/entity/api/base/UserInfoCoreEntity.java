package com.kdc.common.entity.api.base;

public class UserInfoCoreEntity {

	private String userId;
	private String password;
	private String groupId;
	private String authLevel;
	
	/**
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId セットする userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password セットする password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return groupId
	 */
	public String getGroupId() {
		return groupId;
	}
	/**
	 * @param groupId セットする groupId
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	/**
	 * @return authLevel
	 */
	public String getAuthLevel() {
		return authLevel;
	}
	/**
	 * @param authLevel セットする authLevel
	 */
	public void setAuthLevel(String authLevel) {
		this.authLevel = authLevel;
	}

}