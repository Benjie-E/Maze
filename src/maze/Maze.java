package maze;

import static java.awt.Color.black;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;


public class Maze {

    private char[][] maze;
    private int width;
    private int height;
    private int startR;
    private int startC;
    private Graphics2D g2d;

    // Method to FILL MAZE by reading in from a text file with format SUCH AS:
    // 9 9
    // * *******
    // *     * *
    // * ***** *
    // * * *   *
    // * * *** *
    // *   *   *
    // *** * * *
    // *     * *
    // ******* *
    // 
    public void fillMazeFromFile() throws IOException {
	/*Scanner iDev = new Scanner(System.in);
	System.out.println("What is the file name?");
	String fileName = iDev.nextLine();
	File file = new File("./" +fileName+ ".txt"); */ 
	File file = new File("./" +"maze"+ ".txt");  
	Scanner scanner = new Scanner(file);
	width=scanner.nextInt();
	height=scanner.nextInt();
	maze=new char[height][width];
	scanner.nextLine();
	for(int i=0;i<height;i++){
	    maze[i]=scanner.nextLine().toCharArray();
	    System.out.println(Arrays.toString(maze[i]));
	}
	/*File other = new File("./other.txt");
		other.createNewFile();
	FileWriter wr = new FileWriter(other);
	for(int i=0;i<height;i++){
		wr.write(maze[i]);
		wr.write("\n");
	}
	wr.close();*/
	//wr.flush();
        //System.out.println("STUB method to fill array from input file. Replace with your code.");

        // ASK USER for name of file that holds the maze
        // OPEN the file with the maze
        // Read in DIMENSIONS (width, height) from the file
        // INSTANTIATE the 2D array maze given the dimensions
        // READ in the MAZE configuration from the input file (nested loops)
        // into the the 2D array maze
    }
    
    // Method to DISPLAY the maze
    public void display() throws IOException {
	JFrame frame=new JFrame();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setTitle("Maze");
	DrawMaze draw = new DrawMaze();
	frame.add(draw);
	frame.setResizable(false);
	frame.pack();
	frame.setVisible(true);
	
	frame.setAlwaysOnTop(true);
	BufferedImage bi = new BufferedImage((width)*50, (height)*50,BufferedImage.TYPE_INT_RGB);
	g2d = bi.createGraphics();
	g2d.fillRect(0, 0, bi.getWidth(), bi.getHeight());
	File file = new File("./mymaze.png");
        ImageIO.write(bi, "png", file);
    }

    private void newMaze() throws IOException {
	File other = new File("./newMaze.txt");
	other.createNewFile();
	Random rand = new Random();
	FileWriter wr = new FileWriter(other);
	int newWidth = rand.nextInt(25)+1;
	int newHeight = rand.nextInt(13)+1;
	wr.write(newWidth+" "+newHeight+"\n");
	for(int i=0;i<newHeight;i++){
	    for(int j = 0;j<newWidth;j++){
		if(rand.nextBoolean()){
		   wr.write(("*"));
		}else{
		    wr.write((" "));
		}
		
		
	    }
	    wr.write("\n");
	
	}
	wr.close();
    }
    public class DrawMaze extends JComponent{
	DrawMaze(){
	    setPreferredSize(new Dimension((width)*50, (height)*50));
	}
	protected void paintComponent(Graphics g){
	    g2d.setColor(black);
	    for(int i=0;i<width;i++){
		for(int j=0;j<height;j++){
		    if(maze[j][i] == '*'){
			g.fillRect(i*50, j*50, 50, 50);
			g2d.fillRect(i*50, j*50, 50, 50);
		    }
		}
	    }
	}
    }
    // method to request start indices and make initial call to recursive
    // ESCAPE method
    public void escape() {

        System.out.println("STUB method to request start indicates and call recursive escape method. Replace with your code.");
   
        // Request values for startR and startC from user
        
        
        
        // Initial call to recursive escape
        escape(startR,startC);
    }

    // RECURSIVE method to ESCAPE a maze
    public boolean escape(int i, int j) {
	
        System.out.println("STUB RECURIVE method to escape a maze. Replace with your code.");

        return true;
    }

    // EXIT if on wall and not a '*'
    private boolean isExit(int i, int j) {

        System.out.println("STUB method to determine if EXIT. Replace with your code.");
	if(i == height-1 && maze[height-1][j] == ' '){
	    return true;
	}
        return false;
    }

    private boolean isStart(int i, int j) {
        
        System.out.println("STUB method to test if location is start. Replace with your code.");
	if(i == 0 && maze[0][j] == ' '){
	    startR=i;
	    startC=j;
	    return true;
	}
        return false;
    }
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Maze m = new Maze();
	m.newMaze();
	m.fillMazeFromFile();
        m.display();
        m.escape();
    }
}
