package database;

public class Gradovi {

    int id;
    String ime;
	
    public Gradovi() {
    	
	}
    
	public Gradovi(String ime) {
		this.ime = ime;
	}
	
	public Gradovi(int id, String ime) {
		this.ime = ime;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getIme() {
		return ime;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

}
