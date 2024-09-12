package com.kdc.mybatis.mapper.common.entity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kdc.common.entity.db.GroupInfoEntity;

@Mapper
public interface GroupMasterMapper {

	/**
	 * 全件取得
	 * @Param delflg 削除フラグ
	 * @return グループマスタクラスリスト
	 */
	public List<GroupInfoEntity> selectAll(@Param("delflg") Integer delflg );
	
	/**
	 * プライマリーキーを指定して取得
	 * @param groupid
	 * @return
	 */
	public GroupInfoEntity selectPk(@Param("groupid") String groupid ,@Param("delflg") Integer delflg );

	/**
	 * グループマスタ登録
	 * @param rec グループマスタクラス
	 * @return
	 */
	public int insert(@Param("rec") GroupInfoEntity rec);

	/**
	 * グループマスタ更新
	 * @param rec
	 * @return
	 */
	public int update(@Param("rec") GroupInfoEntity rec);
	
	/**
	 * 送信間隔新規グループ登録
	 * @param rec
	 * @return
	 */
	public int insertSendIntervalConfig(@Param("rec") GroupInfoEntity rec);
	
	/**
	 * ユーザー通知新規グループ登録
	 * @param rec
	 * @return
	 */
	public int insertUserAlertConfig(@Param("rec") GroupInfoEntity rec);
	
	/**
	 * 汎用マスタ新規グループ登録
	 * @param rec
	 * @return
	 */
	public int insertConfigMasterg(@Param("rec") GroupInfoEntity rec);
	
	
}