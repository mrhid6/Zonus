package mrhid6.zonus;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class VersionControll implements Runnable {

	public static String currentVersion = "0";
	private static VersionControll instance = new VersionControll();

	public static Properties props = new Properties();
	private static String remoteVer;

	private static byte res = 0;
	public static String updateLocation;

	private static final String urlString = "https://raw.github.com/mrhid6/Zonus/master/version.xml";

	public static void checkVersion() {
		InputStream inStream = null;
		res = 0;

		try {
			URL url = new URL(urlString);
			inStream = url.openStream();
			props.loadFromXML(inStream);

			String VerProperty = props.getProperty("current");

			if (VerProperty != null) {
				String[] tokens = VerProperty.split("\\|");

				if (tokens.length == 2) {
					remoteVer = tokens[0];
					updateLocation = tokens[1];
				} else {
					res = 3;
				}

				if (remoteVer != null) {

					if (!currentVersion.equalsIgnoreCase(remoteVer)) {
						Config.setLastKnownVersion(remoteVer);
					}

					if (Config.Version.equals(remoteVer)) {
						res = 1;
					} else {
						res = 2;
					}

				}
			}

		} catch (Exception e) {

		} finally {

			if (res == 0) {
				res = 3;
			}

			try {
				if (inStream != null) {
					inStream.close();
				}
			} catch (Exception e) {

			}
		}
	}

	public static void Execute() {

		(new Thread(instance)).start();
	}

	public static byte getResult() {
		return res;
	}

	@Override
	public void run() {
		checkVersion();
	}

}
