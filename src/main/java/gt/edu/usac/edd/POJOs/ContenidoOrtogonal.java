package gt.edu.usac.edd.POJOs;

public class ContenidoOrtogonal {
	int columna;
	int fila;
	String ruta;
	String costo;
	String tiempo;
	ContenidoOrtogonal ptrderecha;
	ContenidoOrtogonal ptrizquierda;
	ContenidoOrtogonal ptrabajo;
	ContenidoOrtogonal ptrarriba;
	
	
	public ContenidoOrtogonal(int col,int f,String r,String c) {
		this.columna=col;
		this.fila=f;
		this.ruta=r;
		this.costo=c;
		this.ptrderecha=null;
		this.ptrabajo=null;
		this.ptrarriba=null;
		this.ptrizquierda=null;
	}
	public int getColumna() {
		return columna;
	}
	public void setColumna(int columna) {
		this.columna = columna;
	}
	public int getFila() {
		return fila;
	}
	public void setFila(int fila) {
		this.fila = fila;
	}
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public String getCosto() {
		return costo;
	}
	public void setCosto(String costo) {
		this.costo = costo;
	}
	public ContenidoOrtogonal getPtrderecha() {
		return ptrderecha;
	}
	public void setPtrderecha(ContenidoOrtogonal ptrderecha) {
		this.ptrderecha = ptrderecha;
	}
	public ContenidoOrtogonal getPtrizquierda() {
		return ptrizquierda;
	}
	public void setPtrizquierda(ContenidoOrtogonal ptrizquierda) {
		this.ptrizquierda = ptrizquierda;
	}
	public ContenidoOrtogonal getPtrabajo() {
		return ptrabajo;
	}
	public void setPtrabajo(ContenidoOrtogonal ptrabajo) {
		this.ptrabajo = ptrabajo;
	}
	public ContenidoOrtogonal getPtrarriba() {
		return ptrarriba;
	}
	public void setPtrarriba(ContenidoOrtogonal ptrarriba) {
		this.ptrarriba = ptrarriba;
	}
	
	
}
