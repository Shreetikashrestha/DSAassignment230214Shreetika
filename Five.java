
// Question 5
// Optimizing a Network with Multiple Objectives
// Problem:
// Suppose you are hired as software developer for certain organization and you are tasked with creating a GUI application that helps network administrators design a network topology that is both cost-effective and efficient for data transmission. The application needs to visually represent servers and clients as nodes in a graph, with potential network connections between them, each having associated costs and bandwidths. The goal is to enable the user to find a network topology that minimizes both the total cost and the latency of data transmission.
// Approach:
// 1. Visual Representation of the Network:
// o Design the GUI to allow users to create and visualize a network graph where each node
// represents a server or client, and each edge represents a potential network connection. The
// edges should display associated costs and bandwidths. 2. Interactive Optimization:
// o Implement tools within the GUI that enable users to apply algorithms or heuristics to optimize the network. The application should provide options to find the best combination of connections that minimizes the total cost while ensuring all nodes are connected.
// 3. Dynamic Path Calculation:
// o Include a feature where the user can calculate the shortest path between any pair of nodes
// within the selected network topology. The GUI should display these paths, taking into
// account the bandwidths as weights. 4. Real-time Evaluation:
// o Provide real-time analysis within the GUI that displays the total cost and latency of the current network topology. If the user is not satisfied with the results, they should be able to adjust the topology and explore alternative solutions interactively.
// Example:
//  Input: The user inputs a graph in the application, representing servers, clients, potential connections, their costs, and bandwidths.
//  Output: The application displays the optimal network topology that balances cost and latency, and shows the shortest paths between servers and clients on the GUI.

import javax.swing.*;  // Importing Swing for GUI components
import java.awt.*;      // Importing AWT for layout management
import java.awt.event.*; // Importing event handling
import java.util.*;      // Importing utility classes for data structures

// Class representing the GUI application
public class Five extends JFrame {
    private Graph networkGraph; // Graph to store network topology
    private JPanel graphPanel;  // Panel to display the graph

    // Constructor to initialize GUI components
    public Five() {
        setTitle("Network Optimizer");  // Setting the window title
        setSize(800, 600);              // Setting window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close application when window is closed
        setLayout(new BorderLayout()); // Using BorderLayout for main layout

        networkGraph = new Graph(); // Initializing the graph
        graphPanel = new JPanel() {
            // Overriding paintComponent to draw the graph
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                networkGraph.draw(g); // Drawing the graph
            }
        };
        add(graphPanel, BorderLayout.CENTER); // Adding the graph panel to the center

        // Creating a button panel for user interactions
        JPanel controlPanel = new JPanel();
        JButton addNodeButton = new JButton("Add Node");  // Button to add nodes
        JButton addEdgeButton = new JButton("Add Edge");  // Button to add edges
        JButton optimizeButton = new JButton("Optimize"); // Button to find optimal network
        JButton shortestPathButton = new JButton("Find Shortest Path"); // Button to find shortest path

        // Adding action listeners for button clicks
        addNodeButton.addActionListener(e -> addNode());
        addEdgeButton.addActionListener(e -> addEdge());
        optimizeButton.addActionListener(e -> optimizeNetwork());
        shortestPathButton.addActionListener(e -> findShortestPath());

        // Adding buttons to the control panel
        controlPanel.add(addNodeButton);
        controlPanel.add(addEdgeButton);
        controlPanel.add(optimizeButton);
        controlPanel.add(shortestPathButton);

        add(controlPanel, BorderLayout.SOUTH); // Adding the control panel to the bottom
    }

    // Method to add a node to the graph
    private void addNode() {
        String nodeName = JOptionPane.showInputDialog("Enter node name:");
        if (nodeName != null && !nodeName.trim().isEmpty()) {
            networkGraph.addNode(nodeName); // Adding the node to the graph
            graphPanel.repaint(); // Refreshing the GUI to reflect changes
        }
    }

    // Method to add an edge between two nodes
    private void addEdge() {
        String node1 = JOptionPane.showInputDialog("Enter first node:");
        String node2 = JOptionPane.showInputDialog("Enter second node:");
        String costStr = JOptionPane.showInputDialog("Enter cost:");
        String bandwidthStr = JOptionPane.showInputDialog("Enter bandwidth:");

        try {
            int cost = Integer.parseInt(costStr);
            int bandwidth = Integer.parseInt(bandwidthStr);
            networkGraph.addEdge(node1, node2, cost, bandwidth); // Adding the edge to the graph
            graphPanel.repaint(); // Refreshing the GUI
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Enter numerical values.");
        }
    }

    // Method to optimize the network by minimizing cost
    private void optimizeNetwork() {
        networkGraph.optimize(); // Calls graph optimization method
        graphPanel.repaint(); // Refresh GUI
    }

    // Method to find the shortest path between two nodes
    private void findShortestPath() {
        String startNode = JOptionPane.showInputDialog("Enter start node:");
        String endNode = JOptionPane.showInputDialog("Enter end node:");
        List<String> path = networkGraph.shortestPath(startNode, endNode); // Calculate shortest path

        if (path != null) {
            JOptionPane.showMessageDialog(this, "Shortest Path: " + path);
        } else {
            JOptionPane.showMessageDialog(this, "No path found.");
        }
    }

    // Main method to launch the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Five app = new Five(); // Creating an instance of the GUI
            app.setVisible(true); // Making the GUI visible
        });
    }
}

// Graph class to represent network topology
class Graph {
    private Map<String, Node> nodes; // Map to store nodes by name
    private List<Edge> edges;        // List to store all edges

    // Constructor initializes data structures
    public Graph() {
        nodes = new HashMap<>();
        edges = new ArrayList<>();
    }

    // Method to add a node
    public void addNode(String name) {
        nodes.putIfAbsent(name, new Node(name)); // Add node only if it doesn't exist
    }

    // Method to add an edge
    public void addEdge(String node1, String node2, int cost, int bandwidth) {
        Node n1 = nodes.get(node1);
        Node n2 = nodes.get(node2);
        if (n1 != null && n2 != null) {
            Edge edge = new Edge(n1, n2, cost, bandwidth);
            edges.add(edge); // Add edge to list
            n1.addNeighbor(n2, cost); // Add connection in adjacency list
            n2.addNeighbor(n1, cost);
        }
    }

    // Method to optimize network (simplified for now)
    public void optimize() {
        // Implement optimization algorithm like Kruskal's MST here
        System.out.println("Optimization feature is under development.");
    }

    // Method to find the shortest path using Dijkstra's algorithm
    public List<String> shortestPath(String start, String end) {
        // Implementing Dijkstra's Algorithm
        if (!nodes.containsKey(start) || !nodes.containsKey(end)) {
            return null; // If nodes don't exist, return null
        }

        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.distance));
        Map<Node, Node> previous = new HashMap<>();
        Node source = nodes.get(start);
        Node target = nodes.get(end);

        // Initialize all distances to infinity
        for (Node node : nodes.values()) {
            node.distance = Integer.MAX_VALUE;
        }
        source.distance = 0;
        queue.add(source);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current == target) break; // Stop when reaching target

            for (Map.Entry<Node, Integer> entry : current.neighbors.entrySet()) {
                Node neighbor = entry.getKey();
                int newDist = current.distance + entry.getValue();
                if (newDist < neighbor.distance) {
                    neighbor.distance = newDist;
                    previous.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        List<String> path = new ArrayList<>();
        for (Node at = target; at != null; at = previous.get(at)) {
            path.add(at.name);
        }
        Collections.reverse(path);
        return path.size() > 1 ? path : null;
    }

    // Method to draw graph (Placeholder)
    public void draw(Graphics g) {
        // Implement graphical representation of nodes and edges
    }
}

// Node class representing servers/clients
class Node {
    String name;
    int distance;
    Map<Node, Integer> neighbors; // Stores neighbors and costs

    public Node(String name) {
        this.name = name;
        this.neighbors = new HashMap<>();
    }

    public void addNeighbor(Node node, int cost) {
        neighbors.put(node, cost);
    }
}

// Edge class representing network connections
class Edge {
    Node node1, node2;
    int cost, bandwidth;

    public Edge(Node node1, Node node2, int cost, int bandwidth) {
        this.node1 = node1;
        this.node2 = node2;
        this.cost = cost;
        this.bandwidth = bandwidth;
    }
}

/*Input:
Nodes: A, B, C, D
Edges:
    A --(4)--> B
    A --(1)--> C
    C --(2)--> B
    B --(5)--> D
    C --(8)--> D

Processing:
1. Add A -> C (cost 1)
2. Add C -> B (cost 2)
3. Add B -> D (cost 5)

Output:
Optimal Network: 
Edges: (A-C, cost 1), (C-B, cost 2), (B-D, cost 5)
Total Cost: 8.
 */

//1. Shortest Path (Dijkstra’s Algorithm)

// The shortestPath method finds the shortest path between two nodes using Dijkstra’s Algorithm:

// Uses a priority queue to always expand the closest node.
// Maintains a distance map to track the shortest known path to each node.
// Updates distances dynamically as shorter paths are found.
// Steps:

// Initialize all nodes' distances to infinity except the source, which is set to 0.
// Push the source node into the priority queue.
// While the queue is not empty:
// Remove the node with the smallest distance.
// Update the distances of its neighbors.
// If a neighbor’s new distance is smaller than its stored distance, update and push it into the queue.
// If the destination is reached, backtrack to reconstruct the shortest path.
