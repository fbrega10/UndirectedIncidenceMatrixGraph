package it.uniupo.graph.impl;

import upo.graph.base.Edge;
import upo.graph.base.WeightedGraph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

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
     * @throws IllegalArgumentException if the graph does not contain the edges
     * @throws NoSuchElementException   whether one of the vertexes does not belong to the graph.
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
     * @param integer the vertex that is being removed.
     * @throws NoSuchElementException if the vertex is included in the graph.
     *                                Overrides the superclass method, preserving the edges' weight.
     */
    @Override
    public void removeVertex(Integer integer) throws NoSuchElementException {
        if (!this.containsVertex(integer))
            throw new NoSuchElementException("No such vertex!");
        HashMap<Edge, Double> edgesWeightMap = new HashMap<>();
        this.getEdges()
                .forEach(edge -> {
                    if (edge.getSource().equals(integer) || edge.getTarget().equals(integer))
                        return;
                    Integer source = edge.getSource() > integer ? edge.getSource() - 1 : edge.getSource();
                    Integer target = edge.getTarget() > integer ? edge.getTarget() - 1 : edge.getTarget();
                    Double weight = this.getEdgeWeight(edge);
                    edgesWeightMap.put(Edge.getEdgeByVertexes(source, target), weight);
                });
        super.removeVertex(integer);
        edgesWeightMap.keySet().forEach(key -> {
            this.setEdgeWeight(key, edgesWeightMap.get(key));
        });
    }

    @Override
    public int addVertex() {
        Double[][] matr = new Double[this.size() + 1][this.edges.size()];
        for (int i = 0; i < this.size(); ++i) {
            System.arraycopy(this.matrix[i], 0, matr[i], 0, this.edges.size());
        }
        for (int i = 0; i < this.edges.size(); ++i)
            matr[this.size()][i] = Double.POSITIVE_INFINITY;
        this.matrix = matr;
        return this.size();
    }

    /**
     * Adds a new edge if not present, with the given source/target vertexes.
     * Every edge weight is preserved.
     *
     * @param edge
     * @throws IllegalArgumentException if the Edge contains vertexes that do not belong to the graph.
     */
    @Override
    public void addEdge(Edge edge) throws IllegalArgumentException {
        if (edge == null)
            throw new IllegalArgumentException("The edge cannot be null!");
        if (!this.containsVertex(edge.getSource()) || !this.containsVertex(edge.getTarget()))
            throw new IllegalArgumentException("Cannot have an edge with invalid source/target");
        if (edges.contains(edge) || edges.contains(Edge.getEdgeByVertexes(edge.getTarget(), edge.getSource())))
            return;
        this.edges.add(edge);
        Double[][] matr = new Double[this.size()][this.edges.size()];

        for (int i = 0; i < this.size(); ++i) {
            for (int j = 0; j < this.edges.size() - 1; ++j) {
                matr[i][j] = this.matrix[i][j];
            }
        }
        for (int i = 0; i < this.size(); ++i)
            matr[i][this.edges.size() - 1] = this.belongsToEdge(i, edge) ? 0.0 : Double.POSITIVE_INFINITY;
        this.matrix = matr;
    }

    /**
     * @param edge
     * @throws IllegalArgumentException One of the vertexes doesn't belong to the Graph's vertexes.
     * @throws NoSuchElementException   the edge is not included in the current edges.
     */
    @Override
    public void removeEdge(Edge edge) throws IllegalArgumentException, NoSuchElementException {
        if (!this.containsEdge(edge))
            throw new NoSuchElementException("No such edge.");
        this.edges.remove(edge);
        Double[][] matr = new Double[this.size()][this.edges.size()];
        for (int i = 0; i < this.size(); ++i) {
            for (int j = 0; j < this.edges.size(); ++j) {
                matr[i][j] = this.matrix[i][j];
            }
        }
        this.matrix = matr;
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
        if (!this.containsVertex(integer))
            throw new IllegalArgumentException(String.format("Vertex %d does not belong to the graph", integer));

        WeightedGraph dijkstraVisitGraph = new IncidMatrixUndirWeight();
        PriorityQueue<VertexDistance> priorityQueue =
                new PriorityQueue<>(this.size(), Comparator.comparingDouble((v1 -> v1.distance)));

        List<VertexDistance> vertexDistances = this.getVertices().stream()
                .map(VertexDistance::new)
                .peek(vertexDistance -> {
                    if (Objects.equals(vertexDistance.vertex, integer))
                        vertexDistance.setDistance(0.00);
                    priorityQueue.add(vertexDistance);
                    dijkstraVisitGraph.addVertex();
                })
                .collect(Collectors.toCollection(ArrayList::new));

        Set<Integer> blackVertexes = new HashSet<>();
        while (!priorityQueue.isEmpty()) {
            VertexDistance polledElement = priorityQueue.poll();
            blackVertexes.add(polledElement.vertex);
            this.getAdjacent(polledElement.vertex)
                    .stream()
                    .filter(v -> !blackVertexes.contains(v) )
                    .forEach(adjacent -> {
                        if (this.getEdgeWeight(Edge.getEdgeByVertexes(polledElement.vertex, adjacent)) + polledElement.distance
                                < priorityQueue.stream()
                                .filter(v -> Objects.equals(v.vertex, adjacent))
                                .map(v -> v.distance)
                                .findFirst().orElse(Double.POSITIVE_INFINITY)) {
                            VertexDistance element = priorityQueue.stream()
                                    .filter(v -> Objects.equals(v.vertex, adjacent))
                                    .findFirst()
                                    .orElse(null);
                            priorityQueue.remove(element);
                            element.setDistance(this.getEdgeWeight(Edge.getEdgeByVertexes(polledElement.vertex, adjacent)) + polledElement.distance);
                            element.setPredecessor(polledElement.vertex);
                            priorityQueue.add(element);
                        }
                    });
        }
        vertexDistances.forEach(element -> {
            if (element.predecessor == null)
                return;
            Edge minimunEdge = Edge.getEdgeByVertexes(element.predecessor, element.vertex);
            dijkstraVisitGraph.addEdge(minimunEdge);
            dijkstraVisitGraph.setEdgeWeight(minimunEdge, element.distance);
        });
        return dijkstraVisitGraph;
    }

    public static class VertexDistance {
        private final Integer vertex;
        private Integer predecessor;
        private Double distance;

        public VertexDistance(Integer integer) {
            this.vertex = integer;
            this.predecessor = null;
            this.distance = Double.POSITIVE_INFINITY;
        }

        public VertexDistance(Integer integer, Double distance) {
            this(integer);
            this.distance = distance;
        }

        public void setDistance(Double distance) {
            if (distance < 0)
                throw new UnsupportedOperationException("Cannot support negative weights in Dijkstra.");
            this.distance = distance;
        }

        public void setPredecessor(Integer predecessor) {
            this.predecessor = predecessor;
        }

        @Override
        public boolean equals(Object obj) {
            VertexDistance other = (VertexDistance)obj;
            return this.vertex.equals(other.vertex);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(vertex);
        }
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

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof IncidMatrixUndirWeight))
            return super.equals(o);
        IncidMatrixUndirWeight other = (IncidMatrixUndirWeight) o;
        boolean booleanMarker = this.getEdges().stream().allMatch(edge -> {
            if (!other.containsEdge(edge))
                return false;
            return this.getEdgeWeight(edge) == other.getEdgeWeight(edge);
        });
        return super.equals(other) && booleanMarker;
    }

    @Override
    public String toString() {
        return super.toString().replace("Incident matrix", "Weighted incident matrix");
    }
}
