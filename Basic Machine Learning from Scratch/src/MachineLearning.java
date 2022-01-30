import java.io.File;
import java.lang.Math;
import java.lang.String;
import java.util.*;
import java.util.Random;

/**
 * Contains several useful methods for machine learning.
 */
public class MachineLearning {

    /**
     * Will find the cosine similarity of two arrays.
     * @param A
     * @param B
     * @return top/bottom
     * @throws IllegalAccessException
     */
    public static Double cosineSimilarity(Double[] A, Double[] B) throws IllegalAccessException {
        if (A.length != B.length) { // if the two inputs are the same length, returns an exception
            throw new IllegalAccessException("Inputted arrays need to be the same length.");
        }

        double top = 0;
        double bottom = 0;
        double ASquaredSum = 0;
        double BSquaredSum = 0;

        for (int i=0; i<A.length; i++) { // iterates through the length of A to do calculations
            top += A[i] * B[i]; // dot product
            ASquaredSum += A[i] * A[i]; // square it and sum it
            BSquaredSum += B[i] * B[i]; // square it and sum it
        }

        bottom = Math.sqrt(ASquaredSum) * Math.sqrt(BSquaredSum); // bottom part of eq

        return top/bottom;
    }

    /**
     * Will find the hamming distance of two strings.
     * @param A
     * @param B
     * @return difCount
     * @throws IllegalAccessException
     */
    public static int hammingDistance(String A, String B) throws IllegalAccessException {
        if (A.length() != B.length()) { // exception if different lengths
            throw new IllegalAccessException("Inputted strings need to be the same length.");
        }

        int difCount = 0;

        for (int i=0; i<A.length(); i++) { // iterate through length of a to add up everytime the characters don't match
            if(A.charAt(i) != B.charAt(i)) {
                difCount++;
            }
        }

        return difCount;
    }

    /**
     * Will find the euclidean distance of two arrays.
     * @param A
     * @param B
     * @return Math.sqrt(sum)
     * @throws IllegalAccessException
     */
    public static Double euclideanDistance(Double[] A, Double[] B) throws IllegalAccessException {
        if (A.length != B.length) { // exception if different lengths
            throw new IllegalAccessException("Inputted arrays need to be the same length.");
        }

        double sum = 0;

        for (int i=0; i<A.length; i++) { // iterates through length of A and completes eq
            sum += (A[i] - B[i]) * (A[i] - B[i]);
        }

        return Math.sqrt(sum);
    }

    /**
     * Applies the k-nearest algorithm on a given .csv dataset.
     * @param filename
     * @param tstFtrs
     * @param k
     */
    public static void kNearest(String filename, Double[] tstFtrs, int k) {
        File orderFile = new File("oral_exam1" + File.separator + "S27_MachineLearning_Hard" + File.separator + filename); // set up reading desired file
        ArrayList<Double[]> trnFtrs = new ArrayList<>(); // training features array
        ArrayList<String> trnTgt = new ArrayList<>(); // training target
        String line; // for reading in
        List<String> splits = null; // for reading in
        List<Double> distances = new ArrayList<>(); // keep track of distance to every other data point
        ArrayList<Double[]> distancesWithLabels = new ArrayList<>(); // keeps track of labels too
        Double distance;
        int class1IsPositive = 0; // if neighbor is class1, add one, and if it's class 2, subtract one. this way
        // when we want to find out if it's a majority class1 or class2 we can easily tell
        // by observing if it's positive or not
        Double distanceToFind;
        int iter;

        try { // try and catch allows us to throw exception if file doesn't exist or any error for that mattter
            Scanner input = new Scanner(orderFile); // sets up scanner for reading in
            while (input.hasNextLine()) { // while there's still text left to read in
                line = input.nextLine();
                splits = Arrays.asList(line.split(",")); // split data at comma
                Double[] tmp = new Double[5];
                for (int i=0; i<5; i++) {
                    tmp[i] = Double.parseDouble(splits.get(i)); // turn string into double for storing as array
                }
                trnTgt.add(splits.get(5));
                trnFtrs.add(tmp); // add double array of data to features list
            }
            input.close(); // no longer reading in

            for (int i=0; i<100; i++) { // iterates through every data point
                distance = euclideanDistance(tstFtrs, trnFtrs.get(i)); // computes distance for each point to all training features
                distances.add(distance);
                Double[] tmp = new Double[2];
                tmp[0] = distance;
                tmp[1] = (double) i;
                distancesWithLabels.add(tmp); // adds it to whole array of distances including labels
            }

            Collections.sort(distances); // sorts it by distance

            System.out.println("Top " + k + " Nearest Neighbors...");

            for (int i=0; i<k; i++) { // iterates based on number of neighbors desired
                distanceToFind = distances.get(i); // keeps track of the distance we need, start with smallest b/c it's sorted
                for (int j=0; j<100; j++) { // iterate through to find the distance
                    if (distanceToFind.equals((distancesWithLabels.get(j))[0])) { // if we find that distance then we can find its class
                        iter = ((distancesWithLabels.get(j))[1]).intValue(); // will tell us what index we need to use
                        System.out.println((i+1) + ". " + trnTgt.get(iter) + " with euclidean distance " + distanceToFind); // print class and distance to data point
                        if (trnTgt.get(iter).equals("\"class1\"")) {
                            class1IsPositive++; // add one to tell if it's class1 or class2 later
                        }
                    }
                }
            }

            if (class1IsPositive > 0) { // if it's positive we know its class1 because we add one for every neighbor that's class1
                // we know it will never be zero because we always chose an odd number of k
                System.out.println("New data point belongs to class1\n");
            } else {
                System.out.println("New data point belongs to class2\n");
            }

        } catch (Exception e) {
            System.out.printf("Exception: %s%n", e.getMessage()); // throw exception
        }
    }

    /**
     * Applies the k-clustering algorithm on a given dataset.
     * @param filename
     * @param k
     */
    public static void kmeans(String filename, int k) { // k = number of clusters
        File orderFile = new File("oral_exam1" + File.separator + "S27_MachineLearning_Hard" + File.separator + filename); // set up reading in
        ArrayList<Double[]> dataPoints = new ArrayList<>(); // eventually we want csv in form of data
        String line; // for reading in
        List<String> splits = null; // must set type List as null, also for reading in properly
        int[] clusterIndexes = new int[k]; // used slightly later when keeping track of the index of our initial clusters
        Random rand = new Random(); // random instance used to randomly choose initial clusters
        Double closestCluster; // the index of the closest cluster used for composition of data storing
        ArrayList<Double[]> dataPointsWithClusterIndex = new ArrayList<>(); // keeps track of cluster index next to features


        try { // allows us to throw exception if file is not read properly or any error for that matter
            Scanner input = new Scanner(orderFile); // for reading in
            while (input.hasNextLine()) { // while there's still stuff left to read in
                line = input.nextLine(); // for reading in
                splits = Arrays.asList(line.split(",")); // splits it up by comma, works well for our csv
                Double[] tmp = new Double[2]; // for later data composition
                for (int i=0; i<2; i++) { // we want to to turn them both into double
                    tmp[i] = Double.parseDouble(splits.get(i));
                }
                dataPoints.add(tmp); // add to array of training data
            }
            input.close(); // don't need to read in anymore

            // chose k random data points to be initial clusters
            // System.out.println("The four random initial clusters are...");
            for (int i=0; i<k; i++) { // we need k clusters so we iterate that many times
                clusterIndexes[i] = rand.nextInt(120); // chose random index from 0 to 120
                // System.out.println(Arrays.toString(dataPoints.get(clusterIndexes[i])));
            }

            for (int i=0; i<1; i++) { // start with just using data point 0, then we can move on to doing more at once
                // this was originally how i wanted to do things but i ultimately decided on another algorithm
                // i keep this in because some of the data is used later and it shows my problem solving process
                ArrayList<Double[]> distPlusIndex = new ArrayList<>(); // we keep distances in line with index, never used though
                for (int j = 0; j < k; j++) { // iterate through every cluster
                    Double[] tmp = new Double[2]; // used for data composition
                    tmp[0] = euclideanDistance(dataPoints.get(0), dataPoints.get(clusterIndexes[j])); // find distance
                    tmp[1] = (double) clusterIndexes[j]; // must be double for array
                    distPlusIndex.add(tmp); // add to bigger picture
                }

                // following block was used for debugging
                // closestCluster = getMinFirstElement(distPlusIndex, k)[1];
                // System.out.println("\nDistances from data point 0 to each cluster and their respective index...");
                // System.out.println(Arrays.toString(distPlusIndex.get(0)));
                // System.out.println(Arrays.toString(distPlusIndex.get(1)));
                // System.out.println(Arrays.toString(distPlusIndex.get(2)));
                // System.out.println(Arrays.toString(distPlusIndex.get(3)));
                // System.out.println("\nIndex of closest cluster to data point 0...");
                // System.out.println(closestCluster);
            }

            // now we can do it with every data point and assign points to their nearest cluster
            for (int i=0; i<120; i++) { // iterate through total amount of data points to find distance to each cluster at every data point
                boolean skipIndex = false; // so that we don't use a cluster as a data point
                for (int element : clusterIndexes) { // iterates through list of indexes of clusters and if i matches it we skip this loop
                    if (element == i) {
                        skipIndex = true;
                        break;
                    }
                }
                if (!skipIndex) { // this way we skip finding nearest cluster to a cluster
                    ArrayList<Double[]> distPlusIndex = new ArrayList<>(); // still using distance and index data structure
                    for (int j=0; j<k; j++) { // iterate through num of clusters
                        Double[] tmp = new Double[2];
                        tmp[0] = euclideanDistance(dataPoints.get(i), dataPoints.get(clusterIndexes[j])); // get distance to every cluster
                        tmp[1] = (double) clusterIndexes[j];
                        distPlusIndex.add(tmp); // data composition
                    }
                    closestCluster = getMinFirstElement(distPlusIndex, k)[1]; // uses function that finds min first element and
                    // prints out the second element corresponding to min first element
                    Double[] tmp = new Double[3];
                    tmp[0] = dataPoints.get(i)[0];
                    tmp[1] = dataPoints.get(i)[1];
                    tmp[2] = closestCluster;
                    dataPointsWithClusterIndex.add(tmp); // for bigger data picture
                }
            }

            // more debugging
            // System.out.println("\n[Data point, initial cluster index]...");
            // for (int i=0; i<(120-k); i++) {
            // System.out.println(Arrays.toString(dataPointsWithClusterIndex.get(i)));
            // }

            ArrayList<Double[]> dataPointsWithCluster = new ArrayList<>(); // [cluster data point, data point]
            // this data structure is used much more and i'm starting to get a hang of keeping track of data in java here
            ArrayList<Double[]> clusters = new ArrayList<>(); // now we keep track of clusters on their own too
            for (int i=0; i<k; i++) { // iterate through all clusters
                // System.out.println(Arrays.toString(dataPoints.get(clusterIndexes[i]))); // debugging
                Double[] cluster = new Double[2]; // get clusters together in solidarity
                cluster[0] = (dataPoints.get(clusterIndexes[i]))[0];
                cluster[1] = (dataPoints.get(clusterIndexes[i]))[1];
                clusters.add(cluster);
                for (int j=0; j<(120-k); j++) { // iterate through all data points minus the cluster points we took out
                    if (dataPointsWithClusterIndex.get(j)[2] == clusterIndexes[i]) { // if the indexes match
                        Double[] tmp = new Double[4];
                        tmp[0] = (dataPoints.get(clusterIndexes[i]))[0];
                        tmp[1] = (dataPoints.get(clusterIndexes[i]))[1];
                        tmp[2] = dataPointsWithClusterIndex.get(j)[0];
                        tmp[3] = dataPointsWithClusterIndex.get(j)[1];
                        dataPointsWithCluster.add(tmp); // now we got it set up so that it's [cluster, data point]
                        // no index, just straight up data point for cluster
                    }
                }
            }

            // add initial clusters back to data points + clusters
            for (int i=0; i<k; i++) {
                Double[] tmp = new Double[4];
                tmp[0] = clusters.get(i)[0];
                tmp[1] = clusters.get(i)[1];
                tmp[2] = clusters.get(i)[0];
                tmp[3] = clusters.get(i)[1];
                dataPointsWithCluster.add(tmp);
            }

            // more debugging
            // System.out.println("\n[Initial cluster, data point]...");
            // for (int i=0; i<120; i++) {
            // System.out.println(Arrays.toString(dataPointsWithCluster.get(i)));
            // }
            // System.out.println("\nAll clusters...");
            // for (int i=0; i<k; i++) {
            // System.out.println(Arrays.toString(clusters.get(i)));
            // }

            // replace initial clusters with means
            for (int i=0; i<k; i++) {
                Double[] newCluster = new Double[2];
                newCluster = findClusterMean(clusters.get(0), dataPointsWithCluster);
                clusters.add(newCluster);
                clusters.remove(0);
            }

            // System.out.println("\nAll clusters after getting new mean cluster...");
            // for (int i=0; i<k; i++) {
            // System.out.println(Arrays.toString(clusters.get(i)));
            // System.out.println();
            // }

            // now we have list of new clusters {clusters} and list of [new cluster, data point] {dataPointsWithCluster}
            // we're going to try just running it once to make it more simple then we'll run multiple iterations
            double minDist = 0; // for finding min distance
            double dist; // assists in finding min distance
            Double[] clustxy = new Double[2]; // xy coordinates for each cluster
            Double[] closestClustxy = new Double[2]; // for keeping track of the xy coordinates of the closest cluster
            for (int i=0; i<120; i++) { // iterate through every data point
                Double[] xy = {dataPointsWithCluster.get(0)[2],dataPointsWithCluster.get(0)[3]}; // sets data point x and y
                for (int j=0; j<k; j++) { // iterate through every cluster
                    clustxy[0] = clusters.get(j)[0]; // gets y value of cluster
                    clustxy[1] = clusters.get(j)[1]; // gets y value of cluster
                    dist = euclideanDistance(xy, clustxy); // equate distance
                    if (j==0) { // yes i know i ditched the old min function but i didn't like it
                        minDist = dist;
                        closestClustxy[0] = clusters.get(j)[0];
                        closestClustxy[1] = clusters.get(j)[1];
                    }
                    else if (dist < minDist) { // this is all the classic algorithm for finding min but this time
                        // i'm also manually keeping track of the cluster coordinates that correspond to it
                        minDist = dist;
                        closestClustxy[0] = clusters.get(j)[0];
                        closestClustxy[1] = clusters.get(j)[1];
                    }
                }
                // puts data point and cluster together, same format of [cluster, data point]
                Double[] newDataPointWithCluster = new Double[4];
                newDataPointWithCluster[0] = closestClustxy[0];
                newDataPointWithCluster[1] = closestClustxy[1];
                newDataPointWithCluster[2] = xy[0];
                newDataPointWithCluster[3] = xy[1];
                dataPointsWithCluster.add(newDataPointWithCluster);
                dataPointsWithCluster.remove(0); // get rid of old one
            }

            // System.out.println("\n[Initial cluster, data point]...");
            // for (int i=0; i<120; i++) {
            //     System.out.println(Arrays.toString(dataPointsWithCluster.get(i)));
            // }

            // put it all together! this is the previous loop plus it gets the new clusters and iterates many times to convergence
            for (int h=0; h<10; h++) { // number of iterations to assure it converges
                for (int q=0; q<k; q++) { // replace old clusters with means
                    Double[] newCluster = new Double[2];
                    newCluster = findClusterMean(clusters.get(0), dataPointsWithCluster);
                    clusters.add(newCluster);
                    clusters.remove(0);
                }
                for (int i=0; i<120; i++) { // iterate through every data point
                    Double[] xy = {dataPointsWithCluster.get(0)[2],dataPointsWithCluster.get(0)[3]};
                    for (int j=0; j<k; j++) { // iterate through every cluster
                        clustxy[0] = clusters.get(j)[0];
                        clustxy[1] = clusters.get(j)[1];
                        dist = euclideanDistance(xy, clustxy);
                        if (j==0) {
                            minDist = dist;
                            closestClustxy[0] = clusters.get(j)[0];
                            closestClustxy[1] = clusters.get(j)[1];
                        }
                        else if (dist < minDist) {
                            minDist = dist;
                            closestClustxy[0] = clusters.get(j)[0];
                            closestClustxy[1] = clusters.get(j)[1];
                        }
                    }
                    Double[] newDataPointWithCluster = new Double[4];
                    newDataPointWithCluster[0] = closestClustxy[0];
                    newDataPointWithCluster[1] = closestClustxy[1];
                    newDataPointWithCluster[2] = xy[0];
                    newDataPointWithCluster[3] = xy[1];
                    dataPointsWithCluster.add(newDataPointWithCluster);
                    dataPointsWithCluster.remove(0);
                }
            }

            // System.out.println("\n[Initial cluster, data point]...");
            // for (int i=0; i<120; i++) {
            //     System.out.println(Arrays.toString(dataPointsWithCluster.get(i)));
            // }

            int[] clusterCount = new int[k]; // keeps track of how many data points correspond to each cluster
            for (int i=0; i<120; i++) {
                for (int j=0; j<k; j++) {
                    if (clusters.get(j)[0].equals(dataPointsWithCluster.get(i)[0]) && clusters.get(j)[1].equals(dataPointsWithCluster.get(i)[1])) {
                        clusterCount[j]++;
                    }
                }
            }

            for (int i=0; i<k; i++) { // prints it all out in a clean fashion
                System.out.println("cluster" + (i+1) + ": " + clusterCount[i] + " data point");
            }

            // System.out.println(Arrays.toString(clusterCount));

        } catch (Exception e) {
            System.out.printf("Exception: %s%n", e.getMessage());
        }
    }

    /**
     * Returns the array that has the smallest first element.
     * @param data
     * @param k
     * @return min
     */
    public static Double[] getMinFirstElement(ArrayList<Double[]> data, int k) {
        Double[] min = data.get(0);
        for (int i=1; i<k; i++) {
            if (data.get(i)[0] < min[0]) {
                min = data.get(i);
            }
        }
        return min;
    }

    /**
     * Finds the mean of a cluster.
     * @param cluster
     * @param dataPointsWithCluster
     * @return newClusterMean
     */
    public static Double[] findClusterMean(Double[] cluster, ArrayList<Double[]> dataPointsWithCluster) {
        Double[] newClusterMean = new Double[2];
        Double numDataPointsPerCluster = 0d;
        Double sumX = 0d;
        Double sumY = 0d;

        for (int i=0; i<120; i++) {
            if (cluster[0].equals(dataPointsWithCluster.get(i)[0]) && cluster[1].equals(dataPointsWithCluster.get(i)[1])) { // if data point has that cluster
                numDataPointsPerCluster += 1d;
                sumX += dataPointsWithCluster.get(i)[2];
                sumY += dataPointsWithCluster.get(i)[3];
            }
        }

        newClusterMean[0] = sumX/numDataPointsPerCluster;
        newClusterMean[1] = sumY/numDataPointsPerCluster;

        return newClusterMean;
    }
}