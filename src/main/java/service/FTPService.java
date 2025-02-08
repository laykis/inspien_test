package service;

import com.example.generated.MTRecruitingTestServicesResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import constant.Const;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import util.Decoder;
import util.Encoder;
import util.JsonUtil;
import util.TimeUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static util.JsonUtil.saveJsonToFile;
import static util.TimeUtil.getFileNameFormatTime;

public class FTPService {

    public static void uploadJsonToFtp(MTRecruitingTestServicesResponse response) throws UnsupportedEncodingException {

        String jsonData = Decoder.decodeJson(response.getJSONDATA());

        String ftpServer = response.getFTPCONNINFO().getHOST();  // FTP 서버 주소
        int ftpPort = Integer.parseInt(response.getFTPCONNINFO().getPORT());  // FTP 포트 번호
        String ftpUsername = response.getFTPCONNINFO().getUSER();  // FTP 사용자명
        String ftpPassword = response.getFTPCONNINFO().getPASSWORD();  // FTP 비밀번호

        String fileName = "INSPIEN_JSON_김도현_" + getFileNameFormatTime() + ".txt";  // 파일명 지정
        String localFilePath = "/Users/laykis/Development/inspien_test/src/main/resources/ftpFile/";
        String encodedUtf8FileName = new String((localFilePath + fileName).getBytes(StandardCharsets.UTF_8), Const.EUC_KR);

        System.out.println("encodedUtf8FileName: " + encodedUtf8FileName);

        try {

            JsonUtil.saveJsonToFile(encodedUtf8FileName, jsonData);

            // 2. FTP 서버에 파일 업로드
            uploadFileToFtp(encodedUtf8FileName, ftpServer, ftpPort, ftpUsername, ftpPassword, fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // FTP 서버에 파일을 업로드하는 메서드
    public static void uploadFileToFtp(String localFileName, String ftpServer, int ftpPort, String ftpUsername, String ftpPassword, String ftpRemoteFileName) throws IOException {
        FTPClient ftpClient = new FTPClient();

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
            ftpClient.setFileTransferMode(FTP.BLOCK_TRANSFER_MODE);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setControlEncoding("EUC-KR");
//            // 타임아웃 설정 추가
//            ftpClient.setConnectTimeout(30000);
//            ftpClient.setDefaultTimeout(30000);
//            ftpClient.setDataTimeout(30000);
            // 버퍼 크기 설정
            ftpClient.setBufferSize(1024 * 1024);

            // 파일 업로드
            try (InputStream fileInputStream = new FileInputStream(file);
                 ProgressInputStream progressInputStream = new ProgressInputStream(fileInputStream, fileSize)) {
                ftpClient.changeWorkingDirectory("/");
                String encodedUtf8FileName = new String(ftpRemoteFileName.getBytes(StandardCharsets.UTF_8), Const.EUC_KR);

                System.out.println("upload file name: " + encodedUtf8FileName);
                boolean success = ftpClient.storeFile(encodedUtf8FileName, progressInputStream);
                if (success) {
                    System.out.println("File uploaded successfully!");
                } else {
                    System.out.println("File upload failed.");
                    System.out.println(ftpClient.getReplyString());
                    System.out.println(ftpClient.getReply());
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
            System.out.print("\rUploading: " + progress + "%");
        }
    }
}
