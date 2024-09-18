package com.kdc.mybatis.mapper.api;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kdc.common.entity.db.PlaceAlertRecordEntity;
import com.kdc.common.entity.db.UserLocationRecordEntity;
import com.kdc.common.entity.db.UserLocationRecordEntityWrapper;
import com.kdc.mybatis.domain.api.getlocations.UserInfoDomain;

/**
 * ユーザ位置情報取得API Mapperクラス
 *
 */
@Mapper
public interface GetLocationsMapper {

	/**
	 * 位置情報取得
	 * @param ユーザID
	 * @return ユーザ位置情報レスポンスクラスリスト
	 */
	public List<UserInfoDomain> getLocationList(@Param("userid") String userid,@Param("groupid") String groupid);

	/**
	 * ユーザ履歴取得
	 * @param ユーザ位置履歴テーブルクラス
	 * @param ソートするか
	 * @return ユーザ位置履歴クラスリスト
	 */
	public List<UserLocationRecordEntityWrapper> getLocationRecordList(@Param("rec") UserLocationRecordEntity rec,
			@Param("sort") boolean sort);

	/**
	 * 場所通知記録
	 * @param 場所履歴テーブルクラス
	 * @return 場所履歴クラスリスト
	 */
	public List<PlaceAlertRecordEntity> getPlaceAlertRecordList(@Param("rec") PlaceAlertRecordEntity rec);

}
