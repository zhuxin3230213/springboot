package cn.gmuni.sc.wallet.model;

import java.util.Date;

/**
 * 消费订单
 */
public class WalletPayment {

    private String id;

    //订单金额
    private String amount;

    //买家账号
    private String buyerId;

    //卖家账号
    private String sellerId;

    //支付类型
    private String paymentType;

    //支付状态
    private String status;

    //支付签名
    private String sign;

    //商品名称
    private String title;

    //支付备注
    private String remark;

    //交易创建时间
    private Date createTime;

    //交易付款时间
    private Date payTime;

    //交易结束时间
    private Date endTime;

    //订单号
    private String orderNumber;

    //流水号
    private String seriesNumber;

    //异常编码
    private String errorCode;

    //支付方式
    private  String payMode;

    //交易后余额
    private  String tradeBalance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getSeriesNumber() {
        return seriesNumber;
    }

    public void setSeriesNumber(String seriesNumber) {
        this.seriesNumber = seriesNumber;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getTradeBalance() {
        return tradeBalance;
    }

    public void setTradeBalance(String tradeBalance) {
        this.tradeBalance = tradeBalance;
    }

    @Override
    public String toString() {
        return "WalletPayment{" +
                "id='" + id + '\'' +
                ", amount='" + amount + '\'' +
                ", buyerId='" + buyerId + '\'' +
                ", sellerId='" + sellerId + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", status='" + status + '\'' +
                ", sign='" + sign + '\'' +
                ", title='" + title + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", payTime=" + payTime +
                ", endTime=" + endTime +
                ", orderNumber='" + orderNumber + '\'' +
                ", seriesNumber='" + seriesNumber + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", payMode='" + payMode + '\'' +
                ", tradeBalance='" + tradeBalance + '\'' +
                '}';
    }
}
