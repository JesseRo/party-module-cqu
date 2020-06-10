package hg.party.server;



import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.osgi.service.component.annotations.Component;

@Component(immediate = true, service = CQUMsgService.class)
public class CQUMsgService {

    private static final String USER_ID = "100007";
    private static final String PASSWORD = "zzb2395";

    /**
     * 短信通知手机
     * @param phone
     * @param messageContent
     * @return
     */
    public static String sendPhoneNoticeMsg(String phone,String messageContent) {
        // 远程调用路径
        String endpoint = "http://s.cqu.edu.cn/WebService/SmsServiceCQU.asmx";
        String result = "call failed!";
        String method = "SmsSubmit";
        Service service = new Service();
        Call call;
        try {
            call = (Call) service.createCall();
            call.setTargetEndpointAddress(endpoint);
            //new QName的URL是要指向的命名空间的名称，这个URL地址在你的wsdl打开后可以看到的，
            //上面有写着targetNamespace="http://*.*.*/",这个就是你的命名空间值了;
            call.setOperationName(new QName(endpoint,method));
            // 调用的方法名
            // 设置参数名 :参数名 ,参数类型:String, 参数模式：'IN' or 'OUT'
            // string userID, string password, string phone, string messageContent
            call.addParameter("userID",XMLType.XSD_STRING,ParameterMode.IN);
            call.addParameter("password",XMLType.XSD_STRING,ParameterMode.IN);
            call.addParameter("phone",XMLType.XSD_STRING,ParameterMode.IN);
            call.addParameter("messageContent",XMLType.XSD_STRING,ParameterMode.IN);
            call.setEncodingStyle("UTF-8");
            call.setReturnType(XMLType.XSD_STRING);
            result = (String) call.invoke(new Object[] {USER_ID,PASSWORD,phone,messageContent});// 远程调用
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }

}
