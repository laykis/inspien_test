package service;

import com.example.generated.MTRecruitingTestServicesResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import util.DBConn;
import util.Decoder;
import util.XmlUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;

public class DBService {

    public static void insertDb(MTRecruitingTestServicesResponse response) throws Exception {

        try{
            String decodedXml = Decoder.decodeXml(response.getXMLDATA());

            Connection conn = DBConn.getConnection(response.getDBCONNINFO());

            Document doc = XmlUtil.parseXml(decodedXml);

            conn.setAutoCommit(false);

            NodeList headers = doc.getElementsByTagName("HEADER");
            String headerSQL = "INSERT INTO TEST "
                    + "(ORDER_NUM, ORDER_ID, ORDER_DATE, ORDER_PRICE, ORDER_QTY, RECEIVER_NAME, "
                    + "RECEIVER_NO, ETA_DATE, DESTINATION, DESCIPTION, CURRENT_DT) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement headerStatement = conn.prepareStatement(headerSQL);

            for (int i = 0; i < headers.getLength(); i++) {
                Node header = headers.item(i);
                if(header.getNodeType() == Node.ELEMENT_NODE) {
                    Element headerElement = (Element) header;

                    headerStatement.setString(1, headerElement.getAttribute("ORDER_NUM"));
                    headerStatement.setString(2, headerElement.getElementsByTagName("ORDER_ID").item(0).getTextContent());
                    headerStatement.setString(3, headerElement.getElementsByTagName("ORDER_DATE").item(0).getTextContent());
                    headerStatement.setString(4, headerElement.getElementsByTagName("ORDER_PRICE").item(0).getTextContent());
                    headerStatement.setString(5, headerElement.getElementsByTagName("ORDER_QTY").item(0).getTextContent());
                    headerStatement.setString(6, headerElement.getElementsByTagName("RECEIVER_NAME").item(0).getTextContent());
                    headerStatement.setString(7, headerElement.getElementsByTagName("RECEIVER_NO").item(0).getTextContent());
                    headerStatement.setString(8, headerElement.getElementsByTagName("ETA_DATE").item(0).getTextContent());
                    headerStatement.setString(9, headerElement.getElementsByTagName("DESTINATION").item(0).getTextContent());
                    headerStatement.setString(10, headerElement.getElementsByTagName("DESCIPTION").item(0).getTextContent());
                    headerStatement.setTimestamp(11, java.sql.Timestamp.valueOf(LocalDateTime.now()));

                    headerStatement.addBatch();
                }
            }

            NodeList details = doc.getElementsByTagName("DETAIL");
            String detailSQL = "INSERT INTO TEST "
                    + "(ORDER_NUM, ITEM_SEQ, ITEM_NAME, ITEM_QTY, ITEM_COLOR, ITEM_PRICE, CURRENT_DT) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement detailStatement = conn.prepareStatement(detailSQL);

            for(int i = 0; i < details.getLength(); i++) {
                Node detailNode = details.item(i);
                if (detailNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element detailElement = (Element) detailNode;

                    detailStatement.setInt(1, Integer.parseInt(detailElement.getElementsByTagName("ORDER_NUM").item(0).getTextContent()));
                    detailStatement.setInt(2, Integer.parseInt(detailElement.getElementsByTagName("ITEM_SEQ").item(0).getTextContent()));
                    detailStatement.setString(3, detailElement.getElementsByTagName("ITEM_NAME").item(0).getTextContent());
                    detailStatement.setInt(4, Integer.parseInt(detailElement.getElementsByTagName("ITEM_QTY").item(0).getTextContent()));
                    detailStatement.setString(5, detailElement.getElementsByTagName("ITEM_COLOR").item(0).getTextContent());
                    detailStatement.setInt(6, Integer.parseInt(detailElement.getElementsByTagName("ITEM_PRICE").item(0).getTextContent()));
                    detailStatement.setTimestamp(7, java.sql.Timestamp.valueOf(LocalDateTime.now()));

                    detailStatement.addBatch();  // 배치 추가
                }
            }

            headerStatement.executeBatch();
            detailStatement.executeBatch();

            conn.commit();
            conn.close();

        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
