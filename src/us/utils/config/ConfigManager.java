package us.utils.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import us.utils.exception.BusinessException;

public class ConfigManager {

	private static Properties properties;

	static {
		properties = new Properties();
		try {
			properties.load(ConfigManager.class.getResourceAsStream("/config.properties"));
		} catch (FileNotFoundException e) {
			throw new BusinessException("配置文件config.properties不存在！");
		} catch (IOException e) {
			throw new BusinessException("读取配置文件异常！");
		}

	}

	/**
	 * 获取配置文件属性值
	 * 
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		if (properties != null) {
			return properties.getProperty(key);
		}
		return null;
	}
}
