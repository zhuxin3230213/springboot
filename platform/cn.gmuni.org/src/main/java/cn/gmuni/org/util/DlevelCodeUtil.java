package cn.gmuni.org.util;

public class DlevelCodeUtil {
    public static String generateDlevelCode(String currentCode) {
        String parentCode = currentCode.substring(0, currentCode.length() - 3);
        String tcode = (Integer.parseInt(currentCode.substring(currentCode.length() - 3)) + 1) + "";
        switch (tcode.length()) {
            case 1:
                return parentCode + "00" + tcode;
            case 2:
                return parentCode + "0" + tcode;
            default:
                return parentCode + tcode;
        }
    }

}
