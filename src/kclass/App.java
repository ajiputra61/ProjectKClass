/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package kclass;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Aji Putra Pamungkas
 */
public class App {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        new App().initialize();
    }

    private void initialize() {
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            System.out.println("Error UI Manager: " + ex);
        }
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LayoutLogin frame = new LayoutLogin();
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
            }
        });
      
    }
    
}
