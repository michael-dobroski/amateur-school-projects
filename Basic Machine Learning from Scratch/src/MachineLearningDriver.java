import java.util.Arrays;
import java.lang.String;

/**
 * Tests the MachineLearning class.
 */
public class MachineLearningDriver {
    public static void main(String[] args) throws Exception {
        System.out.println("Cosine similarity of [1,2,3] and [2,6,3] = 0.8781");
        Double[] A = new Double[3]; A[0] = 1d; A[1] = 2d; A[2] = 3d;
        Double[] B = new Double[3]; B[0] = 2d; B[1] = 6d; B[2] = 3d;
        System.out.println("A = " + Arrays.toString(A) + " ~ B = " + Arrays.toString(B) + " ~ cosineSimilarity(A,B) = " + MachineLearning.cosineSimilarity(A,B));
        System.out.println();

        System.out.println("Cosine similarity of [1,2,3,4] and [2,6,3] will return error because they are not the same length.");
        try {
            Double[] C = new Double[4]; C[0] = 1d; C[1] = 2d; C[2] = 3d; C[3] = 4d;
            Double[] D = new Double[3]; D[0] = 2d; D[1] = 6d; D[2] = 3d;
            System.out.println("C = " + Arrays.toString(C) + " ~ D = " + Arrays.toString(D) + " ~ cosineSimilarity(C,D) = ");
            System.out.print(MachineLearning.cosineSimilarity(C,D));
        } catch (IllegalAccessException e) {
            System.out.printf("Exception while executing cosineSimilarity(C,D): %s%n", e.getMessage());
        }
        System.out.println();

        System.out.println("Hamming distance between \"0110101\" and \"1110010\" = 4");
        String s1 = "0110101";
        String s2 = "1110010";
        System.out.println("s1 = " + s1 + " ~ s2 = " + s2 + " ~ MachineLearning.hammingDistance(s1,s2) = " + MachineLearning.hammingDistance(s1,s2));
        System.out.println();

        System.out.println("Hamming distance between \"10110101\" and \"1110010\" will return error because there are not the same length.");
        try {
            String s3 = "10110101";
            String s4 = "1110010";
            System.out.println("s3 = " + s3 + " ~ s4 = " + s4 + " ~ hammingDistance(s3,s4) = ");
            System.out.print(MachineLearning.hammingDistance(s3,s4));
        } catch (IllegalAccessException e) {
            System.out.printf("Exception while executing hammingDistance(s3,s4): %s%n", e.getMessage());
        }
        System.out.println();

        System.out.println("Euclidean distance between [1,2,3] and [2,6,3] = 4.1231");
        System.out.println("A = " + Arrays.toString(A) + " ~ B = " + Arrays.toString(B) + " ~ euclideanDistance(A,B) = " + MachineLearning.euclideanDistance(A,B));
        System.out.println();

        System.out.println("Euclidean distance between [1,2,3,4] and [2,6,3] will return error because they are not the same length.");
        try {
            Double[] C = new Double[4]; C[0] = 1d; C[1] = 2d; C[2] = 3d; C[3] = 4d;
            Double[] D = new Double[3]; D[0] = 2d; D[1] = 6d; D[2] = 3d;
            System.out.println("C = " + Arrays.toString(C) + " ~ D = " + Arrays.toString(D) + " ~ euclideanDistance(C,D) = ");
            System.out.print(MachineLearning.euclideanDistance(C,D));
        } catch (IllegalAccessException e) {
            System.out.printf("Exception while executing euclideanDistance(C,D): %s%n", e.getMessage());
        }
        System.out.println();

        Double[] ftrs = new Double[5]; ftrs[0] = 1.5; ftrs[1] = 3.5; ftrs[2] = 2d; ftrs[3] = 2d; ftrs[4] = 8d;
        MachineLearning.kNearest("S27-MLMedium.csv",ftrs, 5);

        ftrs[0] = 3d; ftrs[1] = 3d; ftrs[2] = 2d; ftrs[3] = 2d; ftrs[4] = 1d;
        MachineLearning.kNearest("S27-MLMedium.csv",ftrs, 5);

        MachineLearning.kmeans("S27-MLHard.csv",4);
    }
}
