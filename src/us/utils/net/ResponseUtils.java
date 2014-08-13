package us.utils.net;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

public class ResponseUtils {

	private static final String HEADER_ENCODING = "encoding";
	private static final String HEADER_NOCACHE = "no-cache";
	private static final String DEFAULT_ENCODING = "UTF-8";
	private static final boolean DEFAULT_NOCACHE = true;
	private static ObjectMapper mapper = new ObjectMapper();

	public static void render(HttpServletResponse httpResponse, String contentType, String content, String[] headers) {
		HttpServletResponse response = initResponseHeader(httpResponse, contentType, headers);
		try {
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static <T> void renderXML(HttpServletResponse httpResponse, T object, Class<T> cls, String[] headers) {
		HttpServletResponse response = initResponseHeader(httpResponse, "text/xml", headers);
		try {
			JAXBContext context = JAXBContext.newInstance(new Class[] { cls });
			Marshaller m = context.createMarshaller();
			m.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
			m.marshal(object, response.getWriter());
			response.getWriter().flush();
		} catch (JAXBException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		JAXBContext context;
	}

	public static void renderText(HttpServletResponse httpResponse, String text, String[] headers) {
		render(httpResponse, "text/plain", text, headers);
	}

	public static void renderHtml(HttpServletResponse httpResponse, String html, String[] headers) {
		render(httpResponse, "text/html", html, headers);
	}

	public static void renderXml(HttpServletResponse httpResponse, String xml, String[] headers) {
		render(httpResponse, "text/xml", xml, headers);
	}

	public static void renderJson(HttpServletResponse httpResponse, String jsonString, String[] headers) {
		render(httpResponse, "application/json", jsonString, headers);
	}

	public static void renderJson(HttpServletResponse httpResponse, Object data, String[] headers) {
		HttpServletResponse response = initResponseHeader(httpResponse, "application/json", headers);
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static void renderJsonp(HttpServletResponse httpResponse, String callbackName, Object object, String[] headers) {
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(object);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}

		String result = callbackName + "(" + jsonString + ");";

		render(httpResponse, "text/javascript", result, headers);
	}

	public static void renderJavascript(HttpServletResponse httpResponse, String jsValue, String[] heads) {
		render(httpResponse, "text/javascript", jsValue, heads);
	}

	private static HttpServletResponse initResponseHeader(HttpServletResponse response, String contentType, String[] headers) {
		String encoding = "UTF-8";
		boolean noCache = true;
		if (null != headers) {
			for (String header : headers) {
				String headerName = StringUtils.substringBefore(header, ":");
				String headerValue = StringUtils.substringAfter(header, ":");

				if (StringUtils.equalsIgnoreCase(headerName, "encoding"))
					encoding = headerValue;
				else if (StringUtils.equalsIgnoreCase(headerName, "no-cache"))
					noCache = Boolean.parseBoolean(headerValue);
				else {
					throw new IllegalArgumentException(headerName + "不是一个合法的header类型");
				}
			}
		}
		// HttpServletResponse response = ServletActionContext.getResponse();

		String fullContentType = contentType + ";charset=" + encoding;
		response.setContentType(fullContentType);
		if (noCache) {
			setNoCacheHeader(response);
		}
		return response;
	}

	public static void setNoCacheHeader(HttpServletResponse response) {
		response.setDateHeader("Expires", 0L);
		response.addHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
	}

}