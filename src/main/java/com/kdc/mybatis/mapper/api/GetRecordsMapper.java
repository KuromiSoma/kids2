package com.kdc.mybatis.mapper.api;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kdc.common.entity.db.UserLocationRecordEntityWrapper;

/**
 * ユーザ履歴情報取得API Mapperクラス
 *
 */
@Mapper
public interface GetRecordsMapper {

	/**
	 * 履歴情報取得
	 * @param ユーザ位置履歴情報クラス
	 * @param ユーザID
	 * @return ユーザ位置履歴情報クラスリスト
	 */
	public List<UserLocationRecordEntityWrapper> getRecordList(@Param("rec") UserLocationRecordEntityWrapper rec,
			@Param("userid") String userid);

}
