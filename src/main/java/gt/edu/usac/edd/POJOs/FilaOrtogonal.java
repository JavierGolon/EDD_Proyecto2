package gt.edu.usac.edd.POJOs;

public class FilaOrtogonal {
	int key;
	String info;
	FilaOrtogonal ptrabajo;
	ContenidoOrtogonal prtorto;
	public FilaOrtogonal(int key,String info) {
		this.key=key;
		this.info=info;
		ptrabajo=null;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public FilaOrtogonal getPtrabajo() {
		return ptrabajo;
	}
	public void setPtrabajo(FilaOrtogonal ptrabajo) {
		this.ptrabajo = ptrabajo;
	}
	public ContenidoOrtogonal getPrtorto() {
		return prtorto;
	}
	public void setPrtorto(ContenidoOrtogonal prtorto) {
		this.prtorto = prtorto;
	}
	
	
	
}
