package com.kdc.mybatis.mapper.api;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kdc.common.entity.db.PlaceAlertConfigEntity;
import com.kdc.common.entity.db.SendIntervalConfigEntity;
import com.kdc.common.entity.db.UserAlertConfigEntity;
import com.kdc.common.entity.db.UserDeviceEntity;

/**
 * 設定取得API Mapperクラス
 *
 */
@Mapper
public interface GetConfigMapper {

	/**
	 * 送信間隔設定情報取得
	 * @return 送信間隔設定テーブルクラス
	 */
	public List<SendIntervalConfigEntity> getSendIntervalConfigList(@Param("groupid") String groupid);

	/**
	 * ユーザ通知設定情報取得
	 * @param 管理レベル
	 * @return ユーザ通知設定テーブルクラス
	 */
	public List<UserAlertConfigEntity> getUserAlertConfigList(@Param("authlevel") int authlevel,@Param("groupid") String groupid);

	/**
	 * 場所通知設定情報取得
	 * @param 管理レベル
	 * @return 場所通知設定テーブルクラス
	 */
	public List<PlaceAlertConfigEntity> getPlaceAlertConfigList(@Param("authlevel") int authlevel,@Param("groupid") String groupid);

	/**
	 * 設定同期日時更新
	 * @param ユーザ端末情報クラス
	 * @return 更新件数
	 */
	public int updateUserDevice(@Param("rec") UserDeviceEntity rec);

}
