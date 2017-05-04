package com.shxzhlxrr.util.md5;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5 工具类
 *
 * @author zxr
 */
public class Md5Util {
    /**
     * 获取文件的md5 值
     *
     * @param path 文件的路径
     * @return 文件的md5 ，全部转换成大写的
     */
    public static String getFileMD5(String path) {

        return "";
    }

    /**
     * 获取文件的md5 值
     *
     * @param file 要获取md5值的对象
     * @return 文件的md5 ，全部转换成大写的
     */
    public static String getFileMD5(File file) {
        String value = null;
        FileInputStream in = null;
        try {

            if (!file.exists()) {
                throw new RuntimeException(file.getAbsolutePath() + "不存在，无法获取md5值");
            }

            in = new FileInputStream(file);
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
            value = value.toUpperCase();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    public static String fileMD5(String inputFile) throws IOException {
        // 缓冲区大小（这个可以抽出一个参数）
        int bufferSize = 256 * 1024;
        FileInputStream fileInputStream = null;
        DigestInputStream digestInputStream = null;
        try {
            // 拿到一个MD5转换器（同样，这里可以换成SHA1）
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // 使用DigestInputStream
            fileInputStream = new FileInputStream(inputFile);
            digestInputStream = new DigestInputStream(fileInputStream, messageDigest);
            // read的过程中进行MD5处理，直到读完文件
            byte[] buffer = new byte[bufferSize];
            while (digestInputStream.read(buffer) > 0)
                ;
            // 获取最终的MessageDigest
            messageDigest = digestInputStream.getMessageDigest();
            // 拿到结果，也是字节数组，包含16个元素
            byte[] resultByteArray = messageDigest.digest();
            // 同样，把字节数组转换成字符串
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                digestInputStream.close();
            } catch (Exception e) {
            }
            try {
                fileInputStream.close();
            } catch (Exception e) {
            }
        }
    }

    public static String byteArrayToHex(byte[] byteArray) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < byteArray.length; n++) {
            stmp = (Integer.toHexString(byteArray[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
            if (n < byteArray.length - 1) {
                hs = hs + "";
            }
        }
        // return hs.toUpperCase();
        return hs.toUpperCase();

        // 首先初始化一个字符数组，用来存放每个16进制字符

		/*
         * char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9',
		 * 'A','B','C','D','E','F' };
		 * 
		 * 
		 * 
		 * //
		 * new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方）
		 * ）
		 * 
		 * char[] resultCharArray =new char[byteArray.length * 2];
		 * 
		 * // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
		 * 
		 * int index = 0;
		 * 
		 * for (byte b : byteArray) {
		 * 
		 * resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];
		 * 
		 * resultCharArray[index++] = hexDigits[b& 0xf];
		 * 
		 * }
		 * 
		 * // 字符数组组合成字符串返回
		 * 
		 * return new String(resultCharArray);
		 */

    }

    public static String stringMD5(String input) {
        try {
            // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // 输入的字符串转换成字节数组
            byte[] inputByteArray = input.getBytes();
            // inputByteArray是输入字符串转换得到的字节数组
            messageDigest.update(inputByteArray);
            // 转换并返回结果，也是字节数组，包含16个元素
            byte[] resultByteArray = messageDigest.digest();
            // 字符数组转换成字符串返回
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

//    public static void main(String[] args) {
////        String path = "C:/Users/zxr/Downloads/haozip_v5.9.exe";
////        try {
////            long start = System.currentTimeMillis();
////            String md5Val = fileMD5(path);
////            long end = System.currentTimeMillis();
////            System.out.println((end - start));
////            System.out.println(md5Val);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//    	
//    	String pwdmd5 = getPwdMd5("123");
//    	System.out.println(pwdmd5);
//    }
    
    /**
     * 获取密码对应的md5
     * @param pwd
     * @return
     */
    public static String getPwdMd5(String pwd){
    	String pwdmd5 = stringMD5(pwd+"smile");
    	return stringMD5(pwdmd5.substring(0, pwdmd5.length()-4));
    }


}
