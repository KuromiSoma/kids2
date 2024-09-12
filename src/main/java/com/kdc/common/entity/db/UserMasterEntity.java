package com.kdc.common.entity.db;


public class UserMasterEntity extends CommonColumnsEntity {

	private String userid;
	private String password;
	private String username;
	private String iconid;
	private byte[] iconfile;
	private String groupid;
	private Integer authlevel;
	private String linecolor;
	private Integer markercolor;
	private Integer disporder;
	
	/**
	 * @return userid
	 */
	public String getUserid() {
		return userid;
	}
	/**
	 * @param userid セットする userid
	 */
	public void setUserid(String userid) {
		this.userid = userid;
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
	 * @return username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username セットする username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return iconid
	 */
	public String getIconid() {
		return iconid;
	}
	/**
	 * @param iconid セットする iconid
	 */
	public void setIconid(String iconid) {
		this.iconid = iconid;
	}
	/**
	 * @return iconfile
	 */
	public byte[] getIconfile() {
		return iconfile;
	}
	/**
	 * @param iconfile セットする iconfile
	 */
	public void setIconfile(byte[] iconfile) {
		this.iconfile = iconfile;
	}
	/**
	 * @return groupid
	 */
	public String getGroupid() {
		return groupid;
	}
	/**
	 * @param groupid セットする groupid
	 */
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	/**
	 * @return authlevel
	 */
	public Integer getAuthlevel() {
		return authlevel;
	}
	/**
	 * @param authlevel セットする authlevel
	 */
	public void setAuthlevel(Integer authlevel) {
		this.authlevel = authlevel;
	}
	/**
	 * @return linecolor
	 */
	public String getLinecolor() {
		return linecolor;
	}
	/**
	 * @param linecolor セットする linecolor
	 */
	public void setLinecolor(String linecolor) {
		this.linecolor = linecolor;
	}
	/**
	 * @return markercolor
	 */
	public Integer getMarkercolor() {
		return markercolor;
	}
	/**
	 * @param markercolor セットする markercolor
	 */
	public void setMarkercolor(Integer markercolor) {
		this.markercolor = markercolor;
	}
	/**
	 * @return disporder
	 */
	public Integer getDisporder() {
		return disporder;
	}
	/**
	 * @param disporder セットする disporder
	 */
	public void setDisporder(Integer disporder) {
		this.disporder = disporder;
	}

}