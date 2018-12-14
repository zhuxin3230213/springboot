package cn.gmuni.sc.socketclient.factory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class WebSocketClientFactory {

    public static CustomWebsocketClient create(String code,String url) throws URISyntaxException {
        return new CustomWebsocketClient(code,URI.create(url),null);
    }

    public static CustomWebsocketClient create(String code,String url, Map<String,String> headers) throws URISyntaxException {
        return new CustomWebsocketClient(code,new URI(url),headers);
    }
}
