package com.kdc.mybatis.mapper.common.util;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kdc.common.entity.api.PushNotificationEntity;

/**
 * Push通知Mapperクラス
 * 
 */
@Mapper
public interface PushNotificationMapper {

	/**
	 * アイコン変更通知対象ユーザのトークンIDのリストを取得
	 * 
	 * @param userid
	 * 			ユーザID
	 * @param withoutMyself
	 * 			ユーザ判定
	 * @return Push通知クラスリスト
	 */
	public List<PushNotificationEntity> selectSendIconTokenIdList(@Param("userid") String userid,
			@Param("withoutMyself") boolean withoutMyself);

	/**
	 * バッテリー残量通知対象ユーザのトークンIDのリストを取得
	 * @param userid
	 * 			ユーザID
	 * @param batterylevel
	 * 			バッテリー残量
	 * @return Push通知クラスリスト
	 */
	public List<PushNotificationEntity> selectBatteryAlertTokenIdList(@Param("userid") String userid,
			@Param("batterylevel") int batterylevel);

	/**
	 * 接続切断通知対象ユーザのトークンIDのリストを取得
	 * 
	 * @param authlevel
	 * 			管理レベル
	 * @return Push通知クラスリスト
	 */
	public List<PushNotificationEntity> selectConnectionAlertTokenIdListByAuthLevel(@Param("authlevel") int authlevel, @Param("groupid") String groupid);

	/**
	 * SOS通知対象ユーザのトークンIDのリストを取得
	 * 
	 * @param userid
	 * 			ユーザID
	 * @return Push通知クラスリスト
	 */
	public List<PushNotificationEntity> selectSosAlertTokenIdList(@Param("userid") String userid);

	/**
	 * 場所通知対象ユーザのトークンIDのリストを取得
	 * @param userid
	 * @param placeid
	 * @return
	 */
	public List<PushNotificationEntity> selectPlaceAlertTokenIdList(@Param("userid") String userid,
			@Param("placeid") String placeid);

	/**
	 * 設定変更通知対象ユーザ（有効なユーザ全員）のトークンIDのリストを取得
	 * @return
	 */
	public List<PushNotificationEntity> selectChangeConfigTokenIdList();
}
