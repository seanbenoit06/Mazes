import java.util.*;
public class BinaryTree extends Maze {
    public BinaryTree(int rows, int cols) {
        super(rows, cols);
        generateMaze();
    }
    public void generateMaze() {
        ICell[][] maze = getMaze();
        Random rand = new Random();
        boolean coinFlip;
        for(int i = 0; i < maze.length - 1; i++) {
            for(int j = 0; j < maze[i].length - 1; j++) {
                coinFlip = rand.nextBoolean();
                if(coinFlip) {
                    createLink(maze[i][j], maze[i][j+1]);
                } else {
                    createLink(maze[i][j], maze[i+1][j]);
                }
            }
            createLink(maze[i][maze[i].length - 1], maze[i+1][maze[i].length - 1]);
        }
        int r = maze.length - 1;
        for(int i = 0; i < maze[r].length - 1; i++) {
            createLink(maze[r][i], maze[r][i + 1]);
        }
    }
}