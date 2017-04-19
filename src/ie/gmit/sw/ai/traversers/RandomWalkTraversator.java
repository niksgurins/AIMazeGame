package ie.gmit.sw.ai.traversers;

import ie.gmit.sw.ai.Maze;
import ie.gmit.sw.ai.Node;

public class RandomWalkTraversator implements Traversator{
	private Node goal;
	
	public RandomWalkTraversator(){}

	public Result traverse(Maze maze, Node node) {
		Node[][] nMaze = maze.getMazeAsNodes();
		goal = maze.getGoal();
		
    	int visitCount = 0;
    	   	
		int steps = (int) Math.pow(nMaze.length, 2) * 2;
		//System.out.println("Number of steps allowed: " + steps);
		
		Result r = null;
		char spider = node.getType();
		
		while(visitCount <= steps && node != null){
			// Update the players whereabouts
			nMaze[goal.getRow()][goal.getCol()].setGoalNode(false);
			goal = maze.getGoal();
			nMaze[goal.getRow()][goal.getCol()].setGoalNode(true);
			
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
			
			//Pick a random adjacent node
			// Check what sprite was here before (Who's moving?) and replace it with a space
			maze.set(node.getRow(), node.getCol(), ' ');
        	Node[] children = node.children(nMaze);
        	node = children[(int)(children.length * Math.random())];	
        	// Change the sprite's location in the maze as they traverse it
        	maze.set(node.getRow(), node.getCol(), spider);
		}
		
		r = new Result(visitCount, false, node);
		return r;
	}
}
