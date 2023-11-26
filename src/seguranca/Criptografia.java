package seguranca;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Criptografia {
	/*
	 * Método para criptografar uma mensagem através de uma chave
	 */
    public static String criptografa(String data, SecretKey chave) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, chave);

        // Criptografar os dados
        byte[] dadosCriptografados = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

        // Converter os dados criptografados para Base64 para facilitar a representação
        return Base64.getEncoder().encodeToString(dadosCriptografados);
    }

    /*
     * Método para decriptografar uma mensagem criptografada através de uma chave
     */
    public static String decriptografa(String dataCriptografada, SecretKey chave) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, chave);

        // Decodificar os dados criptografados de Base64 para obter o array de bytes original
        byte[] decodedData = Base64.getDecoder().decode(dataCriptografada);

        // Descriptografar os dados
        byte[] decryptedBytes = cipher.doFinal(decodedData);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    /*
     * Método para gerar uma SecretKey (chave) a partir de uma chave no tipo String
     */
    public static SecretKey criarChaveSecreta(String chave) {
        byte[] chaveBytes = chave.getBytes(StandardCharsets.UTF_8);
        // Limitando a chave para 8 bytes (64 bits) para DES
        byte[] chaveBytes8 = new byte[8];
        System.arraycopy(chaveBytes, 0, chaveBytes8, 0, Math.min(chaveBytes.length, 8));

        // Convertendo os bytes da chave para um SecretKey usando SecretKeySpec
        return new SecretKeySpec(chaveBytes8, "DES");
    }
}
