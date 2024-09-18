package com.kdc.api.geticon;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kdc.common.entity.db.UserMasterEntity;
import com.kdc.mybatis.mapper.common.entity.UserMasterMapper;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.CommonService;

/**
 * アイコン画像ダウンロード API Service クラス
 *
 */
@Service
public class GetIconService {
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
	public Boolean parameterCheck(GetIconRequestEntity requestEntity, GetIconResponseEntity responseEntity) {
		if (!this.commonService.checkBaseParameter(requestEntity, responseEntity)) {
			return false;
		}
		// 対象ユーザID
		if (StringUtils.isEmpty(requestEntity.getTargetUserId())) {
			responseEntity.setMessage("targetUserId is Empty");
			return false;
		}
		return true;
	}

	/**
	 * 対象ユーザマスタの取得
	 * @param requestEntity
	 * @return UserMasterEntity
	 */
	public UserMasterEntity getUser(GetIconRequestEntity requestEntity) {
		return this.userMasterMapper.selectByPk(requestEntity.getTargetUserId(), CommonConst.FLG_OFF);
	}
}
