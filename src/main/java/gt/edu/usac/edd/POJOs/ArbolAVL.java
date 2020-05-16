package gt.edu.usac.edd.POJOs;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.*;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class ArbolAVL {
	private NodoAVL raiz;
	private NodoAVL Aux;
	private boolean apuntado = false;
	private NodoAVL nuevaRaiz;
	private boolean borrado = false;
	private boolean apuntado2 = false;
	private boolean rotacion = false;
	private boolean aumento = false;
	public boolean encontrado = false;
	public static String iduser="";
	String data = "";

	public ArbolAVL() {
		raiz = null;
	}

	public NodoAVL getRoot() {
		return raiz;
	}

	public boolean vacioAVL() {
		if (raiz == null) {
			return true;
		} else {
			return false;
		}
	}

	// ---------------------------- METODO LLAMAR HTTP -----------
	public void insertar(int d, String n, String p) {
		NodoAVL nuevo = new NodoAVL(d, n, p);
		if (raiz == null) {
			System.out.println("primera");
			raiz = nuevo;
		} else {
			System.out.println("otras");
			raiz = insertarAVL(nuevo, raiz);
		}
	}

	public NodoAVL searchAVL(int d) {
		return buscar(d, raiz);
	}

	public JSONArray imprimir() {
		data = "[";
		recorre(raiz);

		JSONArray gson = null;
		data = data + "]";
		try {
			gson = new JSONArray(data);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return gson;
	}

	public void recorre(NodoAVL root) {
		if (root == null) {
			return;
		} else {
			recorre(root.izq);
			data = data + "{\"id\":\"" + root.getDato() + "\",\"nombre\":\"" + root.getName() + "\",\"password\":\""
					+ root.getPassword() + "\"},";
			recorre(root.der);
		}
	}

	public boolean search(String n, String p) {
		encontrado = false;
		buscarUsuario(raiz, n, p);
		return encontrado;
	}
	


	public void buscarUsuario(NodoAVL root, String n, String p) {
		if (root == null) {
			
		} else {
			if (root.getName().equals(n) && root.getPassword().equals(p)) {
				System.out.println("encontrado");
				encontrado = true;iduser=String.valueOf(root.getDato());
			}
			buscarUsuario(root.izq,n,p);
			
			buscarUsuario(root.der,n,p);
		}
	}
	
	public String getId() {
		if(encontrado==true) {
			return iduser;
		}else {
			return "null";
		}
	}

	// -------------------------------------------------------------------------------------------

	// ============================ METODO GRAFO==========================
	public String ImageGenerator() {
		String path = "C:\\Users\\JavierG\\Pictures\\AVLgraph.jpg";
		FileWriter fichero = null;
		PrintWriter escritor;
		String file = "C:\\Users\\JavierG\\Pictures\\AVL_grafico.dot";
		try {

			fichero = new FileWriter("C:\\Users\\JavierG\\Pictures\\AVL_grafico.dot");
			escritor = new PrintWriter(fichero);
			escritor.print(doc_Dot());
		} catch (Exception e) {
			System.err.println("Error al escribir el archivo AVL_grafico.dot");
		} finally {
			try {
				if (null != fichero)
					fichero.close();
			} catch (Exception e2) {
				System.err.println("Error al cerrar el archivo AVL_grafico.dot");
			}
		}
		try {
			Runtime rt = Runtime.getRuntime();
			rt.exec("dot.exe -Tjpg  " + file + " -o " + path);
			// Esperamos medio segundo para dar tiempo a que la imagen se genere.
			// Para que no sucedan errores en caso de que se decidan graficar varios
			// árboles sucesivamente.
			Thread.sleep(500);
		} catch (Exception ex) {
			System.err.println("Error al generar la imagen para el archivo aux_grafico.dot");
		}
		return path;
	}

	public String doc_Dot() {
		return "digraph grafica{\n" + "rankdir=TB;\n" + "node [shape = record, style=filled, fillcolor=seashell2];\n"
				+ GrafCreator(raiz) + "}\n";
	}

	public String GrafCreator(NodoAVL head) {
		String etiqueta;
		if (head.izq == null && head.der == null) {
			etiqueta = "nodo" + head.dato + " [ label =\"" + head.dato + "\"];\n";
		} else {
			etiqueta = "nodo" + head.dato + " [ label =\"<C0>|" + head.dato + "|<C1>\"];\n";
		}
		if (head.izq != null) {
			etiqueta = etiqueta + GrafCreator(head.izq) + "nodo" + head.dato + ":C0->nodo" + head.izq.dato + "\n";
		}
		if (head.der != null) {
			etiqueta = etiqueta + GrafCreator(head.der) + "nodo" + head.dato + ":C1->nodo" + head.der.dato + "\n";
		}
		return etiqueta;
	}

//--------------- INICIO DE METODOS--------------------

//-------------------- METODO BUSCAR------------------------	

	public NodoAVL buscar(int d, NodoAVL r) {
		System.out.println("veces");
		if (r == null) {
			return null;
		} else if (r.getDato() == d) {
			return r;
		} else if (r.getDato() < d) {
			return buscar(d, r.der);
		} else {
			return buscar(d, r.izq);
		}
	}

// ----------------- METODO FACTOR EQUILIBRIO--------------
	public int getFE(NodoAVL x) {
		if (x == null) {
			return -1;
		} else {
			return x.fe;
		}
	}

// ----------------- ROTACION SIMPLE A LA IZQUIERDA----------
	public NodoAVL rotacionizq(NodoAVL c) {
		NodoAVL aux = c.izq;
		c.izq = aux.der;
		aux.der = c;
		c.fe = Math.max(getFE(c.izq), getFE(c.der)) + 1;
		aux.fe = Math.max(getFE(aux.izq), getFE(aux.der)) + 1;
		return aux;
	}

//----------------- ROTACION SIMPLE DERECHA--------------------
	public NodoAVL rotacionder(NodoAVL c) {
		NodoAVL aux = c.der;
		c.der = aux.izq;
		aux.izq = c;
		c.fe = Math.max(getFE(c.izq), getFE(c.der)) + 1;
		aux.fe = Math.max(getFE(aux.izq), getFE(aux.der)) + 1;
		return aux;
	}

//------------------- ROTACION DOBLE A LA IZQUIERDA----------------
	public NodoAVL RotacionDobleIzq(NodoAVL c) {
		NodoAVL aux;
		c.izq = rotacionder(c.izq);
		aux = rotacionizq(c);
		return aux;
	}

//-------------------- ROTACION DOBLE A LA DERECHA----------------------
	public NodoAVL RotacionDobleDer(NodoAVL c) {
		NodoAVL aux;
		c.der = rotacionizq(c.der);
		aux = rotacionder(c);
		return aux;
	}

//------------------ INSERTAR AVL-------------------------------------
	public NodoAVL insertarAVL(NodoAVL nuevo, NodoAVL sub) {
		NodoAVL nuevoP = sub;
		if (nuevo.dato < sub.dato) {
			if (sub.izq == null) {
				sub.izq = nuevo;
			} else {
				sub.izq = insertarAVL(nuevo, sub.izq);
				if ((getFE(sub.izq) - getFE(sub.der)) == 2) {
					if (nuevo.dato < sub.izq.dato) {
						nuevoP = rotacionizq(sub);
					} else {
						nuevoP = RotacionDobleIzq(sub);
					}
				}
			}
		} else if (nuevo.dato > sub.dato) {
			if (sub.der == null) {
				sub.der = nuevo;
			} else {
				sub.der = insertarAVL(nuevo, sub.der);
				if ((getFE(sub.der) - getFE(sub.izq)) == 2) {
					if (nuevo.dato > sub.der.dato) {
						nuevoP = rotacionder(sub);
					} else {
						nuevoP = RotacionDobleDer(sub);
					}
				}
			}
		} else {
			System.out.println("duplicados");
		}
		// update FE;
		if ((sub.izq == null) && (sub.der != null)) {
			sub.fe = sub.der.fe + 1;
		} else if ((sub.der == null) && (sub.izq != null)) {
			sub.fe = sub.izq.fe + 1;
		} else {
			sub.fe = Math.max(getFE(sub.izq), getFE(sub.der));
		}

		return nuevoP;
	}

	// ------------------------ RECORRIDOS---------------------------------
	public void inOrden(NodoAVL r) {
		if (r != null) {
			inOrden(r.izq);
			System.out.println(r.dato + ", ");
			inOrden(r.der);
		}
	}

	public void preOrden(NodoAVL r) {
		if (r != null) {
			System.out.println(r.dato + ", ");
			inOrden(r.izq);
			inOrden(r.der);
		}

	}

	public void postOrden(NodoAVL r) {
		if (r != null) {
			inOrden(r.izq);
			inOrden(r.der);
			System.out.println(r.dato + ", ");
		}
	}

// -====================================== METODOS PARA ELIMINAR===================================s

	public void eliminar(int llave, NodoAVL A) {
		if (raiz.izq != null || raiz.der != null) {
			if (A != null) {
				if (A.dato < llave) {
					eliminar(llave, A.der);
					if (nuevaRaiz != null && nuevaRaiz.hashCode() != raiz.hashCode()) {
						A.izq = nuevaRaiz;
						nuevaRaiz = null;
					}
					if (borrado == true) {
						A.fe--;
						rotarBorrado(A);
						borrado = (A.fe == 0);
					}
					if (apuntado == true) {
						A.der = Aux;
						apuntado = false;
					}
				} else {
					if (A.dato > llave) {
						eliminar(llave, A.izq);
						if (nuevaRaiz != null && nuevaRaiz.hashCode() != raiz.hashCode()) {
							A.izq = nuevaRaiz;
							nuevaRaiz = null;
						}
						if (borrado == true) {
							A.fe++;
							rotarBorrado(A);
							borrado = (A.fe == 0);
						}
						if (apuntado == true) {
							A.izq = Aux;
							apuntado = false;
						}
					} else {
						if (A.dato == llave) {
							borrado = true;
							apuntado = true;
							if (A.izq == null) {
								Aux = A.der;
							} else {
								if (A.der == null) {
									Aux = A.izq;
								} else {
									Aux = Reemplazar(A, A, true);
								}
							}
						}
					}
				}
			}
		} else {
			raiz = null;
		}
	}

	// ---------------------------------- METODO DE
	// REMPLAZO-----------------------------------------

	private NodoAVL Aux2;

	public NodoAVL Reemplazar(NodoAVL A, NodoAVL buscado, boolean estado) {
		if (estado == true) {
			Reemplazar(A.izq, buscado, false);
			if (nuevaRaiz != null && nuevaRaiz.hashCode() != raiz.hashCode()) {
				A.izq = nuevaRaiz;
				nuevaRaiz = null;
			}
			if (buscado.hashCode() == raiz.hashCode()) {
				raiz = Aux2;
			}
			if (Aux2.hashCode() != buscado.izq.hashCode()) {
				Aux2.izq = buscado.izq;
				buscado.izq = null;
			} else {
				buscado.izq = null;
			}
			Aux2.der = buscado.der;
			buscado.der = null;
			if (borrado == true) {
				Aux2.fe++;
				rotarBorrado(Aux2);
				borrado = (Aux2.fe == 0);
			}
		} else {
			if (A.der == null) {
				Aux2 = A;
				borrado = true;
				apuntado2 = true;
			} else {
				Reemplazar(A.der, buscado, estado);
				if (nuevaRaiz != null && nuevaRaiz.hashCode() != raiz.hashCode()) {
					A.der = nuevaRaiz;
					nuevaRaiz = null;
				}
				if (apuntado2 == true) {
					A.der = Aux2.izq;
					apuntado2 = false;
				}
				if (borrado == true) {
					A.fe--;
					rotarBorrado(A);
					borrado = (A.fe == 0);
				}
			}
		}
		// if(A.derecha!=null)Aux2=null;
		return Aux2;
	}

//-------------------------------- METODO DE ROTACION ELIMINACION------------------------------
	private boolean rotarBorrado(NodoAVL A) {
		if (A.fe < -1) {
			if (A.izq.fe > 0) {
				if (raiz.hashCode() != A.hashCode()) {
					ID(A);
					borrado = false;
					return true;
				} else {
					raiz = ID(A);
					borrado = false;
					return true;
				}

			} else {
				if (raiz.hashCode() != A.hashCode()) {
					II(A);
					borrado = false;
					return true;
				} else {
					raiz = II(A);
					borrado = false;
					return true;
				}

			}
		} else {
			if (A.fe > 1) {
				if (A.der.fe < 0) {
					if (raiz.hashCode() != A.hashCode()) {
						DI(A);
						borrado = false;
						return true;
					} else {
						raiz = DI(A);
						borrado = false;
						return true;
					}
				} else {
					if (raiz.hashCode() != A.hashCode()) {
						DD(A);
						borrado = false;
						return true;
					} else {
						raiz = DD(A);
						aumento = false;
						borrado = false;
						return true;
					}
				}
			}
		}
		return false;
	}

	// ---------------------------------- rotaciones para
	// eliminacion---------------------
	private NodoAVL ID(NodoAVL A) {
		A.izq = DD(A.izq);
		return II(A);
	}

	// rotacion derecha izquierda
	private NodoAVL DI(NodoAVL A) {
		A.der = II(A.der);
		return DD(A);
	}

	// ______________________________________________________________________________________-
	private NodoAVL II(NodoAVL A) {
		rotacion = true;
		NodoAVL aux = A.izq.der;
		A.izq.der = A;
		if (aux == null) {
			if (A.der != null)
				A.izq.fe++;
			if (A.der == null)
				A.izq.fe = 0;
		} else {
			if (A.der != null)
				A.izq.fe = 0;
			if (A.der == null)
				A.izq.fe++;
		}

		NodoAVL aux2 = A.izq;
		A.izq = aux;
		if (aux == null) {
			if (A.der != null)
				A.fe++;
			if (A.der == null)
				A.fe = 0;
		} else {
			if (A.der != null)
				A.fe = 0;
			if (A.der == null)
				A.fe--;
		}
		nuevaRaiz = aux2;

		return aux2;
	}

	// rotacion derecha derecha
	private NodoAVL DD(NodoAVL A) {
		rotacion = true;
		NodoAVL aux = A.der.izq;
		A.der.izq = A;
		if (aux == null) {
			if (A.izq != null)
				A.der.fe--;
			if (A.izq == null)
				A.der.fe = 0;
		} else {
			if (A.izq != null)
				A.der.fe = 0;
			if (A.izq == null)
				A.der.fe--;

		}

		NodoAVL aux2 = A.der;
		A.der = aux;
		if (aux == null) {
			if (A.izq != null)
				A.fe--;
			if (A.izq == null)
				A.fe = 0;
		} else {
			if (A.izq != null)
				A.fe = 0;
			if (A.izq == null)
				A.fe++;

			/*
			 * else{ A.balance=1; }
			 */
		}

		nuevaRaiz = aux2;

		return aux2;

	}

}
