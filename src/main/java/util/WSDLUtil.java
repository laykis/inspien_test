package util;


import constant.Const;

import javax.wsdl.Definition;
import javax.wsdl.Port;
import javax.wsdl.Service;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;
import java.io.File;
import java.util.Map;

public class WSDLUtil {


    public static String getServiceUrlFromWSDL(File wsdlFile, String serviceNameToFind) throws Exception {


        // WSDL 파서 초기화
        WSDLFactory factory = WSDLFactory.newInstance();
        WSDLReader reader = factory.newWSDLReader();
        Definition definition = reader.readWSDL(wsdlFile.getAbsolutePath());

        // 네임스페이스 가져오기
        String targetNamespace = definition.getTargetNamespace();

        // 서비스 검색
        Service service = null;
        Map<?, ?> services = definition.getServices();

        for (Object key : services.keySet()) {
            String existingServiceName = key.toString();

            if (existingServiceName.equals("{" + targetNamespace + "}" + serviceNameToFind)) {
                service = (Service) services.get(key);
                break;
            }
        }

        if (service == null) {
            return null;
        }

        // 포트 검색
        for (Object obj : service.getPorts().values()) {
            Port port = (Port) obj;

            if(port.getName().equals(Const.WSDL_SERVICE_TYPE)){
                // SOAP 바인딩 확인 후 주소 추출
                for (Object extElement : port.getExtensibilityElements()) {
                    if (extElement instanceof javax.wsdl.extensions.soap.SOAPAddress) {
                        javax.wsdl.extensions.soap.SOAPAddress soapAddress = (javax.wsdl.extensions.soap.SOAPAddress) extElement;
                        return soapAddress.getLocationURI();
                    }
                }
            }
        }

        return null;
    }

}
