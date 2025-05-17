package it.uniupo.graph.impl;

import upo.graph.base.Edge;
import upo.graph.base.WeightedGraph;

import java.util.NoSuchElementException;

public class IncidMatrixUndirWeight extends IncidMatrixUndir implements WeightedGraph {

    protected static final String UNSUPPORTED_IMPLEMENTATION = "Do not implement.";
    protected static final String VERTEX_NOT_PRESENT = "The graph does not contain the vertex : %d";

    /**
     * Constructor of IncidMatrixUndirWeight, an implementation of the WeightedGraph interface.
     * Each edge has its weight, it can be adjusted through the setEdgeWeight method.
     */
    protected IncidMatrixUndirWeight() {
        super();
    }

    /**
     * @param edge
     * @return the edge weight as double
     * @throws IllegalArgumentException  if the graph does not contain the edges
     * @throws NoSuchElementException whether one of the vertexes does not belong to the graph.
     */
    @Override
    public double getEdgeWeight(Edge edge) throws IllegalArgumentException, NoSuchElementException {
        if (!super.containsVertex(edge.getSource()))
            throw new IllegalArgumentException(String.format(VERTEX_NOT_PRESENT, edge.getSource()));
        if (!super.containsVertex(edge.getTarget()))
            throw new IllegalArgumentException(String.format(VERTEX_NOT_PRESENT, edge.getTarget()));
        if (!this.containsEdge(edge))
            throw new NoSuchElementException(String.format("The edge (%d, %d) does not belong to the graph", edge.getSource(), edge.getTarget()));
        return super.matrix[edge.getSource()][edges.indexOf(edge)];
    }

    @Override
    public void setEdgeWeight(Edge edge, double v) throws IllegalArgumentException, NoSuchElementException {
        this.getEdgeWeight(edge);
        int edgeIndex = edges.indexOf(edge);
        super.matrix[edge.getSource()][edgeIndex] = super.matrix[edge.getTarget()][edgeIndex] = v;
    }

    /**
     * @param integer
     * @return Currently unsupported, throws an UnsupportedOperationException.
     * @throws UnsupportedOperationException
     * @throws IllegalArgumentException
     */
    @Override
    public WeightedGraph getBellmanFordShortestPaths(Integer integer) throws UnsupportedOperationException, IllegalArgumentException {
        throw new UnsupportedOperationException(UNSUPPORTED_IMPLEMENTATION);
    }

    /**
     * @param integer
     * @return Currently unsupported, throws an UnsupportedOperationException.
     * @throws UnsupportedOperationException
     * @throws IllegalArgumentException
     */
    @Override
    public WeightedGraph getDijkstraShortestPaths(Integer integer) throws UnsupportedOperationException, IllegalArgumentException {
        throw new UnsupportedOperationException(UNSUPPORTED_IMPLEMENTATION);
    }

    /**
     * @param integer
     * @return Currently unsupported, throws an UnsupportedOperationException.
     * @throws UnsupportedOperationException
     * @throws IllegalArgumentException
     */
    @Override
    public WeightedGraph getPrimMST(Integer integer) throws UnsupportedOperationException, IllegalArgumentException {
        throw new UnsupportedOperationException(UNSUPPORTED_IMPLEMENTATION);
    }

    /**
     * @return Currently unsupported, throws an UnsupportedOperationException.
     * @throws UnsupportedOperationException
     */
    @Override
    public WeightedGraph getKruskalMST() throws UnsupportedOperationException {
        throw new UnsupportedOperationException(UNSUPPORTED_IMPLEMENTATION);
    }

    /**
     * @return Currently unsupported, throws an UnsupportedOperationException.
     * @throws UnsupportedOperationException
     */
    @Override
    public WeightedGraph getFloydWarshallShortestPaths() throws UnsupportedOperationException {
        throw new UnsupportedOperationException(UNSUPPORTED_IMPLEMENTATION);
    }

}
