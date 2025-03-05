// public class TwoB {
    
// }
public class TwoB {

    // Function to find the closest pair of points
    public static int[] findClosestPair(int[] x_coords, int[] y_coords) {
        int n = x_coords.length; // Number of points
        int minDistance = Integer.MAX_VALUE; // Initialize with maximum possible value
        int[] result = new int[2]; // To store the indices of the closest pair

        // Iterate through all pairs of points (i, j)
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // Calculate the Manhattan distance
                int distance = Math.abs(x_coords[i] - x_coords[j]) + Math.abs(y_coords[i] - y_coords[j]);

                // If the distance is smaller, update the result
                if (distance < minDistance) {
                    minDistance = distance;
                    result[0] = i;
                    result[1] = j;
                } 
                // If the distance is the same, choose lexicographically smaller pair
                else if (distance == minDistance) {
                    if (i < result[0] || (i == result[0] && j < result[1])) {
                        result[0] = i;
                        result[1] = j;
                    }
                }
            }
        }

        // Return the indices of the closest pair
        return result;
    }

    public static void main(String[] args) {
        // Example input
        int[] x_coords = {1, 2, 3, 2, 4};
        int[] y_coords = {2, 3, 1, 2, 3};

        // Find the closest pair
        int[] closestPair = findClosestPair(x_coords, y_coords);

        // Display the output
        System.out.println("Output: [" + closestPair[0] + ", " + closestPair[1] + "]");
    }
}


/* Iterate through all pairs: For each point 
i
i in x_coords and each point 
j
j in y_coords, calculate the Manhattan distance:
distance=∣x_coords[i]−x_coords[j]∣+∣y_coords[i]−y_coords[j]∣

Track the minimum distance: Keep track of the smallest distance found so far.

Check lexicographical order: If two pairs have the same distance, choose the one with the smaller indices (i,j).

*/


/*Output: [0, 3]
For each pair of points:



 */