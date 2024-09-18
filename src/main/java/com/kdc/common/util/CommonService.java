package com.kdc.common.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import com.kdc.common.entity.api.base.DeviceInfoCoreEntity;
import com.kdc.common.entity.api.base.RequestBaseEntity;
import com.kdc.common.entity.api.base.ResponseBaseEntity;
import com.kdc.common.entity.api.base.UserInfoCoreEntity;
import com.kdc.common.entity.db.InvitationManagementEntity;
import com.kdc.common.entity.db.UserDeviceEntity;
import com.kdc.common.entity.db.UserMasterEntity;
import com.kdc.common.enums.AuthLevelEnum;
import com.kdc.mybatis.mapper.common.entity.InvitationManagementMapper;
import com.kdc.mybatis.mapper.common.entity.UserDeviceMapper;
import com.kdc.mybatis.mapper.common.entity.UserMasterMapper;
import com.kdc.mybatis.mapper.common.util.CommonServiceMapper;

/**
 * 共通サービスクラス.
 */
@Service
public class CommonService {

	@Autowired
	private UserMasterMapper userMasterMapper;

	@Autowired
	private UserDeviceMapper userDeviceMapper;

	@Autowired
	private InvitationManagementMapper invitationManagementMapper;

	@Autowired
	private CommonServiceMapper commonServiceMapper;

	@Autowired
	ResourceLoader resourceLoader;

	/**
	 * 共通パラメータチェック.
	 * 
	 * @param reqEntity
	 * @param resEntity
	 * @return Boolean
	 */
	public Boolean checkBaseParameter(RequestBaseEntity reqEntity, ResponseBaseEntity resEntity) {

		// ユーザ情報
		if (reqEntity.getUserInfo() == null) {
			resEntity.setMessage("userInfo is Empty");
			return false;
		}
		// 単項目チェック
		if (!this.checkUserInfoValues(reqEntity.getUserInfo(), resEntity)) {
			return false;
		}
		// 存在チェック
		if (!this.checkUserExists(reqEntity.getUserInfo(), resEntity)) {
			return false;
		}

		// 端末情報
		if (reqEntity.getDeviceInfo() == null) {
			resEntity.setMessage("deviceInfo is Empty");
			return false;
		}
		// 単項目チェック
		if (!this.checkDeviceInfoValues(reqEntity.getDeviceInfo(), resEntity)) {
			return false;
		}
		// 存在チェック
		if (!this.checkDeviceExists(reqEntity.getDeviceInfo(), resEntity)) {
			return false;
		}

		return true;
	}

	/**
	 * ユーザ情報単項目チェック.
	 * 
	 * @param userInfoCore
	 * @param resEntity
	 * @return Boolean
	 */
	public Boolean checkUserInfoValues(UserInfoCoreEntity userInfoCore, ResponseBaseEntity resEntity) {
		// 必須チェック
		if (StringUtils.isEmpty(userInfoCore.getUserId())) {
			resEntity.setMessage("userInfo.userId is Empty");
			return false;
		}
		if (StringUtils.isEmpty(userInfoCore.getGroupId())) {
			resEntity.setMessage("userInfo.groupId is Empty");
			return false;
		}
		if (StringUtils.isEmpty(userInfoCore.getAuthLevel())) {
			resEntity.setMessage("userInfo.authLevel is Empty");
			return false;
		}

		return true;
	}

	/**
	 * ユーザ存在チェック.
	 * 
	 * @param userInfo
	 * @param resEntity
	 * @return Boolean
	 */
	public Boolean checkUserExists(UserInfoCoreEntity userInfo, ResponseBaseEntity resEntity) {
		// ユーザ情報変更時に対応するためIDのみで認証
		UserMasterEntity entity = this.userMasterMapper.selectByPk(userInfo.getUserId(), CommonConst.FLG_OFF);
		if (entity == null) {
			resEntity.setMessage("userInfo not exists");
			return false;
		}
		return true;
	}

	/**
	 * 管理者権限判定.
	 * 
	 * @param userInfo
	 * @param resEntity
	 * @return Boolean
	 */
	public Boolean isAdminUser(UserInfoCoreEntity userInfo, ResponseBaseEntity resEntity) {
		if (!StringUtils.equals(userInfo.getAuthLevel(), Integer.toString(AuthLevelEnum.ADMIN.getCode()))) {
			resEntity.setMessage("unauthorized user");
			return false;
		}
		UserMasterEntity entity = this.userMasterMapper.selectUser(userInfo.getUserId(), userInfo.getPassword(),
				CommonConst.FLG_OFF);
		if (entity == null) {
			resEntity.setMessage("userInfo not exists");
			return false;
		}
		if (!AuthLevelEnum.ADMIN.equals(AuthLevelEnum.valueOf(entity.getAuthlevel()))) {
			resEntity.setMessage("unauthorized user");
			return false;
		}
		return true;
	}

	/**
	 * 端末情報単項目チェック.
	 * 
	 * @param deviceInfoCore
	 * @param resEntity
	 * @return Boolean
	 */
	public Boolean checkDeviceInfoValues(DeviceInfoCoreEntity deviceInfoCore, ResponseBaseEntity resEntity) {
		// 必須チェック
		if (StringUtils.isEmpty(deviceInfoCore.getTelephoneNumber())) {
			resEntity.setMessage("deviceInfo.telephoneNumber is Empty");
			return false;
		}
		if (StringUtils.isEmpty(deviceInfoCore.getDeviceId())) {
			resEntity.setMessage("deviceInfo.deviceId is Empty");
			return false;
		}

		// 電話番号チェック
//2018/1/12 DEL umemoto SIMなしの場合は端末ID（15桁）が入るため。
//		if (StringUtils.length(deviceInfoCore.getTelephoneNumber()) != CommonConst.TELEPHONE_NUMBER_LENGTH) {
//			resEntity.setMessage("deviceInfo.telephoneNumber is Wrong");
//			return false;
//		}

		return true;
	}

	/**
	 * 端末存在チェック.
	 * 
	 * @param deviceInfo
	 * @param resEntity
	 * @return Boolean
	 */
	public Boolean checkDeviceExists(DeviceInfoCoreEntity deviceInfo, ResponseBaseEntity resEntity) {
		UserDeviceEntity entity = this.userDeviceMapper.selectDevice(deviceInfo.getTelephoneNumber(),
				CommonConst.FLG_OFF);
		if (entity != null && KdcCommonUtils.nullSafeEquals(deviceInfo.getUserId(), entity.getUserid())
				&& KdcCommonUtils.nullSafeEquals(deviceInfo.getDeviceId(), entity.getDeviceid())) {
			return true;
		}
		resEntity.setMessage("deviceInfo not exists");
		return false;
	}

	/**
	 * 既存ユーザと重複しないように色の文字列リストを取得する.
	 *
	 * @param count
	 *            要求件数
	 * @return RGB値を16進表現した文字列のリスト
	 */
	public List<String> getNewColors(int count) {
		String[] rgb = { "00", "33", "66", "99", "CC", "FF" };
		String red = "";
		String green = "";
		String blue = "";
		List<String> colorList = new ArrayList<>();
		List<String> usedColorList = new ArrayList<>();
		StringBuilder colorCode = new StringBuilder();
		Random rand = new SecureRandom();

		// 使用されているライン表示色のリストを取得
		List<UserMasterEntity> list = this.userMasterMapper.selectAll(CommonConst.FLG_OFF);
		for (UserMasterEntity entity : list) {
			usedColorList.add(entity.getLinecolor());
		}

		for (int i = 0; i < count; i++) {
			while (true) {
				// RED
				red = rgb[rand.nextInt(6)];
				// GREEN
				green = rgb[rand.nextInt(6)];
				// BLUE
				blue = rgb[rand.nextInt(6)];
				// グレースケール色（R/G/Bの値が同一）は除外するのでやり直し
				if (StringUtils.equals(red, green) && StringUtils.equals(red, blue)) {
					continue;
				}
				colorCode.append(red).append(green).append(blue);
				// 色が重複していないことを確認
				for (String usedColor : usedColorList) {
					if (StringUtils.equals(colorCode, usedColor)) {
						continue;
					}
				}
				break;
			}
			colorList.add(colorCode.toString());
			// 使用中の色として追加
			usedColorList.add(colorCode.toString());
			colorCode.setLength(0); // 複数の色を取得すると落ちるので、一旦クリア
		}

		return colorList;
	}

	/**
	 * 招待コードを生成する.
	 * 
	 * @return
	 */
	public String createInvitationCode() {
		List<String> usedCodeList = new ArrayList<>();
		StringBuilder invitationCode = new StringBuilder();
		Random rand = new SecureRandom();

		// 使用されているコードのリストを取得
		List<InvitationManagementEntity> list = this.invitationManagementMapper.selectValidCode();
		for (InvitationManagementEntity entity : list) {
			usedCodeList.add(entity.getInvitationcode());
		}

		while (true) {
			for (int i = 0; i < CommonConst.INVITATION_CODE_LENGTH; i++) {
				invitationCode.append(rand.nextInt(10));
			}
			// コード重複していないことを確認
			for (String usedCode : usedCodeList) {
				if (StringUtils.equals(invitationCode, usedCode)) {
					continue;
				}
			}
			break;
		}

		return invitationCode.toString();
	}

	/**
	 * ユーザシーケンス番号取得.
	 * 
	 * @return
	 */
	public int getUserIdSeq() {
		return this.commonServiceMapper.getUserIdSeq();
	}

	/**
	 * 場所ID発番.
	 * 
	 * @return
	 */
	public String createPlaceId() {
		return CommonConst.ID_HEADER_PLACE + String.format("%012d", this.commonServiceMapper.getPlaceIdSeq());
	}

	/**
	 * グループID発番.
	 * 
	 * @return
	 */
	public String createGroupId() {
		return String.valueOf(this.commonServiceMapper.getGroupIdSeq());
	}

}