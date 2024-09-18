package com.kdc.mybatis.mapper.common.entity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kdc.common.entity.db.ConfigMasterEntity;

/**
 * 設定マスタ Mapperクラス
 *
 */
@Mapper
public interface ConfigMasterMapper {

	/**
	 * 全件取得.
	 * 
	 * @return 設定マスタクラスリスト
	 */
	public List<ConfigMasterEntity> selectAll();

	/**
	 * 全件取得.
	 * 
	 * @param delflg
	 * 			削除フラグ
	 * @param sort1
	 * 			ソート判定1
	 * @param sort2
	 * 			ソート判定2
	 * @return 設定マスタクラスリスト
	 */
	public List<ConfigMasterEntity> selectAll(@Param("groupid") String groupid, @Param("delflg") Integer delflg, @Param("sort1") boolean sort1,
			@Param("sort2") boolean sort2);

	/**
	 * プライマリキーを指定して取得.
	 * 
	 * @param configid
	 * 			設定区分ID
	 * @param configcode
	 * 			設定区分コード
	 * @param delflg
	 * 			削除フラグ
	 * @return 設定マスタクラス
	 */
	public ConfigMasterEntity selectByPk(@Param("configid") String configid, @Param("configcode") String configcode,
			@Param("delflg") Integer delflg);

	/**
	 * IDのみを指定して設定リストを取得.
	 * 
	 * @param configid
	 * 			設定区分ID
	 * @return 設定マスタクラスリスト
	 */
	public List<ConfigMasterEntity> selectCodeList(@Param("configid") int configid, @Param("groupid") String groupid);
//	public List<ConfigMasterEntity> selectCodeList(@Param("configid") int configid);

	/**
	 * 追加.
	 * 
	 * @param rec
	 * 			設定マスタクラス
	 * @return 追加件数
	 */
	public int insert(@Param("rec") ConfigMasterEntity rec);

	/**
	 * 更新.
	 * 
	 * @param rec
	 * 			設定マスタクラス
	 * @return 更新件数
	 */
	public int update(@Param("rec") ConfigMasterEntity rec);

}
