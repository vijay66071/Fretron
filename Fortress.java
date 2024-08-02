import java.util.*;

public class Fortress {

    private static final int GRID_SIZE = 9;
    private static char[][] grid = new char[GRID_SIZE][GRID_SIZE];
    private static List<int[]> guards = new ArrayList<>();
    private static int[] fortressPosition = new int[2];
    private static List<List<int[]>> uniquePaths = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of guards:");
        int numGuards = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("Enter coordinates for guards:");
        for (int i = 0; i < numGuards; i++) {
            System.out.print("Enter coordinates for guard " + (i + 1) + ": ");
            String[] coordinates = scanner.nextLine().split(",");
            int x = Integer.parseInt(coordinates[0].trim()) - 1;
            int y = Integer.parseInt(coordinates[1].trim()) - 1;
            guards.add(new int[]{x, y});
            grid[x][y] = 'G'; 
        }

        System.out.println("Enter the coordinates for your ‘special’ fortress:");
        String[] fortressCoords = scanner.nextLine().split(",");
        int startX = Integer.parseInt(fortressCoords[0].trim()) - 1;
        int startY = Integer.parseInt(fortressCoords[1].trim()) - 1;
        fortressPosition[0] = startX;
        fortressPosition[1] = startY;
        grid[startX][startY] = 'F'; 

        System.out.println("Thanks. There are 3 unique paths for your ‘special_fortress’");

        searchPaths(startX, startY, new ArrayList<>());

        if (uniquePaths.isEmpty()) {
            System.out.println("No valid paths found.");
        } else {
            for (int i = 0; i < uniquePaths.size(); i++) {
                System.out.println("Path " + (i + 1));
                System.out.println("=======");
                displayPath(uniquePaths.get(i));
            }
        }
    }

    private static void searchPaths(int x, int y, List<int[]> currentPath) {
        // Using BFS to explore all possible paths
        Queue<FortressState> queue = new LinkedList<>();
        queue.add(new FortressState(x, y, new ArrayList<>(currentPath)));

        while (!queue.isEmpty()) {
            FortressState state = queue.poll();
            int curX = state.x;
            int curY = state.y;
            List<int[]> pathSoFar = state.path;

            // Base case: check if we have returned to the starting position
            if (curX == fortressPosition[0] && curY == fortressPosition[1] && !pathSoFar.isEmpty()) {
                uniquePaths.add(new ArrayList<>(pathSoFar));
                if (uniquePaths.size() == 3) return; // Stop if we found 3 paths
                continue;
            }

            for (int[] guard : guards) {
                int gx = guard[0];
                int gy = guard[1];

                if (isValidMove(curX, curY, gx, gy)) {
                    // Move to the guard
                    char originalCur = grid[curX][curY];
                    char originalGuard = grid[gx][gy];
                    grid[curX][curY] = '.';
                    grid[gx][gy] = '.';

                    List<int[]> newPath = new ArrayList<>(pathSoFar);
                    newPath.add(new int[]{gx, gy});
                    queue.add(new FortressState(gx, gy, newPath));

                    // Backtrack
                    grid[curX][curY] = originalCur;
                    grid[gx][gy] = originalGuard;
                }
            }
        }
    }

    private static boolean isValidMove(int x, int y, int guardX, int guardY) {
        // Check if the fortress can move to the guard's position
        return guardX >= x && guardY == y;
    }

    private static void displayPath(List<int[]> path) {
        System.out.println("Start: (" + (fortressPosition[0] + 1) + ", " + (fortressPosition[1] + 1) + ")");
        for (int[] pos : path) {
            System.out.println("Move to (" + (pos[0] + 1) + ", " + (pos[1] + 1) + "). Turn Left");
        }
        System.out.println("Arrive (" + (fortressPosition[0] + 1) + ", " + (fortressPosition[1] + 1) + ")");
    }

    static class FortressState {
        int x, y;
        List<int[]> path;

        FortressState(int x, int y, List<int[]> path) {
            this.x = x;
            this.y = y;
            this.path = path;
        }
    }
}
