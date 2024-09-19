package com.kdc.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kdc.common.entity.api.PushNotificationEntity;
import com.kdc.common.entity.api.base.UserInfoEntity;
import com.kdc.common.entity.db.PlaceAlertRecordEntity;
import com.kdc.common.entity.db.PlaceMasterEntityWrapper;
import com.kdc.common.entity.db.UserAlertRecordEntity;
import com.kdc.common.enums.ApiIdEnum;
import com.kdc.common.enums.PushNotificationEnum;
import com.kdc.mybatis.mapper.common.entity.PlaceAlertRecordMapper;
import com.kdc.mybatis.mapper.common.entity.UserAlertRecordMapper;

/**
 * Push通知サービス.
 */
@Service
public class PushNotificationService {

	private String apiKey;

	@Autowired
	private UserAlertRecordMapper userAlertRecordMapper;

	@Autowired
	private PlaceAlertRecordMapper placeAlertRecordMapper;

	/**
	 * APIキーをロードする.
	 */
	public void setApiKey() {
		this.apiKey = KdcCommonUtils.getProperty("pushApiKey");
	}

	/**
	 * 通知対象トークンIDに対してPush通知.
	 * 
	 * @param apiItem
	 *            API識別ID Enum
	 * @param pushItem
	 *            Push通知種別Enum
	 * @param user
	 *            ユーザ情報クラス
	 * @param place
	 *            場所マスタクラス
	 * @param notifyList
	 *            Push通知先クラスリスト
	 * @param counter
	 *            Push通知結果カウントクラス
	 * @param logger
	 *            ログ出力クラス
	 * @return 送信結果
	 */
	public Boolean sendAlert(ApiIdEnum apiItem, PushNotificationEnum pushItem, UserInfoEntity user,
			PlaceMasterEntityWrapper place, List<PushNotificationEntity> notifyList, PushNotificationCounter counter,
			Logger logger) {
		boolean completeFlg = false;
		Integer sendCompleteCnt = 0;
		Integer sendErrorCnt = 0;
		Integer sendExceptCnt = 0;

		for (PushNotificationEntity notify : notifyList) {
			if (StringUtils.isEmpty(notify.getTokenid())) {
				sendExceptCnt++;
				continue;
			}
			// Push通知作成＆送信
			boolean doPushResult;
			if (place != null) {
				doPushResult = doPush(apiItem, pushItem, user.getUserId(), user.getUserName(), true, place.getPlaceid(),
						place.getPlacename(), notify, logger);
			} else {
				doPushResult = doPush(apiItem, pushItem, user.getUserId(), user.getUserName(), false, null, null,
						notify, logger);
			}
			if (!doPushResult) {
				counter.getErrorTokenList().add(notify.getTokenid());
				sendErrorCnt++;
				continue;
			}
			sendCompleteCnt++;
			completeFlg = true;
		}
		counter.setSendCompleteCnt(sendCompleteCnt);
		counter.setSendErrorCnt(sendErrorCnt);
		counter.setSendExceptCnt(sendExceptCnt);
		// 1件でも正常送信できていれば送信済みと判定する
		if (completeFlg) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 通知対象トークンIDに対してPush通知.
	 * 
	 * @param apiItem
	 *            API識別ID Enum
	 * @param pushItem
	 *            Push通知種別Enum
	 * @param userInfo
	 *            ユーザ情報クラス
	 * @param notifyList
	 *            Push通知先クラスリスト
	 * @param counter
	 *            Push通知結果カウントクラス
	 * @param logger
	 *            ログ出力クラス
	 * @return
	 */
	public Boolean sendAlert(ApiIdEnum apiItem, PushNotificationEnum pushItem, UserInfoEntity userInfo,
			List<PushNotificationEntity> notifyList, PushNotificationCounter counter, Logger logger) {
		return sendAlert(apiItem, pushItem, userInfo, null, notifyList, counter, logger);
	}

	/**
	 * Push通知.
	 * 
	 * @param apiItem
	 *            API識別ID Enum
	 * @param pushItem
	 *            Push通知種別Enum
	 * @param userId
	 *            ユーザID
	 * @param userName
	 *            ユーザ名
	 * @param placeRequired
	 *            場所が必須かどうか
	 * @param placeId
	 *            場所ID
	 * @param placeName
	 *            場所名
	 * @param entity
	 *            Push通知クラス
	 * @param logger
	 *            ログ出力クラス
	 * @return
	 */
	public Boolean doPush(ApiIdEnum apiItem, PushNotificationEnum pushItem, String userId, String userName,
			boolean placeRequired, String placeId, String placeName, PushNotificationEntity entity, Logger logger) {
		if (StringUtils.isEmpty(this.apiKey)) {
			this.setApiKey();
		}

		StringBuilder json = new StringBuilder();

		json.append("{");
		json.append("\"to\":");
		json.append("\"").append(entity.getTokenid()).append("\"");
		json.append(", ");
		json.append("\"priority\":\"high\"");
		json.append(", ");

		// 主データ部
		json.append("\"data\":");
		json.append("{");
		json.append("\"apiId\":");
		json.append("\"").append(apiItem.getCode()).append("\"");
		json.append(", ");
		json.append("\"title\":");
		json.append("\"").append(CommonConst.KDC_SYSTEM_TITLE).append("\"");
		json.append(", ");
		json.append("\"message\":");
		json.append("\"").append(pushItem.getCode()).append("\"");
		json.append(", ");
		json.append("\"proccessKbn\":");
		json.append("\"").append(pushItem.getCode()).append("\"");
		json.append(", ");
		json.append("\"userId\":");
		json.append("\"").append(userId).append("\"");
		json.append(", ");
		json.append("\"userName\":");
		json.append("\"").append(userName).append("\"");
		json.append(", ");
		if (placeRequired) {
			json.append("\"placeId\":");
			json.append("\"").append(placeId).append("\"");
			json.append(", ");
			json.append("\"placeName\":");
			json.append("\"").append(placeName).append("\"");
			json.append(", ");
		}
		json.append("\"notification\":");
		json.append("\"").append(entity.getNotification()).append("\"");
		json.append(", ");
		json.append("\"popup\":");
		json.append("\"").append(entity.getPopup()).append("\"");
		json.append(", ");
		json.append("\"alarmTime\":");
		json.append("\"").append(entity.getAlarmtime()).append("\"");
		json.append(", ");
		json.append("\"vibrationTime\":");
		json.append("\"").append(entity.getVibrationtime()).append("\"");
		json.append("}");

		json.append("}");

		logger.info("JSON<" + json.toString() + ">");

		// Firebaseサーバに対しメッセージ送信
		try {
			HttpURLConnection connection = null;
			URL url = new URL("https://fcm.googleapis.com/fcm/send");
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setInstanceFollowRedirects(false);
			connection.setRequestProperty("Accept-Language", "jp");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			// FCM登録時のサーバーキー
			// SSEテスト用
			// String apiKey =
			// "AAAAIRlf0Wg:APA91bHf-kz6mcJPgvkEhdlrENDJHlDD6o-LDbTzMS_SLkbRJIayRpTxLW9Gcf-d19_4HB3_Tf863cPdSHlmm0ubkxwethbeHZD-rf4PwYlFBl6RzCr8YIMfJZLfPV55-7O_xJO4GJWn";
			// 検証環境用
			// String apiKey =
			// "AAAAO8yWyIk:APA91bHeXSUVsyz7Tvf_YeEqWhcbdW1VZsJhy7rYfMHFLJSBn9UsMKLltkOqWWn3czPQwff6GPNDQQ2kXJHWayB-WifNzYNzPYccBjwSq50Mgfy6BR526a6rCbLcVGiSlLryIvD8w5tI";
			// 本番用
			// String apiKey =
			// "AAAAPHnWkLs:APA91bGouJhDOiupP10x9C6hW-htmi-IajL2xvKOGEFnZP7swr-RYPh7iqtcuiBIXabimPSfXzIUgsrdCLNnwcDBOiJaPjIX08q880kGS2mx7_XFTxUT51Q5B3na66UvwwwwQF3AqOtt";
			connection.setRequestProperty("Authorization", "key=" + this.apiKey);
			logger.info(this.apiKey);
			logger.info(connection.toString());
			OutputStream os = connection.getOutputStream();
			PrintStream ps = new PrintStream(os);
			ps.print(json.toString());
			ps.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

			String buffer = reader.readLine();
			connection.disconnect();

			logger.info("results<" + buffer + ">");

			// Firebaseサーバからの返信を解析して成功/失敗を判定
			String[] resultItems = buffer.split(",", 0);
			boolean successFlg = false;
			boolean failureFlg = false;
			for (String resultItem : resultItems) {
				String[] resultKeyVal = resultItem.split(":", 0);
				if (StringUtils.equals(StringUtils.remove(resultKeyVal[0], '\"'), "success")) {
					if (Integer.parseInt(resultKeyVal[1]) > 0) {
						successFlg = true;
					}
				} else if (StringUtils.equals(StringUtils.remove(resultKeyVal[0], '\"'), "failure")) {
					if (Integer.parseInt(resultKeyVal[1]) > 0) {
						failureFlg = true;
					}
				}
			}
			if (!successFlg || failureFlg) {
				return false;
			}
			// ObjectMapper mapper = new ObjectMapper();
			// FirebaseResultInfo resultInfo = mapper.readValue(buffer,
			// FirebaseResultInfo.class);
			/***
			 * JSONArray jsonArray = new JSONArray(buffer); for (int i = 0; i <
			 * jsonArray.length(); i++) { JSONObject jsonObject =
			 * jsonArray.getJSONObject(i); java.lang.System.out.println(
			 * "HTTP REQ" + jsonObject.getString("name")); }
			 ***/
		} catch (MalformedURLException e) {
			// e.printStackTrace();
			logger.info(e.getMessage());
		} catch (IOException e) {
			// e.printStackTrace();
			logger.info(e.getMessage());
		}

		return true;
	}

	/**
	 * 最終通知発信日時登録/更新.
	 * 
	 * @param pushItem
	 *            Push通知種別Enum
	 * @param userId
	 *            ユーザID
	 * @return 登録/更新結果判定
	 */
	public Boolean upsertUserAlertRecord(PushNotificationEnum pushItem, String userId) {

		UserAlertRecordEntity rec = new UserAlertRecordEntity();

		Timestamp nowTimestamp = KdcCommonUtils.getNowTimestamp();

		switch (pushItem) {
		case SOS:
			rec.setLastsosalertdate(nowTimestamp);
			break;
		case BATTERY:
			rec.setLastbatteryalertdate(nowTimestamp);
			break;
		case DISCONNECT:
			rec.setLastdisconnectionalertdate(nowTimestamp);
			break;
		case RECONNECT:
			rec.setLastreconnectionalertdate(nowTimestamp);
			break;
		default:
			return false;
		}

		rec.setUserid(userId);
		rec.setRegisteruserid(userId);
		rec.setRegisterdate(nowTimestamp);
		rec.setUpdateuserid(userId);
		rec.setUpdatedate(nowTimestamp);
		userAlertRecordMapper.upsert(rec);

		return true;
	}

	/**
	 * 最終通知発信日時登録/更新.
	 * 
	 * @param pushItem
	 *            Push通知種別Enum
	 * @param userId
	 *            ユーザID
	 * @param placeId
	 *            場所ID
	 * @return 登録/更新結果判定
	 */
	public Boolean upsertPlaceAlertRecord(PushNotificationEnum pushItem, String userId, String placeId) {

		PlaceAlertRecordEntity rec = new PlaceAlertRecordEntity();

		Timestamp nowTimestamp = KdcCommonUtils.getNowTimestamp();

		switch (pushItem) {
		case PLACE_NORMAL_IN:
		case PLACE_DANGER_IN:
			rec.setLastplaceinalertdate(nowTimestamp);
			break;
		case PLACE_NORMAL_OUT:
		case PLACE_DANGER_OUT:
			rec.setLastplaceoutalertdate(nowTimestamp);
			break;
		default:
			return false;
		}

		rec.setUserid(userId);
		rec.setPlaceid(placeId);
		rec.setRegisteruserid(userId);
		rec.setRegisterdate(nowTimestamp);
		rec.setUpdateuserid(userId);
		rec.setUpdatedate(nowTimestamp);
		placeAlertRecordMapper.upsert(rec);

		return true;
	}

}
