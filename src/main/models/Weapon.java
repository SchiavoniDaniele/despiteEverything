package main.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import utils.UtensilUtils;

public class Weapon {
	private int id;
	private int price;
	private String name;
	private int durability;
	private int maxDurability;
	private int level;

	public Weapon(int price, String name, int maxDurability, int level) {
		this.price = price;
		this.name = name;
		this.maxDurability = maxDurability;
		this.durability = maxDurability;
		this.level = level;
	}

	public Weapon(int level) {
		this.price=UtensilUtils.getWeaponPrice(level);
				//level*rand.nextInt(100-50+1)+50;//da modificare
		this.name=UtensilUtils.getWeaponName(level);
		this.maxDurability=UtensilUtils.getWeaponDurability(level);
		this.durability=maxDurability;
		this.level=level;
		
	}
	public Weapon(ResultSet rs) throws SQLException {
		this.id=rs.getInt("id");
		this.price=rs.getInt("price");
		this.name=rs.getString("name");
		this.durability=rs.getInt("durability");
		this.maxDurability=rs.getInt("max_durability");
		this.level=rs.getInt("rarity");
	}

	public int getId() {
		return this!=null?id:1;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDurability() {
		return durability;
	}

	public void setDurability(int durability) {
		this.durability = durability;
	}

	public int getMaxDurability() {
		return maxDurability;
	}

	public void setMaxDurability(int maxDurability) {
		this.maxDurability = maxDurability;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	public boolean isBreaking() {
		return durability<(maxDurability/3);
	}
	public boolean isBroken() {
		return durability<=0;
	}
	public void use() {
		durability--;
	}
	
	public String toString() {
		return "ID: "+id+
				"\nNome: "+name+
				"\nPrezzo: "+price+
				"\nResistenza: "+durability+"/"+maxDurability+
				"Livello: "+level;
	}
}
