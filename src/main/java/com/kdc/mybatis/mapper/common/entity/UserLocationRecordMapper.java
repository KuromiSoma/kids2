package com.kdc.mybatis.mapper.common.entity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kdc.common.entity.db.UserLocationRecordEntityWrapper;

/**
 * ユーザ位置履歴テーブル Mapperクラス
 *
 */
@Mapper
public interface UserLocationRecordMapper {

	/**
	 * 全件取得.
	 * 
	 * @return ユーザ位置履歴テーブルクラスリスト
	 */
	public List<UserLocationRecordEntityWrapper> selectAll();

	/**
	 * 全件取得.
	 * 
	 * @param delflg
	 * 			削除フラグ
	 * @param sort1
	 * 			ソート判定1
	 * @param sort2
	 * 			ソート判定2
	 * @return ユーザ位置履歴テーブルクラスリスト
	 */
	public List<UserLocationRecordEntityWrapper> selectAll(@Param("delflg") Integer delflg,
			@Param("sort1") boolean sort1, @Param("sort2") boolean sort2);

	/**
	 * プライマリキーを指定して取得.
	 * 
	 * @param userid
	 * 			ユーザID
	 * @param receivedate
	 * 			取得日時
	 * @param delflg
	 * 			削除フラグ
	 * @return ユーザ位置履歴テーブルクラス
	 */
	public UserLocationRecordEntityWrapper selectByPk(@Param("userid") String userid,
			@Param("receivedate") String receivedate, @Param("delflg") Integer delflg);

	/**
	 * 日付を指定して取得.
	 * 
	 * @param receivedate
	 * 			取得日時
	 * @return ユーザ位置履歴テーブルクラスリスト
	 */
	public List<UserLocationRecordEntityWrapper> selectByDate(@Param("receivedate") String receivedate);

	/**
	 * userIdと日付を指定して取得.
	 * 
	 * @param userid
	 * 			ユーザID
	 * @param receivedate
	 * 			取得日時
	 * @return ユーザ位置履歴テーブルクラスリスト
	 */
	public List<UserLocationRecordEntityWrapper> selectByUserIdDate(@Param("userid") String userid,
			@Param("receivedate") String receivedate);

	/**
	 * 追加.
	 * 
	 * @param rec
	 * 			ユーザ位置履歴テーブルクラス
	 * @return 追加件数 
	 */
	public int insert(@Param("rec") UserLocationRecordEntityWrapper rec);

}