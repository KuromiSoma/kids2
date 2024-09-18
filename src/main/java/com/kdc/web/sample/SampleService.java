package com.kdc.web.sample;

import org.springframework.stereotype.Service;

/**
 * サンプルサービス
 * @author Satake315Y
 *
 */
@Service
public class SampleService {

	/**
	 * 初期処理.
	 * 
	 * @param form
	 */
	public void init(SampleForm form) {

		// ラベル
		form.setLabeldata("1");
		// 各四項目
		form.setHdndata("1");
		// テキストボックス
		form.setTextdata("1");
		// コンボボックス(選択位置)
		form.setCmbdata("1");
		// チェックボックス１
		form.setChkdata1("1");
		// チェックボックス２
		form.setChkdata2("1");
		// ラジオボタン
		form.setOptdata("1");
	}
}
