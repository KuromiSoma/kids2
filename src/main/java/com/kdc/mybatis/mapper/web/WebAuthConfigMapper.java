package com.kdc.mybatis.mapper.web;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.kdc.common.entity.db.PlaceAlertConfigEntity;
import com.kdc.common.entity.db.SendIntervalConfigEntity;
import com.kdc.common.entity.db.UserAlertConfigEntity;
import com.kdc.mybatis.domain.web.webauthconfig.UserInfoDomain;
import com.kdc.web.webauthconfig.PlaceAlertConfigSub;
import com.kdc.web.webauthconfig.PlaceDispConfig;

/**
 * 管理設定画面Mapperクラス
 *
 */
@Mapper
public interface WebAuthConfigMapper {

	/**
	 * ユーザ通知設定取得.
	 * 
	 * @return
	 */
	public List<UserAlertConfigEntity> selectUserAlertConfig(@Param("notificationauthlevel") int notificationauthlevel, @Param("groupid") String groupid);

	/**
	 * 場所表示設定取得.
	 * 
	 * @return
	 */
	public List<PlaceDispConfig> selectPlaceDispConfig(@Param("authlevel") int authlevel, @Param("groupid") String groupid);

	/**
	 * 場所通知設定取得.
	 * 
	 * @return
	 */
	public List<PlaceAlertConfigSub> selectPlaceAlertConfig(@Param("authlevel") int authlevel,@Param("notificationauthlevel") int notificationauthlevel, @Param("groupid") String groupid);

	/**
	 * 送信設定取得.
	 * 
	 * @return
	 */
	public List<SendIntervalConfigEntity> selectTransmissionConfig(@Param("authlevel") int authlevel,@Param("groupid") String groupid);

	/**
	 * ユーザ取得.
	 * 
	 * @return
	 */
	public List<UserInfoDomain> selectAllUser(@Param("groupid") String groupid);

	/**
	 * ユーザ通知（表示項目）追加/更新.
	 */
	public int upsertUserDisp(@Param("rec") UserAlertConfigEntity rec);

	/**
	 * ユーザ通知（通知項目）追加/更新.
	 */
	public int upsertUserAlert(@Param("rec") UserAlertConfigEntity rec);

	/**
	 * 場所通知追加/更新.
	 */
	public int upsertPlaceAlert(@Param("rec") PlaceAlertConfigEntity rec);

}
