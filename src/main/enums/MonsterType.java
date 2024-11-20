package main.enums;

public enum MonsterType {
	POSSUM(1, "Opossum", 5, 2, 5), GOBLIN(2, "Goblin", 10, 6, 8), GOLEM(3, "Golem", 17, 11, 11),
	CENTAUR(4, "Centauro", 25, 18, 16), PULPITO(5, "Pulpito", 35, 26, 25);

	int level;
	String species;
	int maxIntelligence;
	int minIntelligence;
	int coinsPerMinute;

	MonsterType(int level, String species, int maxIntelligence, int minIntelligence, int coinsPerMinute) {
		this.level = level;
		this.species = species;
		this.maxIntelligence = maxIntelligence;
		this.minIntelligence = minIntelligence;
		this.coinsPerMinute = coinsPerMinute;

	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
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

	public int getCoinsPerMinute() {
		return coinsPerMinute;
	}

	public void setCoinsPerMinute(int coinsPerMinute) {
		this.coinsPerMinute = coinsPerMinute;
	}

	public static MonsterType toEnum(String species) {
		switch (species.toLowerCase()) {
		case "opossum":
			return MonsterType.POSSUM;
		case "goblin":
			return MonsterType.GOBLIN;
		case "golem":
			return MonsterType.GOLEM;
		case "centauro":
			return MonsterType.CENTAUR;
		case "pulpito":
			return MonsterType.PULPITO;
		default:
			return null;
		}

	}

	public static MonsterType toEnum(int level) {
		switch (level) {
		case 1:
			return MonsterType.POSSUM;
		case 2:
			return MonsterType.GOBLIN;
		case 3:
			return MonsterType.GOLEM;
		case 4:
			return MonsterType.CENTAUR;
		case 5:
			return MonsterType.PULPITO;
		default:
			return null;

		}
	}

}
