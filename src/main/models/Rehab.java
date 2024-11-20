package main.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import main.controllers.MonsterController;
import main.dao.BookDAO;
import main.dao.RehabDAO;
import main.enums.MonsterStatus;

public class Rehab implements Runnable {
	private int id;
	private String name;
	private int dimension;
	private List<Monster> monsters;
	private Book usedBook;
	private int price;
	private static int top_price = 10;// da modificare?
	private boolean isWorking;
	private Random rand=new Random();

	public Rehab(String name) {
		this.name = name;
		this.dimension = rand.nextInt(15 - 4) + 5;// tra 5 e 15
		this.monsters = new ArrayList<>();
		this.usedBook = null;
		this.price = top_price;
		top_price*=1.5;
		this.isWorking = false;
	}

	public Rehab(ResultSet rs) throws SQLException {
		RehabDAO rehabDAO=new RehabDAO();
		BookDAO bookDAO=new BookDAO();
		this.id = rs.getInt("id");
		this.name = rs.getString("name");
		this.dimension = rs.getInt("max_capacity");
		this.monsters = rehabDAO.getMonstersFromRehab(id);
		this.usedBook = bookDAO.findById(rs.getInt("book_id"));
		this.price = rs.getInt("price");
		top_price=rehabDAO.getTopPrice();
		this.isWorking = rs.getBoolean("is_working");
	}

	public int getId() {
		return this!=null?id:1;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public List<Monster> getMonsters() {
		return monsters;
	}

	public void setMonsters(List<Monster> monsters) {
		this.monsters = monsters;
	}

	public Book getUsedBook() {
		return usedBook;
	}

	public void setUsedBook(Book usedBook) {
		this.usedBook = usedBook;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	public static int getTopPrice() {
		return top_price;
	}

	public boolean isWorking() {
		return isWorking;
	}

	public void setWorking(boolean isWorking) {
		this.isWorking = isWorking;
	}

	public void cureMonsters() {
	    MonsterController mc = new MonsterController();
	    RehabDAO rehabD=new RehabDAO();
	    rehabD.setWorking(true, this);
	    isWorking = true;
	    
	    System.out.println(monsters.size() + " mostri nell'array");
	    for(Monster m:monsters) {
	    	mc.putInLine(m);
	    }
	    
	    for (Monster m:monsters) {
	        System.out.println("Sto riabilitando!");
	        try {
	            TimeUnit.MINUTES.sleep(usedBook.getMinutesToConvert());
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        System.out.println(m.getSpecies() + " è stato riabilitato alle ore " + LocalTime.now());
	        mc.rehabilitateMonster(m);
	        System.out.println("La struttura sta lavorando? " + isWorking);
	    }
	    monsters.clear();
	    rehabD.setWorking(false, this);
	    isWorking = false;
	    System.out.println("La struttura sta lavorando? " + isWorking);
	}


	@Override
	public void run() {
		System.out.println("Sono nel run");
		cureMonsters();
	}

@Override
public String toString() {
	return "ID: "+id+
"\nNome: "+name+
"\nPosti disponibili: "+dimension+
"\nPosti occupati: "+monsters.size()+
	"\nLibro usato: "+(usedBook!=null?usedBook.getName():"Nessun libro disponibile")+
	"\nPrezzo: "+price+" monete"+
	"\nIn funzione: "+(isWorking?"Sì":"No");
}
}
