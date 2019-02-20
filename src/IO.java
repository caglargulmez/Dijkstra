import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static jdk.nashorn.internal.objects.Global.Infinity;

/**
 * Created by caglar on 11.05.2018.
 */
public class IO {
    String fileName;

    public IO(String fileName) {
        this.fileName = fileName;
    }

/* ################### "TOPOLOGY.IN"  &  "DISTANCES.IN" #########################*/
/* ################# EDGE WEIGHTED GRAPH IMPLEMENTATION #########################*/

    //calculate the size of initial graph from input = "topology.in"
    public int sizeOfGraph() throws IOException {
        FileReader file = new FileReader(this.fileName);
        BufferedReader buffer = new BufferedReader(file);
        String line;
        int counter = 0;
        while ((line = buffer.readLine()) != null) {
            counter++;
        }
        file.close();
        buffer.close();
        return counter;
    }

    //find railway which is to destination from source
    public Edge findEdge(Graph graph, String source, String destination){
        for (Edge e : graph.getAdjacencyMap().get(findVertex(graph, source))){
            if (e.getDestination().getName().equals(destination))
                return e;
        }
        return null;
    }

    //returns Intersection from given value
    public Vertex findVertex(Graph graph, String vertex) {
        for (Vertex v : graph.getAdjacencyMap().keySet()) {
            if(v.getName().equals(vertex)){
                return v;
            }
        }
        return null;
    }

    //make initial graph from input = "topology.in"
    public void initGraph(Graph graph) throws IOException {
        FileReader file = new FileReader(this.fileName);
        BufferedReader buffer = new BufferedReader(file);
        String line;

        while ((line = buffer.readLine()) != null) {
            String[] firstStr = line.split(":");
            String sourceVertex = firstStr[0];
            Vertex v = new Vertex(sourceVertex);
            LinkedList<Edge> edgeList = new LinkedList<>();
            graph.getAdjacencyMap().put(v, edgeList);
        }
        file.close();
        buffer.close();
    }

    //add railways without weights from input = "topology.in"
    public void makeGraph(Graph graph) throws IOException {
        FileReader file = new FileReader(this.fileName);
        BufferedReader buffer = new BufferedReader(file);
        String line;

        while ((line = buffer.readLine()) != null) {

            String[] firstStr = line.split(":");
            String sourceVertex = firstStr[0];
            String[]  secondStr = firstStr[1].split(">");
            String switchTo = secondStr[1];
            String[] thirdStr = secondStr[0].split(",");
            for (int i = 0; i < thirdStr.length ; i++) {
                graph.addEdge(findVertex(graph, sourceVertex), findVertex(graph, thirdStr[i]), findVertex(graph, switchTo));
            }

        }
        file.close();
        buffer.close();
    }

    //set weight of railways from input = "distances.in"
    public void setDistances(Graph graph) throws IOException {
        FileReader file = new FileReader(this.fileName);
        BufferedReader buffer = new BufferedReader(file);
        String line;

        while ((line = buffer.readLine()) != null) {
            String[] firstStr = line.split(" ");
            String[] secondStr = firstStr[0].split("-");
            double weight = Double.parseDouble(firstStr[1]);
            String sourceValue = secondStr[0];
            String destinationValue = secondStr[1];
            graph.hasEdgeTo(sourceValue, destinationValue, weight);

        }
        file.close();
        buffer.close();
    }

/*###############################################################################*/

/*##########################  COMMAND FUNCTIONS  ################################*/

    //BREAK COMMAND - breaks a specified rail
    public void breakEdge(Graph graph, String source, String destination) {
        for (Vertex v : graph.getAdjacencyMap().keySet()) {
            if (v.getName().equals(source)){
                LinkedList<Edge> list = graph.getAdjacencyMap().get(v);
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getDestination().getName().equals(destination)){
                        list.get(i).setIsBroken(1);
                    }
                }
            }
        }
    }

    //REPAIR COMMAND - repair broken rail
    public void repair(Graph graph, String source, String destination) {
        for (Vertex v : graph.getAdjacencyMap().keySet()) {
            if (v.getName().equals(source)){
                LinkedList<Edge> list = graph.getAdjacencyMap().get(v);
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getDestination().getName().equals(destination)){
                        list.get(i).setIsBroken(0);
                    }
                }
            }
        }
    }

    //LISTBROKENRAILS COMMAND - displays broken rails
    public void listBrokenEdges(Graph graph) {
        System.out.print("\tBroken rails: ");
        String str = "";
        for (Vertex v : graph.getAdjacencyMap().keySet()){
            LinkedList<Edge> list = graph.getAdjacencyMap().get(v);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getIsBroken() == 1){
                    str += list.get(i).getSource().getName() + ">" + list.get(i).getDestination().getName() + " ";
                }
            }
        }
        String[] data = str.split(" ");
        ArrayList<String> data2 = new ArrayList<String>();
        for (int i = 0; i < data.length; i++) {
            data2.add(data[i]);
        }
        Collections.sort(data2);
        for (int i = 0; i < data2.size(); i++) {
            if (i < data2.size() - 1)
                System.out.print(data2.get(i) + " ");
            if (i == data2.size() - 1)
                System.out.println(data2.get(i));
        }
    }

    //LISTACTIVERAILS COMMAND - displays active rails
    public void listActiveEdges(Graph graph) {
        System.out.print("\tActive Rails: ");
        String str = "";
        for (Vertex v : graph.getAdjacencyMap().keySet()){
            LinkedList<Edge> list = graph.getAdjacencyMap().get(v);
            for (int i = 0; i < list.size() ; i++) {
                if (list.get(i).getIsActive() == 1){
                    str += v.getName() + ">" + list.get(i).getDestination().getName() + " ";
                }
            }
        }
        String[] data = str.split(" ");
        ArrayList<String> data2 = new ArrayList<String>();
        for (int i = 0; i < data.length; i++) {
            data2.add(data[i]);
        }
        Collections.sort(data2);
        for (int i = 0; i < data2.size(); i++) {
            if (i < data2.size() -1 )
                System.out.print(data2.get(i) + " ");
            if (i == data2.size()-1)
                System.out.print(data2.get(i));
        }
        System.out.println();
    }

    //MAINTAIN COMMAND - set a specified Intersection under maintenance
    public void maintainVertex(Graph graph, String vertex) {
        for (Vertex v : graph.getAdjacencyMap().keySet()) {
            if (v.getName().equals(vertex)) {
                v.setIsMaintain(1);
            }
        }
    }

    //SERVICE COMMAND - puts a specified Intersection into service again
    public void service(Graph graph, String vertex) {
        for (Vertex v : graph.getAdjacencyMap().keySet()){
            if (v.getName().equals(vertex))
                v.setIsMaintain(0);
        }
    }

    //LISTMAINTAINS COMMAND - displays Intersections that are under maintenance
    public void listMaintainIntersections(Graph graph) {
        System.out.print("\tIntersections under maintenance: ");
        String str = "";
        for (Vertex v : graph.getAdjacencyMap().keySet()){
            if (v.getIsMaintain() == 1) {
                str += v.getName() + " ";
            }
        }
        String[] data = str.split(" ");
        ArrayList<String> data2 = new ArrayList<String>();
        for (int i = 0; i < data.length; i++) {
            data2.add(data[i]);
        }
        Collections.sort(data2);
        for (int i = 0; i < data2.size(); i++) {
            if (i < data2.size() - 1)
                System.out.print(data2.get(i) + " ");
            if (i == data2.size() - 1)
                System.out.println(data2.get(i));
        }
    }

    //ADD COMMAND - add new Intersection to Rail Network
    public void addVertex(Graph graph, String name) {
        Vertex newVertex = new Vertex(name);
        LinkedList<Edge> edgeList = new LinkedList<>();
        graph.getAdjacencyMap().put(newVertex, edgeList);
    }

    //LINK COMMAND - add new rail
    public void link(Graph graph, String data) {
        String[] firstStr = data.split(":");
        String source = firstStr[0];
        String[]  secondStr = firstStr[1].split(">");
        String switchTo = secondStr[1];
        String[] thirdStr = secondStr[0].split(",");

        for (int i = 0; i < thirdStr.length ; i++) {
            String str = "";
            String destination = thirdStr[i].split("-")[0];
            graph.addEdge(findVertex(graph, source), findVertex(graph, destination), findVertex(graph, switchTo));
            graph.addEdge(findVertex(graph, destination), findVertex(graph, source), findVertex(graph, destination));
        }
        for (int i = 0; i < thirdStr.length ; i++) {
            String name2 = thirdStr[i].split("-")[0];
            double weight = Double.parseDouble(thirdStr[i].split("-")[1]);
            graph.hasEdgeTo(source, name2, weight);
            graph.hasEdgeTo(name2, source, weight);
        }
    }

    //ROUTE COMMAND - finds a shortest railway from a specified Intersection to another one
    public void route(Graph graph, String vertices, String velocity, double switchTime) {
        String startVertex = vertices.split(">")[0];
        String endVertex = vertices.split(">")[1];
        double trainVelocity = Double.parseDouble(velocity);
        Dijkstra dijkstra = new Dijkstra();
        for(Vertex v : graph.getAdjacencyMap().keySet()){
            if (v.getName().equals(startVertex)){
                dijkstra.calculate(graph, v, trainVelocity, switchTime);
            }
        }
        String str = "";
        for (Vertex v : graph.getAdjacencyMap().keySet()){
            if (v.getName().equals(endVertex)){
                if (v.minDistance == Infinity){
                    //System.out.println("\tNo route from " + startVertex + " to " + endVertex + " found currently!");
                }else {
                    System.out.printf("\tTime (in min): %.3f\n", v.minDistance);
                    System.out.print("\tTotal # of switch changes: " );
                    for (Vertex intersection : v.path) {
                        str += intersection.getName() + " ";
                        intersection.setTotal();
                    }
                    v.setTotal();
                    str += v.getName();
                }
            }
        }
        String[] edges = str.split(" ");
        int counter = 0;
        for (int i = 0; i < edges.length - 1  ; i++) {
            if (findEdge(graph, edges[i], edges[i+1]).getIsActive() == 0){
                for (Edge e : graph.getAdjacencyMap().get(findVertex(graph, edges[i]))){
                    if (e.getIsActive() == 1){
                        e.setIsActive(0);
                    }
                }
                findEdge(graph, edges[i], edges[i+1]).setIsActive(1);
                for (Edge e : graph.getAdjacencyMap().get(findVertex(graph, edges[i]))){
                    e.setSwitchTo(findVertex(graph, edges[i+1]));
                }
                counter++;
            }
        }
        for (Vertex v : graph.getAdjacencyMap().keySet()){
            if (v.getName().equals(endVertex)) {
                if (v.minDistance == Infinity) {
                    System.out.println("\tNo route from " + startVertex + " to " + endVertex + " found currently!");
                }
                else{
                    System.out.println(counter);
                    System.out.print("\tRoute from " + startVertex + " to " + endVertex + ": ");
                    System.out.println(str);
                }
            }
        }
        for (Vertex v : graph.getAdjacencyMap().keySet()){
            v.path.clear();
            v.minDistance = Double.POSITIVE_INFINITY;
        }
    }

    //LISTROUTESFROM - displays all railways which is belong to a specified Intersection
    public void listRoutesFrom(Graph graph, String vertex) {
        String str = "";
        for (Vertex v : graph.getAdjacencyMap().keySet()){
            if (v.getName().equals(vertex)){
                LinkedList<Edge> list = graph.getAdjacencyMap().get(v);
                for (int i = 0; i < list.size(); i++) {
                    if (i < list.size() - 1)
                        str += list.get(i).getDestination().getName() + " ";
                    if (i == list.size() - 1){
                        str += list.get(i).getDestination().getName() + " ";
                    }
                }
            }
        }
        String[] data = str.split(" ");
        ArrayList<String> data2 = new ArrayList<String>();
        for (int i = 0; i < data.length; i++) {
            data2.add(data[i]);
        }
        Collections.sort(data2);
        String str2 = "";
        for (int i = 0; i < data2.size(); i++) {
            if (i < data2.size() - 1)
                str2 += data2.get(i) + " ";
            if (i == data2.size() - 1)
                str2 += data2.get(i);
        }
        System.out.println("\tRoutes from " + vertex + ": " + str2);
    }

    //TOTALNUMBEROFJUNCTIONS COMMAND - displays number of Intersection in Network
    public void totalNumberOfJunctions(Graph graph) {
        int size = graph.size();
        System.out.println("\tTotal # of junctions: " + size);
    }

    //TOTALNUMBEROFRAILS COMMAND - displays number of Railways in Network
    public void totalNumberOfRails(Graph graph) {
        int counter = 0;
        for (Vertex v : graph.getAdjacencyMap().keySet()){
            LinkedList<Edge> list = graph.getAdjacencyMap().get(v);
            for (int i = 0; i < list.size(); i++) {
                counter++;
            }
        }
        System.out.println("\tTotal # of rails: " + counter);
    }

    //LISTCROSSTIMES COMMAND - lists total number of trains that pass over every intersections
    public void listCrossTimes(Graph graph) {
        String str = "";
        for (Vertex v : graph.getAdjacencyMap().keySet()) {
            if (v.getTotal() != 0){
                str += v.getName() + "-" + v.getTotal() + " ";
            }
        }
        String[] data = str.split(" ");
        ArrayList<String> data2 = new ArrayList<String>();
        for (int i = 0; i < data.length; i++) {
            data2.add(data[i]);
        }
        Collections.sort(data2);
        String str2 = "";
        for (int i = 0; i < data2.size(); i++) {
            if (i < data2.size() - 1)
                str2 += data2.get(i).split("-")[0] + ":" + data2.get(i).split("-")[1] + " ";
            if (i == data2.size() - 1)
                str2 += data2.get(i).split("-")[0] + ":" + data2.get(i).split("-")[1];
        }
        System.out.println("\t# of cross times: " + str2);
    }

/*###############################################################################*/

    //command.txt
    public void commands(Graph graph, double switchTime) throws IOException {
        FileReader file = new FileReader(this.fileName);
        BufferedReader buffer = new BufferedReader(file);
        String line;
        String[] commands = {"ROUTE", "BREAK", "REPAIR", "MAINTAIN", "ADD", "LISTROUTESFROM", "SERVICE", "LINK", "LISTMAINTAINS",
                "LISTACTIVERAILS", "LISTBROKENRAILS", "LISTCROSSTIMES", "TOTALNUMBEROFJUNCTIONS", "TOTALNUMBEROFRAILS"};
        ArrayList<String> commands2 = new ArrayList<String>();
        for (int i = 0; i < commands.length; i++) {
            commands2.add(commands[i]);
        }
        while ((line = buffer.readLine()) != null) {
            String[] command = line.split(" ");
            System.out.println("COMMAND IN PROCESS >> " + line);
            switch (command[0]) {
                case "ROUTE":
                    route(graph, command[1], command[2], switchTime);
                    break;
                case "BREAK":
                    String source = command[1].split(">")[0];
                    String destination = command[1].split(">")[1];
                    breakEdge(graph, source, destination);
                    break;
                case "REPAIR":
                    String source2 = command[1].split(">")[0];
                    String destination2 = command[1].split(">")[1];
                    repair(graph, source2, destination2);
                    break;
                case "MAINTAIN":
                    maintainVertex(graph, command[1]);
                    break;
                case "ADD":
                    addVertex(graph, command[1]);
                    break;
                case "LISTROUTESFROM":
                    listRoutesFrom(graph, command[1]);
                    break;
                case "SERVICE":
                    service(graph, command[1]);
                    break;
                case "LINK":
                    link(graph, command[1]);
                    break;
                case "LISTMAINTAINS":
                    listMaintainIntersections(graph);
                    break;
                case "LISTACTIVERAILS":
                    listActiveEdges(graph);
                    break;
                case "LISTBROKENRAILS":
                    listBrokenEdges(graph);
                    break;
                case "LISTCROSSTIMES":
                    listCrossTimes(graph);
                    break;
                case "TOTALNUMBEROFJUNCTIONS":
                    totalNumberOfJunctions(graph);
                    break;
                case "TOTALNUMBEROFRAILS":
                    totalNumberOfRails(graph);
                    break;

                default:
                    System.out.println("\tUnrecognized command \"" + command[0] + "\"!");
                    break;
            }
            if(commands2.contains(command[0]))
                System.out.println("\tCommand " + "\"" + line + "\"" +  "  has been executed successfully!");
        }
    }
}