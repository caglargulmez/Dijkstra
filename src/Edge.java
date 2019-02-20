/**
 * Created by caglar on 11.05.2018.
 */
public class Edge {

    private Vertex source;
    private Vertex destination;
    private double weight;
    private Vertex switchTo;
    private int isBroken;
    private int isActive;

    public Edge(Vertex source, Vertex destination, Vertex switchTo) {
        this.source = source;
        this.destination = destination;
        this.switchTo = switchTo;
        this.isBroken = 0;
        this.isActive = 0;
        this.weight = 0.0;
    }

    public int getIsActive() {
        return isActive;
    }
    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public void setEdgeWeight(double weight) {
        this.setWeight(weight);
    }

    public Vertex getSource() {
        return source;
    }
    public Vertex getDestination() {
        return destination;
    }

    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setSwitchTo(Vertex switchTo) {
        this.switchTo = switchTo;
    }

    public int getIsBroken() {
        return isBroken;
    }
    public void setIsBroken(int isBroken) {
        this.isBroken = isBroken;
    }
}
