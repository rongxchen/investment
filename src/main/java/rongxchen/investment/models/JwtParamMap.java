package rongxchen.investment.models;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class JwtParamMap {

    private String appId;

    private Map<String, String> map;

    public JwtParamMap() {
        this.map = new HashMap<>();
    }

    public void addParam(String key, String value) {
        this.map.put(key, value);
    }

    public String getParam(String key) {
        return this.map.get(key);
    }

}
