package com.util;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.Writer;

public class ResponseUtil {
	public static void outHtml(String string, HttpServletResponse response) {
		responseHtml(string, response, "UTF-8");
	}

	public static void responseHtml(String string,
			HttpServletResponse response, String coding) {
		try {
			if (coding == null || coding.length() == 0)
				coding = "UTF-8";
			response.setContentType("text/html");
			response.setCharacterEncoding(coding);
			PrintWriter out = response.getWriter();
			out.write(string);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Writer outXml(String string, HttpServletResponse response)
			throws Exception {
		return responseXml(string, response, "UTF-8");
	}

	public static Writer responseXml(String string,
			HttpServletResponse response, String coding) throws Exception {
		if (coding == null || coding.length() == 0)
			coding = "UTF-8";
		response.setContentType("text/xml");
		response.setCharacterEncoding(coding);
		PrintWriter out = response.getWriter();
		out.write(string);
		out.flush();
		return out;
	}

	public static void outJsonCache(String jsonString,
			HttpServletResponse response) throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(jsonString);
		out.close();
	}

	public static void outJson(String jsonString, HttpServletResponse response)
			throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		out.print(jsonString);
		out.close();
	}
}
