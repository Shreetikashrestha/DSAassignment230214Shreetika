// b)
// Scenario: A Multithreaded Web Crawler
// Problem:
// You need to crawl a large number of web pages to gather data or index content. Crawling each page sequentially can be time-consuming and inefficient.
// Goal:
// Create a web crawler application that can crawl multiple web pages concurrently using multithreading to improve performance.
// Tasks:
// Design the application:
// Create a data structure to store the URLs to be crawled.
// Implement a mechanism to fetch web pages asynchronously.
// Design a data storage mechanism to save the crawled data.
// Create a thread pool:
// Use the ExecutorService class to create a thread pool for managing multiple threads.
// Submit tasks:
// For each URL to be crawled, create a task (e.g., a Runnable or Callable object) that fetches the web page and processes the content.
// Submit these tasks to the thread pool for execution.
// Handle responses:
// Process the fetched web pages, extracting relevant data or indexing the content.
// Handle errors or exceptions that may occur during the crawling process.
// Manage the crawling queue:
// Implement a mechanism to manage the queue of URLs to be crawled, such as a priority queue or a breadth-first search algorithm.
// By completing these tasks, you will create a multithreaded web crawler that can efficiently crawl large numbers of web page



import java.io.*;
import java.net.*;
import java.util.*;

// Class A - Downloads content from a URL
class CrawlerA extends Thread {
    private String url; // URL to crawl

    public CrawlerA(String url) {
        this.url = url; // Assign URL
    }

    @Override
    public void run() {
        downloadPage(url); // Start downloading
    }

    private void downloadPage(String urlString) {
        try {
            URL url = new URL(urlString); // Create URL object
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream())); // Open stream
            String line;
            StringBuilder content = new StringBuilder(); // Store content

            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n"); // Read and append content
            }
            reader.close(); // Close reader

            saveToFile(urlString, content.toString()); // Save content to file
            System.out.println("Crawled: " + urlString); // Print success message
        } catch (Exception e) {
            System.out.println("Failed to crawl: " + urlString + " - " + e.getMessage()); // Print error message
        }
    }

    private void saveToFile(String url, String content) {
        try {
            String filename = url.replaceAll("[^a-zA-Z0-9]", "_") + ".txt"; // Create filename
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename)); // Open file writer
            writer.write(content); // Write content to file
            writer.close(); // Close writer
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage()); // Print error message
        }
    }
}

// Class B - Another thread for downloading a different URL
class CrawlerB extends Thread {
    private String url; // URL to crawl

    public CrawlerB(String url) {
        this.url = url; // Assign URL
    }

    @Override
    public void run() {
        downloadPage(url); // Start downloading
    }

    private void downloadPage(String urlString) {
        try {
            URL url = new URL(urlString); // Create URL object
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream())); // Open stream
            String line;
            StringBuilder content = new StringBuilder(); // Store content

            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n"); // Read and append content
            }
            reader.close(); // Close reader

            saveToFile(urlString, content.toString()); // Save content to file
            System.out.println("Crawled: " + urlString); // Print success message
        } catch (Exception e) {
            System.out.println("Failed to crawl: " + urlString + " - " + e.getMessage()); // Print error message
        }
    }

    private void saveToFile(String url, String content) {
        try {
            String filename = url.replaceAll("[^a-zA-Z0-9]", "_") + ".txt"; // Create filename
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename)); // Open file writer
            writer.write(content); // Write content to file
            writer.close(); // Close writer
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage()); // Print error message
        }
    }
}

// Main class to start the crawler
public class SixB {
    public static void main(String[] args) {
        List<String> urls = Arrays.asList(
                "https://www.example.com", // First URL
                "https://www.wikipedia.org" // Second URL
        );

        // Creating threads for each URL
        CrawlerA crawlerA = new CrawlerA(urls.get(0)); // Thread for first URL
        CrawlerB crawlerB = new CrawlerB(urls.get(1)); // Thread for second URL

        // Start threads
        crawlerA.start(); // Start first thread
        crawlerB.start(); // Start second thread

        // Wait for threads to complete
        try {
            crawlerA.join(); // Wait for first thread
            crawlerB.join(); // Wait for second thread
        } catch (InterruptedException e) {
            e.printStackTrace(); // Print error if interrupted
        }

        System.out.println("Crawling finished!"); // Print completion message
    }
}
// output

// Crawled: https://www.example.com
// Crawled: https://www.wikipedia.org
// Crawling finished!

// Working Algorithm of the Web Crawler (Short Version)
// Initialize Components:
// Create a thread pool (ExecutorService) with MAX_THREADS = 5.
// Use a queue (urlQueue) to store URLs to crawl.
// Maintain a set (visitedUrls) to track visited pages.
// Store crawled data in a map (crawledData).
// Start Crawling:
// Add the starting URL (startUrl) to urlQueue.
// Process URLs concurrently using multiple threads.
// Fetch Web Page Content:
// Extract the next URL from urlQueue.
// Check if it’s already visited. If not, fetch its content using HttpURLConnection.
// Store the page content in crawledData.
// Extract Links from the Page:
// Identify new URLs in the content (extractUrls(content)).
// Add unvisited URLs back to urlQueue.
// Repeat Until Limit Reached:
// Continue crawling until MAX_PAGES = 10 pages are fetched or urlQueue is empty.
// Shutdown Threads & Print Result:
// Wait for all threads to finish (future.get()).
// Shut down the thread pool and display the total number of pages crawled.