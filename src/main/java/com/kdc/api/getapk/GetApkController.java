package com.kdc.api.getapk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kdc.common.util.CommonConst;

/**
 * APKファイルダウンロード API Controller クラス
 *
 */
@RestController
public class GetApkController {

	@Autowired
	private ResourceLoader resourceLoader;

	// APKファイルをサーバからダウンロード
	@RequestMapping(path = CommonConst.API_BASE_URL + "/dl/apk", method = RequestMethod.GET)
	public HttpEntity<byte[]> getApk(HttpServletResponse httpServletResponse) throws IOException {

		Resource resource = this.resourceLoader
				.getResource("file:" + CommonConst.SYSTEM_MODULE_PATH + CommonConst.APK_FILE_NAME);
		InputStream inputStream = resource.getInputStream();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		// byteへ変換
		IOUtils.copy(inputStream, byteArrayOutputStream);
		byte[] binFile = byteArrayOutputStream.toByteArray();

		// レスポンスデータとして返却
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("filename", CommonConst.APK_FILE_NAME);
		headers.setContentLength(binFile.length);
		return new HttpEntity<byte[]>(binFile, headers);
	}
}
