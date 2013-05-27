package com.five.onair.server.utils;

import java.awt.Component;

import javax.swing.JOptionPane;

public class UserOutUtils {

	public static void showPopUP(String message,Component c){
		JOptionPane.showMessageDialog(c, message,"Messagem",JOptionPane.DEFAULT_OPTION);
	}
	
}
