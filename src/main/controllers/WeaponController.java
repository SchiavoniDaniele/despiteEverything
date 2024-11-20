package main.controllers;

import main.dao.WeaponDAO;
import main.models.Weapon;

public class WeaponController {
private WeaponDAO weaponDAO;
public WeaponController() {
	this.weaponDAO=new WeaponDAO();
}
public Weapon createWeapon(int lvl) {
	Weapon weapon=new Weapon(lvl);
	weapon=weaponDAO.save(weapon);
	return weapon;
}
public Weapon createWeapon(Weapon weapon) {
	return weaponDAO.save(weapon);
}
public Weapon use(Weapon weapon) {
	weapon=weaponDAO.use(weapon);
	return weapon;
}
}
