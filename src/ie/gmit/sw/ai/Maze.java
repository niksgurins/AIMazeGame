package ie.gmit.sw.ai;

import ie.gmit.sw.ai.spiders.*;
import ie.gmit.sw.ai.traversers.Traversator;

public class Maze {
	private char[][] maze;
	private int goalRow;
	private int goalCol;
	
	// Constructor
	public Maze(int dimension){
		maze = new char[dimension][dimension];
		init();
		buildMaze();
		
		int featureNumber = (int)((dimension * dimension) * 0.01);
		addFeature('\u0031', '0', featureNumber); //1 is a sword, 0 is a hedge
		addFeature('\u0032', '0', featureNumber); //2 is help, 0 is a hedge
		addFeature('\u0033', '0', featureNumber); //3 is a bomb, 0 is a hedge
		addFeature('\u0034', '0', featureNumber); //4 is a hydrogen bomb, 0 is a hedge
		
		addFeature('\u0036', '0', featureNumber); //6 is a Black Spider, 0 is a hedge
		addFeature('\u0037', '0', featureNumber); //7 is a Blue Spider, 0 is a hedge
		addFeature('\u0038', '0', featureNumber); //8 is a Brown Spider, 0 is a hedge
		addFeature('\u0039', '0', featureNumber); //9 is a Green Spider, 0 is a hedge
		addFeature('\u003A', '0', featureNumber); //: is a Grey Spider, 0 is a hedge
		addFeature('\u003B', '0', featureNumber); //; is a Orange Spider, 0 is a hedge
		addFeature('\u003C', '0', featureNumber); //< is a Red Spider, 0 is a hedge
		addFeature('\u003D', '0', featureNumber); //= is a Yellow Spider, 0 is a hedge
	}
	
	// Make whole map into a hedge
	private void init(){
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length; col++){
				maze[row][col] = '0'; //Index 0 is a hedge...
			}
		}
	}
	
	// Replace hedges with features
	private void addFeature(char feature, char replace, int number){
		int counter = 0;
		while (counter < feature){
			int row = (int) (maze.length * Math.random());
			int col = (int) (maze[0].length * Math.random());
			
			if (maze[row][col] == replace){
				maze[row][col] = feature;
				counter++;
			}
		}
	}
	
	// Generate spaces for the maze
	private void buildMaze(){ 
		for (int row = 1; row < maze.length - 1; row++){
			for (int col = 1; col < maze[row].length - 1; col++){
				// 50% chance of clearing maze to the right or else clearing it downwards
				int num = (int) (Math.random() * 10);
				if (num > 5 && col + 1 < maze[row].length - 1){
					maze[row][col + 1] = '\u0020'; //\u0020 = 0x20 = 32 (base 10) = SPACE
				}else{
					if (row + 1 < maze.length - 1)maze[row + 1][col] = '\u0020';
				}
			}
		}		
	}
	
	// Return 2d char array representation of the maze
	public char[][] getMaze(){
		return this.maze;
	}
	
	// Turn the character array into a 2d array of nodes and return it
	public Node[][] getMazeAsNodes(){
		Node[][] nodes = new Node[maze.length][maze[0].length];
		
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[0].length; col++){
				Node n = new Node(row, col, this.get(row,col));
				n = getDirections(n);
				nodes[row][col] = n;
			}
		}
		
		return nodes;
	}
	
	// Check around the current node for paths
	protected Node getDirections(Node current){
		int row = current.getRow();
		int col = current.getCol();

		if (row > 0 && validPath(this.get(row - 1, col))){
			current.addPath(Node.Direction.North);
		}
		if (row < maze.length - 1 && validPath(this.get(row + 1, col))){
			current.addPath(Node.Direction.South);
		}
		if (col > 0 && validPath(this.get(row, col - 1))){
			current.addPath(Node.Direction.West);
		}
		if (col < maze.length - 1 && validPath(this.get(row, col + 1))){
			current.addPath(Node.Direction.East);
		}
		
		return current;
	}
	
	// This is used to allow spiders to move through other spiders as well as open spaces
	public boolean validPath(char ch){
		if (ch == ' ' || ch == '\u0036' || ch == '\u0037' || ch == '\u0038' || ch == '\u0039' || ch == '\u003A' || ch == '\u003B' || ch == '\u003C' || ch == '\u003D') return true;
		return false;
	}
	
	// Return a list of all the spiders on the map
	public java.util.List<Spider> getSpiders(Player p){
		java.util.List<Spider> spiders = new java.util.ArrayList<Spider>();
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length; col++){
				Node start = new Node(row,col, maze[row][col]);
				
				switch(maze[row][col]){
					case '\u0036': // Black
						Spider black = new FuzzySpider(start, '\u0036', Traversator.TraverseAlgorithm.AStar, this, p);
						spiders.add(black);
						break;
					case '\u0037': // Blue
						Spider blue = new FuzzySpider(start, '\u0037', Traversator.TraverseAlgorithm.BFS, this, p);
						spiders.add(blue);
						break;
					case '\u0038': // Brown
					    Spider brown = new FuzzySpider(start, '\u0038', Traversator.TraverseAlgorithm.SAHC, this, p);
						spiders.add(brown);
						break;
					case '\u0039': // Green
						Spider green = new FuzzySpider(start, '\u0039', Traversator.TraverseAlgorithm.BestFirst, this, p);
						spiders.add(green);
						break;
					case '\u003A': // Gray
						Spider gray = new NeuralSpider(start, '\u003A', Traversator.TraverseAlgorithm.RW, this, p);
						spiders.add(gray);
						break;
					case '\u003B': // Orange
						Spider orange = new NeuralSpider(start, '\u003B', Traversator.TraverseAlgorithm.DFS, this, p);
						spiders.add(orange);
						break;
					case '\u003C': // Red
						Spider red = new NeuralSpider(start, '\u003C', Traversator.TraverseAlgorithm.AStar, this, p);
						spiders.add(red); 
						break;
					case '\u003D': // Yellow
						Spider yellow = new NeuralSpider(start, '\u003D', Traversator.TraverseAlgorithm.BestFirst, this, p);
						spiders.add(yellow);
						break;
				}
			}
		}
		
		return spiders;
	}
	
	// Update where player node is. This is here to save us time
	public void updateGoal(int row, int col){
		this.goalRow = row;
		this.goalCol = col;
	}
	
	public Node getGoal(){
		return new Node(goalRow, goalCol, '5');
	}
	
	// Get a specific part of the maze
	public char get(int row, int col){
		return this.maze[row][col];
	}
	
	// Set a specific part of the maze
	public void set(int row, int col, char c){
		this.maze[row][col] = c;
	}
	
	public int size(){
		return this.maze.length;
	}
	
	// Print whole maze
	public String toString(){
		StringBuffer sb = new StringBuffer();
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length; col++){
				sb.append(maze[row][col]);
				if (col < maze[row].length - 1) sb.append(",");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}