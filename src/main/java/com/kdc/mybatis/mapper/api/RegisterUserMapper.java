package com.kdc.mybatis.mapper.api;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kdc.common.entity.db.InvitationManagementEntity;

/**
 * ユーザ登録API Mapperクラス
 *
 */
@Mapper
public interface RegisterUserMapper {

	/**
	 * ユーザ名重複チェック
	 * 
	 * @param ユーザ名
	 * @return 取得件数
	 */
	public Integer selectUserInfo(@Param("username") String username);

	/**
	 * 招待コード使用状態更新
	 * 
	 * @param 招待管理テーブルクラス
	 * @return 更新件数
	 */
	public int updateInvitationCodeUsed(@Param("rec") InvitationManagementEntity rec);

	/**
	 * 新規グループID取得
	 * 
	 * @return 新規グループID
	 */
	public String getNewGroupId();
}
