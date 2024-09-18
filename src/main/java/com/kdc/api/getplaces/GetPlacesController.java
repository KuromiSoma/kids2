package com.kdc.api.getplaces;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.kdc.api.getplaces.GetPlacesRequestEntity;
import com.kdc.api.getplaces.GetPlacesResponseEntity;
import com.kdc.common.entity.api.PlaceInfoEntity;
import com.kdc.common.enums.ApiIdEnum;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.KdcCommonUtils;

/**
 * 場所取得 API Controller クラス
 *
 */
@RestController
@Transactional(rollbackFor = Exception.class)
public class GetPlacesController {

	private static final Logger logger = LoggerFactory.getLogger(GetPlacesController.class);

	@Autowired
	private GetPlacesService getPlacesService;

	// 場所取得
	@RequestMapping(path = CommonConst.API_BASE_URL
			+ "/places", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GetPlacesResponseEntity getPlaces(@RequestBody GetPlacesRequestEntity bean, HttpServletResponse response) {

		logger.info(KdcCommonUtils.getApiStartLog(ApiIdEnum.GET_PLACES, bean.getUserInfo(), bean.getDeviceInfo()));

		// レスポンス作成
		GetPlacesResponseEntity resEntity = new GetPlacesResponseEntity();
		resEntity.setApiId(CommonConst.API_ID_GET_PLACES);

		// チェック関連
		if (!this.getPlacesService.parameterCheck(bean, resEntity)) {
			// HTTPステータスコードを"Bad Request(400)"に設定する。
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.GET_PLACES, bean.getUserInfo(), bean.getDeviceInfo(),
					"parameterCheck"));
			return resEntity;
		}

		// DBから取得
		List<PlaceInfoEntity> placelist = this.getPlacesService.getPlace(bean);

		resEntity.setResultCd(CommonConst.RESULT_CD_SUCCESS);
		resEntity.setPlaceInfo(placelist);

		logger.info(KdcCommonUtils.getApiEndLog(ApiIdEnum.GET_PLACES, bean.getUserInfo(), bean.getDeviceInfo(),
				response.getStatus(), resEntity.getResultCd()));
		return resEntity;
	}
}
