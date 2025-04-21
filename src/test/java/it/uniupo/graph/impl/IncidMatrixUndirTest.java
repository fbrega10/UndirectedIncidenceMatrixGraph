package it.uniupo.graph.impl;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import upo.graph.base.Edge;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

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
        Assertions.assertEquals(0, matrixUndir.addVertex(20));
        Assertions.assertEquals(1, matrixUndir.getVertices().size());
        Assertions.assertEquals(1, matrixUndir.size());
        Assertions.assertEquals(0, matrixUndir.addVertex(30));
        Assertions.assertEquals(2, matrixUndir.getVertices().size());
        Assertions.assertEquals(2, matrixUndir.size());
        Assertions.assertEquals(-1, matrixUndir.addVertex(null));
        matrixUndir.addEdge(Edge.getEdgeByVertexes(20, 30));
        Assertions.assertEquals(0, matrixUndir.addVertex(13));
        matrixUndir.addEdge(Edge.getEdgeByVertexes(13, 20));
        //System.out.println(matrixUndir.toString());
    }

    @Test
    void getVertices() {
        matrixUndir.addVertex(30);
        matrixUndir.addVertex(20);
        Assertions.assertEquals(Set.of(30, 20), matrixUndir.getVertices());
    }

    @Test
    void getEdges() {
        matrixUndir.addVertex(30);
        matrixUndir.addVertex(20);
        matrixUndir.addEdge(Edge.getEdgeByVertexes(20, 30));
        Assertions.assertEquals(1, matrixUndir.getEdges().size());
    }

    @Test
    void resizeTest() {
        Integer[][] matrix = IncidMatrixUndir.resize(new Integer[0][0], 2, 2);
        Assertions.assertEquals(2, matrix.length);
        Assertions.assertEquals(2, matrix[0].length);
    }

    @Test
    void containsVertex() {
        matrixUndir.addVertex(13);
        matrixUndir.addVertex(123);
        Assertions.assertTrue(matrixUndir.containsVertex(13));
        Assertions.assertTrue(matrixUndir.containsVertex(123));
        Assertions.assertFalse(matrixUndir.containsVertex(23432));
        Assertions.assertFalse(matrixUndir.containsVertex(432));
    }

    @Test
    void removeVertex() {
        Assertions.assertThrows(NoSuchElementException.class, () -> matrixUndir.removeVertex(3));
        Assertions.assertThrows(NoSuchElementException.class, () -> matrixUndir.removeVertex(390));
        matrixUndir.addVertex(234);
        matrixUndir.addVertex(34);
    }

    @Test
    void addEdge() {
        matrixUndir.addVertex(123);
        matrixUndir.addVertex(13);
        matrixUndir.addEdge(Edge.getEdgeByVertexes(123, 13));
        Iterator<Edge> iterator = matrixUndir.getEdges().iterator();
        Assertions.assertEquals(123, iterator.next().getSource());
        iterator = matrixUndir.getEdges().iterator();
        Assertions.assertEquals(123, iterator.next().getSource());
    }

    @Test
    void containsEdge() {
        matrixUndir.addVertex(123);
        matrixUndir.addVertex(13);
        Edge current = Edge.getEdgeByVertexes(123, 13);
        matrixUndir.addEdge(current);
        Assertions.assertTrue(matrixUndir.containsEdge(current));
        Edge newEdge = Edge.getEdgeByVertexes(11, 10);
        Assertions.assertThrows(IllegalArgumentException.class, () -> matrixUndir.containsEdge(newEdge));
        Assertions.assertThrows(IllegalArgumentException.class, () -> matrixUndir.containsEdge(null));
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

    @AfterEach
    void postConstruct() {
        matrixUndir = new IncidMatrixUndir();
    }
}