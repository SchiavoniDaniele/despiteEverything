package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseLoader {
	private final static String URL = "jdbc:mysql://localhost:3306";
	private final static String USER = "root";
	private static String PASSWORD = "";
	private final static String DROP_DB="DROP DATABASE de_db";
	private final static String CREATE_DATABASE = "CREATE DATABASE IF NOT EXISTS de_db";
	private final static String CREATE_MONSTERS = "CREATE TABLE IF NOT EXISTS de_db.monsters("
			+ "id INT AUTO_INCREMENT PRIMARY KEY,species VARCHAR(50),intelligence INT,"
			+ "status ENUM('selvaggio','addomesticato','in attesa','morto'))";
	private final static String CREATE_WEAPONS = "CREATE TABLE IF NOT EXISTS de_db.weapons("
			+ "id INT AUTO_INCREMENT PRIMARY KEY,name varchar(50),durability INT,max_durability INT,"
			+ "price INT,rarity INT)";
	private final static String CREATE_BOOKS = "CREATE TABLE IF NOT EXISTS de_db.books(id INT AUTO_INCREMENT PRIMARY KEY,"
			+ "name VARCHAR(50),level INT,conversion_rate INT,price INT)";
	private final static String CREATE_SCEPTERS = "CREATE TABLE IF NOT EXISTS de_db.scepters("
			+ "id INT AUTO_INCREMENT PRIMARY KEY,name VARCHAR(50),magic_power INT, level INT,price INT)";
	private final static String CREATE_REHABS = "CREATE TABLE IF NOT EXISTS de_db.rehabs("
			+ "id INT AUTO_INCREMENT PRIMARY KEY,name VARCHAR(255),max_capacity INT,guests INT,"
			+ "price INT,book_id INT, is_working BOOLEAN,FOREIGN KEY(book_id) REFERENCES de_db.books(id))";
	private final static String CREATE_MONSTER_TO_REHAB = "CREATE TABLE IF NOT EXISTS de_db.monster_to_rehab("
			+ "id INT AUTO_INCREMENT PRIMARY KEY,monster_id INT,rehab_id INT,book_id INT,"
			+ "entry_time TIMESTAMP,exit_time TIMESTAMP,"
			+ "FOREIGN KEY(monster_id) REFERENCES de_db.monsters(id),"
			+ "FOREIGN KEY(rehab_id) REFERENCES de_db.rehabs(id),FOREIGN KEY(book_id) REFERENCES de_db.books(id))";
	private final static String CREATE_SAYRE = "CREATE TABLE IF NOT EXISTS de_db.sayre(id INT AUTO_INCREMENT PRIMARY KEY, game_start TIMESTAMP,"
			+ "happiness INT,coins INT,is_playing BOOLEAN,weapon_id INT,book_id INT,"
			+ "scepter_id INT,FOREIGN KEY(weapon_id) REFERENCES de_db.weapons(id),"
			+ "FOREIGN KEY(book_id) REFERENCES de_db.books(id),"
			+ "FOREIGN KEY(scepter_id) REFERENCES de_db.scepters(id))";

	static void setPassword(String DBpassword) {
		PASSWORD=DBpassword;
	}
	
	static void createDatabase() {
		try {
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(CREATE_DATABASE);
			stmt.executeUpdate(CREATE_MONSTERS);
			stmt.executeUpdate(CREATE_WEAPONS);
			stmt.executeUpdate(CREATE_BOOKS);
			stmt.executeUpdate(CREATE_SCEPTERS);
			stmt.executeUpdate(CREATE_REHABS);
			stmt.executeUpdate(CREATE_MONSTER_TO_REHAB);
			stmt.executeUpdate(CREATE_SAYRE);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection connect() {
		try {
			Connection conn=DriverManager.getConnection(URL,USER,PASSWORD);
			return conn;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static boolean canConnect() {
		try {
			Connection conn=DriverManager.getConnection(URL,USER,PASSWORD);
			System.out.println(PASSWORD);
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println(PASSWORD);
			return false;
		}
	}
	
	public static void resetDb() {
		try {
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(DROP_DB);
			stmt.close();
			conn.close();
			createDatabase();
			loadUnarmed();
			loadDefaultObjects();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadDefaultObjects() {
		try {
			Connection conn=connect();
			String weaponQuery="INSERT INTO de_db.weapons(name,durability,max_durability,price,rarity) VALUES ('Spada rotta',15,15,3,1)";
			Statement stmtWeapon=conn.createStatement();
			stmtWeapon.executeUpdate(weaponQuery);
			String scepterQuery="INSERT INTO de_db.scepters(name,level,magic_power,price) VALUES ('Scettro di legno',1,100,1)";
			Statement stmtScepter=conn.createStatement();
			stmtScepter.executeUpdate(scepterQuery);
			String bookQuery="INSERT INTO de_db.books(name,conversion_rate, level, price) VALUES ('Libro di figurine', 1,1,2)";
			Statement stmtBook=conn.createStatement();
			stmtBook.executeUpdate(bookQuery);
			stmtWeapon.close();
			stmtScepter.close();
			stmtBook.close();
			conn.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static void loadUnarmed() {
		try {
			Connection conn=connect();
			String weaponQuery="INSERT INTO de_db.weapons(name,durability,max_durability,price,rarity) VALUES ('Disarmata',0,0,0,1)";
			Statement stmtWeapon=conn.createStatement();
			stmtWeapon.executeUpdate(weaponQuery);
			String scepterQuery="INSERT INTO de_db.scepters(name,level,magic_power,price) VALUES ('Disarmata',1,0,0)";
			Statement stmtScepter=conn.createStatement();
			stmtScepter.executeUpdate(scepterQuery);
			String bookQuery="INSERT INTO de_db.books(name,conversion_rate, level, price) VALUES ('Libro da colorare',7200,1,0)";
			Statement stmtBook=conn.createStatement();
			stmtBook.executeUpdate(bookQuery);
			stmtWeapon.close();
			stmtScepter.close();
			stmtBook.close();
			conn.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
