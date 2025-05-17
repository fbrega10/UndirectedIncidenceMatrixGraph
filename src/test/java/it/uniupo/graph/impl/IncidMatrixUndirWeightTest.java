package it.uniupo.graph.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import upo.graph.base.Edge;
import upo.graph.base.WeightedGraph;

import java.util.NoSuchElementException;

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
}