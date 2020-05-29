
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.StringTokenizer;

public class CuentaGUI extends JFrame implements ActionListener
{
    // Atributos de la Clase CuentaGUI
	private JTextField tfNocta, tfTipo, tfSaldo, tfFecha, tfCveCliente;
	
	private JButton  bCapturar, bConsultar, bConsTipo, bConsClave, bConsFecha, bConsultarCta, bConsNocta, bCancelar, bRetiro, bDeposito;
	
	private JTextArea taDatos;

	private JPanel   panel1;
	private JPanel   panel2;
	
	//private HospitalAD hospitalad = new HospitalAD();
	private BancoADjdbc bancoad = new BancoADjdbc();
	
    // Constructor de la clase DoctorGUI
	public CuentaGUI()
	{
		super("Cuentas");
        
        // 1. Crear objetos de los atributos
        //JTextFieldsc
        tfNocta        = new JTextField();
        tfTipo       = new JTextField();
        tfSaldo    = new JTextField();
        tfFecha     = new JTextField();
        tfCveCliente  = new JTextField();
        
        // JButtons
        bCapturar     = new JButton("Capturar Datos");
        bConsultar    = new JButton("Consultar cuentas");
        bConsTipo    = new JButton("Consultar por tipo de cuenta");
        bConsClave    = new JButton("Consultar por clave cliente");
        bConsFecha    = new JButton("Consultar por fecha");
        bConsultarCta	  = new JButton("Consultar por cuenta");
        bCancelar     = new JButton("Cancelar transaccion");
        bRetiro		  = new JButton("Realizar un retiro");
        bDeposito	  = new JButton("Realizar un depósito");
        
        bRetiro.setEnabled(false);
        bDeposito.setEnabled(false);
        bCancelar.setEnabled(false);

		// Poner addActionListener a los JButtons
		bCapturar.addActionListener(this);
		bConsultar.addActionListener(this);
		bConsTipo.addActionListener(this);
		bConsClave.addActionListener(this);
		bConsFecha.addActionListener(this);
		bConsultarCta.addActionListener(this);
		bCancelar.addActionListener(this);
		bRetiro.addActionListener(this);
		bDeposito.addActionListener(this);
		
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
		panel1.add(tfNocta);
		
		panel1.add(new JLabel("Tipo: "));
		panel1.add(tfTipo);
		
		panel1.add(new JLabel("Saldo: "));
		panel1.add(tfSaldo);
		
		panel1.add(new JLabel("Fecha: "));
		panel1.add(tfFecha);
		
		panel1.add(new JLabel("Clave del cliente: "));
		panel1.add(tfCveCliente);
		
		
        // JButtons
		panel1.add(bCapturar);
		panel1.add(bConsultar);
		panel1.add(bConsTipo);
		panel1.add(bConsClave);
		panel1.add(bConsFecha);
		panel1.add(bConsultarCta);
		panel1.add(bRetiro);
		panel1.add(bDeposito);
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
	
	public JPanel getPanel2()
	{
		return this.panel2;
	}
	
	private void inactivarBotones()
	{		
		bCapturar.setEnabled(false);
        bConsultar.setEnabled(false);
        bConsTipo.setEnabled(false);
        bConsClave.setEnabled(false);
        bConsFecha.setEnabled(false);
        bConsultarCta.setEnabled(false);
        
        bRetiro.setEnabled(true);
        bDeposito.setEnabled(true);
        bCancelar.setEnabled(true);
	}
	
	private void activarBotones()
	{		
		bCapturar.setEnabled(true);
        bConsultar.setEnabled(true);
        bConsTipo.setEnabled(true);
        bConsClave.setEnabled(true);
        bConsFecha.setEnabled(true);
        bConsultarCta.setEnabled(true);
        
		bRetiro.setEnabled(false);
        bDeposito.setEnabled(false);
        bCancelar.setEnabled(false);
	}
	
	private void desplegar(String datos)
	{
		StringTokenizer st = new StringTokenizer(datos,"_");
		
		tfNocta.setText(st.nextToken());
		tfTipo.setText(st.nextToken());
		tfSaldo.setText(st.nextToken());
		tfFecha.setText(st.nextToken());
		tfCveCliente.setText(st.nextToken());
	}
	
	private String obtenerDatos()
	{
		String datos;
		String nocta, tipo, saldo, fecha, cveC;
		
		nocta = tfNocta.getText();
		tipo = tfTipo.getText();
		saldo = tfSaldo.getText();
		fecha = tfFecha.getText();
		cveC = tfCveCliente.getText();
		
		if(nocta.equals("") || tipo.isEmpty() || saldo.isEmpty() || fecha.isEmpty() || cveC.equals(""))
		{
			datos = "VACIO";
		}
		else
		{
			datos = nocta+"_"+tipo+"_"+saldo+"_"+fecha+"_"+cveC;
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
				respuesta = bancoad.capturar("Cuenta",datos);
				
			// 3. Desplegar resultado de la transaccion
			taDatos.setText(respuesta);
		}
		
		if(event.getSource() == bConsultar)
		{
			// 1. Consultar los datos del archivo
			datos = bancoad.consultarCuentas();
			
			// 2. Desplegar los datos del número de cuenta
			taDatos.setText(datos);
		}
		
		if(event.getSource() == bConsTipo)
		{
			String tipo =tfTipo.getText();	

			datos = bancoad.consultarTipo(tipo);
			
			taDatos.setText(datos);
		}
		
		if(event.getSource() == bConsClave)
		{
			// 1. Obtener la clave del JTextField
			String clave = tfCveCliente.getText();
			
			// 2. Realizar la transaccion Consultar por Clave
			datos = bancoad.consultarCuentapC(clave);
			
			// 2. Desplegar los datos
			taDatos.setText(datos);
		}
		
		if(event.getSource() == bConsFecha)
		{
			// 1. Obtener la clave del JTextField
			String fecha = tfFecha.getText();
			
			// 2. Realizar la transaccion Consultar por Clave
			datos = bancoad.consultarFecha(fecha);
			
			// 2. Desplegar los datos
			taDatos.setText(datos);
		}
		
		if(event.getSource() == bConsultarCta)
		{
			// 1. Obtener la clave del JTextField
			String ncta = tfNocta.getText();
			
			// 2. Realizar la transaccion Consultar por Clave
			datos = bancoad.consultarCuenta(ncta);
			
			// Checar los datos
			if(datos.equals("NOT_FOUND"))
				taDatos.setText("No hay cuenta con este número: "+ncta);
			else
			{
				desplegar(datos);
				inactivarBotones();
				tfNocta.setEditable(false);
				taDatos.setText(datos);
			}
		}
		if (event.getSource() == bRetiro)
		{
			JOptionPane.showMessageDialog(null,"Reporte Pacientes de un Doctor...");
	
			
			/*taDatosReportes.setText("REPORTE: PACIENTES DE UN DOCTOR\n\n");
			
			String cved = JOptionPane.showInputDialog("Clave del Doctor:");
			
			String datos = hospitalad.reporteDoctorPaciente(cved);
			
			taDatosReportes.append(datos);*/
		}
		if (event.getSource() == bDeposito)
		{
			//JOptionPane.showMessageDialog(null,"Reporte Pacientes de un Doctor...");
			/*panel.setVisible(false);
			panelReportes.setVisible(true);	
			
			add(panelReportes);
			setVisible(true);
			
			taDatosReportes.setText("REPORTE: PACIENTES DE UN DOCTOR\n\n");
			
			String cved = JOptionPane.showInputDialog("Clave del Doctor:");
			
			String datos = hospitalad.reporteDoctorPaciente(cved);
			
			taDatosReportes.append(datos);*/
		}
		
		if(event.getSource() == bCancelar)
		{
			activarBotones();
			tfNocta.setEditable(true);
		}
	}
	
	public static void main(String args[])
	{
		new CuentaGUI();
	}
}
