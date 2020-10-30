
import dao.ProductDAO;
import dao.ProductJdbcDAO;
import gui.MainMenu;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author petja122
 */
public class Main {

	/**
	 * @param args the command line arguments
	 */
	
	

	public static void main(String[] args){
                ProductJdbcDAO productJdbcDao = new ProductJdbcDAO();
		//create main menu
		MainMenu frame = new MainMenu(productJdbcDao); // create the frame instance
		frame.setLocationRelativeTo(null); // centre the frame on the screen
		frame.setVisible(true); // show the frame
		
		
	}
	
}
