package com.flowers.es.wallet.uril;

import java.time.Duration;
import java.time.ZonedDateTime;

public class WalletUtil {

	public static void main(String[] args) {
		//System.out.print(isValidCCNumber("420011000000000000"));
		ZonedDateTime first = ZonedDateTime.now();
		/*try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		ZonedDateTime second = ZonedDateTime.now();
		getProcessTime(first, second);

	}
	public static String getProcessTime(ZonedDateTime first,ZonedDateTime second){
		//ZonedDateTime now = ZonedDateTime.now();
		//ZonedDateTime oldDate = now.minusDays(1).minusMinutes(10);
		Duration duration = Duration.between(first, second);
		System.out.println("ISO-8601: " + duration);
		System.out.println("Millies: " + duration.toMillis());
		return duration.toMillis()+"";
	}
	public static String getIPGTokenRequestXML(String strCard){
		String str_reqXML = "";
		str_reqXML = "<TKN><TGP>1800FLOWERSTKN</TGP><ACN>"+strCard+"</ACN><PMT>CARD</PMT></TKN>";
		return  str_reqXML ;
	}
	public static boolean isValidCCNumber(String number) {

		if (!number.matches("^\\d{13,19}$")) {
			//System.out.println(" is not a valid credit card number (must be 13-19 digits)");
			//logger.log(Level.WARN, " CC number is not a valid credit card number (must be 13-19 digits");
			return false;
		} 
		int sum = 0;

		boolean alternate = false;
		for (int i = number.length() - 1; i >= 0; i--) {
			int n = Integer.parseInt(number.substring(i, i + 1));
			if (alternate) {
				n *= 2;
				if (n > 9) {
					n = (n % 10) + 1;
				}
			}
			sum += n;
			alternate = !alternate;
		}
		if((sum % 10 == 0)==true){
			return true;
		}
		else{
			return false;
		}
	}
}
