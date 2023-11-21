package problem;

import java.io.PrintWriter;

public class HTTPResponseController {
	
	//POST /login 성공 Response
	public void setSuccessLoginResponse(PrintWriter out) {
		out.println("HTTP/1.1 200 OK");
		out.println();
		out.flush();
	}
	
	public void setSuccessGetResponse(PrintWriter out,String responseBody) {
		// TODO :  GET /{학번} API의 성공 Response를 필수헤더를 포함하여 구성하시오
		// 참고 : requestBody가 있는 API
		out.println("HTTP/1.1 200 OK");
		out.println("Content-Type: application/json; charset=utf-8");
		out.println();
		out.println(responseBody);
		out.flush();

	}

	public void setSuccessPostResponse(PrintWriter out) {
		//TODO : 상품 추가를 요청하는 POST / API의 성공 Response를 구성하시오
		out.println("HTTP/1.1 201 Created");
		out.println();
		out.flush();
	}

	public void setSuccessPatchResponse(PrintWriter out) {
		// TODO : 상품 수정을 요청하는 PUT / 또는 PATCH / API의 성공 Response를 구성하시오
		out.println("HTTP/1.1 200 OK");
		out.println();
		out.flush();
	}

	public void setSuccessDeleteResponse(PrintWriter out) {
		//TODO : 상품 삭제를 요청하는 DELETE /{상품id} API의 성공 Response를 구성하시오
		out.println("HTTP/1.1 200 OK");
		out.println();
		out.flush();
	}
	
	public void setFailedResponse(PrintWriter out) {
		//TODO : 실패를 나타내는 Response를 구성하시오
		out.println("HTTP/1.1 400 Bad Request");
		out.println();
		out.flush();
	}

}
