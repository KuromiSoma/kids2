package com.kdc.mybatis.mapper.common.entity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kdc.common.entity.db.PlaceAlertRecordEntity;

/**
 * 場所通知履歴テーブル Mapperクラス
 *
 */
@Mapper
public interface PlaceAlertRecordMapper {

	/**
	 * 全件取得.
	 * 
	 * @return 場所通知履歴テーブルクラスリスト
	 */
	public List<PlaceAlertRecordEntity> selectAll();

	/**
	 * 全件取得.
	 * 
	 * @param delflg
	 * 			削除フラグ
	 * @param sort1
	 * 			ソート判定1
	 * @param sort2
	 * 			ソート判定2
	 * @return 場所通知履歴テーブルクラスリスト
	 */
	public List<PlaceAlertRecordEntity> selectAll(@Param("delflg") Integer delflg, @Param("sort1") boolean sort1,
			@Param("sort2") boolean sort2);

	/**
	 * プライマリキーを指定して取得.
	 * 
	 * @param userid
	 * 			ユーザID
	 * @param placeid
	 * 			場所ID
	 * @param delflg
	 * 			削除フラグ
	 * @return 場所通知履歴テーブルクラス
	 */
	public PlaceAlertRecordEntity selectByPk(@Param("userid") String userid, @Param("placeid") String placeid,
			@Param("delflg") Integer delflg);

	/**
	 * 追加/更新.
	 * 
	 * @param rec
	 * 			場所通知履歴テーブルクラス
	 * @return 追加/更新件数
	 */
	public int upsert(@Param("rec") PlaceAlertRecordEntity rec);

}
