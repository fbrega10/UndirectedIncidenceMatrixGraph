package it.uniupo.graph.impl;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import upo.graph.base.Edge;

import java.util.Set;

class IncidMatrixUndirTest {

    private IncidMatrixUndir matrixUndir;

    @BeforeEach
    void setUp() {
        matrixUndir = new IncidMatrixUndir();
    }

    @Test
    void emptyConstructorTest(){
        Assertions.assertEquals(0, matrixUndir.getVertices().size());
        Assertions.assertEquals(0, matrixUndir.getEdges().size());
    }

    @Test
    void addVertex() {
        matrixUndir.addVertex(20);
        Assertions.assertEquals(1, matrixUndir.getVertices().size());
        Assertions.assertEquals(1, matrixUndir.size());
        matrixUndir.addVertex(30);
        Assertions.assertEquals(2, matrixUndir.getVertices().size());
        Assertions.assertEquals(2, matrixUndir.size());
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
    void resizeTest(){
        Integer[][] matrix = IncidMatrixUndir.resize(new Integer[0][0], 2, 2);
        Assertions.assertEquals(2, matrix.length);
        Assertions.assertEquals(2, matrix[0].length);
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
    @AfterEach
    void postConstruct(){
        matrixUndir = new IncidMatrixUndir();
    }
}