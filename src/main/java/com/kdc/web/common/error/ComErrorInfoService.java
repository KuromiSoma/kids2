package com.kdc.web.common.error;

import java.util.ArrayList;

import com.kdc.web.common.error.ComErrorInfoData.ErrorInfo;

/**
 * 共通エラー判定処理の実装. 画面独自のエラー判定の場合は各自でComErrorInfoDataのerrorInfoListに追加すること。
 * <p>
 * 変更履歴 2017/09/08 t.fukuda 全体的に修正 エラーチェックメソッドはエラー判定の結果を戻すように修正.
 * 数量項目のチェックメソッドを追加(#5425).
 * </p>
 * 
 * @author Satake315Y
 *
 */
public interface ComErrorInfoService {

	/**
	 * エラー情報初期化処理.
	 * 
	 */
	public void errorInfoInit();

	/**
	 * 必須エラーチェックメソッド.
	 * 
	 * <p>
	 * 変更履歴 2017/09/08 t.fukuda エラーチェックメソッドはエラー判定の結果を戻すように修正.
	 * </p>
	 * 
	 * @param checkName
	 *            項目名
	 * @param checkValue
	 *            項目値
	 * @param changeColorId
	 *            色変更を行う親画面の項目ID
	 * @return エラー判定結果(true:エラー無し/false:エラー有り)
	 * 
	 */
	public boolean requiredErrorCheck(String checkName, String checkValue, String changeColorId);

	/**
	 * 数字チェックメソッド.
	 * 
	 * <p>
	 * 変更履歴 2017/09/08 t.fukuda メソッドを追加(#5425)
	 * </p>
	 * 
	 * @param checkName
	 *            項目名
	 * @param checkValue
	 *            項目値
	 * @param changeColorId
	 *            色変更を行う親画面の項目ID
	 * @return エラー判定結果(true:エラー無し/false:エラー有り)
	 * 
	 */
	public boolean numericErrorCheck(String checkName, String checkValue, String changeColorId);

	/**
	 * 範囲指定チェックメソッド.
	 * 
	 * <p>
	 * 変更履歴 2017/09/08 t.fukuda エラーチェックメソッドはエラー判定の結果を戻すように修正.
	 * </p>
	 * 
	 * @param checkName
	 *            項目名
	 * @param checkValue
	 *            項目値
	 * @param beginsValue
	 *            最小値
	 * @param endValue
	 *            最大値
	 * @param changeColorId
	 *            色変更を行う親画面の項目ID
	 * @return エラー判定結果(true:エラー無し/false:エラー有り)
	 * 
	 */
	public boolean rangeErrorCheck(String checkName, String checkValue, String beginsValue, String endValue,
			String changeColorId);

	/**
	 * 大小関係チェックメソッド. 開始または自 ＞ 終了または至 エラー状態=エラー
	 * 
	 * <p>
	 * 変更履歴 2017/09/08 t.fukuda エラーチェックメソッドはエラー判定の結果を戻すように修正.
	 * </p>
	 * 
	 * @param checkName
	 *            項目名
	 * @param smallValue
	 *            項目値（小）
	 * @param bigValue
	 *            項目値（大）
	 * @param changeColorId
	 *            色変更を行う親画面の項目ID
	 * @return エラー判定結果(true:エラー無し/false:エラー有り)
	 * 
	 */
	public boolean compareErrorCheck(String checkName, String smallValue, String bigValue, String changeColorId);

	/**
	 * 文字数チェックメソッド.
	 * 
	 * <p>
	 * 変更履歴 2017/09/08 t.fukuda エラーチェックメソッドはエラー判定の結果を戻すように修正.
	 * </p>
	 * 
	 * @param checkName
	 *            項目名
	 * @param checkValue
	 *            項目値
	 * @param checkLength
	 *            文字数
	 * @param changeColorId
	 *            色変更を行う親画面の項目ID
	 * @return エラー判定結果(true:エラー無し/false:エラー有り)
	 * 
	 */
	public boolean stringSizeErrorCheck(String checkName, String checkValue, int checkLength, String changeColorId);

	/**
	 * 最小文字数チェックメソッド.
	 * @param checkName
	 *            項目名
	 * @param checkValue
	 *            項目値
	 * @param checkLength
	 *            最小文字数
	 * @param changeColorId
	 *            色変更を行う親画面の項目ID
	 * @return エラー判定結果(true:エラー無し/false:エラー有り)
	 * 
	 */
	public boolean stringSizeMinErrorCheck(String checkName, String checkValue, int checkLength, String changeColorId);

	/**
	 * 全角文字数チェックメソッド.
	 * 
	 * <p>
	 * 変更履歴 2017/09/08 t.fukuda エラーチェックメソッドはエラー判定の結果を戻すように修正.
	 * </p>
	 * 
	 * @param checkName
	 *            項目名
	 * @param checkValue
	 *            項目値
	 * @param checkLength
	 *            文字数
	 * @param changeColorId
	 *            色変更を行う親画面の項目ID
	 * @return エラー判定結果(true:エラー無し/false:エラー有り)
	 * 
	 */
	public boolean stringMixedSizeErrorCheck(String checkName, String checkValue, int checkLength,
			String changeColorId);

	/**
	 * 時間チェックメソッド.
	 * 
	 * <p>
	 * 変更履歴 2017/09/08 t.fukuda エラーチェックメソッドはエラー判定の結果を戻すように修正.
	 * </p>
	 * 
	 * @param checkName
	 *            項目名
	 * @param checkHour
	 *            時
	 * @param checkMinute
	 *            分
	 * @param changeColorId
	 *            色変更を行う親画面の項目ID
	 * @return エラー判定結果(true:エラー無し/false:エラー有り)
	 * 
	 */
	public boolean jikanCheck(String checkName, String checkHour, String checkMinute, String changeColorId);

	/**
	 * エラー情報追加.
	 */
	public void addErrorInfo(ErrorInfo errorInfo);

	/**
	 * エラー情報取得.
	 */
	public ArrayList<ErrorInfo> getErrorInfoList();

}
