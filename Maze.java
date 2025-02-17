import java.util.*;
import java.io.*;
public abstract class Maze implements IMaze{
    private Cell[][] mazeArray;
    private int row;
    private int col;
    private int[][] heatMap;
    public void createHeatMap(int d, int r, int c, Direction recent) {
        if(r < 0 || c < 0) {
            return;
        }
        heatMap[r][c] = d;
        if(mazeArray[r][c].canMove(Direction.NORTH) && recent != Direction.NORTH) {
            createHeatMap(d+1, r-1, c, Direction.SOUTH);
        }
        if(mazeArray[r][c].canMove(Direction.EAST) && recent != Direction.EAST) {
            createHeatMap(d+1, r, c+1, Direction.WEST);
        }
        if(mazeArray[r][c].canMove(Direction.SOUTH) && recent != Direction.SOUTH) {
            createHeatMap(d+1, r+1, c, Direction.NORTH);
        }
        if(mazeArray[r][c].canMove(Direction.WEST) && recent != Direction.WEST) {
            createHeatMap(d+1, r, c-1, Direction.EAST);
        }
    }
    public int getHeat(int r, int c) {
        return heatMap[r][c];
    }
    public Maze(int row, int col, File f) {
        mazeArray = new Cell[row][col];
        this.row = row;
        this.col = col;
        try {
            Scanner read = new Scanner(f);
            String line;
            int i = 0;
            while(read.hasNextLine()) {
                line = read.nextLine();
                for(int j = 0; j < col; j++) {
                    if(line.charAt(j) != 'x') {
                        mazeArray[i][j] = new Cell();
                    }
                }
                i++;
            }
            
            for(int r = 0; r < row; r++) {
                for(int c = 0; c < col; c++) {
                    if(mazeArray[r][c] != null) {
                        ICell[] adj = new Cell[4];
                        if(r > 0) {
                            if(mazeArray[r-1][c] != null) {
                                adj[0] = mazeArray[r - 1][c]; //north
                            }
                        }
                        if(c < col - 1) {
                            if(mazeArray[r][c+1] != null) {
                                adj[1] = mazeArray[r][c + 1]; //east
                            }
                        }
                        if(r < row - 1) {
                            if(mazeArray[r+1][c] != null) {
                                adj[2] = mazeArray[r + 1][c]; //south
                            }
                        }
                        if(c > 0) {
                            if(mazeArray[r][c-1] != null) {
                                adj[3] = mazeArray[r][c - 1]; //west
                            }
                        }
                        
                        mazeArray[r][c].setAdj(adj);
                    }
                }
            }
        } catch(FileNotFoundException ex) {}
    }
    public Maze() {
        mazeArray = new Cell[0][0];
        row = 0;
        col = 0;
    }
    public Maze(int row, int col) {
        mazeArray = new Cell[row][col];
        this.row = row;
        this.col = col;
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++) {
                mazeArray[i][j] = new Cell();
            }
        }
        for(int r = 0; r < row; r++) {
            for(int c = 0; c < col; c++) {
                ICell[] adj = new Cell[4];
                if(r > 0) {
                    adj[0] = mazeArray[r - 1][c]; //north
                }
                if(c < col - 1) {
                    adj[1] = mazeArray[r][c + 1]; //east
                }
                if(r < row - 1) {
                    adj[2] = mazeArray[r + 1][c]; //south
                }
                if(c > 0) {
                    adj[3] = mazeArray[r][c - 1]; //west
                }
                
                mazeArray[r][c].setAdj(adj);
            }
        }
        heatMap = new int[row][col];
    }
    //all code methods from IMaze go here
    //Returns the maze as an Array of Cells
    public ICell[][] getMaze() {
        return mazeArray;
    }
    //Returns a specific cell in the maze
    public ICell getCell(int row, int col) {
        return mazeArray[row][col];
    }
    
    public ICell getCell(int[] coords) {
        return mazeArray[coords[0]][coords[1]];
    }
    //Creates a link between two cells in the maze| Must be two adjacent cells
    public void createLink(ICell from, ICell to) throws IllegalArgumentException {
        ICell[] fromN = from.getAdj();
        int index = -1;
        for(int i = 0; i < fromN.length; i++) {
            if(fromN[i] == to) {
                index = i;
            }
        }
        if(index == -1) throw new IllegalArgumentException("to is not a neighbor of from");
        Direction d = index == 0 ? Direction.NORTH : index == 1 ? Direction.EAST : index == 2 ? Direction.SOUTH : Direction.WEST;
        from.setOpen(d);
        d = index == 0 ? Direction.SOUTH : index == 1 ? Direction.WEST : index == 2 ? Direction.NORTH : Direction.EAST;
        to.setOpen(d);
        from.setVisited();
        to.setVisited();
    }
    //Returns all cells adjacent (intention is NESW)
    public ICell[] getAdjacent(ICell cell) {
        return cell.getAdj();
    }
    //Helper method for the generate maze to ensure all ICells are linked
    //NESW is what I used to keep everything organized
    public boolean allVisited() {
        for(ICell[] row : mazeArray) {
            for(ICell cell : row) {
                if(cell != null) {
                    if(!cell.getVisited()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    //returns a random ICell 
    public ICell getRandomCell() {
        Random rand = new Random();
        /*int randH = rand.nextInt(height);
        int randW = rand.nextInt(width);*/
        
        int row = rand.nextInt(mazeArray.length);
        int col = rand.nextInt(mazeArray[row].length);
        return mazeArray[row][col];
    }
    //returns coordinates of the cell passed in {row, col} format
    public int[] getCoords(ICell cell) {
        for(int row = 0; row < mazeArray.length; row++) {
            for(int col = 0; col < mazeArray[row].length; col++) {
                if(mazeArray[row][col] == cell) {
                    return new int[] {row, col};
                }
            }
        }
        return null;
    }
    //returns the row of the passed cell
    public int getRow(ICell cell) {
        return getCoords(cell)[0];
    }
    //returns the column of the passed cell
    public int getCol(ICell cell) {
        return getCoords(cell)[1];
    }
    //All Children of Maze must implement the generateMaze() method.      
    public abstract void generateMaze();
    
    public String toString() {
        String build = "+";
        for(int i = 0; i < col; i++) {
            build += "---+";
        }
        build += "\n";
        for(int i = 0; i < row; i++) {
            build += "|";
            for(int j = 0; j < col; j++) {
                if(mazeArray[i][j] != null) {
                    if(mazeArray[i][j].canMove(Direction.EAST)) {
                        build += "    ";
                    } else {
                        build += "   |";
                    }
                } else {
                    build += "xxx|";
                }
            }
            build += "\n";
            build += "+";
            for(int j = 0; j < col; j++) {
                if(mazeArray[i][j] != null) {
                    if(mazeArray[i][j].canMove(Direction.SOUTH)) {
                        build += "   +";
                    } else {
                        build += "---+";
                    }
                } else {
                    build += "---+";
                }
            }
            build += "\n";
        }
        return build;
        /*int returnRow = row * 2 + 1;
        int returnCol = row * 2 + 1;
        String[][] returnArray = new String[returnRow][returnCol];
        for(int i = 0; i < returnRow; i++) {
            for(int j = 0; j < returnCol; j++) {
                if(i%2 == j%2) {
                    if(i%2 == 0) {
                        returnArray[i][j] = "+";
                    } else {
                        returnArray[i][j] = "   ";
                    }
                } else if(i%2 == 0) {
                    returnArray[i][j] = "---";
                } else {
                    returnArray[i][j] = "|";
                }
            }
        }
        
        for(int i = 1; i < returnRow; i += 2) {
            for(int j = 1; j < returnCol; j += 2) {
                if(mazeArray[(i-1)/2][(i-1)/2].canMove(Direction.NORTH)) {
                    returnArray[i-1][j] = "   ";
                } else if(mazeArray[(i-1)/2][(i-1)/2].canMove(Direction.EAST)) {
                    returnArray[i][j+1] = " ";
                } else if(mazeArray[(i-1)/2][(i-1)/2].canMove(Direction.SOUTH)) {
                    returnArray[i+1][j] = "   ";
                } else if(mazeArray[(i-1)/2][(i-1)/2].canMove(Direction.WEST)) {
                    returnArray[i][j-1] = " ";
                }
            }
        }
        
        String build;
        String toReturn = "";
        for(int i = 0; i < returnArray.length; i++) {
            build = "";
            for(int j = 0; j < returnArray[i].length; j++) {
                build += returnArray[i][j];
            }
            toReturn += build;
            toReturn += "\n";
        }
        return toReturn;*/
    }
    
    public String heatString() {
        String build = "+";
        for(int i = 0; i < col; i++) {
            build += "---+";
        }
        build += "\n";
        for(int i = 0; i < row; i++) {
            build += "|";
            for(int j = 0; j < col; j++) {
                if(mazeArray[i][j].canMove(Direction.EAST)) {
                    build += " ";
                    build += heatMap[i][j];
                    build += "  ";
                } else {
                    build += " ";
                    build += heatMap[i][j];
                    build += " |";
                }
            }
            build += "\n";
            build += "+";
            for(int j = 0; j < col; j++) {
                if(mazeArray[i][j].canMove(Direction.SOUTH)) {
                    build += "   +";
                } else {
                    build += "---+";
                }
            }
            build += "\n";
        }
        return build;
    }
}