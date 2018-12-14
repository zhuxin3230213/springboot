package cn.gmuni.sc.pay.model;

public class NetAccountInfo {
    //E00
    private String code;
    //可用状态
    private String status;
    //状态时间
    private String statusTime;
    //状态备注
    private String stateNote;
    //储值余额
    private String storedValueBalance;
    //本期使用费
    private String thisFee;
    //本期时长
    private String currentTime;
    //本期流量
    private String currentFlow;
    //起始计费日
    private String initialBilling;
    //套餐编号
    private String packageCode;
    //套餐具体名称
    private String packageName;
    //套餐类型，包月1，包流量0
    private String packageType;
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(String statusTime) {
        this.statusTime = statusTime;
    }

    public String getStateNote() {
        return stateNote;
    }

    public void setStateNote(String stateNote) {
        this.stateNote = stateNote;
    }

    public String getStoredValueBalance() {
        return storedValueBalance;
    }

    public void setStoredValueBalance(String storedValueBalance) {
        this.storedValueBalance = storedValueBalance;
    }

    public String getThisFee() {
        return thisFee;
    }

    public void setThisFee(String thisFee) {
        this.thisFee = thisFee;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getCurrentFlow() {
        return currentFlow;
    }

    public void setCurrentFlow(String currentFlow) {
        this.currentFlow = currentFlow;
    }

    public String getInitialBilling() {
        return initialBilling;
    }

    public void setInitialBilling(String initialBilling) {
        this.initialBilling = initialBilling;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }
}
