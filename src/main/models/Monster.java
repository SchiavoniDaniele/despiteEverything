package main.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import main.enums.MonsterStatus;
import main.enums.MonsterType;

public class Monster {
	private int id;
	private MonsterType monsterType;
	private String species;
	private int intelligence;
	private MonsterStatus status;
	private int coinsPerMinute;
	private Random rand=new Random();

	public Monster(String type) {
		this.monsterType = MonsterType.toEnum(type);
		this.species = monsterType.getSpecies();
		this.intelligence = rand.nextInt(monsterType.getMaxIntelligence() - monsterType.getMinIntelligence() + 1)
				+ monsterType.getMinIntelligence();
		this.status = MonsterStatus.WILD;
		this.coinsPerMinute = monsterType.getCoinsPerMinute();
	}

	public Monster(int level) {
		this.monsterType = MonsterType.toEnum(level);
		this.species = monsterType.getSpecies();
		this.intelligence = rand.nextInt(monsterType.getMaxIntelligence() - monsterType.getMinIntelligence() + 1)
				+ monsterType.getMinIntelligence();
		this.status = MonsterStatus.WILD;
		this.coinsPerMinute = monsterType.getCoinsPerMinute();
	}

	public Monster(String type, String monsterStatus) {
		this.monsterType = MonsterType.toEnum(type);
		this.species = monsterType.getSpecies();
		this.intelligence = rand.nextInt(monsterType.getMaxIntelligence() - monsterType.getMinIntelligence() + 1)
				+ monsterType.getMinIntelligence();
		this.status = MonsterStatus.toEnum(monsterStatus);
		this.coinsPerMinute = monsterType.getCoinsPerMinute();
	}

	public Monster(int level, String monsterStatus) {
		this.monsterType = MonsterType.toEnum(level);
		this.species = monsterType.getSpecies();
		this.intelligence = rand.nextInt(monsterType.getMaxIntelligence() - monsterType.getMinIntelligence() + 1)
				+ monsterType.getMinIntelligence();
		this.status = MonsterStatus.toEnum(monsterStatus);
		this.coinsPerMinute = monsterType.getCoinsPerMinute();
	}

public Monster(ResultSet rs) throws SQLException {
	this.id=rs.getInt("id");
	this.monsterType=MonsterType.toEnum(rs.getString("species"));
	this.species=rs.getString("species");
	this.intelligence=rs.getInt("intelligence");
	this.status=MonsterStatus.toEnum(rs.getString("status"));
	this.coinsPerMinute=monsterType.getCoinsPerMinute();
	
}

	public int getId() {
		return this!=null?id:1;
}

public void setId(int id) {
	this.id = id;
}

	public MonsterType getMonsterType() {
		return monsterType;
	}

	public void setMonsterType(MonsterType monsterType) {
		this.monsterType = monsterType;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}

	public MonsterStatus getStatus() {
		return status;
	}

	public String getStatusToString() {
		return status.getStatus();
	}

	public void setStatus(MonsterStatus status) {
		this.status = status;
	}

	public int getCoinsPerMinute() {
		return coinsPerMinute;
	}

	public void setCoinsPerMinute(int coinsPerMinute) {
		this.coinsPerMinute = coinsPerMinute;
	}

	public Random getRand() {
		return rand;
	}

	public void setRand(Random rand) {
		this.rand = rand;
	}

@Override
public String toString() {
	return "ID: "+id+
			"\nSpecie: "+species+" "+status.getStatus().toLowerCase()+
			"\nIntelligenza: "+intelligence+
			"\nMonete al minuto: "+(status==MonsterStatus.DOMESTICATED?coinsPerMinute:0);
}

}
