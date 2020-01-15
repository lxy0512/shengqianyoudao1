package cn.qiandao.shengqianyoudao.util;

import org.springframework.stereotype.Component;

/**
 * 检查敏感词汇
 */
@Component
public class CheckSensitiveWords {

    public boolean checkWorks(String userWorks){
        SensitiveWord sensitiveWord = new SensitiveWord("CensorWords.txt");
        sensitiveWord.InitializationWork();
        String userResultWorks = sensitiveWord.filterInfo(userWorks);
        int sensitiveNumber = sensitiveWord.sensitiveWordList.size();
        if (sensitiveNumber > 0){
            System.out.println("该段文字含有敏感词汇");
            return false;
        }else {
            System.out.println("该段文字符合规范");
            return true;
        }
    }
}
