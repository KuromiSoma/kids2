package com.kdc.web.sample;

import org.springframework.web.multipart.MultipartFile;

public class SampleForm {

	// ラベル
	private String labeldata;
	// 各四項目
	private String hdndata;
	// テキストボックス
	private String textdata;
	// コンボボックス(選択位置)
	private String cmbdata;
	// チェックボックス１
	private String chkdata1;
	// チェックボックス２
	private String chkdata2;
	// ラジオボタン
	private String optdata;
	// ファイル
    private MultipartFile fileData;
	/**
	 * @return labeldata
	 */
	public String getLabeldata() {
		return labeldata;
	}
	/**
	 * @param labeldata セットする labeldata
	 */
	public void setLabeldata(String labeldata) {
		this.labeldata = labeldata;
	}
	/**
	 * @return hdndata
	 */
	public String getHdndata() {
		return hdndata;
	}
	/**
	 * @param hdndata セットする hdndata
	 */
	public void setHdndata(String hdndata) {
		this.hdndata = hdndata;
	}
	/**
	 * @return textdata
	 */
	public String getTextdata() {
		return textdata;
	}
	/**
	 * @param textdata セットする textdata
	 */
	public void setTextdata(String textdata) {
		this.textdata = textdata;
	}
	/**
	 * @return cmbdata
	 */
	public String getCmbdata() {
		return cmbdata;
	}
	/**
	 * @param cmbdata セットする cmbdata
	 */
	public void setCmbdata(String cmbdata) {
		this.cmbdata = cmbdata;
	}
	/**
	 * @return chkdata1
	 */
	public String getChkdata1() {
		return chkdata1;
	}
	/**
	 * @param chkdata1 セットする chkdata1
	 */
	public void setChkdata1(String chkdata1) {
		this.chkdata1 = chkdata1;
	}
	/**
	 * @return chkdata2
	 */
	public String getChkdata2() {
		return chkdata2;
	}
	/**
	 * @param chkdata2 セットする chkdata2
	 */
	public void setChkdata2(String chkdata2) {
		this.chkdata2 = chkdata2;
	}
	/**
	 * @return optdata
	 */
	public String getOptdata() {
		return optdata;
	}
	/**
	 * @param optdata セットする optdata
	 */
	public void setOptdata(String optdata) {
		this.optdata = optdata;
	}
	/**
	 * @return fileData
	 */
	public MultipartFile getFileData() {
		return fileData;
	}
	/**
	 * @param fileData セットする fileData
	 */
	public void setFileData(MultipartFile fileData) {
		this.fileData = fileData;
	}
    
    
}
