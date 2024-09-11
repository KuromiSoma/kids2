package com.kdc.common.entity.db;


public class AdministratorMasterEntity extends CommonColumnsEntity {

	private String administratorid;
	private String administratorname;
	private String password;
	
	/**
	 * @return administratorid
	 */
	public String getAdministratorid() {
		return administratorid;
	}
	/**
	 * @param administratorid セットする administratorid
	 */
	public void setAdministratorid(String administratorid) {
		this.administratorid = administratorid;
	}
	/**
	 * @return administratorname
	 */
	public String getAdministratorname() {
		return administratorname;
	}
	/**
	 * @param administratorname セットする administratorname
	 */
	public void setAdministratorname(String administratorname) {
		this.administratorname = administratorname;
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

}