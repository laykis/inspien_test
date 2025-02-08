import com.example.generated.MTRecruitingTestServicesResponse;
import service.DBService;
import service.FTPService;
import service.SoapClient;
import util.Decoder;

public class Main {
    public static void main(String[] args) throws Exception {

        MTRecruitingTestServicesResponse response = SoapClient.getResponse();

//        DBService.insertDb(response);
        FTPService.uploadJsonToFtp(response);

    }
}

