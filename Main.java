import java.util.ArrayList;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {

        // Processing input
        Scanner scnr = new Scanner(System.in);
        while (true) {
            System.out.println("Enter your problem: ");
        // First line
        int N, M, K;
        String[] nums = scnr.nextLine().trim().split(" ");
        N = Integer.parseInt(nums[0]);
        M = Integer.parseInt(nums[1]);
        K = Integer.parseInt(nums[2]);


        // Map
        char[][] grid = new char[N][M];
        int init_row = 0, init_col = 0, goal_row = 0, goal_col = 0;
        String line;
        char temp;

            for (int i = 0; i < N; i++) {
                line = scnr.nextLine();
                for (int j = 0; j < M; j++) {
                    temp = line.charAt(j);
                    if (temp == 'S') {
                        init_row = i;
                        init_col = j;
                    } else if (temp == 'E') {
                        goal_row = i;
                        goal_col = j;
                    }
                    grid[i][j] = temp;
                }
            }

            // Monsters
            ArrayList<Monster> monsters = new ArrayList<>();
            for (int k = 0; k < K; k++) {
                nums = scnr.nextLine().trim().split(" ");
                monsters.add(new Monster(Integer.parseInt(nums[0]) - 1, Integer.parseInt(nums[1]) - 1, Integer.parseInt(nums[2])));
            }

            // Input processing complete
            // Setting up State
            State.grid = grid;
            State.monsters = monsters;
            State initState = new State(init_row, init_col);
            State goalState = new State(goal_row, goal_col);
            System.out.println("State set up");

            // Setting up UCS search node and search
            SearchNode UCSNode = new SearchNode(initState, 0, null);
            System.out.println("Starting UCS");
            SearchNode goalNode = UCSNode.breadthFirst();
            System.out.println("Depth: " + goalNode.depth);
            goalNode.printLineage();

            // Setting up A* search node and search
            PriorityNode.goalState = goalState;
            PriorityNode AStarNode = new PriorityNode(initState, 0, null, PriorityNode.L1_dist(initState));
            System.out.println();
            System.out.println("Starting A* search");
            PriorityNode goalNode2 = AStarNode.AStar();
            System.out.println("Depth: " + goalNode2.depth);
            goalNode2.printLineage();
            System.out.println();
        }
    }
}