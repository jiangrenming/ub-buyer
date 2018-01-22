//package com.hykj.hytools.Json.Filter.impl;
//
//import com.hykj.hytools.Json.Filter.HYFilterJson;
//
//public class HYFilterJsonImpl implements HYFilterJson {
//
//	@Override
//	public String formatJsonBom(String s) {
//		if (s != null) {
//			s = s.replaceAll("\ufeff", "");
//		}
//		return s;
//	}
//
//	@Override
//	public String formatJsonParams(String s) {
//		if (s != null) {
//			if (s.contains("../../")) {
//				s.replaceAll("../../", "");
//			}
//			if (s.contains("${@")) {
//				s.replace("${@", "");
//			}
//			if (s.contains("<script>")) {
//				s.replaceAll("<script>", "");
//			}
//			if (s.contains("</script>")) {
//				s.replaceAll("</script>", "");
//			}
//			if (s.contains("print(md5")) {
//				s.replaceAll("print(md5", "");
//			}
//			if (s.contains("/etc/password")) {
//				s.replaceAll("/etc/password", "");
//			}
//			if (s.contains("'")) {
//				s.replaceAll("'", "");
//			}
//			if (s.contains("sleep(")) {
//				s.replaceAll("sleep(", "");
//			}
//			if (s.contains("select")) {
//				s.replaceAll("select ", "");
//			}
//			if (s.contains("update ")) {
//				s.replaceAll("select ", "");
//			}
//			if (s.contains("delete ")) {
//				s.replaceAll("delete ", "");
//			}
//		}
//		return s;
//	}
//
//}
