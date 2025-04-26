package it.uniupo.graph.impl;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import upo.graph.base.Edge;

import java.util.NoSuchElementException;

class IncidMatrixUndirTest {

    private IncidMatrixUndir matrixUndir;

    @BeforeEach
    void setUp() {
        matrixUndir = new IncidMatrixUndir();
    }

    @Test
    void emptyConstructorTest() {
        Assertions.assertEquals(0, matrixUndir.getVertices().size());
        Assertions.assertEquals(0, matrixUndir.getEdges().size());
    }

    @Test
    void addVertex() {
        Assertions.assertEquals(0, matrixUndir.addVertex());
        Assertions.assertEquals(1, matrixUndir.addVertex());
        Assertions.assertEquals(2, matrixUndir.addVertex());
    }

    @Test
    void getVertices() {
        Assertions.assertEquals(0, matrixUndir.getVertices().size());
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        Assertions.assertEquals(2, matrixUndir.getVertices().size());
    }

    @Test
    void getEdges() {
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addEdge(Edge.getEdgeByVertexes(0, 1));
        Assertions.assertEquals(1, matrixUndir.getEdges().size());
        Assertions.assertEquals(0, matrixUndir.getEdges().iterator().next().getSource());
        Assertions.assertEquals(1, matrixUndir.getEdges().iterator().next().getTarget());
    }

    @Test
    void containsVertex() {
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        Assertions.assertTrue(matrixUndir.containsVertex(0));
        Assertions.assertTrue(matrixUndir.containsVertex(1));
        Assertions.assertFalse(matrixUndir.containsVertex(2));
    }

    @Test
    void removeVertex() {
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addEdge(Edge.getEdgeByVertexes(0, 1));
        matrixUndir.addEdge(Edge.getEdgeByVertexes(0, 3));
        matrixUndir.addEdge(Edge.getEdgeByVertexes(2, 1));
        Assertions.assertEquals(4, matrixUndir.size());
        matrixUndir.removeVertex(0);
        Assertions.assertEquals(3, matrixUndir.size());
        Exception ex = Assertions.assertThrows(NoSuchElementException.class, () -> matrixUndir.removeVertex(4));
        Assertions.assertEquals("No such vertex!", ex.getMessage());
    }

    @Test
    void addEdge() {
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        Edge edge = Edge.getEdgeByVertexes(0, 1);
        matrixUndir.addEdge(edge);
        Assertions.assertTrue(matrixUndir.containsEdge(edge));
        Assertions.assertThrows(IllegalArgumentException.class, () -> matrixUndir.addEdge(Edge.getEdgeByVertexes(2, 3)));
    }

    @Test
    void containsEdge() {
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        Edge edge = Edge.getEdgeByVertexes(0, 1);
        matrixUndir.addEdge(edge);
        Assertions.assertThrows(IllegalArgumentException.class, () -> matrixUndir.containsEdge(Edge.getEdgeByVertexes(0, 4)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> matrixUndir.containsEdge(Edge.getEdgeByVertexes(1, 4)));
        Assertions.assertTrue(matrixUndir.containsEdge(edge));
        Assertions.assertTrue(matrixUndir.containsEdge(Edge.getEdgeByVertexes(edge.getTarget(), edge.getSource())));
    }

    @Test
    void removeEdge() {
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        Edge edge = Edge.getEdgeByVertexes(0, 1);
        matrixUndir.addEdge(edge);
        Assertions.assertTrue(matrixUndir.containsEdge(edge));
        matrixUndir.removeEdge(edge);
        Assertions.assertFalse(matrixUndir.containsEdge(edge));
        Assertions.assertThrows(NoSuchElementException.class, () -> matrixUndir.removeEdge(edge));
        Assertions.assertThrows(IllegalArgumentException.class, () -> matrixUndir.removeEdge(Edge.getEdgeByVertexes(3, 0)));
    }

    @Test
    void getAdjacent() {
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addEdge(Edge.getEdgeByVertexes(2, 3));
        matrixUndir.addEdge(Edge.getEdgeByVertexes(2, 0));
        Assertions.assertEquals(2, matrixUndir.getAdjacent(2).size());
        Assertions.assertEquals(2, matrixUndir.getAdjacent(3).iterator().next());
        Assertions.assertEquals(2, matrixUndir.getAdjacent(0).iterator().next());
        Assertions.assertEquals(0, matrixUndir.getAdjacent(4).size());
    }

    @Test
    void isAdjacent() {
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addEdge(Edge.getEdgeByVertexes(2, 3));
        matrixUndir.addEdge(Edge.getEdgeByVertexes(2, 0));
        Assertions.assertTrue(matrixUndir.isAdjacent(2, 0));
        Assertions.assertTrue(matrixUndir.isAdjacent(2, 3));
        Assertions.assertTrue(matrixUndir.isAdjacent(3, 2));
        Assertions.assertFalse(matrixUndir.isAdjacent(4, 3));
        Assertions.assertThrows(IllegalArgumentException.class, () -> matrixUndir.isAdjacent(3, 5));
        Assertions.assertThrows(IllegalArgumentException.class, () -> matrixUndir.isAdjacent(5, 3));
    }

    @Test
    void size() {
        Assertions.assertEquals(0, matrixUndir.size());
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        Assertions.assertEquals(2, matrixUndir.size());
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