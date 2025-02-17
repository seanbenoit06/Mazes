import java.util.*;
public class Wilson extends Maze {
    
    
    public Wilson(int rows, int cols) {
        super(rows, cols);
        generateMaze();
        createHeatMap(0, 0, 0, Direction.WEST);
    }
    public void generateMaze() {
        ICell[][] maze = getMaze();
        ICell[][] branches = new Cell[maze.length][maze[0].length];
        int[][] directions;
        Random rand = new Random();
        ICell start = getRandomCell();
        branches[getCoords(start)[0]][getCoords(start)[1]] = start;
        ICell current;
        int x;
        int y;
        int compass;
        int targetX;
        int targetY;
        while(!isFull(branches)) {
            directions = new int[maze.length][maze[0].length];
            current = getRandomCell();
            x = getCoords(current)[0];
            y = getCoords(current)[1];
            while(!containsBranch(current, branches)) {
                compass = rand.nextInt(4);
                if(current.getAdj()[compass] != null) {
                    directions[getCoords(current)[0]][getCoords(current)[1]] = compass;
                    current = current.getAdj()[compass];
                }
            }
            targetX = getCoords(current)[0];
            targetY = getCoords(current)[1]; 
            
            while(x != targetX || y != targetY) {
                if(directions[x][y] == 0) {
                    createLink(maze[x][y], maze[x-1][y]);
                    branches[x][y] = maze[x][y];
                    x--;
                }
                if(directions[x][y] == 1) {
                    createLink(maze[x][y], maze[x][y+1]);
                    branches[x][y] = maze[x][y];
                    y++;
                }
                if(directions[x][y] == 2) {
                    createLink(maze[x][y], maze[x+1][y]);
                    branches[x][y] = maze[x][y];
                    x++;
                }
                if(directions[x][y] == 3) {
                    createLink(maze[x][y], maze[x][y-1]);
                    branches[x][y] = maze[x][y];
                    y--;
                }
            }
        }
    }
    
    public boolean containsBranch(ICell cell, ICell[][] grid) {
        boolean toReturn = false;
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] == cell) {
                    toReturn = true;
                }
            }
        }
        return toReturn;
    }
    public boolean isFull(ICell[][] grid) {
        boolean toReturn = true;
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] == null) {
                    toReturn = false;
                }
            }
        }
        return toReturn;
    }
}