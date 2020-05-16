package gt.edu.usac.edd.POJOs;

public class ReservacionesHash {
	String costo;
	String tiempo;
	String NoReservacion;
	String nombrecliente;
	String RutaElegida;
	public boolean eliminado=false;
	
	public ReservacionesHash() {
		
	}
	public ReservacionesHash(String c,String t,String r,String nom,String rut) {
		this.costo=c;
		this.tiempo=t;
		this.NoReservacion=r;
		this.nombrecliente=nom;
		this.RutaElegida=rut;
	}
	public String getCosto() {
		return costo;
	}
	public void setCosto(String costo) {
		this.costo = costo;
	}
	public String getTiempo() {
		return tiempo;
	}
	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}
	public String getNoReservacion() {
		return NoReservacion;
	}
	public void setNoReservacion(String noReservacion) {
		NoReservacion = noReservacion;
	}
	public String getNombrecliente() {
		return nombrecliente;
	}
	public void setNombrecliente(String nombrecliente) {
		this.nombrecliente = nombrecliente;
	}
	public String getRutaElegida() {
		return RutaElegida;
	}
	public void setRutaElegida(String rutaElegida) {
		RutaElegida = rutaElegida;
	}
	
	
	
	
	

}
