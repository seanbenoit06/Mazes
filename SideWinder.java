import java.util.*;
public class SideWinder extends Maze {
    public SideWinder(int rows, int cols) {
        super(rows, cols);
        generateMaze();
    }
    public void generateMaze() {
        ICell[][] maze = getMaze();
        Random rand = new Random();
        boolean coinFlip;
        //boolean moreLeft;
        
        
        for(int i = 0; i < maze[0].length - 1; i++) {
            createLink(maze[0][i], maze[0][i+1]);
        }
        
        
        for(int i = 1; i < maze.length; i++) {
            int j = 0;
            int k = 0;
            while(j < maze[i].length) {
                coinFlip = rand.nextBoolean();
                while(coinFlip && j < maze[i].length - 1) {
                    createLink(maze[i][j], maze[i][j+1]);
                    j++;
                    coinFlip = rand.nextBoolean();
                }
                j++;
                int a = rand.nextInt(j-k) + k;
                createLink(maze[i][a], maze[i-1][a]);
                k = j;
            }
            /*if(!maze[i][j-1].canMove(Direction.WEST)) {
                createLink(maze[i][j-1], maze[i-1][j-1]);
            }*/
        }
    }
}