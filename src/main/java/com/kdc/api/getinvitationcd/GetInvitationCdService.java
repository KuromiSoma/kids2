package com.kdc.api.getinvitationcd;

import java.sql.Timestamp;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kdc.common.entity.api.base.ResponseBaseEntity;
import com.kdc.common.entity.db.ConfigMasterEntity;
import com.kdc.common.entity.db.InvitationManagementEntity;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.CommonService;
import com.kdc.common.util.KdcCommonUtils;
import com.kdc.mybatis.mapper.common.entity.ConfigMasterMapper;
import com.kdc.mybatis.mapper.common.entity.InvitationManagementMapper;

/**
 * 招待コード発行 API Service クラス
 *
 */
@Service
public class GetInvitationCdService {

	@Autowired
	private InvitationManagementMapper invitationManagementMapper;

	@Autowired
	private ConfigMasterMapper configMasterMapper;

	@Autowired
	private CommonService commonService;

	/**
	 * パラメータチェック.
	 * @param reqEntity
	 * @param resEntity
	 * @return Boolean
	 */
	public Boolean parameterCheck(GetInvitationCdRequestEntity reqEntity, ResponseBaseEntity resEntity) {

		// ユーザ・端末チェック
		if (!this.commonService.checkBaseParameter(reqEntity, resEntity)) {
			return false;
		}
		// 管理レベル
		if (StringUtils.isEmpty(reqEntity.getAuthLevel())) {
			resEntity.setMessage("authLevel is Empty");
			return false;
		}
		// 管理レベル設定の制限
		if (Integer.parseInt(reqEntity.getUserInfo().getAuthLevel()) < Integer.parseInt(reqEntity.getAuthLevel())) {
			resEntity.setMessage("authLevel is Wrong");
			return false;
		}

		return true;
	}

	/**
	 * 招待コード発行.レスポンス情報の作成.
	 * @param reqEntity
	 * @return GetInvitationCdResponseEntity
	 */
	public GetInvitationCdResponseEntity getCd(GetInvitationCdRequestEntity reqEntity) {

		// 招待コード発行
		String invitationCd = this.commonService.createInvitationCode();

		// 招待コードと発行ユーザ、有効期限を招待管理テーブルに登録
		Timestamp nowTimestamp = KdcCommonUtils.getNowTimestamp();

		// 有効期限
		List<ConfigMasterEntity> configList = this.configMasterMapper
				.selectCodeList(CommonConst.CONFIG_ID_INVITATION_CODE_EXPIRATION_TIME,reqEntity.getUserInfo().getGroupId());
		int expirationTime = (configList != null) ? Integer.parseInt(configList.get(0).getConfigparam1()) : 0;

		Timestamp expirationTimeStamp = KdcCommonUtils.timestampPlusTime(nowTimestamp, 0, expirationTime, 0);

		InvitationManagementEntity rec = new InvitationManagementEntity();

		rec.setInvitationcode(invitationCd);
		rec.setOwneruserid(reqEntity.getUserInfo().getUserId());
		rec.setGroupid(reqEntity.getUserInfo().getGroupId());
		rec.setAuthlevel(Integer.parseInt(reqEntity.getAuthLevel()));
		rec.setExpirationdate(expirationTimeStamp);
		rec.setRegisteruserid(reqEntity.getUserInfo().getUserId());
		rec.setRegisterdate(nowTimestamp);

		this.invitationManagementMapper.insert(rec);

		// レスポンス作成
		GetInvitationCdResponseEntity entity = new GetInvitationCdResponseEntity();

		entity.setExpirationDate(KdcCommonUtils.timestampToDateTimeString(expirationTimeStamp));
		entity.setInvitationCd(invitationCd);

		return entity;

	}

}
