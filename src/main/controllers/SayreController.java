package main.controllers;

import java.time.Duration;
import java.time.LocalDateTime;

import main.dao.SayreDAO;
import main.models.Book;
import main.models.Cave;
import main.models.Monster;
import main.models.Rehab;
import main.models.Sayre;
import main.models.Scepter;
import main.models.Weapon;
import utils.DataBaseLoader;
import utils.UtensilUtils;

public class SayreController{

	private SayreDAO sayreDAO;

	public SayreController() {
		this.sayreDAO = new SayreDAO();
	}
	public Sayre startGame() {
		Sayre sayre;
		
		sayre=(sayreExists()?sayreDAO.getPlaying():createSayre());
		
		if(!Sayre.isThreadAlive()) {
			Thread sayreThread=new Thread(sayre);
			sayreThread.start();
			Sayre.setThreadAlive(true);
		}
		
		return sayre;
	}
	public Sayre createSayre() {
		DataBaseLoader.resetDb();
		Sayre sayre=new Sayre();
		sayre=UtensilUtils.setDafaultUtensils(sayre);
		sayre=sayreDAO.save(sayre);
		System.out.println("Da controller: sayre id="+sayre.getId());
		sayreDAO.startPlaying(sayre);
		
		return sayre;
	}

	public String getTimeLeft(Sayre sayre) {
		LocalDateTime start = LocalDateTime.now();
		LocalDateTime end = sayre.getEndDate();
		Duration timeLeft = Duration.between(start, end);
		long days = timeLeft.toDays();
		long hours = timeLeft.toHoursPart(); // Ore nella differenza
		long minutes = timeLeft.toMinutesPart(); // Minuti rimanenti
		long seconds = timeLeft.toSecondsPart();
		return days + " giorni, " + hours + " ore, " + minutes + " minuti, " + seconds+" secondi";
	}
	public boolean hasTimeLeft(Sayre sayre) {
		LocalDateTime end = sayre.getEndDate();
		return end.isAfter(LocalDateTime.now());
	}
	public void lose(Sayre sayre) {
		sayreDAO.lose(sayre);
	}
	public boolean hasCoins(Sayre sayre, int price) {
		return sayre.getCoins()>=price;
	}
	
	public Sayre getCoins(Sayre sayre, int coins) {
		int sayreCoins=sayre.getCoins()+coins;
		sayre.setCoins(sayreCoins);
		sayre=sayreDAO.update(sayre.getId(), sayre);
		return sayre;
	}
	public Sayre spendCoins(Sayre sayre, int price) {
		int sayreCoins=sayre.getCoins()-price;
		sayre.setCoins(sayreCoins);
		sayre=sayreDAO.update(sayre.getId(), sayre);
		return sayre;
	}
	
	public Sayre getMoneyFromMonsters(Sayre sayre) {
		sayre.getMoneyFromMonsters();
		return sayreDAO.update(sayre.getId(), sayre);
	}
	public Sayre getHappier(Sayre sayre, int happiness) {
		int sayreHappiness=sayre.getHappiness()+happiness;
		sayre.setHappiness(sayreHappiness);
		sayre=sayreDAO.update(sayre.getId(), sayre);
		return sayre;
	}
	public Sayre getLessHappy(Sayre sayre, int happiness) {
		int sayreHappiness=sayre.getHappiness()-happiness;
		sayre.setHappiness(sayreHappiness);
		sayre=sayreDAO.update(sayre.getId(), sayre);
		//if(isDepressed(sayre)) lose(sayre);
		return sayre;
	}
	public boolean isDepressed(Sayre sayre) {
		if(sayre.getHappiness()<0) {
			sayre.setHappiness(0);
		}
		return true;
	}
	
	public Sayre equipWeapon(Sayre sayre, Weapon weapon) {
		sayre.setWeapon(weapon);
		sayre=sayreDAO.update(sayre.getId(), sayre);
		return sayre;
	}
	public Sayre equipBook(Sayre sayre, Book book) {
		sayre.setBook(book);
		sayre=sayreDAO.update(sayre.getId(), sayre);
		return sayre;
	}
	public Sayre equipScepter(Sayre sayre, Scepter scepter) {
		sayre.setScepter(scepter);
		sayre=sayreDAO.update(sayre.getId(), sayre);
		return sayre;
	}
	public Sayre unequipWeapon(Sayre sayre) {
		sayre.setWeapon(null);
		sayre=sayreDAO.update(sayre.getId(), sayre);
		return sayre;
	}
	public Sayre unEquipBook(Sayre sayre) {
		sayre.setBook(null);
		sayre=sayreDAO.update(sayre.getId(), sayre);
		return sayre;
	}
	public Sayre unEquipScepter(Sayre sayre) {
		sayre.setScepter(null);
		sayre=sayreDAO.update(sayre.getId(), sayre);
		return sayre;
	}
	public Sayre buyWeapon(Sayre sayre, Weapon weapon) {
		if(hasCoins(sayre,weapon.getPrice())) {
			equipWeapon(sayre, weapon);
			spendCoins(sayre, weapon.getPrice());
		}
		return sayreDAO.update(sayre.getId(), sayre);

	}
	public Sayre buyBook(Sayre sayre, Book book) {
		if(hasCoins(sayre,book.getPrice())) {
			equipBook(sayre, book);
			spendCoins(sayre, book.getPrice());
		}
		return sayreDAO.update(sayre.getId(), sayre);

	}
	public Sayre buyScepter(Sayre sayre, Scepter scepter ) {
		if(hasCoins(sayre,scepter.getPrice())) {
			equipScepter(sayre, scepter);
			spendCoins(sayre, scepter.getPrice());
		}
		return sayreDAO.update(sayre.getId(), sayre);

	}
	public Sayre donateBook(Sayre sayre, Rehab rehab) {
		RehabController rehabController=new RehabController();
		if(hasBook(sayre)) {
		rehabController.donateBook(rehab,sayre.getBook());
		return unEquipBook(sayre);
		}else {
			System.out.println("Non hai libri!");
			return sayre;
		}
		
	}
	public Sayre buyRehab(Sayre sayre, Rehab rehab) {
		if(hasCoins(sayre,rehab.getPrice())) {
			sayre=spendCoins(sayre, rehab.getPrice());
		}
		sayre=sayreDAO.update(sayre.getId(), sayre);
		return sayre;
	}
	public Sayre fightMonster(Monster monster, Sayre sayre) {
		MonsterController mc=new MonsterController();
		ScepterController sc=new ScepterController();
		int sayrePower=sayre.getScepter().getPower();
		int monsterIntelligence=monster.getIntelligence();
		System.out.println("Potere di Sayre: "+sayrePower);
		System.out.println("Intelligenza del "+monster.getSpecies()+": "+monsterIntelligence);
		if((sayrePower-monsterIntelligence)*sayrePower<50) {
			mc.killMonster(monster);
			sayre=getLessHappy(sayre, 1);
			sayre=useWeapon(sayre);
			System.out.println("Hai ucciso il mostro");
		}else {
			sayre=equipScepter(sayre, sc.use(sayre.getScepter()));
			System.out.println("Hai salvato il mostro");
		}
		sayre=sayreDAO.update(sayre.getId(), sayre);
		return sayreDAO.findById(sayre.getId());
		
	}
	public Sayre useWeapon(Sayre sayre) {
		WeaponController weaponController=new WeaponController();
		Weapon sayreWeapon=sayre.getWeapon();
		sayreWeapon=weaponController.use(sayreWeapon);
		sayre.setWeapon(sayreWeapon);
		sayre=sayreDAO.update(sayre.getId(), sayre);
		return sayre;
	}
	public Sayre findCave(Sayre sayre, Cave cave) {
		for(Monster monster:cave.getMonsters()) {
			sayre=fightMonster(monster, sayre);
		}
		return sayre;
	}
	
	public boolean hasWeapon(Sayre sayre) {
		return sayre.getWeapon()!=null;
	}
	public boolean hasScepter(Sayre sayre) {
		return sayre.getScepter()!=null;
	}
	public boolean hasBook(Sayre sayre) {
		return sayre.getBook()!=null;
	}
	public boolean sayreExists() {
		return sayreDAO.getPlaying()!=null;
	}
public Sayre getPlaying() {
	return sayreDAO.getPlaying();
}
}
