package com.example.demo.configuration;

import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Component
public class KeyReader {

    private final String publicKeyAsString;

    public KeyReader(@Value("${jwt.validation.key}") String publicKeyAsString) {
        this.publicKeyAsString = publicKeyAsString;
    }

    @Bean
    public KeyPair keyPair(@Value("${jwt.sign.key}") String privateKeyAsString)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(KeyReader.loadKey(privateKeyAsString));
        RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpecPKCS8);

        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(KeyReader.loadKey(publicKeyAsString));
        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpecX509);

        return new KeyPair(publicKey, privateKey);
    }

    @Bean
    public RsaVerifier rsaVerifier() {
        return new RsaVerifier(publicKeyAsString);
    }

    private static byte[] loadKey(String key) {
        try (PemReader pemReader = new PemReader(new InputStreamReader(new ByteArrayInputStream(key.getBytes())))) {
            return pemReader.readPemObject().getContent();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read private key", e);
        }
    }
}
