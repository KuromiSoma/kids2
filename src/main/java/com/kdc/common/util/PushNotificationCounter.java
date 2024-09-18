package com.kdc.common.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * Push通知結果カウントクラス.
 */
@Service
public class PushNotificationCounter {

	private Integer sendCompleteCnt = 0;
	private Integer sendErrorCnt = 0;
	private Integer sendExceptCnt = 0;
	private List<String> errorTokenList = new ArrayList<>();

	/**
	 * @return sendCompleteCnt
	 */
	public Integer getSendCompleteCnt() {
		return sendCompleteCnt;
	}

	/**
	 * @param sendCompleteCnt
	 *            セットする sendCompleteCnt
	 */
	public void setSendCompleteCnt(Integer sendCompleteCnt) {
		this.sendCompleteCnt = sendCompleteCnt;
	}

	/**
	 * @return sendErrorCnt
	 */
	public Integer getSendErrorCnt() {
		return sendErrorCnt;
	}

	/**
	 * @param sendErrorCnt
	 *            セットする sendErrorCnt
	 */
	public void setSendErrorCnt(Integer sendErrorCnt) {
		this.sendErrorCnt = sendErrorCnt;
	}

	/**
	 * @return sendExceptCnt
	 */
	public Integer getSendExceptCnt() {
		return sendExceptCnt;
	}

	/**
	 * @param sendExceptCnt
	 *            セットする sendExceptCnt
	 */
	public void setSendExceptCnt(Integer sendExceptCnt) {
		this.sendExceptCnt = sendExceptCnt;
	}

	/**
	 * @return errorTokenList
	 */
	public List<String> getErrorTokenList() {
		return errorTokenList;
	}

	/**
	 * @param errorTokenList
	 *            セットする errorTokenList
	 */
	public void setErrorTokenList(List<String> errorTokenList) {
		this.errorTokenList = errorTokenList;
	}
}
