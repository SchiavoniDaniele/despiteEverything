package main.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.controllers.MonsterController;
import main.enums.CaveType;

public class Cave {
	private CaveType type;
	private String name;
	private int dimension;
	private int difficulty;
	private List<Monster> monsters;
	private Random rand = new Random();

	public Cave(int lvl) {
		this.type = CaveType.toEnum(lvl);
		this.dimension = rand.nextInt(type.getMaxDimension() - type.getMinDimension() + 1) + type.getMinDimension();
		this.difficulty = type.getLevel();
		this.monsters = new ArrayList<>();
		populateMonsters();
	}

	private void populateMonsters() {
		// MonsterController mc=new MonsterController();
		for (int i = 0; i < dimension; i++) {
			int lvl = rand.nextInt(difficulty + 1) + 1;
			Monster monster=new Monster(lvl);
			monsters.add(monster);
		}
	}

	public CaveType getType() {
		return type;
	}

	public void setType(CaveType type) {
		this.type = type;
	}

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public List<Monster> getMonsters() {
		return monsters;
	}

	public void setMonsters(List<Monster> monsters) {
		this.monsters = monsters;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMeanIntelligence() {
		int intelligence=0;
		for(Monster m:monsters) {
			intelligence+=m.getIntelligence();
		}
		int meanIntelligence=intelligence/monsters.size();
		return meanIntelligence;
	}
	
	public String toString() {
		return "Nome grotta: " + name + "\nMostri presenti: " + dimension + "\nDifficoltà: " + difficulty + "("
				+ type.getDifficulty() + ")";
	}
}
