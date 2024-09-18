package com.kdc.api.checkuser;

import com.kdc.common.entity.api.base.ResponseBaseEntity;

public class CheckUserResponseEntity extends ResponseBaseEntity {

	private int userExistsFlg = 0;   //ユーザー存在フラグ　０：存在しない　１：存在する
	private String password = "";	 //初期パスワード

	public int getUserExistsFlg() {
		return userExistsFlg;
	}

	public void setUserExistsFlg(int userExistsFlg) {
		this.userExistsFlg = userExistsFlg;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
