package ie.gmit.sw.ai.traversers;

import ie.gmit.sw.ai.Node;

public class Result {
	private int visitCount = 0;
	private boolean foundPlayer = false;
	private Node finish = null;
	
	public Node getFinish() {
		return finish;
	}

	public void setFinish(Node finish) {
		this.finish = finish;
	}

	public Result(int count, boolean found, Node finish){
		this.visitCount = count;
		this.foundPlayer = found;
		this.finish = finish;
	}

	public int getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}

	public boolean isFoundPlayer() {
		return foundPlayer;
	}

	public void setFoundPlayer(boolean foundPlayer) {
		this.foundPlayer = foundPlayer;
	}
}
