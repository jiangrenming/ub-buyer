package com.hykj.selectarealib.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.hykj.selectarealib.bean.Province;

import android.content.Context;
import android.util.Xml;

public class GetProvince {

	private List<Province> dataList = new ArrayList<Province>();
	private Province province;;

	public List<Province> getProvinces(Context context) {

		// ȡ��xml�ĵ�
		InputStream is;
		try {
			is = context.getAssets().open("province.xml");
			XmlPullParser parser = Xml.newPullParser(); // ��android.util.Xml����һ��XmlPullParserʵ��
			parser.setInput(is, "UTF-8");
			int eventType;
			eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					if (parser.getName().equals("item")) {
						province = new Province();
					} else if (parser.getName().equals("provinceid")) {
						eventType = parser.next();
						province.setProvinceid((Integer.parseInt(parser
								.getText())));
					} else if (parser.getName().equals("provincename")) {
						eventType = parser.next();
						province.setProvincename(parser.getText());
					}
					break;
				case XmlPullParser.END_TAG:
					if (parser.getName().equals("item")) {
						dataList.add(province);
						province = null;
					}
					break;
				}
				eventType = parser.next();
			}
			return dataList;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		return dataList;
	}
}
