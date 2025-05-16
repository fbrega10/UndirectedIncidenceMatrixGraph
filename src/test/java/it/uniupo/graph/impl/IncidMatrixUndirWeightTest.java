package it.uniupo.graph.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import upo.graph.base.Edge;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class IncidMatrixUndirWeightTest {
    private IncidMatrixUndirWeight incidMatrixUndirWeight;

    @BeforeEach
    void setUp() {
        incidMatrixUndirWeight = new IncidMatrixUndirWeight();
    }

    @Test
    @DisplayName("Constructor test")
    void constructorTest(){
        Assertions.assertNotNull(incidMatrixUndirWeight);
        Assertions.assertEquals(0, incidMatrixUndirWeight.size());
    }

    @Test
    void getEdgeWeight() {
        incidMatrixUndirWeight.addVertex();
        incidMatrixUndirWeight.addVertex();
        Assertions.assertThrows(NoSuchElementException.class, () -> incidMatrixUndirWeight.getEdgeWeight(Edge.getEdgeByVertexes(
                0, 1)));
        Edge edge = Edge.getEdgeByVertexes(0, 1);
        incidMatrixUndirWeight.addEdge(edge);
        incidMatrixUndirWeight.setEdgeWeight(edge, 40.8);
        Assertions.assertEquals(40.8, incidMatrixUndirWeight.getEdgeWeight(edge));
    }

    @Test
    void setEdgeWeight() {
    }

    @Test
    void getBellmanFordShortestPaths() {
    }

    @Test
    void getDijkstraShortestPaths() {
    }

    @Test
    void getPrimMST() {
    }

    @Test
    void getKruskalMST() {
    }

    @Test
    void getFloydWarshallShortestPaths() {
    }

    @Test
    void addVertex() {
    }

    @Test
    void getVertices() {
    }

    @Test
    void getEdges() {
    }

    @Test
    void containsVertex() {
    }

    @Test
    void removeVertex() {
    }

    @Test
    void addEdge() {
    }

    @Test
    void containsEdge() {
    }

    @Test
    void removeEdge() {
    }

    @Test
    void getAdjacent() {
    }

    @Test
    void isAdjacent() {
    }

    @Test
    void size() {
    }

    @Test
    void isDirected() {
    }

    @Test
    void isCyclic() {
    }

    @Test
    void isDAG() {
    }

    @Test
    void getBFSTree() {
    }

    @Test
    void getDFSTree() {
    }

    @Test
    void getDFSTOTForest() {
    }

    @Test
    void testGetDFSTOTForest() {
    }

    @Test
    void topologicalSort() {
    }

    @Test
    void stronglyConnectedComponents() {
    }

    @Test
    void connectedComponents() {
    }
}