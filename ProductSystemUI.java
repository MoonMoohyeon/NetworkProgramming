package problem;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ProductSystemUI extends JFrame {

	// 로그인 패널
	private JPanel loginPanel;
	protected JTextField idInput;
	private JLabel idLabel;
	protected JButton loginButton;

	// 로그인 후 보여지는 topPanel
	private JPanel topPanel;
	protected JButton addButton, exitButton;
	protected JTextField loginInfo;
	private JLabel loginInfoLabel;

	// 화면 구성 전환을 위한 카드레이아웃
	protected Container tab;
	protected CardLayout cardLayout;

	// 아래 패널
	private JPanel bottomPanel;
	protected JButton updateButton, deleteButton;
	protected JTextField txt1, txt2, txt3, txt4;

	// 중단 패널의 테이블 관련 요소
	private String colHeaders[] = { "주문 번호", "상품명", "배송 상태", "생성 시간" };
	protected DefaultTableModel model = new DefaultTableModel(colHeaders, 0) {
		@Override
		public boolean isCellEditable(int row, int column) {
			// 직접 수정 불가능하게
			return false;
		}
	};

	private JTable table;
	private JScrollPane scrollPane;

	// 추가 라벨
	private JLabel nameLabel;

	// 상품명 입력 텍스트필드
	protected JTextField nameInput;

	// 생성자
	public ProductSystemUI() {
		// 메인 프레임 구성
		super("배송 관리 시스템");
		Container main = this.getContentPane();
		table = new JTable(model);
		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();

				// 테이블의 모델객체 얻어오기
				TableModel data = table.getModel();
				String id = data.getValueAt(row, 0).toString();
				String name = data.getValueAt(row, 1).toString();
				String status = data.getValueAt(row, 2).toString();
				String created_at = data.getValueAt(row, 3).toString();

				//TODO : 테이블 한 로우 선택 시 하단 패널의 텍스트 박스를 선택한 로우의 내용으로 채우는 코드를 작성하시오
				txt1.setText(id);
				txt2.setText(name);
				txt3.setText(status);
				txt4.setText(created_at);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

		});

		// 로그인 패널 구성
		loginPanel = new JPanel();

		idLabel = new JLabel("관리자 인증번호 : ");
		idInput = new JTextField(10);
		loginButton = new JButton("로그인");

		loginPanel.add(idLabel);
		loginPanel.add(idInput);
		loginPanel.add(loginButton);

		// topPanel 구성
		topPanel = new JPanel();

		loginInfoLabel = new JLabel("관리자 인증번호 :");
		loginInfo = new JTextField(10);
		loginInfo.setEditable(false);

		nameLabel = new JLabel("상품 명 ");
		nameInput = new JTextField(10);
		addButton = new JButton("상품 추가");
		exitButton = new JButton("나가기");

		topPanel.add(loginInfoLabel);
		topPanel.add(loginInfo);
		topPanel.add(nameLabel);
		topPanel.add(nameInput);
		topPanel.add(addButton);
		topPanel.add(exitButton);

		// 상단 패널 화면 전환 구성
		cardLayout = new CardLayout();
		tab = new JPanel();
		tab.setLayout(cardLayout);
		tab.add(loginPanel, "login");
		tab.add(topPanel, "main");

		// 중단 패널 구성
		scrollPane = new JScrollPane(table);
		scrollPane.setSize(500, 300);

		// 하단 패널 구성
		bottomPanel = new JPanel();

		txt1 = new JTextField(10);
		txt2 = new JTextField(10);
		txt3 = new JTextField(10);
		txt4 = new JTextField(12);

		txt1.setEditable(false);
		txt4.setEditable(false);

		updateButton = new JButton("수정");
		deleteButton = new JButton("삭제");

		bottomPanel.add(txt1);
		bottomPanel.add(txt2);
		bottomPanel.add(txt3);
		bottomPanel.add(txt4);

		bottomPanel.add(updateButton);
		bottomPanel.add(deleteButton);

		// 레이아웃 배치
		main.add(tab, BorderLayout.NORTH);
		main.add(scrollPane, BorderLayout.CENTER);
		main.add(bottomPanel, BorderLayout.SOUTH);

		// 프레임 크기 자동으로 설정
		pack();

		// 프레임 크기 조정 불가 설정
		setResizable(false);

		// 프레임이 보여지도록 함
		setVisible(true);

		// 닫기시 종료되도록
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	/**
	 * 이벤트 리스너 등록을 위한 메서드로 파라미터의 리스너 객체는 컨트롤러에서 구현한 객체가 됨. 따라서 실제 이벤트 처리는 컨트롤러 클래스
	 * 코드를 따라감.
	 * 
	 * @param listener
	 */
	public void addButtonActionListener(ActionListener listener) {
		// 이벤트 리스너 등록
		addButton.addActionListener(listener);
		loginButton.addActionListener(listener);
		updateButton.addActionListener(listener);
		deleteButton.addActionListener(listener);
		exitButton.addActionListener(listener);
	}

	/// * this main() method is here only for testing UI, and should be deleted or
	/// commented out later
//	public static void main(String[] args) {
//
//		ProductSystemUI v = new ProductSystemUI();
//
//		v.addButtonActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				Object obj = e.getSource();
//
//				if (obj == v.loginButton) {
//					if (obj == v.loginButton) {
//						// TODO: 로그인 버튼 클릭 시 화면전환을 구현하시오
//						v.cardLayout.show(v.tab, "main");
//						v.loginInfo.setText(v.idInput.getText());
//
//						// 테스트를 위한 임시 로우 추가
//						v.model.addRow(new Object[]{"123", "temp", "temp", "temp"});
//					}
//
//					//테스트를 위한 임시 로우 추가
//					v.model.addRow(new Object[] { "123", "temp", "temp", "temp" });
//				} else if (obj == v.exitButton) {
//					System.exit(0);
//				} else if (obj == v.addButton) {
//					v.nameInput.setText("");
//				} else if (obj == v.updateButton) {
//					// TODO : 수정 버튼 클릭 시 하단 패널의 텍스트 박스 비우기를 구현하시오
//					v.txt1.setText("");
//					v.txt2.setText("");
//					v.txt3.setText("");
//					v.txt4.setText("");
//
//				} else if (obj == v.deleteButton) {
//					// TODO : 삭제 버튼 클릭 시 하단 패널의 텍스트 박스 비우기를 구현하시오
//					v.txt1.setText("");
//					v.txt2.setText("");
//					v.txt3.setText("");
//					v.txt4.setText("");
//				}
//			}
//		});
//	} // delete or comment out this method when working with MyClientController*/

}
