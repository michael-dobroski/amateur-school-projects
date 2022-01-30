import org.junit.Test;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.*;

// i used this stackoverflow page to find out how to round my doubles
// https://stackoverflow.com/questions/153724/how-to-round-a-number-to-n-decimal-places-in-java

/**
 * JUnit 5 testing of the MachineLearning class.
 */
public class MachineLearningJUnit {

    /**
     * Tests the cosine similarity function.
     * @throws IllegalAccessException
     */
    @Test
    public void testCosineSimilarity() throws IllegalAccessException {
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.HALF_EVEN);
        Double[] A = {1d,2d,3d};
        Double[] B = {2d,6d,3d};
        assertEquals(0.8781, Double.parseDouble(df.format(MachineLearning.cosineSimilarity(A,B))));
        A[0] = 6d; A[1] = 4d; A[2] = 25d;
        B[0] = 23d; B[1] = 5d; B[2] = 1d;
        assertEquals(0.2985, Double.parseDouble(df.format(MachineLearning.cosineSimilarity(A,B))));
        Double[] C = {6d,1d,8d,1d,3d,4d};
        Double[] D = {8d,1d,35d,1d,3d,89d};
        assertEquals(0.6422, Double.parseDouble(df.format(MachineLearning.cosineSimilarity(C,D))));
        Double[] E = {87d,97d,87d,97d,98d,1d,31d,54d,20d};
        Double[] F = {87d,51d,21d,8d,31d,3d,54d,14d,4d};
        assertEquals( 0.7775, Double.parseDouble(df.format(MachineLearning.cosineSimilarity(E,F))));
        Double[] G = {1d,2d};
        Double[] H = {2d,3d};
        assertEquals( 0.9923, Double.parseDouble(df.format(MachineLearning.cosineSimilarity(G,H))));
    }

    /**
     * Tests the hamming distance function.
     * @throws IllegalAccessException
     */
    @Test
    public void testHammingDistance() throws IllegalAccessException {
        assertEquals(14, MachineLearning.hammingDistance("010101010100101010100101010111111111","100101010101010010010100100100110111"));
        assertEquals(11, MachineLearning.hammingDistance("0101010101001010101001010101","1001010101010100100101001001"));
        assertEquals(2, MachineLearning.hammingDistance("01010101010","10010101010"));
        assertEquals(1, MachineLearning.hammingDistance("0","1"));
        assertEquals(0, MachineLearning.hammingDistance("",""));
    }

    /**
     * Tests the euclidean distance function.
     * @throws IllegalAccessException
     */
    @Test
    public void testEuclideanDistance() throws IllegalAccessException {
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.HALF_EVEN);
        Double[] A = {1d,2d,3d};
        Double[] B = {2d,6d,3d};
        assertEquals(4.123, Double.parseDouble(df.format(MachineLearning.euclideanDistance(A,B))));
        A[0] = 6d; A[1] = 4d; A[2] = 25d;
        B[0] = 23d; B[1] = 5d; B[2] = 1d;
        assertEquals(29.428, Double.parseDouble(df.format(MachineLearning.euclideanDistance(A,B))));
        Double[] C = {6d,1d,8d,1d,3d,4d};
        Double[] D = {8d,1d,35d,1d,3d,89d};
        assertEquals(89.208, Double.parseDouble(df.format(MachineLearning.euclideanDistance(C,D))));
        Double[] E = {87d,97d,87d,97d,98d,1d,31d,54d,20d};
        Double[] F = {87d,51d,21d,8d,31d,3d,54d,14d,4d};
        assertEquals( 145.846, Double.parseDouble(df.format(MachineLearning.euclideanDistance(E,F))));
        Double[] G = {1d,2d};
        Double[] H = {2d,3d};
        assertEquals( 1.414, Double.parseDouble(df.format(MachineLearning.euclideanDistance(G,H))));
    }
}












