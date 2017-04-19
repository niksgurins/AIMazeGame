package ie.gmit.sw.ai.traversers;

import ie.gmit.sw.ai.*;
import java.util.*;

public class BruteForce implements Traversator{
	private boolean dfs = false;
	
	public BruteForce(boolean depthFirst){
		this.dfs = depthFirst;
	}

	public Result traverse(Maze maze, Node node) {
		int visitCount = 0;
		Node[][] nMaze = maze.getMazeAsNodes();
		Node goal = maze.getGoal();
    	
		Deque<Node> queue = new LinkedList<Node>();
		queue.offer(node);
		
		Result r = null;
		char spider = node.getType();
		
		while (!queue.isEmpty()){
			nMaze[goal.getRow()][goal.getCol()].setGoalNode(false);
			goal = maze.getGoal();
			nMaze[goal.getRow()][goal.getCol()].setGoalNode(true);
			// Check what sprite was here before (Who's moving?) and replace it with a space
			maze.set(node.getRow(), node.getCol(), ' ');
			
			node = queue.poll();
			
			// Change the sprite's location in the maze as they traverse it
			maze.set(node.getRow(), node.getCol(), spider);
			
			nMaze[node.getRow()][node.getCol()].setVisited(true);
			
			visitCount++;
			
			Node[] adjacents = node.adjacentNodes(nMaze);
			for (Node n : adjacents){
				if (n.isGoalNode()){
			        System.out.println("Player found");
			        System.out.println("Visited: " + visitCount + " nodes");
			        r = new Result(visitCount, true, node);
			        return r;
				}
			}
			
			try { //Simulate processing each expanded node				
				Thread.sleep(200);						
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Node[] children = node.children(nMaze);
			//System.out.println(node.toString());
			for (int i = 0; i < children.length; i++) {
				if (children[i] != null && !children[i].isVisited()){
					children[i].setParent(node);
					if (dfs){
						queue.addFirst(children[i]);
					}else{
						queue.addLast(children[i]);
					}
				}									
			}
		}
		
		r = new Result(visitCount, false, node);
		return r;
	}
}