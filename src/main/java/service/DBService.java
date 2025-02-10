package service;


import com.example.generated.MTRecruitingTestServicesResponse;
import entity.Test.Test;
import entity.Test.TestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBService {

    private static final Logger logger = LoggerFactory.getLogger(DBService.class);

    public static void insertDb(MTRecruitingTestServicesResponse response){

        try{

            List<Test> insertDatas = murgeData(response);

            for(Test test : insertDatas){
                System.out.println(test.getSender());
            }

            if(insertDatas.isEmpty()){
                // 빈 리스트가 처리되어야 할 경우
                logger.info("DBService.insertDb : no Data inserted");
                return;
            }

            Connection conn = DBConn.getConnection(response.getDBCONNINFO());

            if(conn == null){
                throw new RuntimeException("DBService.insertDb : No connection found");
            }
            conn.setAutoCommit(false);

            String sql = "INSERT INTO TEST "
                    + "(ORDER_NUM, ORDER_ID, ORDER_DATE, ORDER_PRICE, ORDER_QTY, RECEIVER_NAME, "
                    + "RECEIVER_NO, ETA_DATE, DESTINATION, DESCIPTION, ITEM_SEQ, ITEM_NAME, ITEM_QTY, ITEM_COLOR, ITEM_PRICE, SENDER, CURRENT_DT) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            for(Test t : insertDatas){

                if(t.getOrderNum().isBlank()){
                    continue;
                }

                pstmt.setString(1, t.getOrderNum());
                pstmt.setString(2, t.getOrderId());
                pstmt.setString(3, t.getOrderDate());
                pstmt.setString(4, t.getOrderPrice());
                pstmt.setString(5, t.getOrderQty());
                pstmt.setString(6, t.getReceiverName());
                pstmt.setString(7, t.getReceiverNo());
                pstmt.setString(8, t.getEtaDate());
                pstmt.setString(9, t.getDestination());
                pstmt.setString(10, t.getDescription());
                pstmt.setString(11, t.getItemSeq());
                pstmt.setString(12, t.getItemName());
                pstmt.setString(13, t.getItemQty());
                pstmt.setString(14, t.getItemColor());
                pstmt.setString(15, t.getItemPrice());
                pstmt.setString(16, t.getSender());
                pstmt.setTimestamp(17, java.sql.Timestamp.valueOf(LocalDateTime.now()));

                pstmt.addBatch();
            }

            pstmt.executeBatch();

            conn.commit();

            pstmt.close();
            conn.close();

        }catch (Exception e){
            logger.error("DBService.insertDb : Error in inserting database", e);
            logger.error(e.getMessage());
        }
    }

    public static List<Test> murgeData(MTRecruitingTestServicesResponse response) throws Exception {

        try{
            String decodedXml = Decoder.decodeXml(response.getXMLDATA());

            List<Test> rtnList = new ArrayList<>();

            Document doc = XmlUtil.parseXml(decodedXml);

            NodeList headers = doc.getDocumentElement().getElementsByTagName("HEADER");

            HashMap<String, TestDto> map = new HashMap<>();

            for(int i = 0; i < headers.getLength(); i++){
                Node header = headers.item(i);

                if (header.getNodeType() == Node.ELEMENT_NODE) {
                    Element headerElement = (Element) header;

                    String orderNum = getTextOrBlank(headerElement, "ORDER_NUM");

                    TestDto t = map.getOrDefault(orderNum, new TestDto());
                    t.setOrderNum(orderNum);
                    t.setOrderId(getTextOrBlank(headerElement, "ORDER_ID"));
                    t.setOrderDate(getTextOrBlank(headerElement, "ORDER_DATE"));
                    t.setOrderPrice(getTextOrBlank(headerElement, "ORDER_PRICE"));
                    t.setOrderQty(getTextOrBlank(headerElement, "ORDER_QTY"));
                    t.setReceiverName(getTextOrBlank(headerElement, "RECEIVER_NAME"));
                    t.setReceiverNo(getTextOrBlank(headerElement, "RECEIVER_NO"));
                    t.setEtaDate(getTextOrBlank(headerElement, "ETA_DATE"));
                    t.setDestination(getTextOrBlank(headerElement, "DESTINATION"));
                    t.setDescription(getTextOrBlank(headerElement, "DESCIPTION"));

                    map.put(orderNum, t);
                }

            }

            NodeList details = doc.getDocumentElement().getElementsByTagName("DETAIL");

            for(int j = 0; j<details.getLength(); j++){

                Node detail = details.item(j);

                if(detail.getNodeType() == Node.ELEMENT_NODE){
                    Element detailElement = (Element) detail;

                    String orderNum = getTextOrBlank(detailElement, "ORDER_NUM");

                    TestDto t = map.getOrDefault(orderNum, new TestDto());
                    t.setItemSeq(getTextOrBlank(detailElement,"ITEM_SEQ"));
                    t.setItemName(getTextOrBlank(detailElement,"ITEM_NAME"));
                    t.setItemQty(getTextOrBlank(detailElement,"ITEM_QTY"));
                    t.setItemColor(getTextOrBlank(detailElement,"ITEM_COLOR"));
                    t.setItemPrice(getTextOrBlank(detailElement,"ITEM_PRICE"));
                    t.setSender("김도현");

                    rtnList.add(t.dtoToEntity());
                }
            }

            return rtnList;

        } catch (Exception e) {
            logger.error("DBService.murgeData : Error in setting xml data", e);
            return new ArrayList<>(); // 예외 발생 시 빈 리스트 반환
        }
    }

    // 태그의 텍스트 내용을 가져오되, 해당 태그가 없을 경우 null 반환
    private static String getTextOrBlank(Element element, String tagName) {
        NodeList nodes = element.getElementsByTagName(tagName);
        if (nodes.getLength() > 0 && nodes.item(0) != null) {
            return nodes.item(0).getTextContent();
        }
        return "";
    }
}
