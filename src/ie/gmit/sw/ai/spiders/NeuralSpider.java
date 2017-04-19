package ie.gmit.sw.ai.spiders;

import ie.gmit.sw.ai.Maze;
import ie.gmit.sw.ai.Node;
import ie.gmit.sw.ai.Player;
import ie.gmit.sw.ai.nn.GameCharacter;

public class NeuralSpider extends Spider{
	public NeuralSpider(Node start, char type, TraverseAlgorithm algorithm, Maze maze, Player spartan) {
		super(start, type, algorithm, maze, spartan);
	}

	public double engage(double health, double anger) {
		GameCharacter gc = new GameCharacter();
		
		if (health <= 33){
			health = 0;
		}else if(health <= 66){
			health = 1;
		}else{
			health = 2;
		}
		
		if (anger <= 33){
			anger = 0;
		}else if(anger <= 66){
			anger = 1;
		}else{
			anger = 2;
		}
		
		return gc.action(health, spartan.getSword(), anger);
	}
}
