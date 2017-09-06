package com.hzastudio.easyshu.support.tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.hzastudio.easyshu.support.universal.MainApplication;

import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

public class SecurityTool {

    /**
     * 进行MD5加密
     * @param input 输入待加密字符串
     * @return 输出加密结果
     * @throws NoSuchAlgorithmException 错误
     */
    public static String MD5(String input) throws NoSuchAlgorithmException
    {
        MessageDigest md5=MessageDigest.getInstance("MD5");
        md5.update(input.getBytes());
        byte[] m=md5.digest();
        StringBuilder ret=new StringBuilder(m.length<<1);
        for (byte aM : m) {
            ret.append(Character.forDigit((aM >> 4) & 0xf, 16));
            ret.append(Character.forDigit(aM & 0xf, 16));
        }
        return ret.toString();
    }

    /**
     * 进行RSA加密
     * @param input 输入待加密字符串
     * @return 输出加密结果
     */
    public static String RSA_EncryptedWithPublicKey(String input){
        SharedPreferences sp = MainApplication.getContext().getSharedPreferences("user",
                Context.MODE_PRIVATE);
        String publicKey = sp.getString("publicKey", null);
        String result="";
        try {
            //TODO:用AES128解密公钥
            byte[] decoded = Base64.decode(publicKey, Base64.DEFAULT);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PublicKey key = factory.generatePublic(spec);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(input.getBytes());
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param Username 用户名
     * @param Password 密码
     * @param RandomString 随机字符串
     * @param TimeStamp 时间戳
     * @return 签名
     * @throws NoSuchAlgorithmException 异常
     */
    public static String SignatureGenerate(String Username,String Password,
                                            String RandomString,String TimeStamp) throws NoSuchAlgorithmException
    {
        String target = Password +
                Username +
                RandomString +
                TimeStamp;

        MessageDigest sha1=MessageDigest.getInstance("SHA1");
        sha1.update(target.getBytes());
        byte[] m=sha1.digest();
        StringBuilder ret=new StringBuilder(m.length<<1);
        for (byte aM : m) {
            ret.append(Character.forDigit((aM >> 4) & 0xf, 16));
            ret.append(Character.forDigit(aM & 0xf, 16));
        }
        return ret.toString();
    }

    /**
     * 生成指定长度的随机字符串
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 自动生成服务器用户校验码
     * @return String[] 校验数据
     * @throws NoSuchAlgorithmException 错误
     */
    public static String[] getUserSignature() throws NoSuchAlgorithmException
    {
        String[] Ret=new String[5];
        SharedPreferences user = MainApplication.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        Ret[0]=user.getString("username","");
        Ret[1]=user.getString("password","");
        Ret[2]=SecurityTool.getRandomString(15);
        Ret[3]=String.valueOf(System.currentTimeMillis());
        Ret[4]=SecurityTool.SignatureGenerate(Ret[0],Ret[1],Ret[2],Ret[3]);
        return Ret;
    }

}
