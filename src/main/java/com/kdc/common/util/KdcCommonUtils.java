package com.kdc.common.util;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.AreaAveragingScaleFilter;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Component;
import com.kdc.common.entity.api.base.DeviceInfoEntity;
import com.kdc.common.entity.api.base.UserInfoEntity;
import com.kdc.common.entity.db.PlaceMasterEntityWrapper;
import com.kdc.common.enums.ApiIdEnum;

/**
 * キッズコントロール用ユーティリティクラス.
 */
@Component
public class KdcCommonUtils {

	private static DateTimeFormatter DATEFORMAT_DATETIME = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	private static DateTimeFormatter DATEFORMAT_DATE = DateTimeFormatter.ofPattern("yyyyMMdd");
	private static DateTimeFormatter DATEFORMAT_TIME = DateTimeFormatter.ofPattern("HHmm");
	private static DateTimeFormatter DATEFORMAT_TIME_DISP = DateTimeFormatter.ofPattern("H:mm");
	private static DateTimeFormatter DATEFORMAT_TIME_HMS_DISP = DateTimeFormatter.ofPattern("H:mm:ss");
	private static DateTimeFormatter DATEFORMAT_CALENDARDATE = DateTimeFormatter.ofPattern("yyyy.MM.dd E");

	private static double WGS84_ECC_POW2 = (Math.pow(CommonConst.WGS84_EQU_RAD, 2)
			- Math.pow(CommonConst.WGS84_POL_RAD, 2)) / Math.pow(CommonConst.WGS84_EQU_RAD, 2);
	private static double WGS84_MARC_CON = CommonConst.WGS84_EQU_RAD * (1 - WGS84_ECC_POW2);

	private static Map<String, String> propertyMap = new HashMap<>();

	public static UserColorSet[] USER_COLOR_LIST;

	/**
	 * プロパティ情報を取得.
	 * 
	 * @param key
	 * @return 設定ファイルのプロパティ情報
	 */
	public static String getProperty(String key) {
		if (propertyMap.isEmpty()) {
			loadPropertiesFromIni();
		}
		if (propertyMap.containsKey(key)) {
			return propertyMap.get(key);
		} else {
			return loadPropertiesFromIni(key);
		}
	}

	/**
	 * 設定ファイルからプロパティ情報をロードする
	 */
	public static void loadPropertiesFromIni() {
		try {
			Properties properties = getPropertyFile();
			// Mapに格納
			for (Map.Entry<Object, Object> e : properties.entrySet()) {
				propertyMap.put(e.getKey().toString(), e.getValue().toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return;
	}

	/**
	 * 設定ファイルからキーを指定してプロパティ情報をロードする
	 * 
	 * @param key
	 * @return プロパティ情報配列
	 */
	public static String loadPropertiesFromIni(String key) {
		String value = new String();
		try {
			value = getPropertyFile().getProperty(key, null);
			if (StringUtils.isNotEmpty(value)) {
				propertyMap.put(key, value);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return value;
	}

	/**
	 * プロパティファイルを取得する
	 * 
	 * @return プロパティ情報
	 * @throws IOException
	 */
	public static Properties getPropertyFile() throws IOException {
		Properties properties = new Properties();
		// プロパティファイルのパスを指定する
		String strpass = CommonConst.SYSTEM_MODULE_PATH + CommonConst.SYSTEM_INI_FILE_NAME;

		InputStream istream = new FileInputStream(strpass);
		InputStreamReader ireader = new InputStreamReader(istream, "SJIS");
		properties.load(ireader);
		istream.close();

		return properties;
	}

	/**
	 * 現在日時のTimestamp値を取得
	 * 
	 * @return 現在日時Timestampの文字列
	 */
	public static Timestamp getNowTimestamp() {
		return Timestamp.valueOf(LocalDateTime.now().withNano(0));
	}

	/**
	 * 現在日時文字列(YYYYMMDDHHMISS)を取得
	 * 
	 * @return 現在日時yyyyMMddHHmmssの文字列
	 */
	public static String getNowDateTimeString() {
		return LocalDateTime.now().format(DATEFORMAT_DATETIME);
	}

	/**
	 * 現在日付文字列(YYYYMMDD)を取得
	 * 
	 * @return 現在日時yyyyMMddの文字列
	 */
	public static String getNowDateString() {
		return LocalDateTime.now().format(DATEFORMAT_DATE);
	}

	/**
	 * Timestamp値を日時文字列(YYYYMMDDHHMISS)に変換
	 * 
	 * @param srcTimestamp
	 * @return 日時yyyyMMddHHmmssの文字列
	 */
	public static String timestampToDateTimeString(Timestamp srcTimestamp) {
		if (srcTimestamp == null) {
			return null;
		}
		return srcTimestamp.toLocalDateTime().format(DATEFORMAT_DATETIME);
	}

	/**
	 * Timestamp値を日付文字列(YYYYMMDD)に変換
	 * 
	 * @param srcTimestamp
	 * @return 日時yyyyMMddの文字列
	 */
	public static String timestampToDateString(Timestamp srcTimestamp) {
		if (srcTimestamp == null) {
			return null;
		}
		return srcTimestamp.toLocalDateTime().format(DATEFORMAT_DATE);
	}

	/**
	 * Time値を時分文字列(HHMI)に変換
	 * 
	 * @param srcTime
	 * @return 時間HHmmの文字列
	 */
	public static String timeToHourMinuteString(Time srcTime) {
		if (srcTime == null) {
			return null;
		}
		return srcTime.toLocalTime().format(DATEFORMAT_TIME);
	}

	/**
	 * Time値を表示用時分文字列(H:MI)に変換
	 * 
	 * @param srcTime
	 * @return 時間H:mmの文字列
	 */
	public static String timeToHourMinuteStringForDisp(Time srcTime) {
		if (srcTime == null) {
			return null;
		}
		return srcTime.toLocalTime().format(DATEFORMAT_TIME_DISP);
	}

	/**
	 * Timestamp値を表示用時刻文字列(H:MI:SS)に変換
	 * 
	 * @param srcTimestamp
	 * @return 時間H:mm:ssの文字列
	 */
	public static String timeToTimeStringForDisp(Timestamp srcTimestamp) {
		if (srcTimestamp == null) {
			return null;
		}
		return srcTimestamp.toLocalDateTime().format(DATEFORMAT_TIME_HMS_DISP);
	}

	/**
	 * Timestamp値をカレンダー表示用文字列に変換
	 * 
	 * @param srcTimestamp
	 * @return 日時yyyy.MM.dd Eの文字列
	 */
	public static String timestampToCalendarDateString(Timestamp srcTimestamp) {
		if (srcTimestamp == null) {
			return null;
		}
		return srcTimestamp.toLocalDateTime().format(DATEFORMAT_CALENDARDATE);
	}

	/**
	 * 日時文字列(YYYYMMDDHHMISS)をTimestamp値に変換
	 * 
	 * @param srcString
	 * @return 日時yyyyMMddHHmmssのTimestamp値
	 */
	public static Timestamp dateTimeStringToTimestamp(String srcString) {
		if (isEmpty(srcString)) {
			return null;
		}
		try {
			LocalDateTime dateTime = LocalDateTime.parse(srcString, DATEFORMAT_DATETIME);
			return Timestamp.valueOf(dateTime);
		} catch (DateTimeParseException e) {
			// 時間部分指定無しの場合は00:00:00扱いで変換可
			return dateStringToTimestamp(srcString);
		}
	}

	/**
	 * 日付文字列(YYYYMMDD)をTimestamp値に変換
	 * 
	 * @param srcString
	 * @return 日時yyyyMMddのTimestamp値
	 */
	public static Timestamp dateStringToTimestamp(String srcString) {
		if (isEmpty(srcString)) {
			return null;
		}
		try {
			LocalDate date = LocalDate.parse(srcString, DATEFORMAT_DATE);
			return Timestamp.valueOf(date.atTime(LocalTime.MIN));
		} catch (DateTimeParseException e) {
			return null;
		}
	}

	/**
	 * 時分文字列(HHMI)をTime値に変換
	 * 
	 * @param srcString
	 * @return 日時HHmmのTime値
	 */
	public static Time hourMinuteStringToTime(String srcString) {
		if (isEmpty(srcString)) {
			return null;
		}
		try {
			LocalTime time = LocalTime.parse(srcString, DATEFORMAT_TIME);
			return Time.valueOf(time);
		} catch (DateTimeParseException e) {
			return null;
		}
	}

	/**
	 * 表示用時分文字列(HH:MI)をTime値に変換
	 * 
	 * @param srcString
	 * @return 日時H:mmのTime値
	 */
	public static Time hourMinuteStringForDispToTime(String srcString) {
		if (isEmpty(srcString)) {
			return null;
		}
		try {
			LocalTime time = LocalTime.parse(srcString, DATEFORMAT_TIME_DISP);
			return Time.valueOf(time);
		} catch (DateTimeParseException e) {
			// 通常の時分文字列も変換可
			return hourMinuteStringToTime(srcString);
		}
	}

	/**
	 * 日時文字列(YYYYMMDDHHMISS)が正しい形式かどうか判定する.
	 * 
	 * @param srcString
	 * @return 判定結果
	 */
	public static boolean checkDateTimeString(String srcString) {
		if (isEmpty(srcString)) {
			return false;
		}
		try {
			LocalDateTime.parse(srcString, DATEFORMAT_DATETIME);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}

	/**
	 * 日付文字列(YYYYMMDD)が正しい形式かどうか判定する.
	 * 
	 * @param srcString
	 * @return 判定結果
	 */
	public static boolean checkDateString(String srcString) {
		if (isEmpty(srcString)) {
			return false;
		}
		try {
			LocalDate.parse(srcString, DATEFORMAT_DATE);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}

	/**
	 * 時分文字列(HHMI)が正しい形式かどうか判定する.
	 * 
	 * @param srcString
	 * @return 判定結果
	 */
	public static boolean checkHourMinuteString(String srcString) {
		if (isEmpty(srcString)) {
			return false;
		}
		try {
			LocalTime.parse(srcString, DATEFORMAT_TIME);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}

	/**
	 * あるTimestamp値から指定日時後のTimestamp値を取得
	 * 
	 * @param srcTimestamp
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 * @return Timestamp値
	 */
	public static Timestamp timestampPlusDateTime(Timestamp srcTimestamp, int year, int month, int day, int hour,
			int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(srcTimestamp);
		cal.add(Calendar.YEAR, year);
		cal.add(Calendar.MONTH, month);
		cal.add(Calendar.DAY_OF_MONTH, day);
		cal.add(Calendar.HOUR, hour);
		cal.add(Calendar.MINUTE, minute);
		cal.add(Calendar.SECOND, second);
		return new Timestamp(cal.getTime().getTime());
	}

	/**
	 * あるTimestamp値から指定年月日後のTimestamp値を取得
	 * 
	 * @param srcTimestamp
	 * @param year
	 * @param month
	 * @param day
	 * @return Timestamp値
	 */
	public static Timestamp timestampPlusDate(Timestamp srcTimestamp, int year, int month, int day) {
		return timestampPlusDateTime(srcTimestamp, year, month, day, 0, 0, 0);
	}

	/**
	 * あるTimestamp値から指定時間後のTimestamp値を取得
	 * 
	 * @param srcTimestamp
	 * @param hour
	 * @param minute
	 * @param second
	 * @return Timestamp値
	 */
	public static Timestamp timestampPlusTime(Timestamp srcTimestamp, int hour, int minute, int second) {
		return timestampPlusDateTime(srcTimestamp, 0, 0, 0, hour, minute, second);
	}

	/**
	 * String型のフラグ項目のON/OFFを判定
	 * 
	 * @param flg
	 * @return 判定結果
	 */
	public static boolean isFlgOn(String flg) {
		return StringUtils.equals(flg, CommonConst.FLG_ON.toString());
	}

	/**
	 * IntegerフラグをBoolean値に変換
	 * 
	 * @param flg
	 * @return 変換結果
	 */
	public static boolean isFlgOn(Integer flg) {
		return CommonConst.FLG_ON.equals(flg);
	}

	/**
	 * Boolean値をIntegerフラグに変換する.
	 * 
	 * @param flg
	 * @return 変換結果
	 */
	public static Integer valueFlg(boolean flg) {
		return flg ? CommonConst.FLG_ON : CommonConst.FLG_OFF;
	}

	/**
	 * 拡張null判定。null、空文字に加えて文字列"null"もtrueと判定する.
	 * 
	 * @param val
	 *            判定文字列
	 * @return 判定結果
	 */
	public static boolean isEmpty(String val) {
		if (StringUtils.isEmpty(val) || StringUtils.equals(val, "null")) {
			return true;
		}
		return false;
	}

	/**
	 * 比較対象文字列が同一かどうかを判定する。 Nullと空文字、空文字とNull、NullとNull、空文字と空文字は同一とみなす.
	 * 
	 * @param equalsFrom
	 *            比較文字列
	 * @param equalsTo
	 *            比較文字列
	 * @return 比較結果
	 */
	public static boolean nullSafeEquals(String equalsFrom, String equalsTo) {
		if (isEmpty(equalsFrom) && isEmpty(equalsTo)) {
			return true;
		}
		return StringUtils.equals(equalsFrom, equalsTo);
	}

	/**
	 * nullの場合に、空値を返し、null以外の場合は引数を返す.
	 * 
	 * @param val
	 *            変換対象数値
	 * @return 変換結果
	 */
	public static String nullToEmpty(String val) {
		if (isEmpty(val)) {
			return "";
		}
		return val;
	}

	/**
	 * nullの場合に、0を返し、null以外の場合は引数を返す.
	 * 
	 * @param val
	 *            変換対象数値
	 * @return 変換結果
	 */
	public static String nullToZero(String val) {
		if (isEmpty(val)) {
			return "0";
		}
		return val;
	}

	/**
	 * nullまたは変換不能の場合にnullを返し、それ以外の場合はIntegerを返す
	 * 
	 * @param val
	 *            変換対象数値
	 * @return 変換結果
	 */
	public static Integer nullSafeParseInt(String val) {
		try {
			return isEmpty(val) ? null : Integer.parseInt(val);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * 2地点間の距離を算出.
	 * 
	 * @param startLat
	 *            始点の緯度
	 * @param startLng
	 *            始点の経度
	 * @param endLat
	 *            終点の緯度
	 * @param endLng
	 *            終点の経度
	 * @return 距離（m）
	 */
	public static String computeDistance(BigDecimal startLat, BigDecimal startLng, BigDecimal endLat,
			BigDecimal endLng) {

		Double dStartLat = startLat.doubleValue();
		Double dStartLng = startLng.doubleValue();
		Double dEndLat = endLat.doubleValue();
		Double dEndLng = endLng.doubleValue();

		Double mLat = Math.toRadians((dStartLat + dEndLat) / 2.000000);
		Double dLat = Math.toRadians(dStartLat - dEndLat);
		Double dLng = Math.toRadians(dStartLng - dEndLng);

		Double w = Math.sqrt(1 - WGS84_ECC_POW2 * Math.pow(Math.sin(mLat), 2));

		Double mRad = WGS84_MARC_CON / Math.pow(w, 3);
		Double vRad = CommonConst.WGS84_EQU_RAD / w;

		Double distance = Math.sqrt(Math.pow(dLat * mRad, 2) + Math.pow(dLng * vRad * Math.cos(mLat), 2));

		return String.format("%.0f", distance);
	}

	/**
	 * 場所との重なり判定.
	 * 
	 * @return 位置情報が場所の範囲内にある場合true
	 */
	public static boolean userPlaceEngaged(BigDecimal locationLat, BigDecimal locationLng,
			PlaceMasterEntityWrapper place) {
		// 位置情報の座標と場所の中心座標との距離が半径以内ならば、場所の範囲内
		int distance = Integer
				.parseInt(computeDistance(locationLat, locationLng, place.getLatitude(), place.getLongitude()));
		if (distance <= place.getRadius()) {
			return true;
		}
		return false;
	}

	/**
	 * 新たなユーザ表示色セットを取得する.
	 */
	public static UserColorSet getNewUserColorSet(int userSeq) {
		if (USER_COLOR_LIST == null) {
			int userColors = Integer.parseInt(nullToZero(getProperty("userColors")));
			USER_COLOR_LIST = new UserColorSet[userColors];
			for (int i = 1; i <= userColors; i++) {
				String[] userColor = StringUtils.split(getProperty("userColor" + i), ",");
				USER_COLOR_LIST[i - 1] = new UserColorSet(userColor[0], userColor[1], i);
			}
		}
		int mod = Math.floorMod(userSeq - 1, USER_COLOR_LIST.length);
		return USER_COLOR_LIST[mod];
	}

	/**
	 * マーカー表示色のint値を取得する
	 * 
	 * @param rgb
	 * @return hsbvals[0]
	 */
	public static int getMarkerColorFromRGB(String rgb) {
		float[] hsbvals = { 0, 0, 0 };
		int red = (int) Long.parseLong(StringUtils.substring(rgb, 0, 2), 16);
		int green = (int) Long.parseLong(StringUtils.substring(rgb, 2, 4), 16);
		int blue = (int) Long.parseLong(StringUtils.substring(rgb, 4, 6), 16);
		Color.RGBtoHSB(red, green, blue, hsbvals);
		return (int) hsbvals[0];
	}

	/**
	 * ユーザのデフォルトアイコンのリストを取得する.
	 * 
	 * @return アイコンリスト
	 */
	public static List<IconDataSet> getUserDefaultIconList() {
		return getIconList(CommonConst.SYSTEM_MODULE_PATH + CommonConst.USER_ICON_DIR);
	}

	/**
	 * 場所アイコンのリストを取得する.
	 * 
	 * @return アイコンリスト
	 */
	public static List<IconDataSet> getPlaceIconList() {
		return getIconList(CommonConst.SYSTEM_MODULE_PATH + CommonConst.PLACE_ICON_DIR);
	}

	/**
	 * マーカーアイコンのリストを取得する.
	 * 
	 * @return アイコンリスト
	 */
	public static List<IconDataSet> getMarkerIconList() {
		return getIconList(CommonConst.SYSTEM_MODULE_PATH + CommonConst.MARKER_ICON_DIR);
	}

	/**
	 * 指定されたアイコン格納ディレクトリからアイコンのリストを取得する.
	 * 
	 * @param iconDir
	 *            ディレクトリパス
	 * @return アイコンリスト
	 */
	private static List<IconDataSet> getIconList(String iconDir) {

		List<IconDataSet> dataList = new ArrayList<>();

		File baseDir = new File(iconDir);
		File[] listOfFiles = baseDir.listFiles();

		if (listOfFiles != null) {
			for (File item : listOfFiles) {
				if (item.isFile()) {
					try {
						IconDataSet data = new IconDataSet();
						data.setIconName(StringUtils.substringBeforeLast(item.getName(), "."));
						InputStream istream = new FileInputStream(item.getPath());
						ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();
						IOUtils.copy(istream, outPutStream);
						data.setIconData(KdcCommonUtils.scaleImage(outPutStream.toByteArray(), CommonConst.ICON_WIDTH,
								CommonConst.ICON_HEIGHT, "png"));
						istream.close();
						dataList.add(data);
					} catch (IOException e) {
						e.getStackTrace();
					}
				}
			}
		}

		return dataList;
	}

	/**
	 * 画像のバイトストリームデータをアイコン表示用サイズに整形したうえで、Base64文字列に変換する
	 * 
	 * @param in
	 *            画像バイトストリーム
	 * @return Base64変換後の画像バイトデータ
	 */
	public static String getIconStringBase64(byte[] in) {
		if (in.length == 0) {
			return "";
		}
		return Base64.getEncoder()
				.encodeToString(scaleImage(in, CommonConst.ICON_WIDTH, CommonConst.ICON_HEIGHT, "png"));
	}

	/**
	 * 画像のバイトストリームをリサイズする
	 * 
	 * @param in
	 *            画像バイトデータ
	 * @param width
	 *            画像バイトデータ
	 * @param height
	 *            画像バイトデータ
	 * @param formatName
	 *            画像バイトデータ
	 * @return 画像バイトストリーム
	 */
	public static byte[] scaleImage(byte[] in, int width, int height, String formatName) {
		try {
			BufferedImage org = ImageIO.read(new ByteArrayInputStream(in));
			ImageFilter filter = new AreaAveragingScaleFilter(width, height);
			ImageProducer p = new FilteredImageSource(org.getSource(), filter);
			java.awt.Image dstImage = Toolkit.getDefaultToolkit().createImage(p);
			BufferedImage dst = new BufferedImage(dstImage.getWidth(null), dstImage.getHeight(null),
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = dst.createGraphics();

			// 背景を透過色に設定してイメージを作成する
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
			Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, dst.getWidth(), dst.getHeight());
			g.fill(rect);
			g.setPaintMode();

			g.drawImage(dstImage, 0, 0, null);
			g.dispose();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(dst, formatName, baos);
			return baos.toByteArray();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * ユーザのアイコン画像を、マップ上のマーカーとして表示するアイコン画像に加工する.
	 * 
	 * @param userIconFile
	 *            アイコン画像ファイル
	 * @param markerColorId
	 *            マーカー表示色
	 * @return base64でエンコードされたバイト配列
	 * @throws IOException
	 */
	public static String createUserMarkerIconString(byte[] userIconFile, byte[] markerBaseFile) throws IOException {
		// ユーザアイコン画像
		BufferedImage userIconImg = ImageIO.read(new ByteArrayInputStream(userIconFile));
		// ユーザアイコンを表示するマーカーアイコン画像
		BufferedImage markerImage = ImageIO.read(new ByteArrayInputStream(markerBaseFile));

		// ユーザアイコン画像を円型に切り取る
		BufferedImage userChipImg = new BufferedImage(userIconImg.getWidth(), userIconImg.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D gr = userChipImg.createGraphics();
		gr.setColor(new Color(0, 1F, 0, 1F));
		RoundRectangle2D rr = new RoundRectangle2D.Double(7.0, 7.0, userIconImg.getWidth() - 15.5,
				userIconImg.getHeight() - 15.5, 40, 40);
		gr.fill(rr);
		BasicStroke wideStroke = new BasicStroke(4.0f);
		gr.setStroke(wideStroke);
		gr.setComposite(AlphaComposite.SrcIn);
		gr.drawImage(userIconImg, 0, 0, null);
		gr.dispose();

		// 切り取ったユーザアイコンをマーカーアイコンに重ねる
		Graphics graphics1 = null;
		graphics1 = markerImage.getGraphics();
		int x = 0;
		int y = -3;
		graphics1.drawImage(userChipImg, x, y, null);

		// 合成後の画像を保持
		ByteArrayOutputStream byteImg = new ByteArrayOutputStream();
		ImageIO.write(markerImage, "png", byteImg);

		return Base64.getEncoder().encodeToString(byteImg.toByteArray());
	}

	/**
	 * API開始ログ出力
	 * 
	 * @param api
	 *            API識別ID
	 * @param userInfo
	 *            ユーザ情報クラス
	 * @param deviceInfo
	 *            端末情報クラス
	 * @return ログ文字列
	 */
	public static String getApiStartLog(ApiIdEnum api, UserInfoEntity userInfo, DeviceInfoEntity deviceInfo) {
		StringBuilder logString = new StringBuilder();
		logString.append("API Called:");
		logString.append(api.toString());
		logString.append(" UserId:");
		logString.append(userInfo.getUserId());
		logString.append(" TelephoneNumber:");
		logString.append(deviceInfo.getTelephoneNumber());
		return logString.toString();
	}

	/**
	 * API終了ログ出力
	 * 
	 * @param api
	 *            API識別ID
	 * @param userInfo
	 *            ユーザ情報クラス
	 * @param deviceInfo
	 *            端末情報クラス
	 * @param status
	 *            Httpレスポンスステータスコード
	 * @param resultCd
	 *            処理結果コード
	 * @return ログ文字列
	 */
	public static String getApiEndLog(ApiIdEnum api, UserInfoEntity userInfo, DeviceInfoEntity deviceInfo,
			Integer status, Integer resultCd) {
		StringBuilder logString = new StringBuilder();
		logString.append("API Complete:");
		logString.append(api.toString());
		logString.append(" UserId:");
		logString.append(userInfo.getUserId());
		logString.append(" TelephoneNumber:");
		logString.append(deviceInfo.getTelephoneNumber());
		logString.append(" Status:");
		logString.append(status);
		logString.append(" resultCd:");
		logString.append(resultCd);
		return logString.toString();
	}

	/**
	 * APIエラーログ出力
	 * 
	 * @param api
	 *            API識別ID
	 * @param userInfo
	 *            ユーザ情報クラス
	 * @param deviceInfo
	 *            端末情報クラス
	 * @param error
	 *            エラーコード
	 * @return ログ文字列
	 */
	public static String getApiErrorLog(ApiIdEnum api, UserInfoEntity userInfo, DeviceInfoEntity deviceInfo,
			String error) {
		StringBuilder logString = new StringBuilder();
		logString.append("API Failed:");
		logString.append(api.toString());
		logString.append(" UserId:");
		logString.append(userInfo.getUserId());
		logString.append(" TelephoneNumber:");
		logString.append(deviceInfo.getTelephoneNumber());
		logString.append(" Error:");
		logString.append(error);
		return logString.toString();
	}
}