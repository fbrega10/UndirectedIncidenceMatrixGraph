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
    void getDijkstraShortestPaths() {
        Exception e = Assertions.assertThrows(UnsupportedOperationException.class, () -> weightedGraph.getDijkstraShortestPaths(0));
        Assertions.assertEquals(IncidMatrixUndirWeight.UNSUPPORTED_IMPLEMENTATION, e.getMessage());
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
}