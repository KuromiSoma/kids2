package com.kdc.api.getgroups;

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

import com.kdc.common.entity.db.GroupInfoEntity;
import com.kdc.common.enums.ApiIdEnum;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.KdcCommonUtils;

/**
 * グループ取得　API　コントローラクラス
 * @author umemoto
 *
 */
@RestController
@Transactional(rollbackFor = Exception.class)
public class GetGroupsController {

	private static final Logger logger = LoggerFactory.getLogger(GetGroupsController.class);
	
	@Autowired
	private GetGroupsService getGroupsService;
	
	//グループ情報取得
	@RequestMapping(path = CommonConst.API_BASE_URL + "/group/list" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GetGroupsResponseEntity getGroups(@RequestBody GetGroupsRequestEntity bean, HttpServletResponse response ) {
		//log
		logger.info(KdcCommonUtils.getApiStartLog(ApiIdEnum.GET_GROUPS, bean.getUserInfo(), bean.getDeviceInfo()));
		
		//レスポンス作成
		GetGroupsResponseEntity resEntity = new GetGroupsResponseEntity();
		resEntity.setApiId(CommonConst.API_ID_GET_GROUPS);

		//チェック関連
		if(!this.getGroupsService.parameterCheck(bean, resEntity)) {
			//HTTPステータスコードをBad Request(400)に設定する
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resEntity.setResultCd(CommonConst.RESULT_CD_FAILED);
			logger.info(KdcCommonUtils.getApiErrorLog(ApiIdEnum.GET_GROUPS, bean.getUserInfo(), bean.getDeviceInfo(), "parameterCheck"));
			return resEntity;
		}
		
		// DBから取得
		List<GroupInfoEntity> groupInfo = this.getGroupsService.getGroups(bean);
		
		resEntity.setResultCd(CommonConst.RESULT_CD_SUCCESS);
		resEntity.setGroupInfo(groupInfo);
		
		//log
		logger.info(KdcCommonUtils.getApiEndLog(ApiIdEnum.GET_GROUPS, bean.getUserInfo(), bean.getDeviceInfo(),response.getStatus(),resEntity.getResultCd()));

		return resEntity;
		
	}
}
