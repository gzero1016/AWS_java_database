package frame;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import entity.ProductCategory;
import service.ProductCategoryService;
import utils.CustomSwingTextUtil;

public class ProductCategoryRegisterFrame extends JFrame {

	private JPanel contentPane;
	private JTextField productCategoryNameTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductCategoryRegisterFrame frame = new ProductCategoryRegisterFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ProductCategoryRegisterFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 208);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titleLabel = new JLabel("상품 카테고리 등록");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(12, 10, 410, 51);
		contentPane.add(titleLabel);
		
		JLabel productCategoryNameLabel = new JLabel("카테고리명");
		productCategoryNameLabel.setBounds(12, 71, 67, 15);
		contentPane.add(productCategoryNameLabel);
		
		productCategoryNameTextField = new JTextField();
		productCategoryNameTextField.setBounds(81, 65, 341, 27);
		contentPane.add(productCategoryNameTextField);
		productCategoryNameTextField.setColumns(10);
		
		JButton registerSubmitButton = new JButton("등록하기");
		registerSubmitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String productCategoryName = productCategoryNameTextField.getText();
				if(CustomSwingTextUtil.isTextEmpty(contentPane, productCategoryName)) { return; }
				if(ProductCategoryService.getInstance().isProductCategoryNameDuplicated(productCategoryName)) {
					JOptionPane.showMessageDialog(contentPane, "이미 존재하는 카테고리명입니다.", "중복오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				ProductCategory productCategory = ProductCategory.builder()
						.productCategoryName(productCategoryName)
						.build();
				
				if(!ProductCategoryService.getInstance().registerProductCategory(productCategory)) {
					JOptionPane.showMessageDialog(contentPane, "카테고리등록 중 오류가 발생하였습니다.", "등록오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				JOptionPane.showMessageDialog(contentPane, "새로운 카테고리를 등록하였습니다.", "등록성공", JOptionPane.PLAIN_MESSAGE);
				CustomSwingTextUtil.clearTextField(productCategoryNameTextField);

			}
		});
		registerSubmitButton.setBounds(12, 96, 410, 56);
		contentPane.add(registerSubmitButton);
	}
	
}