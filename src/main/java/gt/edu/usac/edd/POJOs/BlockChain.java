package gt.edu.usac.edd.POJOs;

public class BlockChain {
	private NodoChain inicio, fin;

	public BlockChain() {
		inicio = null;
		fin = null;
	}

// METODO SABER SI ESTA VACIA
	public boolean estaVacia() {
		return inicio == null;
	}

	// METODO PARA AGREGAR AL Final
	public void agregarfinal(int corre, String nb, String hash, String id) {
		if (!estaVacia()) {
			fin = new NodoChain(corre, nb, hash, fin.getHash(), id, null, fin);
			fin.getAnterior().setSiguiente(fin);
		} else {
			inicio = fin = new NodoChain(corre, nb, hash, "null", id);
		}
	}

//==============================================================================================
	public void recorrer(int algundato) {
		NodoChain temp = inicio;
		while (temp != null) {
			if (temp.getCorrelativo() == algundato) {// VALIDACION PARA PODER ENCONTRAR ALGUN DATO EN ESPECIFICO
				break;
			}
			temp = temp.getSiguiente();
		}
	}

//================================= METODO VERIFICAR CAMBIOS========================================================
	public String CheckSecurityByOne(String cods) {
		if (inicio == null) {
			String[] temp = cods.split(",");
			NodoChain block = inicio;
			int i = 0;
			while (temp != null && i < temp.length) {
				if (block.getHash().equals(temp[i])) {
					i++;
					block = block.getSiguiente();
				} else {
					return "Modificada";
				}
			}
			return "Correcta";
		} else {
			return "vacia";
		}
	}

	public String CheckSecurityByList() {

		NodoChain temp = fin;
		while (temp != null) {
			if (temp.getAnterior() == null) {
				return "Correcto";
			} else {
				if (temp.getHas_anterior().equals(temp.getAnterior().getHash())) {
					temp = temp.getAnterior();
				} else {
					return "Modificada";
				}
			}
		}
		return "Correcta";
	}

	public void GenararHash(String info) {

	}
	
	public void imprimirdoble() {
		NodoChain temp = inicio;
		while(temp!=null) {
			System.out.print(temp.getHash()+"----->");
			temp=temp.getSiguiente();
		}
		System.out.println("");
	}
}
