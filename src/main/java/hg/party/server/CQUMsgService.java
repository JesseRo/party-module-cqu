package hg.party.server;


import java.nio.charset.Charset;

import hg.util.result.Result;
import hg.util.result.ResultUtil;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.osgi.service.component.annotations.Component;

@Component(immediate = true, service = CQUMsgService.class)
public class CQUMsgService {

    private static final String USER_ID = "100007";
    private static final String PASSWORD = "zzb2395]";
    private static final String END_POINT = "http://s.cqu.edu.cn/WebService/SmsServiceCQU.asmx";
    private static int socketTimeout = 30000;// 请求超时时间
    private static int connectTimeout = 30000;// 传输超时时间
    /**
     * 短信通知手机
     * @param phone
     * @param messageContent
     * @return
     */
    public static Result sendPhoneNoticeMsg(String phone, String messageContent) {
        // 远程调用路径
        String result = "call failed!";
        String soapAction="http://222.198.128.75/WebService.aspx/SmsSubmit";//"http://tempuri.org/execute";
        String namespace="http://222.198.128.75/WebService.aspx";//命名空间
        int paramsNum=4;//参数个数
        String[] params={USER_ID,PASSWORD,phone,messageContent};//参数组
        result=execute(namespace, END_POINT, soapAction, "execute", paramsNum, params);//访问接口
        return ResultUtil.success(result);
    }

    public static void main(String[] args) {
        sendPhoneNoticeMsg("13272939619","AAA");
        System.out.println("aa");
    }
    /**
     * 访问webservice接口
     * @param namespace 命名空间
     * @param postUrl webservice接口地址
     * @param soapAction soapAction地址
     * @param method 方法名
     * @param paramsNum 参数个数
     * @param params 参数组
     * @return  返回值
     */
    public static String execute(String namespace,String postUrl,String soapAction,String method,int paramsNum,String[] params){
        StringBuffer sb=new StringBuffer("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <SmsSubmit xmlns=\""+namespace+"\">\n" +
                "      <userID>"+params[0]+"</userID>\n" +
                "      <password>"+params[1]+"</password>\n" +
                "      <phone>"+params[2]+"</phone>\n" +
                "      <messageContent>"+params[3]+"</messageContent>\n" +
                "    </SmsSubmit>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>");

        return doPostSoap1_1(postUrl, sb.toString(), soapAction);
    }

    /**
     * 使用SOAP1.1发送消息
     *
     * @param postUrl
     * @param soapXml
     * @param soapAction
     * @return
     */
    public static String doPostSoap1_1(String postUrl, String soapXml,
                                       String soapAction) {
        String retStr = "";
        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost(postUrl);
        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(socketTimeout)
                .setConnectTimeout(connectTimeout).build();
        httpPost.setConfig(requestConfig);
        try {
            httpPost.setHeader("Content-Type", "text/xml;charset=UTF-8");
            httpPost.setHeader("SOAPAction", soapAction);
            StringEntity data = new StringEntity(soapXml,
                    Charset.forName("UTF-8"));
            httpPost.setEntity(data);
            CloseableHttpResponse response = closeableHttpClient
                    .execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                // 打印响应内容
                retStr = EntityUtils.toString(httpEntity, "UTF-8");
            }
            // 释放资源
            closeableHttpClient.close();
        } catch (Exception e) {
        }
        return retStr;
    }
}
