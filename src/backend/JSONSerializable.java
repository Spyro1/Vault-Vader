package backend;

import org.json.simple.JSONObject;

public interface JSONSerializable {
    public JSONObject toJSON();
    public Object fromJSON(JSONObject json);

}
