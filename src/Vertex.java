import java.util.LinkedList;

/**
 * Created by caglar on 11.05.2018.
 */
public class Vertex implements Comparable<Vertex>{
    private String name;
    private int isMaintain;
    private int total;
    public double minDistance = Double.POSITIVE_INFINITY;
    public LinkedList<Vertex> path;

    public int compareTo(Vertex other){
        return Double.compare(minDistance,other.minDistance);
    }
    public Vertex(String name) {
        this.name = name;
        this.isMaintain = 0;
        this.total = 0;
        path = new LinkedList<Vertex>();
    }

    public String getName() {
        return name;
    }

    public int getIsMaintain() {
        return isMaintain;
    }
    public void setIsMaintain(int isMaintain) {
        this.isMaintain = isMaintain;
    }

    public int getTotal() {
        return total;
    }
    public void setTotal() {
        this.total += 1;
    }

    public double getMinDistance() {
        return minDistance;
    }
    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
    }
}
