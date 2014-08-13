/*package us.utils.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;

import com.hongdian.infrastructure.clients.AppClientFactory;
import com.hongdian.infrastructure.clients.AppExceptionClient;
import com.hongdian.infrastructure.clients.AppLogClient;
import com.hongdian.infrastructure.clients.ClientQueueThread;
import com.hongdian.infrastructure.clients.ClientQueueThread.ServiceConfigException;


/*
public class LogManager {

private static AppExceptionClient exceptionClient;
private static AppLogClient logClient;
private static boolean isRemoteLog = false;
private static boolean isLocalLog = false;
private static  String moduleName = "";

static {
	String remotLog = ConfigManager.getProperty("remote.log.enable");
	String localLog = ConfigManager.getProperty("local.log.enable");
	if("true".equals(remotLog)){
		isRemoteLog = true;
	}
	if("true".equals(localLog)){
		isLocalLog = true;
	}
	if(isRemoteLog){
		try {
			ClientQueueThread.load("/service-log-exception.properties");
		} catch (ServiceConfigException e) {
			e.printStackTrace();
		}
		exceptionClient = AppClientFactory.createAppExceptionClient();
		//moduleName = ConfigManager.getProperty("modulename");
		logClient = AppClientFactory.createAppLogClient();
	}
}

 *//**
 * 记录错误级别日志
 * @param log
 * @param t
 * @param message
 * @param params
 */
/*
public static void logError(Log log, Throwable t, String message, String ... params){
if(isRemoteLog){
	exceptionClient.log((Exception)t, true, moduleName, map(merge(message, params)));
}
if(isLocalLog){
	if(log.isErrorEnabled()){
		log.error(merge(message, params), t);
	}
}
}



 *//**
 * 记录错误级别日志
 * @param log
 * @param t
 * @param message
 * @param params
 */
/*
public static void logError(Log log, String message, String ... params){
if(isRemoteLog){
	logClient.error(moduleName, merge(message, params), null);
}
if(isLocalLog){
	if(log.isErrorEnabled()){
		log.error(merge(message, params));
	}
}
}

 *//**
 * 记录警告级别日志
 * @param log
 * @param t
 * @param message
 * @param params
 */
/*
public static void logWarn(Log log, String message, String ... params){
if(isRemoteLog){
	logClient.warn(moduleName, merge(message, params), null);
}
if(isLocalLog){
	if(log.isWarnEnabled()){
		log.warn(merge(message, params));
	}
}
}

 *//**
 * 记录警告级别日志
 * @param log
 * @param t
 * @param message
 * @param params
 */
/*
public static void logWarn(Log log, Exception e, String message, String ... params){
if(isRemoteLog){
	exceptionClient.log(e, true, moduleName, map(merge(message, params)));
}
if(isLocalLog){
	if(log.isWarnEnabled()){
		log.warn(merge(message, params), e);
	}
}
}

 *//**
 * 记录信息级别日志
 * @param log
 * @param t
 * @param message
 * @param params
 */
/*
public static void logInfo(Log log, String message, String ... params){
if(isLocalLog){
	if(log.isInfoEnabled()){
		log.info(merge(message, params));
	}
}
}

 *//**
 * 记录信息级别日志
 * @param log
 * @param t
 * @param message
 * @param params
 */
/*
public static void logRemoteDebug(Log log, String message, String ... params){
if(isRemoteLog){
	logClient.debug(moduleName, message,  map(merge(message, params)));
}
}

 *//**
 * 记录信息级别日志
 * @param log
 * @param t
 * @param message
 * @param params
 */
/*
public static void logDebug(Log log, String message, String ... params){
if(isLocalLog){
	if(log.isDebugEnabled()){
		log.debug(merge(message, params));
	}
}
}
 *//**
 * 将信息与动态参数合并
 * @param message
 * @param params
 * @return
 */
/*
public static String merge(String message, String... params){
if(params == null){
	return message; 
}
for(int i = 0 ; i < params.length ; i++ ){
	message = message.replace("{" + (i+1) + "}", params[i]);
}
return message;
}

 *//**
 * 将字符串转换成Map
 * @param message
 * @return
 */
/*
 * public static Map<String,String> map(String message){ Map<String,String> map
 * = new HashMap<String,String>(); map.put("msg", message); return map; }
 * 
 * }
 */