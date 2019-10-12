package hg.party.unity;

import java.util.Random;

/**
 * 文件名称： party<br>
 * 内容摘要： 生成密码<br>
 * 创建人 　： zhang minggang<br>
 * 创建日期： 2017年11月10日上午11:45:54<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */
public class PasswdCreate {
	/** 
     * 获得密码 
     * @param len 密码长度 
     * @return 
     */  
    public String createPassWord(int len){  
        int random = this.createRandomInt();  
        return this.createPassWord(random, len);  
    }  
      
    public String createPassWord(int random,int len){  
        Random rd = new Random(random);  
        final int  maxNum = 62;  
        StringBuffer sb = new StringBuffer();  
        int rdGet;//取得随机数  
        char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',  
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',  
                'x', 'y', 'z', 'A','B','C','D','E','F','G','H','I','J','K',  
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',  
                'X', 'Y' ,'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };  
          
        int count=0;  
        while(count < len){  
            rdGet = Math.abs(rd.nextInt(maxNum));//生成的数最大为62-1  
            if (rdGet >= 0 && rdGet < str.length) {  
                sb.append(str[rdGet]);  
                count ++;  
            }  
        }  
        return sb.toString();  
    }  
      
    public int createRandomInt(){  
        //得到0.0到1.0之间的数字，并扩大100000倍  
        double temp = Math.random()*100000;  
        //如果数据等于100000，则减少1  
        if(temp>=100000){  
            temp = 99999;  
        }  
        int tempint = (int)Math.ceil(temp);  
        return tempint;  
    }  
      
    public static void main(String[] args){  
        PasswdCreate pwc = new PasswdCreate();  
        System.out.println(pwc.createPassWord(6));  
    }  
}
