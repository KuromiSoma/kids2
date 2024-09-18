package com.kdc.mybatis.mapper.api;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kdc.common.entity.db.GroupInfoEntity;

/**
 * グループ情報取得API　Mapperクラス
 * @author umemoto
 *
 */
@Mapper
public interface GetGroupsMapper {

	/**
	 * グループ情報取得
	 * @return グループ情報レスポンスクラスリスト
	 */
	public List<GroupInfoEntity> getGroupList();
}
