package cn.gmuni.sc.umpush;

import cn.gmuni.sc.umpush.android.AndroidBroadcast;
import cn.gmuni.sc.umpush.android.AndroidGroupcast;
import cn.gmuni.sc.umpush.android.PushAndroidGroupNotification;
import cn.gmuni.sc.umpush.android.PushAndroidNotification;
import cn.gmuni.sc.umpush.client.PushClient;
import cn.gmuni.sc.umpush.notification.IPushGroupNotification;
import cn.gmuni.sc.umpush.notification.IPushNotification;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;

@EnableScheduling
@Controller
public class PushNotificationFactory {

    private static final String appKey = "5ba2f655b465f501d20000f9";

    private static final String secret = "hyodaosdpkszco6povo1piodcy6lsaph";

    private static final PushClient client = new PushClient();

    public static IPushNotification build() throws Exception {
        AndroidBroadcast broadcast = new AndroidBroadcast(appKey,secret);
        return new PushAndroidNotification(broadcast,client);
    }

    public static IPushGroupNotification buildWithGroup() throws Exception {
        AndroidGroupcast groupcast = new AndroidGroupcast(appKey,secret);
        return new PushAndroidGroupNotification(groupcast,client);
    }


}
