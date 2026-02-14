import java.util.*;

public class SearchNode {
    State state;
    int depth;
    SearchNode parent;

    public SearchNode(State state, int depth, SearchNode parent) {
        this.state = state;
        this.depth = depth;
        this.parent = parent;
    }

    public void printLineage() {
        if (this.parent == null) {
            System.out.print("("+this.state.agent_row+", "+this.state.agent_col+")");
            return;
        }
        this.parent.printLineage();
        System.out.print(" -> ("+this.state.agent_row+", "+this.state.agent_col+")");
    }

    public ArrayList<SearchNode> expand() {
        ArrayList<SearchNode> children = new ArrayList<>();
        for (char action: state.getValidActions()) {
            children.add(new SearchNode(state.transition_model(action), this.depth + 1, this));
        }
        return children;
    }

    public SearchNode breadthFirst() {
//         int maxDepth = 0;
         int nodesExtended = 0;
        if (this.state.isGoal()) {
            return this;
        }
        Queue<SearchNode> frontier = new LinkedList<SearchNode>();
        frontier.add(this);
        Set<State> reached = new HashSet<State>();
        reached.add(this.state);
        while (! frontier.isEmpty()) {
            SearchNode node = frontier.remove();
            nodesExtended++;
            for (SearchNode child: node.expand()) {
                State s = child.state;
                if (s.isGoal()) {
                    System.out.println("Search COMPLETE");
                    System.out.println("Nodes Extended: "+ nodesExtended);
                    return child;
                }
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

                    frontier.add(child);
                }
            }
        }
        // Return the failure node
        return new SearchNode(new State(0, 0), -1, null);
    }

    
}
