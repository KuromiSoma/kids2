package com.kdc.mybatis.mapper.common.entity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kdc.common.entity.db.PlaceMasterEntityWrapper;

/**
 * 場所マスタMapperクラス
 *
 */
@Mapper
public interface PlaceMasterMapper {

	/**
	 * 全件取得.
	 * 
	 * @return 場所マスタクラスリスト
	 */
	public List<PlaceMasterEntityWrapper> selectAll();

	/**
	 * 全件取得.
	 * 
	 * @return 場所マスタクラスリスト
	 */
	public List<PlaceMasterEntityWrapper> selectAll(@Param("delflg") Integer delflg,@Param("groupid") String groupid, @Param("sort") boolean sort);

	/**
	 * プライマリキーを指定して取得.
	 * 
	 * @return 場所マスタクラス
	 */
	public PlaceMasterEntityWrapper selectByPk(@Param("placeid") String placeid, @Param("delflg") Integer delflg);

	/**
	 * 追加.
	 * 
	 * @param rec
	 * 			場所マスタクラス
	 * @return 追加件数 
	 */
	public int insert(@Param("rec") PlaceMasterEntityWrapper rec);

	/**
	 * 更新.
	 * 
	 * @param rec
	 * 			場所マスタクラス
	 * @return 更新件数 
	 */
	public int update(@Param("rec") PlaceMasterEntityWrapper rec);

	/**
	 * 論理削除.
	 * 
	 * @param rec
	 * 			場所マスタクラス
	 * @return 更新件数 
	 */
	public int delete(@Param("rec") PlaceMasterEntityWrapper rec);

}
