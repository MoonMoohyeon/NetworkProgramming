package problem;

import java.io.PrintWriter;

public class HTTPRequestController {
	
	private String host;
	
	public HTTPRequestController(String serverURL) {
		this.host=serverURL;
	}
	
	/*
	 * 필수 헤더
	 * HTTP 메서드, 버전 정보 
	 * Host 정보
	 * requestBody가 있는 경우 Content-Type, Content-Length
	 */
	
	// POST /login API
	public void setLoginRequest(PrintWriter pw,String requestBody){
		pw.println("POST /login HTTP/1.1");
		pw.println("Host: "+host);
		pw.println("Content-Type : application/json; charset=utf-8");
		pw.println("Content-Length :"+requestBody.getBytes().length);
		pw.println();
		pw.println(requestBody);
//		System.out.println("http req = " +"POST /login HTTP/1.1"+"Host: "+host+"Content-Type : application/json; charset=utf-8"
//				+"Content-Length :"+requestBody.getBytes().length+"\n"+requestBody);
		pw.flush();
	}
	
	public void setGetRequest(PrintWriter pw,String requestParam){
		//TODO : 상품 조회를 요청하는 GET /{학번} API Request를  필수헤더를 포함하여 구성하시오
		pw.println("GET /" + requestParam + " HTTP/1.1");
		pw.println("Host: " + host);
		pw.println();
		pw.flush();
	}
	
	
	public void setPostRequest(PrintWriter pw,String requestBody){
		//TODO : 상품 추가를 요청하는 POST / API Request를 필수헤더를 포함하여 구성하시오
		pw.println("POST / HTTP/1.1");
		pw.println("Host: " + host);
		pw.println("Content-Type: application/json; charset=utf-8");
		pw.println("Content-Length: " + requestBody.getBytes().length);
		pw.println();
		pw.println(requestBody);
		pw.flush();
	}
	
	
	public void setPutRequest(PrintWriter pw,String requestBody){
		// TODO : 상품 수정을 요청하는 PUT / API Request를 필수헤더를 포함하여 구성하시오
		pw.println("PUT / HTTP/1.1");
		pw.println("Host: " + host);
		pw.println("Content-Type: application/json; charset=utf-8");
		pw.println("Content-Length: " + requestBody.getBytes().length);
		pw.println();
		pw.println(requestBody);
		pw.flush();
	}
	
	public void setPatchRequest(PrintWriter pw,String requestBody){
		// TODO : 상품 수정을 요청하는  PATCH / API Request를 필수헤더를 포함하여 구성하시오
		pw.println("PATCH / HTTP/1.1");
		pw.println("Host: " + host);
		pw.println("Content-Type: application/json; charset=utf-8");
		pw.println("Content-Length: " + requestBody.getBytes().length);
		pw.println();
		pw.println(requestBody);
		pw.flush();
	}
	
	
	public void setDeleteRequest(PrintWriter pw,String requestParam){
		//TODO : 상품 삭제를 요청하는 DELETE /{상품id} API Request를 필수헤더를 포함하여 구성하시오
		pw.println("DELETE /" + requestParam + " HTTP/1.1");
		pw.println("Host: " + host);
		pw.println();
		pw.flush();
	}
}
