package com.kdc.api.getlocations;

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
import com.kdc.api.getlocations.GetLocationsRequestEntity;
import com.kdc.api.getlocations.GetLocationsResponseEntity;
import com.kdc.common.entity.api.UserLocationInfoEntity;
import com.kdc.common.enums.ApiIdEnum;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.KdcCommonUtils;

/**
 * ユーザ位置情報取得 API Controller クラス
 *
 */
@RestController
@Transactional(rollbackFor = Exception.class)
public class GetLocationsController {

	private static final Logger logger = LoggerFactory.getLogger(GetLocationsController.class);

	@Autowired
	private GetLocationsService getLocationsService;

	// ユーザ位置情報取得
	@RequestMapping(path = CommonConst.API_BASE_URL
			+ "/locations", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GetLocationsResponseEntity getLocations(@RequestBody GetLocationsRequestEntity bean,
			HttpServletResponse response) {

		logger.info(KdcCommonUtils.getApiStartLog(ApiIdEnum.GET_LOCATIONS, bean.getUserInfo(), bean.getDeviceInfo()));

		// レスポンス作成
		GetLocationsResponseEntity resEntity = new GetLocationsResponseEntity();
		resEntity.setApiId(CommonConst.API_ID_GET_LOCATIONS);

		// チェック関連
		if (!this.getLocationsService.parameterCheck(bean, resEntity)) {
			// HTTPステータスコードを"Bad Request(400)"に設定する。
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.GET_LOCATIONS, bean.getUserInfo(), bean.getDeviceInfo(),
					"parameterCheck"));
			return resEntity;
		}

		// DBから取得
		List<UserLocationInfoEntity> locationlist = this.getLocationsService.getLocations(bean);

		resEntity.setResultCd(CommonConst.RESULT_CD_SUCCESS);
		resEntity.setUserLocationInfo(locationlist);

		logger.info(KdcCommonUtils.getApiEndLog(ApiIdEnum.GET_LOCATIONS, bean.getUserInfo(), bean.getDeviceInfo(),
				response.getStatus(), resEntity.getResultCd()));
		return resEntity;
	}
}
