package cn.qiandao.shengqianyoudao.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class code {

    public String getLoginCode(){
        StringBuffer sb = new StringBuffer();
        String str = "0123456789";
        Random r = new Random();
        for(int i=0;i<4;i++){
            int num = r.nextInt(str.length());
            sb.append(str.charAt(num));
            str = str.replace((str.charAt(num)+""), "");
        }
        String code = sb.toString();
        String vc = "{\"code\":\""+ code +"\"}";
        return vc;
    }

    public String getInvitationCode(){
        String str = "";
        char[] ch = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        Random random = new Random();
        for (int i = 0; i <6; i++){
            char num = ch[random.nextInt(ch.length)];
            str += num;
        }
        return str;
    }

    /**
     * 生成规则设备编号:设备类型+五位编号（从1开始，不够前补0）
     *
     * @param equipmentType
     *              设备类型
     * @param equipmentNo
     *              最新设备编号
     * @return
     */
    public String getNewEquipmentNo(String equipmentType, String equipmentNo){
        String newEquipmentNo = "00001";

        if(equipmentNo != null && !equipmentNo.isEmpty()){
            int newEquipment = Integer.parseInt(equipmentNo) + 1;
            newEquipmentNo = String.format(equipmentType + "%05d", newEquipment);
        }

        return newEquipmentNo;
    }
}
