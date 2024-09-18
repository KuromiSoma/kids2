package com.kdc.web.webauthconfig;

import java.util.ArrayList;
import java.util.List;

import com.kdc.common.util.CommonConst;

/**
 * 送信間隔設定サブクラス
 */
public class TransmissionConfigSub {
	private boolean changed;
	private String configNo;
	private String startTime;
	private String endTime;
	private String transmissionInterval;

	/**
	 * @return changed
	 */
	public boolean isChanged() {
		return changed;
	}

	/**
	 * @param changed
	 *            セットする changed
	 */
	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	/**
	 * @return configNo
	 */
	public String getConfigNo() {
		return configNo;
	}

	/**
	 * @param configNo
	 *            セットする configNo
	 */
	public void setConfigNo(String configNo) {
		this.configNo = configNo;
	}

	/**
	 * @return startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            セットする startTime
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            セットする endTime
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return transmissionInterval
	 */
	public String getTransmissionInterval() {
		return transmissionInterval;
	}

	/**
	 * @param transmissionInterval
	 *            セットする transmissionInterval
	 */
	public void setTransmissionInterval(String transmissionInterval) {
		this.transmissionInterval = transmissionInterval;
	}

	public static List<TransmissionConfigSub> getNewTransmissionConfigSubList() {
		List<TransmissionConfigSub> dataList = new ArrayList<>();
		for (int i = 0; i < CommonConst.SEND_INTERVAL_CONFIG_COUNT; i++) {
			TransmissionConfigSub data = new TransmissionConfigSub();
			data.setConfigNo(String.valueOf(i + 1));
			dataList.add(data);
		}
		return dataList;
	}
}