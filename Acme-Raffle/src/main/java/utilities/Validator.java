package utilities;

/*
 * 
 * Clase utilizada para validaciones customizadas
 */
public class Validator {

	
	public static boolean isCorrectPhone(String phone) {
		String ph= phone==null || !phone.contains("+")?null : phone.substring(3, 4);
		return ph==null ? false : !ph.equals(" ");
	}
	
	
}
