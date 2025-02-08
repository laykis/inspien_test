package service;

import com.example.generated.MTRecruitingTestServicesResponse;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Decoder;
import util.Encoder;
import util.JsonUtil;

import java.io.*;

import static util.TimeUtil.getNowTime;

public class FTPService {

    private final static Logger logger = LoggerFactory.getLogger(FTPService.class);

    public static void uploadJsonToFtp(MTRecruitingTestServicesResponse response) throws UnsupportedEncodingException {
        String jsonData = Decoder.decodeJson(response.getJSONDATA());

        String ftpServer = response.getFTPCONNINFO().getHOST();  // FTP 서버 주소
        int ftpPort = Integer.parseInt(response.getFTPCONNINFO().getPORT());  // FTP 포트 번호
        String ftpUsername = response.getFTPCONNINFO().getUSER();  // FTP 사용자명
        String ftpPassword = response.getFTPCONNINFO().getPASSWORD();  // FTP 비밀번호

        String fileName = "INSPIEN_JSON_김도현_" + getNowTime() + ".txt";  // 파일명 지정
        String localFilePath = "/Users/laykis/IdeaProjects/inspien_test/inspien/src/main/resources/ftpFile/"; // 로컬 파일경로
        String encodedUtf8FileName = Encoder.encodeToUtf8(localFilePath + fileName); // 경로 + 이름 파일명을 utf8로 인코딩

        logger.info("FTPService uploadJsonToFtp : encodedUtf8FileName: {}", encodedUtf8FileName);

        try {
            // JSON 데이터를 파일로 저장
            JsonUtil.saveJsonToFile(encodedUtf8FileName, jsonData);

            // FTP 서버에 파일 업로드
            uploadFileToFtp(encodedUtf8FileName, ftpServer, ftpPort, ftpUsername, ftpPassword, fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // FTP 서버에 파일을 업로드하는 메서드
    public static void uploadFileToFtp(String localFileName, String ftpServer, int ftpPort, String ftpUsername, String ftpPassword, String ftpRemoteFileName) throws IOException {
        FTPClient ftpClient = new FTPClient(); // ftp client 인스턴스 생성
        File file = new File(localFileName); // /path/to/localfile 읽어서 파일 생성
        long fileSize = file.length();

        try {
            // FTP 서버에 연결
            ftpClient.connect(ftpServer, ftpPort);
            int replyCode = ftpClient.getReplyCode();

            //reply code가 성공적 응답이 아닌경우
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                logger.error("FTPService uploadFileToFtp : FTP server refused connection.");
                return;
            }

            // FTP 로그인
            ftpClient.login(ftpUsername, ftpPassword);
            ftpClient.enterLocalPassiveMode(); // ftp 패시브모드
            ftpClient.setControlEncoding("UTF-8"); // ftp 인코딩 설정
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE); // ftp 파일 타입 설정
            ftpClient.setBufferSize(1024 * 1024);  // ftp 버퍼 크기 설정

            logger.info("FTPService uploadFileToFtp : FTP server connected.");

            // 업로드 중 진행상황을 보기 위해 ProgressInputStream 으로 업로드 % 표시
            try (InputStream fileInputStream = new FileInputStream(file);
                 ProgressInputStream progressInputStream = new ProgressInputStream(fileInputStream, fileSize)) {

                //ftp 서버가 파일 이름을 읽을 수 있도록 ISO-8859-1로 인코딩해서 파일명 넘겨줌.
                String encodedUtf8FileName = Encoder.encodeToISO88591(ftpRemoteFileName);
                logger.info("FTPService uploadFileToFtp : encodedUtf8FileName: {}", encodedUtf8FileName);

                //업로드 호출 및 성공여부 저장
                boolean success = ftpClient.storeFile(encodedUtf8FileName, progressInputStream);

                if (success) {
                    logger.info("FTPService uploadFileToFtp : upload success");
                    
                } else {
                    logger.error("FTPService uploadFileToFtp : upload failed");
                }

            }

        } catch (IOException e) {
            logger.error("FTPService uploadFileToFtp : Cannot read or write file.");
        } finally {
            // FTP 연결 종료 처리
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                logger.error("FTPService uploadFileToFtp : Cannot disconnect ftp client.");
            }
        }
    }

    // 파일 업로드 진행 상황을 추적하는 InputStream 클래스
    static class ProgressInputStream extends InputStream {
        private final InputStream delegate;
        private final long fileSize;
        private long bytesRead;

        public ProgressInputStream(InputStream delegate, long fileSize) {
            this.delegate = delegate;
            this.fileSize = fileSize;
            this.bytesRead = 0;
        }

        @Override
        public int read() throws IOException {
            int byteRead = delegate.read();
            if (byteRead != -1) {
                bytesRead++;
                printProgress();
            }
            return byteRead;
        }

        @Override
        public int read(byte[] b) throws IOException {
            int bytesReadNow = delegate.read(b);
            if (bytesReadNow != -1) {
                bytesRead += bytesReadNow;
                printProgress();
            }
            return bytesReadNow;
        }

        // 진행 상황을 출력하는 메서드
        private void printProgress() {
            int progress = (int) ((bytesRead * 100) / fileSize);
            // 1% 단위로 진행 상황을 출력하도록 설정
            if (bytesRead % (fileSize / 100) == 0) {
                logger.info("FTPService uploadFileToFtp : progress: {}", progress);
            }
        }
    }
}
