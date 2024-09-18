package com.kdc.web.sample;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * サンプルコントローラー
 * 
 * @author Satake315Y
 *
 */
@Controller
@RequestMapping("/sample")
public class SampleController {

	@Autowired
	private SampleService sampleService;

	@RequestMapping(method = RequestMethod.GET)
	public String sample(Model model) {
		// 入力項目の初期値を設定する
		SampleForm form = new SampleForm();

		// 画面初期処理
		sampleService.init(form);
		// コンボボックス設定
		LinkedHashMap<String, String> cmbNendo = new LinkedHashMap<>();
		cmbNendo.put("2012", "2012");
		cmbNendo.put("2013", "2013");
		cmbNendo.put("2014", "2014");
		cmbNendo.put("2015", "2015");
		cmbNendo.put("2016", "2016");
		cmbNendo.put("2017", "2017");
		model.addAttribute("cmbNendo", cmbNendo);
		// 入力項目
		model.addAttribute("form", form);
		return "sample";
	}

	@RequestMapping(params = "action=doCommit", method = RequestMethod.POST)
	public String doCommit(Model model, SampleForm form) {

		form.setOptdata("0");
		model.addAttribute("form", form);
		return "sample";
	}

	@RequestMapping(params = "action=doCancel", method = RequestMethod.POST)
	public String doCancel(Model model, SampleForm form) {

		form.setTextdata("aaaaaaaaaa");
		model.addAttribute("form", form);
		return "sample";
	}
}
