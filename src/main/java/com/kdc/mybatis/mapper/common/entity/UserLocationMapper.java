package com.kdc.mybatis.mapper.common.entity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kdc.common.entity.db.UserLocationEntityWrapper;

/**
 * ユーザ位置情報テーブル Mapperクラス
 *
 */
@Mapper
public interface UserLocationMapper {

	/**
	 * 全件取得.
	 * 
	 * @return ユーザ位置テーブルクラスリスト
	 */
	public List<UserLocationEntityWrapper> selectAll();

	/**
	 * 全件取得.
	 * 
	 * @param delflg
	 * 			削除フラグ
	 * @param sort
	 * 			ソート判定
	 * @return ユーザ位置テーブルクラスリスト
	 */
	public List<UserLocationEntityWrapper> selectAll(@Param("delflg") Integer delflg,@Param("groupid") String groupid, @Param("sort") boolean sort);

	/**
	 * プライマリキーを指定して取得.
	 * 
	 * @param userid
	 * 			ユーザID
	 * @param delflg
	 * 			削除フラグ
	 * @return ユーザ位置テーブルクラス
	 */
	public UserLocationEntityWrapper selectByPk(@Param("userid") String userid, @Param("delflg") Integer delflg);

	/**
	 * 日付を指定して取得.
	 * 
	 * @param lastLocationDate
	 * 			最新位置情報取得日時
	 * @return ユーザ位置テーブルクラスリスト
	 */
	public List<UserLocationEntityWrapper> selectByDate(@Param("lastLocationDate") String lastLocationDate);

	/**
	 * 追加.
	 * 
	 * @param rec
	 * 			ユーザ位置テーブルクラス
	 * @return 追加件数 
	 */
	public int insert(@Param("rec") UserLocationEntityWrapper rec);

	/**
	 * 更新.
	 * 
	 * @param rec
	 * 			ユーザ位置テーブルクラス
	 * @return 更新件数 
	 */
	public int update(@Param("rec") UserLocationEntityWrapper rec);

}