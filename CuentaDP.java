
import java.util.StringTokenizer;

public class CuentaDP
{
    // Atributos
    private String nocta, tipo, saldo, fecha, cveCliente;
    
    // Constructor
    public CuentaDP()
    {
        this.nocta        = "";
        this.tipo       = "";
        this.saldo    = "";
        this.fecha     = "";
        this.cveCliente     = "";
    }
    
    public CuentaDP(String datos)
    {
        StringTokenizer st = new StringTokenizer(datos,"_");
        
        this.nocta        = st.nextToken();
        this.tipo       = st.nextToken();
        this.saldo    = st.nextToken();
        this.fecha     = st.nextToken();
        this.cveCliente     = st.nextToken();
    }
    
    // Accesors o geters
    public String getNocta()
    {
        return this.nocta;
    }
    
    public String getTipo()
    {
        return this.tipo;
    }
    
    public String getSaldo()
    {
        return this.saldo;
    }
    
    public String getFecha()
    {
        return this.fecha;
    }
    
    public String getCveCliente()
    {
        return this.cveCliente;
    }
    
    // Mutators o seters
    public void setNocta(String nct)
    {
        this.nocta = nct;
    }
    
    public void setTipo(String type)
    {
        this.tipo = type;
    }
    
    public void setSaldo(String sal)
    {
        this.saldo = sal;
    }
    
    public void setFecha(String fec)
    {
        this.fecha = fec;
    }
    
    public void setCveCliente(String cveC)
    {
        this.cveCliente = cveC;
    }
    
    // Metodos
    public String toString()
    {
        return this.nocta+"_"+this.tipo+"_"+this.saldo+"_"+this.fecha+"_"+this.cveCliente;
    }
    
    public String toStringSql()
    {
        return "'"+this.nocta+"','"+this.tipo+"','"+this.saldo+"','"+this.fecha+"','"+this.cveCliente+"'";
    }
    
    	public String toStringSqlUpdate()
	{
		return "nocta='"+this.nocta+"',tipo='"+this.tipo+"',saldo='"+this.saldo+"',fecha='"+this.fecha+"',cveCliente='"+this.cveCliente+"'";
	}

}
