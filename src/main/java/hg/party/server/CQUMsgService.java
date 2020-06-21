package hg.party.server;



import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import hg.util.result.Result;
import hg.util.result.ResultCode;
import hg.util.result.ResultUtil;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.osgi.service.component.annotations.Component;

@Component(immediate = true, service = CQUMsgService.class)
public class CQUMsgService {

    private static final String USER_ID = "100007";
    private static final String PASSWORD = "zzb2395]";

    /**
     * 短信通知手机
     * @param phone
     * @param messageContent
     * @return
     */
    public static Result sendPhoneNoticeMsg(String phone, String messageContent) {
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
            call.setOperationName(new QName(method));
            // 调用的方法名
            // 设置参数名 :参数名 ,参数类型:String, 参数模式：'IN' or 'OUT'
            // string userID, string password, string phone, string messageContent
            call.addParameter("userID",XMLType.XSD_STRING,ParameterMode.IN);
            call.addParameter("password",XMLType.XSD_STRING,ParameterMode.IN);
            call.addParameter("phone",XMLType.XSD_STRING,ParameterMode.IN);
            call.addParameter("messageContent",XMLType.XSD_STRING,ParameterMode.IN);
            call.setUseSOAPAction(true);
            call.setSOAPActionURI("http://222.198.128.75/WebService.aspx/GetSmsReport");
            call.setEncodingStyle("UTF-8");
            call.setReturnType(XMLType.XSD_STRING);
            result = (String) call.invoke(new Object[] {USER_ID,PASSWORD,phone,messageContent});// 远程调用
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            if("(404)Not Found".equals(e.getMessage())){
               return  ResultUtil.fail(ResultCode.NOT_FOUND,"短信服务无法连接。");
            }
            return  ResultUtil.fail(ResultCode.INTERNAL_SERVER_ERROR,"短信服务调用出现异常。");
        }
        return ResultUtil.success(result);
    }

    public static void main(String[] args) {
        sendPhoneNoticeMsg("13272939619","AAA");
        System.out.println("aa");
    }
}
