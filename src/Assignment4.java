import java.io.IOException;

public class Assignment4 {

    public static void main(String[] args) throws IOException {
        //create graph
        Graph graph = new Graph();

        //make initial graph
        IO initGraph = new IO(args[0]);
        initGraph.initGraph(graph);

        //add edges without weights
        IO topologyIn = new IO(args[0]);
        topologyIn.makeGraph(graph);

        //set weight values
        IO distances = new IO(args[1]);
        distances.setDistances(graph);

        //commands
        IO commands = new IO(args[2]);
        double switchTime = Double.parseDouble(args[3]);
        commands.commands(graph, switchTime);



    }
}
