package com.kdc.common.enums;

import com.kdc.common.util.CommonConst;

/**
 * グループ登録モードEnum
 * @author umemoto
 *
 */
public enum RegisterGroupModeEnum {

	/** 登録 */
	INSERT(CommonConst.REGISTER_PLACE_MODE_INSERT),
	/** 変更 */
	UPDATE(CommonConst.REGISTER_PLACE_MODE_UPDATE),
	/** 削除 */
	DELETE(CommonConst.REGISTER_PLACE_MODE_DELETE);
	
	final int code;
	
	private RegisterGroupModeEnum(int code ) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public static RegisterGroupModeEnum valueOf(int rm) {
		switch(rm) {
		
		case CommonConst.REGISTER_GROUP_MODE_INSERT:
			return INSERT;
			
		case CommonConst.REGISTER_GROUP_MODE_UPDATE:
			return UPDATE;

		case CommonConst.REGISTER_GROUP_MODE_DELETE:
			return DELETE;
			
		default:
			throw new IllegalArgumentException("argument out of range");
		}
	}
	
}
