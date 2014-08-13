package us.utils.string;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

import org.apache.commons.lang3.StringUtils;

/**
 * 该类扩展了StringUtils, 请使用以"X"结尾的方法{该方法执行效率高于StringUtils相关方法}
 * 
 */

public class StringUtil extends StringUtils {

	/**
	 * 覆盖StringUtils.isEmpty()
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmptyX(String s) {
		return (s == null || s.length() == 0) ? true : false;
	}

	/**
	 * 覆盖StringUtils.isNotEmpty
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNotEmptyX(String s) {
		return !isEmpty(s);
	}

	/**
	 * String2Short
	 * 
	 * @param s
	 * @param def
	 * @return
	 */
	public static short toShort(String s, short def) {
		try {
			return (isEmptyX(s)) ? def : Short.parseShort(s);
		} catch (NumberFormatException e) {
			return def;
		}
	}

	/**
	 * String2Int
	 * 
	 * @param s
	 * @param def
	 * @return
	 */
	public static int toInt(String s, int def) {
		try {
			return (isEmptyX(s)) ? def : Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return def;
		}
	}

	/**
	 * String2Long
	 * 
	 * @param s
	 * @param def
	 * @return
	 */
	public static long toLong(String s, long def) {
		try {
			return (isEmptyX(s)) ? def : Long.parseLong(s);
		} catch (NumberFormatException e) {
			return def;
		}
	}

	/**
	 * String2Float
	 * 
	 * @param s
	 * @param def
	 * @return
	 */
	public static float toFloat(String s, float def) {
		try {
			return (isEmptyX(s)) ? def : Float.parseFloat(s);
		} catch (NumberFormatException e) {
			return def;
		}
	}

	/**
	 * String2Double
	 * 
	 * @param s
	 * @param def
	 * @return
	 */
	public static double toDouble(String s, double def) {
		try {
			return (isEmptyX(s)) ? def : Double.parseDouble(s);
		} catch (NumberFormatException e) {
			return def;
		}
	}

	/**
	 * String2Boolean
	 * 
	 * @param s
	 * @param def
	 * @return
	 */
	public static boolean toBoolean(String s, boolean def) {
		if (isEmptyX(s))
			return def;
		else {
			return "true".equalsIgnoreCase(s);
		}
	}

	/**
	 * 利用Crc32做数据校验
	 * 
	 * @param s
	 * @return
	 */
	public static long getCrc32Value(String s) {
		CRC32 crc32 = new CRC32();
		crc32.update(s.getBytes());
		return crc32.getValue();

	}

	/**
	 * 获得IP
	 * 
	 * @param ipAddress
	 * @return
	 */
	public static long ip2Long(String ipAddress) {
		String[] str = StringUtils.split(ipAddress, ".");
		if (str.length != 4)
			throw new RuntimeException("ipAddress is invalid");

		long ip = 0;
		int len = str.length;
		for (int i = 0; i < str.length; i++) {
			ip = ip + StringUtil.toLong(str[i], 0) * (long) Math.pow(256, (len - i - 1));
		}
		return ip;
	}

	/**
	 * 
	 * @param ipAddress
	 * @return
	 */
	public static String ip2String(long ipAddress) {
		StringBuffer ips = new StringBuffer("");
		long field = 0;
		for (int i = 3; i >= 0; i--) {
			field = ipAddress / ((long) Math.pow(256, i));
			ipAddress = ipAddress % ((long) Math.pow(256, i));
			ips.append(field);
			if (i != 0)
				ips.append(".");
		}
		return ips.toString();
	}

	public static final int RED = 1;
	public static final int BLACK = 2;
	public static final int MIDPAGESIZE = 5;
	public static final int PAGESIZE = 15;
	public static final int INDEXPAGESIZE = 15;

	public static String[] NUMBER = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "百", "千", "万", "亿" };
	public static final String CREATE = "create";
	public static final String DELETE = "delete";
	public static final String SAVE = "save";
	public static final String UPDATE = "update";
	public static final String QUERY = "query";
	public static final String ERROR = "error";
	public static final String SUCCESS = "success";
	public static final String FAILED = "failed";
	public static final String IP = "strIp";
	public static final String ANSWER = "strAnswer";
	public static final String LOGIN = "login";
	public static final String INDEX = "index";
	public static final String HOME = "home";
	public static final String NORIGHT = "noRight";
	public static final String BOSSIP = "strBOSSIp";
	public static final String BOSSIPS = "BOSSIPS";// 配置文件参数
	public static final String BOSSANSWER = "BOSSANSWER";// 配置文件参数
	private static final String RANDOM_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // 设置备选验证码:包括"a-z"和数字"0-9"

	// ----------- 字符串截取 ----------- //
	/**
	 * 截取 float/double 类型 一位小数
	 * 
	 * @param f
	 * @return
	 */
	public static String getDelFormat(Object f) {
		DecimalFormat df = new DecimalFormat("0.0");
		return df.format(f);
	}

	/**
	 * 截取 float/double 类型 两位小数
	 * 
	 * @param f
	 * @return
	 */
	public static String getDelFormat2(Object f) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(f);
	}

	/**
	 * 截取三位
	 * 
	 * @param f
	 * @return
	 */
	public static String getDelFormat3(Object f) {
		DecimalFormat df = new DecimalFormat("0.000");
		return df.format(f);
	}

	/**
	 * 截取字符串 先是一定的长多 多余...
	 * 
	 * @param source
	 *            元字符串
	 * @param len
	 *            显示长多
	 * @return
	 */
	public static String getStringsubstr(String source, int len) {
		if (null == source || "".equals(source)) {
			return "";
		}
		if (source.length() > len) {
			return source.substring(0, len) + "...";
		}

		return source;
	}

	// ----------- 随机数 ----------- //
	/**
	 * 获得0-max的随机数
	 * 
	 * @param length
	 * @return String
	 */
	public static String getRandomNumber(int length, int max) {
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < length; i++) {
			buffer.append(random.nextInt(max));
		}
		return buffer.toString();
	}

	/**
	 * 获取指定长度的随机数字组成的字符串
	 * 
	 * @param size
	 * @return
	 */
	public static String getRandomNumber(int size) {
		String num = "";
		for (int i = 0; i < size; i++) {
			double a = Math.random() * 9;
			a = Math.ceil(a);
			int randomNum = new Double(a).intValue();
			num += randomNum;
		}
		return num;
	}

	/**
	 * 获取随机字符
	 * 
	 * @param size
	 * @return
	 */
	public static String getRandomChar(int size) {
		String sRand = "";
		Random random = new Random();// 创建一个随机类
		for (int i = 0; i < size; i++) {
			String ch = String.valueOf(RANDOM_CHAR.charAt(random.nextInt(RANDOM_CHAR.length())));
			sRand += ch;
		}
		return sRand;
	}

	// ----------- 验证 ----------- //
	/**
	 * 判断字符串是否为数字和有正确的值
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		// Pattern pattern=Pattern.compile("[0-9]*");
		// return pattern.matcher(str).matches();
		if (null != str && 0 != str.trim().length() && str.matches("\\d*")) {
			return true;
		}

		return false;
	}

	/**
	 * 将阿拉伯数字转为中文数字
	 * 
	 * @return
	 */
	public static String toChineseNumber(int number, int depth) {
		if (depth < 0)
			depth = 0;
		if (number <= 0 && depth == 0)
			return NUMBER[0];

		String chinese = "";
		String src = number + "";
		if (src.charAt(src.length() - 1) == 'l' || src.charAt(src.length() - 1) == 'L') {
			src = src.substring(0, src.length() - 1);
		}

		if (src.length() > 4)
			chinese = toChineseNumber(Integer.parseInt(src.substring(0, src.length() - 4)), depth + 1)
					+ toChineseNumber(Integer.parseInt(src.substring(src.length() - 4, src.length())), depth - 1);
		else {
			char prv = 0;
			for (int i = 0; i < src.length(); i++) {
				switch (src.charAt(i)) {
				case '0':
					if (prv != '0')
						chinese = chinese + NUMBER[0];
					break;
				case '1':
					chinese = chinese + NUMBER[1];
					break;
				case '2':
					chinese = chinese + NUMBER[2];
					break;
				case '3':
					chinese = chinese + NUMBER[3];
					break;
				case '4':
					chinese = chinese + NUMBER[4];
					break;
				case '5':
					chinese = chinese + NUMBER[5];
					break;
				case '6':
					chinese = chinese + NUMBER[6];
					break;
				case '7':
					chinese = chinese + NUMBER[7];
					break;
				case '8':
					chinese = chinese + NUMBER[8];
					break;
				case '9':
					chinese = chinese + NUMBER[9];
					break;
				}
				prv = src.charAt(i);

				switch (src.length() - 1 - i) {
				case 1:// 十
					if (prv != '0')
						chinese = chinese + NUMBER[10];
					break;
				case 2:// 百
					if (prv != '0')
						chinese = chinese + NUMBER[11];
					break;
				case 3:// 千
					if (prv != '0')
						chinese = chinese + NUMBER[12];
					break;
				}
			}
		}
		while (chinese.length() > 0 && chinese.lastIndexOf(NUMBER[0]) == chinese.length() - 1)
			chinese = chinese.substring(0, chinese.length() - 1);
		if (depth == 1)
			chinese += NUMBER[13];
		if (depth == 2)
			chinese += NUMBER[14];
		return chinese;
	}

	/**
	 * 验证字符串是否含有特殊字符和中文
	 * 
	 * @param
	 * 
	 * @return
	 */
	public static boolean checkIsEnglish(String s) {
		String Letters = "(){}[]\",.<>\\/~!@#$%^&*;': ";
		int i;
		int c;

		if (s.charAt(0) == '-') {
			return false;
		}
		if (s.charAt(s.length() - 1) == '-') {
			return false;
		}

		for (i = 0; i < s.length(); i++) {
			c = s.charAt(i);
			if (Letters.indexOf(c) > -1) {
				return false;
			}
		}

		// 验证是否刚好匹配
		boolean yesorno = isChineseStr(s);
		if (yesorno) {
			return false;
		}
		return true;
	}

	public static boolean isChineseStr(String pValue) {
		for (int i = 0; i < pValue.length(); i++) {
			if ((int) pValue.charAt(i) > 256)
				return true;
		}
		return false;
	}

	/**
	 * 判断一个字符是Ascill字符还是其它字符（如汉，日，韩文字符）
	 * 
	 * @param char c, 需要判断的字符
	 * @return boolean, 返回true,Ascill字符
	 */
	public static boolean isLetter(char c) {
		int k = 0x80;
		return c / k == 0 ? true : false;
	}

	// ----------- 格式化 ---------- //
	/**
	 * 格式化数据
	 * 
	 * @param decima
	 *            l3453454
	 * @return 3,453,454
	 */
	public final static String FormatDecimalString(String decimal) {
		double dValue = Double.valueOf(decimal).doubleValue();
		DecimalFormat df = new DecimalFormat();
		String positivePattern = " ,000";
		String negativePattern = " ,000";
		if (dValue < 0) {
			df.applyPattern(negativePattern);
			return df.format(dValue).replace(',', ',');
		} else {
			df.applyPattern(positivePattern);
			return df.format(dValue).replace(',', ',');
		}
	}

	/**
	 * 格式化数据
	 * 
	 * @param source
	 *            3453454
	 * @return 3,453,454
	 */
	public static String getNumberFormat(long source) {
		NumberFormat usFormat = NumberFormat.getIntegerInstance(Locale.US);
		return usFormat.format(source);
	}

	// ----------- 过滤 ---------- //
	/**
	 * 过滤字符串里的的特殊字符
	 * 
	 * @param str
	 *            要过滤的字符串
	 * @return 过滤后的字符串
	 */
	public static String stringFilter(String str) {
		// 过滤通过页面表单提交的字符
		String[][] FilterChars = { { "<", "&lt;" }, { ">", "&gt;" }, { " ", "&nbsp;" }, { "\"", "&quot;" }, { "&", "&amp;" },
				{ "/", "&#47;" }, { "\\", "&#92;" }, { "'", "\\'" }, { "%", "%" } };

		String[] str_arr = stringSpilit(str, "");

		for (int i = 0; i < str_arr.length; i++) {
			for (int j = 0; j < FilterChars.length; j++) {
				if (FilterChars[j][0].equals(str_arr[i]))
					str_arr[i] = FilterChars[j][1];
			}
		}
		return (stringConnect(str_arr, "")).trim();
	}

	// 关健字过滤
	public static String stringKeyWorldFilter(String str) {
		// 过滤通过页面表单提交的字符
		String[][] FilterChars = { { "<", "" }, { ">", "" }, { "\"", "" }, { "&", "" }, { "/", "" }, { "\\", "" }, { "'", "" },
				{ "%", "" } };

		String[] str_arr = stringSpilit(str, "");

		for (int i = 0; i < str_arr.length; i++) {
			for (int j = 0; j < FilterChars.length; j++) {
				if (FilterChars[j][0].equals(str_arr[i]))
					str_arr[i] = FilterChars[j][1];
			}
		}
		return (stringConnect(str_arr, "")).trim();
	}

	// ----------- 切割合并 ---------- //
	/**
	 * 分割字符串
	 * 
	 * @param str
	 *            要分割的字符串
	 * @param spilit_sign
	 *            字符串的分割标志
	 * @return 分割后得到的字符串数组
	 */
	public static String[] stringSpilit(String str, String spilit_sign) {
		String[] spilit_string = str.split(spilit_sign);
		if (spilit_string[0].equals("")) {
			String[] new_string = new String[spilit_string.length - 1];
			for (int i = 1; i < spilit_string.length; i++)
				new_string[i - 1] = spilit_string[i];
			return new_string;
		} else
			return spilit_string;
	}

	/**
	 * 用特殊的字符连接字符串
	 * 
	 * @param strings
	 *            要连接的字符串数组
	 * @param spilit_sign
	 *            连接字符
	 * @return 连接字符串
	 */
	public static String stringConnect(String[] strings, String spilit_sign) {
		StringBuffer str = new StringBuffer("");
		for (int i = 0; i < strings.length; i++) {
			str.append(strings[i]).append(spilit_sign);
		}
		return str.toString();
	}

	/**
	 * 四舍五入 返回int类型
	 * 
	 * @param dSource
	 *            2342.45
	 * @return 2342
	 */
	public static int getRound(double dSource) {
		int iRound;
		// BigDecimal的构造函数参数类型是double
		BigDecimal deSource = new BigDecimal(dSource);
		// deSource.setScale(0,BigDecimal.ROUND_HALF_UP) 返回值类型 BigDecimal
		// intValue() 方法将BigDecimal转化为int
		iRound = deSource.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
		return iRound;
	}

	/**
	 * 提供小数位四舍五入处理。
	 * 
	 * @param s
	 *            需要四舍五入的数字
	 * @return 四舍五入后的结果
	 */
	public static double round(double s) {
		BigDecimal b = new BigDecimal(Double.toString(s));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供小数位四舍五入处理。
	 * 
	 * @param s
	 *            需要四舍五入的数字
	 * @return 四舍五入后的结果
	 */
	public static long roundlong(double s) {
		BigDecimal b = new BigDecimal(Double.toString(s));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, BigDecimal.ROUND_HALF_UP).longValue();
	}

	/**
	 * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
	 * 
	 * @param String
	 *            s ,需要得到长度的字符串
	 * @return int, 得到的字符串长度
	 */
	public static int length(String s) {
		if (s == null)
			return 0;
		char[] c = s.toCharArray();
		int len = 0;
		for (int i = 0; i < c.length; i++) {
			len++;
			if (!isLetter(c[i])) {
				len++;
			}
		}
		return len;
	}

	/**
	 * 功能：获得配置文件中指定编码字符串
	 * 
	 * @param str
	 *            解码字符串 charset 指定编码
	 * 
	 */
	public static String getStrByCharset(String str, String charset) throws UnsupportedEncodingException {
		return new String(str.getBytes("ISO-8859-1"), charset);
	}

	/**
	 * 提取字符串中的中文字符
	 * 
	 * @param str
	 * @return
	 */
	public static String getChineseByStr(String str) {

		StringBuilder resultStr = new StringBuilder("");

		Pattern pcn = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = pcn.matcher(str);
		while (m.find()) {
			resultStr.append(m.group().toString());
		}

		return resultStr.toString();
	}

	// 将两位小数的字符串*100
	public static String parseStrInt(String strDouble) {
		String fen = "";
		int dotIndex = strDouble.lastIndexOf(".");
		if (dotIndex >= 0) {// 有小数点字符串
			String zs = strDouble.substring(0, dotIndex);
			if (!isNumber(zs)) {
				return null;
			}
			fen = zs;
			if (strDouble.substring(dotIndex).length() > 1) {// 有小数部分
				String xs = strDouble.substring(dotIndex).substring(1);
				if (!isNumber(xs)) {
					return null;
				}
				if (xs.length() >= 3) {// 截取后面部分
					xs = xs.substring(0, 2);
				} else {
					if (xs.length() < 2) {
						xs = xs + "0";
					}
				}
				fen = zs + xs;
			} else {// 没有小数
				return null;
			}

		} else {// 无小数点字符串
			fen = strDouble + "00";
		}
		return fen;
	}

	/**
	 * 验证字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		boolean isEmpty = false;
		if (str == null || str.equals("")) {
			isEmpty = true;
		}
		return isEmpty;
	}

	/**
	 * 验证字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 检查浮点数
	 * 
	 * @param num
	 * @param type
	 *            "0+":非负浮点数 "+":正浮点数 "-0":非正浮点数 "-":负浮点数 "":浮点数
	 * @return
	 */
	public static boolean checkFloat(String num, String type) {
		String eL = "";
		if (type.equals("0+"))
			eL = "^\\d+(\\.\\d+)?$";// 非负浮点数
		else if (type.equals("+"))
			eL = "^((\\d+\\.\\d*[1-9]\\d*)|(\\d*[1-9]\\d*\\.\\d+)|(\\d*[1-9]\\d*))$";// 正浮点数
		else if (type.equals("-0"))
			eL = "^((-\\d+(\\.\\d+)?)|(0+(\\.0+)?))$";// 非正浮点数
		else if (type.equals("-"))
			eL = "^(-((\\d+\\.\\d*[1-9]\\d*)|(\\d*[1-9]\\d*\\.\\d+)|(\\d*[1-9]\\d*)))$";// 负浮点数
		else
			eL = "^(-?\\d+)(\\.\\d+)?$";// 浮点数
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(num);
		boolean b = m.matches();
		return b;
	}

	public static void main(String[] args) {
		System.out.println(StringUtil.ip2Long("192.168.2.146"));
	}
}
