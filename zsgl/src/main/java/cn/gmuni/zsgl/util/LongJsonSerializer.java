package cn.gmuni.zsgl.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @Author:ZhuXin
 * @Description:
 * Long 类型字段序列化时转为字符串，避免js丢失精度
 * @Date:Create in 17:16 2018/8/2
 * @Modified By:
 **/

public class LongJsonSerializer extends JsonSerializer<Long> {


    @Override
    public void serialize(Long value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String text = (value == null ? null : String.valueOf(value));
        if (text != null) {
            jsonGenerator.writeString(text);
        }
    }
}
