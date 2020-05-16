package gt.edu.usac.edd.POJOs;

import java.io.FileWriter;

//_________________________________ SOLO FALTA AGREGAR LA LISTA DE RUTAS--------------------------
public class TablaHash {
	int tamaño; // CONTADOR ELEMENTOS EN LA TABLA
	int actual;
	Double factorcarga;
	int elementos;
	public static boolean Ready = false;
	ReservacionesHash[] table;

	public TablaHash(int tam) { // INICIALIZAR LA TABLA CON 43 ELEMENTOS
		factorcarga = 0.0;
		elementos = 0;
		tamaño = tam;
		table = new ReservacionesHash[tamaño];
		for (int i = 0; i < tamaño; i++) {
			table[i] = null;
		}
	}

	// ----------------------- METODOS---------------------
	public long equivalente(String id) { // EN DATO CASO EL ID SEA UNA NOMBRE
		String valor = "";
		for (int i = 0; i < id.length(); i++) {
			valor = valor + (int) id.charAt(i);
		}
		long salida = Long.parseLong(valor);
		return salida;
	}

	public int posicion(String id) {
		long p, d;
		d = equivalente(id);
		p = d % tamaño;
		int i = 0;
		long c = p;
		while (table[(int) c] != null && table[(int) c].nombrecliente.equals(id)) {
			i++;
			c = p + i * i;
			c = c % tamaño;
		}
		return (int) c;
	}

	public ReservacionesHash buscar(String nombre) {
		int posicion;
		posicion = posicion(nombre);
		if (table[posicion] != null)
			if (table[posicion].eliminado)
				return null;
		return table[posicion];

	}

	public void eliminar(String nombre) {
		int posicion;
		posicion = posicion(nombre);
		if (table[posicion] != null)
			table[posicion].eliminado = true;
	}

	public void insertar(ReservacionesHash n) {
		Ready = true;
		int posicion;
		posicion = posicion(n.getNoReservacion()); // OBTIENE LA NUEVA LLAVE
		table[posicion] = n;
		elementos = elementos + 1;
		factorcarga = (double) elementos / tamaño;
		if (factorcarga > 0.5) {
			System.out.println("desbordamiento");
		}
	}

	public void rehashing() {
		ReservacionesHash[] temp = table;
		int tamañotemp = tamaño;
		actual = tamaño;
		tamaño = obtencion(tamaño);// CAMBIAR A FUNCION QUE RETORNE PRIMOS
		table = new ReservacionesHash[tamaño];
		for (int i = 0; i < tamañotemp; i++) {
			if (temp[i] != null) {
				ReservacionesHash info = temp[i];
				insertar(info);
			}
		}
	}

	// ----------------- METODO OBTENCIO DEL PRIMO--------------
	public int obtencion(int n) {
		boolean t = false;
		n++;
		while (t != true) {
			if (primo(n) == true) {
				return n;
			}
			n++;
		}
		return n;
	}

	public boolean primo(int n) {
		int a = 0;
		for (int i = 1; i < (n + 1); i++) {
			if (n % i == 0) {
				a++;
			}
		}

		if (a != 2) {
			return false;
		} else {
			return true;
		}
	}

	// ============================== METODO BUSCAR RESERVACIONES DE
	// PERSONA===========================
	public String datauser(String id) {
		System.out.println("cliente :" + id);
		String json = "[";
		boolean dentro = false;
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				System.out.println(table[i].getNombrecliente());
			}
			if (table[i] != null && table[i].getNombrecliente().equals(id)) {
				System.out.println("DENTO HASH");
				if (dentro == false) {
					json = json + "{" + "\"tiempo\":" + "\"" + table[i].getTiempo() + "\"" + "," + "\"costo\":" + "\""
							+ table[i].getCosto() + "\"" + "," + "\"ruta\":" + "\"" + table[i].getRutaElegida() + "\""
							+ "}";
					dentro = true;
				} else {
					json = json + "," + "{" + "\"tiempo\":" + "\"" + table[i].getTiempo() + "\"" + "," + "\"costo\":"
							+ "\"" + table[i].getCosto() + "\"" + "," + "\"ruta\":" + "\"" + table[i].getRutaElegida()
							+ "\"" + "}";
				}
			}
		}
		return json = json + "]";
	}

	// ========================================================================
	public void imprimirhash() {
		for (int i = 0; i < table.length; i++) {
			if (table[i] == null) {
				System.out.println("vacio");
			} else {
				System.out.println(table[i].getCosto());
			}
		}

	}

	public String creargraficaHash() {
		String tempFolder = "C:\\Users\\JavierG\\Pictures\\";
		if (Ready == true) {

			try {

				FileWriter f = new FileWriter(tempFolder + "hash.txt");

				f.write(toDot());// cambiar

				f.close();

			} catch (Exception e) {
				// TODO: Add catch code
				e.printStackTrace();
			}

			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO: Add catch code
				e.printStackTrace();
			}

			doDot(tempFolder + "/hash.txt", tempFolder + "/grafohash.jpg");
			System.out.println("______________> si hay imagen hash");

			return tempFolder + "grafohash.jpg";
		} else {
			System.out.println("______________> no hay imagen hash");
			return "C:\\Users\\JavierG\\Pictures\\noavaiable.png";
		}

	}

	public String toDot() {
		return "digraph G { \n" + "nodesep=.08; \n" + "rankdir=LR; \n"
				+ "node [color=\"blue\",style=\"rounded\", shape=record,width=.1,height=.1]; \n" + cuerpodot() + "\n"
				+ "}";
	}

	public String cuerpodot() {
		String cuerpo = "";
		String separaciones = "";
		String nodo = "";
		String conexiones = "";
		for (int i = 0; i < table.length; i++) {

			if (table[i] != null) {
				separaciones = separaciones + "<f" + i + "> " + table[i].getNombrecliente() + "|";
				String[] rutas = table[i].getRutaElegida().split(",");
				String lista = "";
				for (int h = 0; h < rutas.length; h++) {
					lista += rutas[h] + "| ";
				}
				nodo = nodo + "node" + i + " [label = \"{<n> " + lista + "}\"];" + "\n";
				conexiones = conexiones + "node0:f" + i + " -> node" + i + ";" + "\n";

			}
		}
		cuerpo = cuerpo + "node0 [label = \" " + separaciones + "\",height=4.4,width=1.8];" + "\n";
		cuerpo = cuerpo + "node [width = 1.5];";
		cuerpo = cuerpo + nodo;
		cuerpo = cuerpo + conexiones;
		return cuerpo;
	}

	public void doDot(String pInput, String pOutput) {
		try {

			String dotPath = "dot.exe";

			String fileInputPath = pInput;
			String fileOutputPath = pOutput;

			String tParam = "-Tjpg";
			String tOParam = "-o";

			String[] cmd = new String[5];
			cmd[0] = dotPath;
			cmd[1] = tParam;
			cmd[2] = fileInputPath;
			cmd[3] = tOParam;
			cmd[4] = fileOutputPath;

			Runtime rt = Runtime.getRuntime();

			rt.exec(cmd);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
		}
	}

}
