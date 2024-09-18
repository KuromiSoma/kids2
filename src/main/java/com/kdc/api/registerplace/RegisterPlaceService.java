package com.kdc.api.registerplace;

import java.sql.Timestamp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kdc.common.entity.api.base.ResponseBaseEntity;
import com.kdc.common.entity.db.PlaceMasterEntityWrapper;
import com.kdc.common.enums.AuthLevelEnum;
import com.kdc.common.enums.RegisterPlaceModeEnum;
import com.kdc.common.entity.db.PlaceAlertConfigEntity;
import com.kdc.common.entity.db.PlaceDisplayConfigEntity;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.CommonService;
import com.kdc.common.util.KdcCommonUtils;
import com.kdc.mybatis.mapper.common.entity.PlaceAlertConfigMapper;
import com.kdc.mybatis.mapper.common.entity.PlaceDisplayConfigMapper;
import com.kdc.mybatis.mapper.common.entity.PlaceMasterMapper;

/**
 * 場所登録 API Service クラス
 *
 */
@Service
public class RegisterPlaceService {

	@Autowired
	private PlaceMasterMapper placeMasterMapper;

	@Autowired
	private PlaceDisplayConfigMapper placeDisplayConfigMapper;

	@Autowired
	private PlaceAlertConfigMapper placeAlertConfigMapper;

	@Autowired
	private CommonService commonService;

	/**
	 * パラメータチェック.
	 * @param reqEntity
	 * @param resEntity
	 * @return Boolean
	 */
	public Boolean parameterCheck(RegisterPlaceRequestEntity reqEntity, ResponseBaseEntity resEntity) {

		// 端末情報
		if (!this.commonService.checkBaseParameter(reqEntity, resEntity)) {
			return false;
		}

		// モード
		if (reqEntity.getMode() == null) {
			resEntity.setMessage("mode is Empty");
			return false;
		}

		// （場所情報）
		// 場所コード
		if (StringUtils.isEmpty(reqEntity.getPlaceInfo().getPlaceId())) {
			resEntity.setMessage("placeInfo is Empty");
			return false;
		}
		// 場所名
		if (StringUtils.isEmpty(reqEntity.getPlaceInfo().getPlaceName())) {
			resEntity.setMessage("placeInfo.placeName is Empty");
			return false;
		}
		// 場所区分
		if (StringUtils.isEmpty(reqEntity.getPlaceInfo().getPlaceTypeCd())) {
			resEntity.setMessage("placeInfo.placeTypeCd is Empty");
			return false;
		}
		// 緯度
		if (StringUtils.isEmpty(reqEntity.getPlaceInfo().getLatitude())) {
			resEntity.setMessage("placeInfo.latitude is Empty");
			return false;
		}
		// 経度
		if (StringUtils.isEmpty(reqEntity.getPlaceInfo().getLongitude())) {
			resEntity.setMessage("placeInfo.longitude is Empty");
			return false;
		}
		// 半径
		if (StringUtils.isEmpty(reqEntity.getPlaceInfo().getRadius())) {
			resEntity.setMessage("placeInfo.radius is Empty");
			return false;
		}
		// アイコン画像ID
		if (StringUtils.isEmpty(reqEntity.getPlaceInfo().getIconId())) {
			resEntity.setMessage("placeInfo.iconId is Empty");
			return false;
		}

		// モード
		try {
			RegisterPlaceModeEnum.valueOf(reqEntity.getMode());
		} catch (IllegalArgumentException e) {
			resEntity.setMessage("mode is Wrong");
			return false;
		}

		// 場所名
		if (StringUtils.length(reqEntity.getPlaceInfo().getPlaceName()) > CommonConst.PLACE_NAME_MAX_LENGTH) {
			resEntity.setMessage("placeInfo.placeName is too Long");
			return false;
		}

		return true;
	}

	/**
	 * 場所情報登録.
	 * @param reqEntity
	 * @return Boolean
	 */
	public Boolean register(RegisterPlaceRequestEntity reqEntity) {

		PlaceMasterEntityWrapper rec = new PlaceMasterEntityWrapper();
		PlaceDisplayConfigEntity dispRec = new PlaceDisplayConfigEntity();
		PlaceAlertConfigEntity alertRec = new PlaceAlertConfigEntity();

		Timestamp nowTimestamp = KdcCommonUtils.getNowTimestamp();

		switch (RegisterPlaceModeEnum.valueOf(reqEntity.getMode())) {
		case INSERT:
		case UPDATE:
			// place_master共通
			rec.setPlacename(reqEntity.getPlaceInfo().getPlaceName());
			rec.setPlacetypeflg(Integer.parseInt(reqEntity.getPlaceInfo().getPlaceTypeCd()));
			rec.setLatitude(reqEntity.getPlaceInfo().getLatitude());
			rec.setLongitude(reqEntity.getPlaceInfo().getLongitude());
			rec.setRadius(Integer.parseInt(reqEntity.getPlaceInfo().getRadius()));
			rec.setIconid(reqEntity.getPlaceInfo().getIconId());
			rec.setGroupid(reqEntity.getUserInfo().getGroupId());
			break;
		case DELETE:
			break;
		}

		try {
			switch (RegisterPlaceModeEnum.valueOf(reqEntity.getMode())) {
			case INSERT: // 新規

				// 場所情報登録
				// place_id_seq
				String placeridseq = this.commonService.createPlaceId();

				// place_master
				rec.setPlaceid(placeridseq);
				rec.setRegisteruserid(reqEntity.getUserInfo().getUserId());
				rec.setRegisterdate(nowTimestamp);
				this.placeMasterMapper.insert(rec);

				// place_display_config
				dispRec.setPlaceid(placeridseq);
				dispRec.setPlacedispflg(CommonConst.FLG_ON);
				dispRec.setRegisteruserid(reqEntity.getUserInfo().getUserId());
				dispRec.setRegisterdate(nowTimestamp);
				for (AuthLevelEnum item : AuthLevelEnum.values()) {
					dispRec.setAuthlevel(item.getCode());
					this.placeDisplayConfigMapper.insert(dispRec);
				}

				break;

			case UPDATE: // 更新

				// place_master
				rec.setPlaceid(reqEntity.getPlaceInfo().getPlaceId());
				rec.setUpdateuserid(reqEntity.getUserInfo().getUserId());
				rec.setUpdatedate(nowTimestamp);

				this.placeMasterMapper.update(rec);
				break;

			case DELETE: // 削除

				// place_master(論理削除)
				rec.setPlaceid(reqEntity.getPlaceInfo().getPlaceId());
				rec.setUpdateuserid(reqEntity.getUserInfo().getUserId());
				rec.setUpdatedate(nowTimestamp);
				this.placeMasterMapper.delete(rec);

				// place_display_config
				dispRec.setPlaceid(reqEntity.getPlaceInfo().getPlaceId());
				dispRec.setUpdateuserid(reqEntity.getUserInfo().getUserId());
				dispRec.setUpdatedate(nowTimestamp);
				this.placeDisplayConfigMapper.delete(dispRec);

				// place_alert_config
				alertRec.setPlaceid(reqEntity.getPlaceInfo().getPlaceId());
				alertRec.setUpdateuserid(reqEntity.getUserInfo().getUserId());
				alertRec.setUpdatedate(nowTimestamp);
				this.placeAlertConfigMapper.delete(alertRec);

				break;

			default:

				return false;

			}

			return true;

		} catch (Exception e) {
			return false;
		}
	}

}
