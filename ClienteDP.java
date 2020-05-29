
import java.util.StringTokenizer;

public class ClienteDP
{
    // Atributos
    private String clave, nombre, direccion, telefono, edoCivil;
    
    // Constructor
    public ClienteDP()
    {
        this.clave        = "";
        this.nombre       = "";
        this.direccion    = "";
        this.telefono     = "";
        this.edoCivil 	  = "";
    }
    
    public ClienteDP(String datos)
    {
        StringTokenizer st = new StringTokenizer(datos,"_");
        
        this.clave        = st.nextToken();
        this.nombre       = st.nextToken();
        this.direccion    = st.nextToken();
        this.telefono     = st.nextToken();
        this.edoCivil     = st.nextToken();
    }
    
    // Accesors o geters
    public String getClave()
    {
        return this.clave;
    }
    
    public String getNombre()
    {
        return this.nombre;
    }
    
    public String getDireccion()
    {
        return this.direccion;
    }
    
    public String getTelefono()
    {
        return this.telefono;
    }
    public String getedoCivil()
    {
        return this.edoCivil;
    }
    
    // Mutators o seters
    public void setClave(String cve)
    {
        this.clave = cve;
    }
    
    public void setNombre(String name)
    {
        this.nombre = name;
    }
    
    public void setDireccion(String dir)
    {
        this.direccion = dir;
    }
    
    public void setTelefono(String tel)
    {
        this.telefono = tel;
    }
    
    public void setedoCivil(String edo)
    {
        this.edoCivil = edo;
    }
    
    // Metodos
    public String toString()
    {
        return this.clave+"_"+this.nombre+"_"+this.direccion+"_"+this.telefono+"_"+this.edoCivil;
    }
    
    public String toStringSql()
    {
        return "'"+this.clave+"','"+this.nombre+"','"+this.direccion+"','"+this.telefono+"','"+this.edoCivil+"'";
    }
    
    	public String toStringSqlUpdate()
	{
		return "clave='"+this.clave+"',nombre='"+this.nombre+"',direccion='"+this.direccion+"',telefono='"+this.telefono+"',edoCivil='"+this.edoCivil+"'";
	}

}
