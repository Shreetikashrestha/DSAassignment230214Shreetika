
// Question 3
// You have a network of n devices. Each device can have its own communication module installed at a cost of modules [i - 1]. Alternatively, devices can communicate with each other using direct connections. The cost of connecting two devices is given by the array connections where each connections[j] = [device1j, device2j, costj] represents the cost to connect devices device1j and device2j. Connections are bidirectional, and there could be multiple valid connections between the same two devices with different costs.
// Goal:
// Determine the minimum total cost to connect all devices in the network.
// Input:
// n: The number of devices.
// modules: An array of costs to install communication modules on each device.
// connections: An array of connections, where each connection is represented as a triplet [device1j, device2j, costj].
// Output:
// The minimum total cost to connect all devices.
// Example:
// Input: n = 3, modules = [1, 2, 2], connections = [[1, 2, 1], [2, 3, 1]] Output: 3 Explanation:
// The best strategy is to install a communication module on the first device with cost 1 and connect the other devices to it with cost 2, resulting in a total cost of 3.



import java.util.*;

public class ThreeA {

    // Disjoint Set Union (DSU) class for Kruskal's algorithm
    static class DSU {
        int[] parent, rank;

        // Constructor to initialize DSU with 'n' elements
        DSU(int n) {
            parent = new int[n];  // Array to track the parent of each node
            rank = new int[n];    // Array to track the rank (tree height) of each node

            // Initially, each node is its own parent (self-loop)
            for (int i = 0; i < n; i++) {
                parent[i] = i;    // Each node points to itself
                rank[i] = 1;      // Initial rank is 1 for all nodes
            }
        }

        // Find the root of the set that 'x' belongs to, with path compression
        int find(int x) {
            if (parent[x] != x) {
                // Path compression: make the found root the direct parent of 'x'
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        // Union two sets by rank to keep the tree shallow
        boolean union(int x, int y) {
            int rootX = find(x);  // Find root of set containing x
            int rootY = find(y);  // Find root of set containing y

            // If both nodes have the same root, they are already connected
            if (rootX == rootY) return false;

            // Union by rank: attach smaller tree under the larger tree
            if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;  // Make rootX the parent of rootY
            } else if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;  // Make rootY the parent of rootX
            } else {
                // If ranks are equal, choose one as root and increase its rank
                parent[rootY] = rootX;
                rank[rootX]++;
            }
            return true;  // Union was successful
        }
    }

    public static int minTotalCost(int n, int[] modules, int[][] connections) {
        List<int[]> edges = new ArrayList<>();  // List to store all edges (connections)

        // Step 1: Add virtual edges representing module installation costs
        // Treat each device as connected to a virtual hub (node 0) with the module cost as the edge weight
        for (int i = 0; i < n; i++) {
            edges.add(new int[]{0, i + 1, modules[i]});  // Virtual node 0 to device (i + 1)
        }

        // Step 2: Add all given direct connections between devices
        // Each connection is represented as an edge [device1, device2, cost]
        for (int[] connection : connections) {
            edges.add(new int[]{connection[0], connection[1], connection[2]});
        }

        // Step 3: Sort all edges by cost in ascending order (Kruskal's requirement)
        edges.sort((a, b) -> a[2] - b[2]);

        // Step 4: Initialize DSU for n devices plus one virtual node (index 0)
        DSU dsu = new DSU(n + 1);  // n devices + 1 virtual hub

        int totalCost = 0;         // To track the total minimum cost
        int connectedEdges = 0;    // To track the number of edges used to connect devices

        // Step 5: Iterate through all sorted edges and apply Kruskal's algorithm
        for (int[] edge : edges) {
            int u = edge[0];    // First device (or virtual hub)
            int v = edge[1];    // Second device
            int cost = edge[2]; // Cost of this connection

            // Try to connect u and v if they are in different sets
            if (dsu.union(u, v)) {
                totalCost += cost;  // Add this connection's cost to the total
                connectedEdges++;   // Increase the count of connected components
            }

            // If we've connected all 'n' devices, we can stop early
            if (connectedEdges == n) break;
        }

        // Step 6: If we connected all devices, return the total cost; otherwise, return -1 (disconnected case)
        return connectedEdges == n ? totalCost : -1;
    }

    public static void main(String[] args) {
        int n = 3;  // Number of devices
        int[] modules = {1, 2, 2};  // Cost of installing a module on each device
        int[][] connections = {{1, 2, 1}, {2, 3, 1}};  // Available direct connections between devices

        // Calculate the minimum total cost to connect all devices
        int result = minTotalCost(n, modules, connections);

        // Display the result
        System.out.println("Minimum total cost to connect all devices: " + result);
    }
}

/*
OUTPUT
Minimum total cost to connect all devices: 3
 */


 /*Step-by-Step Approach:
Treat Modules as Virtual Connections:
Imagine each device has a "virtual connection" to a central hub, representing the module installation cost.
Add virtual edges 
[
0
,
i
,
m
o
d
u
l
e
s
[
i
−
1
]
]
[0,i,modules[i−1]] for each device 
i
i, where 0 represents the virtual hub.
Combine All Edges:
Combine the virtual module connections and actual connections into one list of edges.
Sort All Edges by Cost:
Sort the combined edges in ascending order of cost.
Use Kruskal's Algorithm:
Use the Union-Find data structure (Disjoint Set Union, DSU).
For each edge, check if the two devices are already connected.
If not, add the connection and update the total cost.
Check for Minimum Spanning Tree (MST):
Ensure that all devices are connected.
Return the total minimum cost.
 */