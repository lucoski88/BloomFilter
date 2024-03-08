import datastructure.BloomFilter;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        BloomFilter bf = new BloomFilter();
        bf.add("Bloom Filter".getBytes(StandardCharsets.UTF_8));
        
        System.out.println(bf.contains("Bloom Filter".getBytes(StandardCharsets.UTF_8))); //true
        System.out.println(bf.contains("Data not presente in the Bloom Filter"
                .getBytes(StandardCharsets.UTF_8))); //false
        
        System.out.println(bf); //0000000000000000100000000000000001000000001000000000000000000000
    }
}