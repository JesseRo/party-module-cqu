package hg.party.server;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CQUMsgService {


    public void sendPhoneMSG(String msg,String phoneNumber){
        //调用接口
        //方法一:直接AXIS调用远程的web service
        try {
            String endpoint = "http://localhost:8080/platform-jxcx-service/services/settlementServiceImpl?wsdl";
//            Service service = new Service();
//            Call call = (Call) service.createCall();
//            call.setTargetEndpointAddress(endpoint);
//            String parametersName = "settle_num"; 		// 参数名//对应的是 public String printWord(@WebParam(name = "settle_num") String settle_num);
////	            call.setOperationName("printWord");  		// 调用的方法名//当这种调用不到的时候,可以使用下面的,加入命名空间名
//            call.setOperationName(new QName("http://jjxg_settlement.platform.bocins.com/", "printWord"));// 调用的方法名
//            call.addParameter(parametersName, XMLType.XSD_STRING, ParameterMode.IN);//参数名//XSD_STRING:String类型//.IN入参
//            call.setReturnType(XMLType.XSD_STRING); 	// 返回值类型：String
//            String message = "123456789";
//            String result = (String) call.invoke(new Object[] { message });// 远程调用
//            System.out.println("result is " + result);
        } catch (Exception e) {
            System.err.println(e.toString());
        }


    }
}
