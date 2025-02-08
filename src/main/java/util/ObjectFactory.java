package util;

import com.example.generated.MTRecruitingTestServices;
import com.example.generated.MTRecruitingTestServicesResponse;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;

import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {
    public ObjectFactory() {}

    @XmlElementDecl(namespace = "", name = "MT_RecruitingTestServices")
    public JAXBElement<MTRecruitingTestServices> createMTRecruitingTestServices(MTRecruitingTestServices value) {
        return new JAXBElement<>(new QName("MT_RecruitingTestServices"), MTRecruitingTestServices.class, null, value);
    }

    @XmlElementDecl(namespace = "", name = "MT_RecruitingTestServicesResponse")
    public JAXBElement<MTRecruitingTestServicesResponse> createMTRecruitingTestServicesResponse(MTRecruitingTestServicesResponse value) {
        return new JAXBElement<>(new QName("MT_RecruitingTestServicesResponse"), MTRecruitingTestServicesResponse.class, null, value);
    }
}
