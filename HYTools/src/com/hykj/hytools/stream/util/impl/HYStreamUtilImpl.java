package com.hykj.hytools.stream.util.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.hykj.hytools.stream.util.HYStreamUtil;

public class HYStreamUtilImpl implements HYStreamUtil {

	@Override
	public void closeStream(InputStream in, OutputStream out) {
		try {
			if (null != in) {
				in.close();
			}
			if (null != out) {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
