import java.util.ArrayList;
import java.util.Arrays;

public class State {
    public static char[][] grid = null; //INITIALIZE in main
    public static ArrayList<Monster> monsters = null; //INITIALIZE in main
    public static final ArrayList<Character> actions = new ArrayList<>(Arrays.asList('U', 'D', 'L', 'R'));
    public int agent_row; //INITIALIZE in main
    public int agent_col; //INITIALIZE in main


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        State other = (State) obj;

        return this.agent_row == other.agent_row && this.agent_col == other.agent_col;
    }

    @Override
    public int hashCode() {
        return 31 * agent_row + agent_col;
    }

    public State(int agent_row, int agent_col) {
        this.agent_row = agent_row;
        this.agent_col = agent_col;
    }

    public boolean isSafe() {
        int distance;
        for (Monster monster: monsters) {
            distance = Math.abs(monster.row - agent_row) + Math.abs(monster.col - agent_col);
            if (distance <= monster.distance) {
                return false;
            }
        }
        return true;
    }
    public boolean isGoal() {
        return isSafe() && grid[agent_row][agent_col] == 'E';
    }

    public ArrayList<Character> getValidActions() {
        ArrayList<Character> validActions = (ArrayList<Character>) actions.clone();
        State newState = new State(agent_row - 1, agent_col);
        if (grid[agent_row - 1][agent_col] == '#' || !newState.isSafe()) {
            validActions.remove(Character.valueOf('U'));
        }
        newState.agent_row = this.agent_row + 1;
        if (grid[agent_row + 1][agent_col] == '#' || !newState.isSafe()) {
            validActions.remove(Character.valueOf('D'));
        }
        newState.agent_row = this.agent_row;
        newState.agent_col = this.agent_col - 1;
        if (grid[agent_row][agent_col - 1] == '#' || !newState.isSafe()) {
            validActions.remove(Character.valueOf('L'));
        }
        newState.agent_col = this.agent_col + 1;
        if (agent_row == 5 && agent_col == 4) {
            System.out.print("");
        }
        if (grid[agent_row][agent_col + 1] == '#' || !newState.isSafe()) {
            validActions.remove(Character.valueOf('R'));
        }
        return validActions;
    }

    public State transition_model(char action) {
        State newState = null;
        if (action == 'U') {
            newState = new State(agent_row - 1, agent_col);
        } else if (action == 'D') {
            newState = new State( agent_row + 1, agent_col);
        } else if (action == 'R') {
            newState = new State(agent_row, agent_col + 1);
        } else if (action == 'L') {
            newState = new State(agent_row,agent_col - 1);
        }

        return newState;
    }

}
