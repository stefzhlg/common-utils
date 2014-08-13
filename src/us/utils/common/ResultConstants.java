/**  
 * @Title: ResultConstants.java
 * @Package com.hongdian.cardprize.util
 * @Description: TODO(用一句话描述该文件做什么)
 * @author stef zhao
 * @date 2013-5-21 下午03:04:39
 * @version V1.0  
 */
package us.utils.common;

/**
 * @author zhaoliangang
 * 
 */
public class ResultConstants {
	/*
	 * 0 验证通过 1 IP限定非法 2 参数错误或者个数不对 3 没有此代理 4 签名错误 5 帐号不存在 6 帐号已封停 7 密码错误
	 */

	public static final int SUCC = 0;
	public static final int INVIP = 1;
	public static final int INVPAR = 2;
	public static final int NOAGENT = 3;
	public static final int INVSIGN = 4;
	public static final int NOACC = 5;
	public static final int INVACC = 6;
	public static final int INVPASS = 7;

	/**
	 * 服务接口调用成功
	 * 
	 * @param result
	 * @return
	 */
	final public static boolean isSuccess(int result) {
		return result == SUCC;
	}

	/**
	 * 服务接口调用失败
	 * 
	 * @param result
	 * @return
	 */
	final public static boolean isFailure(int result) {
		return result > SUCC;
	}

	public static String getMessage(int result) {
		switch (result) {
		case SUCC:
			return "验证通过";
		case INVIP:
			return "IP限定非法";
		case INVPAR:
			return "参数错误或者个数不对";
		case NOAGENT:
			return "没有此代理";
		case INVSIGN:
			return "签名错误";
		case NOACC:
			return "帐号不存在";
		case INVACC:
			return "帐号已封停";
		case INVPASS:
			return "密码错误";

		default:
			return "登陆失败";
		}
	}

}
