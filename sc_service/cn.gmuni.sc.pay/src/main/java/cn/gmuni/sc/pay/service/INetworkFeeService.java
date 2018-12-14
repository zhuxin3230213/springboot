package cn.gmuni.sc.pay.service;

import java.util.Map;

public interface INetworkFeeService {

    /**
     * 获取此service的标记，判断是否使用
     *
     * @return
     */
    String getFlag();

    /**
     * 查询账户信息
     *
     * @param model
     * @return
     */
    Object queryAccountInfo(Object obj);

    /**
     * 获取账户状态
     *
     * @param model
     * @return
     */
    Object getAccountState(Object obj);

    /**
     * 获取套餐列表
     */
    Object listPlans(Object obj);


    /**
     * 获取用户上网记录
     *
     * @param model
     * @return
     */
    Object listNetPlays(Object obj);


    /**
     * 缴费明细
     *
     * @param model
     * @return
     */
    Object listPayDetails(Object obj);

    /**
     * 账号报停
     *
     * @param model
     * @return
     */
    Object stopService(Object obj);
    /**
     * 预约报停
     *
     * @param model
     * @return
     */
    Object stopDelay(Object obj);

    /**
     * 账号复通
     *
     * @param model
     * @return
     */
    Object startService(Object obj);
    /**
     * 预约复通
     *
     * @param model
     * @return
     */
    Object startDelay(Object obj);

    /**
     * 预约套餐
     *
     * @param model
     * @return
     */
    Object subscribePlan(Object obj);

    /**
     * 保存支付信息，并充值
     *
     * @param payInfo
     * @return
     */
    boolean savePayInfo(Map<String, String[]> payInfo);

    /**
     * 查询用户信息是否存在
     *
     * @return
     */
    Map<String, String> checkNetAccountExist(Map<String, String> param);
}
