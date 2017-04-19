package ie.gmit.sw.ai.traversers;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import ie.gmit.sw.ai.Maze;
import ie.gmit.sw.ai.Node;

public class SteepestAscentHillClimbingTraversator implements Traversator {
private Node goal;
	
	public SteepestAscentHillClimbingTraversator(){
	}

	public Result traverse(Maze maze, Node node) {
		Node[][] nMaze = maze.getMazeAsNodes();
		goal = maze.getGoal();
		
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.addFirst(node);
		
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
			visitCount++;
			
			// Change the sprite's location in the maze as they traverse it
			maze.set(node.getRow(), node.getCol(), spider);
			nMaze[node.getRow()][node.getCol()].setVisited(true);	
			
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
			
			//Sort the children of the current node in order of increasing h(n)
			Node[] children = node.children(nMaze);
			Collections.sort(Arrays.asList(children),(Node current, Node next) -> next.getHeuristic(goal) - current.getHeuristic(goal));
			
			for (int i = 0; i < children.length; i++) {			
				if (children[i] != null && !children[i].isVisited()){
					children[i].setParent(node);
					queue.addFirst(children[i]); //LIFO
				}
			}
		}
		
		r = new Result(visitCount, false, node);
		return r;
	}
}