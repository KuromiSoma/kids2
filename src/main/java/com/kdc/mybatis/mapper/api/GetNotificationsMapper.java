package com.kdc.mybatis.mapper.api;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kdc.common.entity.db.NotificationInfoEntity;

/**
 * 通知情報取得API　Mapperクラス
 * @author kerimu
 *
 */
@Mapper
public interface GetNotificationsMapper {

	/**
	 * 通知情報取得
	 * @return 通知情報レスポンスクラスリスト
	 */
	public List<NotificationInfoEntity> getNotificationList(@Param("userId") String userId, @Param("groupId") String groupId, @Param("notificationDate") String notificationDate);
		
}
