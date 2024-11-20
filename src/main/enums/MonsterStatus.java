package main.enums;

public enum MonsterStatus {
WILD(1,"Selvaggio"),
DOMESTICATED(2,"Addomesticato"),
WAITING(3,"In attesa"),
DEAD(4,"Morto");
	
	int id;
	String status;
	
	 MonsterStatus(int id, String status) {
		this.id=id;
		this.status=status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	 
	public static MonsterStatus toEnum(int id) {
		switch(id) {
		case 1: return MonsterStatus.WILD;
		case 2: return MonsterStatus.DOMESTICATED;
		case 3: return MonsterStatus.WAITING;
		case 4: return MonsterStatus.DEAD;
		default: return null;
		}
	}
	public static MonsterStatus toEnum(String status) {
		switch(status.toLowerCase()) {
		case "selvaggio": return MonsterStatus.WILD;
		case "addomesticato": return MonsterStatus.DOMESTICATED;
		case "in attesa": return MonsterStatus.WAITING;
		case "morto": return MonsterStatus.DEAD;
		default: return null;
		}
	}
}
