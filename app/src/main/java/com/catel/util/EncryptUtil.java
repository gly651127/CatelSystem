package com.catel.util;

import java.security.PublicKey;

/**
 * Created by xieshibin on 2016/12/15.
 */

public class EncryptUtil {

    /* 密钥内容 base64 code */
    private static String PUCLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC5G9R5HzQA5YLtxNhNyTX7" +
            "bduDFVE+RZvHASc5lCYl5SRclyt0TTiDnvc8l5v4lYXruf38IARMri6P5oR5zfhf1lT/AnOhmA/2NNGfXJo4Nx3j" +
            "3Msg/0eTklFqDyVDtb8yW/5h9HkGe0PSYuSw9k1PJz386eyZhC/2tp8T+61e8wIDAQAB";
    /**
     * 加密密码
     */
    public   static String encryptPsw(String psw){
        String decryptStr = "";
        try {
            PublicKey publicKey = RSAUtils.loadPublicKey(PUCLIC_KEY);
            byte[] encryptByte = RSAUtils.encryptData(psw.getBytes(), publicKey);
//            decryptStr = new String(encryptByte);
            decryptStr = Base64Utils.encode(encryptByte);
        } catch (Exception e){
            e.printStackTrace();
        }
        return decryptStr;
    }

}
