package SecureSD.EcryptionAssignment;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.*;
import javax.crypto.*;


public class Encrypt {
    public static void main(String[] args) throws Exception {

        // declare the file
        File file = new File("text.txt");

        // prompt the user and store their input
        System.out.println("Enter a string between 5 and 20 characters");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.next();


        // make sure user input if of valid length
        if (str.length() > 20 || str.length() < 5){
            System.out.println("Please enter a string of the correct length");
            System.exit(0);
        }
        else {

            // Declare encryption algorithm and mode
            String algorithm = "AES";
            String mode = "AES/ECB/PKCS5Padding";

            // create a key to use with declared encryption algorithm
            SecretKey secretKey = secret.key(algorithm);

            // create a cipher object using the secret key and encryption mode
            Cipher cipher = Cipher.getInstance(mode);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // use the created cipher to encrypt the data.
            byte[] encryptedBytes = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
            String encryptedBase64 = Base64.getEncoder().encodeToString(encryptedBytes);

            // print encrypted results to file
            PrintWriter write = new PrintWriter("text.txt");
            write.println(encryptedBase64);
            write.close();

            // reads from file
            Scanner fileRDR = new Scanner(file);
            String fileRead = "";
            while (fileRDR.hasNext()){
                fileRead = fileRDR.next();
            }

            // show the decoded encrypted data
            String encrypted = new String(Base64.getDecoder().decode(encryptedBase64.getBytes()));
            System.out.println("Decoded encrypted data: " + encrypted);
            // show the encrypted data that was written to the file
            System.out.println("Encrypted data from File: " + fileRead);

            // print the decrypted data.
            System.out.println("Original Text: " + decrypt(fileRead, secretKey));

        }



    }


    /**
     * decrypt the encrypted data.
     * @param str The encrypted text
     * @param key The stored secret key
     * @return
     * @throws Exception
     */
    public static String decrypt(String str, SecretKey key)
            throws Exception {
        // create a cipher and decoder with AES alg
        Cipher cipher = Cipher.getInstance("AES");
        Base64.Decoder decoder = Base64.getDecoder();

        // byte array to hold encrypted data
        byte[] encryptByte = decoder.decode(str);

        // decrypt the cipher using the secret key and store results to byte array
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptByte = cipher.doFinal(encryptByte);
        // convert the decrypted byte array into a readable string and return it
        return new String(decryptByte);
    }

}
