package ie.gmit.sw.ai.traversers;

import java.util.Collections;
import java.util.LinkedList;

import ie.gmit.sw.ai.Maze;
import ie.gmit.sw.ai.Node;

public class BestFirstTraversator implements Traversator {
	private Node goal;
		
	public BestFirstTraversator(){
	}
	
	public Result traverse(Maze maze, Node node) {
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.addFirst(node);
		
		Node[][] nMaze = maze.getMazeAsNodes();
		goal = maze.getGoal();
		
    	int visitCount = 0;
    	
    	Result r = null;
		char spider = node.getType();
    	
		while(!queue.isEmpty()){
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
			
			Node[] children = node.children(nMaze);
			for (int i = 0; i < children.length; i++) {
				if (children[i] != null && !children[i].isVisited()){
					children[i].setParent(node);
					queue.addFirst(children[i]);
				}
			}
			
			//Sort the whole queue. Effectively a priority queue, first in, best out
				Collections.sort(queue,(Node current, Node next) -> current.getHeuristic(goal) - next.getHeuristic(goal));		
		}
		
		r = new Result(visitCount, false, node);
        return r;
	}
}
