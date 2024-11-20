package main.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import main.controllers.MonsterController;
import main.controllers.SayreController;
import main.dao.BookDAO;
import main.dao.MonsterDAO;
import main.dao.SayreDAO;
import main.dao.ScepterDAO;
import main.dao.WeaponDAO;
import main.enums.MonsterStatus;

public class Sayre implements Runnable {
	private int id;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private int happiness;
	private int coins;
	private Weapon weapon;
	private Scepter scepter;
	private Book book;
	private List<Monster> monsters;
	private boolean isPlaying;
	private static boolean threadAlive;
	private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm");

	public Sayre(LocalDateTime startDate, int happiness, int coins, Weapon weapon, Book book, Scepter scepter,
			List<Monster> monsters) {
		this.startDate = startDate;
		this.endDate = startDate.plusDays(5);
		this.happiness = happiness;
		this.coins = coins;
		this.weapon = weapon;
		this.scepter = scepter;
		this.book = book;
		this.monsters = monsters;
		this.isPlaying=true;
	}

	public Sayre() {

		this.startDate = LocalDateTime.now();
		this.endDate = startDate.plusDays(5);
		this.happiness = 20;// valore a caso da definire
		this.coins = 0;
		this.weapon = null;
		this.book = null;
		this.scepter = null;
		this.monsters = new ArrayList<>();
		this.isPlaying=true;
	}

	public Sayre(ResultSet rs) throws SQLException {
		WeaponDAO weaponDAO = new WeaponDAO();
		ScepterDAO scepterDAO = new ScepterDAO();
		BookDAO bookDAO = new BookDAO();
		MonsterDAO monsterDAO = new MonsterDAO();
		this.id = rs.getInt("id");
		java.sql.Timestamp startDateDb = rs.getTimestamp("game_start");
		this.startDate = startDateDb.toLocalDateTime();
		this.endDate = startDate.plusDays(5);
		this.happiness = rs.getInt("happiness");
		this.coins = rs.getInt("coins");
		this.weapon = weaponDAO.findById(rs.getInt("weapon_id"));
		this.scepter = scepterDAO.findById(rs.getInt("scepter_id"));
		this.book = bookDAO.findById(rs.getInt("book_id"));
		this.monsters = monsterDAO.getMonstersByStatus(MonsterStatus.DOMESTICATED);
		this.isPlaying=rs.getBoolean("is_playing");
		
	}

	public int getMoneyFromMonsters() {
		MonsterController monsterC=new MonsterController();
		for (Monster monster : monsterC.getMonstersByStatus(MonsterStatus.DOMESTICATED)) {
			coins += monster.getCoinsPerMinute();
		}
		return coins;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}

	public int getHappiness() {
		return happiness;
	}

	public void setHappiness(int happiness) {
		this.happiness = happiness;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public Scepter getScepter() {
		return scepter;
	}

	public void setScepter(Scepter scepter) {
		this.scepter = scepter;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public List<Monster> getMonsters() {
		return monsters;
	}

	public void setMonsters(List<Monster> monsters) {
		this.monsters = monsters;
	}

	public static boolean isThreadAlive() {
		return threadAlive;
	}

	public static void setThreadAlive(boolean threadAlive) {
		Sayre.threadAlive = threadAlive;
	}

	@Override
	public String toString() {
		return "Inizio partita: " + startDate.format(format) + "\nFine partita: " + endDate.format(format)
				+ "\nPunti felicità: " + happiness + "/da definire" + "\nMonete: " + coins + "\nArma: "
				+ weapon.getName() + "\nLibro: " + (book!=null?book.getName():"Nessun libro disponibile") + "\nScettro: " + scepter.getName()
				+ "\nMostri riabilitati: " + monsters.size();
	}

	@Override
	public void run() {
		MonsterDAO md=new MonsterDAO();
		SayreController sc=new SayreController();
		SayreDAO sd=new SayreDAO();
		while (true) {
			try {
				TimeUnit.MINUTES.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			monsters=md.getMonstersByStatus(MonsterStatus.DOMESTICATED);
			System.out.println("Notizie dal thread sayre: controllo se ci sono i mostri alle ore " + LocalTime.now());
			if (monsters.size() > 0) {
				getMoneyFromMonsters();
				System.out.println("prima di update:" +this);
				sd.updateMoney(id, coins);
				System.out.println("dopo update: "+this);
				System.out.println("Ci sono!");
			} else {
				System.out.println("Non ci sono :(");
				
			}
		}
	}

}
