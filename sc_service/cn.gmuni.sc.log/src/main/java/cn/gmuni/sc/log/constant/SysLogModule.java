package cn.gmuni.sc.log.constant;

public enum SysLogModule {

    SCORE_LOG("score_log","成绩查询"),
    NOTICE_LOG("notice_log","新闻通知"),
    EDUCATION_LOG("education_log","教育学习"),
    LIVE_LOG("live_log","生活服务"),
    PAY_LOG("pay_log","充值缴费"),
    CONSUME_LOG("consume","生活消费"),
    JOB_LOG("job_log","招聘求职"),
    LEISURE_TIME_LOG("leisure_time_log","休闲时刻"),
    SOCIALIZING_LOG("socializing_log","社交"),
    OTHER_LOG("other_log","其他");


    private String name;

    private String desc;

     SysLogModule(String name, String desc){
        this.name = name;
        this.desc = desc;
     }

     public String getName(){
        return name;
     }

     public String getDesc(){
         return desc;
     }

}
