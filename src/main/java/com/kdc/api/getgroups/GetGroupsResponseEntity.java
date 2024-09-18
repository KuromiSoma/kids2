package com.kdc.api.getgroups;

import java.util.List;

import com.kdc.common.entity.api.base.ResponseBaseEntity;
import com.kdc.common.entity.db.GroupInfoEntity;

public class GetGroupsResponseEntity extends ResponseBaseEntity{
	
	private List<GroupInfoEntity> groupInfo;

	/**
	 * @return groupInfo
	 */
	public List<GroupInfoEntity> getGroupInfo() {
		return groupInfo;
	}

	/**
	 * @param groupInfo
	 * セットする groupInfo
	 */
	public void setGroupInfo(List<GroupInfoEntity> groupInfo) {
		this.groupInfo = groupInfo;
	}

}
