package problem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MyClientController {

	private ProductSystemUI v;
	private Gson gson;
	private BufferedReader br;
	private PrintWriter pw;
	private HTTPRequestController rc;

//	private String serverURL="203.252.148.148";
	private int port = 80;
//	private String serverURL = "127.0.0.1";
private String serverURL = "localhost";

	public MyClientController(ProductSystemUI v) {
		this.v = v;
		this.gson = new Gson();
		this.rc = new HTTPRequestController(serverURL);
	}

	public void login() {
		Socket socket = null;
		try {
			socket = new Socket(serverURL, port);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			pw = new PrintWriter(socket.getOutputStream());

			// request header 세팅
			rc.setLoginRequest(pw, gson.toJson(new User(v.idInput.getText())));
			socket.shutdownOutput();

			v.model.setRowCount(0);
			String line = null;
			StringBuilder s = new StringBuilder();

			while ((line = br.readLine()) != null) {
				s.append(line + "\r\n");
//				System.out.println("line = " + line);
			}

			String response = s.toString();
			System.out.println("login res = " + response);

			// 올바른 Response일 때만 로그인 이벤트 처리
			if (response.indexOf("HTTP/") != -1) {
				if (response.indexOf("200 OK") != -1) {
					String tempId = v.idInput.getText();
					v.cardLayout.show(v.tab, "main");
					v.loginInfo.setText(tempId);
					getProducts();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();

				if (pw != null)
					pw.close();

				if (socket != null)
					socket.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public void getProducts() {
		Socket socket = null;
		try {
			socket = new Socket(serverURL, port);

			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			pw = new PrintWriter(socket.getOutputStream());

			// request header 세팅
			rc.setGetRequest(pw, v.idInput.getText());
			socket.shutdownOutput();

			// TODO : Response 내용을 받아오는 코드를 작성하시오
			StringBuilder responseStringBuilder = new StringBuilder();
			String line;

			while ((line = br.readLine()) != null) {
				responseStringBuilder.append(line).append("\r\n");
			}

			String responseBody = responseStringBuilder.toString();
			System.out.println("get res = " + responseBody);

			// TODO : 올바른 Response 일 때 상품 조회 이벤트를 처리하시오
			v.model.setRowCount(0);
			// 참고 : ResponseBody 받아오는 방법 -> Response에서 "\r\n\r\n"를 구분자로 split하여 받아옴
			if (responseBody.indexOf("HTTP/") != -1 && responseBody.indexOf("200 OK") != -1) {
				String[] responseParts = responseBody.split("\r\n\r\n");
//				System.out.println("respart = " + responseParts[1]);
				String[] jsonObjects = responseParts[1].split("\r\n");
//				System.out.println("json = " + jsonObjects[0]);
				for (String jsonObject : jsonObjects) {
//					jsonObject = jsonObject + "}";
					System.out.println("jsonobj = " + jsonObject);
//					System.out.println("index = " + jsonObject.charAt(0));
					Gson gson = new GsonBuilder().create();
					Product product = gson.fromJson(jsonObject, Product.class);
//					System.out.println("Order ID: " + product.getOrderId());
//					System.out.println("Name: " + product.getName());
//					System.out.println("Status: " + product.getStatus());
//					System.out.println("Created At: " + product.getCreatedAt());
					Object[] rowData = {product.getOrderId(), product.getName(), product.getStatus(), product.getCreatedAt()};
					v.model.addRow(rowData);
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (pw != null)
					pw.close();

				if (socket != null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	public void addProducts() {
		Socket socket = null;
		try {
			socket = new Socket(serverURL, port);

			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			pw = new PrintWriter(socket.getOutputStream());

			//request header 세팅
			rc.setPostRequest(pw, gson.toJson(new Product(v.idInput.getText(), v.nameInput.getText())));
			socket.shutdownOutput();

			// TODO : Response 내용을 받아오는 코드를 작성하시오
			StringBuilder responseStringBuilder = new StringBuilder();
			String line;

			while ((line = br.readLine()) != null) {
				responseStringBuilder.append(line).append("\r\n");
			}

			String responseBody = responseStringBuilder.toString();
			System.out.println("add res = " + responseBody);

			// TODO : 올바른 Response일 때 상품을 새로고침 하시오
			if (responseBody.indexOf("HTTP/") != -1 && responseBody.indexOf("201 Created") != -1) {
				getProducts();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();

				if (pw != null)
					pw.close();

				if (socket != null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateProduct() {
		Socket socket = null;
		try {
			socket = new Socket(serverURL, port);

			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			pw = new PrintWriter(socket.getOutputStream());

			//request header 설정 (PUT이 안될 경우 PATCH 사용)
			rc.setPatchRequest(pw, gson.toJson(
					new Product(Long.parseLong(v.txt1.getText().toString()), v.txt2.getText(), v.txt3.getText())));
			// rc.setPutRequest(pw, gson.toJson(
			// new Product(Long.parseLong(v.txt1.getText().toString()), v.txt2.getText(),v.txt3.getText())));
			
			socket.shutdownOutput();

			// TODO : Response 내용을 받아오는 코드를 작성하시오
			StringBuilder responseStringBuilder = new StringBuilder();
			String line;

			while ((line = br.readLine()) != null) {
				responseStringBuilder.append(line).append("\r\n");
			}

			String responseBody = responseStringBuilder.toString();
			System.out.println("update res = " + responseBody);

			// TODO : 올바른 Response일 때 상품을 새로고침 하는 코드를 작성하시오
			if (responseBody.indexOf("HTTP/") != -1 && responseBody.indexOf("200 OK") != -1) {
				getProducts();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();

				if (pw != null)
					pw.close();

				if (socket != null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void deleteProduct() {
		Socket socket = null;
		try {
			socket = new Socket(serverURL, port);

			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			pw = new PrintWriter(socket.getOutputStream());

			//request header 설정
			rc.setDeleteRequest(pw, v.txt1.getText());
			socket.shutdownOutput();

			// TODO : Response 내용을 받아오는 코드를 작성하시오
			StringBuilder responseStringBuilder = new StringBuilder();
			String line;

			while ((line = br.readLine()) != null) {
				responseStringBuilder.append(line).append("\r\n");
			}

			String responseBody = responseStringBuilder.toString();
			System.out.println("delete res = " + responseBody);
	
			// TODO : 올바른 Response일 때 상품을 새로고침 하는 코드를 작성하시오
			if (responseBody.indexOf("HTTP/") != -1 && responseBody.indexOf("200 OK") != -1) {
				getProducts();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();

				if (pw != null)
					pw.close();

				if (socket != null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void appMain() {
		v.addButtonActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				if (obj == v.loginButton) {
					// 로그인
					login();
				} else if (obj == v.exitButton) {
					System.exit(0);
				} else if (obj == v.addButton) {
					// 상품 추가
					addProducts();
					v.nameInput.setText("");
				} else if (obj == v.updateButton) {
					// 상품 업데이트
					updateProduct();
				} else if (obj == v.deleteButton) {
					// 상품 삭제
					deleteProduct();
					//TODO : STEP1 이벤트 추가하기
				}
			}
		});
	}

	public static void main(String[] args) {
		MyClientController mc = new MyClientController(new ProductSystemUI());
		mc.appMain();
	}
}
