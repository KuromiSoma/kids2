package com.kdc.common.util;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * URL に係わるユーティリティクラス.
 */
@Component
public class UrlUtils {

	/**
	 * Spring MVC の View に対するリダイレクト指示の文字列を返す.
	 * 
	 * @param path
	 *            リダイレクト先のパス
	 * @return リダイレクト指示の文字列 ({@code not null})
	 */
	public static String getRedirectString(String path) {
		StringBuilder str = new StringBuilder();
		str.append("redirect:").append(CommonConst.WEB_BASE_URL);
		if (!path.startsWith("/")) {
			str.append("/");
		}
		str.append(path);

		return str.toString();
	}

	/**
	 * Spring MVC の View に対するフォワード指示の文字列を返す.
	 * 
	 * @param path
	 *            フォワード先のパス
	 * @return フォワード指示の文字列 ({@code not null})
	 */
	public static String getForwardString(String path) {
		StringBuilder str = new StringBuilder();
		str.append("forward:/").append(CommonConst.WEB_BASE_URL);
		if (!path.startsWith("/")) {
			str.append("/");
		}
		str.append(path);

		return str.toString();
	}

	/**
	 * Web 画面の URL 文字列を返す.
	 * <p>
	 * JavaScript や HTML などのリンクに使用する
	 * </p>
	 * 
	 * @param path
	 *            リンク先のパス
	 * @return URL文字列 ({@code not null})
	 */
	public static String getWebLinkUrl(String path) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		StringBuilder str = new StringBuilder();
		str.append(request.getContextPath());
		if (!path.startsWith("/")) {
			str.append("/");
		}
		str.append(path);

		return str.toString();
	}

}