package com.kdc.mybatis.mapper.common.entity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kdc.common.entity.db.UserDeviceEntity;

/**
 * ユーザ端末情報テーブル Mapperクラス
 *
 */
@Mapper
public interface UserDeviceMapper {

	/**
	 * 全件取得.
	 * 
	 * @return ユーザ端末情報テーブルクラスリスト
	 */
	public List<UserDeviceEntity> selectAll();

	/**
	 * 全件取得.
	 * 
	 * @param delflg
	 * 			削除フラグ
	 * @param sort
	 * 			ソート判定
	 * @return  ユーザ端末情報テーブルクラスリスト
	 */
	public List<UserDeviceEntity> selectAll(@Param("delflg") Integer delflg, @Param("sort") boolean sort);

	/**
	 * プライマリキーを指定して取得.
	 * 
	 * @param userid
	 * 			ユーザID
	 * @param delflg
	 * 			削除フラグ
	 * @return　ユーザ端末情報テーブルクラス
	 */
	public UserDeviceEntity selectByPk(@Param("userid") String userid, @Param("delflg") Integer delflg);

	/**
	 * 端末を取得.
	 * 
	 * @param telephonenumber
 * 				電話番号
	 * @param delflg
	 * 			削除フラグ
	 * @return ユーザ端末情報テーブルクラス
	 */
	public UserDeviceEntity selectDevice(@Param("telephonenumber") String telephonenumber,
			@Param("delflg") Integer delflg);

	/**
	 * 端末を取得.
	 * 
	 * @param telephonenumber
 * 				電話番号
	 * @param delflg
	 * 			削除フラグ
	 * @return ユーザ端末情報テーブルクラス
	 */
	public UserDeviceEntity selectDeviceExist(@Param("telephonenumber") String telephonenumber,@Param("userid") String userid,
			@Param("delflg") Integer delflg);

	/**
	 * プライマリキーを指定して取得.
	 * 
	 * @param userid
	 * 			ユーザID
	 * @param delflg
	 * 			削除フラグ
	 * @return ユーザマスタクラスリスト
	 */
	public UserDeviceEntity selectUserExist(@Param("userid") String userid,@Param("deviceid") String deviceid, @Param("delflg") Integer delflg);

	/**
	 * 追加.
	 * 
	 * @param rec
	 * 			ユーザ端末情報テーブルクラス
	 * @return 追加件数 
	 */
	public int insert(@Param("rec") UserDeviceEntity rec);

	/**
	 * 更新.
	 * 
	 * @param rec
	 * 			ユーザ端末情報テーブルクラス
	 * @return 更新件数 
	 */
	public int update(@Param("rec") UserDeviceEntity rec);

	/**
	 * 指定端末論理削除.
	 * 
	 * @param rec
	 * 			ユーザ端末情報テーブルクラス
	 * @return 更新件数 
	 */
	public int deleteDevice(@Param("rec") UserDeviceEntity rec);

}
