package com.kdc.web.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kdc.common.entity.db.AdministratorMasterEntity;
import com.kdc.mybatis.mapper.web.WebLoginMapper;

/**
 * ログイン画面 Service クラス
 * 
 *
 */
@Service
public class WebLoginService {

	@Autowired
	private WebLoginMapper webLoginMapper;

	/**
	 * 初期処理.
	 * 
	 * @param form
	 */
	public void init(WebLoginForm form) {
		form.setLoginId("");
		form.setLoginPass("");
		form.setLoginErrorFlg(false);
	}

	/**
	 * 管理者マスタ取得.
	 * 
	 * @param form
	 * 			ログイン画面フォーム
	 * @return 取得判定結果
	 */
	public Boolean getAdministrator(WebLoginForm form) {

		AdministratorMasterEntity rec = new AdministratorMasterEntity();
		rec.setAdministratorid(form.getLoginId());
		rec.setPassword(form.getLoginPass());

		AdministratorMasterEntity entity = webLoginMapper.selectAdmin(rec);
		if (entity == null) {
			return false;
		}
		return true;
	}

}