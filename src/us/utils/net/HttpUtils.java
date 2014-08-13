package us.utils.net;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class HttpUtils {
	/**
	 * 添加Cookie
	 * 
	 * @param response
	 * @param key
	 * @param value
	 * @param domain
	 * @param path
	 * @param age
	 */
	public static void addCookie(HttpServletResponse response, String key, String value, String domain, String path, int age) {
		Cookie cookie = new Cookie(key, value);
		cookie.setDomain(domain);
		cookie.setPath(path);
		cookie.setMaxAge(age);
		response.addCookie(cookie);
	}

	/**
	 * 从request中获取Cookie
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static Cookie getCookie(HttpServletRequest request, String key) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null || cookies.length == 0)
			return null;

		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals(key))
				return cookies[i];
		}
		return null;
	}

	/**
	 * 生成当前URL
	 * 
	 * @param request
	 * @return
	 */
	public static String buildCurrentURL(HttpServletRequest request) {

		StringBuffer url = new StringBuffer("");
		if (request.getScheme().startsWith("http"))
			url.append("http://");
		else
			url.append("https://");
		url.append(request.getHeader("host"));

		if (request.getServerPort() != 80)
			url.append(":" + request.getServerPort());
		url.append(request.getRequestURI());
		if (StringUtils.isNotEmpty(request.getQueryString())) {
			url.append("?");
			url.append(request.getQueryString());
		}
		return url.toString();
	}
}
