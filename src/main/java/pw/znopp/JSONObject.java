package pw.znopp;

import org.json.JSONException;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

public class JSONObject extends org.json.JSONObject {

    public JSONObject(String json){
        super(json);
    }

    public JSONObject(){
        super();
    }

    @Override
    public org.json.JSONObject put(String key, Object value) throws JSONException {
        try {
            Field map = org.json.JSONObject.class.getDeclaredField("map");
            map.setAccessible(true);
            Object mapValue = map.get(this);
            if (!(mapValue instanceof LinkedHashMap)) {
                map.set(this, new LinkedHashMap<>());
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return super.put(key, value);
    }

}
