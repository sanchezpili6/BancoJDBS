
import java.util.StringTokenizer;

public class RetiroDP
{
	// Atributos
	private String numcta, tipo, saldo, cantidad, saldoNuevo, fecha;
	
	// Constructor
	public RetiroDP()
	{
		this.numcta        = "";
		this.tipo          = "";
		this.saldo   = "";
		this.cantidad = "";
		this.saldoNuevo   = "";
		this.fecha  = "";
	}
	
	public RetiroDP(String datos)
	{
		StringTokenizer st = new StringTokenizer(datos,"_");
		
		this.numcta        = st.nextToken();
		this.tipo          = st.nextToken();
		this.saldo   = st.nextToken();
		this.cantidad = st.nextToken();
		this.saldoNuevo   = st.nextToken();
		this.fecha   = st.nextToken();
	}
	
	// Accesors o geters
	public String getNumcta()
	{
		return this.numcta;
	}
	
	public String getTipo()
	{
		return this.tipo;
	}
	
	public String getSaldo()
	{
		return this.saldo;
	}
	
	public String getCantidad()
	{
		return this.cantidad;
	}
	
	public String getSaldoNuevo()
	{
		return this.saldoNuevo;
	}
		
	public String getFecha()
	{
		return this.fecha;
	}
	
	// Mutators o seters
	public void setNumcta(String ncta)
	{
		this.numcta = ncta;
	}
	
	public void setTipo(String tipo)
	{
		this.tipo = tipo;
	}
	
	public void setSaldo(String sal)
	{
		this.saldo = sal;
	}
	
	public void setCantidad(String cant)
	{
		this.cantidad = cant;
	}
	
	public void setSaldoNuevo(String salN)
	{
		this.saldoNuevo = salN;
	}
	
	public void setFecha(String fec)
	{
		this.fecha = fec;
	}
	
	// Metodos
	public String toString()
	{
		return this.numcta+"_"+this.tipo+"_"+this.saldo+"_"+this.cantidad+"_"+this.saldoNuevo+"_"+this.fecha;
	}
	
	public String toStringSql()
	{
		return "'"+this.numcta+"','"+this.tipo+"','"+this.saldo+"','"+this.cantidad+"','"+this.saldoNuevo+"','"+this.fecha+"'";
	}
}