package gt.edu.usac.edd.POJOs;

public class NodoChain {
	int correlativo;
	String nombrebloque;
	String Hash;
	String Has_anterior;
	String id_factura;
	NodoChain siguiente;
	NodoChain anterior;

	public NodoChain(int corre, String nb, String hash, String anterior, String id) {
		this.correlativo = corre;
		this.nombrebloque = nb;
		this.Hash = hash;
		this.Has_anterior = anterior;
		this.id_factura = id;
		this.siguiente = null;
		this.anterior = null;
	}
	
	public NodoChain(int corre, String nb, String hash, String anterior, String id,NodoChain sig,NodoChain ant) {
		this.correlativo = corre;
		this.nombrebloque = nb;
		this.Hash = hash;
		this.Has_anterior = anterior;
		this.id_factura = id;
		this.siguiente = sig;
		this.anterior = ant;
	}

	public int getCorrelativo() {
		return correlativo;
	}

	public void setCorrelativo(int correlativo) {
		this.correlativo = correlativo;
	}

	public String getNombrebloque() {
		return nombrebloque;
	}

	public void setNombrebloque(String nombrebloque) {
		this.nombrebloque = nombrebloque;
	}

	public String getHash() {
		return Hash;
	}

	public void setHash(String hash) {
		Hash = hash;
	}

	public String getHas_anterior() {
		return Has_anterior;
	}

	public void setHas_anterior(String has_anterior) {
		Has_anterior = has_anterior;
	}

	public String getId_factura() {
		return id_factura;
	}

	public void setId_factura(String id_factura) {
		this.id_factura = id_factura;
	}

	public NodoChain getSiguiente() {
		return siguiente;
	}

	public void setSiguiente(NodoChain siguiente) {
		this.siguiente = siguiente;
	}

	public NodoChain getAnterior() {
		return anterior;
	}

	public void setAnterior(NodoChain anterior) {
		this.anterior = anterior;
	}

}
