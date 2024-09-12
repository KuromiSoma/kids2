package com.kdc.mybatis.mapper.web;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.kdc.common.entity.db.UserMasterEntity;

/**
 * メイン画面Mapperクラス
 *
 */
@Mapper
public interface WebMainMapper {

	/**
	 * 有効ユーザを取得
	 * @return
	 */
	List<UserMasterEntity> getValidUser();

}