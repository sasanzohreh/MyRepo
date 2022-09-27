import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Map;
import java.util.HashMap;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Sasan Zohreh
 * @version 1.0
 * @userid szohreh3
 * @GTID 903402201
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Cannot perform search on null vertex or null graph");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Vertex was not found in graph");
        }
        List<Vertex<T>> visitedInOrder = new ArrayList<>(graph.getVertices().size());
        Set<Vertex<T>> visited = new HashSet<>(graph.getVertices().size());
        Queue<Vertex<T>> queue = new LinkedList<>();
        visitedInOrder.add(start);
        visited.add(start);
        queue.add(start);
        while (!queue.isEmpty()) {
            Vertex<T> v = queue.remove();
            //check all of the adjacent vertices of the current vertex
            for (VertexDistance<T> vertex : graph.getAdjList().get(v)) {
                if (!visited.contains(vertex.getVertex())) {
                    visitedInOrder.add(vertex.getVertex());
                    visited.add(vertex.getVertex());
                    queue.add(vertex.getVertex());
                }
            }
        }
        return visitedInOrder;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Cannot perform search on null vertex or null graph");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Vertex was not found in graph");
        }
        List<Vertex<T>> visitedInOrder = new ArrayList<>(graph.getVertices().size());
        Set<Vertex<T>> visited = new HashSet<>(graph.getVertices().size());
        dfsHelper(start, graph, visitedInOrder, visited);
        return visitedInOrder;
    }

    /**
     * Recursive helper method for depth first search
     *
     * @param <T> generic typing of data
     * @param current the current vertex to perform dfs on
     * @param graph the graph to search through
     * @param visitedInOrder a list of all of the vertices visited
     * @param visited a set of all of the vertices visited
     * */
    private static <T> void dfsHelper(Vertex<T> current, Graph<T> graph, List<Vertex<T>> visitedInOrder,
                                      Set<Vertex<T>> visited) {
        visitedInOrder.add(current);
        visited.add(current);
        //check all of the adjacent vertices of the current vertex
        for (VertexDistance<T> vertex : graph.getAdjList().get(current)) {
            if (!visited.contains(vertex.getVertex())) {
                dfsHelper(vertex.getVertex(), graph, visitedInOrder, visited);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Cannot perform search on null vertex or null graph");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Vertex was not found in graph");
        }
        Set<Vertex<T>> visited = new HashSet<>(graph.getVertices().size());
        Queue<VertexDistance<T>> pQueue = new PriorityQueue<>(graph.getVertices().size());
        Map<Vertex<T>, Integer> distanceMap = new HashMap<>(graph.getVertices().size());
        //set the shortest distance of each vertex as max value
        for (Vertex<T> vertex : graph.getVertices()) {
            distanceMap.put(vertex, Integer.MAX_VALUE);
        }
        pQueue.add(new VertexDistance<T>(start, 0));
        while (!pQueue.isEmpty() && visited.size() != graph.getVertices().size()) {
            VertexDistance<T> vertex = pQueue.remove();
            if (!visited.contains(vertex.getVertex())) {
                visited.add(vertex.getVertex());
                distanceMap.replace(vertex.getVertex(), vertex.getDistance());
                //check all of the adjacent vertices of the current vertex
                for (VertexDistance<T> current : graph.getAdjList().get(vertex.getVertex())) {
                    if (!visited.contains(current.getVertex())) {
                        pQueue.add(new VertexDistance<T>(current.getVertex(), current.getDistance()
                                + vertex.getDistance()));
                    }
                }
            }
        }
        return distanceMap;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops or parallel edges into the MST.
     *
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Cannot create an MST from a null graph");
        }
        Queue<Edge<T>> pQueue = new PriorityQueue<>(graph.getEdges());
        Set<Edge<T>> mst = new HashSet<>(2 * (graph.getVertices().size() - 1));
        DisjointSet<T> disjointSet = new DisjointSet<>();
        //add all vertices to the disjoint set
        for (Vertex<T> vertex : graph.getVertices()) {
            disjointSet.find(vertex.getData());
        }
        while (!pQueue.isEmpty() && mst.size() < 2 * (graph.getVertices().size() - 1)) {
            Edge<T> edge = pQueue.remove();
            if (disjointSet.find(edge.getU()) != disjointSet.find(edge.getV())) {
                //graph is undirected so add the edge along with a version with the vertices switched
                mst.add(edge);
                mst.add(new Edge<T>(edge.getV(), edge.getU(), edge.getWeight()));
                disjointSet.union(edge.getU().getData(), edge.getV().getData());
            }
        }
        if (mst.size() != 2 * (graph.getVertices().size() - 1)) {
            return null;
        }
        return mst;
    }

}
