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

    public static String RSA_EncryptedWithPublicKey(String input){
        SharedPreferences sp = MainApplication.getContext().getSharedPreferences("data",
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

}
