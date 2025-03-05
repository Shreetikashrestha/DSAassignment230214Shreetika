// Question 2
// a)
// You have a team of n employees, and each employee is assigned a performance rating given in the integer array ratings. You want to assign rewards to these employees based on the following rules: Every employee must receive at least one reward.
// Employees with a higher rating must receive more rewards than their adjacent colleagues.
// Goal:
// Determine the minimum number of rewards you need to distribute to the employees.
// Input:
// ratings: The array of employee performance ratings.
// Output:
// The minimum number of rewards needed to distribute.
// Example 1:
// Input: ratings = [1, 0, 2]
// Output: 5
// Explanation: You can allocate to the first, second and third employee with 2, 1, 2 rewards respectively. Example 2:
// Input: ratings = [1, 2, 2]
// Output: 4
// Explanation: You can allocate to the first, second and third employee with 1, 2, 1 rewards respectively. The third employee gets 1 rewards because it satisfies the above two conditions.



import java.util.Arrays;

public class TwoA {

    public static int findMinRewards(int[] ratings) {
        int n = ratings.length;
        if (n == 0) return 0;  // If no employees, no rewards needed.

        // Step 1: Create an array for rewards and give 1 reward to each employee.
        int[] rewards = new int[n];
        Arrays.fill(rewards, 1);

        // Step 2: Left to right pass.
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                rewards[i] = rewards[i - 1] + 1;  // If current rating is higher than left, give more rewards.
            }
        }

        // Step 3: Right to left pass.
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                rewards[i] = Math.max(rewards[i], rewards[i + 1] + 1);  // Ensure the correct rewards.
            }
        }

        // Step 4: Calculate the total rewards.
        int totalRewards = 0;
        for (int reward : rewards) {
            totalRewards += reward;
        }

        return totalRewards;  // Return the final count of rewards.
    }

    public static void main(String[] args) {
        // Example 1
        int[] ratings1 = {1, 0, 2};
        System.out.println("Minimum rewards for ratings [1, 0, 2]: " + findMinRewards(ratings1));

        // Example 2
        int[] ratings2 = {1, 2, 2};
        System.out.println("Minimum rewards for ratings [1, 2, 2]: " + findMinRewards(ratings2));
    }
}


/*

output
Minimum rewards for ratings [1, 0, 2]: 5
Minimum rewards for ratings [1, 2, 2]: 4 */


/*Step-by-Step Process:
Initialization:
Create an array rewards[] of the same size as ratings[].
Set each value in rewards[] to 1, as every employee must receive at least one reward.
Left-to-Right Pass:
Start from the second employee (index 1) and move to the right.
If ratings[i] > ratings[i - 1], increase rewards[i] by 1 more than rewards[i - 1].
This ensures that employees with higher ratings than their left neighbors get more rewards.
Right-to-Left Pass:
Start from the second-last employee (index n - 2) and move to the left.
If ratings[i] > ratings[i + 1], set rewards[i] to the maximum of its current value and rewards[i + 1] + 1.
This ensures that employees with higher ratings than their right neighbors also get more rewards.
Calculate Total Rewards:
Sum all the values in the rewards[] array.
This sum represents the minimum number of rewards needed.
Return the Result:
Output the total rewards as the final answer.
Example Walkthrough:
For ratings = [1, 0, 2]:

Initial rewards: [1, 1, 1]
Left pass: [2, 1, 2]
Right pass: [2, 1, 2]
Total rewards: 
2
+
1
+
2
=
5
2+1+2=5 */