package com.hykj.selectarealib.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.hykj.selectarealib.bean.City;

import android.content.Context;
import android.util.Xml;

public class GetCity {

	private List<City> dataList = new ArrayList<City>();
	private City city;;

	public List<City> getCity(Context context) {

		// ȡ��xml�ĵ�
		InputStream is;
		try {
			is = context.getAssets().open("city.xml");
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
						city = new City();
					} else if (parser.getName().equals("cityid")) {
						eventType = parser.next();
						city.setCityid((Integer.parseInt(parser.getText())));
					} else if (parser.getName().equals("cityname")) {
						eventType = parser.next();
						city.setCityname(parser.getText());
					} else if (parser.getName().equals("parentid")) {
						eventType = parser.next();
						city.setParentid((Integer.parseInt(parser.getText())));
					}
					break;
				case XmlPullParser.END_TAG:
					if (parser.getName().equals("item")) {
						dataList.add(city);
						city = null;
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
