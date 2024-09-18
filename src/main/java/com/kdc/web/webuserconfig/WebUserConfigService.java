package com.kdc.web.webuserconfig;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import com.kdc.common.entity.api.PushNotificationEntity;
import com.kdc.common.entity.api.base.UserInfoEntity;
import com.kdc.common.entity.db.UserMasterEntity;
import com.kdc.common.enums.ApiIdEnum;
import com.kdc.common.enums.PushNotificationEnum;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.KdcCommonUtils;
import com.kdc.common.util.PushNotificationCounter;
import com.kdc.common.util.PushNotificationService;
import com.kdc.mybatis.mapper.common.entity.UserMasterMapper;
import com.kdc.mybatis.mapper.common.util.PushNotificationMapper;
import com.kdc.web.common.error.ComErrorInfoData.ErrorInfo;
import com.kdc.web.common.WebLoginInfoHolder;
import com.kdc.web.common.error.ComErrorInfoService;

/**
 * ユーザ設定画面 Service クラス
 */
@Service
public class WebUserConfigService {

	private static final Logger logger = LoggerFactory.getLogger(WebUserConfigService.class);

	@Autowired
	private WebLoginInfoHolder loginInfoHolder;

	@Autowired
	private PushNotificationMapper pushNotificationMapper;

	@Autowired
	private UserMasterMapper userMasterMapper;

	@Autowired
	private ComErrorInfoService comErrorInfoService;

	@Autowired
	private PushNotificationService pushNotificationService;

	/**
	 * 初期処理.
	 * 
	 * @param form
	 * 			ユーザ設定画面フォーム
	 */
	public void initUser(Model model, WebUserConfigForm form) {
		Map<String, String> cmbUser = new LinkedHashMap<>();
		// ユーザマスタからユーザＩＤ一覧を取得し、コンボボックスに設定
		List<UserMasterEntity> userList = userMasterMapper.selectAll(CommonConst.FLG_OFF);
		int selectedIndex = 0;
		for (int cntCombo = 0; cntCombo < userList.size(); cntCombo++) {
			// 長いユーザ名は省略表示
			StringBuffer userName = new StringBuffer(userList.get(cntCombo).getUsername());
			if (userName.toString().getBytes(Charset.forName("Shift_JIS")).length > 20) {
				while (userName.toString().getBytes(Charset.forName("Shift_JIS")).length > 20) {
					userName.deleteCharAt(userName.length() - 1);
				}
				userName.append("...");
			}
			cmbUser.put(userList.get(cntCombo).getUserid(), userName.toString());
			if (userList.get(cntCombo).getUserid().equals(form.getSelectedUserId())) {
				selectedIndex = cntCombo;
			}
		}
		form.setCmbUser(form.getSelectedUserId());
		form.setSelectedUser(selectedIndex);
		model.addAttribute("cmbUser", cmbUser);

		this.setFormUserData(form, userList.get(form.getSelectedUser()));
	}

	/**
	 * フォームのユーザ情報をセットする
	 * 
	 * @param form
	 * 			ユーザ設定画面フォーム
	 * @param user
	 * 			ユーザマスタクラス
	 */
	private void setFormUserData(WebUserConfigForm form, UserMasterEntity user) {
		// ユーザＩＤ
		form.setUserId(user.getUserid());
		// パスワード
		form.setPassword(user.getPassword());
		// ユーザ名
		form.setUserName(user.getUsername());
		// アイコンID
		form.setIconId(user.getIconid());
		// アイコン画像ファイル
		if (StringUtils.isNotEmpty(user.getIconid())) {
			String iconStringBase64 = KdcCommonUtils.getIconStringBase64(user.getIconfile());
			form.setIconDataString(CommonConst.ICON_IMG_HEADER + iconStringBase64);
			form.setSelectedIconDataString(iconStringBase64);
		} else {
			form.setIconDataString("");
		}
		// グループＩＤ
		form.setGroupId(user.getGroupid());
		// 管理レベル
		form.setAuthLevel(String.valueOf(user.getAuthlevel()));
		// ライン色指定透過度
		form.setLineColorAlpha(String.valueOf(Integer.parseInt(StringUtils.substring(user.getLinecolor(), 0, 2), 16)));
		// ライン色指定
		form.setLineColor("#" + StringUtils.substring(user.getLinecolor(), 2));
		// マーカー色指定
		form.setMarkerColor(String.valueOf(user.getMarkercolor()));
	}

	/**
	 * エラーチェック.
	 * 
	 * @param form
	 * 			ユーザ設定画面フォーム
	 * @param errorInfoList
	 * 			エラー情報クラスリスト
	 */
	public void errorCheck(WebUserConfigForm form, List<ErrorInfo> errorInfoList) {
		// エラーチェック処理(共通化できないエラー処理は独自に実装する)
		this.comErrorInfoService.errorInfoInit();

		// 必須チェック
		this.comErrorInfoService.requiredErrorCheck("ユーザ名", form.getUserName(), "userName");
		this.comErrorInfoService.requiredErrorCheck("パスワード", form.getPassword(), "password");
		// 文字数チェック
		this.comErrorInfoService.stringSizeErrorCheck("ユーザ名", form.getUserName(), CommonConst.USER_NAME_MAX_LENGTH,
				"userName");
		this.comErrorInfoService.stringSizeErrorCheck("パスワード", form.getPassword(), CommonConst.PASSWORD_MAX_LENGTH,
				"password");
		this.comErrorInfoService.stringSizeErrorCheck("グループＩＤ", form.getGroupId(), CommonConst.GROUP_ID_MAX_LENGTH,
				"groupId");

		errorInfoList.addAll(this.comErrorInfoService.getErrorInfoList());
	}

	/**
	 * ユーザマスタ登録
	 * 
	 * @param webUserForm
	 * 			ユーザ設定画面フォーム
	 */
	public void registUserMaster(WebUserConfigForm webUserForm) {
		UserMasterEntity registConfigEntity = new UserMasterEntity();

		boolean iconUpdate = false;
		Timestamp nowTimestamp = KdcCommonUtils.getNowTimestamp();

		// 各項目を設定
		registConfigEntity.setUserid(webUserForm.getUserId());
		registConfigEntity.setPassword(webUserForm.getPassword());
		registConfigEntity.setUsername(webUserForm.getUserName());
		// ファイルが設定（変更）されている場合はアップロード処理＋プッシュ通知
		if (webUserForm.getIconImg() != null
				&& StringUtils.isNotEmpty(webUserForm.getIconImg().getOriginalFilename())) {
			try {
				iconUpdate = true;
				registConfigEntity.setIconid(webUserForm.getUserId());
				// アイコン用にリサイズして保存
				registConfigEntity.setIconfile(KdcCommonUtils.scaleImage(webUserForm.getIconImg().getBytes(),
						CommonConst.ICON_WIDTH, CommonConst.ICON_HEIGHT, "png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		registConfigEntity.setGroupid(webUserForm.getGroupId());
		registConfigEntity.setAuthlevel(Integer.parseInt(webUserForm.getAuthLevel().replace("authLevelValue", "")));
		registConfigEntity.setLinecolor(String.format("%02x", Integer.parseInt(webUserForm.getLineColorAlpha()))
				+ StringUtils.substring(webUserForm.getLineColor(), 1));
		registConfigEntity.setMarkercolor(Integer.parseInt(webUserForm.getMarkerColor()));
		registConfigEntity.setUpdateuserid(this.loginInfoHolder.getLoginId());
		registConfigEntity.setUpdatedate(nowTimestamp);

		// UPDATEを行う
		userMasterMapper.update(registConfigEntity);

		if (iconUpdate) {
			PushNotificationCounter counter = new PushNotificationCounter();
			// アイコン登録通知（Push通知）送信
			this.sendPushNotification(webUserForm, counter);
		}
	}

	/**
	 * アイコン登録Push通知.
	 * 
	 * @param form
	 * 			ユーザ設定画面フォーム
	 * @param counter
	 * 			Push通知結果カウントクラス
	 */
	private void sendPushNotification(WebUserConfigForm form, PushNotificationCounter counter) {
		UserInfoEntity userInfo = new UserInfoEntity();
		userInfo.setUserId(form.getUserId());
		userInfo.setUserName(form.getUserName());

		// アイコン変更したユーザの表示を行うユーザに通知する（対象ユーザ自身を含む）
		List<PushNotificationEntity> notifyList = this.pushNotificationMapper
				.selectSendIconTokenIdList(form.getUserId(), false);
		this.pushNotificationService.setApiKey();
		this.pushNotificationService.sendAlert(ApiIdEnum.valueOf(CommonConst.API_ID_SERVER_PROCESS),
				PushNotificationEnum.USERICON, userInfo, notifyList, counter, logger);
		logger.info("ユーザアイコン変更Push通知 OK:" + counter.getSendCompleteCnt() + " NG:" + counter.getSendErrorCnt()
				+ " TokenID未設定:" + counter.getSendExceptCnt());
		for (String token : counter.getErrorTokenList()) {
			logger.info("Send Error:" + token);
		}
	}

	/**
	 * 削除処理
	 * 
	 * @param model
	 * @param form
	 * 			ユーザ設定画面フォーム
	 */
	public void deleteUser(Model model, WebUserConfigForm form) {
		UserMasterEntity rec = new UserMasterEntity();

		rec.setUserid(form.getUserId());
		rec.setUpdateuserid(this.loginInfoHolder.getLoginId());
		rec.setUpdatedate(KdcCommonUtils.getNowTimestamp());
		userMasterMapper.delete(rec);

		// 対象ユーザが削除されるため再取得
		Map<String, String> cmbUser = new LinkedHashMap<>();
		// ユーザマスタからユーザＩＤ一覧を取得し、コンボボックスに設定
		List<UserMasterEntity> userList = userMasterMapper.selectAll(CommonConst.FLG_OFF);
		for (UserMasterEntity entity : userList) {
			cmbUser.put(entity.getUserid(), entity.getUsername());
		}
		model.addAttribute("cmbUser", cmbUser);

		// 末尾のユーザを削除した場合は前にずれる
		int selectedIndex = form.getSelectedUser();
		if (selectedIndex > userList.size() - 1) {
			selectedIndex--;
		}
		form.setSelectedUser(selectedIndex);

		this.setFormUserData(form, userList.get(form.getSelectedUser()));
	}
}