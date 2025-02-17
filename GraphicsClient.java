import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
public class GraphicsClient {
    public static final int ROWS = 9;
    public static final int COLS = 33;
    public static final int CELL_SIZE = 15;
    public static void main(String[] args) {
        File f = new File("testMask.txt");
        AldousBroder maze = new AldousBroder(ROWS, COLS, f);
    
        //System.out.println(maze);
        JFrame frame = new JFrame("Mazes!");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MazePanel p = new MazePanel(ROWS, COLS, CELL_SIZE, maze);
        Timer t = new Timer(17, p);
        p.setActor(new ChamberActor(CELL_SIZE, (COLS*CELL_SIZE)/2, (COLS*CELL_SIZE)/2 + (ROWS*CELL_SIZE), maze.getMaze()[0][0], ROWS, COLS));
        
        frame.add(p);
        frame.pack();
       
        frame.setResizable(false);
        frame.setVisible(true);
        t.start();
    }
    
    static class MazePanel extends JPanel implements ActionListener {
        int rows, cols, size;
        Maze maze;
        ChamberActor cha;
        public MazePanel(int rows, int cols, int size, Maze maze) {
            this.rows = rows;
            this.cols = cols;
            this.size = size;
            this.maze = maze;
            setPreferredSize(new Dimension(cols*size, rows*size + (cols*size)));
        }
        public void setActor(ChamberActor cha) {
            this.cha = cha;
            addKeyListener(cha);
            setFocusable(true);
        }
        @Override
        public void actionPerformed(ActionEvent ae) {
            repaint();
        }
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            //paint maze
            ICell[][] m = maze.getMaze();
            g.setColor(Color.BLACK);
            ICell actorCell = cha.getLocation();
            for(int row = 0; row < rows; row++) {
                for(int col = 0; col < cols; col++) {
                    ICell c = m[row][col];
                    if(c != null) {
                        boolean[] paths = c.getOpenArray();
                        g.setColor(Color.BLACK);
                        if(!paths[0]) {
                            //north
                            g.drawLine(col*size, row*size, (col+1)*size, row*size);
                        }
                        if(!paths[2]) {
                            //south
                            g.drawLine(col*size, (row+1)*size, (col+1)*size, (row+1)*size);
                        }
                        if(!paths[3]) {
                            //west
                            g.drawLine(col*size, row*size, col*size, (row+1)*size);
                        }
                        if(!paths[1]) {
                            //east
                            g.drawLine((col+1)*size, row*size, (col+1)*size, (row+1)*size);
                        }
                        if(c == actorCell) {
                            g.setColor(Color.RED);
                            g.fillOval(col*size, row*size, size, size);
                        }
                    } else {
                        g.setColor(Color.BLACK);
                        g.fillRect(col*size, row*size, size, size);
                    }
                }
            }
            
            //draw adventure background
            g.setColor(new Color(0, 102, 153));
            g.fillRect(0, rows*size, cols*size, cols*size);
            //set up walls
            boolean[] adventurePaths = actorCell.getOpenArray();
            g.setColor(Color.BLACK);
            if(!adventurePaths[0]) {
                //north
                g.fillRect(0, rows*size, cols*size, size);
            }
            if(!adventurePaths[2]) {
                //south
                g.fillRect(0, rows*size + cols*size - size, cols*size, size);
            }
            if(!adventurePaths[3]) {
                //west
                g.fillRect(0, rows*size, size, rows*size + cols*size);
            }
            if(!adventurePaths[1]) {
                //east
                g.fillRect(cols*size - size, rows*size, size, rows*size + cols*size);
            }
            //draw adventure avatar
            g.setColor(Color.RED);
            g.fillOval(cha.getX(), cha.getY(), size, size);
            //check directions to see if we enter a new cell
            if(cha.getY() <= rows*size) {
                //north
                if(adventurePaths[0]) {
                    cha.setLocation(actorCell.getAdj()[0]);
                    cha.setY(rows*size + cols*size - size);
                } else {
                    cha.setY(rows*size + size);
                }
            }
            if(cha.getX() >= cols*size) {
                //east
                if(adventurePaths[1]) {
                    cha.setLocation(actorCell.getAdj()[1]);
                    cha.setX(size);
                } else {
                    cha.setX(cols*size - size - size);
                }
            }
            if(cha.getY() >= cols*size + rows*size) {
                //south
                if(adventurePaths[2]) {
                    cha.setLocation(actorCell.getAdj()[2]);
                    cha.setY(rows*size + size);
                } else {
                    cha.setY(rows*size + cols*size - size - size);
                }
            }
            if(cha.getX() <= 0) {
                //west
                if(adventurePaths[3]) {
                    cha.setLocation(actorCell.getAdj()[3]);
                    cha.setX(cols*size - size);
                } else {
                    cha.setX(size);
                }
            }
        }
        
    }
    // might have to disable Actor
    
    
    static class ChamberActor implements KeyListener {
        ICell location;
        int dx;
        int dy;
        int size;
        int rows;
        int cols;
        public ChamberActor(int size, int dx, int dy, ICell loc, int rows, int cols) {
            this.size = size;
            this.dx = dx;
            this.dy = dy;
            location = loc;
            this.rows = rows;
            this.cols = cols;
        }
        
        //mutators
        
        public void setX(int x) {
            dx = x;
        }
        
        public void setY(int y) {
            dy = y;
        }
        
        public void setLocation(ICell loc) {
            location = loc;
        }
        
        //accessors
        
        public ICell getLocation() {
            return location;
        }
        
        public int getX() {
            return dx;
        }
        
        public int getY() {
            return dy;
        }
        
        @Override
        public void keyPressed(KeyEvent ke) {
            if(ke.getKeyCode() == KeyEvent.VK_UP) {
                dy -= size;
            }
            if(ke.getKeyCode() == KeyEvent.VK_DOWN) {
                dy += size;
            }
            if(ke.getKeyCode() == KeyEvent.VK_LEFT) {
                dx -= size;
            }
            if(ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                dx += size;
            }
        }
        public void keyReleased(KeyEvent ke) {
            
        }
        public void keyTyped(KeyEvent ke) {
            
        }
    }
    
    
    /*static class Actor implements KeyListener {
        ICell loc;
        int size;
        public Actor(ICell loc, int size) {
            this.size = size;
            this.loc = loc;
        }
        
        //location mutator
        public void setLocation(ICell loc) {
            this.loc = loc;
        }
        public ICell getLocation() {
            return loc;
        }
        @Override
        public void keyPressed(KeyEvent ke) {
            /*ICell[] neighbors = loc.getAdj();
            boolean[] open = loc.getOpenArray();
            if(ke.getKeyCode() == KeyEvent.VK_UP && open[0]) {
                loc = neighbors[0];
            }
            if(ke.getKeyCode() == KeyEvent.VK_DOWN && open[2]) {
                loc = neighbors[2];
            }
            if(ke.getKeyCode() == KeyEvent.VK_LEFT && open[3]) {
                loc = neighbors[3];
            }
            if(ke.getKeyCode() == KeyEvent.VK_RIGHT && open[1]) {
                loc = neighbors[1];
            }
        }
        public void keyReleased(KeyEvent ke) {
            
        }
        public void keyTyped(KeyEvent ke) {
            
        }
    }*/
}