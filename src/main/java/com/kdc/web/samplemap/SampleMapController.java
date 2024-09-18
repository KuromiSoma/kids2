package com.kdc.web.samplemap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * サンプルコントローラー
 * @author Satake315Y
 *
 */
@Controller
@RequestMapping("/samplemap")
public class SampleMapController {

    @Autowired
    private SampleMapService sampleService;

    @RequestMapping(method = RequestMethod.GET)
	public String sample(Model model){
        // 入力項目の初期値を設定する
		SampleMapForm form = new SampleMapForm();

		// 画面初期処理
		sampleService.init(form);
		// コンボボックス設定
        //LinkedHashMap<String, String> cmbNendo = new LinkedHashMap<>();
        // 入力項目
        model.addAttribute("form", form);
		return "samplemap";
	}
	
    @RequestMapping(params = "action=doCommit", method = RequestMethod.POST)
    public String doCommit(Model model , SampleMapForm form) {

    	form.setOptdata("0");
        model.addAttribute("form", form);
		return "samplemap";
    }

    @RequestMapping(params = "action=doCancel", method = RequestMethod.POST)
    public String doCancel(Model model , SampleMapForm form) {

    	form.setTextdata("aaaaaaaaaa");
        model.addAttribute("form", form);
		return "samplemap";
    }
}
