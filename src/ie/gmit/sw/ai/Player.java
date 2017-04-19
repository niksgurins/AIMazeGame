package ie.gmit.sw.ai;

public class Player{
	private double health = 100;
	private double sword = 0;
	private double anger = 0;
	private int durability = 0;
	private int bomb = 0;
	private int hBomb = 0;

	public Player(){
	}
	
	public double getDurability(){
		return durability;
	}

	public double getAnger() {
		return anger;
	}

	public void setAnger(double anger) {
		this.anger = anger;
	}
	
	public void addAnger(){
		if (this.anger >= 95){
			this.anger = 100;
		}else{
			this.anger += 5;
		}
	}
	
	public double getSword(){
		return sword;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		if (health <= 0){
			System.out.println("Defeat. You were killed in: " + (System.currentTimeMillis() - GameRunner.time) + "ms"); 
			//SoundEffects.DEATH.play();
			System.exit(0);
		}
		this.health = health;
	}

	public int getBomb() {
		return bomb;
	}

	public void setBomb(int bomb) {
		this.bomb = bomb;
	}

	public int gethBomb() {
		return hBomb;
	}

	public void sethBomb(int hBomb) {
		this.hBomb = hBomb;
	}

	public void setSword(double sword) {
		this.sword = sword;
		
		// Picking up a sword
		if(sword == 1){
			durability = 3;
		}
	}
	
	public double fight(){
		// Calculate damage to spider
		double extradamage = this.anger / 10.0;
		extradamage = Math.round(extradamage);
		
		if(sword == 1){
			durability--;
			if(durability == 0){
				// Sword broke
				sword = 0;
			}
			return 10 + extradamage;
		}else{
			return 5 + extradamage;
		}
	}
}
