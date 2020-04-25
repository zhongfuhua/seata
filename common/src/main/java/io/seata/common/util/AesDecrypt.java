/*
 *  Copyright 1999-2019 Seata.io Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.seata.common.util;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: <br>
 *
 * @author chen.fengjun <br>
 * @version v1.0<br>
 * @date 2018/12/3 <br>
 * @see com.gigrt.cxm.core.security <br>
 */
public class AesDecrypt {

  private static Logger logger = LoggerFactory.getLogger(AesDecrypt.class);
  private static String key = "zK8Gv+ycuzumesWziXH4yA==";

  /**
   * 自动生成AES128位密钥
   */
  private static String generateKey() {
    String key = null;
    try {
      KeyGenerator kg = KeyGenerator.getInstance("AES");
      kg.init(128);
      SecretKey sk = kg.generateKey();
      byte[] b = sk.getEncoded();
      key = Base64.getEncoder().encodeToString(b);
    } catch (NoSuchAlgorithmException e) {
      logger.warn("warn", e);
    }
    return key;

  }

  /**
   * 加密
   *
   * @param content String
   * @return byte[]
   */
  private static byte[] encrypt(String content) throws Exception {
    SecretKeySpec skeySpec = new SecretKeySpec(Base64.getDecoder().decode(key), "AES");
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
    cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
    byte[] encrypted = cipher.doFinal(content.getBytes());
    return encrypted;
  }

  /**
   * 解密
   *
   * @param content String
   * @return String
   */
  private static String decrypt(byte[] content) throws Exception {
    SecretKeySpec skeySpec = new SecretKeySpec(Base64.getDecoder().decode(key), "AES");
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
    cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
    byte[] original = cipher.doFinal(content);
    return new String(original);
  }


  /**
   * AES加密为base 64 code
   *
   * @param content 待加密的内容
   * @return 加密后的base 64 code
   * @throws Exception //加密传String类型，返回String类型
   */
  public static String aesEncrypt(String content) throws Exception {
    return Base64.getEncoder().encodeToString(encrypt(content));
  }

  /**
   * 将base 64 code AES解密
   *
   * @param encryptStr 待解密的base 64 code
   * @return 解密后的string   //解密传String类型，返回String类型
   */
  public static String aesDecrypt(String encryptStr) throws Exception {
    return StringUtils.isBlank(encryptStr) ? null : decrypt(Base64.getDecoder().decode(encryptStr));
  }


  public static void main(String[] args) throws Exception {
    System.out.println("userdev:" + aesDecrypt("9uJk4Gl6JKjjsEts3MXcESBFzoTRlmAwlkxnShrJnis="));
    System.out.println("userdev:" + aesEncrypt("whgjCcRx6GEHmPHDufbdoJzQ"));


  }


}
