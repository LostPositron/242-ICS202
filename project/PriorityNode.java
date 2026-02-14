import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class PriorityNode extends SearchNode implements Comparable<PriorityNode> {
    public static State goalState;
    int priority;

    public PriorityNode(State state, int depth, SearchNode parent, int heuristic) {
        super(state, depth, parent);
        this.priority = heuristic;
    }

    @Override
    public int compareTo(PriorityNode o) {
        return this.priority - o.priority;
    }

    public ArrayList<SearchNode> expand() {
        ArrayList<SearchNode> children = new ArrayList<>();
        State newState;
        for (char action: state.getValidActions()) {
            newState = state.transition_model(action);
            children.add(new PriorityNode(newState, this.depth + 1, this, L1_dist(newState)));
        }
        return children;
    }

    public PriorityNode AStar() {
//        int maxDepth = 0;
        int nodesExtended = 0;
        if (this.state.isGoal()) {
            return this;
        }
        PriorityQueue<PriorityNode> frontier = new PriorityQueue<>();
        frontier.add(this);
        Set<State> reached = new HashSet<State>();
        reached.add(this.state);
        while (! frontier.isEmpty()) {
            PriorityNode node = frontier.remove();
            if (node.state.isGoal()) {
                System.out.println("Search COMPLETE");
                System.out.println("Nodes Extended: "+ nodesExtended);
                return node;
            }
            nodesExtended++;
            for (SearchNode child: node.expand()) {
                State s = child.state;

                if (! reached.contains(s)) {
                    reached.add(s);
//                    if (node.depth > maxDepth) {
//                        System.out.print(",  Depth: " + node.depth + "  ");
//                        maxDepth = node.depth;
//                    }
//                    for (SearchNode test: frontier) {
//                        System.out.print(" Row: " + test.state.agent_row + " Col: " + test.state.agent_col + " Depth: " + test.depth + ", ");
//                    }
//                    System.out.println();

                    frontier.add((PriorityNode) child);
                }
            }
        }
        // Return the failure node
        return new PriorityNode(new State(0, 0), -1, null, 0);
    }

    public static int L1_dist(State currState) {
        return Math.abs(currState.agent_row - goalState.agent_row) + Math.abs(currState.agent_col - goalState.agent_col);
    }
}
