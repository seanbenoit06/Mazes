public class Cell implements ICell{
    boolean isVisited;
    private boolean[] adj;
    private ICell[] neighbors;
    boolean isRemoved;
    
    public Cell() {
        adj = new boolean[4];
        /*for(int i = 0; i < adj.length; i++) {
            adj[i] = false;
        }*/
    }
    //Letting the generated know if the cell has been removed as part of a mask
    public void setRemoved() {
        isRemoved = true;
    }
    public boolean getRemoved() {
        return isRemoved;
    }
    //Letting the generated know if a cell had been visited or not
    public void setVisited() {
        isVisited = true;
    }
    public boolean getVisited() {
        return isVisited;
    }
    //changes a direction from a barrier (default) to a non barrier
    public void setOpen(Direction d) {
        if(d == Direction.NORTH) {
            adj[0] = true;
        } else if(d == Direction.EAST) {
            adj[1] = true;
        } else if(d == Direction.SOUTH) {
            adj[2] = true;
        } else if(d == Direction.WEST) {
            adj[3] = true;
        }
    }
    //checking if a direction is a barrier or not
    public boolean canMove(Direction d) {
        if(d == Direction.NORTH) {
            return adj[0];
        } else if(d == Direction.EAST) {
            return adj[1];
        } else if(d == Direction.SOUTH) {
            return adj[2];
        } else if(d == Direction.WEST) {
            return adj[3];
        }
        return false;
    }
    //get an array of booleans indicating if the ordinal directions are open
    //NESW is what is intended
    public boolean[] getOpenArray() {
        return adj;
    }
    //Sets the adjacent cells.  
    //NESW is what is intended
    public void setAdj(ICell[] adj) {
        neighbors = adj;
    }
    public ICell[] getAdj() {
        return neighbors;
    }
}