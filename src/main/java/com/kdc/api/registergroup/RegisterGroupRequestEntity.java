package com.kdc.api.registergroup;

import com.kdc.common.entity.api.base.RequestBaseEntity;
import com.kdc.common.entity.db.GroupInfoEntity;

/**
 * グループ情報エンティティ
 * @author umemoto
 *
 */
public class RegisterGroupRequestEntity extends RequestBaseEntity {
	
	private Integer mode;
	private GroupInfoEntity groupInfo;
	
	/**
	 * @return mode
	 */
	public Integer getMode() {
		return mode;
	}
	
	/**
	 * @param mode
	 *  セットする mode
	 */
	public void setMode(Integer mode) {
		this.mode = mode;
	}
	
	/**
	 * @return groupMaster
	 */
	public GroupInfoEntity getGroupInfo() {
		return groupInfo;
	}
	
	/**
	 * @param groupMaster
	 * セットする groupMaster
	 */
	public void setGroupInfo(GroupInfoEntity groupInfo) {
		this.groupInfo = groupInfo;
	}
	
	

}
