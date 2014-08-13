package us.utils.config;

public class ConstantsConfig {

	private ConstantsConfig() {
	}

	private static Config conf = null;

	private static Config getConfig() {
		if (conf == null) {
			conf = new Config("/constants.properties");
		}
		return conf;
	}

	public final static int[] CPWD_KEY = getConfig().getIntArray("CPWD_KEY");
	public final static String SIGN_KEY = getConfig().getString("SIGN_KEY", "0");
	public final static String SIGN_CARDCODE_KEY = getConfig().getString("SIGN_CARDCODE_KEY", "0");
	public final static String[] IP_ARR = getConfig().getStringArray("IP_ARR");
	public final static String[] IP_ARR_CARDCODE = getConfig().getStringArray("IP_ARR_CARDCODE");

	public static void main(String[] args) {
		System.out.println(ConstantsConfig.SIGN_KEY);

	}

}
