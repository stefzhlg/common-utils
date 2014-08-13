package us.utils.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;


/**
 * 
 * @author
 * @since 1.0
 */
public abstract class SpyMemcached<T> {
	protected static final Logger logger = LoggerFactory.getLogger(SpyMemcached.class);
	protected static ObjectMapper mapper = new ObjectMapper();
	protected MemcachedClient client;
	@Autowired
	private MemcachedConfig memcachedConfig;
	protected String keywords;
	protected String ver;
	protected static final String separator = "-";
	private Class<T> cls;

	public T get(String key) throws IOException {
		Assert.notNull(key, "访问memcache的key不可为空");
		String realKey = composeKey(key);
		String value = (String) client.get(realKey);
		if (value == null) {
			return null;
		}
		TypeReference<T> typeRef = new TypeReference<T>() {
		};
		return mapper.readValue(value, typeRef);
	}

	public String getString(String key) throws IOException {
		Assert.notNull(key, "访问memcache的key不可为空");
		String realKey = composeKey(key);
		String value = (String) client.get(realKey);
		if (value == null) {
			return null;
		}
		return value;
	}

	public <E> List<E> getList(String key, Class<E> cls) throws IOException {
		Assert.notNull(key, "访问memcache的key不可为空");
		String realKey = composeKey(key);
		String value = (String) client.get(realKey);
		logger.debug("get value from memcache key:{} value:{}", realKey, value);
		if (value == null) {
			return null;
		}
		if (StringUtils.equalsIgnoreCase("[]", value)) {
			return new ArrayList<E>();
		}
		TypeReference<List<E>> typeRef = new TypeReference<List<E>>() {
		};
		return mapper.readValue(value, typeRef);
	}

	public <K, V> Map<K, V> getMap(String key, Class<K> kcls, Class<V> vcls) throws IOException {
		Assert.notNull(key, "访问memcache的key不可为空");
		String realKey = composeKey(key);
		String value = (String) client.get(realKey);
		logger.debug("get value from memcache key:{} value:{}", realKey, value);
		if (value == null) {
			return null;
		}
		TypeReference<Map<K, V>> typeRef = new TypeReference<Map<K, V>>() {
		};
		return mapper.readValue(value, typeRef);
	}

	public T get(int key) throws IOException {
		return get(String.valueOf(key));
	}

	public T get(long key) throws IOException {
		return get(String.valueOf(key));
	}

	public String getJsonString(String key) throws IOException {
		Assert.notNull(key, "访问memcache的key不可为空");
		String realKey = composeKey(key);
		String value = (String) client.get(realKey);
		logger.debug("get value from memcache key:{} value:{}", realKey, value);
		if (value == null) {
			return null;
		}
		return value;

	}

	public String getJsonString(int key) throws IOException {
		return getJsonString(String.valueOf(key));
	}

	public void set(String key, T value) throws IOException {
		set(key, 0, value);
	}

	public void set(long key, T value) throws IOException {
		set(String.valueOf(key), 0, value);
	}

	public void delete(String key) {
		Assert.notNull(key, "删除memcache的key不可为空");
		String realKey = composeKey(key);
		client.delete(realKey);
	}

	public void delete(Integer key) {
		delete(String.valueOf(key));
	}

	public void delete(Long key) {
		delete(String.valueOf(key));
	}

	public void set(String key, int expre, T value) throws IOException {
		Assert.notNull(key, "设置memcache的key不可为空");
		Assert.isTrue(expre >= 0, "expre time must >= 0");
		String realKey = composeKey(key);
		logger.debug("begin to set value to memcache key:{} value:{}", realKey, value);
		if (cls.getName().equals(String.class.getName())) {
			client.set(realKey, expre, value);
		} else {
			client.set(realKey, expre, mapper.writeValueAsString(value));
		}
		logger.debug("success set value to memcache key:{} value:{}", realKey, value);
	}

	protected String composeKey(String key) {
		String result = "";
		if (StringUtils.isNotBlank(keywords)) {
			result = memcachedConfig.channel + separator + memcachedConfig.module + separator + keywords + separator + key;
		} else {
			result = memcachedConfig.channel + separator + memcachedConfig.module + separator + key;
		}
		if (StringUtils.isNotBlank(this.getVer())) {
			// 如果包含ver
			result += separator + ver;
		}
		return result;
	}

	@PostConstruct
	public void initClient() throws IOException {
		client = new MemcachedClient(AddrUtil.getAddresses(memcachedConfig.getAddresses()));
		cls = ReflectionUtils.getSuperClassGenricType(this.getClass());

	}

	@PreDestroy
	public void destroy() {
		client.shutdown();
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

}
