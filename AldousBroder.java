import java.util.*;
import java.io.*;
public class AldousBroder extends Maze {
    public AldousBroder(int rows, int cols) {
        super(rows, cols);
        generateMaze();
    }
    public AldousBroder(int rows, int cols, File f) {
        super(rows, cols, f);
        generateMaze();
    }
    public void generateMaze() {
        ICell[][] maze = getMaze();
        Random rand = new Random();
        int compass;
        int randomRow = rand.nextInt(maze.length);
        int randomCol = rand.nextInt(maze[0].length);
        
        ICell current = maze[randomRow][randomCol];
        
        while(!allVisited()) {
            compass = rand.nextInt(4);
            if(current != null) {
                if(current.getAdj()[compass] != null) {
                    ICell compare = current.getAdj()[compass];
                    if(!maze[getRow(compare)][getCol(compare)].getVisited()) {
                        createLink(maze[getRow(current)][getCol(current)], maze[getRow(compare)][getCol(compare)]);
                    }
                    current = compare;
                }
            } else {
                current = maze[rand.nextInt(maze.length)][rand.nextInt(maze[0].length)];
            }
        }
    }
}