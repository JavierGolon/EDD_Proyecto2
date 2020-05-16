package gt.edu.usac.edd.POJOs;

public class NodoAVL {
	int dato,fe;
	String name;
	String password;
	 NodoAVL izq;
	 NodoAVL der;
	public NodoAVL(int d,String n,String p) {
		this.name=n;
		this.password=p;
		this.dato=d;
		this.fe=0;
		this.izq=null;
		this.der=null;
	}
	public int getDato() {
		return dato;
	}
	public void setDato(int dato) {
		this.dato = dato;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
