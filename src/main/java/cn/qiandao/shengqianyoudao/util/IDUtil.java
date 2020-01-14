package cn.qiandao.shengqianyoudao.util;

/**
 * @author lxy
 * @date 2019/12/31 0031 11:49
 **/
public class IDUtil {
    /**
     * 生成规则编号:设备类型+六位编号（从1开始，不够前补0）
     *
     * @param equipmentType
     *              前缀
     * @param equipmentNo
     *              最大编号
     * @return
     */
    public static String getNewEquipmentNo(String equipmentType, String equipmentNo){
        String newEquipmentNo = "000001";

        if(equipmentNo != null && !equipmentNo.isEmpty()){
            String substring = equipmentNo.substring(equipmentType.length());
            int newEquipment = Integer.parseInt(substring) + 1;
            newEquipmentNo = String.format(equipmentType + "%06d", newEquipment);
        }
        return newEquipmentNo;
    }

}
