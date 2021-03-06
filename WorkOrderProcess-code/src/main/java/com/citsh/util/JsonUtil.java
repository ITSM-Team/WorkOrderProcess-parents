package com.citsh.util;


import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

public class JsonUtil {
	  /** logger. */
    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    /** jackson. */
    private ObjectMapper mapper;

    /** constructor. */
    public JsonUtil() {
        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public String toJson(Object object) throws IOException {
        return mapper.writeValueAsString(object);
    }

    public <T> T fromJson(String jsonString, Class<T> clazz) throws IOException {
        if ((jsonString == null) || "".equals(jsonString.trim())) {
            return null;
        }

        return mapper.readValue(jsonString, clazz);
    }

    /**
     * new TypeReference<List<String>>(){}
     */
    public <T> T fromJson(String jsonString, TypeReference typeReference)
            throws IOException {
        if ((jsonString == null) || "".equals(jsonString.trim())) {
            return null;
        }

        return (T) mapper.readValue(jsonString, typeReference);
    }

    public String toJsonP(String functionName, Object object)
            throws IOException {
        return toJson(new JSONPObject(functionName, object));
    }
}
