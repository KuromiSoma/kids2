package com.kdc.mybatis.mapper.common.entity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kdc.common.entity.db.PlaceAlertConfigEntity;

/**
 * 場所通知設定テーブル Mapperクラス
 *
 */
@Mapper
public interface PlaceAlertConfigMapper {

	/**
	 * 全件取得.
	 * 
	 * @return 場所通知設定テーブルクラスリスト
	 */
	public List<PlaceAlertConfigEntity> selectAll();

	/**
	 * 全件取得.
	 * 
	 * @return
	 */
	public List<PlaceAlertConfigEntity> selectAll(@Param("delflg") Integer delflg, @Param("sort1") boolean sort1,
			@Param("sort2") boolean sort2, @Param("sort3") boolean sort3);

	/**
	 * プライマリキーを指定して取得.
	 * 
	 * @return
	 */
	public PlaceAlertConfigEntity selectByPk(@Param("authlevel") String authlevel,
			@Param("notificationauthlevel") String notificationauthlevel, @Param("placeid") String placeid,
			@Param("delflg") Integer delflg);

	/**
	 * 追加.
	 */
	public int insert(@Param("rec") PlaceAlertConfigEntity rec);

	/**
	 * 追加・更新.
	 */
	public int upsert(@Param("rec") PlaceAlertConfigEntity rec);

	/**
	 * 論理削除.
	 */
	public int delete(@Param("rec") PlaceAlertConfigEntity rec);

}
