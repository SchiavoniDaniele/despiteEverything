package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import main.controllers.BookController;
import main.controllers.MonsterController;
import main.controllers.RehabController;
import main.controllers.SayreController;
import main.controllers.ScepterController;
import main.controllers.WeaponController;
import main.dao.MonsterDAO;
import main.dao.SayreDAO;
import main.enums.MonsterStatus;
import main.models.Book;
import main.models.Cave;
import main.models.Monster;
import main.models.Rehab;
import main.models.Sayre;
import main.models.Scepter;
import main.models.Weapon;

public class Main {

	public static void main(String[] args){
		InitialWindow frame = new InitialWindow();
		frame.setVisible(true);
		
		

	}
	public static void startGame() throws IOException {
		int door = 8080;
		HttpServer server = HttpServer.create(new InetSocketAddress(door), 0);
		server.createContext("/", new SayreHouseHandler());
		server.createContext("/img", new ImgLoader());
		server.createContext("/city", new CityLoader());
		server.createContext("/rehabs", new RehabHandler());
		server.createContext("/caves", new CaveHandler());
		server.createContext("/shop", new ShopHandler());

		server.start();

		System.out.println("Server avviato sulla porta " + door);
	}

	static class SayreHouseHandler implements HttpHandler, Runnable {
		private Sayre sayre;
		private SayreController sayreC;
		private BookController bookC;
		private MonsterController monsterC;
		private RehabController rehabC;
		private ScepterController scepterC;
		private WeaponController weaponC;
		private String sayreHTML;
		private Document doc;

		public SayreHouseHandler() throws IOException {
			this.sayreC = new SayreController();
			this.bookC = new BookController();
			this.monsterC = new MonsterController();
			this.rehabC = new RehabController();
			this.scepterC = new ScepterController();
			this.weaponC = new WeaponController();
			this.sayre = sayreC.startGame();
			this.sayreHTML = new String(Files.readAllBytes(Paths.get("src/resources/sayreHouse.html")));
			Thread sayreThread = new Thread(this);
			sayreThread.start();
			
		}

		@Override
		public void run() {
			System.out.println("Sto runnando il thread SayreHouse!");
				while(true) {
					doc = Jsoup.parse(sayreHTML);
					Element coinSpan = doc.getElementById("coins");
					Element happinessSpan = doc.getElementById("happiness");
					Element timeLeftSpan = doc.getElementById("time-left");
					Element savedMonster = doc.getElementById("saved-monsters");
					coinSpan.text(String.valueOf(sayre.getCoins()));

					happinessSpan.text(String.valueOf(sayre.getHappiness()));
					timeLeftSpan.text(sayreC.getTimeLeft(sayre));
					savedMonster.text(String.valueOf(sayre.getMonsters().size()));
					
					//updatedSayreHTML=doc.html();
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
		}

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			System.out.println(exchange.getRequestMethod());
			if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
				SayreDAO sayreD=new SayreDAO();
				sayre=sayreD.getPlaying();
				Weapon weapon = sayre.getWeapon();
				Scepter scepter = sayre.getScepter();
				Book book = sayre.getBook();
				// Bisogna sistemare il problema del tempo che scorre correttamente
//				DateTimeFormatter format=DateTimeFormatter.ofPattern("MMM dd, uuuu h:mm:ss a XXX");
//				String endString=sayre.getEndDate().format(format);
////				System.out.println(endDateString);

				doc.getElementById("weapon-span").text(weapon.getName());
				doc.getElementById("weapon-durability-span")
						.text(weapon.getDurability() + "/" + weapon.getMaxDurability());
				doc.getElementById("scepter-span").text(scepter.getName());
				doc.getElementById("scepter-power-span").text(String.valueOf(scepter.getPower()));
				doc.getElementById("book-span").text(book.getName());
				doc.getElementById("conversion-span").text(String.valueOf(book.getMinutesToConvert()));

				// doc.getElementById("hidden-end-date").text(String.valueOf(sayre.getEndDate()));

				String updatedSayreHTML = doc.html();
				exchange.getResponseHeaders().set("Content-Type", "text/html");
				exchange.sendResponseHeaders(200, updatedSayreHTML.getBytes().length);
				OutputStream os = exchange.getResponseBody();
				os.write(updatedSayreHTML.getBytes());
				os.close();
			}

		}

	}

	public static class CityLoader implements HttpHandler, Runnable {
		private Sayre sayre;
		private SayreController sayreC;
		private BookController bookC;
		private MonsterController monsterC;
		private RehabController rehabC;
		private ScepterController scepterC;
		private WeaponController weaponC;
		private String cityHTML;
		private Document doc;

		public CityLoader() throws IOException {
			this.sayreC = new SayreController();
			this.bookC = new BookController();
			this.monsterC = new MonsterController();
			this.rehabC = new RehabController();
			this.scepterC = new ScepterController();
			this.weaponC = new WeaponController();
			SayreDAO sayreD=new SayreDAO();
			this.sayre = sayreD.getPlaying();
			this.cityHTML = new String(Files.readAllBytes(Paths.get("src/resources/city.html")));
			Thread sayreThread = new Thread(this);
			sayreThread.start();
		}

		@Override
		public void run() {
			System.out.println("Sto runnando il thread city!");
			while (true) {
				doc = Jsoup.parse(cityHTML);
				Element coinSpan = doc.getElementById("coins");
				Element happinessSpan = doc.getElementById("happiness");
				Element timeLeftSpan = doc.getElementById("time-left");
				Element savedMonster = doc.getElementById("saved-monsters");
				coinSpan.text(String.valueOf(sayre.getCoins()));

				happinessSpan.text(String.valueOf(sayre.getHappiness()));
				timeLeftSpan.text(sayreC.getTimeLeft(sayre));
				savedMonster.text(String.valueOf(sayre.getMonsters().size()));
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
				SayreDAO sayreD=new SayreDAO();
				sayre=sayreD.getPlaying();

				doc.getElementById("rehab-price-span").text(String.valueOf(Rehab.getTopPrice()));

				String updatedCityHTML = doc.html();
				exchange.getResponseHeaders().set("Content-Type", "text/html");
				exchange.sendResponseHeaders(200, updatedCityHTML.getBytes().length);
				OutputStream os = exchange.getResponseBody();
				os.write(updatedCityHTML.getBytes());
				os.close();
			} else if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
				sayre=sayreC.getPlaying();
				Element popup = doc.getElementById("pop-up");
				System.out.println("Sono nel post");
				InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
				BufferedReader br = new BufferedReader(isr);
				String formData = br.readLine();
				System.out.println(formData);
				String rehabName = "";
				if (!formData.endsWith("=")) {
					String rehabDirtyName=formData.split("=")[1];
					String[] formDataArray = formData.split("&");
					 rehabDirtyName = formDataArray[0].split("=")[1];
					rehabName = rehabDirtyName.replaceAll("[^a-zA-Z ]+", " ");
				} else {
					rehabName = UtensilUtils.getRehabName();
				}

				if (sayreC.hasCoins(sayre, Rehab.getTopPrice())) {
					Rehab rehab = rehabC.createRehab(rehabName);
					sayre = sayreC.buyRehab(sayre, rehab);
					popup.text("alert('Rehab " + rehabName + " comprato');");
				} else {
					popup.text("alert('Non hai abbastanza soldi!');");
				}
				exchange.getResponseHeaders().set("location", "/city");
				exchange.sendResponseHeaders(302, -1);

			}

		}
	}

	static class RehabHandler implements Runnable, HttpHandler {
		private Sayre sayre;
		private SayreController sayreC;
		private BookController bookC;
		private MonsterController monsterC;
		private RehabController rehabC;
		private ScepterController scepterC;
		private WeaponController weaponC;
		private String rehabHTML;
		private Document doc;

		public RehabHandler() throws IOException {
			this.sayreC = new SayreController();
			this.bookC = new BookController();
			this.monsterC = new MonsterController();
			this.rehabC = new RehabController();
			this.scepterC = new ScepterController();
			this.weaponC = new WeaponController();
			SayreDAO sayreD=new SayreDAO();
			this.sayre = sayreD.getPlaying();
			this.rehabHTML = new String(Files.readAllBytes(Paths.get("src/resources/rehab.html")));
			Thread sayreThread = new Thread(this);
			sayreThread.start();
		}

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
				SayreDAO sayreD=new SayreDAO();
				sayre=sayreD.getPlaying();

				Element rehabsDiv = doc.getElementById("rehabs");
				ArrayList<Rehab> rehabs=rehabC.getAllRehabs();
				for (Rehab rehab : rehabs) {
					String bookName = (rehab.getUsedBook() != null ? rehab.getUsedBook().getName()
							: "Nessun libro utilizzato");
					int convRate = (rehab.getUsedBook() != null ? rehab.getUsedBook().getMinutesToConvert() : 7200);
					Element rehabDiv = new Element("div").addClass("rehab-div");
					Element list = new Element("ul");
					Element name = new Element("li").text("Nome: " + rehab.getName());
					Element dimension = new Element("li").text("Dimensione: " + rehab.getDimension());
					Element usedBook = new Element("li").text("Libro di testo: " + bookName);
					Element time = new Element("li")
							.text("Tempo necessario per riabilitare un mostro: " + convRate + " minuti");
					Element isWorking = new Element("li")
							.text("Attualmente in funzione: " + (rehab.isWorking() ? "Sì" : "No"));
					Element monsterForm = new Element("form").id("monster-form").attr("method", "post").attr("action",
							"/rehabs");
					Element donateBookBtn = new Element("button").addClass("button").attr("type", "submit")
							.attr("name", "action").text("Dona libro").attr("formmethod", "post")
							.attr("value", String.valueOf("b" + rehab.getId()));
					Element rehabMonsterBtn = new Element("button").addClass("button").attr("type", "submit")
							.attr("name", "action").text("Riabilita mostri").attr("formmethod", "post")
							.attr("value", String.valueOf("m" + rehab.getId()));
					if (rehab.isWorking()) {
						donateBookBtn.attr("disabled", "disabled");
						rehabMonsterBtn.attr("disabled", "disabled");
					}
					list.appendChild(name);
					list.appendChild(dimension);
					list.appendChild(usedBook);
					list.appendChild(time);
					list.appendChild(isWorking);
					rehabDiv.appendChild(list);
					monsterForm.appendChild(donateBookBtn);
					monsterForm.appendChild(rehabMonsterBtn);
					rehabDiv.appendChild(monsterForm);
					rehabsDiv.appendChild(rehabDiv);

				}
				if (rehabs.size() <= 0) {
					Element rehabDiv = new Element("div").addClass("rehab-div");
					Element noRehabsP = new Element("p").text("Non hai a disposizione alcun centro di"
							+ " riabilitazione. Torna in città per comprarne uno");
					rehabDiv.appendChild(noRehabsP);
					rehabsDiv.appendChild(rehabDiv);
				}
				String updatedRehabHTML = doc.html();
				exchange.getResponseHeaders().set("Content-Type", "text/html");
				exchange.sendResponseHeaders(200, updatedRehabHTML.getBytes().length);
				OutputStream os = exchange.getResponseBody();

				os.write(updatedRehabHTML.getBytes());
				os.close();
			} else if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
				sayre=sayreC.getPlaying();

				doc.getElementById("rehab-script").text("");
				if (!sayreC.hasBook(sayre)) {
					doc.getElementById("rehab-script").text("alert('Non hai alcun libro da donare!');");
				}
				InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
				BufferedReader br = new BufferedReader(isr);
				String formData = br.readLine();
				System.out.println(formData);
				String[] formDataArray = formData.split("&");
				String value = formDataArray[0].split("=")[1];
				String matrix = value.substring(0, 1);
				int id = Integer.valueOf(value.substring(1));
				System.out.println(matrix);
				System.out.println(id);
				if (matrix.equalsIgnoreCase("b")) {
					donateBooks(id);
				} else {
					donateMonsters(id);
				}
				exchange.getResponseHeaders().set("location", "/rehabs");
				exchange.sendResponseHeaders(302, -1);
			}

		}

		public void donateBooks(int id) {
			Rehab rehab = rehabC.findById(id);
			String bookName = sayre.getBook().getName();
			sayre = sayreC.donateBook(sayre, rehab);
			System.out.println("Hai donato " + bookName + "a " + rehab.getName());
			doc.getElementById("rehab-script").text("alert('Hai donato " + bookName + " a " + rehab.getName() + "');");

		}

		public void donateMonsters(int id) {
			Rehab rehab = rehabC.findById(id);
			System.out.println(rehab);
			ArrayList<Monster> monsters=monsterC.getMonstersByStatus(MonsterStatus.WILD);
			if (monsters.size() <= 0) {
				doc.getElementById("rehab-script").text("alert('Non hai alcun mostro da riabilitare!');");
				return;

			}
			System.out.println("Quantità mostri: "+monsters.size());
			System.out.println("Posti massimi: "+rehab.getDimension());
			ArrayList<Monster> monstersToRehab = new ArrayList<>();
			int maxToProcess = Math.min(monsters.size(), rehab.getDimension());
			for (int i = 0; i < maxToProcess; i++) {
				
				monstersToRehab.add(monsters.get(i));
				System.out.println(monsters.get(i).getSpecies()+" aggiunto all'array "+(i+1));
			}
			System.out.println("Sono uscito dal for");
			rehabC.rehabMonsters(rehab, monstersToRehab);
			System.out.println("Ho eseguito rehabC");
			int monsterLeftOut=monsters.size()-monstersToRehab.size();
			int happiness= maxToProcess*2;
			sayre=sayreC.getHappier(sayre,happiness);
			doc.getElementById("rehab-script").text("alert('" + maxToProcess + " sono stati accolti da "
					+ rehab.getName() + ". " +monsterLeftOut  + " rimangono bisognosi di aiuto. Guadagni "+happiness+" punti felicità');");
		}

		@Override
		public void run() {
			System.out.println("Sto runnando il thread rehab!");
			while(true) {
				this.doc = Jsoup.parse(rehabHTML);
				Element coinSpan = doc.getElementById("coins");
				Element happinessSpan = doc.getElementById("happiness");
				Element timeLeftSpan = doc.getElementById("time-left");
				Element savedMonster = doc.getElementById("saved-monsters");
				coinSpan.text(String.valueOf(sayre.getCoins()));

				happinessSpan.text(String.valueOf(sayre.getHappiness()));
				timeLeftSpan.text(sayreC.getTimeLeft(sayre));
				savedMonster.text(String.valueOf(sayre.getMonsters().size()));
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public static class CaveHandler implements Runnable, HttpHandler {
		private Sayre sayre;
		private SayreController sayreC;
		private BookController bookC;
		private MonsterController monsterC;
		private RehabController rehabC;
		private ScepterController scepterC;
		private WeaponController weaponC;
		private MonsterDAO monsterD;
		private String caveHTML;
		private Document doc;
		private Cave lvl1Cave;
		private Cave lvl2Cave;
		private Cave lvl3Cave;
		private Cave lvl4Cave;
		private int caveId;

		public CaveHandler() throws IOException {

			this.sayreC = new SayreController();
			this.bookC = new BookController();
			this.monsterC = new MonsterController();
			this.rehabC = new RehabController();
			this.scepterC = new ScepterController();
			this.weaponC = new WeaponController();
			SayreDAO sayreD=new SayreDAO();
			this.sayre = sayreD.getPlaying();
			this.monsterD = new MonsterDAO();
			this.caveHTML = new String(Files.readAllBytes(Paths.get("src/resources/caveHub.html")));
			
			Thread sayreThread = new Thread(this);
			sayreThread.start();
		}

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
				SayreDAO sayreD=new SayreDAO();
				sayre=sayreD.getPlaying();
				lvl1Cave = new Cave(1);
				lvl2Cave = new Cave(2);
				lvl3Cave = new Cave(3);
				lvl4Cave = new Cave(4);

				doc.getElementById("cave-1-monsters").text(String.valueOf(lvl1Cave.getMonsters().size()));
				doc.getElementById("cave-2-monsters").text(String.valueOf(lvl2Cave.getMonsters().size()));
				doc.getElementById("cave-3-monsters").text(String.valueOf(lvl3Cave.getMonsters().size()));
				doc.getElementById("cave-4-monsters").text(String.valueOf(lvl4Cave.getMonsters().size()));

				doc.getElementById("cave-1-intelligence").text(String.valueOf(lvl1Cave.getMeanIntelligence()));
				doc.getElementById("cave-2-intelligence").text(String.valueOf(lvl2Cave.getMeanIntelligence()));
				doc.getElementById("cave-3-intelligence").text(String.valueOf(lvl3Cave.getMeanIntelligence()));
				doc.getElementById("cave-4-intelligence").text(String.valueOf(lvl4Cave.getMeanIntelligence()));

				String updatedCaveHTML = doc.html();
				exchange.getResponseHeaders().set("Content-Type", "text/html");
				exchange.sendResponseHeaders(200, updatedCaveHTML.getBytes().length);
				OutputStream os = exchange.getResponseBody();
				os.write(updatedCaveHTML.getBytes());
				os.close();
			} else if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
				sayre=sayreC.getPlaying();

				InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
				BufferedReader br = new BufferedReader(isr);
				caveId = Integer.valueOf(br.readLine().split("=")[1]);
				Cave chosenCave;
				switch (caveId) {
				case 1:
					chosenCave = lvl1Cave;
					break;
				case 2:
					chosenCave = lvl2Cave;
					break;
				case 3:
					chosenCave = lvl3Cave;
					break;
				default:
					chosenCave = lvl4Cave;
					break;
				}
				// System.out.println(chosenCave);
				ArrayList<Monster> monsters = new ArrayList<>();
				for (Monster monster : chosenCave.getMonsters()) {
					monster = monsterD.save(monster);
					sayre = sayreC.fightMonster(monster, sayre);
					monster = monsterC.findById(monster.getId());
					System.out.println(monster.getStatusToString());
					monsters.add(monster);

				}
				chosenCave.setMonsters(monsters);
				int killedMonsters = 0;
				int savedMonsters = 0;
				for (Monster monster : chosenCave.getMonsters()) {
					if (monster.getStatus() == MonsterStatus.DEAD) {
						killedMonsters++;
					} else if (monster.getStatus() == MonsterStatus.WILD) {
						savedMonsters++;
					}
				}
				doc.getElementById("fight-end")
						.text("alert('Hai ucciso " + killedMonsters + " mostri, perdendo " + killedMonsters
								+ " punti felicità, ma ne hai salvati " + savedMonsters
								+ ", che ora attendono il tuo aiuto per "
								+ "essere riabilitati. Vai, continua la tua missione')");
				exchange.getResponseHeaders().set("location", "/caves");
				exchange.sendResponseHeaders(302, -1);
			}

		}

		@Override
		public void run() {
			System.out.println("Sto runnando il thread caves!");
			while (true) {
				doc = Jsoup.parse(caveHTML);
				Element coinSpan = doc.getElementById("coins");
				Element happinessSpan = doc.getElementById("happiness");
				Element timeLeftSpan = doc.getElementById("time-left");
				Element savedMonster = doc.getElementById("saved-monsters");
				coinSpan.text(String.valueOf(sayre.getCoins()));

				happinessSpan.text(String.valueOf(sayre.getHappiness()));
				timeLeftSpan.text(sayreC.getTimeLeft(sayre));
				savedMonster.text(String.valueOf(sayre.getMonsters().size()));
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public static class ShopHandler implements HttpHandler, Runnable {
		private Sayre sayre;
		private SayreController sayreC;
		private BookController bookC;
		private MonsterController monsterC;
		private RehabController rehabC;
		private ScepterController scepterC;
		private WeaponController weaponC;
		private String shopHTML;
		private Document doc;
		private Weapon weapon1;
		private Weapon weapon2;
		private Weapon weapon3;
		private Scepter scepter1;
		private Scepter scepter2;
		private Scepter scepter3;
		private Book book1;
		private Book book2;
		private Book book3;
		private Element shopScript;

		public ShopHandler() throws IOException {
			this.sayreC = new SayreController();
			this.bookC = new BookController();
			this.monsterC = new MonsterController();
			this.rehabC = new RehabController();
			this.scepterC = new ScepterController();
			this.weaponC = new WeaponController();
			SayreDAO sayreD=new SayreDAO();
			this.sayre = sayreD.getPlaying();

			this.shopHTML = new String(Files.readAllBytes(Paths.get("src/resources/shop.html")));


			

			Thread sayreThread = new Thread(this);
			sayreThread.start();
		}

		@Override
		public void run() {
			System.out.println("Sto runnando il thread shop!");
			while (true) {
				this.doc = Jsoup.parse(shopHTML);
				Element coinSpan = doc.getElementById("coins");
				Element happinessSpan = doc.getElementById("happiness");
				Element timeLeftSpan = doc.getElementById("time-left");
				Element savedMonster = doc.getElementById("saved-monsters");
				coinSpan.text(String.valueOf(sayre.getCoins()));

				happinessSpan.text(String.valueOf(sayre.getHappiness()));
				timeLeftSpan.text(sayreC.getTimeLeft(sayre));
				savedMonster.text(String.valueOf(sayre.getMonsters().size()));
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
				SayreDAO sayreD=new SayreDAO();
				sayre=sayreD.getPlaying();

				this.weapon1 = new Weapon(1);
				this.scepter1 = new Scepter(1);
				this.book1 = new Book(1);

				this.weapon2 = new Weapon(2);
				this.scepter2 = new Scepter(2);
				this.book2 = new Book(2);

				this.weapon3 = new Weapon(3);
				this.scepter3 = new Scepter(3);
				this.book3 = new Book(3);

				shopScript = doc.getElementById("shop-script");

				doc.getElementById("weapon-one-name").text(weapon1.getName());
				doc.getElementById("weapon-one-durability").text(String.valueOf(weapon1.getDurability()));
				doc.getElementById("weapon-one-price").text(String.valueOf(weapon1.getPrice()));

				doc.getElementById("weapon-two-name").text(weapon2.getName());
				doc.getElementById("weapon-two-durability").text(String.valueOf(weapon2.getDurability()));
				doc.getElementById("weapon-two-price").text(String.valueOf(weapon2.getPrice()));

				doc.getElementById("weapon-three-name").text(weapon3.getName());
				doc.getElementById("weapon-three-durability").text(String.valueOf(weapon3.getDurability()));
				doc.getElementById("weapon-three-price").text(String.valueOf(weapon3.getPrice()));

				doc.getElementById("book-one-name").text(book1.getName());
				doc.getElementById("book-one-conversion").text(String.valueOf(book1.getMinutesToConvert()));
				doc.getElementById("book-one-price").text(String.valueOf(book1.getPrice()));

				doc.getElementById("book-two-name").text(book2.getName());
				doc.getElementById("book-two-conversion").text(String.valueOf(book2.getMinutesToConvert()));
				doc.getElementById("book-two-price").text(String.valueOf(book2.getPrice()));

				doc.getElementById("book-three-name").text(book3.getName());
				doc.getElementById("book-three-conversion").text(String.valueOf(book3.getMinutesToConvert()));
				doc.getElementById("book-three-price").text(String.valueOf(book3.getPrice()));

				doc.getElementById("scepter-one-name").text(scepter1.getName());
				doc.getElementById("scepter-one-conversion").text(String.valueOf(scepter1.getPower()));
				doc.getElementById("scepter-one-price").text(String.valueOf(scepter1.getPrice()));

				doc.getElementById("scepter-two-name").text(scepter2.getName());
				doc.getElementById("scepter-two-conversion").text(String.valueOf(scepter2.getPower()));
				doc.getElementById("scepter-two-price").text(String.valueOf(scepter2.getPrice()));

				doc.getElementById("scepter-three-name").text(scepter3.getName());
				doc.getElementById("scepter-three-conversion").text(String.valueOf(scepter3.getPower()));
				doc.getElementById("scepter-three-price").text(String.valueOf(scepter3.getPrice()));

				String updatedShopHTML = doc.html();
				exchange.getResponseHeaders().set("Content-Type", "text/html");
				exchange.sendResponseHeaders(200, updatedShopHTML.getBytes().length);
				OutputStream os = exchange.getResponseBody();
				os.write(updatedShopHTML.getBytes());
				os.close();
			} else {
				sayre=sayreC.getPlaying();
				InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
				BufferedReader br = new BufferedReader(isr);
				String formData = br.readLine();
				System.out.println(formData);
				String value = formData.split("=")[1];
				String matrix = value.substring(0, 1);
				int id = Integer.valueOf(value.substring(1));
				System.out.println(matrix);
				System.out.println(id);
				switch (matrix) {
				case "w":
					Weapon chosenWeapon;
					switch (id) {
					case 1:
						chosenWeapon = weapon1;
						break;
					case 2:
						chosenWeapon = weapon2;
						break;
					default:
						chosenWeapon = weapon3;
						break;
					}
					if (!sayreC.hasCoins(sayre, chosenWeapon.getPrice())) {
						shopScript.text("alert('Non puoi permetterti " + chosenWeapon.getName() + "!')");
						return;
					}
					System.out.println("Arma comprata");
					chosenWeapon = weaponC.createWeapon(chosenWeapon);
					sayre = sayreC.buyWeapon(sayre, chosenWeapon);
					shopScript.text("alert('Hai comprato " + chosenWeapon.getName() + "')");
					break;
				case "b":
					Book chosenBook;
					switch (id) {
					case 1:
						chosenBook = book1;
						break;
					case 2:
						chosenBook = book2;
						break;
					default:
						chosenBook = book3;
						break;
					}
					if (!sayreC.hasCoins(sayre, chosenBook.getPrice())) {
						shopScript.text("alert('Non puoi permetterti " + chosenBook.getName() + "!')");
						return;
					}
					chosenBook = bookC.createBook(chosenBook);
					sayre = sayreC.buyBook(sayre, chosenBook);
					shopScript.text("alert('Hai comprato " + chosenBook.getName() + "')");
					break;
				case "s":
					Scepter chosenScepter;
					switch (id) {
					case 1:
						chosenScepter = scepter1;
						break;
					case 2:
						chosenScepter = scepter2;
						break;
					default:
						chosenScepter = scepter3;
						break;
					}
					if (!sayreC.hasCoins(sayre, chosenScepter.getPrice())) {
						shopScript.text("alert('Non puoi permetterti " + chosenScepter.getName() + "!')");
						return;
					}
					chosenScepter=scepterC.createScepter(chosenScepter);
					sayre=sayreC.buyScepter(sayre, chosenScepter);
					shopScript.text("alert('Hai comprato " + chosenScepter.getName() + "')");
					break;
				}
				exchange.getResponseHeaders().set("location", "/shop");
				exchange.sendResponseHeaders(302, -1);
			}

		}

		void buyWeapon(int id) {
			

		}

		void buyBook(int id) {
			
		}

		void buyScepter(int id) {
			

		}
	}
}
