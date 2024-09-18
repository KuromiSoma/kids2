package com.kdc.mybatis.mapper.api;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kdc.mybatis.domain.api.getplaces.PlaceInfoDomain;

/**
 * 場所取得API Mapperクラス
 *
 */
@Mapper
public interface GetPlacesMapper {

	/**
	 * 場所取得
	 * @param ユーザID
	 * @return 場所情報レスポンスクラスリスト
	 */
	public List<PlaceInfoDomain> getPlaceList(@Param("userid") String userid);

}
