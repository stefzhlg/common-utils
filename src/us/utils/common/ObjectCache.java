package us.utils.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;



@Service
public class ObjectCache extends SpyMemcached<List<Object>> {

	public List<Object> getObject(String key) throws IOException {
		Assert.notNull(key, "访问memcache的key不可为空");
		String realKey = composeKey(key);
		String value = (String) client.get(realKey);
		if (value == null) {
			return null;
		}
		if (StringUtils.equalsIgnoreCase("[]", value)) {
			return new ArrayList<Object>();
		}
		TypeReference<List<Object>> typeRef = new TypeReference<List<Object>>() {
		};
		return mapper.readValue(value, typeRef);
	}
}
