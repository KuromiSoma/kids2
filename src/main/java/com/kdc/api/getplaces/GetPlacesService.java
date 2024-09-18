package com.kdc.api.getplaces;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kdc.common.entity.api.PlaceInfoEntity;
import com.kdc.common.entity.api.base.ResponseBaseEntity;
import com.kdc.common.util.CommonService;
import com.kdc.mybatis.domain.api.getplaces.PlaceInfoDomain;
import com.kdc.mybatis.mapper.api.GetPlacesMapper;

/**
 * 場所取得 API Service クラス
 *
 */
@Service
public class GetPlacesService {

	@Autowired
	private GetPlacesMapper getPlacesMapper;

	@Autowired
	private CommonService commonService;

	/**
	 * パラメータチェック.
	 * @param reqEntity
	 * @param resEntity
	 * @return Boolean
	 */
	public Boolean parameterCheck(GetPlacesRequestEntity reqEntity, ResponseBaseEntity resEntity) {

		if (!this.commonService.checkBaseParameter(reqEntity, resEntity)) {
			return false;
		}

		return true;
	}

	/**
	 * 場所情報取得.
	 * @param reqEntity
	 * @return List
	 */
	public List<PlaceInfoEntity> getPlace(GetPlacesRequestEntity reqEntity) {

		List<PlaceInfoEntity> result = new ArrayList<>();

		List<PlaceInfoDomain> placeList = this.getPlacesMapper.getPlaceList(reqEntity.getUserInfo().getUserId());

		if (placeList != null) {
			for (PlaceInfoDomain placeEntity : placeList) {
				PlaceInfoEntity place = new PlaceInfoEntity();
				place.setPlaceId(placeEntity.getPlaceid());
				place.setPlaceName(placeEntity.getPlacename());
				place.setPlaceTypeCd(String.valueOf(placeEntity.getPlacetypeflg()));
				place.setLatitude(placeEntity.getLatitude().toString());
				place.setLongitude(placeEntity.getLongitude().toString());
				place.setRadius(String.valueOf(placeEntity.getRadius()));
				place.setIconId(placeEntity.getIconid());
				place.setDispFlg(placeEntity.getPlacedispflg());

				result.add(place);
			}
		}

		return result;
	}

}
