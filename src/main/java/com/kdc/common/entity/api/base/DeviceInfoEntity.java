package com.kdc.common.entity.api.base;

public class DeviceInfoEntity extends DeviceInfoCoreEntity {

	private String tokenId;
	
	/**
	 * @return tokenId
	 */
	public String getTokenId() {
		return tokenId;
	}
	/**
	 * @param tokenId セットする tokenId
	 */
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

}
