package ie.gmit.sw.ai.spiders;

import ie.gmit.sw.ai.Maze;
import ie.gmit.sw.ai.Node;
import ie.gmit.sw.ai.Player;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class FuzzySpider extends Spider{
	public FuzzySpider(Node start, char type, TraverseAlgorithm algorithm, Maze maze, Player spartan) {
		super(start, type, algorithm, maze, spartan);
	}
	
	public double engage(double health, double anger) {
		FIS fis = FIS.load("./fcl/engage.fcl", true);
		fis.getFunctionBlock("Engage");
		
		//JFuzzyChart.get().chart(fb);
		fis.setVariable("health", spartan.getHealth());
		fis.setVariable("anger", spartan.getAnger());
		fis.evaluate();
		Variable engage = fis.getVariable("engage");
		return engage.getValue();
	}
}
