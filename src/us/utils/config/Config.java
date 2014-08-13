package us.utils.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

public class Config {

	private Properties properties = null;

	public Config(String fileName) {
		initProperties(fileName);
	}

	private void initProperties(String fileName) {
		if (properties == null) {
			InputStream inputStream = Config.class.getClassLoader().getResourceAsStream(fileName);
			try {
				if (inputStream == null) {
					inputStream = new FileInputStream(fileName);
				}
				if (inputStream != null) {
					properties = new Properties();
					properties.load(inputStream);
				}
			} catch (IOException e) {
				properties = null;
				e.printStackTrace();
				System.out.println(" failture to loada propertis  file>>> errMsg:" + e.getMessage());
			}

		}
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public boolean getBoolean(String key) {
		return getBoolean(key, false);
	}

	/**
	 * 
	 * @param key
	 * @param def
	 * @return
	 */
	public boolean getBoolean(String key, boolean def) {

		String value = getString(key, "").trim();
		if ("true".equalsIgnoreCase(value))
			return true;

		if ("false".equalsIgnoreCase(value))
			return false;

		return def;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public int getInt(String key) {
		return getInt(key, 0);
	}

	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public int getInt(String key, int defaultValue) {

		try {
			return Integer.parseInt(getString(key));
		} catch (NumberFormatException e) {

			return defaultValue;
		}
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public long getLong(String key) {
		return getLong(key, 0);
	}

	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public long getLong(String key, long defaultValue) {
		try {
			return Long.parseLong(getString(key));
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public float getFloat(String key, float defaultValue) {
		try {
			return Float.parseFloat(getString(key));
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public double getDouble(String key, double defaultValue) {
		try {
			return Double.parseDouble(getString(key));
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	/**
	 * 
	 * @param key
	 * @param def
	 * @return
	 */
	public String getString(String key, String def) {

		return properties.getProperty(key, def).trim();

	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		return getString(key, "");
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public String[] getStringArray(String key) {
		return StringUtils.split(getString(key, ""), ",");
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public short[] getShortArray(String key) {

		String[] values = getStringArray(key);
		short[] retValues = new short[values.length];
		for (int i = 0; i < values.length; i++)
			retValues[i] = toShort(values[i], Short.MIN_VALUE);
		return retValues;

	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public int[] getIntArray(String key) {
		String[] values = getStringArray(key);
		int[] retValues = new int[values.length];
		for (int i = 0; i < values.length; i++)
			retValues[i] = toInt(values[i], Integer.MIN_VALUE);
		return retValues;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public int[] getIntArray1(String key) {
		String[] values = getStringArray(key);
		int[] retValues = new int[values.length];
		for (int i = 0; i < values.length; i++)
			retValues[i] = Integer.parseInt(values[i]);
		return retValues;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public long[] getLongArray(String key) {
		String[] values = getStringArray(key);
		long[] retValues = new long[values.length];
		for (int i = 0; i < values.length; i++)
			retValues[i] = toLong(values[i], Long.MIN_VALUE);
		return retValues;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public float[] getFloatArray(String key) {
		String[] values = getStringArray(key);
		float[] retValues = new float[values.length];
		for (int i = 0; i < values.length; i++)
			retValues[i] = toFloat(values[i], Float.MIN_VALUE);
		return retValues;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public double[] getDoubleArray(String key) {
		String[] values = getStringArray(key);
		double[] retValues = new double[values.length];
		for (int i = 0; i < values.length; i++)
			retValues[i] = toDouble(values[i], Double.MIN_VALUE);
		return retValues;
	}

	public static boolean isEmptyX(String s) {
		return (s == null || s.length() == 0) ? true : false;
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNotEmptyX(String s) {
		return !isEmptyX(s);
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

}
