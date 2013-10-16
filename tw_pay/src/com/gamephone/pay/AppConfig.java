package com.gamephone.pay;

import java.util.HashMap;
import java.util.Map;


public class AppConfig {

    /***************** 应用汇的接口相关配置 **********************************/
    public static final Map<String, String> YYH_CPKEY= new HashMap<String, String>();
    
    /***************** UC相关配置 **********************************/
    public static final String UC_SERVER_URL="http://sdk.g.uc.cn";//http://sdk.test4.g.uc.cn
    
    /*****************斯凯的支付key **********************************/
    public static final String OPEN_SKY_KEY="w%qq5kiljy";
    
    /*****************华为支付 **********************************/
    public static final String HUA_WEI_PUBLIC_KEY="MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAMReHg4Efd73YC4COUe7WFfrJu4dS0erqApE/lvhTqpZWEVoMD0zlGfXz8uGiskxpqW8KSqOA6SKEm0Cg6hjrkUCAwEAAQ==";
    
    public static final String HUA_WEI_PRIVATE_KEY="MIIBVgIBADANBgkqhkiG9w0BAQEFAASCAUAwggE8AgEAAkEAxF4eDgR93vdgLgI5R7tYV+sm7h1LR6uoCkT+W+FOqllYRWgwPTOUZ9fPy4aKyTGmpbwpKo4DpIoSbQKDqGOuRQIDAQABAkEAlfSFoRC/CokAcsIR+fxQP3t81DOcrFOi1IzRbVrGVwDiorZDV+FnGyuY4fulkFo+6fT1BGye5jO95l+DQULbAQIhAOxxIRXWdXuyNA97oKqxwQ6Vaoe+dtBPr4GXudNySKAFAiEA1JxhI6DfdoY6fERQf6zhkRc4TN28l+1/f92O5r7daUECIQCihcCwOhHXTM7sNZZivnxtgdcQJ6OT3LJO6fQZm9y/4QIhAMvEcMkv1twGu6j75Fbpf2qKYZgx8B2ALvGCjAIUranBAiAVJGLDTqPl653yesampECGRSxjjFb7SyzbMF2bQk4cUw==";
    
    public static final String HUA_WEI_ACCOUNT="北京云游游";// 使用的是昵称不是登录用户名
    
    /*****************酷派支付密钥配置信息******************************/
    public static final String KUPAD_CPKEY="j2rjae08lfu1whr3wn1bcfta";
    
    static{
        YYH_CPKEY.put("yyh_gameId_10024600000001100246", "xwwhkhythn3zlrm41tr72mar");
        YYH_CPKEY.put("yyh_gameId_10025900000001100259", "vkhb0rzjt0oiz10n74m4rhox");
        YYH_CPKEY.put("yyh_gameId_10026500000001100265", "pi9vxtgunwcydoheyg66tqmv");
    }
}
