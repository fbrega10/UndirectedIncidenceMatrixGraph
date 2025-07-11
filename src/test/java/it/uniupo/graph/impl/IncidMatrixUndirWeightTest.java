package it.uniupo.graph.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import upo.graph.base.Edge;
import upo.graph.base.WeightedGraph;

import java.util.NoSuchElementException;
import java.util.Set;

class IncidMatrixUndirWeightTest {
    private WeightedGraph weightedGraph;

    @BeforeEach
    void setUp() {
        weightedGraph = new IncidMatrixUndirWeight();
    }

    @Test
    @DisplayName("Constructor test")
    void constructorTest() {
        Assertions.assertNotNull(weightedGraph);
        Assertions.assertEquals(0, weightedGraph.size());
    }

    @Test
    @DisplayName("Get edge weight test")
    void getEdgeWeight() {
        weightedGraph.addVertex();
        weightedGraph.addVertex();
        Assertions.assertThrows(NoSuchElementException.class, () -> weightedGraph.getEdgeWeight(Edge.getEdgeByVertexes(
                0, 1)));
        Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> weightedGraph.getEdgeWeight(Edge.getEdgeByVertexes(
                0, 3)));
        Assertions.assertEquals(String.format(IncidMatrixUndirWeight.VERTEX_NOT_PRESENT, 3), e.getMessage());
        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> weightedGraph.getEdgeWeight(Edge.getEdgeByVertexes(
                3, 0)));
        Assertions.assertEquals(String.format(IncidMatrixUndirWeight.VERTEX_NOT_PRESENT, 3), ex.getMessage());
        Edge edge = Edge.getEdgeByVertexes(0, 1);
        weightedGraph.addEdge(edge);
        weightedGraph.setEdgeWeight(edge, 40.8);
        Assertions.assertEquals(40.8, weightedGraph.getEdgeWeight(edge));
        weightedGraph.setEdgeWeight(edge, 0);
        Assertions.assertEquals(0, weightedGraph.getEdgeWeight(edge));
        weightedGraph.removeEdge(edge);
        Assertions.assertThrows(NoSuchElementException.class, () -> weightedGraph.getEdgeWeight(Edge.getEdgeByVertexes(
                0, 1)));
    }

    @Test
    void setEdgeWeight() {
        weightedGraph.addVertex();
        weightedGraph.addVertex();
        Edge missingEdge = Edge.getEdgeByVertexes(0, 2);
        Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> weightedGraph.setEdgeWeight(missingEdge, 5));
        Assertions.assertEquals(String.format(IncidMatrixUndirWeight.VERTEX_NOT_PRESENT, 2), e.getMessage());
        weightedGraph.addVertex();
        Edge edge = missingEdge;
        weightedGraph.addEdge(edge);
        Assertions.assertEquals(0, weightedGraph.getEdgeWeight(edge));
        weightedGraph.setEdgeWeight(edge, 50);
        Assertions.assertEquals(50, weightedGraph.getEdgeWeight(edge));
    }

    @Test
    @DisplayName("weighted graph - add vertex test")
    void addVertex() {
        weightedGraph.addVertex();
        weightedGraph.addVertex();
        weightedGraph.addVertex();
        weightedGraph.addVertex();
        weightedGraph.addVertex();
        weightedGraph.addEdge(Edge.getEdgeByVertexes(1, 2));
        weightedGraph.addEdge(Edge.getEdgeByVertexes(3, 2));
        weightedGraph.setEdgeWeight(Edge.getEdgeByVertexes(3, 2), 54.9);
        weightedGraph.setEdgeWeight(Edge.getEdgeByVertexes(1, 2), 50);
        weightedGraph.addVertex();
        Assertions.assertEquals(54.9, weightedGraph.getEdgeWeight(Edge.getEdgeByVertexes(3, 2)));
        Assertions.assertEquals(50, weightedGraph.getEdgeWeight(Edge.getEdgeByVertexes(1, 2)));
    }

    @Test
    @DisplayName("weighted graph - add vertex test")
    void removeEdge() {
        weightedGraph.addVertex();
        weightedGraph.addVertex();
        weightedGraph.addVertex();
        weightedGraph.addVertex();
        weightedGraph.addVertex();
        weightedGraph.addEdge(Edge.getEdgeByVertexes(1, 2));
        weightedGraph.addEdge(Edge.getEdgeByVertexes(3, 2));
        weightedGraph.setEdgeWeight(Edge.getEdgeByVertexes(3, 2), 54.9);
        weightedGraph.setEdgeWeight(Edge.getEdgeByVertexes(1, 2), 50);
        Assertions.assertEquals(54.9, weightedGraph.getEdgeWeight(Edge.getEdgeByVertexes(3, 2)));
        Set<Edge> edgesBefore = weightedGraph.getEdges();
        weightedGraph.removeEdge(Edge.getEdgeByVertexes(3, 2));
        Exception e = Assertions.assertThrows(NoSuchElementException.class, () -> weightedGraph.removeEdge(Edge.getEdgeByVertexes(3, 2)));
        Assertions.assertEquals("No such edge.", e.getMessage());
        //checks in the edges collections before/after
        Assertions.assertNotEquals(edgesBefore, weightedGraph.getEdges());
        Assertions.assertEquals(2, edgesBefore.size());
        Assertions.assertEquals(1, weightedGraph.getEdges().size());
        Assertions.assertEquals(50, weightedGraph.getEdgeWeight(Edge.getEdgeByVertexes(1, 2)));
    }

    @Test
    void addEdge() {
        weightedGraph.addVertex();
        weightedGraph.addVertex();
        weightedGraph.addVertex();
        weightedGraph.addVertex();
        weightedGraph.addVertex();
        weightedGraph.addEdge(Edge.getEdgeByVertexes(1, 2));
        weightedGraph.addEdge(Edge.getEdgeByVertexes(3, 2));
        weightedGraph.setEdgeWeight(Edge.getEdgeByVertexes(3, 2), 54.9);
        weightedGraph.setEdgeWeight(Edge.getEdgeByVertexes(1, 2), 50);
        Assertions.assertEquals(2, weightedGraph.getEdges().size());
        Exception nullException = Assertions.assertThrows(IllegalArgumentException.class, () -> weightedGraph.addEdge(null));
        Assertions.assertEquals("The edge cannot be null!", nullException.getMessage());
        Exception sourceException = Assertions.assertThrows(IllegalArgumentException.class, () -> weightedGraph.addEdge(Edge.getEdgeByVertexes(2345, 1)));
        Assertions.assertEquals("Cannot have an edge with invalid source/target", sourceException.getMessage());
        Exception targetException = Assertions.assertThrows(IllegalArgumentException.class, () -> weightedGraph.addEdge(Edge.getEdgeByVertexes(1, 2345)));
        Assertions.assertEquals("Cannot have an edge with invalid source/target", targetException.getMessage());
        Assertions.assertDoesNotThrow(() -> weightedGraph.addEdge(Edge.getEdgeByVertexes(3, 2)));
    }

    @Test
    void getBellmanFordShortestPaths() {
        Exception e = Assertions.assertThrows(UnsupportedOperationException.class, () -> weightedGraph.getBellmanFordShortestPaths(0));
        Assertions.assertEquals(IncidMatrixUndirWeight.UNSUPPORTED_IMPLEMENTATION, e.getMessage());
    }

    @Test
    @DisplayName("Dijkstra shortest path test")
    void getDijkstraShortestPaths() {
        weightedGraph.addVertex();
        weightedGraph.addVertex();
        weightedGraph.addVertex();
        weightedGraph.addVertex();
        weightedGraph.addVertex();

        Edge edge_1_2 = Edge.getEdgeByVertexes(1, 2);
        weightedGraph.addEdge(edge_1_2);
        weightedGraph.setEdgeWeight(edge_1_2, 30);

        Edge edge_1_3 = Edge.getEdgeByVertexes(1, 3);
        weightedGraph.addEdge(edge_1_3);
        weightedGraph.setEdgeWeight(edge_1_3, 20);

        Edge edge_3_2 = Edge.getEdgeByVertexes(3, 2);
        weightedGraph.addEdge(edge_3_2);
        weightedGraph.setEdgeWeight(edge_3_2, 2);

        Edge edge_1_4 = Edge.getEdgeByVertexes(1, 4);
        weightedGraph.addEdge(edge_1_4);
        weightedGraph.setEdgeWeight(edge_1_4, 52);

        Edge edge_3_4 = Edge.getEdgeByVertexes(3, 4);
        weightedGraph.addEdge(edge_3_4);
        weightedGraph.setEdgeWeight(edge_3_4, 2);

        WeightedGraph graph = weightedGraph.getDijkstraShortestPaths(1);
        Assertions.assertEquals(22.0, graph.getEdgeWeight(edge_3_4));
        Assertions.assertEquals(20.0, graph.getEdgeWeight(edge_1_3));
    }
    @Test
    public void testDijkstraSimpleGraph() {
        IncidMatrixUndirWeight graph = new IncidMatrixUndirWeight();

        int v0 = graph.addVertex();
        int v1 = graph.addVertex();
        int v2 = graph.addVertex();
        int v3 = graph.addVertex();

        Edge e0 = Edge.getEdgeByVertexes(v0, v1);
        Edge e1 = Edge.getEdgeByVertexes(v1, v2);
        Edge e2 = Edge.getEdgeByVertexes(v0, v2);
        Edge e3 = Edge.getEdgeByVertexes(v2, v3);

        graph.addEdge(e0);
        graph.setEdgeWeight(e0, 1.0);

        graph.addEdge(e1);
        graph.setEdgeWeight(e1, 2.0);

        graph.addEdge(e2);
        graph.setEdgeWeight(e2, 4.0);

        graph.addEdge(e3);
        graph.setEdgeWeight(e3, 1.0);

        WeightedGraph result = graph.getDijkstraShortestPaths(v0);

        // Verifica che l'albero dei cammini minimi contenga gli archi corretti
        Assertions.assertTrue(result.containsEdge(e0)); // v0 -> v1
        Assertions.assertTrue(result.containsEdge(e1)); // v1 -> v2
        Assertions.assertTrue(result.containsEdge(e3)); // v2 -> v3

        // Peso del cammino minimo da v0 a v3 deve essere 1 + 2 + 1 = 4
        double totalDistance = graph.getEdgeWeight(e0) + graph.getEdgeWeight(e1) + graph.getEdgeWeight(e3);
        Assertions.assertEquals(4.0, totalDistance);
    }

    @Test
    public void testDijkstraThrowsOnInvalidSource() {
        IncidMatrixUndirWeight graph = new IncidMatrixUndirWeight();
        graph.addVertex();
        graph.addVertex();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            graph.getDijkstraShortestPaths(5); // vertice 5 non esiste
        });
    }

    @Test
    void getPrimMST() {
        Exception e = Assertions.assertThrows(UnsupportedOperationException.class, () -> weightedGraph.getPrimMST(0));
        Assertions.assertEquals(IncidMatrixUndirWeight.UNSUPPORTED_IMPLEMENTATION, e.getMessage());
    }

    @Test
    void getKruskalMST() {
        Exception e = Assertions.assertThrows(UnsupportedOperationException.class, () -> weightedGraph.getKruskalMST());
        Assertions.assertEquals(IncidMatrixUndirWeight.UNSUPPORTED_IMPLEMENTATION, e.getMessage());
    }

    @Test
    void getFloydWarshallShortestPaths() {
        Exception e = Assertions.assertThrows(UnsupportedOperationException.class, () -> weightedGraph.getFloydWarshallShortestPaths());
        Assertions.assertEquals(IncidMatrixUndirWeight.UNSUPPORTED_IMPLEMENTATION, e.getMessage());
    }

    @Test
    @DisplayName("Equals test")
    void equals() {
        weightedGraph.addVertex();
        weightedGraph.addVertex();
        weightedGraph.addVertex();
        weightedGraph.addVertex();
        Edge zeroToOne = Edge.getEdgeByVertexes(0, 1);
        Edge oneToTwo = Edge.getEdgeByVertexes(1, 2);
        weightedGraph.addEdge(zeroToOne);
        weightedGraph.addEdge(oneToTwo);
        weightedGraph.setEdgeWeight(zeroToOne, 45);
        weightedGraph.setEdgeWeight(oneToTwo, 47);
        IncidMatrixUndirWeight incidMatrixUndirWeight = (IncidMatrixUndirWeight) weightedGraph;
        Assertions.assertEquals(weightedGraph, incidMatrixUndirWeight);

        incidMatrixUndirWeight = new IncidMatrixUndirWeight();
        incidMatrixUndirWeight.addVertex();
        incidMatrixUndirWeight.addVertex();
        incidMatrixUndirWeight.addVertex();
        incidMatrixUndirWeight.addVertex();
        incidMatrixUndirWeight.addEdge(zeroToOne);
        incidMatrixUndirWeight.addEdge(oneToTwo);
        incidMatrixUndirWeight.setEdgeWeight(oneToTwo, 47);
        incidMatrixUndirWeight.setEdgeWeight(zeroToOne, 9);
        Assertions.assertNotEquals(weightedGraph, incidMatrixUndirWeight);

        weightedGraph.setEdgeWeight(zeroToOne, 0.0);
        weightedGraph.setEdgeWeight(oneToTwo, 0.0);

        IncidMatrixUndir matrixUndir = new IncidMatrixUndir();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addEdge(zeroToOne);
        matrixUndir.addEdge(oneToTwo);
        Assertions.assertEquals(weightedGraph, matrixUndir);
    }

    @Test
    @DisplayName("Remove vertex test")
    void removeVertex() {
        weightedGraph.addVertex();
        weightedGraph.addVertex();
        weightedGraph.addVertex();
        weightedGraph.addVertex();
        weightedGraph.addVertex();
        weightedGraph.addEdge(Edge.getEdgeByVertexes(0, 1));
        weightedGraph.addEdge(Edge.getEdgeByVertexes(0, 2));
        weightedGraph.setEdgeWeight(Edge.getEdgeByVertexes(0, 1), 40);
        weightedGraph.setEdgeWeight(Edge.getEdgeByVertexes(0, 2), 10);
        weightedGraph.removeVertex(1);
        Assertions.assertEquals(10, weightedGraph.getEdgeWeight(Edge.getEdgeByVertexes(0, 1)));
        Assertions.assertFalse(weightedGraph.getVertices().contains(4));
        Assertions.assertFalse(weightedGraph.containsEdge(Edge.getEdgeByVertexes(0, 2)));

        weightedGraph.addVertex();
        weightedGraph.addVertex();
        weightedGraph.addVertex();
        weightedGraph.addVertex();
        weightedGraph.addVertex();
        Edge fiveToSix = Edge.getEdgeByVertexes(5, 6);
        weightedGraph.addEdge(fiveToSix);
        weightedGraph.setEdgeWeight(fiveToSix, 30);
        weightedGraph.addEdge(Edge.getEdgeByVertexes(7, 8));
        weightedGraph.setEdgeWeight(Edge.getEdgeByVertexes(7, 8), 5);
        System.out.println(weightedGraph);
        weightedGraph.removeVertex(5);
        Assertions.assertEquals(5, weightedGraph.getEdgeWeight(Edge.getEdgeByVertexes(6, 7)));
        Assertions.assertEquals(10.0, weightedGraph.getEdgeWeight(Edge.getEdgeByVertexes(0, 1)));
        System.out.println(weightedGraph);
    }

    @Test
    void vertexDistanceTest(){
        IncidMatrixUndirWeight.VertexDistance vd = new IncidMatrixUndirWeight.VertexDistance(4) ;
        IncidMatrixUndirWeight.VertexDistance vd2 = new IncidMatrixUndirWeight.VertexDistance(4, 0.0) ;
        vd.setDistance(4.0);
        vd2.setDistance(5.0);
        Assertions.assertEquals(vd, vd2);
        Assertions.assertEquals(vd.hashCode(), vd2.hashCode());
    }
}