import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by caglar on 11.05.2018.
 */
public class Graph {
    private Map<Vertex, LinkedList<Edge>> adjacencyMap;

    public Graph() {
        adjacencyMap = new HashMap<Vertex, LinkedList<Edge>>();
    }

    public int size(){
        return this.getAdjacencyMap().size();
    }

    public void addEdge(Vertex source, Vertex destination, Vertex switchTo) {
        Edge edge = new Edge(source, destination, switchTo);
        if (destination.getName().equals(switchTo.getName())){
            edge.setIsActive(1);
        }
        adjacencyMap.get(source).addFirst(edge);
    }

    public void hasEdgeTo(String source, String destination, double weight) {
        for (Vertex v : adjacencyMap.keySet()) {
            LinkedList<Edge> list = adjacencyMap.get(v);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getSource().getName().equals(source)) {
                    if (list.get(i).getDestination().getName().equals(destination))
                        list.get(i).setEdgeWeight(weight);
                }
                if (list.get(i).getSource().getName().equals(destination)) {
                    if (list.get(i).getDestination().getName().equals(source))
                        list.get(i).setEdgeWeight(weight);
                }
            }
        }
    }

    public Map<Vertex, LinkedList<Edge>> getAdjacencyMap() {
        return adjacencyMap;
    }
}
