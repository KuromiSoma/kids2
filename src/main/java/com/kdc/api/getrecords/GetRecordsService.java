package com.kdc.api.getrecords;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kdc.common.entity.api.LocationRecordInfoEntity;
import com.kdc.common.entity.api.base.ResponseBaseEntity;
import com.kdc.common.entity.db.UserLocationRecordEntityWrapper;
import com.kdc.common.util.CommonService;
import com.kdc.common.util.KdcCommonUtils;
import com.kdc.mybatis.mapper.api.GetRecordsMapper;

/**
 * ユーザ履歴情報取得 API Service クラス
 *
 */
@Service
public class GetRecordsService {

	@Autowired
	private GetRecordsMapper getRecordsMapper;
	@Autowired
	private CommonService commonService;

	/**
	 * パラメータチェック.
	 * @param reqEntity
	 * @param resEntity
	 * @return Boolean
	 */
	public Boolean parameterCheck(GetRecordsRequestEntity reqEntity, ResponseBaseEntity resEntity) {

		// ユーザ・端末情報
		if (!this.commonService.checkBaseParameter(reqEntity, resEntity)) {
			return false;
		}

		// 対象ユーザID
		if (StringUtils.isEmpty(reqEntity.getTargetUserId())) {
			resEntity.setMessage("targetUserId is Empty");
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
	 * ユーザ履歴情報取得.
	 * @param reqEntity
	 * @return List
	 */
	public List<LocationRecordInfoEntity> getRecord(GetRecordsRequestEntity reqEntity) {

		List<LocationRecordInfoEntity> result = new ArrayList<>();

		UserLocationRecordEntityWrapper rec = new UserLocationRecordEntityWrapper();
		rec.setUserid(reqEntity.getTargetUserId());
		rec.setReceivedate(KdcCommonUtils.dateStringToTimestamp(reqEntity.getTargetDate()));
		List<UserLocationRecordEntityWrapper> recordlist = this.getRecordsMapper.getRecordList(rec,
				reqEntity.getUserInfo().getUserId());

		if (recordlist != null) {
			for (UserLocationRecordEntityWrapper recordEntity : recordlist) {
				LocationRecordInfoEntity record = new LocationRecordInfoEntity();

				record.setRecordDate(KdcCommonUtils.timestampToDateTimeString(recordEntity.getReceivedate()));
				record.setBatteryLevel(String.valueOf(recordEntity.getBatterylevel()));
				record.setReceptionStatus(String.valueOf(recordEntity.getReceptionstatus()));
				record.setLatitude(recordEntity.getLatitude().toString());
				record.setLongitude(recordEntity.getLongitude().toString());

				result.add(record);
			}
		}

		return result;
	}

}
