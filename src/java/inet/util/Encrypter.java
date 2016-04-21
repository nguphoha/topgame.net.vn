package inet.util;

public class Encrypter {

    private static String encryptionKey = "LifeSoft: LifeSoft.com.vn";
    private static String encryptionScheme = StringEncrypter.DES_ENCRYPTION_SCHEME;
    private static StringEncrypter encrypter = null;

    static {
        try {
            encrypter = new StringEncrypter(encryptionScheme, encryptionKey);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String encrypt(String clearText) throws Exception {
        return encrypter.encrypt(clearText);
    }

    public static String decrypt(String encryptedText) throws Exception {
        return encrypter.decrypt(encryptedText);
    }

    public static String encrypt(String clearText, String key) throws Exception {
        StringEncrypter stringEncrypter = new StringEncrypter(encryptionScheme, key);
        return stringEncrypter.encrypt(clearText);
    }

    public static String decrypt(String encryptedText, String key) throws Exception {
        StringEncrypter stringEncrypter = new StringEncrypter(encryptionScheme, key);
        return stringEncrypter.decrypt(encryptedText);
    }

    public static void main(String[] args) throws Exception {
        String text;
        System.out.println(text = Encrypter.encrypt("Trinh Le Tu"));
        System.out.println(Encrypter.decrypt(text));
    }

}
