package main.controllers;

import main.dao.ScepterDAO;
import main.models.Sayre;
import main.models.Scepter;

public class ScepterController {
private ScepterDAO scepterDAO;
public ScepterController() {
	this.scepterDAO=new ScepterDAO();
}
public Scepter createScepter(int lvl) {
	Scepter scepter=new Scepter(lvl);
	scepter=scepterDAO.save(scepter);
	return scepter;
}
public Scepter createScepter(Scepter scepter) {
	return scepterDAO.save(scepter);
}
public Scepter use(Scepter scepter) {
	scepter=scepterDAO.losePower(scepter);
	return scepter;
}
}
