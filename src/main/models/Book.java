package main.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import utils.UtensilUtils;

public class Book {
	private int id;
	private int price;
	private String name;
	private int minutesToConvert;
	private int level;

	public Book(int price, String name, int minutesToConvert, int level) {
		this.price = price;
		this.name = name;
		this.minutesToConvert = minutesToConvert;
		this.level = level;
	}

	public Book(int level) {
		this.price = UtensilUtils.getBookPrice(level);
		this.name = UtensilUtils.getBookName(level);
		switch (level) {
		case 1:
			this.minutesToConvert = 3;
			break;
		case 2:
			this.minutesToConvert = 2;
			break;
		case 3:
			this.minutesToConvert = 1;
			break;
		}
		this.level = level;
	}

	public Book(ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.price = rs.getInt("price");
		this.name = rs.getString("name");
		this.minutesToConvert = rs.getInt("conversion_rate");
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

	public int getMinutesToConvert() {
		return minutesToConvert;
	}

	public void setMinutesToConvert(int minutesToConvert) {
		this.minutesToConvert = minutesToConvert;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	@Override
	public String toString() {
		return "ID: "+id+
				"\nNome: "+name+
				"\nPrezzo: "+price+" monete"+
				"\nTempo necessario per convertire: "+minutesToConvert+" minuti"+
				"\nLivello: "+level;
	}
}