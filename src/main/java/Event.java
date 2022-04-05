import java.util.*;


public class Event {
    /**
     *
     */

    static Scanner sc = new Scanner(System.in);
    private char[] alphabet;
    private final String choice_invalid_msg = "Choice invalid!";

    // from event_definitions.csv
    private Integer id = null;
    private String description = "";
    private String prompt = "";
    private ArrayList<Integer> child_ids = new ArrayList<Integer>();

    private ArrayList<Event> children = new ArrayList<Event>();
    private String[] actions = {};
    private Map<String, Event> choices = new HashMap();

    public Event(Integer id, String description, String prompt, String child_ids_str) {
        reset_alphabet();
        this.id = id;
        this.description = description;
        this.prompt = prompt;
        if (child_ids_str != "") {
            String[] child_ids_spl = child_ids_str.split(";");
            for (String child_id_str : child_ids_spl) {
                Integer child_id = Integer.parseInt(child_id_str);
                this.child_ids.add(child_id);
            }
        }
    }

    public void print() {
        System.out.print("\nEvent" + id + " -> ");
        if (children != null) {
            for(Event e : children) {
                System.out.print(e.id + "; ");
            }
        }
        System.out.println();
    }

    public ArrayList<Integer> getChildIds() {
        return child_ids;
    }

    public Integer getId() {
        return id;
    }

    public void addChild(Event to_event) {
        // add child event edge from this node to to_event node
        children.add(to_event);
    }

    private void reset_alphabet() {
        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    }


    private void assign_actions() {
        for (Event child: children) {
            // convert next available alphabet char to String
            String selector = String.valueOf(alphabet[0]);
            // keep all but 0th index, i.e. pop first char we just used off front
            alphabet = Arrays.copyOfRange(alphabet, 1, alphabet.length-1);
            // store choice
            choices.put(selector, child);
        }
    }

    private void display_prompt() {
        System.out.println(this.prompt);
    }

    private void display_choices() {
        for (Map.Entry<String, Event> entry : choices.entrySet()) {
            String key = entry.getKey();
            Event value = entry.getValue();
            System.out.println("\t" + key + ": " + value.description);
        }
    }

    private String prompt_for_selection() {
//        String selection = System.console().readLine();
        if (sc.hasNextLine()) {
            String selection = sc.nextLine();
            selection = selection.toUpperCase(Locale.ROOT);
            if (choice_valid(selection)) {
                return selection;
            }
        }
        System.out.println(choice_invalid_msg);
        return choice_invalid_msg;
    }

    private boolean choice_valid(String selection) {
        if (choices.containsKey(selection)) {
            return true;
        }
        return false;
    }

    public void play() {
//        System.out.println("Playing event!");
        // display the event prompt
        display_prompt();

        // check if end game condition met (i.e. null children)
        if (children.size() == 0) {
            System.exit(0);
        }

        // if children, list display all actions player can take
        reset_alphabet();
        assign_actions();
        display_choices();

        // continue prompting user until valid input selected
        String player_choice = choice_invalid_msg;
        while (player_choice == choice_invalid_msg) {
            player_choice = prompt_for_selection();
        }

        // now play the selected event!
        Event selected_event = choices.get(player_choice);
        selected_event.play();
    }
}


