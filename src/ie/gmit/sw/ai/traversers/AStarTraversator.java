package ie.gmit.sw.ai.traversers;

import java.util.*;

import ie.gmit.sw.ai.Maze;
import ie.gmit.sw.ai.Node;

public class AStarTraversator implements Traversator{
	private Node goal;
	
	public AStarTraversator(){
		
	}
	
	public Result traverse(Maze maze, Node node) {
		int visitCount = 0;
		
		Node[][] nMaze = maze.getMazeAsNodes();
		goal = maze.getGoal();
		
		PriorityQueue<Node> open = new PriorityQueue<Node>(20, (Node current, Node next)-> (current.getPathCost() + current.getHeuristic(goal)) - (next.getPathCost() + next.getHeuristic(goal)));
		java.util.List<Node> closed = new ArrayList<Node>();
    	   	
		open.offer(node);
		node.setPathCost(0);		
		
		Result r = null;
		char spider = node.getType();
		
		while(!open.isEmpty()){
			nMaze[goal.getRow()][goal.getCol()].setGoalNode(false);
			goal = maze.getGoal();
			nMaze[goal.getRow()][goal.getCol()].setGoalNode(true);
			// Check what sprite was here before (Who's moving?) and replace it with a space
			maze.set(node.getRow(), node.getCol(), ' ');
			
			node = open.poll();	
			
			// Change the sprite's location in the maze as they traverse it
			maze.set(node.getRow(), node.getCol(), spider);
			nMaze[node.getRow()][node.getCol()].setVisited(true);
			
			closed.add(node);	
			visitCount++;
			
			Node[] adjacents = node.adjacentNodes(nMaze);
			for (Node n : adjacents){
				if (n.isGoalNode()){
			        System.out.println("Player found");
			        System.out.println("Visited: " + visitCount + " nodes");
			        // maze.set(r.getRow(), r.getCol(), ' ');
			        // Check whether to engage
			        r = new Result(visitCount, true, node);
			        return r;
				}
			}
			
			try { //Simulate processing each expanded node
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//Process adjacent nodes
			Node[] children = node.children(nMaze);
			for (int i = 0; i < children.length; i++) {
				Node child = children[i];
				int score = node.getPathCost() + 1 + child.getHeuristic(maze.getGoal());
				int existing = child.getPathCost() + child.getHeuristic(maze.getGoal());
				
				if ((open.contains(child) || closed.contains(child)) && existing < score){
					continue;
				}else{
					open.remove(child);
					closed.remove(child);
					child.setParent(node);
					child.setPathCost(node.getPathCost() + 1);
					open.add(child);
				}
			}									
		}
		
		r = new Result(visitCount, false, node);
		return r;
	}
}
