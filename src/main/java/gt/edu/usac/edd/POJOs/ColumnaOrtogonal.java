package gt.edu.usac.edd.POJOs;

public class ColumnaOrtogonal {
	int key;
	String info;
	ColumnaOrtogonal ptrderecha;
	ContenidoOrtogonal ptrorto;
	public ColumnaOrtogonal(int k,String inf) {
		this.key=k;
		this.info=inf;
		ptrderecha=null;
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
	public ColumnaOrtogonal getPtrderecha() {
		return ptrderecha;
	}
	public void setPtrderecha(ColumnaOrtogonal ptrderecha) {
		this.ptrderecha = ptrderecha;
	}
	public ContenidoOrtogonal getPtrorto() {
		return ptrorto;
	}
	public void setPtrorto(ContenidoOrtogonal ptrorto) {
		this.ptrorto = ptrorto;
	}
	
	
}
