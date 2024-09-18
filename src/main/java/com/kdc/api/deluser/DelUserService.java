package com.kdc.api.deluser;

import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kdc.common.entity.api.base.ResponseBaseEntity;
import com.kdc.common.entity.db.UserMasterEntity;
import com.kdc.common.util.CommonService;
import com.kdc.common.util.KdcCommonUtils;
import com.kdc.mybatis.mapper.common.entity.UserMasterMapper;

/**
 * ユーザ削除 API Service クラス
 *
 */
@Service
public class DelUserService {

	@Autowired
	private UserMasterMapper userMasterMapper;

	@Autowired
	private CommonService commonService;

	/**
	 * パラメータチェック.
	 * @param reqEntity
	 * @param resEntity
	 * @return Boolean
	 */
	public Boolean parameterCheck(DelUserRequestEntity reqEntity, ResponseBaseEntity resEntity) {

		// ユーザ・端末情報
		if (!this.commonService.checkBaseParameter(reqEntity, resEntity)) {
			return false;
		}

		// 対象ユーザID
		if (StringUtils.isEmpty(reqEntity.getTargetUserId())) {
			resEntity.setMessage("targetUserId is Empty");
			return false;
		}

		// 権限チェック
		if (!this.commonService.isAdminUser(reqEntity.getUserInfo(), resEntity)) {
			return false;
		}

		return true;
	}

	/**
	 * ユーザ削除.
	 * @param reqEntity
	 * @param resEntity
	 * @return Boolean
	 */
	public Boolean delete(DelUserRequestEntity reqEntity, ResponseBaseEntity resEntity) {

		Timestamp nowTimestamp = KdcCommonUtils.getNowTimestamp();

		UserMasterEntity rec = new UserMasterEntity();
		rec.setUserid(reqEntity.getTargetUserId());
		rec.setUpdateuserid(reqEntity.getUserInfo().getUserId());
		rec.setUpdatedate(nowTimestamp);
		try {
			this.userMasterMapper.delete(rec);
		} catch (Exception e) {
			return false;
		}

		return true;

	}

}
