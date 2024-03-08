/*
 * Basic implementation of Bloom Filter data structure
 */

package datastructure;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BloomFilter {
    //long = 64 bits
    private long dataHolder;
    private final BigInteger numberOfBits = BigInteger.valueOf(64L);
    private MessageDigest sha160;
    private MessageDigest sha256;
    private MessageDigest sha512;
    
    public BloomFilter() {
        //Fill dataHolder with bit values to zero
        dataHolder = 0;
        
        try {
            sha160 = MessageDigest.getInstance("SHA-1");
            sha512 = MessageDigest.getInstance("SHA-512");
            sha256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            //This exception will never be thrown
            e.printStackTrace();
        }
    }
    
    public void add(byte[] element) {
        byte[] result160 = sha160.digest(element);
        byte[] result256 = sha256.digest(element);
        byte[] result512 = sha512.digest(element);
        
        BigInteger mod160 = new BigInteger(1, result160).mod(numberOfBits);
        BigInteger mod256 = new BigInteger(1, result256).mod(numberOfBits);
        BigInteger mod512 = new BigInteger(1, result512).mod(numberOfBits);
        dataHolder |= (long)0x01 << mod160.longValue();
        dataHolder |= (long) 0x01 << mod256.longValue();
        dataHolder |= (long) 0x01 << mod512.longValue();
    }
    
    public boolean contains(byte[] element) {
        byte[] result160 = sha160.digest(element);
        byte[] result256 = sha256.digest(element);
        byte[] result512 = sha512.digest(element);
        
        BigInteger mod160 = new BigInteger(1, result160).mod(numberOfBits);
        BigInteger mod256 = new BigInteger(1, result256).mod(numberOfBits);
        BigInteger mod512 = new BigInteger(1, result512).mod(numberOfBits);
        
        return (((dataHolder >>> mod160.longValue()) & 1) == 1) &&
                (((dataHolder >>> mod256.longValue()) & 1) == 1) &&
                (((dataHolder >>> mod512.longValue()) & 1) == 1);
    }
    
    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        for (int i = 63; i >= 0; i--) {
            tmp.append((dataHolder >>> i) & 1);
        }
        return tmp.toString();
    }
}
