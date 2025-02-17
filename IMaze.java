public interface IMaze{
    //Returns the maze as an Array of Cells
    public ICell[][] getMaze();
    //Code for the maze generating algorithm 
    public void generateMaze();
    //Returns a specific cell in the maze
    public ICell getCell(int row, int col);
    public ICell getCell(int[] coords);
    //Creates a link between two cells in the maze| Must be two adjacent cells
    public void createLink(ICell from, ICell to) throws IllegalArgumentException;
    //Returns all cells adjacent (intention is NESW)
    public ICell[] getAdjacent(ICell cell);
    //Helper method for the generate maze to ensure all ICells are linked
    //NESW is what I used to keep everything organized
    public boolean allVisited();
    //returns a random ICell 
    public ICell getRandomCell();
    //returns coordinates of the cell passed in {row, col} format
    public int[] getCoords(ICell cell);
    //returns the row of the passed cell
    public int getRow(ICell cell);
    //returns the column of the passed cell
    public int getCol(ICell cell);
}