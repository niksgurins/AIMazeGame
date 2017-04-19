package ie.gmit.sw.ai;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Node {
	public enum Direction {North, South, East, West};
	private Node parent;
	private Color color = Color.BLACK;
	private List<Direction> paths = new ArrayList<Direction>();
	public boolean visited =  false;
	public boolean goal = false;
	private int row = -1;
	private int col = -1;
	private int distance;
	private char type;
	
	public Node(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public Node(int row, int col, char type) {
		this.row = row;
		this.col = col;
		this.type = type;
		
		if(type == '\u0035'){
			goal = true;
		}
	}

	public int getRow() {
		return row;
	}
	
	public void setRow(int row){
		this.row = row;
	}

	public int getCol() {
		return col;
	}
	
	public void setCol(int col){
		this.col = col;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	
	public boolean hasDirection(Direction direction){
		for (int i = 0; i < paths.size(); i++) {
			if (paths.get(i) == direction) return true;
		}
		
		return false;
	}
	
	public Node[] children(Node[][] maze){		
		List<Node> children = new ArrayList<Node>();
		
		if (maze[row][col].hasDirection(Direction.North)) children.add(maze[row - 1][col]); //Add North
		if (maze[row][col].hasDirection(Direction.South)) children.add(maze[row + 1][col]); //Add South
		if (maze[row][col].hasDirection(Direction.West)) children.add(maze[row][col - 1]); //Add West
		if (maze[row][col].hasDirection(Direction.East)) children.add(maze[row][col + 1]); //Add East
		
		return (Node[]) children.toArray(new Node[children.size()]);
	}

	public Node[] adjacentNodes(Node[][] maze){
		java.util.List<Node> adjacents = new java.util.ArrayList<Node>();
		
		if (row > 0) adjacents.add(maze[row - 1][col]); //Add North
		if (row < maze.length - 1) adjacents.add(maze[row + 1][col]); //Add South
		if (col > 0) adjacents.add(maze[row][col - 1]); //Add West
		if (col < maze[row].length - 1) adjacents.add(maze[row][col + 1]); //Add East
		
		return (Node[]) adjacents.toArray(new Node[adjacents.size()]);
	}
	
	public List<Direction> getPaths() {
		return paths;
	}

	public void addPath(Direction direction) {
		paths.add(direction);
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.color = Color.BLUE;
		this.visited = visited;
	}

	public boolean isGoalNode() {
		return goal;
	}

	public void setGoalNode(boolean goal) {
		this.goal = goal;
	}
	
	public int getHeuristic(Node goal){
		double x1 = this.col;
		double y1 = this.row;
		double x2 = goal.getCol();
		double y2 = goal.getRow();
		return (int) Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
	}
	
	public int getPathCost() {
		return distance;
	}

	public void setPathCost(int distance) {
		this.distance = distance;
	}

	public String toString() {
		return "[" + row + "/" + col + "]";
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}
}