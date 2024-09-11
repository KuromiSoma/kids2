package com.kdc.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * ユーザ表示色クラス.
 */
public class UserColorSet {

	private String colorName;
	private String lineColor;
	private int markerColorId;

	// private Color colorObj;

	public UserColorSet(String colorName, String lineColor, int markerColorId) {
		this.colorName = colorName;
		this.lineColor = lineColor;
		this.markerColorId = markerColorId;
		// this.colorObj = new
		// Color(Integer.parseInt(StringUtils.substring(lineColor, 2, 8), 16));
	}

	/**
	 * @return colorName
	 */
	public String getColorName() {
		return colorName;
	}

	/**
	 * @param colorName
	 *            セットする colorName
	 */
	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	/**
	 * @return lineColor
	 */
	public String getLineColor() {
		return lineColor;
	}

	/**
	 * @param lineColor
	 *            セットする lineColor
	 */
	public void setLineColor(String lineColor) {
		this.lineColor = lineColor;
	}

	/**
	 * @return markerColorId
	 */
	public int getMarkerColorId() {
		return markerColorId;
	}

	/**
	 * @param markerColorId
	 *            セットする markerColorId
	 */
	public void setMarkerColorId(int markerColorId) {
		this.markerColorId = markerColorId;
	}

	/**
	 * @return ラインカラーのRGB領域
	 */
	public String getLineColorRGBHex() {
		return StringUtils.substring(this.lineColor, 2, 8);
	}

	/**
	 * @return ラインカラーのアルファ（透過）領域
	 */
	public String getLineColorAlphaHex() {
		return StringUtils.substring(this.lineColor, 0, 2);
	}

	/**
	 * @return ラインカラーの赤領域
	 */
	public String getLineColorRedHex() {
		return StringUtils.substring(this.lineColor, 2, 4);
	}

	/**
	 * @return ラインカラーの緑領域
	 */
	public String getLineColorGreenHex() {
		return StringUtils.substring(this.lineColor, 4, 6);
	}

	/**
	 * @return ラインカラーの青領域
	 */
	public String getLineColorBlueHex() {
		return StringUtils.substring(this.lineColor, 6, 8);
	}

	/**
	 * @return ラインカラーの透過領域
	 */
	public double getLineColorAlpha() {
		return ((int) Long.parseLong(this.getLineColorAlphaHex(), 16)) / (double) 255.0;
	}

	/**
	 * @return ラインカラー赤の16進数をLong型に変換
	 */
	public int getLineColorRed() {
		return (int) Long.parseLong(this.getLineColorRedHex(), 16);
	}

	/**
	 * @return ラインカラー緑の16進数をLong型に変換
	 */
	public int getLineColorGreen() {
		return (int) Long.parseLong(this.getLineColorGreenHex(), 16);
	}

	/**
	 * @return ラインカラー青の16進数をLong型に変換
	 */
	public int getLineColorBlue() {
		return (int) Long.parseLong(this.getLineColorBlueHex(), 16);
	}

	// /**
	// * @return 赤のRGB値
	// */
	// public int getMarkerColorRed() {
	// return this.colorObj.getRed();
	// }
	//
	// /**
	// * @return 緑のRGB値
	// */
	// public int getMarkerColorGreen() {
	// return this.colorObj.getGreen();
	// }
	//
	// /**
	// * @return 青のRGB値
	// */
	// public int getMarkerColorBlue() {
	// return this.colorObj.getBlue();
	// }
	//
	// /**
	// * @return マーカーカラーRGB16進数
	// */
	// public String getMarkerColorRGBHex() {
	// return new
	// StringBuilder().append(this.getMarkerColorRedHex()).append(this.getMarkerColorGreenHex())
	// .append(this.getMarkerColorBlueHex()).toString();
	// }
	//
	// /**
	// * @return 16進数2桁の文字列に変換した赤のRGB値
	// */
	// public String getMarkerColorRedHex() {
	// return String.format("%02x", this.getMarkerColorRed());
	// }
	//
	// /**
	// * @return 16進数2桁の文字列に変換した緑のRGB値
	// */
	// public String getMarkerColorGreenHex() {
	// return String.format("%02x", this.getMarkerColorGreen());
	// }
	//
	// /**
	// * @return 16進数2桁の文字列に変換した青のRGB値
	// */
	// public String getMarkerColorBlueHex() {
	// return String.format("%02x", this.getMarkerColorBlue());
	// }

}