// 1a)
// You have a material with n temperature levels. You know that there exists a critical temperature f where 0 <= f <= n such that the material will react or change its properties at temperatures higher than f but remain unchanged at or below f.
// Rules:
//  You can measure the material's properties at any temperature level once.
//  If the material reacts or changes its properties, you can no longer use it for further measurements.
//  If the material remains unchanged, you can reuse it for further measurements.
// Goal:
// Determine the minimum number of measurements required to find the critical temperature.
// Input:
//  k: The number of identical samples of the material.
//  n: The number of temperature levels.
// Output:
//  The minimum number of measurements required to find the critical temperature. Example 1:
// Input: k = 1, n = 2 Output: 2 Explanation:
// Check the material at temperature 1. If its property changes, we know that f = 0.
// Otherwise, raise temperature to 2 and check if property changes. If its property changes, we know that f = 1.If its property changes at temperature, then we know f = 2.
// Hence, we need at minimum 2 moves to determine with certainty what the value of f is.
// Example 2:
// Input: k = 2, n = 6
// Output: 3
// Example 3:
// Input: k = 3, n = 14
// Output: 4
















// Define a class named CriticalTemperature
class CriticalTemperature {

    // Method to calculate the minimum number of measurements needed
    public static int minMeasurements(int k, int n) {
        // Create a 2D array dp where:
        // dp[i][j] represents the maximum number of floors that can be tested with 'i' thermometers and 'j' attempts
        int[][] dp = new int[k + 1][n + 1];

        // Variable to track the number of attempts
        int attempts = 0;

        // Continue until we can test at least 'n' floors with 'k' thermometers
        while (dp[k][attempts] < n) {
            attempts++; // Increase the number of attempts
            
            // Iterate through all thermometers from 1 to k
            for (int i = 1; i <= k; i++) {
                // Recurrence relation:
                // dp[i][attempts] = dp[i - 1][attempts - 1] + dp[i][attempts - 1] + 1
                // Explanation:
                // - dp[i - 1][attempts - 1]: If the thermometer breaks at a certain level
                // - dp[i][attempts - 1]: If the thermometer does not break
                // - +1 accounts for the current attempt
                dp[i][attempts] = dp[i - 1][attempts - 1] + dp[i][attempts - 1] + 1;
            }
        }

        // Return the minimum number of attempts required
        return attempts;
    }

    // Main method to test the function
    public static void main(String[] args) {
        // Example 1: Test with 1 thermometer and 2 floors
        System.out.println("Example 1:");
        System.out.println("Input: k = 1, n = 2");
        System.out.println("Output: " + minMeasurements(1, 2));

        // Example 2: Test with 2 thermometers and 6 floors
        System.out.println("\nExample 2:");
        System.out.println("Input: k = 2, n = 6");
        System.out.println("Output: " + minMeasurements(2, 6));

        // Example 3: Test with 3 thermometers and 14 floors
        System.out.println("\nExample 3:");
        System.out.println("Input: k = 3, n = 14");
        System.out.println("Output: " + minMeasurements(3, 14));
    }
}

// //Example 1:
// Input: k = 1, n = 2
// Output: 2

// Example 2:
// Input: k = 2, n = 6
// Output: 3

// Example 3:
// Input: k = 3, n = 14
// Output: 4

 
/*Algorithm:
The function minMeasurements(k, n) determines the minimum number of attempts needed to find a critical temperature using k thermometers and n floors.


It uses a dynamic programming approach where dp[i][j] stores how many floors can be tested with i thermometers and j attempts.

It iteratively increases attempts until we can test at least n floors.

The formula   dp[i][attempts]=dp[i−1][attempts−1]+dp[i][attempts−1]+1

helps track the number of floors that can be tested.


The main method runs three test cases to check how many attempts are needed for different values of k and n.

*/