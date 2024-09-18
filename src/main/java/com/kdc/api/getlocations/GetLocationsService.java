package com.kdc.api.getlocations;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kdc.mybatis.domain.api.getlocations.UserInfoDomain;
import com.kdc.common.entity.api.LocationInfoEntity;
import com.kdc.common.entity.api.LocationRecordInfoEntity;
import com.kdc.common.entity.api.base.UserInfoEntity;
import com.kdc.common.entity.api.UserLocationInfoEntity;
import com.kdc.common.entity.api.base.ResponseBaseEntity;
import com.kdc.common.entity.db.UserLocationRecordEntity;
import com.kdc.common.entity.db.UserLocationRecordEntityWrapper;
import com.kdc.common.util.CommonService;
import com.kdc.common.util.KdcCommonUtils;
import com.kdc.mybatis.mapper.api.GetLocationsMapper;

/**
 * ユーザ位置情報取得 API Service クラス
 *
 */
@Service
public class GetLocationsService {

	@Autowired
	private GetLocationsMapper getLocationsMapper;

	@Autowired
	private CommonService commonService;

	/**
	 * パラメータチェック.
	 * @param reqEntity
	 * @param resEntity
	 * @return Boolean
	 */
	public Boolean parameterCheck(GetLocationsRequestEntity reqEntity, ResponseBaseEntity resEntity) {

		if (!this.commonService.checkBaseParameter(reqEntity, resEntity)) {
			return false;
		}
		// 対象日付
		if (StringUtils.isEmpty(reqEntity.getTargetDate())) {
			resEntity.setMessage("targetDate is Empty");
			return false;
		}
		// 日付不正チェック
		if (!KdcCommonUtils.checkDateString(reqEntity.getTargetDate())) {
			resEntity.setMessage("targetDate is Wrong");
			return false;
		}

		return true;

	}

	/**
	 * ユーザ位置情報取得.
	 * @param reqEntity
	 * @return List
	 */
	public List<UserLocationInfoEntity> getLocations(GetLocationsRequestEntity reqEntity) {

		List<UserLocationInfoEntity> result = new ArrayList<>();

		List<UserInfoDomain> userList = this.getLocationsMapper.getLocationList(reqEntity.getUserInfo().getUserId(),reqEntity.getUserInfo().getGroupId());

		if (userList != null) {
			for (UserInfoDomain userEntity : userList) {
				UserLocationInfoEntity userLocation = new UserLocationInfoEntity();
				UserInfoEntity user = new UserInfoEntity();
				LocationInfoEntity location = new LocationInfoEntity();

				// ユーザ情報userinfo
				user.setUserId(userEntity.getUserid());
				user.setUserName(userEntity.getUsername());
				user.setIconId(userEntity.getIconid());
				user.setAuthLevel(userEntity.getAuthlevel());
				user.setPassword(userEntity.getPassword());
				user.setLineColor(userEntity.getLinecolor());
				user.setMarkerColor(userEntity.getMarkercolor());
				user.setGroupId(userEntity.getGroupid());

				userLocation.setUserInfo(user);

				location.setLatitude(userEntity.getLatitude());
				location.setLongitude(userEntity.getLongitude());

				// 移動距離表示許可
				if (KdcCommonUtils.isFlgOn(userEntity.getMovingdistancedisplayflg())) {
					location.setMovingDistance(userEntity.getMovingdistance());
				}

				// バッテリー残量表示許可
				if (KdcCommonUtils.isFlgOn(userEntity.getBatteryleveldisplayflg())) {
					location.setBatteryLevel(userEntity.getBatterylevel());
				}

				// 電波状況表示許可
				if (KdcCommonUtils.isFlgOn(userEntity.getReceptionstatusdisplayflg())) {
					location.setReceptionStatus(userEntity.getReceptionstatus());
				}

				location.setConnectionStatus(userEntity.getConnectionstatus());
				location.setLastLocationDate(
						KdcCommonUtils.timestampToDateTimeString(userEntity.getLastlocationdate()));
				userLocation.setLocationInfo(location);

				List<LocationRecordInfoEntity> resultLocRecList = new ArrayList<>();
				UserLocationRecordEntity locRec = new UserLocationRecordEntity();
				locRec.setUserid(userEntity.getUserid());
				locRec.setReceivedate(KdcCommonUtils.dateStringToTimestamp((reqEntity.getTargetDate())));
				List<UserLocationRecordEntityWrapper> recordlist = this.getLocationsMapper.getLocationRecordList(locRec,
						false);

				if (recordlist != null) {
					for (UserLocationRecordEntityWrapper recordEntity : recordlist) {

						LocationRecordInfoEntity locationRecord = new LocationRecordInfoEntity();
						locationRecord
								.setRecordDate(KdcCommonUtils.timestampToDateTimeString(recordEntity.getReceivedate()));
						locationRecord.setLatitude(String.valueOf(recordEntity.getLatitude()));
						locationRecord.setLongitude(String.valueOf(recordEntity.getLongitude()));

						// バッテリー残量表示許可
						if (KdcCommonUtils.isFlgOn(userEntity.getBatteryleveldisplayflg())) {
							locationRecord.setBatteryLevel(String.valueOf(recordEntity.getBatterylevel()));
						}

						// 電波状況表示許可
						if (KdcCommonUtils.isFlgOn(userEntity.getReceptionstatusdisplayflg())) {
							locationRecord.setReceptionStatus(String.valueOf(recordEntity.getReceptionstatus()));
						}

						resultLocRecList.add(locationRecord);

					}

					userLocation.setLocationRecordInfo(resultLocRecList);

				}

				result.add(userLocation);
			}
		}

		return result;
	}

}
