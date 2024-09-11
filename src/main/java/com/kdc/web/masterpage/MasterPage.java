package com.kdc.web.masterpage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.kdc.web.common.javascript.JavaScriptConfirmHolder;
import com.kdc.web.common.javascript.JavaScriptHolder;

/**
 * マスタページ処理（認証なし）
 * 
 * @author umemoto
 * @since 2016/07/06
 * @version
 *
 */
@Component
public class MasterPage {

    // 確認メッセージ情報保持
    @Autowired
    private JavaScriptConfirmHolder javaScriptConfirmHolder;
    
    // Ajax 情報保持
    @Autowired
    private JavaScriptHolder javaScriptHolder;

//    // 元号情報
//    @Autowired
//    private CodeInfoGengoHolder codeInfoGengoHolder; 
    
    /**
     * マスタページ表示処理. 
     */
    public void showPage(Model model) {
        // JavaScript の設定
        StringBuilder js = new StringBuilder();
        // （確認メッセージ用のJavaScript）
        js.append(this.javaScriptConfirmHolder.getJavaScriptSource(false));
//        // （元号変換用の情報）
//        js.append("var msSrvGengoDays=").append(this.codeInfoGengoHolder.getJsGengoDaysArray()).append(";");
//        js.append("var msSrvGengoWarekis=").append(this.codeInfoGengoHolder.getJsGengoWarekisArray()).append(";");
//        // （休日判断用の情報）
//        js.append("var msSrvHolidays=").append("[];");
        // （ユーザ定義のJavaScript）
        js.append(this.javaScriptHolder.getJavaScriptSource(false));
        model.addAttribute("pagejs", js.toString());

        // CSS の設定
        StringBuilder css = new StringBuilder();
        css.append(this.javaScriptHolder.getCssStyleSource(false));
        model.addAttribute("pagecss", css.toString());
        
        return;
    }

}