package com.hyss.vihaanuser.Pojos;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yash on 28/10/17.
 */

public class Content implements Serializable {
    private Map<String,String> data;

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public void createData(String type, String latitude, String longitude, String time, String username, String password)
    {

        if (data==null)
        {
            data = new HashMap<String, String>();
        }
        data.put("type",type);
        data.put("latitude",latitude);
        data.put("longitude",longitude);
        data.put("time",time);
        data.put("username",username);
        data.put("password",password);
    }
}
