package main.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import utils.UtensilUtils;

public class Scepter {
	private int id;
	private int price;
	private String name;
	private int power;
	private int level;

	public Scepter(int price, String name, int power, int level) {
		this.price = price;
		this.name = name;
		this.power = power;
		this.level = level;
	}

	public Scepter(int level) {
		this.price = UtensilUtils.getScepterPrice(level);
		this.name = UtensilUtils.getScepterName(level);
		this.power = UtensilUtils.getScepterPower(level);
		this.level = level;
	}

	public Scepter(ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.price = rs.getInt("price");
		this.name = rs.getString("name");
		this.power = rs.getInt("magic_power");
		this.level = rs.getInt("level");
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

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String toString() {
		return "ID: " + id + "\nNome: " + name + "\nPrezzo: " + price + "\nPotere: " + power + "\nLivello: " + level;
	}
}
