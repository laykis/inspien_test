package service;

import com.example.generated.MTRecruitingTestServicesResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import util.Decoder;
import util.JsonUtil;
import util.TimeUtil;

import java.io.*;

import static util.JsonUtil.saveJsonToFile;
import static util.TimeUtil.getFileNameFormatTime;

public class FTPService {

    public static void uploadJsonToFtp(MTRecruitingTestServicesResponse response){

        String jsonData = Decoder.decodeJson(response.getJSONDATA());
        String fileName = "INSPIEN_JSON_김도현_" + TimeUtil.getFileNameFormatTime() + ".txt";  // 파일명 지정
        String ftpServer = response.getFTPCONNINFO().getHOST();  // FTP 서버 주소
        int ftpPort = Integer.parseInt(response.getFTPCONNINFO().getPORT());  // FTP 포트 번호
        String ftpUsername = response.getFTPCONNINFO().getUSER();  // FTP 사용자명
        String ftpPassword = response.getFTPCONNINFO().getPASSWORD();  // FTP 비밀번호
        String localFilePath = "/Users/laykis/IdeaProjects/inspien/src/main/resources/ftpFile/";

        try {

            JsonUtil.saveJsonToFile(localFilePath + fileName, jsonData);


            // 파일이 쓰여질 때까지 기다리기
            Thread.sleep(5000);
            // 2. FTP 서버에 파일 업로드
            uploadFileToFtp(localFilePath + fileName, ftpServer, ftpPort, ftpUsername, ftpPassword, fileName);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // FTP 서버에 파일을 업로드하는 메서드
    public static void uploadFileToFtp(String localFileName, String ftpServer, int ftpPort, String ftpUsername, String ftpPassword, String ftpRemoteDirectory) throws IOException {
        FTPClient ftpClient = new FTPClient();
        System.out.println(localFileName);
        File file = new File(localFileName);
        long fileSize = file.length();

        try {
            ftpClient.connect(ftpServer, ftpPort);
            System.out.println("Connected to " + ftpServer + ":" + ftpPort);

            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("FTP server refused the connection.");
                return;  // 서버 연결 실패 시 종료
            }

            ftpClient.login(ftpUsername, ftpPassword);
            System.out.println("Logged into " + ftpServer + ":" + ftpPort);

            ftpClient.enterLocalPassiveMode();
            // 파일 타입 설정 (텍스트 파일로 설정)
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setControlEncoding("UTF-8");
//            // 타임아웃 설정 추가
//            ftpClient.setConnectTimeout(30000);
//            ftpClient.setDefaultTimeout(30000);
//            ftpClient.setDataTimeout(30000);
            // 버퍼 크기 설정
            ftpClient.setBufferSize(1024 * 1024);

            // 파일 업로드
            try (InputStream inputStream = new FileInputStream(file)) {
                ftpClient.changeWorkingDirectory("/");
                boolean success = ftpClient.storeFile(ftpRemoteDirectory, inputStream);
                if (success) {
                    System.out.println("File uploaded successfully!");
                } else {
                    System.out.println(ftpClient.getReplyString());
                    System.out.println(ftpClient.getReply());
                    System.out.println("File upload failed.");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // FTP 연결 종료 처리
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


//    static class ProgressInputStream extends InputStream {
//        private final InputStream delegate;
//        private final long fileSize;
//        private long bytesRead;
//
//        public ProgressInputStream(InputStream delegate, long fileSize) {
//            this.delegate = delegate;
//            this.fileSize = fileSize;
//            this.bytesRead = 0;
//        }
//
//        @Override
//        public int read() throws IOException {
//            int byteRead = delegate.read();
//            if (byteRead != -1) {
//                bytesRead++;
//                printProgress();
//            }
//            return byteRead;
//        }
//
//        @Override
//        public int read(byte[] b) throws IOException {
//            int bytesReadNow = delegate.read(b);
//            if (bytesReadNow != -1) {
//                bytesRead += bytesReadNow;
//                printProgress();
//            }
//            return bytesReadNow;
//        }
//
//        // 진행 상황을 출력하는 메서드
//        private void printProgress() {
//            int progress = (int) ((bytesRead * 100) / fileSize);
//            System.out.print("\rUploading: " + progress + "%");
//        }
//    }
}
