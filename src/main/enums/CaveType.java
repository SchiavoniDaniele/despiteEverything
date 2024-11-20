package main.enums;

public enum CaveType {
EASY("Facile", 1, 10,4,10,2),
MEDIUM("Media", 2, 15,8,22,6),
HARD("Difficile",3,20,13,30,10),
EXTREME("Estrema", 4,13,8,35,36);
	
	String difficulty;
	int level;
	int maxDimension;
	int minDimension;
	int maxIntelligence;
	int minIntelligence;
	
	CaveType(String difficulty, int level,int maxDimension,int minDimension,int maxIntelligence,int minIntelligence){
		this.difficulty=difficulty;
		this.level=level;
		this.maxDimension=maxDimension;
		this.minDimension=minDimension;
		this.maxIntelligence=maxIntelligence;
		this.minIntelligence=minIntelligence;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getMaxDimension() {
		return maxDimension;
	}

	public void setMaxDimension(int maxDimension) {
		this.maxDimension = maxDimension;
	}

	public int getMinDimension() {
		return minDimension;
	}

	public void setMinDimension(int minDimension) {
		this.minDimension = minDimension;
	}

	public int getMaxIntelligence() {
		return maxIntelligence;
	}

	public void setMaxIntelligence(int maxIntelligence) {
		this.maxIntelligence = maxIntelligence;
	}

	public int getMinIntelligence() {
		return minIntelligence;
	}

	public void setMinIntelligence(int minIntelligence) {
		this.minIntelligence = minIntelligence;
	}
	
	public static CaveType toEnum(String difficulty) {
		switch(difficulty.toLowerCase()) {
		case "facile": return CaveType.EASY;
		case "medio": return CaveType.MEDIUM;
		case "difficile": return CaveType.HARD;
		case "estremo": return CaveType.EXTREME;
		default: return null;
		}
	}
	
	public static CaveType toEnum(int lvl) {
		switch(lvl) {
		case 1: return CaveType.EASY;
		case 2: return CaveType.MEDIUM;
		case 3: return CaveType.HARD;
		case 4: return CaveType.EXTREME;
		default: return null;
		}
	}
}
