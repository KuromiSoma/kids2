package com.kdc.api.getrecords;

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
import com.kdc.api.getrecords.GetRecordsRequestEntity;
import com.kdc.api.getrecords.GetRecordsResponseEntity;
import com.kdc.common.entity.api.LocationRecordInfoEntity;
import com.kdc.common.enums.ApiIdEnum;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.KdcCommonUtils;

/**
 * ユーザ履歴情報取得 API Controller クラス
 *
 */
@RestController
@Transactional(rollbackFor = Exception.class)
public class GetRecordsController {

	private static final Logger logger = LoggerFactory.getLogger(GetRecordsController.class);

	@Autowired
	private GetRecordsService getRecordsService;

	// ユーザ履歴情報取得
	@RequestMapping(path = CommonConst.API_BASE_URL
			+ "/records", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GetRecordsResponseEntity getRecords(@RequestBody GetRecordsRequestEntity bean,
			HttpServletResponse response) {

		logger.info(KdcCommonUtils.getApiStartLog(ApiIdEnum.GET_RECORDS, bean.getUserInfo(), bean.getDeviceInfo()));

		// レスポンス作成
		GetRecordsResponseEntity resEntity = new GetRecordsResponseEntity();
		resEntity.setApiId(CommonConst.API_ID_GET_RECORDS);

		// チェック関連
		if (!this.getRecordsService.parameterCheck(bean, resEntity)) {
			// HTTPステータスコードを"Bad Request(400)"に設定する。
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.GET_RECORDS, bean.getUserInfo(), bean.getDeviceInfo(),
					"parameterCheck"));
			return resEntity;
		}

		// DBから取得
		List<LocationRecordInfoEntity> recordlist = this.getRecordsService.getRecord(bean);

		resEntity.setResultCd(CommonConst.RESULT_CD_SUCCESS);
		resEntity.setUserRecordInfo(recordlist);

		logger.info(KdcCommonUtils.getApiEndLog(ApiIdEnum.GET_RECORDS, bean.getUserInfo(), bean.getDeviceInfo(),
				response.getStatus(), resEntity.getResultCd()));
		return resEntity;
	}

}
