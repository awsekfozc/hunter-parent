package com.csair.csairmind.hunter.common.util;

import com.csair.csairmind.hunter.common.request.ApiRequest;
import com.csair.csairmind.hunter.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;

/**
 * Created by zhengcheng
 */
@Slf4j
public class ApiUtils {
    /**
     * 对要发送的数据进行签名
     *
     * @param appKey
     * @param appSecret
     * @param parameters 需要签名的参数
     * @return
     */
    public static String signRequest(String appKey, String appSecret, HashMap<String, String> parameters) {
        //赶进度,加密算法先不实现
        //TODO 加密算法实现
        return appKey + "" + appSecret;
    }

    /**
     * 根据泛型创建实例
     *
     * @param request
     * @param <T>
     * @return
     */
    public static <T extends ApiResponse> T newInstance(ApiRequest<T> request) {
        Class cc = request.getClass();
        System.out.println(cc.getSimpleName());
        T rsp;
        try {
            rsp = (T) request.getClass().newInstance();
        } catch (Exception ex) {
            rsp = null;
        }
        return rsp;
    }

    /**
     * 获取本地IP，失败返回空
     *
     * @return
     */
    public static String getLocalIp() {
        try {
            InetAddress ia = InetAddress.getLocalHost();
            return ia.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取mac，失败返回空
     *
     * @return
     */
    public static String getLocalMac() {

        try {
            //获取网卡，获取地址
            InetAddress ia = InetAddress.getLocalHost();
            byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
            log.info("mac数组长度：" + mac.length);
            StringBuffer sb = new StringBuffer("");
            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
                    sb.append("-");
                }
                //字节转换为整数
                int temp = mac[i] & 0xff;
                String str = Integer.toHexString(temp);
//            System.out.println("每8位:"+str);
                if (str.length() == 1) {
                    sb.append("0" + str);
                } else {
                    sb.append(str);
                }
            }
            log.info("本机MAC地址:" + sb.toString().toUpperCase());
            return sb.toString().toUpperCase();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) throws Exception {
    }

}
