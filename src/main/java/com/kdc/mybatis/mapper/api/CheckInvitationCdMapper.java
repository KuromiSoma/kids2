package com.kdc.mybatis.mapper.api;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kdc.common.entity.db.InvitationManagementEntity;

/**
 * 招待コード認証API Mapperクラス
 *
 */
@Mapper
public interface CheckInvitationCdMapper {

	/**
	 * 招待管理テーブル更新
	 * @param 招待管理テーブルクラス
	 * @return 更新件数
	 */
	public int updateInvitationManagement(@Param("rec") InvitationManagementEntity rec);

}
