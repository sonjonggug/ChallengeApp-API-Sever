package com.upside.api.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 패스워드 암호화 함수
 * @author user
 *
 */
public class Encrypt { 

 		public static String sha2String(String inputStr) {

 			if (inputStr == null)
 				return null;

 			MessageDigest clsSha2;

 			try {

 				clsSha2 = MessageDigest.getInstance("SHA-256");
 				clsSha2.update(inputStr.getBytes());

 			} catch (NoSuchAlgorithmException e) {
 				return null;
 			}

 			byte[] arrBuf = clsSha2.digest();

 			int iLen = arrBuf.length;

 			StringBuffer clsBuffer = new StringBuffer();

 			for (int i = 0; i < iLen; i++) {
 				clsBuffer.append(String.format("%02x", 0xFF & arrBuf[i]));
 			}

 			return clsBuffer.toString();
 		}

 		public static String sha2StringLowerCase(String inputStr) {
 			return sha2String(inputStr).toLowerCase();
 		}
 	}


