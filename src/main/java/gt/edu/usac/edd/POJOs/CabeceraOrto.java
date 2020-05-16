package gt.edu.usac.edd.POJOs;

public class CabeceraOrto {
	ColumnaOrtogonal primeracolumna;
	FilaOrtogonal primerafila;
	
	public CabeceraOrto() {
		this.primeracolumna=null;
		this.primerafila=null;
	}

	public ColumnaOrtogonal getPrimeracolumna() {
		return primeracolumna;
	}

	public void setPrimeracolumna(ColumnaOrtogonal primeracolumna) {
		this.primeracolumna = primeracolumna;
	}

	public FilaOrtogonal getPrimerafila() {
		return primerafila;
	}

	public void setPrimerafila(FilaOrtogonal primerafila) {
		this.primerafila = primerafila;
	}
	
	
}
