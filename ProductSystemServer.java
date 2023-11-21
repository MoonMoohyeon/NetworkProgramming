package problem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.google.gson.Gson;

public class ProductSystemServer {
	private static final Logger logger = Logger.getLogger("MyHTTPServer");
	private final int port;
	private final ProductService service;
	private Gson gson;

	public ProductSystemServer(int port) {
		this.port = port;
		this.gson = new Gson();
		this.service = new ProductService();
	}

	public void start() {
		ExecutorService pool = Executors.newFixedThreadPool(10);
		try (ServerSocket server = new ServerSocket(this.port)) {
			logger.info("Accepting connections on port " + server.getLocalPort());
			while (true) {
				try {
					System.out.println("server start");
					//TODO : 요청이 올때마다 Thread 생성하고 Handling 하시오
					Socket connection = server.accept();
					pool.submit(new HTTPHandler(connection));
				} catch (IOException ex) {
					logger.log(Level.WARNING, "Exception accepting connection", ex);
				} catch (RuntimeException ex) {
					logger.log(Level.SEVERE, "Unexpected error", ex);
				}
			}
		} catch (IOException ex) {
			logger.log(Level.SEVERE, "Could not start server", ex);
		}
	}

	private class HTTPHandler implements Callable<Void> {
		private final Socket connection;
		private PrintWriter out;
		private BufferedReader in;
		
		HTTPHandler(Socket connection) {
			this.connection = connection;
		}

		public Void call() throws IOException {
			try {
				System.out.println("call called");
				//TODO: PrintWrite 객체 this.out, BufferedReader 객체 this.in을 생성하는 코드를 작성하시오. BufferedReader의 인코딩은 UTF-8으로 설정
				this.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8")), true);
				this.in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

				StringBuilder s=new StringBuilder();
				String tmp;
				String request;
				
				while ((tmp = in.readLine()) != null) {
					s.append(tmp+"\r\n");
				}
				
				request = s.toString();
				
				//HTTP 메서드에 따라 분기처리를 하는 파트
				if (request.indexOf("HTTP/") != -1) {
					if(request.indexOf("POST /login")!=-1) {
						String requestBody = request.split("\r\n\r\n")[1];
						System.out.println("Server = " + requestBody);
						service.login(out,requestBody);
					} else if (request.indexOf("GET /") != -1) {
						String requestParam = request.toString().split(" ")[1].replace("/", "");
						service.getProducts(out,requestParam);
					} else if (request.indexOf("POST /") != -1) {
						String requestBody = request.split("\r\n\r\n")[1];
						service.addProduct(out, requestBody);
					} else if (request.indexOf("PATCH /") != -1) {
						String requestBody = request.split("\r\n\r\n")[1];
						service.updateProduct(out, requestBody);
					} else if (request.indexOf("DELETE /") != -1) {
						String requestParam = request.split(" ")[1].replace("/", "");
						service.deleteProduct(out, requestParam);
					} else {
						service.setDefaultResponse(out);
					}
					//TODO : POST, PATCH(또는 PUT), DELETE API에 대한 분기처리를 작성하시오
				}
			} catch (IOException ex) {
				logger.log(Level.WARNING, "Error writing to client", ex);
				System.err.println(ex);
			} finally {
				in.close();
				out.close();
				connection.close();
			}
			return null;
		}


	}

	public static void main(String[] args) throws IOException {
		try {
			int port = 80;
			ProductSystemServer server = new ProductSystemServer(port);
			server.service.connectDB();
			server.start();
		} catch (RuntimeException ex) {
			System.out.println("run time error");
		}
	}
}
