package gt.edu.usac.edd.Services;

import gt.edu.usac.edd.POJOs.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.JsonObject;

@Path("/app")
public class ServiceClient {
	public static ArbolB tree = new ArbolB(3);
	public static ArbolAVL usertree = new ArbolAVL();
	public static Ortogonal matrizorto = new Ortogonal();
	public static boolean gone = false;
	public static String already = "";
	public String[] precio = new String[5];
	public static TablaHash tablaH = new TablaHash(43);
	public static BlockChain blockchain = new BlockChain();
	public static int contadorchain=0;
	public static int contadorGestiones = 0;
	public static int lastid=0;
	public static int contadorhash=0;
	public static int correlativo=0;
//===================================== METODO INICIO DE SESION==============================	// CORRECTO
	@GET
	@Path("/getClient")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String getUsuario(@QueryParam("name") String name, @QueryParam("pass") String pass) {
		System.out.println("user: " + name + " password: " + pass);
		boolean find = usertree.search(name, pass);
			
		if (find == true) {
			String id= usertree.getId();
			return "true"+","+id;
		} else {
			return "false"+","+"Null";
		}
	}
//==----------------------------------------------------------------------------------------------


//========================================== INCERSION Y BUSQUEDA USUARIOS================================== // CORRECTO
	@POST
	@Path("/insertuser")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String InsertarUser(String Info) {
		String id = "";
		String nombre = "";
		String password = "";
		try {
			JSONArray jsonarray = new JSONArray(Info);
			for (int i = 0; i < jsonarray.length(); i++) {
				JSONObject jb = jsonarray.getJSONObject(i);
				nombre = jb.getString("nombre");
				password = jb.getString("pass");
				id = jb.getString("id");
				
				usertree.insertar(Integer.parseInt(id), nombre, password);
			}
		} catch (JSONException e) {
			System.out.println("Error en la Obtencion de los datos");
		}

		System.out.println(id + " " + nombre + " " + password);
		return "insertado";
	}
//=================================== INCERSION ANDROID==================================  // CORRECTO
	@POST
	@Path("/insertAndroid")
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
	@Produces({ MediaType.APPLICATION_JSON })
	public String insertAndroid(@FormParam("id")String id,@FormParam("name")String name,@FormParam("pass")String pass) {
		String iden = id;
		String nombre = name;
		String password =pass;
		System.out.println("id: "+id+" nombre: "+name);
				
	    usertree.insertar(Integer.parseInt(iden), nombre, password); //VERIFICAR
		
		return "true";
	}
//=======================================OBTENCION DE LISTA DE PAISES=================================================================== //CORRECTO
	@GET
	@Path("/getpaises")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String getlistapaises() {
		System.out.println("dentro peticion paises");
		return matrizorto.getpaises();
	}

//================================= OBTENCION DE DATOS PARA TABLA DE RUTAS============================ // CORRECTO
	@GET
	@Path("/tablaRutas")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public JSONArray buscarRuta() {
		if (gone == false) {
			return null;
		} else {
			JSONArray gson = null;

			try {
				gson = new JSONArray(already);
			} catch (JSONException e) {
				System.out.println("Error en la obtencion tabla de rutas");
				e.printStackTrace();
			}
			return gson;
		}

	}

//============================================ INCERSION PAISES A ORTOGONAL==================================  // CORRECTO
	@POST
	@Path("/insertPais")
	@Consumes({ MediaType.APPLICATION_JSON })
	public void insertPais(String Info) {
		String Code = "";
		String Nombre = "";
		try {
			JSONArray jsonarray = new JSONArray(Info);
			for (int i = 0; i < jsonarray.length(); i++) {
				JSONObject jb = jsonarray.getJSONObject(i);
				Code = jb.getString("Codigo");
				Nombre = jb.getString("Nombre");
				matrizorto.insertarColumna(Integer.parseInt(Code), Nombre);
				matrizorto.insertarfila(Integer.parseInt(Code), Nombre);
			}

		} catch (JSONException e) {
			System.out.println("Error en la Obtencion de los datos");
		}

		System.out.println(Code + " " + Nombre);
		matrizorto.imprimir_todo();
	}

//================================================= FIN============================================================	

//==================================== METODO OBTENCION DE GRAFICAS================================================
	@GET
	@Path("/buscarAVL")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public JSONArray tablausuario() {

		return usertree.imprimir();
	}
	
//========================= GRAFICA MATRIZ=============================	// CORRECTO
	@GET
	@Path("/graficamatriz")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String getMatrizGraph() {

		System.out.println("holaa");
		if (matrizorto.vacia() == false) {
			String path = matrizorto.graficaMatriz();
			matrizorto.imprimir_todo();
			return base64(path);
		} else {
			return base64("C:\\Users\\JavierG\\Pictures\\noavaiable.png");
		}
	}

	// ============== GRAFICA ARBOL AVL==================================   //CORRECTO
	@GET
	@Path("/graficaAVL")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String getgraficaavl() {

		System.out.println("holaa2");

		if (usertree.vacioAVL() == false) {
			return base64(usertree.ImageGenerator());
		} else {
			return base64("C:\\Users\\JavierG\\Pictures\\noavaiable.png");
		}
	}

//====================================== GRAFICA  ARBOL B===================  // CORRECTO
	@GET
	@Path("/graficaArbolB")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String getgraficaArbolB() {

		System.out.println("holaa arbol b");

		if (tree.vaciob() == false) {
			System.out.println("holaa arbol creado");

			return base64(CrearGraficaArbolB());
		} else {
			return base64("C:\\Users\\JavierG\\Pictures\\noavaiable.png");
		}
	}

//==================================GRAFICA DE  RUTAS   ============================  // CORRECTO
	@POST
	@Path("/grafoRUTAS")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String getgraficaRUTAS(String info) {
		already = info;
		gone = true;
		if (matrizorto.vacia() == false) {
			System.out.println("mi grafica");
			String data = matrizorto.graficarutas(info);

			return base64(data);
		} else {
			System.out.println("mi grafica 2");
			return base64("C:\\Users\\JavierG\\Pictures\\noavaiable.png");
		}

	}
//======================= GRAFICA RUTAS2 ========================================= //CORRECTO
	@GET
	@Path("/grafoRUTAS2")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String getgraficaRutas2() {         
		System.out.println("hola");

		if (gone == false) {
			System.out.println("mi grafica");

			return base64("C:\\Users\\JavierG\\Pictures\\noavaiable.png");

		} else {
			System.out.println("entro" + already + "----->");
			String data = "C:\\Users\\JavierG\\Pictures\\grafoR.jpg";
			return base64(data);

		}

	}

//=================================== GRAFICA TABLA HASH======================   //CORRECTO
	@GET
	@Path("/grafoHASH")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String getHash() {
		return base64(tablaH.creargraficaHash());

	}

//=======================================================================	  // CORRECTO
	public String base64(String path) {
		String base64Image = "";
		File file = new File(path);
		try {
			FileInputStream imageInFile = new FileInputStream(file);
			// Reading a Image file from file system
			byte imageData[] = new byte[(int) file.length()];
			imageInFile.read(imageData);
			base64Image = Base64.getEncoder().encodeToString(imageData);
			imageInFile.close();
		} catch (FileNotFoundException e) {
			System.out.println("Image not found error  " + e);
		} catch (IOException ioe) {
			System.out.println("Exception while reading the Image " + ioe);
		}
		return base64Image;
	}

//========================================= FIN METODOS==============================================================	

//=============================== METODO PARA LAS  RUTAS=============================================	  // CORRECTO
	@GET
	@Path("/getMiruta")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String getruta(@QueryParam("origen") String origen, @QueryParam("destino") String destino) {
		String jsonObject = "[";
		String dotimage = "";
		int salida = Integer.parseInt(origen);
		int llegada = Integer.parseInt(destino);
		int parcial = 0;
		int tiempo=0;
		ArrayList<Integer> ingresados = new ArrayList<Integer>();
		ingresados.add(1);
		matrizorto.rutas = new ArrayList<ArrayList<Integer>>();
		matrizorto.GetRuta(salida,llegada);
		int[] costo = new int[matrizorto.rutas.size()];
		for (int j = 0; j < matrizorto.rutas.size(); j++) {
			if (j == 0) {
				jsonObject += "{\"Ruta\":\"";
			} else {
				jsonObject += ",{\"Ruta\":\"";
			}

			for (int i = 0; i < matrizorto.rutas.get(j).size(); i++) {
				int id = matrizorto.rutas.get(j).get(i);
				FilaOrtogonal temp = matrizorto.buscarfila(id);
				jsonObject += temp.getInfo() + "|";
				if (i == matrizorto.rutas.get(j).size() - 1) {
					dotimage = dotimage + temp.getInfo();
					System.out.print(matrizorto.rutas.get(j).get(i));

				} else {
					System.out.print(matrizorto.rutas.get(j).get(i) + ",");
					parcial = parcial + matrizorto.obtenerprecio(matrizorto.rutas.get(j).get(i),
							matrizorto.rutas.get(j).get(i + 1));
					tiempo=+tiempo+Integer.parseInt(matrizorto.obtenertiempo());
					dotimage = dotimage + temp.getInfo() + ",";
				}
			}
			
			costo[j] = parcial;
			jsonObject +="\",\"costo\":\""+costo[j]+"\""+",\"tiempo\":"+"\""+tiempo+"\""+"}";
			parcial = 0;
			tiempo=0;
			dotimage += "&";
			System.out.println("-------------" + costo[j]);
		}

		System.out.println("");
		jsonObject += "]";
		return jsonObject;
	}

//=========================================== METODO PARA LAS GESTIONES====================================================	
	
//=============================== METODO INSERCION AL ARBOL B======================================  //CORRECTO comprobar blockchain
	@POST
	@Path("/insertarB")
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
	@Produces({ MediaType.APPLICATION_JSON })
	public void insertB(@FormParam("fecha") String fecha,@FormParam("hora") String hora,@FormParam("costo") String costo,@FormParam("nameus") String nameus,@FormParam("coduser") String coduser,@FormParam("descrip") String descrip ) {
		correlativo++;
		System.out.println("fecha: "+fecha+" hora: "+hora+" Costo :"+costo+" nombre "+nameus);
		int[] values = { 100, 101, 40, 30, 25, 26, 15, 12, 55, 205, 360, 30, 20, 103, 170, 171,21,13,11,2,99,166,33,101,8,33,17,29,73,101,131,37,19,54,6,215,111,103,200,44,73,91,97,69,22,300,301,302,303,304,305,306,307,308,309,310,311,312,313,314};
		// estos valores solo son las llaves pero pueden ser aleatorios como pide el
		// enunciado
			String cadena=fecha+hora+coduser;
			String obtencionHashcode = getHashChain(cadena);
			blockchain.agregarfinal(contadorchain,nameus+fecha,obtencionHashcode,coduser);
			blockchain.imprimirdoble();
			tree.insert(new InfoGestion(values[contadorhash], fecha, hora, Double.parseDouble(costo),nameus,coduser ,descrip), "Dummy ");
			contadorhash++;
		

	}
//======================================================================================================
	@GET
	@Path("/getGestion")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String getGestion(int id) {
		InfoGestion nuevo = new InfoGestion(id, "", "", 0.0, "", "", "");
		Generico Busqueda = tree.search(nuevo);
		if (Busqueda == null) {
			return "Gestion Nula";
		} else {
			return Busqueda.getName().toString();
		}

	}

//==================================== FIN METODOS GESTION============================================

//======================================== INCERSIONES TABLA HASH================================= CORRECTO // 
	@POST
	@Path("/insertarhash")
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
	@Produces({ MediaType.APPLICATION_JSON })
	public String insertHash(@FormParam("cost")String cost,@FormParam("time")String time,@FormParam("reserv")String reserv,@FormParam("client")String client,@FormParam("rut")String rut ) {
	
		String costo = cost;
		String tiempo = time;
		String reservacion = reserv;
		String cliente = client;
		String ruta = rut;
		ReservacionesHash nuevo = new ReservacionesHash(costo, tiempo, reservacion, cliente, ruta);
		tablaH.insertar(nuevo);
		tablaH.imprimirhash();
		System.out.println(costo + " " + cliente);
		tablaH.creargraficaHash();
		return "insertado";
	}
//=========================== obtencion reservaciones=================================== // CORRECTO
	@GET
	@Path("/getMisdatos")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String getdatos(@QueryParam("cliente") String cliente) {
		System.out.println("dentro get hash");
		String jsondata= tablaH.datauser(cliente);
		System.out.println(jsondata);
		
		return jsondata;
	}
//=======================================================================================
//============================== Obtencion BLOCKCHAIN=====================================
	public String getHashChain(String data) {
		String cod="";
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
			digest.update(data.getBytes());
			byte messageDigest[] = digest.digest();
			StringBuffer hexstring = new StringBuffer();
			for (int i=0; i<messageDigest.length; i++) {
				hexstring.append(Integer.toHexString(0xFF & messageDigest[i]));
			}
			
			cod=hexstring.toString();
			
		} catch (NoSuchAlgorithmException e) {
			cod="null";
			System.out.println("Error a la hora de convertir el dato");
			e.printStackTrace();
		}
		return cod;
		
	}

//========================================0 GRAFICA DE MI USUARIO====================================
	@GET
	@Path("/getImagenRutaUser")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String getImageRutUser(@QueryParam("Paises") String Paises) {
		System.out.println("Inicio");
		String path = matrizorto.executeGraphUser(Paises,1);
		System.out.println("final");
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			System.out.println("error");
			e.printStackTrace();
		}
		System.out.println("Imagen");
		
		String base=base64(path);
		return base;
	}
	
//==========================================================================================	
	public String CrearGraficaArbolB() {
		String tempFolder = "C:\\Users\\JavierG\\Pictures\\";
		try {

			FileWriter f = new FileWriter(tempFolder + "grafo1.txt");

			f.write(tree.toDot());

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

		doDot(tempFolder + "/grafo1.txt", tempFolder + "/grafo1.jpg");

		return tempFolder + "grafo1.jpg";

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
