package com.kdc.web.common.error;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

/**
 * エラー・警告情報：データ
 * 
 * @author umemoto
 * @since 2016/08/24
 * @version
 *
 */
@Component
public class ComErrorInfoData {

	// エラー情報リスト
	private ArrayList<ErrorInfo> errorInfoList;

	/**
	 * エラー情報クラス .
	 */
	public static class ErrorInfo {

		// 位置
		private String errorPosition;

		// 色変更先セレクタID
		private String changeColorId;

		/**
		 * @return errorPosition
		 */
		public String getErrorPosition() {
			return errorPosition;
		}

		/**
		 * @param errorPosition
		 *            セットする errorPosition
		 */
		public void setErrorPosition(String errorPosition) {
			this.errorPosition = errorPosition;
		}

		/**
		 * @return changeColorId
		 */
		public String getChangeColorId() {
			return changeColorId;
		}

		/**
		 * @param changeColorId
		 *            セットする changeColorId
		 */
		public void setChangeColorId(String changeColorId) {
			this.changeColorId = changeColorId;
		}

	}

	/**
	 * @return errorInfoList
	 */
	public ArrayList<ErrorInfo> getErrorInfoList() {
		return errorInfoList;
	}

	/**
	 * @param errorInfoList
	 *            セットする errorInfoList
	 */
	public void setErrorInfoList(ArrayList<ErrorInfo> errorInfoList) {
		this.errorInfoList = errorInfoList;
	}
}