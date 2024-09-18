package com.kdc.web.common.error;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kdc.common.util.KdcCommonUtils;
import com.kdc.web.common.error.ComErrorInfoData.ErrorInfo;

/**
 * 共通エラー判定処理の実装サービス.
 * <p>
 * 変更履歴 2017/09/08 t.fukuda 全体的に修正 エラーチェックメソッドはエラー判定の結果を戻すように修正(#5422関連).
 * 数量項目のチェックメソッドを追加(#5425). 2017/09/11 t.fukuda エラーチェック内容修正(#5425関連)
 * rangeErrorCheck,compareErrorCheckでは入力値の数字チェックを行うように修正
 * </p>
 * 
 * @author Satake315Y
 *
 */
@Service
public class ComErrorInfoServiceImpl implements ComErrorInfoService {

	@Autowired
	private ComErrorInfoData comErrorInfoData;

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public void errorInfoInit() {
		this.comErrorInfoData.setErrorInfoList(new ArrayList<ErrorInfo>());
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public boolean requiredErrorCheck(String checkName, String checkValue, String changeColorId) {
		// エラー情報リスト
		ErrorInfo errorInfo = null;
		// 必須入力チェック
		if (StringUtils.isEmpty(checkValue)) {
			errorInfo = new ErrorInfo();
			errorInfo.setErrorPosition(checkName);
			errorInfo.setChangeColorId(changeColorId);

			this.comErrorInfoData.getErrorInfoList().add(errorInfo);
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public boolean numericErrorCheck(String checkName, String checkValue, String changeColorId) {
		// エラー情報リスト
		ErrorInfo errorInfo = null;
		if (KdcCommonUtils.isEmpty(checkValue)) {
			return true;
		}
		if (!StringUtils.isNumeric(checkValue)) {
			errorInfo = new ErrorInfo();
			errorInfo.setErrorPosition(checkName);
			errorInfo.setChangeColorId(changeColorId);
			this.comErrorInfoData.getErrorInfoList().add(errorInfo);
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}.
	 * <p>
	 * 変更履歴 2017/09/11 t.fukuda 入力値の数字チェックを行うように修正.
	 * </p>
	 */
	@Override
	public boolean rangeErrorCheck(String checkName, String checkValue, String beginValue, String endValue,
			String changeColorId) {

		// エラー情報リスト
		ErrorInfo errorInfo = null;
		// 先に数字チェックを実施
		if (!this.numericErrorCheck(checkName, checkValue, changeColorId)) {
			return false;
		}
		// 範囲指定チェック
		if (!StringUtils.isEmpty(beginValue) && !StringUtils.isEmpty(checkValue) && !StringUtils.isEmpty(endValue)) {

			if (Integer.parseInt(checkValue) < Integer.parseInt(beginValue)
					|| Integer.parseInt(endValue) < Integer.parseInt(checkValue)) {
				errorInfo = new ErrorInfo();
				errorInfo.setErrorPosition(checkName);
				errorInfo.setChangeColorId(changeColorId);
				this.comErrorInfoData.getErrorInfoList().add(errorInfo);
				return false;
			}
		}
		return true;
	}

	/**
	 * {@inheritDoc}.
	 * <p>
	 * 変更履歴 2017/09/11 t.fukuda 入力値の数字チェックを行うように修正.
	 * </p>
	 */
	@Override
	public boolean compareErrorCheck(String checkName, String smallValue, String bigValue, String changeColorId) {

		// エラー情報リスト
		ErrorInfo errorInfo = null;
		// 先に数字チェックを実施
		if (!this.numericErrorCheck(checkName, smallValue, changeColorId)) {
			return false;
		}
		if (!this.numericErrorCheck(checkName, bigValue, changeColorId)) {
			return false;
		}
		// 範囲指定チェック
		if (!StringUtils.isEmpty(smallValue) && !StringUtils.isEmpty(bigValue)) {

			if (Integer.parseInt(bigValue) < Integer.parseInt(smallValue)) {
				errorInfo = new ErrorInfo();
				errorInfo.setErrorPosition(checkName);
				errorInfo.setChangeColorId(changeColorId);
				this.comErrorInfoData.getErrorInfoList().add(errorInfo);
				return false;
			}
		}
		return true;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public boolean stringSizeErrorCheck(String checkName, String checkValue, int checkLength, String changeColorId) {

		// エラー情報リスト
		ErrorInfo errorInfo = null;
		// 件数チェック
		if (KdcCommonUtils.nullToEmpty(checkValue).length() > checkLength) {
			// 入力チェック
			errorInfo = new ErrorInfo();
			errorInfo.setErrorPosition(checkName);
			errorInfo.setChangeColorId(changeColorId);
			this.comErrorInfoData.getErrorInfoList().add(errorInfo);
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public boolean stringSizeMinErrorCheck(String checkName, String checkValue, int checkLength, String changeColorId) {

		// エラー情報リスト
		ErrorInfo errorInfo = null;
		// 文字数が最小値に満たない場合エラー
		if (KdcCommonUtils.nullToEmpty(checkValue).length() < checkLength) {
			// 入力チェック
			errorInfo = new ErrorInfo();
			errorInfo.setErrorPosition(checkName);
			errorInfo.setChangeColorId(changeColorId);
			this.comErrorInfoData.getErrorInfoList().add(errorInfo);
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public boolean stringMixedSizeErrorCheck(String checkName, String checkValue, int checkLength,
			String changeColorId) {

		// エラー情報リスト
		ErrorInfo errorInfo = null;
		// 件数チェック
		try {
			if (KdcCommonUtils.nullToEmpty(checkValue).getBytes("Shift_JIS").length > checkLength) {
				// 入力チェック
				errorInfo = new ErrorInfo();
				errorInfo.setErrorPosition(checkName);
				errorInfo.setChangeColorId(changeColorId);
				this.comErrorInfoData.getErrorInfoList().add(errorInfo);
				return false;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public boolean jikanCheck(String checkName, String checkHour, String checkMinute, String changeColorId) {

		// エラー情報リスト
		ErrorInfo errorInfo = null;

		// 時チェック
		if (!KdcCommonUtils.isEmpty(checkHour)){
			if (StringUtils.contains(checkHour, ".") 
					|| KdcCommonUtils.nullSafeParseInt(checkHour) == null 
					|| Integer.parseInt(checkHour) < 0
					|| Integer.parseInt(checkHour) >= 24) {
				errorInfo = new ErrorInfo();
				errorInfo.setErrorPosition(checkName);
				errorInfo.setChangeColorId(changeColorId);
				this.comErrorInfoData.getErrorInfoList().add(errorInfo);
				return false;
			}
		}
		// 分チェック
		if (!KdcCommonUtils.isEmpty(checkMinute)) {
			if (StringUtils.contains(checkMinute, ".")
					|| KdcCommonUtils.nullSafeParseInt(checkMinute) == null
					|| Integer.parseInt(checkMinute) < 0 
					|| Integer.parseInt(checkMinute) >= 60) {
				errorInfo = new ErrorInfo();
				errorInfo.setErrorPosition(checkName);
				errorInfo.setChangeColorId(changeColorId);
				this.comErrorInfoData.getErrorInfoList().add(errorInfo);
				return false;
			}
		}
		return true;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public void addErrorInfo(ErrorInfo errorInfo) {
		if (errorInfo != null) {
			this.comErrorInfoData.getErrorInfoList().add(errorInfo);
		}
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public ArrayList<ErrorInfo> getErrorInfoList() {
		return this.comErrorInfoData.getErrorInfoList();
	}
}
