package com.kdc.common.entity.db;

public class ConfigMasterEntity extends CommonColumnsEntity {

	private String groupid;
	private Integer configid;
	private Integer configcode;
	private String configname;
	private String configparam1;
	private String configparam2;
	private String configparam3;
	private String configparam4;
	private String configparam5;
	
	/**
	 * @return configid
	 */
	public Integer getConfigid() {
		return configid;
	}
	/**
	 * @param configid セットする configid
	 */
	public void setConfigid(Integer configid) {
		this.configid = configid;
	}
	/**
	 * @return configcode
	 */
	public Integer getConfigcode() {
		return configcode;
	}
	/**
	 * @param configcode セットする configcode
	 */
	public void setConfigcode(Integer configcode) {
		this.configcode = configcode;
	}
	/**
	 * @return configname
	 */
	public String getConfigname() {
		return configname;
	}
	/**
	 * @param configname セットする configname
	 */
	public void setConfigname(String configname) {
		this.configname = configname;
	}
	/**
	 * @return configparam1
	 */
	public String getConfigparam1() {
		return configparam1;
	}
	/**
	 * @param configparam1 セットする configparam1
	 */
	public void setConfigparam1(String configparam1) {
		this.configparam1 = configparam1;
	}
	/**
	 * @return configparam2
	 */
	public String getConfigparam2() {
		return configparam2;
	}
	/**
	 * @param configparam2 セットする configparam2
	 */
	public void setConfigparam2(String configparam2) {
		this.configparam2 = configparam2;
	}
	/**
	 * @return configparam3
	 */
	public String getConfigparam3() {
		return configparam3;
	}
	/**
	 * @param configparam3 セットする configparam3
	 */
	public void setConfigparam3(String configparam3) {
		this.configparam3 = configparam3;
	}
	/**
	 * @return configparam4
	 */
	public String getConfigparam4() {
		return configparam4;
	}
	/**
	 * @param configparam4 セットする configparam4
	 */
	public void setConfigparam4(String configparam4) {
		this.configparam4 = configparam4;
	}
	/**
	 * @return configparam5
	 */
	public String getConfigparam5() {
		return configparam5;
	}
	/**
	 * @param configparam5 セットする configparam5
	 */
	public void setConfigparam5(String configparam5) {
		this.configparam5 = configparam5;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	
}
