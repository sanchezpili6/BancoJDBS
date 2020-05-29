
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

public class RetiroGUI extends JFrame implements ActionListener
{
    // Atributos de la Clase DepositoGUI
	private JTextField tfNumcta, tfTipo, tfSaldo, tfCantidad, tfSaldoNuevo, tfFecha;
	
	private JButton  bCapturar, bConsNumcta, bConsTipo, bModificar, bActualizar, bCancelar;
	
	private JTextArea taDatos;

	private JPanel   panel1;
	private JPanel   panel2;
	
	//private HospitalAD hospitalad = new HospitalAD();
	private BancoADjdbc bancoad = new BancoADjdbc();
	
    // Constructor de la clase DoctorGUI
	public RetiroGUI()
	{
		super("Retiros");
        
        // 1. Crear objetos de los atributos
        //JTextFieldsc
        tfNumcta        = new JTextField();
        tfTipo       = new JTextField();
        tfSaldo    = new JTextField();
        tfCantidad     = new JTextField();
        tfSaldoNuevo  = new JTextField();
        tfFecha  = new JTextField();
        
        // JButtons
        bCapturar     = new JButton("Capturar Datos");
        bConsNumcta    = new JButton("Consultar número de cuenta");
        bConsTipo    = new JButton("Consultar por tipo de cuenta");
        bModificar	  = new JButton("Modificar Datos");
        bActualizar   = new JButton("Actualizar Datos");
        bCancelar     = new JButton("Cancelar transaccion");
        
        bActualizar.setEnabled(false);
        bCancelar.setEnabled(false);

		// Poner addActionListener a los JButtons
		bCapturar.addActionListener(this);
		bConsNumcta.addActionListener(this);
		bConsTipo.addActionListener(this);
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
		panel1.add(new JLabel("GESTION DE CUENTAS"));
		panel1.add(new JLabel(""));
		
		panel1.add(new JLabel("Número de cuenta: "));
		panel1.add(tfNumcta);
		
		panel1.add(new JLabel("Tipo: "));
		panel1.add(tfTipo);
		
		panel1.add(new JLabel("Saldo: "));
		panel1.add(tfSaldo);
		
		panel1.add(new JLabel("Cantidad: "));
		panel1.add(tfCantidad);
		
		panel1.add(new JLabel("Saldo Nuevo: "));
		panel1.add(tfSaldoNuevo);
		
		panel1.add(new JLabel("Fecha: "));
		panel1.add(tfFecha);
		

		
		
        // JButtons
		panel1.add(bCapturar);
		panel1.add(bConsNumcta);
		panel1.add(bConsTipo);
		panel1.add(bModificar);
		panel1.add(bActualizar);
		panel1.add(bCancelar);
				
        // 4. Adicionar panel1 a panel2
		panel2.add(panel1);
		panel2.add(new JScrollPane(taDatos));
        
        // 5. Adicionar panel2 al JFrame y hacerlo visible
		add(panel2);
		setSize(500,500);
		setVisible(true);
        
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private void inactivarBotones()
	{		
		bCapturar.setEnabled(false);
        bConsNumcta.setEnabled(false);
        bConsTipo.setEnabled(false);
        bModificar.setEnabled(false);
		
		bActualizar.setEnabled(true);
        bCancelar.setEnabled(true);
	}
	
	private void activarBotones()
	{		
		bCapturar.setEnabled(true);
        bConsNumcta.setEnabled(true);
        bConsTipo.setEnabled(true);
        bModificar.setEnabled(true);
		
		bActualizar.setEnabled(false);
        bCancelar.setEnabled(false);
	}
	
	private void desplegar(String datos)
	{
		StringTokenizer st = new StringTokenizer(datos,"_");
		
		tfNumcta.setText(st.nextToken());
		tfTipo.setText(st.nextToken());
		tfSaldo.setText(st.nextToken());
		tfCantidad.setText(st.nextToken());
		tfSaldoNuevo.setText(st.nextToken());
		tfFecha.setText(st.nextToken());
		
	}
	
	private String obtenerDatos()
	{
		String datos;
		String numcta, tipo, saldo, cantidad, saldoNuevo, fecha;
		
		numcta = tfNumcta.getText();
		tipo = tfTipo.getText();
		saldo = tfSaldo.getText();
		cantidad = tfCantidad.getText();
		saldoNuevo = tfSaldoNuevo.getText();
		fecha = tfFecha.getText();
	
		
		if(numcta.equals("") || tipo.isEmpty() || saldo.isEmpty() || fecha.isEmpty() || cantidad.equals("") || saldoNuevo.isEmpty() )
		{
			datos = "VACIO";
		}
		else
		{
			datos = numcta+"_"+tipo+"_"+saldo+"_"+cantidad+"_"+saldoNuevo+"_"+fecha;
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
				respuesta = bancoad.capturar("Retiro",datos);
				
			// 3. Desplegar resultado de la transaccion
			taDatos.setText(respuesta);
		}
		
		if(event.getSource() == bConsNumcta)
		{
			String numcta=tfNumcta.getText();
			String tabla="Retiro";
			// 1. Consultar los datos del archivo
			datos = bancoad.consultarNumcta(tabla,numcta);
			
			// 2. Desplegar los datos del número de cuenta
			taDatos.setText(datos);
		}
		
		if(event.getSource() == bConsTipo)
		{
			String tipo =tfTipo.getText();
			String tabla="retiro";	

			datos = bancoad.consultarTipoDR(tabla,tipo);
			
			taDatos.setText(datos);
		}
		
		if(event.getSource() == bModificar)
		{
			// 1. Obtener la clave del JTextField
			String numcta = tfNumcta.getText();
			String tabla="retiro";
			
			// 2. Realizar la transaccion Consultar por Clave
			datos = bancoad.consultarNumcta(tabla, numcta);
			
			// Checar los datos
			if(datos.equals("NOT_FOUND"))
				taDatos.setText("No existe este número de cuenta: "+numcta);
			else
			{
				desplegar(datos);
				inactivarBotones();
				tfNumcta.setEditable(false);
			}
		}
		
		if(event.getSource() == bActualizar)
		{
			// 1. Obtener los datos de los JTextFields
			datos = obtenerDatos();
			String tabla = "Retiro";
			
			// 2. Checar los datos
			if(datos.equals("VACIO"))
				respuesta = "Algun campo esta vacio...";
			else
			{
				respuesta = bancoad.actualizar(tabla,datos);
				
				activarBotones();
				tfNumcta.setEditable(true);
			}
				
			// 3. Desplegar resultado de la transaccion
			taDatos.setText(respuesta);
		}
		
		if(event.getSource() == bCancelar)
		{
			activarBotones();
			tfNumcta.setEditable(true);
		}
	}
	
	public static void main(String args[])
	{
		new RetiroGUI();
	}
}
