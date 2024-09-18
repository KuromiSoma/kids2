package com.kdc.mybatis.mapper.common.entity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kdc.common.entity.db.UserAlertRecordEntity;

/**
 * ユーザ通知履歴テーブル Mapperクラス
 *
 */
@Mapper
public interface UserAlertRecordMapper {

	/**
	 * 全件取得.
	 * 
	 * @return ユーザ通知履歴テーブルクラスリスト
	 */
	public List<UserAlertRecordEntity> selectAll();

	/**
	 * 全件取得.
	 * 
	 * @param delflg
	 * 			削除フラグ
	 * @param sort
	 * 			ソート判定
	 * @return ユーザ通知履歴テーブルクラスリスト
	 */
	public List<UserAlertRecordEntity> selectAll(@Param("delflg") Integer delflg, @Param("sort") boolean sort);

	/**
	 * プライマリキーを指定して取得.
	 * 
	 * @param userid
	 * 			ユーザID
	 * @param delflg
	 * 			削除フラグ
	 * @return ユーザ通知履歴テーブルクラス
	 */
	public UserAlertRecordEntity selectByPk(@Param("userid") String userid, @Param("delflg") Integer delflg);

	/**
	 * 追加/更新.
	 * 
	 * @param rec
	 * 			ユーザ通知履歴テーブルクラス
	 * @return 追加/更新件数 
	 */
	public int upsert(@Param("rec") UserAlertRecordEntity rec);

}
