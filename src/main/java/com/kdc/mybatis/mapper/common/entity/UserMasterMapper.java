package com.kdc.mybatis.mapper.common.entity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kdc.common.entity.db.UserMasterEntity;

/**
 * ユーザマスタ Mapperクラス
 *
 */
@Mapper
public interface UserMasterMapper {

	/**
	 * 全件取得.
	 * 
	 * @return ユーザマスタクラスリスト
	 */
	public List<UserMasterEntity> selectAll();

	/**
	 * 全件取得.
	 * 
	 * @param delflg
	 * 			削除フラグ
	 * @return ユーザマスタクラスリスト
	 */
	public List<UserMasterEntity> selectAll(@Param("delflg") Integer delflg);

	/**
	 * プライマリキーを指定して取得.
	 * 
	 * @param userid
	 * 			ユーザID
	 * @param delflg
	 * 			削除フラグ
	 * @return ユーザマスタクラスリスト
	 */
	public UserMasterEntity selectByPk(@Param("userid") String userid, @Param("delflg") Integer delflg);

	/**
	 * ユーザを取得.
	 * 
	 * @param userid
	 * 			ユーザID
	 * @param password
	 * 			パスワード
	 * @param delflg
	 * 			削除フラグ
	 * @return ユーザマスタクラスリスト
	 */
	public UserMasterEntity selectUser(@Param("userid") String userid, @Param("password") String password,
			@Param("delflg") Integer delflg);

	/**
	 * ユーザグループID（MIN)を取得.
	 * 
	 * @param delflg
	 * 			削除フラグ
	 * @return ユーザマスタクラス
	 */
	public UserMasterEntity selectUserGroupId(@Param("delflg") Integer delflg);

	/**
	 * 追加.
	 * 
	 * @param rec
	 * 			ユーザマスタクラス
	 * @return 追加件数 
	 */
	public int insert(@Param("rec") UserMasterEntity rec);

	/**
	 * 更新.
	 * @param rec
	 * 			ユーザマスタクラス
	 * @return 更新件数 
	 */
	public int update(@Param("rec") UserMasterEntity rec);

	/**
	 * 論理削除.
	 * 
	 * @param rec
	 * 			ユーザマスタクラス
	 * @return 更新件数 
	 */
	public int delete(@Param("rec") UserMasterEntity rec);
}