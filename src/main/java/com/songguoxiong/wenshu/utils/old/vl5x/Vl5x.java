package com.songguoxiong.wenshu.utils.old.vl5x;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Vl5x {
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    private final Vjkl5 vjkl5;

    private String value;

    public Vl5x(String vjkl5) {
        this.vjkl5 = new Vjkl5(vjkl5);
    }

    public Vl5x(Vjkl5 vjkl5) {
        this.vjkl5 = vjkl5;
    }

    public Vjkl5 getVjkl5() {
        return vjkl5;
    }

    public String getValue() {
        if (value == null) {
            String vjkl5 = getVjkl5().getValue();
            int index = Integer.parseInt(strToLong(vjkl5)) % 400;
            try {
                Method method = Vl5x.class.getDeclaredMethod("makeKey" + index, String.class);
                value = (String) method.invoke(this, vjkl5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }

    private String base64encode(String s) {
        return Base64.getEncoder().encodeToString(s.getBytes());
    }

    private String md5ToHex(String str) {
        return digestToHex(str, "MD5");
    }

    private String sha1ToHex(String str) {
        return digestToHex(str, "SHA1");
    }

    private String digestToHex(String str, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] digest = md.digest(str.getBytes());
            return bytesToHex(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = hexArray[v >>> 4];
            hexChars[i * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars).toLowerCase();
    }

    private String strToLong(String str) {
        long sum = 0;
        for (int i = 0; i < str.length(); i++) {
            int s = str.charAt(i);
            sum += (s << (i % 16));
        }
        return sum + "";
    }

    private String strToLongEn(String str) {
        long sum = 0;
        for (int i = 0; i < str.length(); i++) {
            int s = str.charAt(i);
            sum += (s << (i % 16)) + i;
        }
        return sum + "";
    }

    private String strToLongEn2(String str, int step) {
        long sum = 0;
        for (int i = 0; i < str.length(); i++) {
            int s = str.charAt(i);
            sum += (s << (i % 16)) + (i * step);
        }
        return sum + "";
    }

    private String strToLongEn3(String str, int step) {
        long sum = 0;
        for (int i = 0; i < str.length(); i++) {
            int s = str.charAt(i);
            sum += (s << (i % 16)) + (i + step - s);
        }
        return sum + "";
    }

    private String makeKey0(String str) {
        return md5ToHex(str.substring(5, 30) + str.substring(36, 39)).substring(4, 28);
    }

    private String makeKey1(String str) {
        str = str.substring(5, 30) + "5" + str.substring(1, 3) + "1" + str.substring(36, 39);
        return md5ToHex(str.substring(4) + (str.substring(5) + str.substring(4)).substring(6)).substring(4, 28);
    }

    private String makeKey2(String str) {
        str = str.substring(5, 30) + "15" + str.substring(1, 3) + str.substring(36, 39);
        return md5ToHex(str.substring(4) + (strToLong(str.substring(5)) + str.substring(4)).substring(5)).substring(1, 25);
    }

    private String makeKey3(String str) {
        str = str.substring(5, 30) + "15" + str.substring(1, 3) + str.substring(36, 39);
        return md5ToHex(str.substring(4) + (strToLongEn(str.substring(5)) + str.substring(4)).substring(5)).substring(3, 27);
    }

    private String makeKey4(String str) {
        str = str.substring(5, 30) + "2" + str.substring(1, 3) + str.substring(36, 39);
        return md5ToHex(md5ToHex(str.substring(1)) + strToLong((strToLongEn(str.substring(5)) + str.substring(4)).substring(5))).substring(3, 27);
    }

    private String makeKey5(String str) {
        str = base64encode(str.substring(5, 30) + str.substring(1, 3) + "1") + str.substring(36, 39);
        return md5ToHex(str).substring(4, 28);
    }

    private String makeKey6(String str) {
        str = str.substring(5, 30) + str.substring(36, 39);
        return md5ToHex(str.substring(6) + (base64encode(str.substring(4, 14)) + str.substring(2)).substring(2)).substring(2, 26);
    }

    private String makeKey7(String str) {
        str = base64encode(str.substring(5, 25) + "55" + str.substring(1, 3)) + str.substring(36, 39);
        return md5ToHex(md5ToHex(str.substring(1)) + strToLong((strToLong(str.substring(5)) + str.substring(4)).substring(5))).substring(3, 27);
    }

    private String makeKey8(String str) {
        str = base64encode(str.substring(5, 29) + "5-5") + str.substring(1, 3) + str.substring(36, 39);
        return md5ToHex(md5ToHex(str.substring(1)) + strToLongEn((strToLong(str.substring(5)) + str.substring(4)).substring(5))).substring(4, 28);
    }

    private String makeKey9(String str) {
        str = str.substring(5, 30) + "5" + str.substring(1, 3) + "1" + str.substring(36, 39);
        return md5ToHex(sha1ToHex(str.substring(4)) + (str.substring(5) + str.substring(4)).substring(6)).substring(4, 28);
    }

    private String makeKey10(String str) {
        str = base64encode(str.substring(5, 29) + "5") + str.substring(1, 3) + str.substring(36, 39);
        return md5ToHex(md5ToHex(str.substring(1)) + sha1ToHex((strToLong(str.substring(5)) + str.substring(4)).substring(5))).substring(4, 28);
    }

    private String makeKey11(String str) {
        str = str.substring(5, 29) + "2" + str.substring(1, 3) + str.substring(36, 39);
        return md5ToHex(str.substring(1) + sha1ToHex((strToLong(str.substring(5)) + str.substring(2)).substring(5))).substring(2, 26);
    }

    private String makeKey12(String str) {
        str = str.substring(5, 29) + str.substring(36, 39) + "2" + str.substring(1, 3);
        return md5ToHex(str.substring(1) + sha1ToHex(str.substring(5))).substring(1, 25);
    }

    private String makeKey13(String str) {
        str = str.substring(5, 29) + "2" + str.substring(1, 3);
        return md5ToHex(base64encode(str.substring(1) + sha1ToHex(str.substring(5)))).substring(1, 25);
    }

    private String makeKey14(String str) {
        str = str.substring(5, 29) + "2" + str.substring(1, 3);
        return sha1ToHex(base64encode((str.substring(1) + str.substring(5) + str.substring(1, 4)))).substring(1, 25);
    }

    private String makeKey15(String str) {
        str = str.substring(5, 29) + "2" + str.substring(1, 3);
        return sha1ToHex(base64encode(((strToLong(str.substring(5)) + str.substring(2)).substring(1) + str.substring(5) + str.substring(2, 5)))).substring(1, 25);
    }

    private String makeKey16(String str) {
        str = str.substring(5, 29) + "2" + str.substring(1, 3) + "-5";
        return md5ToHex(base64encode(((strToLongEn(str.substring(5)) + str.substring(2)).substring(1))) + strToLongEn2(str.substring(5), 5) + str.substring(2, 5)).substring(2, 26);
    }

    private String makeKey17(String str) {
        str = str.substring(5, 29) + "7" + str.substring(1, 3) + "-5";
        return md5ToHex(base64encode(((strToLongEn(str.substring(5)) + str.substring(2)).substring(1))) + strToLongEn2(str.substring(5), 6) + str.substring(7, 10)).substring(0, 24);
    }

    private String makeKey18(String str) {
        str = str.substring(5, 29) + "7" + str.substring(1, 3) + "5" + str.substring(7, 10);
        return md5ToHex((strToLongEn(str.substring(5)) + str.substring(2)).substring(1) + strToLongEn2(str.substring(5), 6) + str.substring(7, 10)).substring(0, 24);
    }

    private String makeKey19(String str) {
        str = str.substring(5, 29) + "7" + str.substring(5, 7) + "5" + str.substring(7, 10);
        return md5ToHex((strToLongEn(str.substring(5)) + str.substring(2)).substring(1) + strToLongEn3(str.substring(5), 4) + str.substring(7, 10)).substring(0, 24);
    }

    private String makeKey20(String str) {
        return md5ToHex(makeKey10(str) + makeKey5(str)).substring(1, 25);
    }

    private String makeKey21(String str) {
        return md5ToHex(makeKey11(str) + makeKey3(str)).substring(2, 26);
    }

    private String makeKey22(String str) {
        return md5ToHex(makeKey14(str) + makeKey19(str)).substring(3, 27);
    }

    private String makeKey23(String str) {
        return md5ToHex(makeKey15(str) + makeKey0(str)).substring(4, 28);
    }

    private String makeKey24(String str) {
        return md5ToHex(makeKey16(str) + makeKey1(str)).substring(1, 25);
    }

    private String makeKey25(String str) {
        return md5ToHex(makeKey9(str) + makeKey4(str)).substring(2, 26);
    }

    private String makeKey26(String str) {
        return md5ToHex(makeKey10(str) + makeKey5(str)).substring(3, 27);
    }

    private String makeKey27(String str) {
        return md5ToHex(makeKey17(str) + makeKey3(str)).substring(4, 28);
    }

    private String makeKey28(String str) {
        return md5ToHex(makeKey18(str) + makeKey7(str)).substring(1, 25);
    }

    private String makeKey29(String str) {
        return md5ToHex(makeKey19(str) + makeKey3(str)).substring(2, 26);
    }

    private String makeKey30(String str) {
        return md5ToHex(makeKey0(str) + makeKey7(str)).substring(3, 27);
    }

    private String makeKey31(String str) {
        return md5ToHex(makeKey1(str) + makeKey8(str)).substring(4, 28);
    }

    private String makeKey32(String str) {
        return md5ToHex(makeKey4(str) + makeKey14(str)).substring(3, 27);
    }

    private String makeKey33(String str) {
        return md5ToHex(makeKey5(str) + makeKey15(str)).substring(4, 28);
    }

    private String makeKey34(String str) {
        return md5ToHex(makeKey3(str) + makeKey16(str)).substring(1, 25);
    }

    private String makeKey35(String str) {
        return md5ToHex(makeKey7(str) + makeKey9(str)).substring(2, 26);
    }

    private String makeKey36(String str) {
        return md5ToHex(makeKey8(str) + makeKey10(str)).substring(3, 27);
    }

    private String makeKey37(String str) {
        return md5ToHex(makeKey6(str) + makeKey17(str)).substring(1, 25);
    }

    private String makeKey38(String str) {
        return md5ToHex(makeKey12(str) + makeKey18(str)).substring(2, 26);
    }

    private String makeKey39(String str) {
        return md5ToHex(makeKey14(str) + makeKey19(str)).substring(3, 27);
    }

    private String makeKey40(String str) {
        return md5ToHex(makeKey15(str) + makeKey0(str)).substring(4, 28);
    }

    private String makeKey41(String str) {
        return md5ToHex(makeKey16(str) + makeKey1(str)).substring(3, 27);
    }

    private String makeKey42(String str) {
        return md5ToHex(makeKey9(str) + makeKey4(str)).substring(4, 28);
    }

    private String makeKey43(String str) {
        return md5ToHex(makeKey10(str) + makeKey5(str)).substring(1, 25);
    }

    private String makeKey44(String str) {
        return md5ToHex(makeKey17(str) + makeKey3(str)).substring(2, 26);
    }

    private String makeKey45(String str) {
        return md5ToHex(makeKey18(str) + makeKey7(str)).substring(3, 27);
    }

    private String makeKey46(String str) {
        return md5ToHex(makeKey19(str) + makeKey17(str)).substring(4, 28);
    }

    private String makeKey47(String str) {
        return md5ToHex(makeKey0(str) + makeKey18(str)).substring(1, 25);
    }

    private String makeKey48(String str) {
        return md5ToHex(makeKey1(str) + makeKey19(str)).substring(2, 26);
    }

    private String makeKey49(String str) {
        return md5ToHex(makeKey4(str) + makeKey0(str)).substring(3, 27);
    }

    private String makeKey50(String str) {
        return md5ToHex(makeKey5(str) + makeKey1(str)).substring(4, 28);
    }

    private String makeKey51(String str) {
        return md5ToHex(makeKey3(str) + makeKey4(str)).substring(1, 25);
    }

    private String makeKey52(String str) {
        return md5ToHex(makeKey7(str) + makeKey14(str)).substring(2, 26);
    }

    private String makeKey53(String str) {
        return md5ToHex(makeKey12(str) + makeKey15(str)).substring(3, 27);
    }

    private String makeKey54(String str) {
        return md5ToHex(makeKey14(str) + makeKey16(str)).substring(4, 28);
    }

    private String makeKey55(String str) {
        return md5ToHex(makeKey15(str) + makeKey9(str)).substring(3, 27);
    }

    private String makeKey56(String str) {
        return md5ToHex(makeKey16(str) + makeKey10(str)).substring(4, 28);
    }

    private String makeKey57(String str) {
        return md5ToHex(makeKey9(str) + makeKey17(str)).substring(1, 25);
    }

    private String makeKey58(String str) {
        return md5ToHex(makeKey10(str) + makeKey18(str)).substring(2, 26);
    }

    private String makeKey59(String str) {
        return md5ToHex(makeKey17(str) + makeKey19(str)).substring(3, 27);
    }

    private String makeKey60(String str) {
        return md5ToHex(makeKey18(str) + makeKey0(str)).substring(1, 25);
    }

    private String makeKey61(String str) {
        return md5ToHex(makeKey19(str) + makeKey1(str)).substring(2, 26);
    }

    private String makeKey62(String str) {
        return md5ToHex(makeKey0(str) + makeKey4(str)).substring(3, 27);
    }

    private String makeKey63(String str) {
        return md5ToHex(makeKey1(str) + makeKey19(str)).substring(4, 28);
    }

    private String makeKey64(String str) {
        return md5ToHex(makeKey4(str) + makeKey0(str)).substring(3, 27);
    }

    private String makeKey65(String str) {
        return md5ToHex(makeKey14(str) + makeKey1(str)).substring(1, 25);
    }

    private String makeKey66(String str) {
        return md5ToHex(makeKey15(str) + makeKey4(str)).substring(2, 26);
    }

    private String makeKey67(String str) {
        return md5ToHex(makeKey16(str) + makeKey5(str)).substring(3, 27);
    }

    private String makeKey68(String str) {
        return md5ToHex(makeKey9(str) + makeKey3(str)).substring(4, 28);
    }

    private String makeKey69(String str) {
        return md5ToHex(makeKey10(str) + makeKey7(str)).substring(1, 25);
    }

    private String makeKey70(String str) {
        return md5ToHex(makeKey17(str) + makeKey0(str)).substring(2, 26);
    }

    private String makeKey71(String str) {
        return md5ToHex(makeKey18(str) + makeKey1(str)).substring(3, 27);
    }

    private String makeKey72(String str) {
        return md5ToHex(makeKey19(str) + makeKey4(str)).substring(4, 28);
    }

    private String makeKey73(String str) {
        return md5ToHex(makeKey0(str) + makeKey17(str)).substring(1, 25);
    }

    private String makeKey74(String str) {
        return md5ToHex(makeKey1(str) + makeKey18(str)).substring(2, 26);
    }

    private String makeKey75(String str) {
        return md5ToHex(makeKey14(str) + makeKey19(str)).substring(3, 27);
    }

    private String makeKey76(String str) {
        return md5ToHex(makeKey15(str) + makeKey0(str)).substring(4, 28);
    }

    private String makeKey77(String str) {
        return md5ToHex(makeKey16(str) + makeKey1(str)).substring(3, 27);
    }

    private String makeKey78(String str) {
        return md5ToHex(makeKey9(str) + makeKey4(str)).substring(4, 28);
    }

    private String makeKey79(String str) {
        return md5ToHex(makeKey10(str) + makeKey9(str)).substring(1, 25);
    }

    private String makeKey80(String str) {
        return md5ToHex(makeKey17(str) + makeKey10(str)).substring(2, 26);
    }

    private String makeKey81(String str) {
        return md5ToHex(makeKey18(str) + makeKey17(str)).substring(3, 27);
    }

    private String makeKey82(String str) {
        return md5ToHex(makeKey14(str) + makeKey18(str)).substring(1, 25);
    }

    private String makeKey83(String str) {
        return md5ToHex(makeKey15(str) + makeKey19(str)).substring(4, 28);
    }

    private String makeKey84(String str) {
        return md5ToHex(makeKey16(str) + makeKey0(str)).substring(1, 25);
    }

    private String makeKey85(String str) {
        return md5ToHex(makeKey9(str) + makeKey1(str)).substring(2, 26);
    }

    private String makeKey86(String str) {
        return md5ToHex(makeKey10(str) + makeKey4(str)).substring(3, 27);
    }

    private String makeKey87(String str) {
        return md5ToHex(makeKey14(str) + makeKey14(str)).substring(4, 28);
    }

    private String makeKey88(String str) {
        return md5ToHex(makeKey15(str) + makeKey15(str)).substring(1, 25);
    }

    private String makeKey89(String str) {
        return md5ToHex(makeKey16(str) + makeKey16(str)).substring(2, 26);
    }

    private String makeKey90(String str) {
        return md5ToHex(makeKey9(str) + makeKey9(str)).substring(3, 27);
    }

    private String makeKey91(String str) {
        return md5ToHex(makeKey10(str) + makeKey10(str)).substring(4, 28);
    }

    private String makeKey92(String str) {
        return md5ToHex(makeKey17(str) + makeKey17(str)).substring(3, 27);
    }

    private String makeKey93(String str) {
        return md5ToHex(makeKey18(str) + makeKey18(str)).substring(4, 28);
    }

    private String makeKey94(String str) {
        return md5ToHex(makeKey19(str) + makeKey19(str)).substring(1, 25);
    }

    private String makeKey95(String str) {
        return md5ToHex(makeKey0(str) + makeKey0(str)).substring(2, 26);
    }

    private String makeKey96(String str) {
        return md5ToHex(makeKey1(str) + makeKey1(str)).substring(3, 27);
    }

    private String makeKey97(String str) {
        return md5ToHex(makeKey4(str) + makeKey4(str)).substring(4, 28);
    }

    private String makeKey98(String str) {
        return md5ToHex(makeKey5(str) + makeKey5(str)).substring(3, 27);
    }

    private String makeKey99(String str) {
        return md5ToHex(makeKey3(str) + makeKey3(str)).substring(4, 28);
    }

    private String makeKey100(String str) {
        return md5ToHex(makeKey7(str) + makeKey3(str)).substring(1, 25);
    }

    private String makeKey101(String str) {
        return md5ToHex(makeKey10(str) + makeKey7(str)).substring(2, 26);
    }

    private String makeKey102(String str) {
        return md5ToHex(makeKey17(str) + makeKey18(str)).substring(1, 25);
    }

    private String makeKey103(String str) {
        return md5ToHex(makeKey18(str) + makeKey19(str)).substring(2, 26);
    }

    private String makeKey104(String str) {
        return md5ToHex(makeKey19(str) + makeKey0(str)).substring(3, 27);
    }

    private String makeKey105(String str) {
        return md5ToHex(makeKey0(str) + makeKey0(str)).substring(4, 28);
    }

    private String makeKey106(String str) {
        return md5ToHex(makeKey1(str) + makeKey1(str)).substring(1, 25);
    }

    private String makeKey107(String str) {
        return md5ToHex(makeKey14(str) + makeKey14(str)).substring(2, 26);
    }

    private String makeKey108(String str) {
        return md5ToHex(makeKey15(str) + makeKey15(str)).substring(3, 27);
    }

    private String makeKey109(String str) {
        return md5ToHex(makeKey16(str) + makeKey16(str)).substring(4, 28);
    }

    private String makeKey110(String str) {
        return md5ToHex(makeKey9(str) + makeKey9(str)).substring(1, 25);
    }

    private String makeKey111(String str) {
        return md5ToHex(makeKey10(str) + makeKey10(str)).substring(2, 26);
    }

    private String makeKey112(String str) {
        return md5ToHex(makeKey17(str) + makeKey17(str)).substring(3, 27);
    }

    private String makeKey113(String str) {
        return md5ToHex(makeKey18(str) + makeKey18(str)).substring(4, 28);
    }

    private String makeKey114(String str) {
        return md5ToHex(makeKey19(str) + makeKey19(str)).substring(3, 27);
    }

    private String makeKey115(String str) {
        return md5ToHex(makeKey0(str) + makeKey0(str)).substring(4, 28);
    }

    private String makeKey116(String str) {
        return md5ToHex(makeKey1(str) + makeKey1(str)).substring(1, 25);
    }

    private String makeKey117(String str) {
        return md5ToHex(makeKey4(str) + makeKey4(str)).substring(2, 26);
    }

    private String makeKey118(String str) {
        return md5ToHex(makeKey5(str) + makeKey15(str)).substring(3, 27);
    }

    private String makeKey119(String str) {
        return md5ToHex(makeKey3(str) + makeKey16(str)).substring(1, 25);
    }

    private String makeKey120(String str) {
        return md5ToHex(makeKey19(str) + makeKey9(str)).substring(1, 25);
    }

    private String makeKey121(String str) {
        return md5ToHex(makeKey0(str) + makeKey10(str)).substring(2, 26);
    }

    private String makeKey122(String str) {
        return md5ToHex(makeKey1(str) + makeKey17(str)).substring(3, 27);
    }

    private String makeKey123(String str) {
        return md5ToHex(makeKey4(str) + makeKey18(str)).substring(4, 28);
    }

    private String makeKey124(String str) {
        return md5ToHex(makeKey5(str) + makeKey19(str)).substring(1, 25);
    }

    private String makeKey125(String str) {
        return md5ToHex(makeKey3(str) + makeKey0(str)).substring(2, 26);
    }

    private String makeKey126(String str) {
        return md5ToHex(makeKey7(str) + makeKey1(str)).substring(3, 27);
    }

    private String makeKey127(String str) {
        return md5ToHex(makeKey3(str) + makeKey4(str)).substring(4, 28);
    }

    private String makeKey128(String str) {
        return md5ToHex(makeKey7(str) + makeKey5(str)).substring(1, 25);
    }

    private String makeKey129(String str) {
        return md5ToHex(makeKey8(str) + makeKey3(str)).substring(2, 26);
    }

    private String makeKey130(String str) {
        return md5ToHex(makeKey14(str) + makeKey7(str)).substring(3, 27);
    }

    private String makeKey131(String str) {
        return md5ToHex(makeKey15(str) + makeKey10(str)).substring(4, 28);
    }

    private String makeKey132(String str) {
        return md5ToHex(makeKey16(str) + makeKey17(str)).substring(3, 27);
    }

    private String makeKey133(String str) {
        return md5ToHex(makeKey9(str) + makeKey18(str)).substring(4, 28);
    }

    private String makeKey134(String str) {
        return md5ToHex(makeKey10(str) + makeKey19(str)).substring(1, 25);
    }

    private String makeKey135(String str) {
        return md5ToHex(makeKey17(str) + makeKey0(str)).substring(2, 26);
    }

    private String makeKey136(String str) {
        return md5ToHex(makeKey18(str) + makeKey1(str)).substring(1, 25);
    }

    private String makeKey137(String str) {
        return md5ToHex(makeKey19(str) + makeKey14(str)).substring(2, 26);
    }

    private String makeKey138(String str) {
        return md5ToHex(makeKey0(str) + makeKey15(str)).substring(3, 27);
    }

    private String makeKey139(String str) {
        return md5ToHex(makeKey1(str) + makeKey16(str)).substring(4, 28);
    }

    private String makeKey140(String str) {
        return md5ToHex(makeKey4(str) + makeKey9(str)).substring(1, 25);
    }

    private String makeKey141(String str) {
        return md5ToHex(makeKey5(str) + makeKey10(str)).substring(2, 26);
    }

    private String makeKey142(String str) {
        return md5ToHex(makeKey3(str) + makeKey17(str)).substring(3, 27);
    }

    private String makeKey143(String str) {
        return md5ToHex(makeKey7(str) + makeKey18(str)).substring(4, 28);
    }

    private String makeKey144(String str) {
        return md5ToHex(makeKey17(str) + makeKey19(str)).substring(1, 25);
    }

    private String makeKey145(String str) {
        return md5ToHex(makeKey18(str) + makeKey0(str)).substring(2, 26);
    }

    private String makeKey146(String str) {
        return md5ToHex(makeKey19(str) + makeKey1(str)).substring(3, 27);
    }

    private String makeKey147(String str) {
        return md5ToHex(makeKey0(str) + makeKey4(str)).substring(4, 28);
    }

    private String makeKey148(String str) {
        return md5ToHex(makeKey1(str) + makeKey5(str)).substring(3, 27);
    }

    private String makeKey149(String str) {
        return md5ToHex(makeKey4(str) + makeKey3(str)).substring(4, 28);
    }

    private String makeKey150(String str) {
        return md5ToHex(makeKey14(str) + makeKey19(str)).substring(1, 25);
    }

    private String makeKey151(String str) {
        return md5ToHex(makeKey15(str) + makeKey0(str)).substring(2, 26);
    }

    private String makeKey152(String str) {
        return md5ToHex(makeKey16(str) + makeKey1(str)).substring(3, 27);
    }

    private String makeKey153(String str) {
        return md5ToHex(makeKey9(str) + makeKey4(str)).substring(1, 25);
    }

    private String makeKey154(String str) {
        return md5ToHex(makeKey10(str) + makeKey5(str)).substring(1, 25);
    }

    private String makeKey155(String str) {
        return md5ToHex(makeKey17(str) + makeKey3(str)).substring(2, 26);
    }

    private String makeKey156(String str) {
        return md5ToHex(makeKey18(str) + makeKey7(str)).substring(3, 27);
    }

    private String makeKey157(String str) {
        return md5ToHex(makeKey19(str) + makeKey3(str)).substring(4, 28);
    }

    private String makeKey158(String str) {
        return md5ToHex(makeKey0(str) + makeKey7(str)).substring(1, 25);
    }

    private String makeKey159(String str) {
        return md5ToHex(makeKey1(str) + makeKey8(str)).substring(2, 26);
    }

    private String makeKey160(String str) {
        return md5ToHex(makeKey4(str) + makeKey14(str)).substring(3, 27);
    }

    private String makeKey161(String str) {
        return md5ToHex(makeKey19(str) + makeKey15(str)).substring(4, 28);
    }

    private String makeKey162(String str) {
        return md5ToHex(makeKey0(str) + makeKey16(str)).substring(1, 25);
    }

    private String makeKey163(String str) {
        return md5ToHex(makeKey1(str) + makeKey9(str)).substring(2, 26);
    }

    private String makeKey164(String str) {
        return md5ToHex(makeKey4(str) + makeKey10(str)).substring(3, 27);
    }

    private String makeKey165(String str) {
        return md5ToHex(makeKey5(str) + makeKey17(str)).substring(4, 28);
    }

    private String makeKey166(String str) {
        return md5ToHex(makeKey3(str) + makeKey18(str)).substring(3, 27);
    }

    private String makeKey167(String str) {
        return md5ToHex(makeKey7(str) + makeKey19(str)).substring(4, 28);
    }

    private String makeKey168(String str) {
        return md5ToHex(makeKey0(str) + makeKey0(str)).substring(1, 25);
    }

    private String makeKey169(String str) {
        return md5ToHex(makeKey1(str) + makeKey1(str)).substring(2, 26);
    }

    private String makeKey170(String str) {
        return md5ToHex(makeKey4(str) + makeKey4(str)).substring(3, 27);
    }

    private String makeKey171(String str) {
        return md5ToHex(makeKey17(str) + makeKey5(str)).substring(1, 25);
    }

    private String makeKey172(String str) {
        return md5ToHex(makeKey18(str) + makeKey3(str)).substring(2, 26);
    }

    private String makeKey173(String str) {
        return md5ToHex(makeKey19(str) + makeKey7(str)).substring(3, 27);
    }

    private String makeKey174(String str) {
        return md5ToHex(makeKey0(str) + makeKey17(str)).substring(4, 28);
    }

    private String makeKey175(String str) {
        return md5ToHex(makeKey1(str) + makeKey18(str)).substring(1, 25);
    }

    private String makeKey176(String str) {
        return md5ToHex(makeKey4(str) + makeKey19(str)).substring(2, 26);
    }

    private String makeKey177(String str) {
        return md5ToHex(makeKey9(str) + makeKey0(str)).substring(3, 27);
    }

    private String makeKey178(String str) {
        return md5ToHex(makeKey10(str) + makeKey1(str)).substring(4, 28);
    }

    private String makeKey179(String str) {
        return md5ToHex(makeKey17(str) + makeKey4(str)).substring(1, 25);
    }

    private String makeKey180(String str) {
        return md5ToHex(makeKey18(str) + makeKey14(str)).substring(3, 27);
    }

    private String makeKey181(String str) {
        return md5ToHex(makeKey19(str) + makeKey15(str)).substring(1, 25);
    }

    private String makeKey182(String str) {
        return md5ToHex(makeKey0(str) + makeKey16(str)).substring(2, 26);
    }

    private String makeKey183(String str) {
        return md5ToHex(makeKey1(str) + makeKey9(str)).substring(3, 27);
    }

    private String makeKey184(String str) {
        return md5ToHex(makeKey4(str) + makeKey10(str)).substring(4, 28);
    }

    private String makeKey185(String str) {
        return md5ToHex(makeKey14(str) + makeKey17(str)).substring(3, 27);
    }

    private String makeKey186(String str) {
        return md5ToHex(makeKey15(str) + makeKey18(str)).substring(4, 28);
    }

    private String makeKey187(String str) {
        return md5ToHex(makeKey16(str) + makeKey19(str)).substring(4, 28);
    }

    private String makeKey188(String str) {
        return md5ToHex(makeKey9(str) + makeKey0(str)).substring(1, 25);
    }

    private String makeKey189(String str) {
        return md5ToHex(makeKey10(str) + makeKey1(str)).substring(2, 26);
    }

    private String makeKey190(String str) {
        return md5ToHex(makeKey17(str) + makeKey4(str)).substring(3, 27);
    }

    private String makeKey191(String str) {
        return md5ToHex(makeKey18(str) + makeKey19(str)).substring(4, 28);
    }

    private String makeKey192(String str) {
        return md5ToHex(makeKey19(str) + makeKey0(str)).substring(1, 25);
    }

    private String makeKey193(String str) {
        return md5ToHex(makeKey0(str) + makeKey1(str)).substring(2, 26);
    }

    private String makeKey194(String str) {
        return md5ToHex(makeKey1(str) + makeKey4(str)).substring(3, 27);
    }

    private String makeKey195(String str) {
        return md5ToHex(makeKey4(str) + makeKey14(str)).substring(4, 28);
    }

    private String makeKey196(String str) {
        return md5ToHex(makeKey5(str) + makeKey15(str)).substring(3, 27);
    }

    private String makeKey197(String str) {
        return md5ToHex(makeKey3(str) + makeKey16(str)).substring(4, 28);
    }

    private String makeKey198(String str) {
        return md5ToHex(makeKey3(str) + makeKey9(str)).substring(1, 25);
    }

    private String makeKey199(String str) {
        return md5ToHex(makeKey7(str) + makeKey1(str)).substring(2, 26);
    }

    private String makeKey200(String str) {
        return md5ToHex(makeKey18(str) + makeKey19(str)).substring(2, 26);
    }

    private String makeKey201(String str) {
        return md5ToHex(makeKey19(str) + makeKey0(str)).substring(3, 27);
    }

    private String makeKey202(String str) {
        return md5ToHex(makeKey0(str) + makeKey1(str)).substring(1, 25);
    }

    private String makeKey203(String str) {
        return md5ToHex(makeKey1(str) + makeKey4(str)).substring(2, 26);
    }

    private String makeKey204(String str) {
        return md5ToHex(makeKey4(str) + makeKey5(str)).substring(3, 27);
    }

    private String makeKey205(String str) {
        return md5ToHex(makeKey14(str) + makeKey3(str)).substring(4, 28);
    }

    private String makeKey206(String str) {
        return md5ToHex(makeKey15(str) + makeKey7(str)).substring(1, 25);
    }

    private String makeKey207(String str) {
        return md5ToHex(makeKey16(str) + makeKey17(str)).substring(2, 26);
    }

    private String makeKey208(String str) {
        return md5ToHex(makeKey9(str) + makeKey18(str)).substring(3, 27);
    }

    private String makeKey209(String str) {
        return md5ToHex(makeKey10(str) + makeKey19(str)).substring(4, 28);
    }

    private String makeKey210(String str) {
        return md5ToHex(makeKey17(str) + makeKey0(str)).substring(1, 25);
    }

    private String makeKey211(String str) {
        return md5ToHex(makeKey18(str) + makeKey1(str)).substring(3, 27);
    }

    private String makeKey212(String str) {
        return md5ToHex(makeKey19(str) + makeKey4(str)).substring(1, 25);
    }

    private String makeKey213(String str) {
        return md5ToHex(makeKey0(str) + makeKey14(str)).substring(2, 26);
    }

    private String makeKey214(String str) {
        return md5ToHex(makeKey1(str) + makeKey15(str)).substring(3, 27);
    }

    private String makeKey215(String str) {
        return md5ToHex(makeKey4(str) + makeKey16(str)).substring(4, 28);
    }

    private String makeKey216(String str) {
        return md5ToHex(makeKey19(str) + makeKey9(str)).substring(3, 27);
    }

    private String makeKey217(String str) {
        return md5ToHex(makeKey0(str) + makeKey10(str)).substring(4, 28);
    }

    private String makeKey218(String str) {
        return md5ToHex(makeKey1(str) + makeKey17(str)).substring(4, 28);
    }

    private String makeKey219(String str) {
        return md5ToHex(makeKey4(str) + makeKey18(str)).substring(1, 25);
    }

    private String makeKey220(String str) {
        return md5ToHex(makeKey5(str) + makeKey19(str)).substring(2, 26);
    }

    private String makeKey221(String str) {
        return md5ToHex(makeKey3(str) + makeKey0(str)).substring(3, 27);
    }

    private String makeKey222(String str) {
        return md5ToHex(makeKey7(str) + makeKey1(str)).substring(4, 28);
    }

    private String makeKey223(String str) {
        return md5ToHex(makeKey0(str) + makeKey4(str)).substring(1, 25);
    }

    private String makeKey224(String str) {
        return md5ToHex(makeKey1(str) + makeKey5(str)).substring(2, 26);
    }

    private String makeKey225(String str) {
        return md5ToHex(makeKey4(str) + makeKey3(str)).substring(3, 27);
    }

    private String makeKey226(String str) {
        return md5ToHex(makeKey17(str) + makeKey7(str)).substring(4, 28);
    }

    private String makeKey227(String str) {
        return md5ToHex(makeKey18(str) + makeKey17(str)).substring(2, 26);
    }

    private String makeKey228(String str) {
        return md5ToHex(makeKey19(str) + makeKey18(str)).substring(3, 27);
    }

    private String makeKey229(String str) {
        return md5ToHex(makeKey0(str) + makeKey19(str)).substring(1, 25);
    }

    private String makeKey230(String str) {
        return md5ToHex(makeKey1(str) + makeKey0(str)).substring(2, 26);
    }

    private String makeKey231(String str) {
        return md5ToHex(makeKey4(str) + makeKey1(str)).substring(3, 27);
    }

    private String makeKey232(String str) {
        return md5ToHex(makeKey9(str) + makeKey4(str)).substring(4, 28);
    }

    private String makeKey233(String str) {
        return md5ToHex(makeKey10(str) + makeKey14(str)).substring(1, 25);
    }

    private String makeKey234(String str) {
        return md5ToHex(makeKey17(str) + makeKey15(str)).substring(2, 26);
    }

    private String makeKey235(String str) {
        return md5ToHex(makeKey18(str) + makeKey16(str)).substring(3, 27);
    }

    private String makeKey236(String str) {
        return md5ToHex(makeKey19(str) + makeKey9(str)).substring(4, 28);
    }

    private String makeKey237(String str) {
        return md5ToHex(makeKey0(str) + makeKey10(str)).substring(1, 25);
    }

    private String makeKey238(String str) {
        return md5ToHex(makeKey1(str) + makeKey17(str)).substring(3, 27);
    }

    private String makeKey239(String str) {
        return md5ToHex(makeKey4(str) + makeKey19(str)).substring(1, 25);
    }

    private String makeKey240(String str) {
        return md5ToHex(makeKey14(str) + makeKey0(str)).substring(2, 26);
    }

    private String makeKey241(String str) {
        return md5ToHex(makeKey15(str) + makeKey1(str)).substring(3, 27);
    }

    private String makeKey242(String str) {
        return md5ToHex(makeKey16(str) + makeKey4(str)).substring(4, 28);
    }

    private String makeKey243(String str) {
        return md5ToHex(makeKey9(str) + makeKey5(str)).substring(3, 27);
    }

    private String makeKey244(String str) {
        return md5ToHex(makeKey10(str) + makeKey3(str)).substring(4, 28);
    }

    private String makeKey245(String str) {
        return md5ToHex(makeKey17(str) + makeKey7(str)).substring(4, 28);
    }

    private String makeKey246(String str) {
        return md5ToHex(makeKey18(str) + makeKey17(str)).substring(2, 26);
    }

    private String makeKey247(String str) {
        return md5ToHex(makeKey19(str) + makeKey18(str)).substring(3, 27);
    }

    private String makeKey248(String str) {
        return md5ToHex(makeKey0(str) + makeKey19(str)).substring(1, 25);
    }

    private String makeKey249(String str) {
        return md5ToHex(makeKey1(str) + makeKey0(str)).substring(2, 26);
    }

    private String makeKey250(String str) {
        return md5ToHex(makeKey4(str) + makeKey1(str)).substring(3, 27);
    }

    private String makeKey251(String str) {
        return md5ToHex(makeKey19(str) + makeKey4(str)).substring(4, 28);
    }

    private String makeKey252(String str) {
        return md5ToHex(makeKey0(str) + makeKey14(str)).substring(1, 25);
    }

    private String makeKey253(String str) {
        return md5ToHex(makeKey1(str) + makeKey15(str)).substring(2, 26);
    }

    private String makeKey254(String str) {
        return md5ToHex(makeKey4(str) + makeKey4(str)).substring(3, 27);
    }

    private String makeKey255(String str) {
        return md5ToHex(makeKey5(str) + makeKey14(str)).substring(4, 28);
    }

    private String makeKey256(String str) {
        return md5ToHex(makeKey3(str) + makeKey15(str)).substring(1, 25);
    }

    private String makeKey257(String str) {
        return md5ToHex(makeKey7(str) + makeKey16(str)).substring(3, 27);
    }

    private String makeKey258(String str) {
        return md5ToHex(makeKey0(str) + makeKey9(str)).substring(1, 25);
    }

    private String makeKey259(String str) {
        return md5ToHex(makeKey1(str) + makeKey10(str)).substring(2, 26);
    }

    private String makeKey260(String str) {
        return md5ToHex(makeKey4(str) + makeKey17(str)).substring(3, 27);
    }

    private String makeKey261(String str) {
        return md5ToHex(makeKey17(str) + makeKey18(str)).substring(4, 28);
    }

    private String makeKey262(String str) {
        return md5ToHex(makeKey18(str) + makeKey19(str)).substring(3, 27);
    }

    private String makeKey263(String str) {
        return md5ToHex(makeKey19(str) + makeKey0(str)).substring(4, 28);
    }

    private String makeKey264(String str) {
        return md5ToHex(makeKey0(str) + makeKey1(str)).substring(4, 28);
    }

    private String makeKey265(String str) {
        return md5ToHex(makeKey1(str) + makeKey4(str)).substring(1, 25);
    }

    private String makeKey266(String str) {
        return md5ToHex(makeKey4(str) + makeKey19(str)).substring(2, 26);
    }

    private String makeKey267(String str) {
        return md5ToHex(makeKey9(str) + makeKey0(str)).substring(3, 27);
    }

    private String makeKey268(String str) {
        return md5ToHex(makeKey10(str) + makeKey1(str)).substring(4, 28);
    }

    private String makeKey269(String str) {
        return md5ToHex(makeKey17(str) + makeKey4(str)).substring(1, 25);
    }

    private String makeKey270(String str) {
        return md5ToHex(makeKey18(str) + makeKey14(str)).substring(2, 26);
    }

    private String makeKey271(String str) {
        return md5ToHex(makeKey19(str) + makeKey15(str)).substring(3, 27);
    }

    private String makeKey272(String str) {
        return md5ToHex(makeKey0(str) + makeKey16(str)).substring(4, 28);
    }

    private String makeKey273(String str) {
        return md5ToHex(makeKey1(str) + makeKey9(str)).substring(3, 27);
    }

    private String makeKey274(String str) {
        return md5ToHex(makeKey19(str) + makeKey1(str)).substring(4, 28);
    }

    private String makeKey275(String str) {
        return md5ToHex(makeKey0(str) + makeKey19(str)).substring(1, 25);
    }

    private String makeKey276(String str) {
        return md5ToHex(makeKey1(str) + makeKey0(str)).substring(2, 26);
    }

    private String makeKey277(String str) {
        return md5ToHex(makeKey4(str) + makeKey1(str)).substring(2, 26);
    }

    private String makeKey278(String str) {
        return md5ToHex(makeKey5(str) + makeKey4(str)).substring(3, 27);
    }

    private String makeKey279(String str) {
        return md5ToHex(makeKey3(str) + makeKey5(str)).substring(1, 25);
    }

    private String makeKey280(String str) {
        return md5ToHex(makeKey7(str) + makeKey3(str)).substring(2, 26);
    }

    private String makeKey281(String str) {
        return md5ToHex(makeKey17(str) + makeKey7(str)).substring(3, 27);
    }

    private String makeKey282(String str) {
        return md5ToHex(makeKey18(str) + makeKey17(str)).substring(4, 28);
    }

    private String makeKey283(String str) {
        return md5ToHex(makeKey19(str) + makeKey18(str)).substring(1, 25);
    }

    private String makeKey284(String str) {
        return md5ToHex(makeKey0(str) + makeKey19(str)).substring(2, 26);
    }

    private String makeKey285(String str) {
        return md5ToHex(makeKey1(str) + makeKey0(str)).substring(3, 27);
    }

    private String makeKey286(String str) {
        return md5ToHex(makeKey4(str) + makeKey1(str)).substring(4, 28);
    }

    private String makeKey287(String str) {
        return md5ToHex(makeKey14(str) + makeKey4(str)).substring(1, 25);
    }

    private String makeKey288(String str) {
        return md5ToHex(makeKey15(str) + makeKey14(str)).substring(3, 27);
    }

    private String makeKey289(String str) {
        return md5ToHex(makeKey16(str) + makeKey15(str)).substring(1, 25);
    }

    private String makeKey290(String str) {
        return md5ToHex(makeKey9(str) + makeKey16(str)).substring(2, 26);
    }

    private String makeKey291(String str) {
        return md5ToHex(makeKey10(str) + makeKey9(str)).substring(3, 27);
    }

    private String makeKey292(String str) {
        return md5ToHex(makeKey17(str) + makeKey10(str)).substring(4, 28);
    }

    private String makeKey293(String str) {
        return md5ToHex(makeKey18(str) + makeKey17(str)).substring(3, 27);
    }

    private String makeKey294(String str) {
        return md5ToHex(makeKey18(str) + makeKey18(str)).substring(4, 28);
    }

    private String makeKey295(String str) {
        return md5ToHex(makeKey19(str) + makeKey19(str)).substring(4, 28);
    }

    private String makeKey296(String str) {
        return md5ToHex(makeKey0(str) + makeKey0(str)).substring(1, 25);
    }

    private String makeKey297(String str) {
        return md5ToHex(makeKey1(str) + makeKey1(str)).substring(2, 26);
    }

    private String makeKey298(String str) {
        return md5ToHex(makeKey4(str) + makeKey4(str)).substring(3, 27);
    }

    private String makeKey299(String str) {
        return md5ToHex(makeKey5(str) + makeKey5(str)).substring(4, 28);
    }

    private String makeKey300(String str) {
        return md5ToHex(makeKey3(str) + makeKey3(str)).substring(1, 25);
    }

    private String makeKey301(String str) {
        return md5ToHex(makeKey7(str) + makeKey7(str)).substring(2, 26);
    }

    private String makeKey302(String str) {
        return md5ToHex(makeKey17(str) + makeKey17(str)).substring(3, 27);
    }

    private String makeKey303(String str) {
        return md5ToHex(makeKey18(str) + makeKey18(str)).substring(4, 28);
    }

    private String makeKey304(String str) {
        return md5ToHex(makeKey19(str) + makeKey19(str)).substring(3, 27);
    }

    private String makeKey305(String str) {
        return md5ToHex(makeKey0(str) + makeKey0(str)).substring(4, 28);
    }

    private String makeKey306(String str) {
        return md5ToHex(makeKey1(str) + makeKey1(str)).substring(1, 25);
    }

    private String makeKey307(String str) {
        return md5ToHex(makeKey4(str) + makeKey4(str)).substring(2, 26);
    }

    private String makeKey308(String str) {
        return md5ToHex(makeKey14(str) + makeKey14(str)).substring(2, 26);
    }

    private String makeKey309(String str) {
        return md5ToHex(makeKey15(str) + makeKey15(str)).substring(3, 27);
    }

    private String makeKey310(String str) {
        return md5ToHex(makeKey16(str) + makeKey16(str)).substring(1, 25);
    }

    private String makeKey311(String str) {
        return md5ToHex(makeKey9(str) + makeKey9(str)).substring(2, 26);
    }

    private String makeKey312(String str) {
        return md5ToHex(makeKey10(str) + makeKey10(str)).substring(3, 27);
    }

    private String makeKey313(String str) {
        return md5ToHex(makeKey17(str) + makeKey17(str)).substring(4, 28);
    }

    private String makeKey314(String str) {
        return md5ToHex(makeKey19(str) + makeKey19(str)).substring(1, 25);
    }

    private String makeKey315(String str) {
        return md5ToHex(makeKey0(str) + makeKey0(str)).substring(2, 26);
    }

    private String makeKey316(String str) {
        return md5ToHex(makeKey1(str) + makeKey1(str)).substring(3, 27);
    }

    private String makeKey317(String str) {
        return md5ToHex(makeKey4(str) + makeKey4(str)).substring(4, 28);
    }

    private String makeKey318(String str) {
        return md5ToHex(makeKey5(str) + makeKey5(str)).substring(1, 25);
    }

    private String makeKey319(String str) {
        return md5ToHex(makeKey3(str) + makeKey3(str)).substring(3, 27);
    }

    private String makeKey320(String str) {
        return md5ToHex(makeKey7(str) + makeKey7(str)).substring(1, 25);
    }

    private String makeKey321(String str) {
        return md5ToHex(makeKey17(str) + makeKey17(str)).substring(2, 26);
    }

    private String makeKey322(String str) {
        return md5ToHex(makeKey18(str) + makeKey18(str)).substring(3, 27);
    }

    private String makeKey323(String str) {
        return md5ToHex(makeKey19(str) + makeKey19(str)).substring(4, 28);
    }

    private String makeKey324(String str) {
        return md5ToHex(makeKey0(str) + makeKey0(str)).substring(3, 27);
    }

    private String makeKey325(String str) {
        return md5ToHex(makeKey1(str) + makeKey1(str)).substring(4, 28);
    }

    private String makeKey326(String str) {
        return md5ToHex(makeKey4(str) + makeKey4(str)).substring(4, 28);
    }

    private String makeKey327(String str) {
        return md5ToHex(makeKey19(str) + makeKey14(str)).substring(1, 25);
    }

    private String makeKey328(String str) {
        return md5ToHex(makeKey0(str) + makeKey15(str)).substring(2, 26);
    }

    private String makeKey329(String str) {
        return md5ToHex(makeKey1(str) + makeKey16(str)).substring(3, 27);
    }

    private String makeKey330(String str) {
        return md5ToHex(makeKey4(str) + makeKey9(str)).substring(4, 28);
    }

    private String makeKey331(String str) {
        return md5ToHex(makeKey19(str) + makeKey10(str)).substring(1, 25);
    }

    private String makeKey332(String str) {
        return md5ToHex(makeKey0(str) + makeKey17(str)).substring(2, 26);
    }

    private String makeKey333(String str) {
        return md5ToHex(makeKey1(str) + makeKey18(str)).substring(3, 27);
    }

    private String makeKey334(String str) {
        return md5ToHex(makeKey4(str) + makeKey18(str)).substring(4, 28);
    }

    private String makeKey335(String str) {
        return md5ToHex(makeKey5(str) + makeKey19(str)).substring(3, 27);
    }

    private String makeKey336(String str) {
        return md5ToHex(makeKey3(str) + makeKey0(str)).substring(4, 28);
    }

    private String makeKey337(String str) {
        return md5ToHex(makeKey7(str) + makeKey1(str)).substring(2, 26);
    }

    private String makeKey338(String str) {
        return md5ToHex(makeKey0(str) + makeKey4(str)).substring(3, 27);
    }

    private String makeKey339(String str) {
        return md5ToHex(makeKey1(str) + makeKey5(str)).substring(1, 25);
    }

    private String makeKey340(String str) {
        return md5ToHex(makeKey4(str) + makeKey3(str)).substring(2, 26);
    }

    private String makeKey341(String str) {
        return md5ToHex(makeKey17(str) + makeKey7(str)).substring(3, 27);
    }

    private String makeKey342(String str) {
        return md5ToHex(makeKey18(str) + makeKey17(str)).substring(4, 28);
    }

    private String makeKey343(String str) {
        return md5ToHex(makeKey19(str) + makeKey18(str)).substring(1, 25);
    }

    private String makeKey344(String str) {
        return md5ToHex(makeKey0(str) + makeKey19(str)).substring(2, 26);
    }

    private String makeKey345(String str) {
        return md5ToHex(makeKey1(str) + makeKey0(str)).substring(3, 27);
    }

    private String makeKey346(String str) {
        return md5ToHex(makeKey4(str) + makeKey1(str)).substring(4, 28);
    }

    private String makeKey347(String str) {
        return md5ToHex(makeKey9(str) + makeKey4(str)).substring(1, 25);
    }

    private String makeKey348(String str) {
        return md5ToHex(makeKey10(str) + makeKey14(str)).substring(3, 27);
    }

    private String makeKey349(String str) {
        return md5ToHex(makeKey17(str) + makeKey15(str)).substring(1, 25);
    }

    private String makeKey350(String str) {
        return md5ToHex(makeKey18(str) + makeKey16(str)).substring(2, 26);
    }

    private String makeKey351(String str) {
        return md5ToHex(makeKey19(str) + makeKey9(str)).substring(3, 27);
    }

    private String makeKey352(String str) {
        return md5ToHex(makeKey0(str) + makeKey10(str)).substring(4, 28);
    }

    private String makeKey353(String str) {
        return md5ToHex(makeKey1(str) + makeKey17(str)).substring(3, 27);
    }

    private String makeKey354(String str) {
        return md5ToHex(makeKey18(str) + makeKey19(str)).substring(4, 28);
    }

    private String makeKey355(String str) {
        return md5ToHex(makeKey19(str) + makeKey0(str)).substring(4, 28);
    }

    private String makeKey356(String str) {
        return md5ToHex(makeKey0(str) + makeKey1(str)).substring(1, 25);
    }

    private String makeKey357(String str) {
        return md5ToHex(makeKey1(str) + makeKey4(str)).substring(2, 26);
    }

    private String makeKey358(String str) {
        return md5ToHex(makeKey4(str) + makeKey5(str)).substring(3, 27);
    }

    private String makeKey359(String str) {
        return md5ToHex(makeKey5(str) + makeKey3(str)).substring(4, 28);
    }

    private String makeKey360(String str) {
        return md5ToHex(makeKey3(str) + makeKey7(str)).substring(2, 26);
    }

    private String makeKey361(String str) {
        return md5ToHex(makeKey7(str) + makeKey17(str)).substring(3, 27);
    }

    private String makeKey362(String str) {
        return md5ToHex(makeKey17(str) + makeKey18(str)).substring(1, 25);
    }

    private String makeKey363(String str) {
        return md5ToHex(makeKey18(str) + makeKey19(str)).substring(2, 26);
    }

    private String makeKey364(String str) {
        return md5ToHex(makeKey19(str) + makeKey0(str)).substring(3, 27);
    }

    private String makeKey365(String str) {
        return md5ToHex(makeKey0(str) + makeKey1(str)).substring(4, 28);
    }

    private String makeKey366(String str) {
        return md5ToHex(makeKey1(str) + makeKey4(str)).substring(1, 25);
    }

    private String makeKey367(String str) {
        return md5ToHex(makeKey4(str) + makeKey7(str)).substring(2, 26);
    }

    private String makeKey368(String str) {
        return md5ToHex(makeKey14(str) + makeKey17(str)).substring(3, 27);
    }

    private String makeKey369(String str) {
        return md5ToHex(makeKey15(str) + makeKey18(str)).substring(4, 28);
    }

    private String makeKey370(String str) {
        return md5ToHex(makeKey16(str) + makeKey19(str)).substring(1, 25);
    }

    private String makeKey371(String str) {
        return md5ToHex(makeKey9(str) + makeKey0(str)).substring(3, 27);
    }

    private String makeKey372(String str) {
        return md5ToHex(makeKey10(str) + makeKey1(str)).substring(1, 25);
    }

    private String makeKey373(String str) {
        return md5ToHex(makeKey17(str) + makeKey4(str)).substring(2, 26);
    }

    private String makeKey374(String str) {
        return md5ToHex(makeKey19(str) + makeKey17(str)).substring(3, 27);
    }

    private String makeKey375(String str) {
        return md5ToHex(makeKey0(str) + makeKey18(str)).substring(4, 28);
    }

    private String makeKey376(String str) {
        return md5ToHex(makeKey1(str) + makeKey19(str)).substring(3, 27);
    }

    private String makeKey377(String str) {
        return md5ToHex(makeKey4(str) + makeKey0(str)).substring(4, 28);
    }

    private String makeKey379(String str) {
        return md5ToHex(makeKey3(str) + makeKey4(str)).substring(1, 25);
    }

    private String makeKey378(String str) {
        return md5ToHex(makeKey5(str) + makeKey1(str)).substring(4, 28);
    }

    private String makeKey380(String str) {
        return md5ToHex(makeKey7(str) + makeKey9(str)).substring(2, 26);
    }

    private String makeKey381(String str) {
        return md5ToHex(makeKey17(str) + makeKey10(str)).substring(3, 27);
    }

    private String makeKey382(String str) {
        return md5ToHex(makeKey18(str) + makeKey17(str)).substring(4, 28);
    }

    private String makeKey383(String str) {
        return md5ToHex(makeKey19(str) + makeKey18(str)).substring(1, 25);
    }

    private String makeKey384(String str) {
        return md5ToHex(makeKey0(str) + makeKey19(str)).substring(2, 26);
    }

    private String makeKey385(String str) {
        return md5ToHex(makeKey1(str) + makeKey0(str)).substring(3, 27);
    }

    private String makeKey386(String str) {
        return md5ToHex(makeKey4(str) + makeKey1(str)).substring(4, 28);
    }

    private String makeKey387(String str) {
        return md5ToHex(makeKey17(str) + makeKey1(str)).substring(2, 26);
    }

    private String makeKey388(String str) {
        return md5ToHex(makeKey18(str) + makeKey4(str)).substring(3, 27);
    }

    private String makeKey389(String str) {
        return md5ToHex(makeKey19(str) + makeKey7(str)).substring(1, 25);
    }

    private String makeKey390(String str) {
        return md5ToHex(makeKey0(str) + makeKey17(str)).substring(2, 26);
    }

    private String makeKey391(String str) {
        return md5ToHex(makeKey1(str) + makeKey18(str)).substring(3, 27);
    }

    private String makeKey392(String str) {
        return md5ToHex(makeKey4(str) + makeKey19(str)).substring(4, 28);
    }

    private String makeKey393(String str) {
        return md5ToHex(makeKey9(str) + makeKey0(str)).substring(1, 25);
    }

    private String makeKey394(String str) {
        return md5ToHex(makeKey10(str) + makeKey1(str)).substring(2, 26);
    }

    private String makeKey395(String str) {
        return md5ToHex(makeKey17(str) + makeKey4(str)).substring(3, 27);
    }

    private String makeKey396(String str) {
        return md5ToHex(makeKey18(str) + makeKey17(str)).substring(4, 28);
    }

    private String makeKey397(String str) {
        return md5ToHex(makeKey19(str) + makeKey18(str)).substring(1, 25);
    }

    private String makeKey398(String str) {
        return md5ToHex(makeKey0(str) + makeKey19(str)).substring(3, 27);
    }

    private String makeKey399(String str) {
        return md5ToHex(makeKey1(str) + makeKey0(str)).substring(1, 25);
    }
}

