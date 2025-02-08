package service;

import com.example.generated.InspienGetRecruitingTestServicesInfo;
import com.example.generated.InspienGetRecruitingTestServicesInfoService;
import com.example.generated.MTRecruitingTestServices;
import com.example.generated.MTRecruitingTestServicesResponse;
import constant.Const;
import jakarta.xml.ws.BindingProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.TimeUtil;
import util.WSDLUtil;

import java.io.File;
import java.util.Map;


public class SoapClient {
    
    private static final Logger logger = LoggerFactory.getLogger(SoapClient.class);

    public static MTRecruitingTestServicesResponse getResponse() throws Exception {

        logger.info(TimeUtil.getNowTime() + " Starting SoapClient to get response");
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
            logger.error("SoapClient getResponse: error while getting response", e);
            logger.error(e.getMessage());
        }

        return response;
    }
    

    private InspienGetRecruitingTestServicesInfo port;

    public SoapClient() throws Exception {
        File wsdlFile = new File(Const.WSDL_PATH);
        if (!wsdlFile.exists()) {
            throw new RuntimeException("WSDL 파일을 찾을 수 없습니다: " + Const.WSDL_PATH);
        }

        // WSDL 파일 기반으로 서비스 생성
        InspienGetRecruitingTestServicesInfoService service = new InspienGetRecruitingTestServicesInfoService(wsdlFile.toURI().toURL());
        port = service.getHTTPSPort();

        // Endpoint 설정 (필요한 경우)
        Map<String, Object> requestContext = ((BindingProvider) port).getRequestContext();
        requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, getEndpointFromWsdl());
    }

    public MTRecruitingTestServicesResponse getRecruitingTestService(String name, String phoneNumber, String email) {
        // 요청 객체 생성
        MTRecruitingTestServices request = new MTRecruitingTestServices();
        request.setNAME(name);
        request.setPHONENUMBER(phoneNumber);
        request.setEMAIL(email);

        // SOAP 호출 및 응답 반환
        return port.inspienGetRecruitingTestServicesInfo(request);
    }

    private String getEndpointFromWsdl() throws Exception {
        return WSDLUtil.getServiceUrlFromWSDL(new File(Const.WSDL_PATH), Const.SERVICE_NAME);
    }

}


