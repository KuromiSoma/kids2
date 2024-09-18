package com.kdc.api.checkinvitationcd;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kdc.common.entity.api.base.ResponseBaseEntity;
import com.kdc.common.entity.db.InvitationManagementEntity;
import com.kdc.common.entity.db.UserMasterEntity;
import com.kdc.common.enums.AuthenticationModeEnum;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.CommonService;
import com.kdc.common.util.KdcCommonUtils;
import com.kdc.mybatis.mapper.api.CheckInvitationCdMapper;
import com.kdc.mybatis.mapper.common.entity.UserMasterMapper;

/**
 * 招待コード認証 API Service クラス
 *
 */
@Service
public class CheckInvitationCdService {

	@Autowired
	private CheckInvitationCdMapper checkInvitationCdMapper;

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private UserMasterMapper userMasterMappaer;

	/**
	 * パラメータチェック.
	 * 
	 * @param reqEntity
	 * @param resEntity
	 * @return Boolean
	 */
	public Boolean parameterCheck(CheckInvitationCdRequestEntity reqEntity, ResponseBaseEntity resEntity) {

		// モード
		if (reqEntity.getMode() == null) {
			resEntity.setMessage("mode is Empty");
			return false;
		}
		try {
			AuthenticationModeEnum.valueOf(reqEntity.getMode());
		} catch (IllegalArgumentException e) {
			resEntity.setMessage("mode is Wrong");
			return false;
		}

		// 招待コード
		switch (AuthenticationModeEnum.valueOf(reqEntity.getMode())) {
		case LOGIN:
		case REGISTER_USER:
			if (StringUtils.isEmpty(reqEntity.getInvitationCd())) {
				resEntity.setMessage("invitationCd is Empty");
				return false;
			}
			if ( CommonConst.INVITATION_CD.equals(reqEntity.getInvitationCd())) {
				//ユーザー存在チェック
				List<UserMasterEntity> userList = this.userMasterMappaer.selectAll();
				if ( userList.size() == 0 ) {
					//ユーザー不在の場合、スルー
					return true;
				}
			}
			break;
		case NEW_GROUP:
			// 新規グループ作成時は招待コード不要
			break;
		}

		// 端末情報
		if (!this.commonService.checkDeviceInfoValues(reqEntity.getDeviceInfo(), resEntity)) {
			return false;
		}

		return true;
	}

	/**
	 * 招待管理テーブル更新.
	 * 
	 * @param reqEntity
	 * @param resEntity
	 * @return Boolean
	 */
	public Boolean updateInvitationManagement(CheckInvitationCdRequestEntity reqEntity,
			CheckInvitationCdResponseEntity resEntity) {

		Timestamp nowTimestamp = KdcCommonUtils.getNowTimestamp();

		//初回ユーザー処理　招待コード＝1111111で認証スルー
		if ( CommonConst.INVITATION_CD.equals(reqEntity.getInvitationCd())) {
			return true;
		}

		// 招待管理テーブル情報更新
		InvitationManagementEntity rec = new InvitationManagementEntity();
		rec.setInvitationcode(reqEntity.getInvitationCd());
		rec.setGuesttelephonenumber(reqEntity.getDeviceInfo().getTelephoneNumber());
		rec.setExpirationdate(nowTimestamp);
		rec.setUpdateuserid(reqEntity.getDeviceInfo().getTelephoneNumber());
		rec.setUpdatedate(nowTimestamp);

		try {
			// 招待コード一致、有効期限以内、使用フラグ=0、使用者ID・電話番号=NULL）
			int count = this.checkInvitationCdMapper.updateInvitationManagement(rec);

			// 更新件数0の場合、認証失敗。
			if (count == 0) {
				// 認証失敗は正常処理
				resEntity.setMessage("invitationCode is invalid");
				resEntity.setAuthFlg(CommonConst.FLG_OFF);
				return true;
			}

			// 更新件数>0の場合、認証成功。
			resEntity.setAuthFlg(CommonConst.FLG_ON);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
