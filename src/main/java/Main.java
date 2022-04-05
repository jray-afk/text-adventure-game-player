import tech.tablesaw.api.Table;
import java.io.IOException;

public class Main {

    private static Table read_event_definitions() {
        try {
            Table t = Table.read().csv("src/main/resources/event_definitions.csv");
            // debug
//            System.out.println(t.columns());
//            for (Row row: t) {
//                System.out.println(row);
//            }
//            t.print();
//            t.first(2);
//            System.out.println("Printed table.");
            return t;
        } catch (IOException e) {
//            System.out.println("ERROR loading CSV file!");
            e.printStackTrace();
        }
//        System.out.println("Finished trying to load table.");
        return null;
    }

    private static EventGraph build_event_graph(Table event_defs) {
        EventGraph eg = new EventGraph(event_defs);
        return eg;
    }

    public static void main(String[] args) {
        // read events config
        Table event_defs = read_event_definitions();
        // generate graph of events
        EventGraph eg = build_event_graph(event_defs);
//        eg.print();
        // play first event!
        eg.play();
    }
}


