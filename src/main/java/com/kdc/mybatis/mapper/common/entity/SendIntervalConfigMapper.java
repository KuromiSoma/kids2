package com.kdc.mybatis.mapper.common.entity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kdc.common.entity.db.SendIntervalConfigEntity;

/**
 * 送信間隔設定テーブル Mapperクラス
 *
 */
@Mapper
public interface SendIntervalConfigMapper {

	/**
	 * 全件取得.
	 * 
	 * @return 送信間隔設定テーブルクラスリスト
	 */
	public List<SendIntervalConfigEntity> selectAll();

	/**
	 * 全件取得.
	 * 
	 * @param delflg
	 * 			削除フラグ
	 * @param sort1
	 * 			ソート判定1
	 * @param sort2
	 * 			ソート判定2
	 * @param sort3
	 * 			ソート判定3
	 * @return 送信間隔設定テーブルクラスリスト
	 */
	public List<SendIntervalConfigEntity> selectAll(@Param("delflg") Integer delflg, @Param("sort1") boolean sort1,
			@Param("sort2") boolean sort2, @Param("sort3") boolean sort3);

	/**
	 * プライマリキーを指定して取得.
	 * 
	 * @param authlevel
	 * 			管理レベル
	 * @param dayofweek
	 * 			曜日区分
	 * @param configno
	 * 			設定番号
	 * @param delflg
	 * 			削除フラグ
	 * @return 送信間隔設定テーブルクラス
	 */
	public SendIntervalConfigEntity selectByPk(@Param("authlevel") String authlevel,
			@Param("dayofweek") String dayofweek, @Param("configno") String configno, @Param("delflg") Integer delflg);

	/**
	 * 追加.	
	 *  
	 * @param rec
	 * 			送信間隔設定テーブルクラス
	 * @return 追加件数 
	 */
	public int insert(@Param("rec") SendIntervalConfigEntity rec);

	/**
	 * 追加/更新.
	 * 
	 * @param rec
	 * 			送信間隔設定テーブルクラス
	 * @return 追加/更新件数 
	 */
	public int upsert(@Param("rec") SendIntervalConfigEntity rec);

}
