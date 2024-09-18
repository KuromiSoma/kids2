package com.kdc.api.registergroup;

import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kdc.common.entity.api.base.ResponseBaseEntity;
import com.kdc.common.entity.db.GroupInfoEntity;
import com.kdc.common.entity.db.UserMasterEntity;
import com.kdc.common.enums.RegisterGroupModeEnum;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.CommonService;
import com.kdc.common.util.KdcCommonUtils;
import com.kdc.mybatis.mapper.api.SettingUserMapper;
import com.kdc.mybatis.mapper.common.entity.GroupMasterMapper;

/**
 * グループ登録API Serviceクラス
 * @author umemoto
 *
 */
@Service
public class RegisterGroupService {

	@Autowired
	private GroupMasterMapper groupMasterMapper;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private SettingUserMapper settingUserMapper;

	/**
	 * パラメータチェック
	 * @param reqEntity
	 * @param resEntity
	 * @return Boolean
	 */
	public Boolean parameterCheck(RegisterGroupRequestEntity reqEntity, ResponseBaseEntity resEntity) {
		
		//端末情報
		if(!this.commonService.checkBaseParameter(reqEntity, resEntity)){
			return false;
		}
		
		//モード
		if(reqEntity.getMode() == null ) {
			resEntity.setMessage("mode is Empty");
			return false;
		}
		
		//グループ情報
		
		//モード
		try {
			RegisterGroupModeEnum.valueOf(reqEntity.getMode());
			if ( reqEntity.getMode() == 1) {
				//新規モード
				//グループ名
				if ( StringUtil.isEmpty(reqEntity.getGroupInfo().getGroupname())){
					resEntity.setMessage("groupMaster.groupName is Empty");
					return false;
				}
			} else if ( reqEntity.getMode() == 2) {
				//更新
				//グループID
				if ( StringUtil.isEmpty(reqEntity.getGroupInfo().getGroupid())){
					resEntity.setMessage("groupMaster.groupId is Empty");
					return false;
				}
				//グループ名
				if ( StringUtil.isEmpty(reqEntity.getGroupInfo().getGroupname())){
					resEntity.setMessage("groupMaster.groupName is Empty");
					return false;
				}
			} else if ( reqEntity.getMode() == 3 ) {
				//削除
				//グループID
				if ( StringUtil.isEmpty(reqEntity.getGroupInfo().getGroupid())){
					resEntity.setMessage("groupMaster.groupId is Empty");
					return false;
				}
			}
			
		} catch (IllegalArgumentException e) {
			resEntity.setMessage("mode is Wrong");
			return false;
		}
		
		//グループ名MAX LENGTH
		if ( StringUtils.length(reqEntity.getGroupInfo().getGroupname()) > CommonConst.GROUP_NAME_MAX_LENGTH) {
			resEntity.setMessage("groupMaster.groupName is too Long");
			return false;
		}
		
		return true;
	}
	
	/**
	 * グループ情報登録
	 * @param reqEntity
	 * @return
	 */
	public Boolean registerGroup(RegisterGroupRequestEntity reqEntity, RegisterGroupResponseEntity resEntity) {
		GroupInfoEntity rec = new GroupInfoEntity();
		Timestamp nowTimestamp = KdcCommonUtils.getNowTimestamp();
		
		switch(RegisterGroupModeEnum.valueOf(reqEntity.getMode())) {
		case INSERT:
		case UPDATE:
			// group_master共通
			rec.setGroupname(reqEntity.getGroupInfo().getGroupname());
			rec.setConfigparam1(reqEntity.getGroupInfo().getConfigparam1());
			rec.setConfigparam2(reqEntity.getGroupInfo().getConfigparam2());
			rec.setConfigparam3(reqEntity.getGroupInfo().getConfigparam3());
			rec.setConfigparam4(reqEntity.getGroupInfo().getConfigparam4());
			rec.setConfigparam5(reqEntity.getGroupInfo().getConfigparam5());
			break;
		case DELETE:
			break;
		}
		
		try {
			switch(RegisterGroupModeEnum.valueOf(reqEntity.getMode())){
			case INSERT: //新規
				//グループ情報登録
				//group_id_seq
				String groupidseq = this.commonService.createGroupId();
				
				//group_master
				rec.setGroupid(groupidseq);
				rec.setRegisteruserid(reqEntity.getUserInfo().getUserId());
				rec.setRegisterdate(nowTimestamp);
				//初期値０で設定
				rec.setConfigparam1("0");
				rec.setConfigparam2("0");
				rec.setConfigparam3("0");
				rec.setConfigparam4("0");
				rec.setConfigparam5("0");
				resEntity.setMessage("groupMasterMapper.insert");
				this.groupMasterMapper.insert(rec);
				resEntity.setMessage("groupMasterMapper.insertSendIntervalConfig");
				this.groupMasterMapper.insertSendIntervalConfig(rec);
				resEntity.setMessage("groupMasterMapper.insertUserAlertConfig");
				this.groupMasterMapper.insertUserAlertConfig(rec);
				resEntity.setMessage("groupMasterMapper.insertConfigMasterg");
				this.groupMasterMapper.insertConfigMasterg(rec);
				
				// ユーザーマスタのグループID更新
				UserMasterEntity regRec = new UserMasterEntity();
				regRec.setUserid(reqEntity.getUserInfo().getUserId());
				regRec.setGroupid(groupidseq);
				regRec.setUpdateuserid(reqEntity.getUserInfo().getUserId());
				regRec.setUpdatedate(KdcCommonUtils.getNowTimestamp());
				this.settingUserMapper.updateUserMaster(regRec, regRec);

				//結果APIにグループIDを返却
				resEntity.setGroupId(groupidseq);

				break;
			case UPDATE: //更新
				rec.setGroupid(reqEntity.getGroupInfo().getGroupid());
				rec.setUpdateuserid(reqEntity.getUserInfo().getUserId());
				rec.setUpdatedate(nowTimestamp);
				this.groupMasterMapper.update(rec);

				// ユーザーマスタのグループID更新
				regRec = new UserMasterEntity();
				regRec.setUserid(reqEntity.getUserInfo().getUserId());
				regRec.setGroupid(reqEntity.getGroupInfo().getGroupid());
				regRec.setUpdateuserid(reqEntity.getUserInfo().getUserId());
				regRec.setUpdatedate(KdcCommonUtils.getNowTimestamp());
				this.settingUserMapper.updateUserMaster(regRec, regRec);

				//結果APIにグループIDを返却
				resEntity.setGroupId(reqEntity.getGroupInfo().getGroupid());

				break;
			case DELETE: //削除
				break;
			default:
				return false;
			}

			return true;

		}catch ( Exception e ) {
			resEntity.setMessage(e.getMessage() + " groupid:" + resEntity.getGroupId());
			return false;
		}		
	}
}
