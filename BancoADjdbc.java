
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.StringTokenizer;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;


public class BancoADjdbc
{
	// Atributos
	private PrintWriter    archivoOut;
	private BufferedReader archivoIn;
	
	private Connection conexion;
	private Statement  statement;
	
	private ClienteDP   clientedp;
	private CuentaDP	cuentadp;
	private RetiroDP 	retirodp;
	private DepositoDP  depositodp;
	
	// Constructor
	public BancoADjdbc()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance(); // Indicar el tipo de driver JDBC a utilizar
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/banco?user=root"); // Conectar a la BD
			
			System.out.println("Conexion exitosa a la BD...");
		}
		catch(ClassNotFoundException cnfe)
		{
			System.out.println("Error 1: "+cnfe);
		}
		catch(InstantiationException ie)
		{
			System.out.println("Error 2: "+ie);
		}
		catch(IllegalAccessException iae)
		{
			System.out.println("Error 3: "+iae);
		}
		catch(SQLException sqle)
		{
			System.out.println("Error 4: "+sqle);
		}
	}
	
	//Métodos
	public String capturar(String tabla, String datos)
	{
		String resultado="";
		String insert="";
		
		
		if(tabla.equals("Cliente"))
		{
			clientedp = new ClienteDP(datos);
			insert = "INSERT INTO "+tabla+" VALUES("+clientedp.toStringSql()+")";
		}
		
		if(tabla.equals("Cuenta"))
		{
			cuentadp = new CuentaDP(datos);
			insert = "INSERT INTO "+tabla+" VALUES("+cuentadp.toStringSql()+")";
		}
		
		if(tabla.equals("Deposito"))
		{
			depositodp = new DepositoDP(datos);
			insert = "INSERT INTO "+tabla+" VALUES("+depositodp.toStringSql()+")";
		
		}
		
		if(tabla.equals("Retiro"))
		{
			retirodp = new RetiroDP(datos);
			insert = "INSERT INTO "+tabla+" VALUES("+retirodp.toStringSql()+")";
		}
		
		try
		{
			// 1. Abrir el archivo para escribir o capturar los datos
			//archivoOut = new PrintWriter(new FileWriter("Cliente.txt",true));
			statement = conexion.createStatement();
			
			// 2. Escribir o capturar los datos en el archivo
			//archivoOut.println(datos);
			statement.executeUpdate(insert);
			
			// 3. Cerrar archivo 
			//archivoOut.close();
			statement.close();
			
			resultado = "Captura correcta: "+datos;
			
			System.out.println(insert);
		}
		catch(SQLException ioe)
		{
			resultado = "Error: "+ioe;
			System.out.println("Error: "+ioe);
		}
		
		return resultado;
	}
	
		// Metodos de gestion de la tabla Cuenta
	public String consultarCuentas()
	{
		String datos="";
		String query;
		ResultSet tr;
		
		query = "SELECT * FROM Cuenta";
		
		try
		{
			// 1.Abrir el archivo para leer datos
			//archivoIn = new BufferedReader(new FileReader("Cuenta.txt"));
			statement = conexion.createStatement();
			
			// Ejecutar query
			tr = statement.executeQuery(query);
			
			cuentadp = new CuentaDP();
			
			while(tr.next())
			{
				cuentadp.setNocta(tr.getString("nocta"));
				cuentadp.setTipo(tr.getString("tipo"));
				cuentadp.setSaldo(tr.getString("saldo"));
				cuentadp.setFecha(tr.getString("fecha"));
				cuentadp.setCveCliente(tr.getString("cveCliente"));
				
				datos = datos + cuentadp.toString() + "\n";
			}
			
			// 3. Cerrar el archivo
			//archivoIn.close();
			statement.close();
			
			System.out.println(query);
		}
		catch(SQLException sqle)
		{
			datos = "Error 2: "+sqle;
			System.out.println("Error: "+sqle);
		}
		
		return datos;
	}
	
	public String consultarTipo(String tipo)
	{
		String datos="";
		String query;
		boolean encontrado=false;
		ResultSet tr;
		
		query = "SELECT * FROM Cuenta WHERE tipo='"+tipo+"'";
		
		try
		{
			// 1.Abrir la BD
			statement = conexion.createStatement();
			
			// Ejecutar query
			tr = statement.executeQuery(query);
						
			// 2. Procesar los datos de la Tabla Resultante
			cuentadp = new CuentaDP();
			
			while(tr.next())
			{
				cuentadp.setNocta(tr.getString("nocta"));
				cuentadp.setTipo(tr.getString("tipo"));
				cuentadp.setSaldo(tr.getString("saldo"));
				cuentadp.setFecha(tr.getString("fecha"));
				cuentadp.setCveCliente(tr.getString("cveCliente"));
				
				datos = datos + cuentadp.toString() + "\n";
				
				encontrado = true;
			}
			
			// 3. Cerrar ND
			statement.close();
			
			if(!encontrado)
				datos = "No hay cuentas con el tipo: "+tipo;
			
			System.out.println(query);
		}
		catch(SQLException sqle)
		{
			datos = "Error 2: "+sqle;
			System.out.println("Error: "+sqle);
		}
		
		return datos;
	}
	
	public String consultarClave(String cve)
	{
		String datos="";
		String query;
		boolean encontrado=false;
		ResultSet tr;
		
		query = "SELECT * FROM Cliente WHERE clave='"+cve+"'";
		
		try
		{
			// 1.Abrir la BD
			statement = conexion.createStatement();
			
			// Ejecutar query
			tr = statement.executeQuery(query);
						
			// 2. Procesar los datos de la Tabla Resultante
			clientedp = new ClienteDP();
			
			if(tr.next())
			{
				clientedp.setClave(tr.getString("clave"));
				clientedp.setNombre(tr.getString("nombre"));
				clientedp.setDireccion(tr.getString("direccion"));
				clientedp.setTelefono(tr.getString("telefono"));
				clientedp.setedoCivil(tr.getString("edoCivil"));
				
				datos = datos + clientedp.toString() + "\n";
				
				encontrado = true;
			}
			
			// 3. Cerrar ND
			statement.close();
			
			if(!encontrado)
				datos = "NOT_FOUND";
			
			System.out.println(query);
		}
		catch(SQLException sqle)
		{
			datos = "Error 2: "+sqle;
			System.out.println("Error: "+sqle);
		}
		
		return datos;
	}
		public String consultarCuentapC(String cve)
	{
		String datos="";
		String query;
		boolean encontrado=false;
		ResultSet tr;
		
		query = "SELECT * FROM Cuenta WHERE cveCliente='"+cve+"'";
		
		try
		{
			// 1.Abrir la BD
			statement = conexion.createStatement();
			
			// Ejecutar query
			tr = statement.executeQuery(query);
						
			// 2. Procesar los datos de la Tabla Resultante
			cuentadp = new CuentaDP();
			
			while(tr.next())
			{
				cuentadp.setNocta(tr.getString("nocta"));
				cuentadp.setTipo(tr.getString("tipo"));
				cuentadp.setSaldo(tr.getString("saldo"));
				cuentadp.setFecha(tr.getString("fecha"));
				cuentadp.setCveCliente(tr.getString("cveCliente"));
				
				datos = datos + cuentadp.toString() + "\n";
				
				encontrado = true;
			}
			
			// 3. Cerrar ND
			statement.close();
			
			if(!encontrado)
				datos = "NOT_FOUND";
			
			System.out.println(query);
		}
		catch(SQLException sqle)
		{
			datos = "Error 2: "+sqle;
			System.out.println("Error: "+sqle);
		}
		
		return datos;
	}
	
	public String consultarCuenta(String ncta)
	{
		String datos="";
		String query;
		boolean encontrado=false;
		ResultSet tr;
		
		query = "SELECT * FROM Cuenta WHERE nocta='"+ncta+"'";
		
		try
		{
			// 1.Abrir la BD
			statement = conexion.createStatement();
			
			// Ejecutar query
			tr = statement.executeQuery(query);
						
			// 2. Procesar los datos de la Tabla Resultante
			cuentadp = new CuentaDP();
			
			if(tr.next())
			{
				cuentadp.setNocta(tr.getString("nocta"));
				cuentadp.setTipo(tr.getString("tipo"));
				cuentadp.setSaldo(tr.getString("saldo"));
				cuentadp.setFecha(tr.getString("fecha"));
				cuentadp.setCveCliente(tr.getString("cveCliente"));
				
				datos = datos + cuentadp.toString();
				
				encontrado = true;
			}
			
			// 3. Cerrar ND
			statement.close();
			
			if(!encontrado)
				datos = "NOT_FOUND";
			
			System.out.println(query);
		}
		catch(SQLException sqle)
		{
			datos = "Error 2: "+sqle;
			System.out.println("Error: "+sqle);
		}
		
		return datos;
	}
	
		public String consultarFecha(String fecha)
	{
		String datos="";
		String query;
		boolean encontrado=false;
		ResultSet tr;
		
		query = "SELECT * FROM Cuenta WHERE fecha='"+fecha+"'";
		
		try
		{
			// 1.Abrir la BD
			statement = conexion.createStatement();
			
			// Ejecutar query
			tr = statement.executeQuery(query);
						
			// 2. Procesar los datos de la Tabla Resultante
			cuentadp = new CuentaDP();
			
			if(tr.next())
			{
				cuentadp.setNocta(tr.getString("nocta"));
				cuentadp.setTipo(tr.getString("tipo"));
				cuentadp.setSaldo(tr.getString("saldo"));
				cuentadp.setFecha(tr.getString("fecha"));
				cuentadp.setCveCliente(tr.getString("cveCliente"));
				
				datos = datos + cuentadp.toString() + "\n";
				
				encontrado = true;
			}
			
			// 3. Cerrar ND
			statement.close();
			
			if(!encontrado)
				datos = "NOT_FOUND";
			
			System.out.println(query);
		}
		catch(SQLException sqle)
		{
			datos = "Error 2: "+sqle;
			System.out.println("Error: "+sqle);
		}
		
		return datos;

	}	
	public String actualizar(String tabla, String datos)
	{
		String resultado="";
		String update="";
		
		if(tabla.equals("Cuenta"))
		{
			cuentadp = new CuentaDP(datos);
			update = "UPDATE Cuenta SET "+cuentadp.toStringSqlUpdate()+" WHERE nocta='"+cuentadp.getNocta()+"'";
		}
		
		if(tabla.equals("Cliente"))
		{
			clientedp = new ClienteDP(datos);
			update = "UPDATE cliente SET "+clientedp.toStringSqlUpdate()+" WHERE clave='"+clientedp.getClave()+"'";
		}
		
		try
		{
			// 1. Abrir la BD
			statement = conexion.createStatement();
			
			// 2. Actualizar datos con Update
			statement.executeUpdate(update);
			
			// 3. Cerrar la BD
			statement.close();
			
			resultado = "Actualizacion correcta...";
			
			System.out.println(update);
		}
		catch(SQLException ioe)
		{
			resultado = "Error: "+ioe;
			System.out.println("Error: "+ioe);
		}
		
		return resultado;
	}
	//Métodos para tabla clientes
		public String consultarCliente()
	{
		String datos="";
		String query;
	
		ResultSet tr;
		
		query = "SELECT * FROM Cliente";
		
		try
		{
			// 1.Abrir el archivo para leer datos
			//archivoIn = new BufferedReader(new FileReader("Doctor.txt"));
			statement = conexion.createStatement();
			
			// Ejecutar query
			tr = statement.executeQuery(query);
			
			// 2. Procesar los datos
			clientedp = new ClienteDP();
			
			while(tr.next())
			{
				clientedp.setClave(tr.getString("clave"));
				clientedp.setNombre(tr.getString("nombre"));
				clientedp.setDireccion(tr.getString("direccion"));
				clientedp.setTelefono(tr.getString(4));
				
				datos = datos + clientedp.toString() + "\n";
			}
			
			// 3. Cerrar el archivo
			//archivoIn.close();
			statement.close();
			
			System.out.println(query);
		}
		catch(SQLException sqle)
		{
			datos = "Error 2: "+sqle;
			System.out.println("Error: "+sqle);
		}
		
		return datos;
	}
	
	
	//Métodos tabla depósito y retiro
	public String consultarNumcta(String tabla, String numcta)
	{
		String datos="";
		String query;
		boolean encontrado=false;
		ResultSet tr;
		
		query = "SELECT * FROM " +tabla+" WHERE numcta='"+numcta+"'";
		
		try
		{
			// 1.Abrir el archivo para leer datos
			statement = conexion.createStatement();
			
			// Ejecutar query
			tr = statement.executeQuery(query);
			
			if(tabla=="deposito")
			{
				depositodp = new DepositoDP();
				while(tr.next())
				{
					depositodp.setNumcta(tr.getString("numcta"));
					depositodp.setTipo(tr.getString("tipo"));
					depositodp.setSaldo(tr.getString("saldo"));
					depositodp.setCantidad(tr.getString("cantidad"));
					depositodp.setSaldoNuevo(tr.getString("saldoNuevo"));
					depositodp.setFecha(tr.getString("fecha"));
					
					datos = datos + depositodp.toString() + "\n";
					encontrado=true;
				}
			}
			else
			{
				retirodp = new RetiroDP();
				while(tr.next())
				{
					retirodp.setNumcta(tr.getString("numcta"));
					retirodp.setTipo(tr.getString("tipo"));
					retirodp.setSaldo(tr.getString("saldo"));
					retirodp.setCantidad(tr.getString("cantidad"));
					retirodp.setSaldoNuevo(tr.getString("saldoNuevo"));
					retirodp.setFecha(tr.getString("fecha"));
					
					datos = datos + retirodp.toString() + "\n";
					encontrado=true;
				}
			}
			
			
			// 3. Cerrar el archivo
			//archivoIn.close();
			statement.close();
			
			if(!encontrado)
				datos="NOT_FOUND";
			
			System.out.println(query);
		}
		catch(SQLException sqle)
		{
			datos = "Error 2: "+sqle;
			System.out.println("Error: "+sqle);
		}
		
		return datos;
	}
	public String consultarTipoDR(String tabla, String tipo)
	{
		String datos="";
		String query;
		boolean encontrado=false;
		ResultSet tr;
		
		query = "SELECT * FROM " +tabla+" WHERE tipo='"+tipo+"'";
		
		try
		{
			// 1.Abrir el archivo para leer datos
			//archivoIn = new BufferedReader(new FileReader("Doctor.txt"));
			statement = conexion.createStatement();
			
			// Ejecutar query
			tr = statement.executeQuery(query);
			
			if(tabla=="deposito")
			{
				depositodp = new DepositoDP();
				while(tr.next())
				{
					depositodp.setNumcta(tr.getString("numcta"));
					depositodp.setTipo(tr.getString("tipo"));
					depositodp.setSaldo(tr.getString("saldo"));
					depositodp.setCantidad(tr.getString("cantidad"));
					depositodp.setSaldoNuevo(tr.getString("saldoNuevo"));
					depositodp.setFecha(tr.getString("fecha"));
					
					datos = datos + depositodp.toString() + "\n";
					encontrado = true;
				}
			}
			else
			{
				retirodp = new RetiroDP();
				while(tr.next())
				{
					retirodp.setNumcta(tr.getString("numcta"));
					retirodp.setTipo(tr.getString("tipo"));
					retirodp.setSaldo(tr.getString("saldo"));
					retirodp.setCantidad(tr.getString("cantidad"));
					retirodp.setSaldoNuevo(tr.getString("saldoNuevo"));
					retirodp.setFecha(tr.getString("fecha"));
					
					datos = datos + retirodp.toString() + "\n";
					encontrado = true;
				}
			}
			
			// 3. Cerrar el archivo
			//archivoIn.close();
			statement.close();
			
			if(!encontrado)
				datos = "NOT_FOUND";
			
			System.out.println(query);
		}
		catch(SQLException sqle)
		{
			datos = "Error 2: "+sqle;
			System.out.println("Error: "+sqle);
		}
		
		return datos;
	}
}