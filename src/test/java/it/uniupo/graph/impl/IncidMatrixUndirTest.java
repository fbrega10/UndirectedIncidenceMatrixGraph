package it.uniupo.graph.impl;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import upo.graph.base.Edge;
import upo.graph.base.VisitResult;

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
        Assertions.assertEquals(0, matrixUndir.addVertex(30));
        Assertions.assertEquals(2, matrixUndir.getVertices().size());
        Assertions.assertEquals(-1, matrixUndir.addVertex(null));
        Assertions.assertEquals(-1, matrixUndir.addVertex(30));
        matrixUndir.addEdge(Edge.getEdgeByVertexes(20, 30));
        Assertions.assertThrows(IllegalArgumentException.class, () -> matrixUndir.addEdge(Edge.getEdgeByVertexes(20, 234782)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> matrixUndir.addEdge(Edge.getEdgeByVertexes(234782, 20)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> matrixUndir.addEdge(Edge.getEdgeByVertexes(20, 30)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> matrixUndir.addEdge(null));
        Assertions.assertEquals(0, matrixUndir.addVertex(13));
        matrixUndir.addEdge(Edge.getEdgeByVertexes(13, 20));
    }

    @Test
    void getVertices() {
        Assertions.assertEquals(0, matrixUndir.getVertices().size());
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
    void containsVertex() {
        matrixUndir.addVertex(13);
        matrixUndir.addVertex(123);
        Assertions.assertTrue(matrixUndir.containsVertex(13));
        Assertions.assertTrue(matrixUndir.containsVertex(123));
        Assertions.assertFalse(matrixUndir.containsVertex(23432));
        Assertions.assertFalse(matrixUndir.containsVertex(432));
        Assertions.assertFalse(matrixUndir.containsVertex(null));
    }

    @Test
    void removeVertex() {
        Assertions.assertThrows(NoSuchElementException.class, () -> matrixUndir.removeVertex(3));
        Assertions.assertThrows(NoSuchElementException.class, () -> matrixUndir.removeVertex(390));
        matrixUndir.addVertex(234);
        matrixUndir.addVertex(34);
        matrixUndir.addVertex(15);
        matrixUndir.addEdge(Edge.getEdgeByVertexes(234, 34));
        matrixUndir.addEdge(Edge.getEdgeByVertexes(234, 15));
        System.out.println(matrixUndir.toString());
        matrixUndir.removeVertex(34);
        System.out.println(matrixUndir.toString());
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
        matrixUndir.addVertex(123);
        matrixUndir.addVertex(13);
        Edge current = Edge.getEdgeByVertexes(123, 13);
        matrixUndir.addEdge(current);
        Assertions.assertEquals(1, matrixUndir.getEdges().size());
        matrixUndir.removeEdge(current);
        Assertions.assertEquals(0, matrixUndir.getEdges().size());
        Assertions.assertThrows(NoSuchElementException.class, () -> matrixUndir.removeEdge(current));
        Assertions.assertThrows(IllegalArgumentException.class, () -> matrixUndir.removeEdge(null));
    }

    @Test
    void getAdjacent() {
        matrixUndir.addVertex(30);
        matrixUndir.addVertex(45);
        matrixUndir.addVertex(130);
        matrixUndir.addEdge(Edge.getEdgeByVertexes(30, 45));
        Assertions.assertEquals(45, matrixUndir.getAdjacent(30).iterator().next());
        Assertions.assertEquals(0, matrixUndir.getAdjacent(130).size());
        Assertions.assertThrows(NoSuchElementException.class, () -> matrixUndir.getAdjacent(0));
        Assertions.assertThrows(NoSuchElementException.class, () -> matrixUndir.getAdjacent(null));
        Assertions.assertEquals(30, matrixUndir.getAdjacent(45).iterator().next());
    }

    @Test
    void isAdjacent() {
        matrixUndir.addVertex(30);
        matrixUndir.addVertex(20);
        Assertions.assertThrows(IllegalArgumentException.class, () -> matrixUndir.isAdjacent(null, null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> matrixUndir.isAdjacent(30, null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> matrixUndir.isAdjacent(30, 323));
        Assertions.assertThrows(IllegalArgumentException.class, () -> matrixUndir.isAdjacent(323, 30));
        Assertions.assertFalse(matrixUndir.isAdjacent(20, 30));
        matrixUndir.addEdge(Edge.getEdgeByVertexes(30, 20));
        Assertions.assertTrue(matrixUndir.isAdjacent(20, 30));
        Assertions.assertTrue(matrixUndir.isAdjacent(30, 20));
        matrixUndir.removeEdge(Edge.getEdgeByVertexes(30, 20));
        Assertions.assertFalse(matrixUndir.isAdjacent(20, 30));
    }

    @Test
    void size() {
        Assertions.assertEquals(0, matrixUndir.size());
        matrixUndir.addVertex(30);
        matrixUndir.addVertex(20);
        Assertions.assertEquals(0, matrixUndir.size());
        matrixUndir.addEdge(Edge.getEdgeByVertexes(30, 20));
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
        matrixUndir.addVertex(30);
        matrixUndir.addVertex(345);
        matrixUndir.addVertex(70);
        matrixUndir.addVertex(56);
        matrixUndir.addEdge(Edge.getEdgeByVertexes(30, 345));
        matrixUndir.addEdge(Edge.getEdgeByVertexes(345, 56));
        VisitResult visitResult = matrixUndir.getBFSTree(30);
        Assertions.assertNotNull(visitResult);
        Assertions.assertEquals(VisitResult.Color.BLACK, visitResult.getColor(30));
        Assertions.assertEquals(VisitResult.Color.BLACK, visitResult.getColor(345));
        Assertions.assertEquals(30, visitResult.getPartent(345));
        Assertions.assertEquals(VisitResult.Color.BLACK, visitResult.getColor(56));
        Assertions.assertEquals(345, visitResult.getPartent(56));
        Assertions.assertEquals(VisitResult.Color.WHITE, visitResult.getColor(70));
        Assertions.assertNull(visitResult.getPartent(30));

        Assertions.assertThrows(IllegalArgumentException.class, () -> matrixUndir.getBFSTree(1231));
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