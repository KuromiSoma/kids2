package com.kdc.mybatis.mapper.common.util;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kdc.common.entity.KdcAlertEntity;

/**
 * キッズコントロール 常駐タスク処理Mapperクラス
 * 
 */
@Mapper
public interface KdcScheduledMapper {

	/**
	 * 送信間隔設定が有効になっているか判定
	 * 
	 * @param dayofweek
	 * 			曜日区分
	 * @param date
	 * 			現在日時
	 * @return  該当件数
	 */
	public int searchUsingSendInterval(@Param("dayofweek") int dayofweek, @Param("date") Timestamp date, @Param("groupid") String groupid);

	/**
	 * 接続切断判定.
	 * 
	 * @param dayofweek
	 * 			曜日区分
	 * @param date
	 * 			現在日時
	 * @param updateuserid
	 * 			更新ユーザID
	 * @return 更新件数
	 */
	public int updateDisconnect(@Param("dayofweek") int dayofweek, @Param("date") Timestamp date,
			@Param("updateuserid") String updateuserid, @Param("groupid") String groupid);

	/**
	 * 接続切断通知対象ユーザを取得.
	 * 
	 * @param dayofweek
	 * 			曜日区分
	 * @param date
	 * 			現在日時
	 * @param disconnecttime
	 * 			切断時間
	 * @return 通知クラスリスト
	 */
	public List<KdcAlertEntity> searchUserOnDisconnectionAlert(@Param("dayofweek") int dayofweek,
			@Param("date") Timestamp date, @Param("disconnecttime") int disconnecttime);

	/**
	 * 接続回復通知対象ユーザを取得.
	 * 
	 * @param dayofweek
	 * 			曜日区分
	 * @param date
	 * 			現在日時
	 * @return 通知クラスリスト
	 */
	public List<KdcAlertEntity> searchUserOnReconnectionAlert(@Param("dayofweek") int dayofweek,
			@Param("date") Timestamp date);

}
