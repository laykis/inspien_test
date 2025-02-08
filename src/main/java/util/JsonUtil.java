package util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import com.example.generated.MTRecruitingTestServicesResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import constant.Const;

import static util.TimeUtil.getFileNameFormatTime;

public class JsonUtil {

    public static void saveJsonToFile(String fileName, String jsonData) throws IOException {
        // JSON 파싱
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        JsonArray recordArray = jsonObject.getAsJsonArray("record");


        // 파일 경로와 이름 설정
        File file = new File(fileName);


        // EUC-KR로 인코딩 후 파일 쓰기
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), Const.EUC_KR))) {
            for (int i = 0; i < recordArray.size(); i++) {
                JsonObject record = recordArray.get(i).getAsJsonObject();

                String names = record.get("Names").getAsString();
                String phone = record.get("Phone").getAsString();
                String email = record.get("Email").getAsString();
                String birthDate = record.get("BirthDate").getAsString();
                String company = record.get("Company").getAsString();
                String personalNumber = record.get("PersonalNumber").getAsString();
                String organisationNumber = record.get("OrganisationNumber").getAsString();
                String country = record.get("Country").getAsString();
                String region = record.get("Region").getAsString();
                String city = record.get("City").getAsString();
                String street = record.get("Street").getAsString();
                String zipCode = record.get("ZipCode").getAsString();
                String creditCard = record.get("CreditCard").getAsString();
                String guid = record.get("GUID").getAsString();

                // 각 필드를 ^로 구분하여 한 줄에 저장
                String line = String.join("^", names, phone, email, birthDate, company, personalNumber,
                        organisationNumber, country, region, city, street, String.valueOf(zipCode),
                        String.valueOf(creditCard), guid);

                writer.write(line);
                writer.newLine();  // 줄바꿈
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
