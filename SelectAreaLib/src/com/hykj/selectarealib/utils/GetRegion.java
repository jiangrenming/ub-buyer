package com.hykj.selectarealib.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.hykj.selectarealib.bean.Region;

import android.content.Context;
import android.util.Xml;

public class GetRegion {

	private List<Region> dataList = new ArrayList<Region>();
	private Region region;;

	public List<Region> getRegion(Context context) {

		// ȡ��xml�ĵ�
		InputStream is;
		try {
			is = context.getAssets().open("region.xml");
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
						region = new Region();
					} else if (parser.getName().equals("regionid")) {
						eventType = parser.next();
						region.setRegionid((Integer.parseInt(parser.getText())));
					} else if (parser.getName().equals("regionname")) {
						eventType = parser.next();
						region.setRegionname(parser.getText());
					} else if (parser.getName().equals("parentid")) {
						eventType = parser.next();
						region.setParentid((Integer.parseInt(parser.getText())));
					}
					break;
				case XmlPullParser.END_TAG:
					if (parser.getName().equals("item")) {
						dataList.add(region);
						region = null;
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
