package com.mib.simpilist.service.Security;

import com.mib.simpilist.utililty.Constants;
import com.mib.simpilist.utililty.Utilities;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Service
public class PasswordService {

    private final Integer saltBytesLength;
    private final Integer pbkdf2Iterations;
    private final Integer pbkdf2Bits;
    private final String pbkdf2Algorithm;
    private final SecureRandom secureRandom;

    public PasswordService(@Value("${security.salt-bytes-length}")Integer saltBytesLength,
                           @Value("${security.PBKDF2-iterations}")Integer pbkdf2Iterations,
                           @Value("${security.PBKDF2-bits}")Integer pbkdf2Bits,
                           @Value("${security.PBKDF2-algorithm}")String pbkdf2Algorithm,
                           SecureRandom secureRandom){
        this.secureRandom=secureRandom;
        this.pbkdf2Bits=pbkdf2Bits;
        this.pbkdf2Iterations=pbkdf2Iterations;
        this.pbkdf2Algorithm=pbkdf2Algorithm;
        this.saltBytesLength=saltBytesLength;
    }

    public boolean verifyPassword(String password, String storedSalt, String storedHash) {
        try {
            byte[] saltBytes = Base64.getDecoder().decode(storedSalt);
            int iterations = pbkdf2Iterations;
            int keyLength = pbkdf2Bits;

            KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, iterations, keyLength);
            byte[] hashBytes = SecretKeyFactory.getInstance(pbkdf2Algorithm)
                    .generateSecret(spec)
                    .getEncoded();
            String loginHash = Base64.getEncoder().encodeToString(hashBytes);
            // Constant-time comparison to prevent timing attacks
            return MessageDigest.isEqual(loginHash.getBytes(StandardCharsets.UTF_8),
                    storedHash.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException("Error verifying password", e);
        }
    }

    public Pair<String,String> generateSaltAndSaltedHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] saltBytes = new byte[saltBytesLength];
        secureRandom.nextBytes(saltBytes);

        int iterations = pbkdf2Iterations;
        int keyLength = pbkdf2Bits;

        KeySpec spec = new PBEKeySpec(password.toCharArray(),
                saltBytes,
                iterations,
                keyLength);
        byte[] hash = SecretKeyFactory
                .getInstance(pbkdf2Algorithm)
                .generateSecret(spec)
                .getEncoded();

        String saltedHash = Base64
                .getEncoder()
                .encodeToString(hash);

        String salt = Base64
                .getEncoder()
                .encodeToString(saltBytes);

        return Pair.of(salt,saltedHash);
    }
}
