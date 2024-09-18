package com.kdc.mybatis.mapper.api;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kdc.common.entity.db.UserMasterEntity;

/**
 * ユーザ情報変更 API Mapperクラス
 *
 */
@Mapper
public interface SettingUserMapper {

	/**
	 * ユーザマスタ更新
	 * @param regRec
	 * 			ユーザマスタクラス
	 * @param condRec
	 * 			ユーザマスタクラス
	 * @return 更新件数
	 */
	public int updateUserMaster(@Param("regRec") UserMasterEntity regRec, @Param("condRec") UserMasterEntity condRec);

	/**
	 * ユーザ名重複チェック
	 * @param rec
	 * @return 該当件数
	 */
	public Integer selectUserInfo(@Param("rec") UserMasterEntity rec);

}
