package main.controllers;

import java.util.ArrayList;

import main.dao.RehabDAO;
import main.models.Book;
import main.models.Monster;
import main.models.Rehab;

public class RehabController {
	private RehabDAO rehabDAO;

	public RehabController() {
		this.rehabDAO = new RehabDAO();
	}

	public Rehab createRehab(String name) {
		Rehab rehab = new Rehab(name);
		rehab = rehabDAO.save(rehab);
		return rehab;
	}

	public Rehab donateBook(Rehab rehab, Book book) {
		rehab.setUsedBook(book);
		return rehabDAO.setBook(rehab, book);
	}

	public void rehabMonsters(Rehab rehab, ArrayList<Monster> monsters) {
		rehab.setMonsters(monsters);
		rehab=rehabDAO.setMonsters(monsters, rehab);
		Thread rehabThread = new Thread(rehab);
		rehabThread.start();

	}
	public Rehab findById(int id) {
		return rehabDAO.findById(id);
	}
	public ArrayList<Rehab> getAllRehabs(){
		return rehabDAO.getAllRehabs();
	}
}
