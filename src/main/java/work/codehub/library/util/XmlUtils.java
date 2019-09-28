package work.codehub.library.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.XML;

/**
 * XML工具类 .<br>
 *
 * @author andy.sher
 * @date 2018/8/7 15:59
 */
@Slf4j
public final class XmlUtils {

    private XmlUtils() {
    }

    /**
     * XML文本转换为JSON对象 .<br>
     *
     * @param xml XML文本
     * @return com.alibaba.fastjson.JSONObject JSON对象
     * @author andy.sher
     * @date 2018/8/7 16:02
     */
    public static JSONObject toJSONObject(String xml) {
        org.json.JSONObject jsonObject = new org.json.JSONObject();
        try {
            jsonObject = XML.toJSONObject(xml.replace("<xml>", "").replace("</xml>", ""));
        } catch (JSONException e) {
            log.error(XmlUtils.class.getName(), e);
        }
        JSONObject returnJson = JSON.parseObject(jsonObject.toString());
        return returnJson;
    }

}
