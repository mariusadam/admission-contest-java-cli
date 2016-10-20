package helper.saver;

import org.apache.commons.lang.ArrayUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * @author Marius Adam
 */
public class JsonSaver extends FileSaver {
    @Override
    public String transform(Object object) {
        Field[] fields = this.getAllFields(object.getClass());
        JSONObject jsonObject = new JSONObject();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                jsonObject.put(field.getName(), field.get(object));
            } catch (JSONException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return jsonObject.toString();
    }

    private <T> Field[] getAllFields(Class<T> clazz) {
        Class<?> parent = clazz.getSuperclass();

        if(parent != null) {
            return (Field[]) ArrayUtils.addAll(clazz.getDeclaredFields(), getAllFields(parent));
        }

        return clazz.getDeclaredFields();
    }
}
