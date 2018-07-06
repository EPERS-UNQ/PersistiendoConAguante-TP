package ar.edu.unq.epers.woe.backend.redisDAO;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class JsonSerializer {

    public static String toJson(Object object){
        return new Gson().toJson(object);
    }

    public static <T> List<T> fromJsonList(String json, Class<T> entityClass){
        return new Gson().fromJson(json, new ListParameterizedType(entityClass));
    }

    private static class ListParameterizedType implements ParameterizedType {

        private Type type;

        private ListParameterizedType(Type type) {
            this.type = type;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[] {type};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }

    }
}