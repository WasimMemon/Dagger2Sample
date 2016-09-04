package com.androprogrammer.dagger2sample.domain.network;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;


public class RequestBuilder {

    private static final String TAG = RequestBuilder.class.getSimpleName();

    // TODO: add api version
    public static final String SERVER_URL_API = "http://jsonplaceholder.typicode.com/users";

    public static HashMap<String, String> getRequestParameter(Object request) {

        JSONObject jObjReq = new JSONObject();

        HashMap<String, String> parameters = new HashMap<>();

        try {
            if (request != null) {
                for (Map.Entry<String, String> row : mapProperties(request).entrySet()) {

                    jObjReq.put(row.getKey(), row.getValue());

                }
            }

            //jObjReq.put(AppConstants.TAG_LOGINTOKEN, AppConstants.DEVICE_TOKEN);

            Log.i(TAG, jObjReq.toString());
            //parameters.put(AppConstants.TAG_PARAMS, CipherUtils.encrypt(jObjReq.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parameters;
    }


    public static Map<String, String> mapProperties(Object bean) throws Exception {
        Map<String, String> properties = new HashMap<>();
        try {
            for (Method method : bean.getClass().getDeclaredMethods()) {
                if (Modifier.isPublic(method.getModifiers())
                        && method.getParameterTypes().length == 0
                        && method.getReturnType() != void.class
                        && method.getName().matches("^(get|is).+")
                        ) {
                    String name = method.getName().replaceAll("^(get|is)", "");
                    name = Character.toLowerCase(name.charAt(0)) + (name.length() > 1 ? name.substring(1) : "");

                    Object objValue = method.invoke(bean);

                    if (objValue != null) {
                        String value = String.valueOf(objValue);
                        //String value = method.invoke(bean).toString();
                        properties.put(name, value);
                    } else {
                        properties.put(name, "");
                    }

                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return properties;
    }
}



