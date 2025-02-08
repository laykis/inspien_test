package service;

import com.example.generated.InspienGetRecruitingTestServicesInfo;
import com.example.generated.InspienGetRecruitingTestServicesInfoService;
import com.example.generated.MTRecruitingTestServices;
import com.example.generated.MTRecruitingTestServicesResponse;
import jakarta.xml.ws.BindingProvider;
import util.Decoder;
import util.WSDLUtil;
import java.io.File;
import java.util.Map;


public class SoapClient {

    public static MTRecruitingTestServicesResponse getResponse() throws Exception {

        MTRecruitingTestServicesResponse response = new MTRecruitingTestServicesResponse();

        try {
            SoapClient client = new SoapClient();

            // 요청 데이터 설정
            String name = "김도현";
            String phoneNumber = "010-7470-6471";
            String email = "flash6471@naver.com";

            // 서비스 호출
            response = client.getRecruitingTestService(name, phoneNumber, email);

            return response;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private static final String WSDL_PATH = "src/main/resources/wsdl/InspienGetRecruitingTestServicesInfo.wsdl";
    private static final String SERVICE_NAME = "InspienGetRecruitingTestServicesInfoService";

    private InspienGetRecruitingTestServicesInfo port;

    public SoapClient() throws Exception {
        File wsdlFile = new File(WSDL_PATH);
        if (!wsdlFile.exists()) {
            throw new RuntimeException("WSDL 파일을 찾을 수 없습니다: " + WSDL_PATH);
        }

        // WSDL 파일 기반으로 서비스 생성
        InspienGetRecruitingTestServicesInfoService service = new InspienGetRecruitingTestServicesInfoService(wsdlFile.toURI().toURL());
        port = service.getHTTPSPort();

        // Endpoint 설정 (필요한 경우)
        Map<String, Object> requestContext = ((BindingProvider) port).getRequestContext();
        requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, getEndpointFromWsdl());
    }

    public MTRecruitingTestServicesResponse getRecruitingTestService(String name, String phoneNumber, String email) throws Exception {
        // 요청 객체 생성
        MTRecruitingTestServices request = new MTRecruitingTestServices();
        request.setNAME(name);
        request.setPHONENUMBER(phoneNumber);
        request.setEMAIL(email);

        // SOAP 호출 및 응답 반환
        return port.inspienGetRecruitingTestServicesInfo(request);
    }

    private String getEndpointFromWsdl() throws Exception {
        return WSDLUtil.getServiceUrlFromWSDL(new File(WSDL_PATH), SERVICE_NAME);
    }

}

//    private static SOAPMessage createSOAPRequest() throws Exception {
//        // SOAP 메시지 팩토리 생성
//        MessageFactory messageFactory = MessageFactory.newInstance();
//        SOAPMessage soapMessage = messageFactory.createMessage();
//
//        // SOAP Part 가져오기
//        SOAPPart soapPart = soapMessage.getSOAPPart();
//        SOAPEnvelope envelope = soapPart.getEnvelope();
//        envelope.addNamespaceDeclaration("test", "http://inspien.co.kr/Recruit/Test");
//
//        // SOAP Body 생성
//        SOAPBody soapBody = envelope.getBody();
//        SOAPElement rootElement = soapBody.addChildElement("MT_RecruitingTestServices", "test");
//
//        // 요청 데이터 추가
//        rootElement.addChildElement("NAME", "test").addTextNode("김도현");
//        rootElement.addChildElement("PHONE_NUMBER", "test").addTextNode("010-7470-6471");
//        rootElement.addChildElement("E_MAIL", "test").addTextNode("flash6471@naver.com");
//
//        soapMessage.saveChanges();
//
//        // 생성된 SOAP 메시지 출력 (디버깅용)
//        System.out.println("SOAP Request:");
//        soapMessage.writeTo(System.out);
//        System.out.println("\n");
//
//        return soapMessage;
//
//    }
//
//    private static String sendSOAPRequest(String url, SOAPMessage soapMessage) throws Exception {
//        // URL 연결 설정
//        URL endpoint = new URL(url);
//        HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
//        connection.setRequestMethod("POST");
//        connection.setDoOutput(true);
//        connection.setDoInput(true);
//        connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
//        connection.setRequestProperty("SOAPAction", "http://sap.com/xi/WebService/soap1.1");
//
//        // SOAP 메시지 전송
//        OutputStream outputStream = connection.getOutputStream();
//        soapMessage.writeTo(outputStream);
//        outputStream.flush();
//        outputStream.close();
//
//        // 응답 코드 확인
//        int responseCode = connection.getResponseCode();
//
//        if (responseCode == 200) {
//            // 응답 스트림 읽기
//            InputStream responseStream = connection.getInputStream();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream, "UTF-8"));
//            StringBuilder response = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                response.append(line).append("\n");
//            }
//            reader.close();
//
//            return response.toString();
//
//        } else {
//            return "fail";
//        }
//    }



