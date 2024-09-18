package com.kdc.api.getgroups;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kdc.common.entity.api.base.ResponseBaseEntity;
import com.kdc.common.entity.db.GroupInfoEntity;
import com.kdc.common.util.CommonService;
import com.kdc.mybatis.mapper.api.GetGroupsMapper;

/**
 * グループ　API　Serviceクラス
 */
@Service
public class GetGroupsService {

	@Autowired
	private GetGroupsMapper getGroupMapper;

	@Autowired
	private CommonService commonService;
	
	/**
	 * パラメータチェック
	 * @param reqEntity
	 * @param resEntity
	 * @return
	 */
	public Boolean parameterCheck(GetGroupsRequestEntity reqEntity, ResponseBaseEntity resEntity) {
		
		if(!this.commonService.checkBaseParameter(reqEntity, resEntity)){
			return false;
		}
		
		return true;
	}
	
	/**
	 * グループ情報取得
	 * @param reqEntity
	 * @return
	 */
	public List<GroupInfoEntity> getGroups(GetGroupsRequestEntity reqEntity) {
		
		List<GroupInfoEntity> groupList = this.getGroupMapper.getGroupList();
		return groupList;
		
	}
}
