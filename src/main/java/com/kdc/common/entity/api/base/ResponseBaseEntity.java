package com.kdc.common.entity.api.base;

public class ResponseBaseEntity {

	private Integer apiId;
	private Integer resultCd;
	private String message;

	/**
	 * @return apiId
	 */
	public Integer getApiId() {
		return apiId;
	}

	/**
	 * @param apiId
	 *            セットする apiId
	 */
	public void setApiId(Integer apiId) {
		this.apiId = apiId;
	}

	/**
	 * @return resultCd
	 */
	public Integer getResultCd() {
		return resultCd;
	}

	/**
	 * @param resultCd
	 *            セットする resultCd
	 */
	public void setResultCd(Integer resultCd) {
		this.resultCd = resultCd;
	}

	/**
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            セットする message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
