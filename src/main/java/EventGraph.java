import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;

import java.util.ArrayList;

public class EventGraph {
    ArrayList<Event> graph;
    int V;

    public EventGraph(Table event_defs) {
        // # of vertices in graph is # of events
        V = event_defs.rowCount();
        // create graph
        graph = new ArrayList<Event>();
        // create nodes into graph
        createNodes(event_defs);
        // connect nodes within graph
        connectEvents();
    }

    private void createNodes(Table event_defs) {
        for (Row event_def : event_defs) {
            Integer id = event_def.getInt("id");
            String desc = event_def.getString("description");
            String prompt = event_def.getString("prompt");
            String child_ids_str = event_def.getString("children");
            addNode(id, desc, prompt, child_ids_str);
        }
    }

    void connectEvents() {
        for (Event e : graph) {
            for (Integer child_id : e.getChildIds()) {
                addEdge(e.getId(), getNode(child_id));
            }
        }
    }

    private Event getNode(Integer id) {
        return graph.get(id);
    }

    private void addNode(Integer id, String desc, String prompt, String child_ids_str) {
        Event e = new Event(id, desc, prompt, child_ids_str);
        graph.add(e);
    }

    private void removeNode(Integer id) {
        // note that id must match array index in csv file (start at 0)
        graph.remove(id);
    }

    private void addEdge(Integer from_id, Event to_event) {
        Event from_node = getNode(from_id);
        from_node.addChild(to_event);
    }

    public void print() {
        for (Event e : graph) {
            e.print();
        }
    }

    public void play() {
        // play first event!
        getNode(0).play();
    }


}
