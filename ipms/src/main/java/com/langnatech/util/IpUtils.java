package com.langnatech.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

public class IpUtils {

    public static void main(String[] args) {
        System.out.println(getMaskBits("255.255.235.0"));
        System.out.println(getHostNum(24));
        System.out.println(getBroadcastIp("222.216.207.55", 24));
        System.out.println(getNetWorkIp("222.216.207.55", 24));
        System.out.println(getFirstIp("222.216.207.55", 24));
        System.out.println(getLastIp("222.216.207.55", 24));
        long dec=IpUtils.getDecByIp("222.216.0.1");
        System.out.println(IpUtils.getIpByDec(dec+550));
    }

    private final static int ipbits = 32;

    /**
     * @Title: isIp
     * @Description: 验证是否为IP 格式：192.168.0.201
     * @param ip
     * @return
     */
    public static boolean isIp(String ip) {
        if(StringUtils.isEmpty(ip)){
            return false;
        }
        String reg = "^([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}$";
        Pattern pat = Pattern.compile(reg);
        Matcher mat = pat.matcher(ip);
        return mat.matches();
    }

    /**
     * @Title: isMask
     * @Description: 判断是否是子网掩码 格式：255.255.255.0
     * @param mask
     * @return
     */
    public static boolean isMask(String netmask) {
        if(StringUtils.isEmpty(netmask)){
            return false;
        }        
        String reg = "^(254|252|248|240|224|192|128|0)\\.0\\.0\\.0|255\\.(254|252|248|240|224|192|128|0)\\.0\\.0|255\\.255\\.(254|252|248|240|224|192|128|0)\\.0|255\\.255\\.255\\.(254|252|248|240|224|192|128|0)$";
        Pattern pat = Pattern.compile(reg);
        Matcher mat = pat.matcher(netmask);
        return mat.matches();
    }

    /**
     * @Title: getDecByIp
     * @Description: 获取IP十进制
     * @param ip 192.168.0.3
     * @return
     */
    public static long getDecByIp(String ip) {
        Assert.isTrue(IpUtils.isIp(ip), "Parameter ip: IP address is not Vaild ");
        long[] ary = new long[4];
        int position1 = ip.indexOf(".");
        int position2 = ip.indexOf(".", position1 + 1);
        int position3 = ip.indexOf(".", position2 + 1);

        ary[0] = Long.parseLong(ip.substring(0, position1));
        ary[1] = Long.parseLong(ip.substring(position1 + 1, position2));
        ary[2] = Long.parseLong(ip.substring(position2 + 1, position3));
        ary[3] = Long.parseLong(ip.substring(position3 + 1));

        // ary1*256*256*256+ary2*256*256+ary3*256+ary4
        long decIp = (ary[0] << 24) + (ary[1] << 16) + (ary[2] << 8) + ary[3];
        return decIp;
    }

    /**
     * @Title: getIpByDec
     * @Description: 通过十进制的ip获取点分十进制Ip
     * @param decIp 十进制Ip
     * @return
     */
    public static String getIpByDec(long decIp) {
        StringBuffer sb = new StringBuffer("");

        // 直接右移24位
        sb.append(String.valueOf(decIp >>> 24));
        sb.append(".");
        // 将高8位置0，然后右移16位
        sb.append(String.valueOf( (decIp & 0x00FFFFFF) >>> 16));
        sb.append(".");
        sb.append(String.valueOf( (decIp & 0x0000FFFF) >>> 8));
        sb.append(".");
        sb.append(String.valueOf(decIp & 0x000000FF));
        return sb.toString();
    }

    /**
     * @Title: getIpByBin
     * @Description: 根据二进制Ip地址获取点分十进制IP地址
     * @param ipBin 二进制Ip
     * @return
     */
    public static String getIpByBin(String ipBin) {
        Long ipDec = Long.parseLong(ipBin, 2);
        return getIpByDec(ipDec);
    }

    /**
     * @Title: getMaskBinByMbits
     * @Description: 根据网络位数得到子网掩码二进制
     * @param maskbits 网络位数
     * @return
     */
    public static String getMaskBinByMbits(int maskbits) {
        StringBuffer sb = new StringBuffer();
        int location = 0;

        for (; maskbits > 0; maskbits-- , location++ ) {
            sb.append(String.valueOf(1));
        }
        for (; location < ipbits; location++ ) {
            sb.append(String.valueOf(0));
        }
        return sb.toString();
    }

    /**
     * @Title: getMaskBits
     * @Description: 根据点分十进制子网掩码得到掩码位数
     * @param netmarks 点分十进制子网掩码
     * @return
     */
    public static int getMaskBits(String netmark) {
        String str = "";
        int maskBits = 0, count = 0;
        String[] ipList = netmark.split("\\.");
        for (int n = 0; n < ipList.length; n++ ) {
            str = Integer.toBinaryString(Integer.parseInt(ipList[n]));
            count = 0;
            for (int i = 0; i < str.length(); i++ ) {
                i = str.indexOf('1', i);
                if (i == -1) {
                    break;
                }
                count++ ;
            }
            maskBits += count;
        }
        return maskBits;
    }

    /**
     * @Title: getMask
     * @Description: 根据网络位数得到点分十进制子网掩码
     * @param maskbits 网络位数
     * @return
     */
    public static String getMask(int maskbits) {
        return getIpByBin(getMaskBinByMbits(maskbits));
    }

    /**
     * @Title: getNetWorkIp
     * @Description: 根据子网地址(点分十进制)、网络掩码(点分十进制)得网络地址
     * @param ipDec 子网地址
     * @param netmask 子网掩码
     * @return
     */
    public static String getNetWorkIp(String ip, String netmask) {
        char ipCharList[] = Long.toBinaryString(getDecByIp(ip)).toCharArray();
        char subCharList[] = Long.toBinaryString(getDecByIp(netmask)).toCharArray();
        StringBuffer sb = new StringBuffer("");

        int value = 0;
        int length = (ipCharList.length > subCharList.length) ? subCharList.length : ipCharList.length;
        int i = 0;
        int j = Math.abs(ipCharList.length - subCharList.length);
        for (; i < length; i++ , j++ ) {
            value = Integer.parseInt(String.valueOf(ipCharList[i])) & Integer.parseInt(String.valueOf(subCharList[j]));
            sb.append(value);
        }
        return getIpByBin(sb.toString());
    }

    /**
     * @Title: getNetWorkIp
     * @Description:根据点分十进制IP子网地址和网络位数获得网络地址
     * @param ip 点分十进制Ip
     * @param maskbits 网络位数
     * @return
     */
    public static String getNetWorkIp(String ip, int maskbits) {
        String netMaskBin = getMaskBinByMbits(maskbits);
        return getNetWorkIp(ip, getIpByBin(netMaskBin));
    }

    /**
     * @Title: getHostNum
     * @Description: 根据子网掩码计算主机数量（子网大小）
     * @param maskbits 掩码位数
     * @return
     */
    public static int getHostNum(int maskbits) {
        if (maskbits <= 0 || maskbits >= 32) {
            return 0;
        }
        int bits = 32 - maskbits;
        int hostNum = (int)Math.pow(2, bits) - 2;
        return hostNum + 2;
    }

    /**
     * @Title: getBroadcastIp
     * @Description: 根据点分十进制IP子网地址和子网掩码获得广播地址
     * @param ip 点分十进制Ip
     * @param netmask 子网掩码
     * @return String 点分十进制地址
     */
    public static String getBroadcastIp(String ip, String netmask) {
        String ipBin = Long.toBinaryString(getDecByIp(ip));
        int maskbits = getMaskBits(netmask);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maskbits - ipBin.length(); i++ ) {
            sb.append("0");
        }
        sb.append(ipBin);
        char[] valueList = sb.toString().toCharArray();
        for (int k = maskbits; k < ipbits && k < valueList.length; k++ ) {
            valueList[k] = '1';
        }

        return getIpByBin(String.valueOf(valueList));
    }

    /**
     * @Title: getBroadcastIp
     * @Description: 根据子网地址(十进制)、网络位数获得广播地址
     * @param ipDec 子网地址(十进制)
     * @param maskbits 网络位数
     * @return
     */
    public static String getBroadcastIp(String ip, int maskbits) {
        String netMaskBin = getMaskBinByMbits(maskbits);
        return getBroadcastIp(ip, getIpByBin(netMaskBin));
    }

    // public static String getNetWorkIp(String ip, int maskbits) {
    // String binIp = Long.toBinaryString(getDecByIp(ip));
    // String binMask = getMaskBinByMbits(maskbits);
    // int len = binIp.length();
    // String netIp = "";
    //
    // if (binMask.length() > len) {
    // len = binMask.length();
    // }
    //
    // while (binIp.length() < len) {
    // binIp = "0" + binIp;
    // }
    //
    // while (binMask.length() < len) {
    // binMask = "0" + binMask;
    // }
    // for (int i = 0; i < len; i++ ) {
    // netIp = netIp + ( (String.valueOf(binIp.charAt(i)).equals("1") && String.valueOf(binMask.charAt(i)).equals("1"))
    // ? "1" : "0");
    // }
    // return getIpByBin(netIp);
    // }

    /**
     * @Title: getFirstIp
     * @Description: 根据ip地址及其网络位数获取第一个可用Ip
     * @param ip ip地址
     * @param maskbits 网络位数
     * @return
     */
    public static String getFirstIp(String ip, int maskbits) {
        String netWorkIp = getNetWorkIp(ip, maskbits);
        return getIpByDec(getDecByIp(netWorkIp) + 1);
    }

    /**
     * @Title: getLastIp
     * @Description: 根据Ip地址（点分十进制）及其网络位数得到最后一个可用ip
     * @param ip ip地址（点分十进制）
     * @param maskbits 网络位数
     * @return
     */
    public static String getLastIp(String ip, int maskbits) {
        String broadcastIp = getBroadcastIp(ip, maskbits);
        return getIpByDec(getDecByIp(broadcastIp) - 1);

    }

    // TODO 扩展（js中是返回的对象，实际上底层都是算出子网掩码去进行计算）

    /**
     * @Title: getMask
     * @Description: 根据点分十进制ip及其子网数量得到子网掩码（网络位数）
     * @param ip
     * @param subnetnum
     * @return
     */
    public static int getmBitsBysnum(String ip, int subnetnum) {
        String binarySnum = Integer.toBinaryString(subnetnum);
        int slen = binarySnum.length();
        String defaultMask = getDefaultMask(getDecByIp(ip));
        String binDefaultmask = Long.toBinaryString(getDecByIp(defaultMask));
        String binaryMask = binDefaultmask;

        if (defaultMask == "255.0.0.0") {
            for (int i = 0; i < slen; i++ ) {
                binaryMask = replaceChar(binaryMask, 9 + i, "1");
            }
        }

        if (defaultMask == "255.255.0.0") {
            for (int i = 0; i < slen; i++ ) {
                binaryMask = replaceChar(binaryMask, 17 + i, "1");

            }
        }

        if (defaultMask == "255.255.255.0") {
            for (int i = 0; i < slen; i++ ) {
                binaryMask = replaceChar(binaryMask, 25 + i, "1");
            }

        }
        return getMaskBits(getIpByBin(binaryMask));
    }

    /**
     * @Title: getMaskBits
     * @Description:通过点分十进制ip和每个网络的主机数获得网络位数（子网掩码）
     * @param ip
     * @param hostnum
     * @return
     */
    public static int getmBitsByHostNum(String ip, int hostnum) {

        String binHostNum = Integer.toBinaryString(hostnum);
        int hlen = binHostNum.length();
        String specialMask = Long.toBinaryString(getDecByIp( ("255.255.255.255")));
        String binaryMask = specialMask;

        for (int i = 0; i < hlen; i++ ) {

            binaryMask = replaceChar(binaryMask, specialMask.length() - i, "0");
        }

        return getMaskBits(getIpByBin(binaryMask));
    }

    /**
     * @Title: getmBitsByIpNum
     * @Description: 通过点分十进制ip地址和所需ip数量来得到网络位数（子网掩码）
     * @param ip
     * @param ipnum
     * @return
     */
    public static int getmBitsByIpNum(String ip, int ipnum) {

        int expval = (int) (Math.log(ipnum) / Math.log(2)) + 1;
        int maxaddrval = (int)Math.pow(2, expval);

        if (maxaddrval - ipnum < 2) {
            expval += 1;
        }

        int maskbits = (32 - expval);

        return maskbits;
    }

    /**
     * @Title: getDefaultMask
     * @Description: 根据十进制IP得到默认的子网掩码
     * @param ipdec
     * @return
     */
    public static String getDefaultMask(Long ipdec) {
        String mask = "";

        if (ipdec > getDecByIp("0.0.0.0") && ipdec < getDecByIp("126.255.255.255")) {
            mask = "255.0.0.0";
        }

        if (ipdec > getDecByIp("128.0.0.0") && ipdec < getDecByIp("191.255.255.255")) {
            mask = "255.255.0.0";
        }

        if (ipdec > getDecByIp("192.0.0.0") && ipdec < getDecByIp("223.255.255.255")) {
            mask = "255.255.255.0";
        }

        return mask;
    }

    public static String replaceChar(String str, int pos, String text) {
        return str.substring(0, pos - 1) + text + str.substring(pos, str.length());
    }

}