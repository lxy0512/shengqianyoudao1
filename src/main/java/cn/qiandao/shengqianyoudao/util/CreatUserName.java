package cn.qiandao.shengqianyoudao.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CreatUserName {

    //生成随机用户名，数字和字母组成,
    public String getNewUserName() {

        String val = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for(int i = 0; i < 9; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        val = "生钱有道_" + val;
        return val;
    }
}
