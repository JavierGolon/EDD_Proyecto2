package gt.edu.usac.edd.POJOs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Ortogonal {
	private CabeceraOrto head;
	protected FilaOrtogonal firstF, lastF;
	protected ColumnaOrtogonal firstC, lastC;
	public ArrayList<ArrayList<Integer>> rutas = new ArrayList<ArrayList<Integer>>();
	public String tiempo="";

	public Ortogonal() {
		this.head = new CabeceraOrto();
		this.firstF = null;
		this.lastF = null;
		this.firstC = null;
		this.lastC = null;

	}

//----------------------- COMPROBACION PUNTERO CABEZA NULOS-----------------------------
	public boolean vacia() {
		if (firstF == null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean punteroHeadColumna() {
		if (head.primeracolumna == null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean punteroHeadFila() {
		if (head.primerafila == null) {
			return false;
		} else {
			return true;
		}
	}
//------------------------------ COMPROBACION PUNTEROS FILA Y COLUMNA---------------------------

	public boolean firstColumna(ColumnaOrtogonal m) {
		if (m.ptrorto == null) {
			return false;
		} else {
			return true;
		}
	}

	public boolean firstFila(FilaOrtogonal m) {
		if (m.prtorto == null) {
			return false;
		} else {
			return true;
		}
	}

//========================= en listas paises======================================
	public String getpaises() {
		FilaOrtogonal temp = firstF;
		String paises = "";
		
		while (temp != null) {
			if (temp.getPtrabajo() != null) {
				paises = paises + temp.getInfo()+"+"+temp.getKey()+ ",";
			} else {
				paises = paises + temp.getInfo()+"+"+temp.getKey();
			}
			temp = temp.getPtrabajo();
		}
		return paises;
	}
//--------------------------- BUSCAR FILA---------------------------------------------------------------

	public FilaOrtogonal buscarfila(int fila) {
		FilaOrtogonal temporal = firstF;
		while (temporal != null) {
			if (temporal.getKey() == fila) {
				return temporal;
			}
			temporal = temporal.getPtrabajo();
		}
		return temporal;
	}
//--------------------- BUSCAR COLUMNA-------------------------------------------------------------------------

	public ColumnaOrtogonal buscarcolumna(int col) {

		ColumnaOrtogonal temporal = firstC;
		while (temporal != null) {

			if (temporal.getKey() == col) {

				return temporal;
			}
			temporal = temporal.getPtrderecha();
		}
		return temporal;
	}

	public void insertarfila(int key, String info) {
		FilaOrtogonal nuevo = new FilaOrtogonal(key, info);
		if (firstF == null) {
			System.out.println("primera fila");
			firstF = nuevo;
			head.setPrimerafila(firstF);
		} else {
			if (key < firstF.getKey()) {
				nuevo.setPtrabajo(firstF);
				firstF = nuevo;
			} else {
				FilaOrtogonal reco = firstF;
				FilaOrtogonal atras = firstF;
				while (key >= reco.getKey() && reco.getPtrabajo() != null) {
					atras = reco;
					reco = reco.getPtrabajo();
				}
				if (key >= reco.getKey()) {
					reco.setPtrabajo(nuevo);
				} else {
					nuevo.setPtrabajo(reco);
					atras.setPtrabajo(nuevo);

				}
			}
		}

	}

	public void insertarColumna(int key, String info) {
		ColumnaOrtogonal nuevo = new ColumnaOrtogonal(key, info);
		if (firstC == null) {
			System.out.println("primera columna");
			firstC = nuevo;
			head.setPrimeracolumna(firstC);
		} else {
			System.out.println("mas columnas");
			if (key < firstC.getKey()) {
				nuevo.setPtrderecha(firstC);
				firstC = nuevo;
			} else {
				ColumnaOrtogonal reco = firstC;
				ColumnaOrtogonal atras = firstC;
				while (key >= reco.getKey() && reco.getPtrderecha() != null) {
					atras = reco;
					reco = reco.getPtrderecha();
				}
				if (key >= reco.getKey()) {
					reco.setPtrderecha(nuevo);
				} else {
					nuevo.setPtrderecha(reco);
					atras.setPtrderecha(nuevo);

				}
			}
		}

	}

//=========================================================================================================
	public void insertar(ContenidoOrtogonal in) {
		ContenidoOrtogonal nuevonodo = new ContenidoOrtogonal(in.getColumna(), in.getFila(), in.getRuta(),
				in.getCosto());
		FilaOrtogonal filaplace = buscarfila(nuevonodo.getFila());
		ColumnaOrtogonal columnaplace = buscarcolumna(nuevonodo.getColumna());

//******** CREACION COLUMNA y FILA EN CASO SEA NULA*******************
		if (filaplace == null) {
			insertarfila(nuevonodo.getFila(), nuevonodo.getRuta());
			filaplace = buscarfila(nuevonodo.getFila());
		}

		if (columnaplace == null) {
			System.out.println("dentro info" + nuevonodo.getCosto() + " problemas--->>");
			insertarColumna(nuevonodo.getColumna(), nuevonodo.getRuta());
			columnaplace = buscarcolumna(nuevonodo.getColumna());
		}
//============================== INSERCION DE CONTENIDO POR COLUMNA=================
		if (columnaplace.getPtrorto() == null) {
			columnaplace.setPtrorto(nuevonodo);

			System.out.println("primera vez col" + nuevonodo.getCosto() + "jajajajajajaj");
		} else if (columnaplace.getPtrorto().getFila() >= nuevonodo.getFila()) {
			System.out.println("primera vez col" + nuevonodo.getCosto() + "jajajajajajaj");
			nuevonodo.setPtrabajo(columnaplace.getPtrorto());
			columnaplace.getPtrorto().setPtrarriba(nuevonodo);
			columnaplace.setPtrorto(nuevonodo);
		} else {
			System.out.println("Opcion else 169");
			System.out.println("Opcion else 169" + nuevonodo.getCosto());
			ContenidoOrtogonal temp = columnaplace.getPtrorto();
			while (temp != null) {
				System.out.println("enciclado");
				if (temp.getFila() >= nuevonodo.getFila()) {
					nuevonodo.setPtrabajo(temp);
					temp.getPtrarriba().setPtrabajo(nuevonodo);
					nuevonodo.setPtrarriba(temp.getPtrarriba());
					temp.setPtrarriba(nuevonodo);

					break;
				} else if (temp.getPtrabajo() == null) {
					temp.setPtrabajo(nuevonodo);
					nuevonodo.setPtrarriba(temp);
					break;
				}

				temp = temp.getPtrabajo();
			}
		}
//=============================== INSERCION DE CONTENIDO POR FILA==================
		if (filaplace.getPrtorto() == null) {
			filaplace.setPrtorto(nuevonodo);
		} else if (filaplace.getPrtorto().getColumna() >= nuevonodo.getColumna()) {
			nuevonodo.setPtrderecha(filaplace.getPrtorto());
			filaplace.getPrtorto().setPtrizquierda(nuevonodo);
			filaplace.setPrtorto(nuevonodo);
		} else {

			ContenidoOrtogonal temp = filaplace.getPrtorto();
			while (temp != null) {
				System.out.println("enciclado");
				if (temp.getColumna() >= nuevonodo.getColumna()) {
					nuevonodo.setPtrderecha(temp);
					temp.getPtrizquierda().setPtrderecha(nuevonodo);
					nuevonodo.setPtrizquierda(temp.getPtrizquierda());
					temp.setPtrizquierda(nuevonodo);

					break;
				} else if (temp.getPtrderecha() == null) {
					temp.setPtrderecha(nuevonodo);
					nuevonodo.setPtrizquierda(temp);
					break;
				}

				temp = temp.getPtrderecha();
			}

		}

	}

	public void imprimirfila() {
		FilaOrtogonal temp = head.getPrimerafila();
		while (temp != null) {
			System.out.print(temp.getKey() + "----->");
			temp = temp.getPtrabajo();
		}
		System.out.println(" ");
	}

	public void imprimircolumna() {
		ColumnaOrtogonal temp = head.getPrimeracolumna();
		while (temp != null) {
			System.out.print(temp.getKey() + "----->");
			temp = temp.getPtrderecha();
		}
		System.out.println(" ");
	}

	public void imprimir_todo() {
		System.out.println("impresion dentro");
		ColumnaOrtogonal temp = head.getPrimeracolumna();
		while (temp != null) {
			System.out.print(temp.getKey() + "----->");

			ContenidoOrtogonal inside = temp.getPtrorto();
			while (inside != null) {

				System.out.print(inside.getCosto() + "----->");
				inside = inside.getPtrabajo();
			}
			System.out.println("");
			temp = temp.getPtrderecha();
		}
	}

	public String GrafoRuta_DOT() {
		Map<String, Integer> locations = new HashMap<String, Integer>();
		String conexF = "";
		String conexC = "";
		String Contenido = "";
		int contador = 0;
		String graf = "digraph {" + "node[shape=record];" + "graph [pad=\"0.212,0.055\" bgcolor=azure3]" + "\n";
		FilaOrtogonal temp = head.getPrimerafila();
		while (temp != null) {
			System.out.println("temp temp get gila");
			contador++;
			graf = graf + "\"F_" + contador + "\"[label = \"" + temp.getKey() + "\" ,pos=\"" + 0 + "," + contador
					+ "!\",style = filled, fillcolor = darksalmon]" + "\n";
			graf = graf + "\"C_" + contador + "\"[label = \"" + temp.getKey() + "\" ,pos=\"" + contador + "," + 0
					+ "!\",style = filled, fillcolor = darksalmon]" + "\n";
			if (contador == 1) {
				conexF = conexF + "\"F_" + contador + "\"";
				conexC = conexC + "\"C_" + contador + "\"";
			} else {
				conexF = conexF + "->\"F_" + contador + "\"";
				conexC = conexC + "->\"C_" + contador + "\"";
			}
			locations.put(String.valueOf(temp.getKey()), contador);
			temp = temp.getPtrabajo();
		}
		graf = graf + "\"Cabecera\"[label = \"Cabecera\" ,pos=\"" + 0 + "," + 0
				+ "!\",style = filled, fillcolor = crimson]" + "\n";
		if (head.getPrimerafila() != null) {
			graf = graf + "\"Cabecera\"" + "->" + "\"F_1\"" + "\n";
			graf = graf + "\"Cabecera\"" + "->" + "\"C_1\"" + "\n";
		}

		if (head.getPrimerafila().getPtrabajo() != null) {
			graf = graf + conexF + "\n";
			graf = graf + conexC + "\n";
		}
		// _====================================== HASTA ESTA PARTE
		// CORRECTO=========================================
		// ____________________________________CONECTAR LAS
		// FILAS___________________________________________________________
		int contadorinterno = 0;
		String coneexter = "";
		boolean first = true;
		if (head.getPrimerafila() != null) {
			String conexions = "";
			FilaOrtogonal filatemp = head.getPrimerafila();
			while (filatemp != null) {
				System.out.println("dentro while filetemp!=null 288");
				first = true;
				contadorinterno++;
				ContenidoOrtogonal temporto = filatemp.getPrtorto();
				System.out.println("292");
				if (temporto != null) {
					System.out.println("dentro if 292");
					coneexter = coneexter + "\"F_" + contadorinterno + "\"" + "->" + "\"N_" + temporto.getFila() + "_"
							+ temporto.getColumna() + "\"";
				}
				while (temporto != null) {
					System.out.println("dentro temporto 294");
					if (first == true) {
						System.out.println("primera conexion fila");
						conexions = conexions + "\"N_" + temporto.getFila() + "_" + temporto.getColumna() + "\"";
						first = false;
					} else {
						conexions = conexions + "->" + "\"N_" + temporto.getFila() + "_" + temporto.getColumna() + "\"";
					}
					Contenido = Contenido + "\"N_" + temporto.getFila() + "_" + temporto.getColumna()
							+ "\"[label = \"Costo:" + temporto.getCosto() + "\\nTiempo:" + temporto.getCosto()
							+ "\" ,pos=\"" + locations.get(String.valueOf(temporto.getColumna())) + ","
							+ contadorinterno + "!\", fontsize = \"10\",style = filled, fillcolor = cornflowerblue]"
							+ "\n";

					temporto = temporto.getPtrderecha();
				}
				conexions = conexions + "\n" + coneexter;
				coneexter = "";
				filatemp = filatemp.getPtrabajo();
			}
			graf = graf + Contenido;
			graf = graf + "\n" + conexions;
		}
		// ______________________________________ CONECTAR
		// COLUMNAS________________________________________________________________

		int contadorinterno2 = 0;
		String conexexter = "";
		boolean primero = true;
		if (head.getPrimeracolumna() != null) {
			String conexions = "";
			ColumnaOrtogonal columnatemp = head.getPrimeracolumna();
			while (columnatemp != null) {
				primero = true;
				contadorinterno2++;
				ContenidoOrtogonal temporto = columnatemp.getPtrorto();
				if (temporto != null) {
					conexexter = conexexter + "\"C_" + contadorinterno2 + "\"" + "->" + "\"N_" + temporto.getFila()
							+ "_" + temporto.getColumna() + "\"";
				}
				while (temporto != null) {
					if (primero == true) {
						conexions = conexions + "\"N_" + temporto.getFila() + "_" + temporto.getColumna() + "\"";
						primero = false;
					} else {
						conexions = conexions + "->" + "\"N_" + temporto.getFila() + "_" + temporto.getColumna() + "\"";
					}
					temporto = temporto.getPtrabajo();
				}

				conexions = conexions + "\n" + conexexter;
				conexexter = "";
				columnatemp = columnatemp.getPtrderecha();
			}
			graf = graf + "\n" + conexions;
		}

		return graf + "\n" + "}";
	}

//######################### TERMINADO ##############
	public String generador_txt(String rut, String body) {
		String ruta = rut;
		try {

			String contenido = body;
			File file = new File(ruta);
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(contenido);
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ruta;

	}

	public String graficaMatriz() {
		return ExecutGraphviz("neato.exe", "C:\\Users\\JavierG\\Pictures\\graforutas.jpg",
				"C:\\Users\\JavierG\\Pictures\\rutas.txt", GrafoRuta_DOT());

	}

	public String graficarutas(String info) {
		return ExecutGraphviz("dot.exe", "C:\\Users\\JavierG\\Pictures\\grafoR.jpg",
				"C:\\Users\\JavierG\\Pictures\\Rut.txt", GrafoRutas(info));
	}

	public String executeGraphUser(String info, int i) {
		return ExecutGraphviz("dot.exe", "C:\\Users\\JavierG\\Pictures\\GrafoUser" + i + ".jpg",
				"C:\\Users\\JavierG\\Pictures\\User" + i + ".txt", GrafoRutaElegida(info));
	}

	public String ExecutGraphviz(String dot, String outpath, String rutag, String General) {
		String dotPath = dot;
		String fileInputPath = generador_txt(rutag, General);// crear metodo ruta del archivo .dot;
		System.out.println(General);
		String fileOutputPath = outpath; // nuevo nombre imagen
		String tParam = "-Tjpg";
		String tOParam = "-o";
		String[] cmd = new String[5];
		cmd[0] = dotPath;
		cmd[1] = tParam;
		cmd[2] = fileInputPath;
		cmd[3] = tOParam;
		cmd[4] = fileOutputPath;
		Runtime rt = Runtime.getRuntime();
		try {
			rt.exec(cmd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error en la generacion de la imagen");
		}
		return fileOutputPath;
	}

//====================== GRAFO RUTA GENERAL===========================================
	public String GrafoRutas(String data) {
		String info = "graph g {" + "\n" + "node [color=lightblue2, style=filled];" + "\n" + grafo(data) + "\n" + "}";
		return info;
	}

	public String grafo(String dta) {

		String info = "";
		System.out.println(dta.length() + "largo");
		try {
			JSONArray jsonarray = new JSONArray(dta);
			for (int i = 0; i < jsonarray.length(); i++) {
				JSONObject jb = jsonarray.getJSONObject(i);
				int key = Integer.parseInt(jb.getString("Codigo1"));
				int key2 = Integer.parseInt(jb.getString("Codigo2"));
				String costo = jb.getString("Costo");
				String tiempo = jb.getString("Tiempo");
				FilaOrtogonal cod1 = buscarfila(key);
				FilaOrtogonal cod2 = buscarfila(key2);
				if (cod1 != null && cod2 != null) {
					System.out.println("Se econtro" + cod1.getInfo() + " " + cod2.getInfo());
					ContenidoOrtogonal n = new ContenidoOrtogonal(key, key2, costo, tiempo);
					insertar(n);

					info = info + "\"" + cod1.getInfo() + "\"" + "--" + "\"" + cod2.getInfo() + "\"" + " [label=\""
							+ costo + "\n" + tiempo + " \"];";

				} else {
					System.out.println("no se encontro");
				}
			}
		} catch (JSONException e) {
			System.out.println("ERROR CREACION 1");
			e.printStackTrace();
		}

		return info;
	}

//=========================== GRAFO RUTA POR USUARIOS===================================
	public String GrafoRutaElegida(String data) {
		String info = "graph g {" + "\n" + "node [color=lightblue2, style=filled];" + "\n" + contenidoRutaelegida(data)
				+ "\n" + "}";
		return info;
	}

	public String contenidoRutaelegida(String rutas) {
		System.out.println("mi ruta"+rutas);
		String info = "";
		String[] data = rutas.split("/");
		for (int i = 0; i < data.length; i++) {
			String[] ids = data[i].split(",");

			String name1 = ids[0];
			String name2 = ids[1];

			info = info + "\"" + name1 + "\"" + "--" + "\"" + name2 + "\"" + "";

		}

		return info;
	}

//=================================== METODO OBTENCION DE LA RUTA MAS CORTA===================================

	public int GetRuta(int salida, int llegada) {
		ColumnaOrtogonal tempF = head.getPrimeracolumna(); // Creamos el apuntador de la Fila
		int buscando = salida;
		while (tempF != null) {
			if (tempF.getKey() == salida) {
				ArrayList<Integer> temp = new ArrayList();
				ArrayList<Integer> temp1 = new ArrayList();
				ArrayList<Integer> temp2 = new ArrayList();
				ArrayList<Integer> temp3 = new ArrayList();
				ArrayList<Integer> temp4 = new ArrayList();
				if (!temp.contains(buscando)) {
					temp.add(buscando);
					temp2.add(buscando);
					temp3.add(buscando);
					temp4.add(buscando);
				}
				ContenidoOrtogonal tempC = tempF.getPtrorto();
				while (tempC != null) {
					int cLlegada = tempC.getFila();
					if (cLlegada == llegada) {
						temp.add(cLlegada);
						rutas.add(temp);
						temp = new ArrayList<Integer>();
						temp.add(buscando);
					} else {
						ColumnaOrtogonal tempF2 = buscarcolumna(tempC.getFila());

						ContenidoOrtogonal tempC2 = tempF2.getPtrorto();
						cLlegada = tempC.getFila();
						if (cLlegada == llegada) {
							if (!temp1.contains(cLlegada) && !temp1.contains(tempC2.getColumna())) {
								temp1.add(tempC.getColumna());
								temp1.add(cLlegada);
								if (!rutas.contains(temp1)) {
									rutas.add(temp1);
									temp1 = new ArrayList<Integer>();
									temp1.add(buscando);
								}
							}
						} else {
							while (tempC2 != null) {
								tempF2 = buscarcolumna(tempC2.getFila());
								ContenidoOrtogonal tempC3 = tempF2.getPtrorto();
								cLlegada = tempC2.getFila();
								if (cLlegada == llegada) {
									if (!temp2.contains(cLlegada) && !temp2.contains(tempC2.getColumna())) {
										temp2.add(tempC2.getColumna());
										temp2.add(cLlegada);
										if (!rutas.contains(temp2)) {
											rutas.add(temp2);
											temp2 = new ArrayList<Integer>();
											temp2.add(buscando);
										}
									}
								} else {
									while (tempC3 != null) {
										tempF2 = buscarcolumna(tempC3.getFila());
										ContenidoOrtogonal tempC4 = tempF2.getPtrorto();
										cLlegada = tempC3.getFila();
										if (cLlegada == llegada) {
											if (!temp3.contains(cLlegada) && !temp3.contains(tempC2.getClass())
													&& !temp3.contains(tempC.getColumna())) {
												temp3.add(tempC2.getFila());
												temp3.add(tempC2.getFila());
												temp3.add(cLlegada);
												if (!rutas.contains(temp3)) {
													rutas.add(temp3);
													temp3 = new ArrayList<Integer>();
													temp3.add(buscando);
												}
											}
										} else {
											while (tempC4 != null) {
												tempF2 = buscarcolumna(tempC3.getFila());
												cLlegada = tempC4.getFila();
												if (cLlegada == llegada) {
													if (!temp4.contains(cLlegada)
															&& !temp4.contains(tempC2.getColumna())
															&& !temp4.contains(tempC3.getColumna())
															&& !temp4.contains(tempC4.getColumna())) {
														temp4.add(tempC2.getColumna());
														temp4.add(tempC3.getColumna());
														temp4.add(tempC4.getColumna());
														temp4.add(cLlegada);
														if (!rutas.contains(temp4)) {
															rutas.add(temp4);
															temp4 = new ArrayList<Integer>();
															temp4.add(buscando);
														}
													}
												}
												tempC4 = tempC4.getPtrabajo();
											}
										}
										tempC3 = tempC3.getPtrabajo();
									}
								}
								tempC2 = tempC2.getPtrabajo();
							}
						}
					}
					tempC = tempC.getPtrabajo();
				}
			}
			tempF = tempF.getPtrderecha();
		}
		return 0;
	}
	
	public String obtenertiempo() {
		return tiempo;
	}

	public int obtenerprecio(int salida, int entrada) {
		int costo = 0;

		ColumnaOrtogonal temp = buscarcolumna(salida);

		ContenidoOrtogonal cont = temp.getPtrorto();
		while (cont != null) {

			if (cont.getFila() == entrada) {
				tiempo=cont.getRuta();
				return Integer.parseInt(cont.getCosto());
			}
			cont = cont.getPtrabajo();
		}

		return costo;
	}
}
