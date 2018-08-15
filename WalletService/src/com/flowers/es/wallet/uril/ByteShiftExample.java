package com.flowers.es.wallet.uril;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.SecretKey;
import java.io.*;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class ByteShiftExample {

	/**
	 * The {@link Cipher} used to encrypt the data.
	 */
	private static Cipher enCipher = null;

	/**
	 * The {@link Cipher} used to decrypt the data.
	 */
	private static Cipher deCipher = null;

	/**
	 * The private key that will be used. 24 bytes in length.
	 */
	private static byte[] baOurKey = null;

	/**
	 * The initialization vector that will be used. 8 bytes in length.
	 */
	private static IvParameterSpec ourIPS = null;

	/**
	 * The {@link SecretKey} is used to store the key data.
	 */

	private static SecretKey secretKey = null;
	
	
	public static String decryptCard(String srKey, String strCardNumber ) {
		String decryptedCard = "FALSE";
		//String strKey = PicUtil.getCryptoKey(strSourceId);
		decryptedCard = decrypt18FCard(strCardNumber,srKey);		
		return decryptedCard;
	}
	
	public static String encryptCard(String strKey, String strCardNumber ) {
		String encryptedCard = strCardNumber;
		
					encryptedCard = encrypt18FCard(strCardNumber,strKey);
				
		
		return encryptedCard;
	}
	public static String decrypt18FCard(String strCardNumber,String strKey) {
		String decryptedCard = "FALSE";
		try{
			if (strKey != null){
				removeKey();
				setKey(strKey.getBytes());
				byte[] iv = {0x01, 0x23,0x45, 0x67, (byte)0x89, (byte)0xab, (byte)0xcd, (byte)0xef};
				setIV(iv);
				decryptedCard = convertBack(byteToHex(decrypt(hexToByte(strCardNumber))));
			}
		}catch(Exception ex){
			decryptedCard ="FALSE";
		}
		return decryptedCard;
	}
	public static String encrypt18FCard(String strCardNumber,String strKey) {
		String decryptedCard = "FALSE";
		try{
			if (strKey != null){
				removeKey();
				setKey(strKey.getBytes());
				byte[] iv = {0x01, 0x23,0x45, 0x67, (byte)0x89, (byte)0xab, (byte)0xcd, (byte)0xef};
				setIV(iv);
				decryptedCard = byteToHex(encrypt(hexToByte(convertSafe(strCardNumber))));
			}
		}catch(Exception ex){
			decryptedCard ="FALSE";
		}
		return decryptedCard;
	}

	 public static void removeKey()
	    {
	    	secretKey = null;
	    }
	public static String decrypt18FCards(String str) {
		//boolean bEncrypting = args[0].matches("[Ee][Nn][Cc]");
		String decryptedCard = "";
		boolean bEncrypting = false;
		setKey("18F-SuperSekritKeyValue!".getBytes());
		byte[] iv = {0x01, 0x23,0x45, 0x67, (byte)0x89, (byte)0xab, (byte)0xcd, (byte)0xef};
		setIV(iv);
		try {
			//FileInputStream fstream = new FileInputStream( "//tmp//encrypt//18fcrypto//output.txt");
			//FileInputStream fstream = new FileInputStream( "//tmp//encrypt//18fcrypto//input.txt");
			//FileInputStream fstream = new FileInputStream( "C:\\TOKEN_WSDL_TEST\\Encryptiontest\\test\\input.txt");
			FileInputStream fstream = new FileInputStream( "C:\\TOKEN_WSDL_TEST\\Encryptiontest\\test\\output.txt");
			//File file = new File("C:\\TOKEN_WSDL_TEST\\Encryptiontest\\test\\output.txt");
			//File file = new File("C:\\TOKEN_WSDL_TEST\\Encryptiontest\\test\\output.txt");
			//FileWriter fw = new FileWriter(file.getName());
			//BufferedWriter bw = new BufferedWriter(fw);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				String crdCard = strLine;
				//System.out.println("CC number from file :" + crdCard);
				if (bEncrypting && (crdCard.length() == 0 || !(crdCard.matches("([0-9]+)+")))) {
					System.out.println("Credit Card Invalid");
					//bw.write("Credit Card Invalid");
					//bw.newLine();

				} else {
						String result = null;
					if (bEncrypting) {
						result = byteToHex(encrypt(hexToByte(convertSafe(crdCard))));
					}
					else {
						//encrypt = convertBack(byteToHex((hexToByte(crdCard))));
						result = convertBack(byteToHex(decrypt(hexToByte(crdCard))));
					}
					//System.out.println(strLine+"\t\t");
					System.out.println(result);
					decryptedCard = result;
					//bw.write(result);
					//bw.newLine();

				}
			}

			//bw.close();
			fstream.close();
			return decryptedCard;
		} catch (FileNotFoundException e) {
			System.err.println("FileNotFoundException: " + e.getMessage());
		}
		catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		return "";
	}

	private static boolean init() {
		boolean bRet = true;
		try {
			secretKey = new SecretKeySpec(baOurKey, "DESede");
			enCipher = Cipher.getInstance("DESede/CBC/NoPadding");
			deCipher = Cipher.getInstance("DESede/CBC/NoPadding");
		}
		catch (Exception ex) {
			//Log.add(Level.SEVERE, "Could not init cryptography: " + ex.toString());
			bRet = false;
		}
		return bRet;
	}

	public static byte[] encrypt(byte[] plainText) {
		if (secretKey == null && !init()) {
			return null;
		}

		byte[] byteCipherText = null;
		try {
			enCipher.init(Cipher.ENCRYPT_MODE, secretKey, ourIPS);
			byteCipherText = enCipher.doFinal(plainText);
		} catch (InvalidKeyException invalidKey) {
			System.err.println(" Invalid Key " + invalidKey);
		} catch (IllegalBlockSizeException illegalBlockSize) {
			System.err.println(" Illegal Bolck Size " + illegalBlockSize);
		} catch (InvalidAlgorithmParameterException illegalBlockSize) {
			System.err.println(" Invalid Algorithm Parameter " + illegalBlockSize);
		} catch (BadPaddingException illegalBlockSize) {
			System.err.println(" Bad Padding " + illegalBlockSize);
		}
		return byteCipherText;
	}

	public static byte[] decrypt(byte[] cipherText) {
		if (secretKey == null && !init()) {
			return null;
		}

		byte[] byteClearText = null;
		try {
			deCipher.init(Cipher.DECRYPT_MODE, secretKey, ourIPS);
			byteClearText = deCipher.doFinal(cipherText);
		} catch (InvalidKeyException invalidKey) {
			System.err.println(" Invalid Key " + invalidKey);
		} catch (IllegalBlockSizeException illegalBlockSize) {
			System.err.println(" Illegal Bolck Size " + illegalBlockSize);
		} catch (InvalidAlgorithmParameterException illegalBlockSize) {
			System.err.println(" Invalid Algorithm Parameter " + illegalBlockSize);
		} catch (BadPaddingException illegalBlockSize) {
			System.err.println(" Bad Padding " + illegalBlockSize);
		}
		return byteClearText;
	}



	public static void setKey(byte[] baKey) {
		baOurKey = baKey;
	}

	public static void setIV(byte[] baIV) {
		ourIPS = new IvParameterSpec(baIV);
	}

	private static String byteToHex(byte[] in) {

            byte ch = 0x00;
            if (in == null || in.length <= 0)
                return null;

            String pseudo[] = {"0", "1", "2","3", "4", "5", "6", "7", "8","9", "A", "B", "C", "D", "E","F"};
            StringBuffer out = new StringBuffer(in.length * 2);

            for (int i = 0; i < in.length; i++) {
				//System.out.println("0:" + in[i]);
                ch = (byte) (in[i] & 0xF0); // Strip off high nibble
				//System.out.println("1:" + ch);
                ch = (byte) (ch >>> 4);      // shift the bits down
                ch = (byte) (ch & 0x0F); // Strip off low nibble
				//System.out.println("2:" + ch);

                out.append(pseudo[(int) ch]); // convert the nibble to a String Character
                ch = (byte) (in[i] & 0x0F); // Strip off low nibble
				//System.out.println("3:" + ch);
                out.append(pseudo[(int) ch]); // convert the nibble to a String Character
            }

            String rslt = new String(out);
			//System.out.println("byteToHex() = " + rslt);
            return rslt;
        }

	private static byte[] hexToByte(String creditCardData) {
		byte[] creditCardByte = creditCardData.getBytes();
		byte[] creditCardByteOut = new byte[8];
		int tempCnt = 0;
		for (int cnt = 0; cnt < creditCardData.length(); cnt += 2) {
			creditCardByte[cnt]  = (byte)(creditCardByte[cnt] - (creditCardByte[cnt] < 58 ? 48 : 55));
			creditCardByte[cnt+1]  = (byte)(creditCardByte[cnt+1] - (creditCardByte[cnt+1] < 58 ? 48 : 55));
			creditCardByteOut[tempCnt++] = (byte) ((creditCardByte[cnt] << 4) + creditCardByte[cnt+1]);
		}
		//System.out.println("hexToByte() = " + new String(creditCardByteOut));
		return creditCardByteOut;
	}

	//  Convert '0' to 'A' and pad string to 16 characters with 'B'
	private static String convertSafe(String creditCardData) {
		if (creditCardData.length() < 16) {
			creditCardData += "BBBBBBBBBBBBBBBBBBBBB";
		}
		creditCardData = creditCardData.substring(0, 16);
		//System.out.println("convertSafe() = " + creditCardData.replace('0', 'A'));
		return creditCardData.replace('0', 'A');
	}

	//  Convert A to 0 and trim any B's from end of string.
	private static String convertBack(String in) {
		//System.out.println("convertBack() = " + in.replace('A', '0').replace('B', ' ').trim());
		return in.replace('A', '0').replace('B', ' ').trim();
	}

}
