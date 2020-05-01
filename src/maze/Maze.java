package maze;

import static java.awt.Color.black;
import static java.awt.Color.green;
import static java.awt.Color.red;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
    private char[][] solving;
    private int width;
    private int height;
    private int startRow=-1;
    private int startCol=-1;
    private Graphics2D g2d;
    private DrawSolution solution;
    private static final int boxHeight=10;
    private static final int boxWidth=10;
    private static final char path='*';
    private static final char wall=' ';
    
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
	File file = new File("./" +"maze2"+ ".txt");  
	Scanner scanner = new Scanner(file);
	width = scanner.nextInt();
	height = scanner.nextInt();
	maze = new char[height][width];
	solving = new char[height][width];
	scanner.nextLine();
	for(int row=0;row<height;row++){
	    maze[row]=scanner.nextLine().toCharArray();
	}
	resetArray(maze,solving);
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
	solution = new DrawSolution();
	frame.add(draw);
	frame.setResizable(false);
	frame.pack();
	frame.add(solution);
	Listener click=new Listener();
		frame.addMouseListener(click);
	frame.setVisible(true);
	frame.setAlwaysOnTop(true);
	frame.setComponentZOrder(solution,0);
	BufferedImage bi = new BufferedImage((width)*boxWidth, (height)*boxHeight,BufferedImage.TYPE_INT_RGB);
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
	for(int row=0;row<newHeight;row++){
	    for(int col = 0;col<newWidth;col++){
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
	    setPreferredSize(new Dimension((width)*boxWidth, (height)*boxHeight));
	}
	protected void paintComponent(Graphics g){
	    g2d.setColor(black);
	    g.setColor(black);
	    for(int row=0;row<height;row++){
		for(int col=0;col<width;col++){
		    if(maze[row][col] == wall){
		    //if(true){
			g.fillRect(col*boxWidth, row*boxHeight, boxWidth, boxHeight);}
		    else{
			//g.drawString(Character.toString(solving[row][col]), col*boxWidth, row*boxHeight);
			g2d.fillRect(col*boxWidth, row*boxHeight, boxWidth, boxHeight);
		    }
		}
	    }
	}
    }
    // method to request start indices and make initial call to recursive
    // ESCAPE method
    public synchronized void escape(){
	//solving[startC][startR]='a';
        System.out.println(escape(startRow,startCol,'a'));
    }

    // RECURSIVE method to ESCAPE a maze
    public boolean indexExists(int i, int j){
	return i>=0 && j>=0 && i<width && j<height;
    }
    public boolean escape(int row, int col, char marker) {
	solving[row][col]=marker;
	char here = solving[row][col];
	if(isExit(row,col)){
	    solution.solved(here);
	    return true;
	}
	for(int negFlip=1;negFlip>-2;negFlip-=2){
	    if(indexExists(row,col+negFlip) && solving[row][col+negFlip] == path){
		if(escape(row,col+negFlip, (char) (here+1))){
		    return true;   
		}
	    }
	    if(indexExists(row+negFlip,col) && solving[row+negFlip][col] == path){
		if(escape(row+negFlip,col, (char) (here+1))){
		    return true;
		}
	    }
	}
	return false;
    }

    // EXIT if on wall and not a wall
    private boolean isExit(int row, int col) {

	if(!isStart(row,col) && (row==0 || col==0 || row==height-1 || col==width-1)){
	    return true;
	}
        return false;
    }
    class DrawSolution extends JComponent{
	private char c='A';
	DrawSolution(){
	}
	public void solved(char lastChar){
	    c=lastChar;
	    repaint();
	}
	@Override
	protected void paintComponent(Graphics g){
	    g.setColor(green);
	    for(int col = 0;col < width;col++){
		for(int row = 0;row < height;row++){
		    if(solving[row][col]!=wall && solving[row][col]!=path){
			//g.fillRect(0,0,100,100);
			g.fillRect(boxWidth*col,boxHeight*row , boxWidth, boxHeight);
		    }
		}
	    }
	}
    }
    private boolean isStart(int i, int j) {
        
	    return startRow==i && startCol==j;
    }
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
        Maze m = new Maze();
	//m.newMaze();
	m.fillMazeFromFile();
        m.display();
        //m.escape();
	
    }
    public void resetArray(char[][] src,char[][] dest){
	for(int row=0;row<src.length;row++){
	    dest[row]=src[row].clone();
	}
    }
    class Listener implements MouseListener{
	public synchronized void mouseClicked(MouseEvent e) {
	    resetArray(maze,solving);
	    startRow=(int) Math.round((e.getY()-27)/boxHeight);
	    startCol=(int) Math.round((e.getX()-3)/boxWidth);
	    if(maze[startRow][startCol]==path){
		escape();
	    }
	    
	}
	
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
    }
}
