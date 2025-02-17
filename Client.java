import java.io.*;
public class Client {
    public static void main(String[] args) {
        /*System.out.println("Binary Tree");
        BinaryTree m = new BinaryTree(4, 5);
        System.out.print(m);
        
        
        System.out.println();
        System.out.println();
        System.out.println();
        
        System.out.println("Sidewinder");
        SideWinder s = new SideWinder(4, 5);
        System.out.print(s);
        
        System.out.println();
        System.out.println();
        System.out.println();
        
        System.out.println("Aldous Broder");
        AldousBroder ab = new AldousBroder(4,5);
        System.out.print(ab);
        
        System.out.println();
        System.out.println();
        System.out.println();
        
        System.out.println("Wilson");
        Wilson w = new Wilson(4,5);
        System.out.print(w);
        
        System.out.println();
        System.out.println();
        System.out.println();
        
        System.out.println("Heatmap");
        System.out.print(w.heatString());
        
        System.out.println();
        System.out.println();
        System.out.println();*/
        
        System.out.println("Mask");
        File f = new File("testMask.txt");
        AldousBroder mab = new AldousBroder(5, 7, f);
        System.out.println(mab);
        
    }
}