package com.kdc.common.entity.db;

import java.sql.Timestamp;

public class InvitationManagementEntity extends CommonColumnsEntity {

   	private String invitationcode;
 	private String owneruserid;
 	private String guestuserid;
 	private String guesttelephonenumber;
	private String groupid;
 	private Integer authlevel;
   	private Timestamp expirationdate;
 	private Integer usedflg;
 	
	/**
	 * @return invitationcode
	 */
	public String getInvitationcode() {
		return invitationcode;
	}
	/**
	 * @param invitationcode セットする invitationcode
	 */
	public void setInvitationcode(String invitationcode) {
		this.invitationcode = invitationcode;
	}
	/**
	 * @return owneruserid
	 */
	public String getOwneruserid() {
		return owneruserid;
	}
	/**
	 * @param owneruserid セットする owneruserid
	 */
	public void setOwneruserid(String owneruserid) {
		this.owneruserid = owneruserid;
	}
	/**
	 * @return guestuserid
	 */
	public String getGuestuserid() {
		return guestuserid;
	}
	/**
	 * @param guestuserid セットする guestuserid
	 */
	public void setGuestuserid(String guestuserid) {
		this.guestuserid = guestuserid;
	}
	/**
	 * @return guesttelephonenumber
	 */
	public String getGuesttelephonenumber() {
		return guesttelephonenumber;
	}
	/**
	 * @param guesttelephonenumber セットする guesttelephonenumber
	 */
	public void setGuesttelephonenumber(String guesttelephonenumber) {
		this.guesttelephonenumber = guesttelephonenumber;
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
	 * @return expirationdate
	 */
	public Timestamp getExpirationdate() {
		return expirationdate;
	}
	/**
	 * @param expirationdate セットする expirationdate
	 */
	public void setExpirationdate(Timestamp expirationdate) {
		this.expirationdate = expirationdate;
	}
	/**
	 * @return usedflg
	 */
	public Integer getUsedflg() {
		return usedflg;
	}
	/**
	 * @param usedflg セットする usedflg
	 */
	public void setUsedflg(Integer usedflg) {
		this.usedflg = usedflg;
	}
 	
}
