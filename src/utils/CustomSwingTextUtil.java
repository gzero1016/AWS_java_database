package utils;

import java.awt.Component;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class CustomSwingTextUtil {

	public static boolean isTextEmpty(Component targetComponent, String str) {
		if(str != null) {
			if(!str.isBlank()) {
				return false;
			}
		}
		
		JOptionPane.showMessageDialog(targetComponent, "내용을 입력하세요", "입력오류", JOptionPane.ERROR_MESSAGE);
		return true;
	}
	
	public static void clearTextField(JTextField textField) {
		textField.setText("");
	}
}
