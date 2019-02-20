import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Created by caglar on 12.05.2018.
 */
public class Dijkstra {

    public void calculate(Graph graph, Vertex source, double trainVelocity, double switchTime) {

        source.setMinDistance(0);
        PriorityQueue<Vertex> queue = new PriorityQueue<Vertex>();
        queue.add(source);
        double time = 0.0;
        while (!queue.isEmpty()) {
            Vertex u = queue.poll();
            for (Edge edge : graph.getAdjacencyMap().get(u)) {

                time = (edge.getWeight() / trainVelocity) * 60.0;

                if(edge.getIsBroken() == 0 && edge.getIsActive() == 1){
                    if (edge.getDestination().getMinDistance() > u.getMinDistance() + time && edge.getDestination().getIsMaintain() == 0 && edge.getSource().getIsMaintain() == 0) {
                        queue.remove(edge.getDestination());
                        edge.getDestination().setMinDistance(u.getMinDistance() + time);
                        edge.getDestination().path = new LinkedList<Vertex>(u.path);
                        edge.getDestination().path.add(u);
                        queue.add(edge.getDestination());
                    }
                }
                if (edge.getIsActive() == 0 && edge.getIsBroken() == 0) {
                    if (edge.getDestination().getMinDistance() > u.getMinDistance() + time + switchTime && edge.getDestination().getIsMaintain() == 0 && edge.getSource().getIsMaintain() == 0) {
                        queue.remove(edge.getDestination());
                        edge.getDestination().setMinDistance(u.getMinDistance() + time + switchTime);
                        edge.getDestination().path = new LinkedList<Vertex>(u.path);
                        edge.getDestination().path.add(u);
                        queue.add(edge.getDestination());
                    }
                }
            }
        }
    }
}

