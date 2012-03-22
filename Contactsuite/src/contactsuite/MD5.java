package contactsuite;

/**
 * Die Klasse MD5 ist f�r die Verschl�sselung der Nutzerpassw�rter zust�ndig.
 * 
 * @author Dominik
 * 
 */
public class MD5 {
	/**
	 * Verschl�sselung des �bergebenen Passworts.
	 * 
	 * @param md5
	 *            : �bergebenes Passwort
	 * @exception NoSuchAlgorithmException
	 * @return sb: verschl�sseltes Passwort wenn die Verschl�sselung erfolgreich
	 *         war, sonst null.
	 */
	public static String getMd5Hash(String md5) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
			ErrorHandler.writeError(e);
		}
		return null;
	}
}
