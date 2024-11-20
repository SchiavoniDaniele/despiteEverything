package main.controllers;

import java.util.ArrayList;

import main.dao.MonsterDAO;
import main.enums.MonsterStatus;
import main.models.Monster;

public class MonsterController {
	private MonsterDAO monsterDAO;
public MonsterController() {
	this.monsterDAO=new MonsterDAO();
}
	
	public Monster createMonster(int lvl) {
		Monster monster = new Monster(lvl);
		monster = monsterDAO.save(monster);
		return monster;
	}

	public Monster createMonster(String species) {
		Monster monster = new Monster(species);
		monster = monsterDAO.save(monster);
		return monster;
	}

	public Monster rehabilitateMonster(Monster monster) {
		monster=monsterDAO.updateStatus(monster.getId(), MonsterStatus.DOMESTICATED);
		return monsterDAO.findById(monster.getId());
	}
	
	public Monster putInLine(Monster monster) {
		monster=monsterDAO.updateStatus(monster.getId(), MonsterStatus.WAITING);
		return monsterDAO.findById(monster.getId());
	}
	
	public Monster killMonster(Monster monster) {
		monster=monsterDAO.updateStatus(monster.getId(), MonsterStatus.DEAD);
		return monsterDAO.findById(monster.getId());
	}
	public Monster findById(int id) {
		return monsterDAO.findById(id);
	}
	public ArrayList<Monster> getMonstersByStatus(MonsterStatus status){
		return monsterDAO.getMonstersByStatus(status);
	}
	public Monster update(int id, Monster monster) {
		return monsterDAO.update(id,  monster);
	}
	
	
}
