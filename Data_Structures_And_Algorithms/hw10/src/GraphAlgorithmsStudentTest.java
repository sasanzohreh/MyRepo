import org.junit.*;
import org.junit.rules.Timeout;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;


public class GraphAlgorithmsStudentTest {

    private static final int TIMEOUT = 200;
    private Graph<Integer> directedGraph;
    private Graph<Character> undirectedGraph;

    @Before
    public void init() {
        directedGraph = createDirectedGraph();
        undirectedGraph = createUndirectedGraph();
    }

    /**
     * Creates a directed graph.
     * The graph is depicted in the pdf.
     *
     * @return the completed graph
     */
    private Graph<Integer> createDirectedGraph() {
        Set<Vertex<Integer>> vertices = new HashSet<>();
        for (int i = 1; i <= 7; i++) {
            vertices.add(new Vertex<>(i));
        }

        Set<Edge<Integer>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(2), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(5), 0));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(6), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(7), 0));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(6), 0));

        return new Graph<>(vertices, edges);
    }

    /**
     * Creates an undirected graph.
     * The graph is depicted in the pdf.
     *
     * @return the completed graph
     */
    private Graph<Character> createUndirectedGraph() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        for (int i = 65; i <= 70; i++) {
            vertices.add(new Vertex<>((char) i));
        }

        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 7));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 7));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 5));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('A'), 5));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('D'), 2));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('C'), 2));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 4));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('A'), 4));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('E'), 1));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('D'), 1));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('E'), 3));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('B'), 3));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('F'), 8));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('B'), 8));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('F'), 6));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('E'), 6));

        return new Graph<>(vertices, edges);
    }

    @Test(timeout = TIMEOUT)
    public void testBFS() {
        List<Vertex<Integer>> bfsActual = GraphAlgorithms.bfs(
            new Vertex<>(1), directedGraph);

        List<Vertex<Integer>> bfsExpected = new LinkedList<>();
        bfsExpected.add(new Vertex<>(1));
        bfsExpected.add(new Vertex<>(2));
        bfsExpected.add(new Vertex<>(3));
        bfsExpected.add(new Vertex<>(4));
        bfsExpected.add(new Vertex<>(5));
        bfsExpected.add(new Vertex<>(6));
        bfsExpected.add(new Vertex<>(7));

        assertEquals(bfsExpected, bfsActual);
    }

    @Test(timeout = TIMEOUT)
    public void testDFS() {
        List<Vertex<Integer>> dfsActual =
            GraphAlgorithms.dfs(new Vertex<>(5), directedGraph);

        List<Vertex<Integer>> dfsExpected = new LinkedList<>();
        dfsExpected.add(new Vertex<>(5));
        dfsExpected.add(new Vertex<>(4));
        dfsExpected.add(new Vertex<>(6));
        dfsExpected.add(new Vertex<>(7));

        assertEquals(dfsExpected, dfsActual);
    }

    @Test(timeout = TIMEOUT)
    public void testDijkstras() {
        Map<Vertex<Character>, Integer> dijkActual = GraphAlgorithms.dijkstras(
            new Vertex<>('D'), undirectedGraph);

        Map<Vertex<Character>, Integer> dijkExpected = new HashMap<>();
        dijkExpected.put(new Vertex<>('A'), 4);
        dijkExpected.put(new Vertex<>('B'), 4);
        dijkExpected.put(new Vertex<>('C'), 2);
        dijkExpected.put(new Vertex<>('D'), 0);
        dijkExpected.put(new Vertex<>('E'), 1);
        dijkExpected.put(new Vertex<>('F'), 7);

        assertEquals(dijkExpected, dijkActual);
    }

    @Test(timeout = TIMEOUT)
    public void testKruskals() {
        Set<Edge<Character>> mstActual = GraphAlgorithms.kruskals(
            undirectedGraph);

        Set<Edge<Character>> mstExpected = new HashSet<>();
        mstExpected.add(new Edge<>(new Vertex<>('C'), new Vertex<>('D'), 2));
        mstExpected.add(new Edge<>(new Vertex<>('D'), new Vertex<>('C'), 2));
        mstExpected.add(new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 4));
        mstExpected.add(new Edge<>(new Vertex<>('D'), new Vertex<>('A'), 4));
        mstExpected.add(new Edge<>(new Vertex<>('D'), new Vertex<>('E'), 1));
        mstExpected.add(new Edge<>(new Vertex<>('E'), new Vertex<>('D'), 1));
        mstExpected.add(new Edge<>(new Vertex<>('B'), new Vertex<>('E'), 3));
        mstExpected.add(new Edge<>(new Vertex<>('E'), new Vertex<>('B'), 3));
        mstExpected.add(new Edge<>(new Vertex<>('E'), new Vertex<>('F'), 6));
        mstExpected.add(new Edge<>(new Vertex<>('F'), new Vertex<>('E'), 6));

        assertEquals(mstExpected, mstActual);
    }

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() {
        assertTrue(outContent.toString().isEmpty());
    }

    /**
     *    A - B
     *    | /
     *    C - E
     *    |
     *    D
     *  <a>https://gatech.instructure.com/courses/141902/pages/breadth-first-search-example-1?module_item_id=1048714</a>
     */
    @Test
    public void testBfsExampleOne() {
        Vertex<Character> start = new Vertex<>('A');
        Graph<Character> graph = createGraph(Stream.of(
                new Vertex<>('A'),
                new Vertex<>('B'),
                new Vertex<>('C'),
                new Vertex<>('D'),
                new Vertex<>('E')
        ).collect(Collectors.toSet()), interpolateEdges(new Object[][]{
                {'A', 'B', 0}, {'B', 'A', 0},
                {'B', 'C', 0}, {'C', 'B', 0},
                {'C', 'D', 0}, {'D', 'C', 0},
                {'C', 'E', 0}, {'E', 'C', 0},
                {'A', 'C', 0}, {'C', 'A', 0}
        }));
        List<Vertex<Character>> expected = listOfVertices('A', 'B', 'C', 'D', 'E');

        List<Vertex<Character>> actual = GraphAlgorithms.bfs(start, graph);

        assertThat(actual, is(equalTo(expected)));
    }

    /**
     *         B -- C -- D
     *        /|   / \   | \
     *      A  |  I   \  |  E
     *        \| / \   \ | /
     *         H -- G -- F
     *  <a>https://gatech.instructure.com/courses/141902/pages/breadth-first-search-example-2?module_item_id=1048716</a>
     */
    @Test
    public void testBfsExampleTwo() {
        Vertex<Character> start = new Vertex<>('A');
        Graph<Character> graph = createGraph(Stream.of(
                new Vertex<>('A'),
                new Vertex<>('B'),
                new Vertex<>('C'),
                new Vertex<>('D'),
                new Vertex<>('E'),
                new Vertex<>('F'),
                new Vertex<>('H'),
                new Vertex<>('I'),
                new Vertex<>('G')
        ).collect(Collectors.toSet()), interpolateEdges(new Object[][]{
                {'A', 'B', 0}, {'B', 'A', 0},
                {'A', 'H', 0}, {'H', 'A', 0},
                {'B', 'C', 0}, {'C', 'B', 0},
                {'G', 'H', 0}, {'H', 'G', 0},
                {'H', 'I', 0}, {'I', 'H', 0},
                {'C', 'D', 0}, {'D', 'C', 0},
                {'C', 'F', 0}, {'F', 'C', 0},
                {'D', 'E', 0}, {'E', 'D', 0},
                {'E', 'F', 0}, {'F', 'E', 0},
                {'F', 'G', 0}, {'G', 'F', 0},
                {'I', 'G', 0}, {'G', 'I', 0},
                {'F', 'D', 0}, {'D', 'F', 0},
                {'B', 'H', 0}, {'H', 'B', 0},
                {'I', 'C', 0}, {'C', 'I', 0},
        }));
        List<Vertex<Character>> expected = listOfVertices('A', 'B', 'H', 'C', 'G', 'I', 'D', 'F', 'E');

        List<Vertex<Character>> actual = GraphAlgorithms.bfs(start, graph);

        assertThat(actual, is(equalTo(expected)));
    }

    /**
     *    A - B
     *    | /
     *    C - E
     *    |
     *    D
     *    <a>https://gatech.instructure.com/courses/141902/pages/depth-first-search-example-1?module_item_id=1048710</a>
     */
    @Test
    public void testDfsExampleOne() {
        Vertex<Character> start = new Vertex<>('A');
        Graph<Character> graph = createGraph(Stream.of(
                new Vertex<>('A'),
                new Vertex<>('B'),
                new Vertex<>('C'),
                new Vertex<>('D'),
                new Vertex<>('E')
        ).collect(Collectors.toSet()), interpolateEdges(new Object[][]{
                {'A', 'B', 0}, {'B', 'A', 0},
                {'B', 'C', 0}, {'C', 'B', 0},
                {'C', 'D', 0}, {'D', 'C', 0},
                {'C', 'E', 0}, {'E', 'C', 0},
                {'A', 'C', 0}, {'C', 'A', 0}
        }));
        List<Vertex<Character>> expected = listOfVertices('A', 'B', 'C', 'D', 'E');

        List<Vertex<Character>> actual = GraphAlgorithms.dfs(start, graph);

        assertThat(actual, is(equalTo(expected)));
    }

    /**
     *         B -- C -- D
     *        /|   / \   | \
     *      A  |  I   \  |  E
     *        \| / \   \ | /
     *         J -- H -- F
     *  <a>https://gatech.instructure.com/courses/141902/pages/depth-first-search-example-2?module_item_id=1048712</a>
     */
    @Test
    public void testDfsExampleTwo() {
        Vertex<Character> start = new Vertex<>('A');
        Graph<Character> graph = createGraph(Stream.of(
                new Vertex<>('A'),
                new Vertex<>('B'),
                new Vertex<>('C'),
                new Vertex<>('D'),
                new Vertex<>('E'),
                new Vertex<>('F'),
                new Vertex<>('H'),
                new Vertex<>('I'),
                new Vertex<>('J')
        ).collect(Collectors.toSet()), interpolateEdges(new Object[][]{
                {'A', 'B', 0}, {'B', 'A', 0},
                {'B', 'C', 0}, {'C', 'B', 0},
                {'C', 'D', 0}, {'D', 'C', 0},
                {'D', 'E', 0}, {'E', 'D', 0},
                {'E', 'F', 0}, {'F', 'E', 0},
                {'F', 'H', 0}, {'H', 'F', 0},
                {'H', 'I', 0}, {'I', 'H', 0},
                {'I', 'J', 0}, {'J', 'I', 0},
                {'F', 'D', 0}, {'D', 'F', 0},
                {'A', 'J', 0}, {'J', 'A', 0},
                {'B', 'J', 0}, {'J', 'B', 0},
                {'I', 'C', 0}, {'C', 'I', 0},
                {'J', 'H', 0}, {'H', 'J', 0},
                {'C', 'F', 0}, {'F', 'C', 0},
        }));
        List<Vertex<Character>> expected = listOfVertices('A', 'B', 'C', 'D', 'E', 'F', 'H', 'I', 'J');

        List<Vertex<Character>> actual = GraphAlgorithms.dfs(start, graph);

        assertThat(actual, is(equalTo(expected)));
    }

    /**       1
     *      A - B
     *    3 | / 1
     *      C
     *    0 | \ 4
     *      D - E
     *        4
     *   <a>https://gatech.instructure.com/courses/141902/pages/dijkstras-algorithm-example-1?module_item_id=1048722</a>
     */
    @Test
    public void testDijkstrasExampleOne() {
        Vertex<Character> start = new Vertex<>('A');
        Graph<Character> graph = createGraph(Stream.of(
                new Vertex<>('A'),
                new Vertex<>('B'),
                new Vertex<>('C'),
                new Vertex<>('D'),
                new Vertex<>('E')
        ).collect(Collectors.toSet()), interpolateEdges(new Object[][]{
                {'A', 'B', 1}, {'B', 'A', 1},
                {'B', 'C', 1}, {'C', 'B', 1},
                {'C', 'D', 0}, {'D', 'C', 0},
                {'C', 'E', 4}, {'E', 'C', 4},
                {'A', 'C', 3}, {'C', 'A', 3},
                {'D', 'E', 4}, {'E', 'D', 4}
        }));
        Map<Vertex<Character>, Integer> expected = Map.of(
                new Vertex<>('A'), 0,
                new Vertex<>('B'), 1,
                new Vertex<>('C'), 2,
                new Vertex<>('D'), 2,
                new Vertex<>('E'), 6
        );

        Map<Vertex<Character>, Integer> actual = GraphAlgorithms.dijkstras(start, graph);

        assertThat(actual, is(equalTo(expected)));
    }

    /**
     *          B
     *     -1 /   \ -4
     *      A       D
     *     -2 \   / -8
     *          C
     *   <a>https://gatech.instructure.com/courses/141902/pages/dijkstras-algorithm-example-2?module_item_id=1048724</a>
     */
    @Test
    @Ignore
    public void testDijkstrasExampleTwo() {
        Vertex<Character> start = new Vertex<>('A');
        Graph<Character> graph = createGraph(Stream.of(
                new Vertex<>('A'),
                new Vertex<>('B'),
                new Vertex<>('C'),
                new Vertex<>('D')
        ).collect(Collectors.toSet()), interpolateEdges(new Object[][]{
                {'A', 'B', -1},
                {'A', 'C', -2},
                {'B', 'D', -4},
                {'C', 'D', -8},
        }));
        Map<Vertex<Character>, Integer> expected = Map.of(
                new Vertex<>('A'), 0,
                new Vertex<>('B'), -1,
                new Vertex<>('C'), -2,
                new Vertex<>('D'), -10
        );

        Map<Vertex<Character>, Integer> actual = GraphAlgorithms.dijkstras(start, graph);

        assertThat(actual, is(equalTo(expected)));
    }

    /**          8    7
     *         B -- C -- D
     *      4 /|11 /2\   | \ 9
     *      A  |  I   \4 |14E
     *      8 \|7/ \6  \ | / 10
     *         H -- G -- F
     *           1     2
     *  <a>https://gatech.instructure.com/courses/141902/pages/dijkstras-algorithm-example-3?module_item_id=1048726</a>
     */
    @Test
    public void testDijkstrasExampleThree() {
        Vertex<Character> start = new Vertex<>('A');
        Graph<Character> graph = createGraph(Stream.of(
                new Vertex<>('A'),
                new Vertex<>('B'),
                new Vertex<>('C'),
                new Vertex<>('D'),
                new Vertex<>('E'),
                new Vertex<>('F'),
                new Vertex<>('H'),
                new Vertex<>('I'),
                new Vertex<>('G')
        ).collect(Collectors.toSet()), interpolateEdges(new Object[][]{
                {'A', 'B', 4}, {'B', 'A', 4},
                {'A', 'H', 8}, {'H', 'A', 8},
                {'B', 'C', 8}, {'C', 'B', 8},
                {'G', 'H', 1}, {'H', 'G', 1},
                {'H', 'I', 7}, {'I', 'H', 7},
                {'C', 'D', 7}, {'D', 'C', 7},
                {'C', 'F', 4}, {'F', 'C', 4},
                {'D', 'E', 9}, {'E', 'D', 9},
                {'E', 'F', 10}, {'F', 'E', 10},
                {'F', 'G', 2}, {'G', 'F', 2},
                {'I', 'G', 6}, {'G', 'I', 6},
                {'F', 'D', 14}, {'D', 'F', 14},
                {'B', 'H', 11}, {'H', 'B', 11},
                {'I', 'C', 2}, {'C', 'I', 2},
        }));
        Map<Vertex<Character>, Integer> expected = Map.of(
                new Vertex<>('A'), 0,
                new Vertex<>('B'), 4,
                new Vertex<>('C'), 12,
                new Vertex<>('D'), 19,
                new Vertex<>('E'), 21,
                new Vertex<>('F'), 11,
                new Vertex<>('G'), 9,
                new Vertex<>('H'), 8,
                new Vertex<>('I'), 14
        );

        Map<Vertex<Character>, Integer> actual = GraphAlgorithms.dijkstras(start, graph);

        assertThat(actual, is(equalTo(expected)));
    }

    /**          8    7
     *         B -- C -- D
     *      4 /|11 /2\   | \ 9
     *      A  |  I   \4 |14E
     *      8 \|7/ \6  \ | / 10
     *         H -- G -- F
     *           1     2
     *             3
     *           X -- Y
     *  <a>https://gatech.instructure.com/courses/141902/pages/kruskals-algorithm-example-1?module_item_id=1048750</a>
     */
    @Test
    public void testKruskalsExampleOne() {
        Graph<Character> graph = createGraph(Stream.of(
                new Vertex<>('A'),
                new Vertex<>('B'),
                new Vertex<>('C'),
                new Vertex<>('D'),
                new Vertex<>('E'),
                new Vertex<>('F'),
                new Vertex<>('H'),
                new Vertex<>('I'),
                new Vertex<>('G'),
                new Vertex<>('X'),
                new Vertex<>('Y')
        ).collect(Collectors.toSet()), interpolateEdges(new Object[][]{
                {'A', 'B', 4}, {'B', 'A', 4},
                {'A', 'H', 8}, {'H', 'A', 8},
                {'B', 'C', 8}, {'C', 'B', 8},
                {'G', 'H', 1}, {'H', 'G', 1},
                {'H', 'I', 7}, {'I', 'H', 7},
                {'C', 'D', 7}, {'D', 'C', 7},
                {'C', 'F', 4}, {'F', 'C', 4},
                {'D', 'E', 9}, {'E', 'D', 9},
                {'E', 'F', 10}, {'F', 'E', 10},
                {'F', 'G', 2}, {'G', 'F', 2},
                {'I', 'G', 6}, {'G', 'I', 6},
                {'F', 'D', 14}, {'D', 'F', 14},
                {'B', 'H', 11}, {'H', 'B', 11},
                {'I', 'C', 2}, {'C', 'I', 2},
                {'X', 'Y', 3}, {'Y', 'X', 3}
        }));
        Set<Edge<Character>> expected = null;

        Set<Edge<Character>> actual = GraphAlgorithms.kruskals(graph);

        assertThat(actual, is(equalTo(expected)));
    }

    /**       6      3
     *      A -- B -------- D
     *      |\7     4     / |
     *    5 | C ---------   | 16
     *      |  \2           |
     *      E -- F      --- J
     *      | 1  | \5 8/  / |
     *    8 |  10|  I  --   | 11
     *      |    |6//   9   |
     *      G -- H -------- K
     *        14    10
     *    <a>https://gatech.instructure.com/courses/141902/pages/kruskals-algorithm-example-2?module_item_id=1048752</a>
     */
    @Test
    public void testKruskalsExampleTwo() {
        Graph<Character> graph = new Graph<>(Stream.of(
                new Vertex<>('A'),
                new Vertex<>('B'),
                new Vertex<>('C'),
                new Vertex<>('D'),
                new Vertex<>('E'),
                new Vertex<>('F'),
                new Vertex<>('G'),
                new Vertex<>('H'),
                new Vertex<>('I'),
                new Vertex<>('J'),
                new Vertex<>('K')
        ).collect(Collectors.toSet()), interpolateEdges(new Object[][]{
                {'A', 'B', 6}, {'B', 'A', 6},
                {'B', 'D', 3}, {'D', 'B', 3},
                {'A', 'C', 7}, {'C', 'A', 7},
                {'C', 'D', 4}, {'D', 'C', 4},
                {'A', 'E', 5}, {'E', 'A', 5},
                {'C', 'F', 2}, {'F', 'C', 2},
                {'E', 'F', 1}, {'F', 'E', 1},
                {'E', 'G', 8}, {'G', 'E', 8},
                {'G', 'H', 14}, {'H', 'G', 14},
                {'F', 'H', 10}, {'H', 'F', 10},
                {'H', 'I', 6}, {'I', 'H', 6},
                {'F', 'I', 5}, {'I', 'F', 5},
                {'I', 'J', 8}, {'J', 'I', 8},
                {'H', 'J', 9}, {'J', 'H', 9},
                {'D', 'J', 16}, {'J', 'D', 16},
                {'J', 'K', 11}, {'K', 'J', 11},
                {'H', 'K', 10}, {'K', 'H', 10}
        }));
        Set<Edge<Character>> expected = interpolateEdges(new Object[][]{
                {'F', 'E', 1}, {'E', 'F', 1},
                {'C', 'F', 2}, {'F', 'C', 2},
                {'B', 'D', 3}, {'D', 'B', 3},
                {'C', 'D', 4}, {'D', 'C', 4},
                {'A', 'E', 5}, {'E', 'A', 5},
                {'F', 'I', 5}, {'I', 'F', 5},
                {'H', 'I', 6}, {'I', 'H', 6},
                {'I', 'J', 8}, {'J', 'I', 8},
                {'E', 'G', 8}, {'G', 'E', 8},
                {'H', 'K', 10}, {'K', 'H', 10}
        });

        Set<Edge<Character>> actual = GraphAlgorithms.kruskals(graph);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBfsThrowsIllegalArgumentExceptionForNullStart() {
        GraphAlgorithms.bfs(null, new Graph<>(new HashSet<>(), new HashSet<>()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBfsThrowsIllegalArgumentExceptionForNullGraph() {
        GraphAlgorithms.bfs(new Vertex<>(""), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBfsThrowsIllegalArgumentExceptionForNonExistentStart() {
        GraphAlgorithms.bfs(new Vertex<>('X'), new Graph<>(new HashSet<>(), new HashSet<>()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDfsThrowsIllegalArgumentExceptionForNullStart() {
        GraphAlgorithms.dfs(null, new Graph<>(new HashSet<>(), new HashSet<>()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDfsThrowsIllegalArgumentExceptionForNullGraph() {
        GraphAlgorithms.dfs(new Vertex<>(""), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDfsThrowsIllegalArgumentExceptionForNonExistentStart() {
        GraphAlgorithms.dfs(new Vertex<>('X'), new Graph<>(new HashSet<>(), new HashSet<>()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDijkstrasThrowsIllegalArgumentExceptionForNullStart() {
        GraphAlgorithms.dijkstras(null, new Graph<>(new HashSet<>(), new HashSet<>()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDijkstrasThrowsIllegalArgumentExceptionForNullGraph() {
        GraphAlgorithms.dijkstras(new Vertex<>(""), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDijkstrasThrowsIllegalArgumentExceptionForNonExistentStart() {
        GraphAlgorithms.dijkstras(new Vertex<>('X'), new Graph<>(new HashSet<>(), new HashSet<>()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testKruskalsThrowsIllegalArgumentExceptionForNullGraph() {
        GraphAlgorithms.kruskals(null);
    }

    /**
     *
     * @param vertices vertices
     * @param edges edges
     * @return graph from inputs
     */
    private Graph<Character> createGraph(Set<Vertex<Character>> vertices, Set<Edge<Character>> edges) {
        return new Graph<>(vertices, edges);
    }

    /**
     *
     * @param array 2d array representation of edges
     * @return set of edges specified by input array
     */
    private Set<Edge<Character>> interpolateEdges(Object[][] array) {
        Set<Edge<Character>> edges = new LinkedHashSet<>();
        for (Object[] objects : array) {
            edges.add(new Edge<>(new Vertex<>((char) objects[0]), new Vertex<>((char) objects[1]), (int) objects[2]));
        }
        return edges;
    }

    /**
     *
     * @param chars characters
     * @return list of vertices
     */
    private List<Vertex<Character>> listOfVertices(char... chars) {
        List<Vertex<Character>> vertices = new ArrayList<>(chars.length);
        for (char character : chars) {
            vertices.add(new Vertex<>(character));
        }
        return vertices;
    }

    private Graph<Character> undirectedGraph1;
    private Graph<Character> undirectedGraph2;
    private Graph<Character> directedGraph1;
    private Graph<Character> directedGraph2;


    @Before
    public void initGraph() {

        /*
            d ------ y  k
             \       | /
              \      |/
               m     z
         */
        Set<Vertex<Character>> vertices1 = setOfVertices('d', 'k', 'm', 'y', 'z');
        Set<Edge<Character>> edges1 = interpolateEdges2(new Object[][]{
                {'d', 'm', 0}, {'d', 'y', 0}, {'k', 'z', 0}, {'y', 'd', 0},
                {'y', 'z', 0}, {'m', 'd', 0}, {'z', 'k', 0}, {'z', 'y', 0}
        });
        this.undirectedGraph1 = new Graph<>(vertices1, edges1);

        /*
                5   3
              A----B----C
              |  2 |    |
            1 |    F    | 1
              | 6 / \ 3 |
              |  /   \  |
              D---------E
                   7
         */
        Set<Vertex<Character>> vertices2 = setOfVertices('a', 'b', 'c', 'd',
                'e', 'f');
        Set<Edge<Character>> edges2 = interpolateEdges2(new Object[][]{
                {'a', 'b', 5}, {'a', 'd', 1}, {'b', 'a', 5}, {'b', 'c', 3},
                {'b', 'f', 2}, {'c', 'b', 3}, {'c', 'e', 1}, {'d', 'a', 1},
                {'d', 'e', 7}, {'d', 'f', 6}, {'e', 'c', 1}, {'e', 'd', 7},
                {'e', 'f', 3}, {'f', 'b', 2}, {'f', 'd', 6}, {'f', 'e', 3}
        });
        this.undirectedGraph2 = new Graph<>(vertices2, edges2);
    }

    @Test(timeout = TIMEOUT)
    public void testDfs1() {
        List<Vertex<Character>> expected = listOfVertices2('y', 'd', 'm', 'z', 'k');
        assertEquals(expected,
                GraphAlgorithms.dfs(new Vertex<>('y'), undirectedGraph1));
    }

    @Test(timeout = TIMEOUT)
    public void testDfs2() {
        List<Vertex<Character>> expected = listOfVertices2('d', 'a', 'b', 'c',
                'e', 'f');
        assertEquals(expected,
                GraphAlgorithms.dfs(new Vertex<>('d'), undirectedGraph2));
    }

    @Test(timeout = TIMEOUT)
    public void testBfs1() {
        List<Vertex<Character>> expected = listOfVertices2('z', 'k', 'y', 'd', 'm');
        assertEquals(expected,
                GraphAlgorithms.bfs(new Vertex<>('z'), undirectedGraph1));
    }

    @Test(timeout = TIMEOUT)
    public void testBfs2() {
        List<Vertex<Character>> expected = listOfVertices2('d', 'a', 'e', 'f',
                'b', 'c');
        assertEquals(expected,
                GraphAlgorithms.bfs(new Vertex<>('d'), undirectedGraph2));
    }

    @Test(timeout = TIMEOUT)
    public void testDjikstra1() {


        /*       6     4
              H - - K - - M
          2 / |         /  \ 3
           /  |       /     \
         D  1 |   2 /         L
           \  |   /           |
         5  \ | /             |
             \|/      6       |
              C --------------
         */

        Set<Vertex<Character>> vertices3 = setOfVertices('d', 'h', 'k', 'm',
                'c', 'l');
        Set<Edge<Character>> edges3 = interpolateEdges2(new Object[][]{
                {'d', 'h', 2}, {'d', 'c', 5}, {'h', 'c', 1}, {'h', 'k', 6},
                {'c', 'm', 2}, {'c', 'l', 6}, {'k', 'm', 4}, {'m', 'l', 3}
        });
        this.directedGraph1 = new Graph<>(vertices3, edges3);

        Map<Vertex<Character>, Integer> expected = Map.of(
                new Vertex<>('d'), 0,
                new Vertex<>('h'), 2,
                new Vertex<>('k'), 8,
                new Vertex<>('c'), 3,
                new Vertex<>('m'), 5,
                new Vertex<>('l'), 8
        );

        assertEquals(expected,
                GraphAlgorithms.dijkstras(new Vertex<>('d'), directedGraph1));
    }

    @Test(timeout = TIMEOUT)
    public void testDjikstra2() {


        /*          5
            B ------------ C
         3 /                \  8
          /  1     7      2  \
         A ---- D ---- E ---- H
          \                  /
         2 \        4       / 6
            F ------------ G
         */

        Set<Vertex<Character>> vertices4 = setOfVertices('a', 'b', 'c', 'd',
                'e', 'f', 'g', 'h');
        Set<Edge<Character>> edges4 = interpolateEdges2(new Object[][]{
                {'a', 'b', 3}, {'b', 'c', 5}, {'c', 'h', 8},
                {'a', 'd', 1}, {'d', 'e', 7}, {'e', 'h', 2},
                {'a', 'f', 2}, {'f', 'g', 4}, {'g', 'h', 6}
        });

        this.directedGraph2 = new Graph<>(vertices4, edges4);

        Map<Vertex<Character>, Integer> expected = Map.of(
                new Vertex<>('a'), 0,
                new Vertex<>('b'), 3,
                new Vertex<>('c'), 8,
                new Vertex<>('d'), 1,
                new Vertex<>('e'), 8,
                new Vertex<>('f'), 2,
                new Vertex<>('g'), 6,
                new Vertex<>('h'), 10
        );

        assertEquals(expected,
                GraphAlgorithms.dijkstras(new Vertex<>('a'), directedGraph2));
    }

    @Test(timeout = TIMEOUT)
    public void testKruskal1() {
        /*            5
                B ---------- C
             2 /|            |\ 9
              / |     11     | \
             A ---------------- F
              \ | 8        6 | /
             3 \|            |/ 7
                D ---------- E
                      4
         */

        Set<Vertex<Character>> vertices = setOfVertices('a', 'b', 'c', 'd',
                'e', 'f');
        Set<Edge<Character>> edges = interpolateEdges2(new Object[][]{
                {'a', 'b', 2}, {'b', 'a', 2},
                {'a', 'd', 3}, {'d', 'a', 3},
                {'d', 'e', 4}, {'e', 'd', 4},
                {'b', 'c', 5}, {'c', 'b', 5},
                {'c', 'e', 6}, {'e', 'c', 6},
                {'e', 'f', 7}, {'f', 'e', 7},
                {'b', 'd', 8}, {'d', 'b', 8},
                {'c', 'f', 9}, {'f', 'c', 9},
                {'a', 'f', 11}, {'f', 'a', 11},
        });
        Graph<Character> graph = new Graph<>(vertices, edges);

        Set<Edge<Character>> mstExpected = interpolateEdges2(new Object[][]{
                {'a', 'b', 2}, {'b', 'a', 2},
                {'a', 'd', 3}, {'d', 'a', 3},
                {'d', 'e', 4}, {'e', 'd', 4},
                {'b', 'c', 5}, {'c', 'b', 5},
                {'e', 'f', 7}, {'f', 'e', 7},
        });
        assertEquals(mstExpected, GraphAlgorithms.kruskals(graph));
    }


    // example from https://gatech.instructure.com/courses/132344/quizzes/173633
    @Test(timeout = TIMEOUT)
    public void testKruskal2() {
        Set<Edge<Character>> mstExpected = interpolateEdges2(new Object[][]{
                {'a', 'd', 1}, {'d', 'a', 1},
                {'c', 'e', 1}, {'e', 'c', 1},
                {'b', 'f', 2}, {'f', 'b', 2},
                {'f', 'e', 3}, {'e', 'f', 3},
                {'a', 'b', 5}, {'b', 'a', 5},
        });

        assertEquals(mstExpected,
                GraphAlgorithms.kruskals(this.undirectedGraph2));
    }

    @Test(timeout = TIMEOUT)
    public void testKruskalDisconnected() {
        /*
                B            C
             2 /|            |\ 9
              / |            | \
             A  |            |  F
              \ | 8        6 | /
             3 \|            |/ 7
                D            E
                      4
         */

        Set<Vertex<Character>> vertices = setOfVertices('a', 'b', 'c', 'd',
                'e', 'f');
        Set<Edge<Character>> edges = interpolateEdges2(new Object[][]{
                {'a', 'b', 2}, {'b', 'a', 2},
                {'a', 'd', 3}, {'d', 'a', 3},
                {'c', 'e', 8}, {'e', 'c', 6},
                {'e', 'f', 8}, {'f', 'e', 7},
                {'b', 'd', 8}, {'d', 'b', 8},
                {'c', 'f', 8}, {'f', 'c', 8},
        });
        Graph<Character> graph = new Graph<>(vertices, edges);

        assertNull(GraphAlgorithms.kruskals(graph));
    }

    /**
     * Create a set of vertices with the given characters
     * @param chars characters to add to vertex set
     * @return the set of vertices
     */
    private Set<Vertex<Character>> setOfVertices(char... chars) {
        Set<Vertex<Character>> vertices = new LinkedHashSet<>();
        for (char character : chars) {
            vertices.add(new Vertex<>(character));
        }
        return vertices;
    }

    // The below helper methods are from Aubrey Yan's test file
    // (https://github.gatech.edu/gist/ayan46/c9174199f450f174a5b71ea8e0c5842a)
    /**
     *
     * @param array 2d array representation of edges
     * @return set of edges specified by input array
     */
    private Set<Edge<Character>> interpolateEdges2(Object[][] array) {
        Set<Edge<Character>> edges = new LinkedHashSet<>();
        for (Object[] objects : array) {
            edges.add(new Edge<>(new Vertex<>((char) objects[0]), new Vertex<>((char) objects[1]), (int) objects[2]));
        }
        return edges;
    }

    /**
     *
     * @param chars characters
     * @return list of vertices
     */
    private List<Vertex<Character>> listOfVertices2(char... chars) {
        List<Vertex<Character>> vertices = new ArrayList<>(chars.length);
        for (char character : chars) {
            vertices.add(new Vertex<>(character));
        }
        return vertices;
    }

    @Rule
    public Timeout globalTimeout = new Timeout(200, TimeUnit.MILLISECONDS);

    /*
        Directed down and to the right (- means ->, | means down arrow)
        1 - 11 - 111 - 1111
        |   |     |      |
        2 - 22 - 222 - 2222
        |   |     |      |
        3 - 33 - 333 - 3333
        |   |     |      |
        4 - 44 - 4444 - 4444
        The edges are added in the order of down and then right
        I am too lazy to write all of the weights down in the diagram, you can look at the code
        below if you are interested. They are just "randomly" assigned.
     */
    private Graph<Integer> elongatedGraph() {
        Set<Vertex<Integer>> vertices = new HashSet<>();
        int[] items =
                new int[] {1, 2, 3, 4, 11, 22, 33, 44, 111, 222, 333, 444, 1111, 2222, 3333, 4444};
        for (int integer : items) {
            vertices.add(new Vertex<>(integer));
        }

        Set<Edge<Integer>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(2), 5));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(11), 2));

        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(3), 3));
        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(22), 7));

        edges.add(new Edge<>(new Vertex<>(11), new Vertex<>(22), 8));
        edges.add(new Edge<>(new Vertex<>(11), new Vertex<>(111), 8));

        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(4), 7));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(33), 1));

        edges.add(new Edge<>(new Vertex<>(22), new Vertex<>(33), 0));
        edges.add(new Edge<>(new Vertex<>(22), new Vertex<>(222), 5));

        edges.add(new Edge<>(new Vertex<>(111), new Vertex<>(222), 2));
        edges.add(new Edge<>(new Vertex<>(111), new Vertex<>(1111), 3));

        edges.add(new Edge<>(new Vertex<>(111), new Vertex<>(222), 2));
        edges.add(new Edge<>(new Vertex<>(111), new Vertex<>(1111), 35));

        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(44), 5));

        edges.add(new Edge<>(new Vertex<>(33), new Vertex<>(44), 5));
        edges.add(new Edge<>(new Vertex<>(33), new Vertex<>(333), 6));

        edges.add(new Edge<>(new Vertex<>(222), new Vertex<>(333), 3));
        edges.add(new Edge<>(new Vertex<>(222), new Vertex<>(2222), 23));

        edges.add(new Edge<>(new Vertex<>(333), new Vertex<>(444), 5));
        edges.add(new Edge<>(new Vertex<>(333), new Vertex<>(3333), 4));

        edges.add(new Edge<>(new Vertex<>(1111), new Vertex<>(2222), 3));

        edges.add(new Edge<>(new Vertex<>(44), new Vertex<>(444), 3));

        edges.add(new Edge<>(new Vertex<>(333), new Vertex<>(444), 2));
        edges.add(new Edge<>(new Vertex<>(333), new Vertex<>(3333), 1));

        edges.add(new Edge<>(new Vertex<>(2222), new Vertex<>(3333), 0));

        edges.add(new Edge<>(new Vertex<>(444), new Vertex<>(4444), 9));

        edges.add(new Edge<>(new Vertex<>(3333), new Vertex<>(4444), 3));

        return new Graph<>(vertices, edges);
    }

    @Test
    public void elongatedBfs() {
        List<Vertex<Integer>> bfsActual = GraphAlgorithms.bfs(
                new Vertex<>(1), elongatedGraph());

        List<Vertex<Integer>> bfsExpected =
                createVertices(1, 2, 11, 3, 22, 111, 4, 33, 222, 1111, 44, 333, 2222, 444, 3333, 4444);

        assertEquals(bfsExpected, bfsActual);
    }

    @Test
    public void elongatedDfs() {
        List<Vertex<Integer>> dfsActual = GraphAlgorithms.dfs(
                new Vertex<>(1), elongatedGraph());

        List<Vertex<Integer>> dfsExpected =
                createVertices(1, 2, 3, 4, 44, 444, 4444, 33, 333, 3333, 22, 222, 2222, 11, 111, 1111);

        assertEquals(dfsExpected, dfsActual);
    }

    /**
     * Interesting information. In Aubrey's test, they used Map.of() method, I tried to
     * use it and got an error! I looked at the source code and saw that the method is just
     * overloaded a whole bunch of times so you can only use that function to create a map of at
     * most length 10. I took the easy route and just split it up into two calls to that build
     * method. {@link java.util.Map#of()}
     */
    @Test
    public void dijkstras() {
        Map<Vertex<Integer>, Integer> dijkstras =
                GraphAlgorithms.dijkstras(new Vertex<>(1), elongatedGraph());
        Map<Vertex<Integer>, Integer> expected = new HashMap<>(Map.of(
                new Vertex<>(1), 0,
                new Vertex<>(33), 9,
                new Vertex<>(2), 5,
                new Vertex<>(3), 8,
                new Vertex<>(4), 15,
                new Vertex<>(3333), 16,
                new Vertex<>(11), 2,
                new Vertex<>(44), 14,
                new Vertex<>(2222), 16,
                new Vertex<>(111), 10
        ));

        expected.putAll(
                Map.of(
                        new Vertex<>(22), 10,
                        new Vertex<>(1111), 13,
                        new Vertex<>(444), 17,
                        new Vertex<>(4444), 19,
                        new Vertex<>(333), 15,
                        new Vertex<>(222), 12
                )
        );

        assertEquals(expected, dijkstras);
    }

    @Test
    public void testKruskals2() {
        Set<Edge<Integer>> kruskals = GraphAlgorithms.kruskals(elongatedGraph());
        Set<Edge<Integer>> expected = new HashSet<>(List.of(
                new Edge<>(new Vertex<>(2), new Vertex<>(3), 3),
                new Edge<>(new Vertex<>(3), new Vertex<>(2), 3),
                new Edge<>(new Vertex<>(1), new Vertex<>(2), 5),
                new Edge<>(new Vertex<>(2), new Vertex<>(1), 5),
                new Edge<>(new Vertex<>(1), new Vertex<>(11), 2),
                new Edge<>(new Vertex<>(11), new Vertex<>(1), 2),
                new Edge<>(new Vertex<>(333), new Vertex<>(3333), 1),
                new Edge<>(new Vertex<>(3333), new Vertex<>(333), 1),
                new Edge<>(new Vertex<>(22), new Vertex<>(222), 5),
                new Edge<>(new Vertex<>(222), new Vertex<>(22), 5),
                new Edge<>(new Vertex<>(222), new Vertex<>(333), 3),
                new Edge<>(new Vertex<>(333), new Vertex<>(222), 3),
                new Edge<>(new Vertex<>(44), new Vertex<>(444), 3),
                new Edge<>(new Vertex<>(444), new Vertex<>(44), 3),
                new Edge<>(new Vertex<>(3333), new Vertex<>(4444), 3),
                new Edge<>(new Vertex<>(4444), new Vertex<>(3333), 3),
                new Edge<>(new Vertex<>(3), new Vertex<>(33), 1),
                new Edge<>(new Vertex<>(33), new Vertex<>(3), 1),
                new Edge<>(new Vertex<>(2222), new Vertex<>(3333), 0),
                new Edge<>(new Vertex<>(3333), new Vertex<>(2222), 0),
                new Edge<>(new Vertex<>(4), new Vertex<>(44), 5),
                new Edge<>(new Vertex<>(44), new Vertex<>(4), 5),
                new Edge<>(new Vertex<>(111), new Vertex<>(222), 2),
                new Edge<>(new Vertex<>(222), new Vertex<>(111), 2),
                new Edge<>(new Vertex<>(333), new Vertex<>(444), 2),
                new Edge<>(new Vertex<>(444), new Vertex<>(333), 2),
                new Edge<>(new Vertex<>(22), new Vertex<>(33), 0),
                new Edge<>(new Vertex<>(33), new Vertex<>(22), 0),
                new Edge<>(new Vertex<>(111), new Vertex<>(1111), 3),
                new Edge<>(new Vertex<>(1111), new Vertex<>(111), 3)
        ));
        assertEquals(kruskals, expected);
    }

    @Test(expected = IllegalArgumentException.class)
    public void bfsNullStart() {
        GraphAlgorithms.bfs(null, elongatedGraph());
    }

    @Test(expected = IllegalArgumentException.class)
    public void bfsNullGraph() {
        GraphAlgorithms.bfs(new Vertex<>(1), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void bfsNullGraphAndEdge() {
        GraphAlgorithms.bfs(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void bfsStartNotInGraph() {
        GraphAlgorithms.bfs(new Vertex<>(-1), elongatedGraph());
    }

    @Test(expected = IllegalArgumentException.class)
    public void dfsNullStart() {
        GraphAlgorithms.dfs(null, elongatedGraph());
    }

    @Test(expected = IllegalArgumentException.class)
    public void dfsNullGraph() {
        GraphAlgorithms.dfs(new Vertex<>(1), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void dfsNullGraphAndEdge() {
        GraphAlgorithms.dfs(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void dfsStartNotInGraph() {
        GraphAlgorithms.dfs(new Vertex<>(-1), elongatedGraph());
    }

    @Test(expected = IllegalArgumentException.class)
    public void dijkstrasNullStart() {
        GraphAlgorithms.dijkstras(null, elongatedGraph());
    }

    @Test(expected = IllegalArgumentException.class)
    public void dijkstrasNullGraph() {
        GraphAlgorithms.dijkstras(new Vertex<>(1), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void dijkstrasNullGraphAndEdge() {
        GraphAlgorithms.dijkstras(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void dijkstrasStartNotInGraph() {
        GraphAlgorithms.dijkstras(new Vertex<>(-1), elongatedGraph());
    }

    @Test(expected = IllegalArgumentException.class)
    public void kruskalsNullGraph() {
        GraphAlgorithms.kruskals(null);
    }

    /**
     * This is just a reminder to run checkstyle!
     * You can just comment this out xD
     */
    @Test
    public void runCheckstyle() {
        fail("This is a reminder to run checkstyle!");
    }

    private List<Vertex<Integer>> createVertices(int... vertices) {
        List<Vertex<Integer>> vertices1 = new ArrayList<>(vertices.length);
        for (int vertex : vertices) {
            vertices1.add(new Vertex<>(vertex));
        }
        return vertices1;
    }
}