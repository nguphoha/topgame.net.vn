package inet.util;

import java.security.InvalidKeyException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;



public class StringEncrypter {

    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    public static final String DES_ENCRYPTION_SCHEME = "DES";
    public static final String DEFAULT_ENCRYPTION_KEY = "This is a fairly long phrase used to encrypt";

    private KeySpec keySpec;
    private SecretKeyFactory keyFactory;
    private Cipher cipher;


    public StringEncrypter(String encryptionScheme) throws Exception {
        this(encryptionScheme, DEFAULT_ENCRYPTION_KEY);
    }

    public StringEncrypter(String encryptionScheme, String encryptionKey) throws Exception {

        if (encryptionKey == null)
            throw new IllegalArgumentException("encryption key was null");
        if (encryptionKey.trim().length() < 10)
            throw new IllegalArgumentException(
                "encryption key was less than 10 characters");
        try {
            byte[] keyAsBytes = encryptionKey.getBytes("UTF8");

            if (encryptionScheme.equals(DESEDE_ENCRYPTION_SCHEME)) {
                keySpec = new DESedeKeySpec(keyAsBytes);
            } else if (encryptionScheme.equals(DES_ENCRYPTION_SCHEME)) {
                keySpec = new DESKeySpec(keyAsBytes);
            } else {
                throw new IllegalArgumentException("Encryption scheme not supported: " + encryptionScheme);
            }

            keyFactory = SecretKeyFactory.getInstance(encryptionScheme);
            cipher = Cipher.getInstance(encryptionScheme);

        } catch (InvalidKeyException e) {
        	throw e;
        }

    }

    public String encrypt(String unencryptedString) throws Exception {
        if (unencryptedString == null || unencryptedString.trim().length() == 0)
            throw new IllegalArgumentException("unencrypted string was null or empty");
        try {
            SecretKey key = keyFactory.generateSecret(keySpec);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] cleartext = unencryptedString.getBytes("UTF8");
            byte[] ciphertext = cipher.doFinal(cleartext);

            BASE64Encoder base64encoder = new BASE64Encoder();
            return base64encoder.encode(ciphertext);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public String decrypt(String encryptedString) throws Exception {
        if (encryptedString == null || encryptedString.trim().length() <= 0)
            throw new IllegalArgumentException("encrypted string was null or empty");

        try {
            SecretKey key = keyFactory.generateSecret(keySpec);
            cipher.init(Cipher.DECRYPT_MODE, key);
            BASE64Decoder base64decoder = new BASE64Decoder();
            byte[] cleartext = base64decoder.decodeBuffer(encryptedString);
            byte[] ciphertext = cipher.doFinal(cleartext);
            return new String(ciphertext, "UTF8");

        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
