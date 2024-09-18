package com.kdc.web.webauthconfig;

import java.util.ArrayList;
import java.util.List;
import com.kdc.common.enums.DayOfWeekEnum;

/**
 * 送信間隔設定クラス
 */
public class TransmissionConfig {
	private String dayOfWeek;
	private String dayOfWeekLabel;
	private List<TransmissionConfigSub> sub;

	/**
	 * @return dayOfWeek
	 */
	public String getDayOfWeek() {
		return dayOfWeek;
	}

	/**
	 * @param dayOfWeek
	 *            セットする dayOfWeek
	 */
	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	/**
	 * @return dayOfWeekLabel
	 */
	public String getDayOfWeekLabel() {
		return dayOfWeekLabel;
	}

	/**
	 * @param dayOfWeekLabel
	 *            セットする dayOfWeekLabel
	 */
	public void setDayOfWeekLabel(String dayOfWeekLabel) {
		this.dayOfWeekLabel = dayOfWeekLabel;
	}

	/**
	 * @return sub
	 */
	public List<TransmissionConfigSub> getSub() {
		return sub;
	}

	/**
	 * @param sub
	 *            セットする sub
	 */
	public void setSub(List<TransmissionConfigSub> sub) {
		this.sub = sub;
	}

	public static List<TransmissionConfig> getNewTransmissionConfigList() {
		List<TransmissionConfig> dataList = new ArrayList<>();
		for (DayOfWeekEnum dayOfWeek : DayOfWeekEnum.values()) {
			TransmissionConfig data = new TransmissionConfig();
			data.setDayOfWeek(String.valueOf(dayOfWeek.getCode()));
			data.setDayOfWeekLabel(dayOfWeek.getLabel());
			data.setSub(TransmissionConfigSub.getNewTransmissionConfigSubList());
			dataList.add(data);
		}
		return dataList;
	}
}