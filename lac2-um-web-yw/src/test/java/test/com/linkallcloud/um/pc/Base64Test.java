package test.com.linkallcloud.um.pc;

import com.linkallcloud.lang.Lang;
import com.linkallcloud.log.Log;
import com.linkallcloud.log.Logs;
import com.linkallcloud.security.Securities;
import com.linkallcloud.um.domain.party.YwUser;
import com.linkallcloud.um.pc.utils.Base64Util;

public class Base64Test {

	private static Log log = Logs.get();

	public static void main(String[] args) {
		String sBase = "data:application/vnd.android.package-archive;base64,UEsDBBQACAAIAMIYx0xtlXFauE0AAN/";
		log.info("SBase: " + sBase);

		if (sBase.indexOf("base64,") > 0)
			sBase = sBase.substring(sBase.indexOf("base64,") + 7);
		String bin = Base64Util.Base64ToStr(sBase);
		log.info("SBase: " + sBase);
		log.info("SBase: " + bin.length());

		String pass = "zjhq@2019";
		String salt = new YwUser().generateUuid();
		String epass = Securities.password4Md5Src(Lang.md5(pass), salt);
		log.infof("salt:%s,plan password:%s, epass:%s", salt, pass, epass);
	}

}
