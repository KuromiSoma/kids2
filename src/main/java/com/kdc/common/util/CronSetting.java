package com.kdc.common.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * cron設定ファイル読込処理.
 */
@Component
@ConfigurationProperties(prefix = "cron")
public class CronSetting {

	private String connectTask;

	/**
	 * @return connectTask
	 */
	public String getConnectTask() {
		return connectTask;
	}

	/**
	 * @param connectTask
	 *            セットする connectTask
	 */
	public void setConnectTask(String connectTask) {
		this.connectTask = connectTask;
	}

}
