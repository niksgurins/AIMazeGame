package ie.gmit.sw.ai;

public class Bomb extends Thread{
	private char type;
	private Maze model;
	private int row;
	private int col;
	
	public Bomb(char type, Maze model, int currentRow, int currentCol){
		this.type = type;
		this.model = model;
		this.row = currentRow;
		this.col = currentCol;
	}
	
	public void run() {
		try {
			// Sleep for 1.5 seconds to allow player to get away from explosion
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(type == '\u003E'){
			// Do normal explosion.
			for (int i = row-1; i <= row+1; i++){
				for (int j = col-1; j <= col+1; j ++){
					// Blow up help sprites (turn them to hedges)
					if(model.get(i, j) == '\u0031' || model.get(i, j) == '\u0032' || model.get(i, j) == '\u0033' || model.get(i, j) == '\u0034'){
						model.set(i, j, '0');
					}
					
					if(model.get(i, j) == '\u0035'){
						// Do damage to player
						GameRunner.spartan.setHealth(GameRunner.spartan.getHealth() - 15);
						GameRunner.information.setText("  Health: " + Double.toString(GameRunner.spartan.getHealth()) + "   Durability: " + GameRunner.spartan.getDurability() + "   Anger: " + GameRunner.spartan.getAnger() + "  Bombs:  " + GameRunner.spartan.getBomb()
				    	+ "  H-Bombs  " + GameRunner.spartan.gethBomb());
					}
				}
			}
			
			if(model.getGoal().getRow() == row && model.getGoal().getCol() == col){
				GameRunner.spartan.setHealth(GameRunner.spartan.getHealth() - 15);
				GameRunner.information.setText("  Health: " + Double.toString(GameRunner.spartan.getHealth()) + "   Durability: " + GameRunner.spartan.getDurability() + "   Anger: " + GameRunner.spartan.getAnger() + "  Bombs:  " + GameRunner.spartan.getBomb()
		    	+ "  H-Bombs  " + GameRunner.spartan.gethBomb());
			}
			
			model.set(row, col, ' ');
		}else{
			// Do hydrogen explosion.
			for (int i = row-1; i <= row+1; i++){
				for (int j = col-1; j <= col+1; j ++){
					// "Blow up" help sprites (turn them to hedges)
					if(model.get(i, j) == '\u0031' || model.get(i, j) == '\u0032' || model.get(i, j) == '\u0033' || model.get(i, j) == '\u0034' || model.get(i, j) == '0' ||  model.get(i, j) == '\u003F'){
						model.set(i, j, ' ');
					}
					
					if(model.get(i, j) == '\u0035'){
						// Do damage to player
						GameRunner.spartan.setHealth(GameRunner.spartan.getHealth() - 30);
						GameRunner.information.setText("  Health: " + Double.toString(GameRunner.spartan.getHealth()) + "   Durability: " + GameRunner.spartan.getDurability() + "   Anger: " + GameRunner.spartan.getAnger() + "  Bombs:  " + GameRunner.spartan.getBomb()
				    	+ "  H-Bombs  " + GameRunner.spartan.gethBomb());
					}
				}
			}
			
			if(model.getGoal().getRow() == row && model.getGoal().getCol() == col){
				GameRunner.spartan.setHealth(GameRunner.spartan.getHealth() - 30);
				GameRunner.information.setText("  Health: " + Double.toString(GameRunner.spartan.getHealth()) + "   Durability: " + GameRunner.spartan.getDurability() + "   Anger: " + GameRunner.spartan.getAnger() + "  Bombs:  " + GameRunner.spartan.getBomb()
		    	+ "  H-Bombs  " + GameRunner.spartan.gethBomb());
			}
		}
	}
}
