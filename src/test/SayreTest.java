package test;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import main.controllers.BookController;
import main.controllers.MonsterController;
import main.controllers.RehabController;
import main.controllers.SayreController;
import main.controllers.ScepterController;
import main.controllers.WeaponController;
import main.dao.RehabDAO;
import main.dao.SayreDAO;
import main.models.Book;
import main.models.Cave;
import main.models.Monster;
import main.models.Rehab;
import main.models.Sayre;
import main.models.Scepter;
import main.models.Weapon;

public class SayreTest {
private static SayreController sayreC=new SayreController();
private static WeaponController weaponC=new WeaponController();
public static BookController bookC=new BookController();
public static ScepterController scepterC=new ScepterController();
public static MonsterController monsterC=new MonsterController();
public static SayreDAO sayreD=new SayreDAO();
public static RehabController rehabC=new RehabController();
public static Rehab rehab;
public static RehabDAO rehabD=new RehabDAO();

public static Sayre sayre;
public static Random rand;
public SayreTest() {
	this.sayre=makeSayre();
	rand=new Random();
}
	public static void main(String[] args) throws InterruptedException {
		new SayreTest();
		changeArsenal();
		getRich();
		//getMonsters();
		//buyArsenal();
		//TimeUnit.MINUTES.sleep(2);
		//System.out.println("Tempo rimasto: "+sayreC.getTimeLeft(sayre));
		//System.out.println("Ha ancora tempo? "+sayreC.hasTimeLeft(sayre));
		//System.out.println("\nStai per perdere!");
		//sayreC.lose(sayre);
		//waah();
		//waah();
		//yeee();
		//unequip();
		//unequipAll();
//		getRich();
//		getRich();
//		buyArsenal();
		getRehab();
		sayreC.donateBook(sayre, rehab);
		rehab=rehabD.findById(rehab.getId());
		System.out.println(sayre);
		System.out.println(rehab);
	//	rehabPrices();
		buyRehab();
		sayre.setBook(bookC.createBook(3));
		getMonsterstoRehab();
		sayre.setScepter(scepterC.createScepter(3));
		System.out.println("Ora Sayre utilizza "+sayre.getScepter().getName());
		System.out.println();
		Cave cave=new Cave(1);
		sayreC.findCave(sayre, cave);
	}
	public static Sayre makeSayre() {
		Sayre sayre=sayreC.createSayre();
		System.out.println("Sayre creata:\n"+sayre);
		return sayre;
	}
	public static void endGame() {
		sayreC.lose(sayreD.findById(1));
		System.out.println("Sayre rimossa");
	}
	public static void changeArsenal() {
		Weapon weapon=weaponC.createWeapon(2);
		Book book=bookC.createBook(1);
		Scepter scepter=scepterC.createScepter(3);
		sayreC.equipBook(sayre, book);
		sayreC.equipScepter(sayre, scepter);
		sayreC.equipWeapon(sayre, weapon);
		System.out.println("\n\nSayre aggiornata:\n"+sayre);
	}
	
	public static void getRich() {
		sayreC.getCoins(sayre, 500);
		System.out.println("Sayre si è arricchita!\n"+sayre);
	}
	public static void getMonsters() {
		Random rand=new Random();
		ArrayList<Monster> monsters=new ArrayList<>();
		for(int i=0;i<40;i++) {
			int random=rand.nextInt(5)+1;
			Monster monster=monsterC.createMonster(random);
			monsters.add(monster);
		}
		for(Monster m:monsters) {
			monsterC.rehabilitateMonster(m);
		}
		
		sayre=sayreD.getPlaying();
		System.out.println("\n\nSayre ha adottato 40 mostri!\n"+sayre);
	}
	public static void getMonsterstoRehab() {
		Random rand=new Random();
		ArrayList<Monster> monsters=new ArrayList<>();
		for(int i=0;i<40;i++) {
			int random=rand.nextInt(5)+1;
			Monster monster=monsterC.createMonster(random);
			monsters.add(monster);
			//System.out.println(monster.getSpecies()+" aggiunto");
		}
		rehabC.rehabMonsters(rehab, monsters);
	}
	public static void buyArsenal() {
		Weapon weapon=weaponC.createWeapon(2);
		Book book=bookC.createBook(1);
		Scepter scepter=scepterC.createScepter(3);
		sayreC.buyBook(sayre, book);
		sayreC.buyScepter(sayre, scepter);
		sayreC.buyWeapon(sayre, weapon);
		int total=weapon.getPrice()+book.getPrice()+scepter.getPrice();
		System.out.println("\n\nSayre ha speso "+total+":\n"+sayre);
	}
	public static void waah() {
		sayreC.getLessHappy(sayre, 9);
		System.out.println("\nSayre è triste!\n"+sayre);
	}
	public static void yeee() {
		sayreC.getHappier(sayre, 5);
		System.out.println("\nOra è di nuovo felice!");
	}
	public static void unequip() {
		sayre=sayreC.unEquipScepter(sayre);
		System.out.println("Sayre è caduta dalle scale e ha perso lo scettro\n"+sayre);
	}
	public static void unequipAll() {
		sayre=sayreC.unEquipBook(sayre);
		sayre=sayreC.unEquipScepter(sayre);
		sayre=sayreC.unequipWeapon(sayre);
		System.out.println("Ora ha perso tuttooooooo\n"+sayre);
	}
	public static void getRehab() {
		rehab=rehabC.createRehab("Il paese della merda");
		System.out.println("Nuovo rehab all'orizzonte!\n"+rehab);
	}
	public static void rehabPrices() {
		for(int i=1;i<10;i++) {
			Rehab newRehab=rehabC.createRehab("Rehab "+1);
			System.out.println("Prezzo nuovo rehab: "+newRehab.getPrice());
		}
	}
	public static void buyRehab() {
		sayreC.buyRehab(sayre, rehab);
		System.out.println("Sayre ha speso "+rehab.getPrice());
		System.out.println("\n\n"+sayre);
	}
	

}
