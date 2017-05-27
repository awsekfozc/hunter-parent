package com.csair.csairmind.hunter.common.util;

import org.apache.commons.lang3.StringUtils;
import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;
import sun.security.provider.MD5;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by ZC on 2016/5/9.
 * sim算法，比较文本相似度。4以内为相识度很高的文本。
 * 生成64位签名。用于去重操作
 * 中文采用IKAnalyzer分词
 */

public class SimHash {

    private String tokens;


    private BigInteger intSimHash;


    private String strSimHash;

    private int hashbits = 64;

    public String getStrSimHash() {
        return strSimHash;
    }

    public SimHash(String tokens) throws IOException {
        this.tokens = tokens
                .replace("0", "a")
                .replace("1", "b")
                .replace("2", "c")
                .replace("3", "d")
                .replace("4", "e")
                .replace("5", "f")
                .replace("6", "g")
                .replace("7", "h")
                .replace("8", "i")
                .replace("9", "j")
                .replace("0", "k");
        this.intSimHash = this.simHash();
    }

    HashMap<String, Integer> wordMap = new HashMap<String, Integer>();

    //生成签名
    public BigInteger simHash() throws IOException {
        // 定义特征向量/数组，这里选择默认64位
        int[] v = new int[this.hashbits];
        // 1、中文分词，分词器采用 IKAnalyzer
        StringReader reader = new StringReader(this.tokens);
        // 当为true时，分词器进行最大词长切分
        IKSegmentation ik = new IKSegmentation(reader, true);
        Lexeme lexeme = null;
        String word = null;
        String temp = null;
        while ((lexeme = ik.next()) != null) {
            word = lexeme.getLexemeText();
            // 注意停用词会被干掉
            // 2、将每一个分词hash为一组固定长度的数列.比如 64bit 的一个整数.
            BigInteger t = this.hash(word);
            for (int i = 0; i < this.hashbits; i++) {
                BigInteger bitmask = new BigInteger("1").shiftLeft(i);
                // 3、建立一个长度为64的整数数组(假设要生成64位的数字指纹,也可以是其它数字),
                // 对每一个分词hash后的数列进行判断,如果是1000...1,那么数组的第一位和末尾一位加1,
                // 中间的62位减一,也就是说,逢1加1,逢0减1.一直到把所有的分词hash数列全部判断完毕.
                if (t.and(bitmask).signum() != 0) {
                    // 这里是计算整个文档的所有特征的向量和
                    // 这里实际使用中需要 +- 权重，比如词频，而不是简单的 +1/-1，
                    v[i] += 1;
                } else {
                    v[i] -= 1;
                }
            }
        }

        BigInteger fingerprint = new BigInteger("0");
        StringBuffer simHashBuffer = new StringBuffer();
        for (int i = 0; i < this.hashbits; i++) {
            // 4、最后对数组进行判断,大于0的记为1,小于等于0的记为0,得到一个 64bit 的数字指纹/签名.
            simHashBuffer.append(v[i]);
//            if (v[i] >= 0) {
//                fingerprint = fingerprint.add(new BigInteger("1").shiftLeft(i));
//                simHashBuffer.append("1");
//            } else {
//                simHashBuffer.append("0");
//            }
        }
        this.strSimHash = simHashBuffer.toString();
        return fingerprint;
    }

    //局部hash
    private BigInteger hash(String source) {
        if (source == null || source.length() == 0) {
            return new BigInteger("0");
        } else {
            char[] sourceArray = source.toCharArray();
            BigInteger x = BigInteger.valueOf(((long) sourceArray[0]) << 7);
            BigInteger m = new BigInteger("1000003");
            BigInteger mask = new BigInteger("2").pow(this.hashbits).subtract(new BigInteger("1"));
            for (char item : sourceArray) {
                BigInteger temp = BigInteger.valueOf((long) item);
                x = x.multiply(m).xor(temp).and(mask);
            }
            x = x.xor(new BigInteger(String.valueOf(source.length())));
            if (x.equals(new BigInteger("-1"))) {
                x = new BigInteger("-2");
            }
            return x;
        }
    }

    public int hammingDistance(SimHash other) {

        BigInteger x = this.intSimHash.xor(other.intSimHash);
        int tot = 0;

        // 统计x中二进制位数为1的个数
        // 一个二进制数减去1，那么，从最后那个1（包括那个1）后面的数字全都反了，
        // 然后，n&(n-1)就相当于把后面的数字清0，
        // 看n能做多少次这样的操作就OK了。

        while (x.signum() != 0) {
            tot += 1;
            x = x.and(x.subtract(new BigInteger("1")));
        }
        return tot;
    }

    public int getDistance(String str1, String str2) {
        int distance;
        if (str1.length() != str2.length()) {
            distance = -1;
        } else {
            distance = 0;
            for (int i = 0; i < str1.length(); i++) {
                if (str1.charAt(i) != str2.charAt(i)) {
                    distance++;
                }
            }
        }
        return distance;
    }

    public List subByDistance(SimHash simHash, int distance) {
        // 分成几组来检查
        int numEach = this.hashbits / (distance + 1);
        List characters = new ArrayList();
        StringBuffer buffer = new StringBuffer();

        int k = 0;
        for (int i = 0; i < this.intSimHash.bitLength(); i++) {
            // 当且仅当设置了指定的位时，返回 true
            boolean sr = simHash.intSimHash.testBit(i);

            if (sr) {
                buffer.append("1");
            } else {
                buffer.append("0");
            }

            if ((i + 1) % numEach == 0) {
                // 将二进制转为BigInteger
                BigInteger eachValue = new BigInteger(buffer.toString(), 2);
                //System.out.println("----" + eachValue);
                buffer.delete(0, buffer.length());
                characters.add(eachValue);
            }
        }
        return characters;
    }

    public static String getSimCode(String str) {
        if (StringUtils.isNotBlank(str)) {
            String reCode = "";
            try {
                SimHash h1 = new SimHash(str);
                reCode = Md5Code.md5ByHex(h1.getStrSimHash());
            } catch (IOException ex) {

            }
            return reCode;
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        SimHash h1 = new SimHash("否,201606--0999!,50年,厂房lll ,宿舍,厂房1,宿舍1,5956.31,3383.02,坪地街道富坪路同富裕工业区,3963.58,深圳市祥地实业有限公司,G10307-0137,4层,6层,2016-12-23,");
        SimHash h2 = new SimHash("否，3383.02,201606--0999!,50年,厂房lll,宿舍,厂房1,宿舍1,5956.31,坪地街道富坪路同富裕工业区,3963.58,深圳市祥地实业有限公司,G10307-0137,4层,6层,2016-12-23,");
        System.out.println(h1.getStrSimHash());
        System.out.println(h2.getStrSimHash());
        System.out.println(h1.getStrSimHash());
        System.out.println(h2.getStrSimHash());
        System.out.println(h1.hammingDistance(h2));
        System.out.println(h1.subByDistance(h2, 3));
        System.out.println(getSimCode("sdsdqweqwe"));
    }
}