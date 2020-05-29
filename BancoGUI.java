
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class BancoGUI extends JFrame implements ActionListener
{
	/** Atributos del Menu de Barras Principal, Menus y MenuItems **/
	private JMenuBar  menuBarCatalogos;
	private JMenu     menuCatalogos, menuReportes;
	private JMenuItem miCuenta, miCliente;
	private JMenuItem miSalir;
	
		
	/** Objeto Model **/
	private BancoADjdbc bancoad = new BancoADjdbc();
	
	private ClienteGUI   cliente   = new ClienteGUI();
	private CuentaGUI 	 cuenta    = new CuentaGUI();
	
	
	private JPanel panel;
	
	public BancoGUI()
	{
		super("Sistema de BD Banco");
		
		// 1. Creacion de Objetos
		menuBarCatalogos = new JMenuBar();
		menuCatalogos    = new JMenu("Gestion Sistema BD");

				
		miCuenta   = new JMenuItem("Cuentas");
		miCliente = new JMenuItem("Clientes");
		miSalir    = new JMenuItem("Salir");


		// 2. Adicionar deteccion de eventos a los JMenuItems
		miCuenta.addActionListener(this);
		miCliente.addActionListener(this);
		miSalir.addActionListener(this);


		panel = new JPanel();

		// 3. Adicionar los objetos a los JMenu 

		
		menuCatalogos.add(miCuenta);
		menuCatalogos.add(miCliente);

		menuCatalogos.add(miSalir);
		
	
		
		// 4. Adicionar menuCatalogos y menuReportes a menuBarCatalogos
		menuBarCatalogos.add(menuCatalogos);		
		setJMenuBar(menuBarCatalogos);
		setSize(500,550);
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		System.out.println("Ejecute el constructor...\n");
	}
	
		public void actionPerformed(ActionEvent e)
	{	
		if (e.getSource() == miCuenta)
		{
			//JOptionPane.showMessageDialog(null,"Tabla Cuenta...");
			panel.setVisible(false);
			
			panel = cuenta.getPanel2();
			panel.setVisible(true);
			
			add(panel);
			setVisible(true);
		}
		
		if (e.getSource() == miCliente)
		{
			//JOptionPane.showMessageDialog(null,"Tabla Clientes");
			panel.setVisible(false);
			panel = cliente.getPanel2();
			panel.setVisible(true);
			
			add(panel);
			setVisible(true);
		}
		
				
		if (e.getSource() == miSalir)
			System.exit(0);
		
		
	}

	public static void main(String[] args)
		{
			new BancoGUI();
		}	
		
	
}