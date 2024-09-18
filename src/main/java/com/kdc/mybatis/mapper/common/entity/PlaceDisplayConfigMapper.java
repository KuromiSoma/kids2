package com.kdc.mybatis.mapper.common.entity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kdc.common.entity.db.PlaceDisplayConfigEntity;

/**
 * 場所表示設定テーブル Mapperクラス
 *
 */
@Mapper
public interface PlaceDisplayConfigMapper {

	/**
	 * 全件取得.
	 * 
	 * @return 場所表示設定テーブルクラスリスト
	 */
	public List<PlaceDisplayConfigEntity> selectAll();

	/**
	 * 全件取得.
	 * 
	 * @param delflg
	 * 			削除フラグ
	 * @param sort1
	 * 			ソート判定1
	 * @param sort2
	 * 			ソート判定2	 * 
	 * @return 場所表示設定テーブルクラスリスト
	 */
	public List<PlaceDisplayConfigEntity> selectAll(@Param("delflg") Integer delflg, @Param("sort1") boolean sort1,
			@Param("sort2") boolean sort2);

	/**
	 * プライマリキーを指定して取得.
	 * 
	 * @param authlevel
	 * 			管理レベル
	 * @param placeid
	 * 			場所ID
	 * @param delflg
	 * 			削除フラグ
	 * @return 場所表示設定テーブルクラス
	 */
	public PlaceDisplayConfigEntity selectByPk(@Param("authlevel") String authlevel, @Param("placeid") String placeid,
			@Param("delflg") Integer delflg);

	/**
	 * 追加.
	 * 
	 * @param rec
	 * 			場所表示設定テーブルクラス
	 * @return 追加件数 
	 */
	public int insert(@Param("rec") PlaceDisplayConfigEntity rec);

	/**
	 * 追加/更新.
	 * 
	 * @param rec
	 * 			場所表示設定テーブルクラス
	 * @return 追加/更新件数 
	 */
	public int upsert(@Param("rec") PlaceDisplayConfigEntity rec);

	/**
	 * 論理削除.
	 * 
	 * @param rec
	 * 			場所表示設定テーブルクラス
	 * @return 更新件数 
	 */
	public int delete(@Param("rec") PlaceDisplayConfigEntity rec);

}
