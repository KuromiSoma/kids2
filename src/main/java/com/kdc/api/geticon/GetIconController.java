package com.kdc.api.geticon;

import java.io.IOException;
import java.util.Base64;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.kdc.api.geticon.GetIconRequestEntity;
import com.kdc.api.geticon.GetIconResponseEntity;
import com.kdc.common.entity.db.UserMasterEntity;
import com.kdc.common.enums.ApiIdEnum;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.KdcCommonUtils;

/**
 * アイコン画像ダウンロード API Controller クラス
 *
 */
@RestController
public class GetIconController {

	private static final Logger logger = LoggerFactory.getLogger(GetIconController.class);

	@Autowired
	private GetIconService getIconService;

	// アイコン画像ファイルをサーバからダウンロード
	@RequestMapping(path = CommonConst.API_BASE_URL
			+ "/dl/usericon", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GetIconResponseEntity getUserIcon(@RequestBody GetIconRequestEntity requestEntity,
			HttpServletResponse response) throws IOException {

		logger.info(KdcCommonUtils.getApiStartLog(ApiIdEnum.DL_USERICON, requestEntity.getUserInfo(),
				requestEntity.getDeviceInfo()));

		GetIconResponseEntity responseEntity = new GetIconResponseEntity();
		responseEntity.setApiId(CommonConst.API_ID_DL_USERICON);

		// パラメータチェック
		if (!this.getIconService.parameterCheck(requestEntity, responseEntity)) {
			// HTTPステータスコードを"Bad Request(400)"に設定する。
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			responseEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.DL_USERICON, requestEntity.getUserInfo(),
					requestEntity.getDeviceInfo(), "parameterCheck"));
			return responseEntity;
		}

		// 対象ユーザマスタからアイコン取得
		UserMasterEntity entity = this.getIconService.getUser(requestEntity);
		if (entity.getIconfile() == null) {
			responseEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			responseEntity.setMessage("iconfile not found");
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.DL_USERICON, requestEntity.getUserInfo(),
					requestEntity.getDeviceInfo(), "iconfile not found"));
			return responseEntity;
		}
		responseEntity.setIconId(entity.getIconid());
		responseEntity.setIconFile(Base64.getEncoder().encodeToString(entity.getIconfile()));

		// レスポンス情報を登録
		responseEntity.setResultCd(CommonConst.RESULT_CD_SUCCESS);
		logger.info(KdcCommonUtils.getApiEndLog(ApiIdEnum.DL_USERICON, requestEntity.getUserInfo(),
				requestEntity.getDeviceInfo(), response.getStatus(), responseEntity.getResultCd()));
		return responseEntity;
	}
}
