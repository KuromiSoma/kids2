package com.kdc.mybatis.mapper.common.util;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kdc.common.entity.db.UserMasterEntity;

/**
 * 共通サービスMapperクラス
 *
 */
@Mapper
public interface CommonServiceMapper {

	/**
	 * 指定したライン表示色を使用しているユーザを取得.
	 * 
	 * @param linecolor
	 * 			ラインカラー
	 * @return ユーザマスタクラスリスト
	 */
	public List<UserMasterEntity> searchUserByColor(@Param("linecolor") String linecolor);

	/**
	 * ユーザID
	 * @return ユーザIDシーケンス
	 */
	public int getUserIdSeq();

	/**
	 * 場所ID
	 * @return 場所IDシーケンス
	 */
	public int getPlaceIdSeq();

	/**
	 * グループID
	 * @return グループIDシーケンス
	 */
	public int getGroupIdSeq();

}
