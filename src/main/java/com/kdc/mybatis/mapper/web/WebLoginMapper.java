package com.kdc.mybatis.mapper.web;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kdc.common.entity.db.AdministratorMasterEntity;

/**
 * ログイン画面Mapperクラス
 *
 */
@Mapper
public interface WebLoginMapper {

	/**
	 * 管理者マスタ情報取得
	 * @param rec
	 * @return
	 */
	AdministratorMasterEntity selectAdmin(@Param("rec") AdministratorMasterEntity rec);

}