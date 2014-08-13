package us.utils.common;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


@Service
public class SessionCache extends SpyMemcached<String> {

	public String get(String key) throws IOException {
		Assert.notNull(key, "访问memcache的key不可为空");
		String realKey = composeKey(key);
		String value = (String) client.get(realKey);
		if (value == null) {
			return null;
		}
		return value;
	}
}
