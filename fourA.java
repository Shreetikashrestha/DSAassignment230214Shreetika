//Question 4
// a)
// Input: Tweets table:
// [15 Marks]
 
// Write a solution to find the top 3 trending hashtags in February 2024. Every tweet may contain several hashtags.
// Return the result table ordered by count of hashtag, hashtag in descending order.
// The result format is in the following example.
//  Explanation:
// #HappyDay: Appeared in tweet IDs 13, 14, and 17, with a total count of 3 mentions. #TechLife: Appeared in tweet IDs 16 and 18, with a total count of 2 mentions.
// #WorkLife: Appeared in tweet ID 15, with a total count of 1 mention.
// Note: Output table is sorted in descending order by hashtag_count and hashtag respectively.
import java.util.*;
import java.util.regex.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

class fourA {  // Class definition
    static class Tweet {  // Inner class to represent a Tweet
        int userId;       // Stores the user ID
        int tweetId;      // Stores the tweet ID
        String tweet;     // Stores the tweet content
        LocalDate tweetDate;  // Stores the date of the tweet

        // Constructor to initialize the Tweet object
        public Tweet(int userId, int tweetId, String tweet, String tweetDate) {
            this.userId = userId;
            this.tweetId = tweetId;
            this.tweet = tweet;
            this.tweetDate = LocalDate.parse(tweetDate);  // Converts string date to LocalDate
        }
    }

    public static void main(String[] args) {  
        // List of sample tweets with hashtags
        List<Tweet> tweets = Arrays.asList(
            new Tweet(1435, 1317, "Bringing a great start to the day. #HappyDay #MorningVibes", "2024-02-01"),
            new Tweet(1614, 1418, "Another #HappyDay with good vibes! #FeelGood", "2024-02-01"),
            new Tweet(1666, 1616, "Exploring new tech frontiers. #TechLife #Innovation", "2024-02-04"),
            new Tweet(1419, 1717, "Gratitude for today's moments. #HappyDay #Thankful", "2024-02-05"),
            new Tweet(1419, 1818, "Innovation drives us. #TechLife #FutureTech", "2024-02-07"),
            new Tweet(1419, 1919, "Connecting with nature's serenity. #Nature #Peaceful", "2024-02-09")
        );

        // Define the date range for filtering tweets
        LocalDate startDate = LocalDate.of(2024, 2, 1);  // Start date: Feb 1, 2024
        LocalDate endDate = LocalDate.of(2024, 2, 29);   // End date: Feb 29, 2024

        // Regular expression to match hashtags (words starting with #)
        Pattern pattern = Pattern.compile("#\\w+");

        // Stream processing: Extract, filter, and count hashtags
        Map<String, Integer> hashtagCount = tweets.stream()
            // Filter tweets within the specified date range
            .filter(tweet -> tweet.tweetDate.isBefore(endDate) && tweet.tweetDate.isAfter(startDate))
            // Extract hashtags from each tweet
            .flatMap(tweet -> {
                Matcher matcher = pattern.matcher(tweet.tweet);
                List<String> hashtags = new ArrayList<>();
                while (matcher.find()) {  // Find hashtags in the tweet text
                    hashtags.add(matcher.group());  // Add found hashtag to the list
                }
                return hashtags.stream();  // Convert list to a stream
            })
            // Count occurrences of each hashtag
            .collect(Collectors.toMap(
                hashtag -> hashtag,  // Key: Hashtag text
                count -> 1,          // Value: Initial count 1
                Integer::sum         // Sum up counts if the hashtag appears multiple times
            ));

        // Sort hashtags first by frequency (descending), then alphabetically (if tied)
        List<Map.Entry<String, Integer>> sortedHashtags = hashtagCount.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())  // Sort by count descending
                .thenComparing(Map.Entry::getKey))  // If counts are equal, sort alphabetically
            .limit(3)  // Keep only the top 3 hashtags
            .collect(Collectors.toList());

        // Print the result in a formatted table
        System.out.println("Top 3 Trending Hashtags in February 2024:");
        System.out.println("+-----------+-------+");
        System.out.println("| hashtag   | count |");
        System.out.println("+-----------+-------+");

        // Loop through sorted hashtags and print each one
        for (Map.Entry<String, Integer> entry : sortedHashtags) {
            System.out.printf("| %-9s | %5d |\n", entry.getKey(), entry.getValue());
        }

        System.out.println("+-----------+-------+");  // End of table
    }
}


// Top 3 Trending Hashtags in February 2024:
// +-----------+-------+
// | hashtag   | count |
// +-----------+-------+
// | #TechLife |     2 |
// | #FutureTech |     1 |
// | #HappyDay |     1 |
// +-----------+-------+




// Here’s a short breakdown of the algorithm in the fourA class:

// Initialize Tweets
// Create a list of tweets with user ID, tweet ID, text, and date.
// Define Date Range & Hashtag Pattern
// Set a start date (Feb 1, 2024) and end date (Feb 29, 2024).
// Define a regex pattern to extract hashtags (#\w+).
// Extract & Count Hashtags
// Filter tweets within the date range.
// Use regex to find hashtags in each tweet.
// Store and count hashtag occurrences in a map ({hashtag -> count}).
// Sort Hashtags
// Sort hashtags by frequency (descending).
// If counts are the same, sort alphabetically.
// Select the top 3 hashtags.
// Display Output
// Print the top 3 trending hashtags in a formatted table.

