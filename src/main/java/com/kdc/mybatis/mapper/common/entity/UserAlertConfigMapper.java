package com.kdc.mybatis.mapper.common.entity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kdc.common.entity.db.UserAlertConfigEntity;

/**
 * ユーザ通知設定テーブル Mapperクラス
 *
 */
@Mapper
public interface UserAlertConfigMapper {

	/**
	 * 全件取得.
	 * 
	 * @return ユーザ通知設定テーブルクラスリスト
	 */
	public List<UserAlertConfigEntity> selectAll();

	/**
	 * 全件取得.
	 * 
	 * @param delflg
	 * 			削除フラグ
	 * @param sort1
	 * 			ソート判定1
	 * @param sort2
	 * 			ソート判定2
	 * @return ユーザ通知設定テーブルクラスリスト
	 */
	public List<UserAlertConfigEntity> selectAll(@Param("delflg") Integer delflg, @Param("sort1") boolean sort1,
			@Param("sort2") boolean sort2);

	/**
	 * プライマリキーを指定して取得.
	 * 
	 * @param authlevel
	 * 			管理レベル
	 * @param notificationauthlevel
	 * 			通知管理レベル
	 * @param delflg
	 * 			削除フラグ
	 * @return ユーザ通知設定テーブルクラス
	 */
	public UserAlertConfigEntity selectByPk(@Param("authlevel") String authlevel,
			@Param("notificationauthlevel") String notificationauthlevel, @Param("delflg") Integer delflg);

	/**
	 * 追加.
	 * 
	 * @param rec
	 * 			ユーザ通知設定テーブルクラス
	 * @return 追加件数 
	 */
	public int insert(@Param("rec") UserAlertConfigEntity rec);

	/**
	 * 追加/更新.
	 * 
	 * @param rec
	 * 			ユーザ通知設定テーブルクラス
	 * @return 追加/更新件数 
	 */
	public int upsert(@Param("rec") UserAlertConfigEntity rec);

}
