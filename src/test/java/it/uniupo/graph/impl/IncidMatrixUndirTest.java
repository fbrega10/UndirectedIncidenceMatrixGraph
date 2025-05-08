package it.uniupo.graph.impl;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import upo.graph.base.Edge;
import upo.graph.base.VisitResult;

import java.util.NoSuchElementException;
import java.util.Set;

class IncidMatrixUndirTest {

    private IncidMatrixUndir matrixUndir;

    @BeforeEach
    void setUp() {
        matrixUndir = new IncidMatrixUndir();
    }

    @Test
    @DisplayName("Empty constructor test")
    void emptyConstructorTest() {
        Assertions.assertEquals(0, matrixUndir.getVertices().size());
        Assertions.assertEquals(0, matrixUndir.getEdges().size());
    }

    @Test
    @DisplayName("Equals test")
    void equalsTest() {
        IncidMatrixUndir other = new IncidMatrixUndir();
        Assertions.assertEquals(matrixUndir, other);
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addEdge(Edge.getEdgeByVertexes(0, 1));
        other.addVertex();
        other.addVertex();
        other.addVertex();
        other.addEdge(Edge.getEdgeByVertexes(0, 1));
        Assertions.assertEquals(matrixUndir, other);
        other.addVertex();
        Assertions.assertNotEquals(matrixUndir, other);
        Assertions.assertNotEquals(null, matrixUndir);
        Assertions.assertNotEquals(new IncidMatrixUndir(), matrixUndir);
    }

    @Test
    @DisplayName("HashCode test")
    void hashCodeTest() {
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addEdge(Edge.getEdgeByVertexes(0, 1));
        IncidMatrixUndir other = new IncidMatrixUndir();
        other.addVertex();
        other.addVertex();
        other.addVertex();
        other.addEdge(Edge.getEdgeByVertexes(0, 1));
        Assertions.assertEquals(matrixUndir.hashCode(), other.hashCode());
        other.addVertex();
        Assertions.assertNotEquals(matrixUndir.hashCode(), other.hashCode());
    }

    @Test
    @DisplayName("addVertex test")
    void addVertex() {
        Assertions.assertEquals(0, matrixUndir.addVertex());
        Assertions.assertEquals(1, matrixUndir.addVertex());
        Assertions.assertEquals(2, matrixUndir.addVertex());
    }

    @Test
    @DisplayName("getVertices test")
    void getVertices() {
        Assertions.assertEquals(0, matrixUndir.getVertices().size());
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        Assertions.assertEquals(2, matrixUndir.getVertices().size());
    }

    @Test
    @DisplayName("getEdges test")
    void getEdges() {
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addEdge(Edge.getEdgeByVertexes(0, 1));
        Assertions.assertEquals(1, matrixUndir.getEdges().size());
        Assertions.assertEquals(0, matrixUndir.getEdges().iterator().next().getSource());
        Assertions.assertEquals(1, matrixUndir.getEdges().iterator().next().getTarget());
    }

    @Test
    @DisplayName("ContainsVertex test")
    void containsVertex() {
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        Assertions.assertTrue(matrixUndir.containsVertex(0));
        Assertions.assertTrue(matrixUndir.containsVertex(1));
        Assertions.assertFalse(matrixUndir.containsVertex(2));
    }

    @Test
    @DisplayName("removeVertex test")
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
        matrixUndir.removeVertex(2);
        Assertions.assertEquals(2, matrixUndir.size());
    }

    @Test
    @DisplayName("addEdge test")
    void addEdge() {
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        Edge edge = Edge.getEdgeByVertexes(0, 1);
        matrixUndir.addEdge(edge);
        Assertions.assertTrue(matrixUndir.containsEdge(edge));
        Assertions.assertThrows(IllegalArgumentException.class, () -> matrixUndir.addEdge(Edge.getEdgeByVertexes(2, 3)));
    }

    @Test
    @DisplayName("containsEdge test")
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
    @DisplayName("removeEdge test")
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
    @DisplayName("getAdjacent test")
    void getAdjacent() {
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        Assertions.assertEquals(0, matrixUndir.getAdjacent(0).size());
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
    @DisplayName("isAdjacent test")
    void isAdjacent() {
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        Assertions.assertFalse(matrixUndir.isAdjacent(0, 1));
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
    @DisplayName("size test")
    void size() {
        Assertions.assertEquals(0, matrixUndir.size());
        matrixUndir.addVertex();
        Assertions.assertEquals(1, matrixUndir.size());
        matrixUndir.addVertex();
        Assertions.assertEquals(2, matrixUndir.size());
        matrixUndir.removeVertex(1);
        Assertions.assertEquals(1, matrixUndir.size());
        matrixUndir.removeVertex(0);
        Assertions.assertEquals(0, matrixUndir.size());
    }

    @Test
    @DisplayName("isDirected test")
    void isDirected() {
        Assertions.assertFalse(matrixUndir.isDirected());
    }

    @Test
    @DisplayName("isCyclic test")
    void isCyclic() {
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addEdge(Edge.getEdgeByVertexes(0, 1));
        matrixUndir.addEdge(Edge.getEdgeByVertexes(1, 2));
        matrixUndir.addEdge(Edge.getEdgeByVertexes(2, 3));
        matrixUndir.addEdge(Edge.getEdgeByVertexes(3, 0));
        Assertions.assertTrue(matrixUndir.isCyclic());
        matrixUndir = new IncidMatrixUndir();
        for (int i = 0; i < 10; ++i)
            matrixUndir.addVertex();
        for (int i = 0; i < 5; ++i)
            matrixUndir.addEdge(Edge.getEdgeByVertexes(i, i + 1));
        Assertions.assertFalse(matrixUndir.isCyclic());
        matrixUndir = new IncidMatrixUndir();
        for (int i = 0; i < 10; ++i)
            matrixUndir.addVertex();
        for (int i = 0; i < 5; ++i)
            matrixUndir.addEdge(Edge.getEdgeByVertexes(i, i + 1));
        matrixUndir.addEdge(Edge.getEdgeByVertexes(5, 0));
        Assertions.assertTrue(matrixUndir.isCyclic());
        matrixUndir = new IncidMatrixUndir();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addEdge(Edge.getEdgeByVertexes(0, 1));
        matrixUndir.addEdge(Edge.getEdgeByVertexes(1, 2));
        matrixUndir.addEdge(Edge.getEdgeByVertexes(2, 0));
        Assertions.assertTrue(matrixUndir.isCyclic());
    }

    @Test
    @DisplayName("isDag test")
    void isDAG() {
       Assertions.assertFalse(matrixUndir.isDAG());
    }

    @Test
    @DisplayName("getBFSTree test")
    void getBFSTree() {
        //first we visit a connected graph, then a forest.
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addEdge(Edge.getEdgeByVertexes(0, 1));
        matrixUndir.addEdge(Edge.getEdgeByVertexes(1, 2));
        VisitResult visitResult = matrixUndir.getBFSTree(0);
        Assertions.assertEquals(VisitResult.Color.BLACK, visitResult.getColor(0));
        Assertions.assertEquals(VisitResult.Color.BLACK, visitResult.getColor(1));
        Assertions.assertEquals(VisitResult.Color.BLACK, visitResult.getColor(2));
        Assertions.assertEquals(VisitResult.Color.WHITE, visitResult.getColor(3));
        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> matrixUndir.getBFSTree(5));
        Assertions.assertEquals("The vertex does not belong to the graph.", ex.getMessage());
        matrixUndir = new IncidMatrixUndir();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        visitResult = matrixUndir.getBFSTree(0);
        Assertions.assertEquals(VisitResult.Color.BLACK, visitResult.getColor(0));
        Assertions.assertEquals(VisitResult.Color.WHITE, visitResult.getColor(1));
        Assertions.assertEquals(VisitResult.Color.WHITE, visitResult.getColor(2));
        Assertions.assertEquals(VisitResult.Color.WHITE, visitResult.getColor(3));
    }

    @Test
    @DisplayName("getDFSTreeRic test")
    void getDFSTreeRicTest() {
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addEdge(Edge.getEdgeByVertexes(0, 1));
        matrixUndir.addEdge(Edge.getEdgeByVertexes(1, 2));
        VisitResult visit = matrixUndir.getDFSTreeRic(0);
        Assertions.assertNotNull(visit);
        Assertions.assertEquals(VisitResult.Color.BLACK, visit.getColor(0));
        Assertions.assertEquals(1, visit.getStartTime(0));
        Assertions.assertEquals(VisitResult.Color.BLACK, visit.getColor(1));
        Assertions.assertEquals(2, visit.getStartTime(1));
        Assertions.assertEquals(VisitResult.Color.BLACK, visit.getColor(2));
        Assertions.assertEquals(3, visit.getStartTime(2));
        Assertions.assertEquals(VisitResult.Color.WHITE, visit.getColor(3));
        Assertions.assertEquals(VisitResult.Color.WHITE, visit.getColor(4));
        Assertions.assertEquals(VisitResult.Color.WHITE, visit.getColor(5));
        Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> matrixUndir.getDFSTreeRic(23));
        Assertions.assertEquals("Vertex does not belong to the Graph", e.getMessage());
    }

    @Test
    @DisplayName("belongsToEdge test")
    void belongsToEdgeTest() {
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        Edge edge = Edge.getEdgeByVertexes(0, 1);
        matrixUndir.addEdge(edge);
        Assertions.assertFalse(matrixUndir.belongsToEdge(3, edge));
        Assertions.assertTrue(matrixUndir.belongsToEdge(0, edge));
        Assertions.assertTrue(matrixUndir.belongsToEdge(1, edge));
    }

    @Test
    @DisplayName("getDFSTree test")
    void getDFSTree() {
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addEdge(Edge.getEdgeByVertexes(0, 1));
        matrixUndir.addEdge(Edge.getEdgeByVertexes(1, 2));
        VisitResult visit = matrixUndir.getDFSTree(0);
        Assertions.assertNotNull(visit);
        Assertions.assertEquals(VisitResult.Color.BLACK, visit.getColor(0));
        Assertions.assertEquals(1, visit.getStartTime(0));
        Assertions.assertEquals(VisitResult.Color.BLACK, visit.getColor(1));
        Assertions.assertEquals(2, visit.getStartTime(1));
        Assertions.assertEquals(VisitResult.Color.BLACK, visit.getColor(2));
        Assertions.assertEquals(3, visit.getStartTime(2));
        Assertions.assertEquals(VisitResult.Color.WHITE, visit.getColor(3));
        Assertions.assertEquals(VisitResult.Color.WHITE, visit.getColor(4));
        Assertions.assertEquals(VisitResult.Color.WHITE, visit.getColor(5));
        Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> matrixUndir.getDFSTree(23));
        Assertions.assertEquals("Vertex does not belong to the Graph", e.getMessage());
    }

    @Test
    @DisplayName("getDFSTOTForest test")
    void getDFSTOTForestTest() {
        //First step : visit an empty graph
        Assertions.assertThrows(IllegalArgumentException.class, () -> matrixUndir.getDFSTOTForest(0));
        matrixUndir.addVertex();
        Assertions.assertDoesNotThrow(() -> matrixUndir.getDFSTOTForest(0));
        //Second step : visit the connected graph
        matrixUndir.addVertex();
        matrixUndir.addEdge(Edge.getEdgeByVertexes(0, 1));
        VisitResult visitResult = matrixUndir.getDFSTOTForest(0);
        Assertions.assertEquals(VisitResult.Color.BLACK, visitResult.getColor(0));
        Assertions.assertEquals(VisitResult.Color.BLACK, visitResult.getColor(1));

        //Third step: Visit the whole forest
        matrixUndir = new IncidMatrixUndir();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addEdge(Edge.getEdgeByVertexes(0, 1));
        matrixUndir.addEdge(Edge.getEdgeByVertexes(1, 2));
        VisitResult forestVisit = matrixUndir.getDFSTOTForest(0);
        Assertions.assertEquals(VisitResult.Color.BLACK, forestVisit.getColor(0));
        Assertions.assertEquals(VisitResult.Color.BLACK, forestVisit.getColor(1));
        Assertions.assertEquals(VisitResult.Color.BLACK, forestVisit.getColor(2));
        Assertions.assertEquals(VisitResult.Color.BLACK, forestVisit.getColor(3));
        Assertions.assertEquals(VisitResult.Color.BLACK, forestVisit.getColor(4));
    }

    @Test
    @DisplayName("getDFSTOTForest test")
    void testGetDFSTOTForest() {
    }

    @Test
    @DisplayName("topologicalSort test")
    void topologicalSort() {
        UnsupportedOperationException e = Assertions.assertThrows(UnsupportedOperationException.class, () -> matrixUndir.topologicalSort());
        Assertions.assertEquals("Cannot find a topologicalSort of a graph represented as an undirected incident matrix.", e.getMessage());
    }

    @Test
    @DisplayName("stronglyConnectedComponents test")
    void stronglyConnectedComponents() {
        UnsupportedOperationException e = Assertions.assertThrows(UnsupportedOperationException.class, () -> matrixUndir.stronglyConnectedComponents());
        Assertions.assertEquals("Unsupported operation, this is an undirected matrix graph implementation.", e.getMessage());
    }

    @Test
    @DisplayName("connectedComponents test")
    void connectedComponents() {
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addVertex();
        matrixUndir.addEdge(Edge.getEdgeByVertexes(0, 1));
        matrixUndir.addEdge(Edge.getEdgeByVertexes(1, 2));
        matrixUndir.addEdge(Edge.getEdgeByVertexes(3, 4));
        matrixUndir.addEdge(Edge.getEdgeByVertexes(4, 5));
        Set<Set<Integer>> cc = matrixUndir.connectedComponents();
        Assertions.assertTrue(cc.contains(Set.of(0, 1, 2)));
        Assertions.assertTrue(cc.contains(Set.of(3, 4, 5)));
        Assertions.assertEquals(2, cc.size());
    }
}