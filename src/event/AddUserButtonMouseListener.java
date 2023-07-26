package event;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

public class AddUserButtonMouseListener extends MouseAdapter{
	
//	MouseAdapter 가 추상클래스이기 때문에 원하는 Clicked만 오버라이딩 가능하다.
	@Override
	public void mouseClicked(MouseEvent e) {
		JOptionPane.showMessageDialog(null, "test", "test2", JOptionPane.PLAIN_MESSAGE);
	}
}
