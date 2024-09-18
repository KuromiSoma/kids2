package com.kdc.mybatis.mapper.api;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.kdc.common.entity.db.UserDeviceEntity;
import com.kdc.common.entity.db.UserLocationRecordEntityWrapper;

/**
 * 位置情報送信API Mapperクラス
 *
 */
@Mapper
public interface SendLocationMapper {

	/**
	 * 最新履歴取得
	 * @param ユーザID
	 * @return ユーザ位置履歴テーブルクラス
	 */
	public UserLocationRecordEntityWrapper selectLastRecord(@Param("userid") String userid);

	/**
	 * 設定同期日時取得
	 * @param userid
	 * @return Timestamp
	 */
	public Timestamp selectConfigSyncDate(@Param("userid") String userid);

	/**
	 * ユーザマスタ更新日時取得
	 * @param userid
	 * @return Timestamp
	 */
	public Timestamp selectUpdateDateUserMaster(@Param("userid") String userid);

	/**
	 * ユーザ通知更新日時最新取得
	 * @param userid
	 * @return Timestamp
	 */
	public Timestamp selectMaxUpdUserAlertConfig(@Param("userid") String userid);

	/**
	 * 送信間隔更新日時最新取得
	 * @param userid
	 * @return Timestamp
	 */
	public Timestamp selectMaxUpdSendIntervalConfig(@Param("userid") String userid);

	/**
	 * 場所通知更新日時最新取得
	 * @param userid
	 * @return Timestamp
	 */
	public Timestamp selectMaxUpdPlaceAlertConfig(@Param("userid") String userid);

	/**
	 * 設定マスタ更新日時最新取得
	 * @return Timestamp
	 */
	public Timestamp selectMaxUpdConfigMaster(@Param("groupid") String groupid);

	/**
	 * ユーザ端末テーブル更新（トークンID反映）
	 * @param rec
	 * @return 更新件数
	 */
	public int updateUserDevice(@Param("rec") UserDeviceEntity rec);

}
