package com.kdc.api.checkuser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kdc.common.entity.db.ConfigMasterEntity;
import com.kdc.common.entity.db.UserMasterEntity;
import com.kdc.common.util.CommonConst;
import com.kdc.common.util.CommonService;
import com.kdc.mybatis.mapper.common.entity.ConfigMasterMapper;
import com.kdc.mybatis.mapper.common.entity.UserMasterMapper;

@Service
public class CheckUserService {

	@Autowired
	private UserMasterMapper userMasterMapper;
	
	@Autowired
	private CommonService commonService;

	@Autowired
	private ConfigMasterMapper configMasterMapper;

	/**
	 * パラメータチェック
	 * 
	 */
	public Boolean parameterCheck(CheckUserRequestEntity reqEntity, CheckUserResponseEntity resEntity) {

		//端末
		if ( reqEntity.getDeviceInfo() == null ) {
			resEntity.setMessage("deviceInfo is Empty ");
			return false;
		}
		//単項目チェック
		if ( !this.commonService.checkDeviceInfoValues(reqEntity.getDeviceInfo(), resEntity)) {
			return false;
		}
		
		return true;
	}
	
	public Boolean checkRegistration(CheckUserRequestEntity bean, CheckUserResponseEntity res) {
		//ユーザー情報取得（1人でもユーザが存在するか）
		List<UserMasterEntity> userList = this.userMasterMapper.selectAll();
		if ( userList.size() > 0) {
			return true;
		}
		return false;
	}

	public String getPassword(CheckUserRequestEntity bean, CheckUserResponseEntity res) {
		//初期パスワード取得
		List<ConfigMasterEntity> configList = this.configMasterMapper.selectCodeList(CommonConst.CONFIG_ID_INIT_PASSWORD,"0");
		String password = (configList != null) ? String.valueOf(configList.get(0).getConfigcode()) : "0";
		return password;
	}
}
