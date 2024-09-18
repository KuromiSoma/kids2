package com.kdc.mybatis.mapper.common.entity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kdc.common.entity.db.NotificationInfoEntity;

@Mapper
public interface NotificationInfoMapper {
	
	/**
	 * 全件取得
	 * @Param delflg 削除フラグ
	 * @return 通知情報クラスリスト
	 */
	public List<NotificationInfoEntity> selectAll(@Param("delflg") Integer delflg );
	
	/**
	 * プライマリーキーを指定して取得
	 * @param userid
	 * @return
	 */
	public NotificationInfoEntity selectPk(@Param("userId") String userId ,@Param("delflg") Integer delflg );

	/**
	 * 通知情報登録
	 * @param rec 通知情報クラス
	 * @return
	 */
	public int insert(@Param("rec") NotificationInfoEntity rec);

	/**
	 * 通知情報更新
	 * @param rec
	 * @return
	 */
	public int update(@Param("rec") NotificationInfoEntity rec);
	
	/**
	 * 論理削除.
	 * 
	 * @param rec
	 * 			場所マスタクラス
	 * @return 更新件数 
	 */
	public int delete(@Param("rec") NotificationInfoEntity rec);
	/**
	 * 送信間隔新規通知情報登録
	 * @param rec
	 * @return
	 */
	public int insertSendIntervalConfig(@Param("rec") NotificationInfoEntity rec);
	
	/**
	 * ユーザー通知新規通知情報登録
	 * @param rec
	 * @return
	 */
	public int insertUserAlertConfig(@Param("rec") NotificationInfoEntity rec);
	
	/**
	 * 汎用通知情報登録
	 * @param rec
	 * @return
	 */
	public int insertConfigMasterg(@Param("rec") NotificationInfoEntity rec);
	
	

}
