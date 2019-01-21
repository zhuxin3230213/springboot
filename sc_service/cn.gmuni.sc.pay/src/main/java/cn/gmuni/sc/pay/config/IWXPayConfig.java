package cn.gmuni.sc.pay.config;

import cn.gmuni.sc.pay.wxpay.sdk.IWXPayDomain;
import cn.gmuni.sc.pay.wxpay.sdk.WXPayConfig;
import cn.gmuni.sc.pay.wxpay.sdk.WXPayConstants;

import java.io.InputStream;


public class IWXPayConfig extends WXPayConfig {
    @Override
    public String getAppID() {
        return "wx194d9fd877b4bcfb";
    }

    @Override
    public String getMchID() {
        return "1375489602";
    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public InputStream getCertStream() {
        return null;
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
        IWXPayDomain iwxPayDomain = new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }
            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
        return iwxPayDomain;
    }
}
