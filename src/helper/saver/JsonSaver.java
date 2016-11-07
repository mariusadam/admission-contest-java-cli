package helper.saver;

import domain.HasId;
import org.apache.commons.lang.ArrayUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * @author Marius Adam
 */
public class JsonSaver<T> extends FileSaver<T> {
    @Override
    public String transform(Object object) {
        Field[] fields = this.getAllFields(object.getClass());
        JSONObject jsonObject = new JSONObject();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object fieldValue = field.get(object);
                if (fieldValue instanceof HasId) {
                    jsonObject.put(field.getName(), ((HasId)fieldValue).getId());
                } else {
                    jsonObject.put(field.getName(), fieldValue);
                }
            } catch (JSONException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return jsonObject.toString();
    }

    private <E> Field[] getAllFields(Class<E> clazz) {
        Class<?> parent = clazz.getSuperclass();

        if(parent != null) {
            return (Field[]) ArrayUtils.addAll(clazz.getDeclaredFields(), getAllFields(parent));
        }

        return clazz.getDeclaredFields();
    }
}
