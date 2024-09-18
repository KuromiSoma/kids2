package com.kdc.api.registeruser;

import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kdc.common.entity.api.base.ResponseBaseEntity;
import com.kdc.common.entity.api.base.UserInfoEntity;
import com.kdc.common.entity.db.GroupInfoEntity;
import com.kdc.common.entity.db.InvitationManagementEntity;
import com.kdc.common.entity.db.UserMasterEntity;
import com.kdc.common.enums.AuthLevelEnum;
import com.kdc.common.enums.AuthenticationModeEnum;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.CommonService;
import com.kdc.common.util.KdcCommonUtils;
import com.kdc.common.util.UserColorSet;
import com.kdc.mybatis.mapper.api.RegisterUserMapper;
import com.kdc.mybatis.mapper.common.entity.GroupMasterMapper;
import com.kdc.mybatis.mapper.common.entity.InvitationManagementMapper;
import com.kdc.mybatis.mapper.common.entity.UserMasterMapper;

/**
 * ユーザ登録 API Service クラス
 *
 */
@Service
public class RegisterUserService {

	@Autowired
	private CommonService commonService;

	@Autowired
	private UserMasterMapper userMasterMapper;

	@Autowired
	private InvitationManagementMapper invitationManagementMapper;

	@Autowired
	private RegisterUserMapper registerUserMapper;
	
	@Autowired
	private GroupMasterMapper groupMasterMapper;

	/**
	 * パラメータチェック.
	 * 
	 * @param reqEntity
	 * @param resEntity
	 * @return Boolean
	 */
	public Boolean checkParameter(RegisterUserRequestEntity reqEntity, ResponseBaseEntity resEntity) {

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

		// ユーザID
		if (StringUtils.isEmpty(reqEntity.getUserId())) {
			resEntity.setMessage("userId is Empty");
			return false;
		}
		if (StringUtils.length(reqEntity.getUserName()) > CommonConst.USER_NAME_MAX_LENGTH) {
			resEntity.setMessage("userName is too Long");
			return false;
		}

		// パスワード
		if (StringUtils.isEmpty(reqEntity.getPassword())) {
			resEntity.setMessage("password is Empty");
			return false;
		}
		if (StringUtils.length(reqEntity.getPassword()) > CommonConst.PASSWORD_MAX_LENGTH) {
			resEntity.setMessage("password is too Long");
			return false;
		}

		// ユーザ名
		if (StringUtils.isEmpty(reqEntity.getUserName())) {
			resEntity.setMessage("userName is Empty");
			return false;
		}
		if (StringUtils.length(reqEntity.getUserName()) > CommonConst.USER_NAME_MAX_LENGTH) {
			resEntity.setMessage("userName is too Long");
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
	 * ユーザ重複チェック.
	 * 
	 * @param reqEntity
	 * @param resEntity
	 * @return Boolean
	 */
	public Boolean checkUserDuplicated(RegisterUserRequestEntity reqEntity, RegisterUserResponseEntity resEntity) {

		// ユーザIDは削除済みユーザとも重複不可
		UserMasterEntity entity = this.userMasterMapper.selectByPk(reqEntity.getUserId(), null);
		// ユーザID重複
		if (entity != null) {
			resEntity.setMessage("UserId Duplicated");
			resEntity.setUserIdDuplicatedFlg(CommonConst.FLG_ON);
			return false;
		}
		// ユーザ名は有効ユーザと重複不可
		Integer hitCnt = this.registerUserMapper.selectUserInfo(reqEntity.getUserName());
		// ユーザ名重複
		if (hitCnt > 0) {
			resEntity.setMessage("UserName Duplicated");
			resEntity.setUserIdDuplicatedFlg(CommonConst.FLG_ON);
			return false;
		}

		return true;
	}

	/**
	 * 招待コード適用.
	 * 
	 * @param reqEntity
	 * @param resEntity
	 * @return Boolean
	 */
	public Boolean useInvitationCode(RegisterUserRequestEntity reqEntity, RegisterUserResponseEntity resEntity) {

		// 招待コード適用
		Timestamp nowTimestamp = KdcCommonUtils.getNowTimestamp();
		InvitationManagementEntity rec = new InvitationManagementEntity();
		rec.setInvitationcode(reqEntity.getInvitationCd());
		rec.setGuestuserid(reqEntity.getUserId());
		rec.setGuesttelephonenumber(reqEntity.getDeviceInfo().getTelephoneNumber());
		rec.setExpirationdate(nowTimestamp);
		rec.setUpdateuserid(reqEntity.getUserId());
		rec.setUpdatedate(nowTimestamp);

		try {
			int count = this.registerUserMapper.updateInvitationCodeUsed(rec);

			// 更新件数0は招待コード適用失敗
			if (count == 0) {
				resEntity.setMessage("invitationCode is invalid");
				return false;
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * ユーザ登録.
	 * 
	 * @param reqEntity
	 * @param resEntity
	 * @return Boolean
	 */
	public Boolean registerUser(RegisterUserRequestEntity reqEntity, RegisterUserResponseEntity resEntity) {

		String groupId;
		String groupName;
		Integer authLevel;

		switch (AuthenticationModeEnum.valueOf(reqEntity.getMode())) {
		case LOGIN:
		case REGISTER_USER:
		default:
			// 招待コードに紐づいたグループIDと管理レベルを取得
			InvitationManagementEntity entity = this.invitationManagementMapper.selectByPk(reqEntity.getInvitationCd(),
					CommonConst.FLG_OFF);
			groupId = entity.getGroupid();
			authLevel = entity.getAuthlevel();
			break;
		case NEW_GROUP:
			// 新規グループ作成時は管理者で登録
			groupId = this.registerUserMapper.getNewGroupId();
			groupName = groupId + "グループ";
			authLevel = AuthLevelEnum.ADMIN.getCode();
			
			//グループマスタ登録
			GroupInfoEntity groupMasterRec = new GroupInfoEntity();
			groupMasterRec.setGroupid(groupId);
			groupMasterRec.setGroupname(groupName);
			groupMasterRec.setConfigparam1(String.valueOf(CommonConst.FLG_OFF));
			groupMasterRec.setConfigparam2(String.valueOf(CommonConst.FLG_OFF));
			groupMasterRec.setConfigparam3(String.valueOf(CommonConst.FLG_OFF));
			groupMasterRec.setConfigparam4(String.valueOf(CommonConst.FLG_OFF));
			groupMasterRec.setConfigparam5(String.valueOf(CommonConst.FLG_OFF));
			groupMasterRec.setRegisteruserid(reqEntity.getUserId());
			groupMasterRec.setRegisterdate(KdcCommonUtils.getNowTimestamp());
			
			try {
				this.groupMasterMapper.insert(groupMasterRec);
			} catch( Exception e) {
				return false;
			}
			break;
		}

		// ユーザシーケンス番号取得
		int userSeq = this.commonService.getUserIdSeq();

		// 表示色を取得
		UserColorSet userColorSet = KdcCommonUtils.getNewUserColorSet(userSeq);

		// ユーザ情報登録
		UserMasterEntity userMasterRec = new UserMasterEntity();
		userMasterRec.setUserid(reqEntity.getUserId());
		userMasterRec.setPassword(reqEntity.getPassword());
		userMasterRec.setUsername(reqEntity.getUserName());
		userMasterRec.setGroupid(groupId);
		userMasterRec.setAuthlevel(authLevel);
		userMasterRec.setLinecolor(userColorSet.getLineColor());
		userMasterRec.setMarkercolor(userColorSet.getMarkerColorId());
		userMasterRec.setDisporder(userSeq);
		userMasterRec.setRegisteruserid(reqEntity.getUserId());
		userMasterRec.setRegisterdate(KdcCommonUtils.getNowTimestamp());

		try {
			this.userMasterMapper.insert(userMasterRec);

			// レスポンス作成
			resEntity.setUserIdDuplicatedFlg(CommonConst.FLG_OFF);

			UserInfoEntity userInfo = new UserInfoEntity();
			userInfo.setUserId(reqEntity.getUserId());
			userInfo.setPassword(reqEntity.getPassword());
			userInfo.setUserName(reqEntity.getUserName());
			userInfo.setGroupId(groupId);
			userInfo.setAuthLevel(String.valueOf(authLevel));
			userInfo.setLineColor(userColorSet.getLineColor());
			userInfo.setMarkerColor(String.valueOf(userColorSet.getMarkerColorId()));
			resEntity.setUserInfo(userInfo);

			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
