
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.GridLayout;
import java.awt.FlowLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.StringTokenizer;

public class ClienteGUI extends JFrame implements ActionListener
{
    // Atributos de la Clase CuentaGUI
	private JTextField tfClave, tfNombre, tfDireccion, tfTelefono, tfEdoCivil;
	
	private JButton  bCapturar, bConsClave, bConsNombre, bConsDireccion, bModificar, bConsTelefono, bActualizar, bCancelar;
	
	private JTextArea taDatos;

	private JPanel   panel1;
	private JPanel   panel2;
	
	//private HospitalAD hospitalad = new HospitalAD();
	private BancoADjdbc bancoad = new BancoADjdbc();
	
    // Constructor de la clase DoctorGUI
	public ClienteGUI()
	{
		super("Catalogo Clientes");
        
        // 1. Crear objetos de los atributos
        //JTextFieldsc
        tfClave        = new JTextField();
        tfNombre       = new JTextField();
        tfDireccion    = new JTextField();
        tfTelefono     = new JTextField();
        tfEdoCivil 	   = new JTextField();
        
        // JButtons
        bCapturar     = new JButton("Capturar Datos");
        bConsClave    = new JButton("Consultar clave del cliente");
        bConsNombre    = new JButton("Consultar nombre del cliente");
        bModificar	  = new JButton("Modificar Datos");
        bActualizar   = new JButton("Actualizar Datos");
        bCancelar     = new JButton("Cancelar transaccion");
        
        bActualizar.setEnabled(false);
        bCancelar.setEnabled(false);

		// Poner addActionListener a los JButtons
		bCapturar.addActionListener(this);
		bConsClave.addActionListener(this);
		bConsNombre.addActionListener(this);
		bModificar.addActionListener(this);
		bActualizar.addActionListener(this);
		bCancelar.addActionListener(this);
		
        // JPanels y JTextArea
		panel1  = new JPanel();
		panel2  = new JPanel();
        taDatos = new JTextArea(10,35);
        
        // 2. Crear los Layouts de los JPanels
		panel1.setLayout(new GridLayout(11,2));
		panel2.setLayout(new FlowLayout());
		
        // 3. Adicionar objetos al panel1
        // JTextFields
		panel1.add(new JLabel("GESTION DE CLIENTES"));
		panel1.add(new JLabel(""));
		
		panel1.add(new JLabel("Clave del cliente: "));
		panel1.add(tfClave);
		
		panel1.add(new JLabel("Nombre: "));
		panel1.add(tfNombre);
		
		panel1.add(new JLabel("Dirección: "));
		panel1.add(tfDireccion);
		
		panel1.add(new JLabel("Teléfono: "));
		panel1.add(tfTelefono);
		
		panel1.add(new JLabel("edoCivil: "));
		panel1.add(tfEdoCivil);
		
		
        // JButtons
		panel1.add(bCapturar);
		panel1.add(bConsClave);
		panel1.add(bConsNombre);
		panel1.add(bModificar);
		panel1.add(bActualizar);
		panel1.add(bCancelar);
				
        // 4. Adicionar panel1 a panel2
		panel2.add(panel1);
		panel2.add(new JScrollPane(taDatos));
        
        // 5. Adicionar panel2 al JFrame y hacerlo visible
		add(panel2);
		setSize(500,500);
		//setVisible(true);
        
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
		public JPanel getPanel2()
	{
		return this.panel2;
	}
	
	
	private void inactivarBotones()
	{		
		bCapturar.setEnabled(false);
        bConsClave.setEnabled(false);
        bConsNombre.setEnabled(false);
        bModificar.setEnabled(false);
		
		bActualizar.setEnabled(true);
        bCancelar.setEnabled(true);
	}
	
	private void activarBotones()
	{		
		bCapturar.setEnabled(true);
        bConsClave.setEnabled(true);
        bConsNombre.setEnabled(true);
        bModificar.setEnabled(true);
		
		bActualizar.setEnabled(false);
        bCancelar.setEnabled(false);
	}
	
	private void desplegar(String datos)
	{
		StringTokenizer st = new StringTokenizer(datos,"_");
		
		tfClave.setText(st.nextToken());
		tfNombre.setText(st.nextToken());
		tfDireccion.setText(st.nextToken());
		tfTelefono.setText(st.nextToken());
		tfEdoCivil.setText(st.nextToken());
	}
	
	private String obtenerDatos()
	{
		String datos;
		String clave, nombre, direccion, telefono, edoCivil;
		
		clave = tfClave.getText();
		nombre = tfNombre.getText();
		direccion = tfDireccion.getText();
		telefono = tfTelefono.getText();
		edoCivil = tfEdoCivil.getText();
		
		if(clave.equals("") || nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || edoCivil.equals(""))
		{
			datos = "VACIO";
		}
		else
		{
			datos = clave+"_"+nombre+"_"+direccion+"_"+telefono+"_"+edoCivil;
		}
		
		return datos;
	}
	
	public void actionPerformed(ActionEvent event)
	{
		String datos="";
		String respuesta="";
		
		if(event.getSource() == bCapturar)
		{
			// 1. Obtener los datos de los JTextFields
			datos = obtenerDatos();
			
			// 2. Checar los datos
			if(datos.equals("VACIO"))
				respuesta = "Algun campo esta vacio...";
			else
				respuesta = bancoad.capturar("Cliente",datos);
				
			// 3. Desplegar resultado de la transaccion
			taDatos.setText(respuesta);
		}
		
		if(event.getSource() == bConsNombre)
		{
			// 1. Consultar los datos del archivo
			datos = bancoad.consultarCliente();
			
			// 2. Desplegar los datos del cliente
			taDatos.setText(datos);
		}
		
		
		if(event.getSource() == bConsClave)
		{
			// 1. Obtener la clave del JTextField
			String clave = tfClave.getText();
			
			// 2. Realizar la transaccion Consultar por Clave
			datos = bancoad.consultarClave(clave);
			
			// 2. Desplegar los datos
			taDatos.setText(datos);
		}

		if(event.getSource() == bModificar)
		{
			// 1. Obtener la clave del JTextField
			String clave = tfClave.getText();
			
			// 2. Realizar la transaccion Consultar por Clave
			datos = bancoad.consultarClave(clave);
			
			// Checar los datos
			if(datos.equals("NOT_FOUND"))
				taDatos.setText("No hay un cliente con la clave: "+clave);
			else
			{
				desplegar(datos);
				inactivarBotones();
				tfClave.setEditable(false);
			}
		}
		
		if(event.getSource() == bActualizar)
		{
			// 1. Obtener los datos de los JTextFields
			datos = obtenerDatos();
			String tabla = "Cliente";
			
			// 2. Checar los datos
			if(datos.equals("VACIO"))
				respuesta = "Algun campo esta vacio...";
			else
			{
				respuesta = bancoad.actualizar(tabla,datos);
				
				activarBotones();
				tfClave.setEditable(true);
			}
				
			// 3. Desplegar resultado de la transaccion
			taDatos.setText(respuesta);
		}
		
		if(event.getSource() == bCancelar)
		{
			activarBotones();
			tfClave.setEditable(true);
		}
	}
	
	public static void main(String args[])
	{
		new ClienteGUI();
	}
}
