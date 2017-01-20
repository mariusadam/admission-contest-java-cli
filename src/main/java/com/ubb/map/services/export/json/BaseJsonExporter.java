package com.ubb.map.services.export.json;

import com.ubb.map.services.export.BaseExporter;
import com.ubb.map.services.export.ColumnsMapper;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;

/**
 * Created by marius on 1/20/2017.
 */
public class BaseJsonExporter<T> extends BaseExporter<T> {
    public BaseJsonExporter(List<T> items, String destinationPath) {
        super(items, destinationPath);
    }

    @Override
    protected Object call() throws Exception {
        int size = items.size();
        int sleepTime = getSleepTimePerStep(size);
        JSONObject jsonRoot = new JSONObject();
        JSONObject jsonCollection = new JSONObject();
        jsonRoot.put("size", size);
        jsonRoot.put("values", jsonCollection);
        for (int i = 0; i < size; i++) {
            Map<String, Object> columnsMap = ColumnsMapper.getObjectMap(items.get(i));
            JSONObject item = new JSONObject();
            jsonCollection.put(columnsMap.get(ColumnsMapper.ID_FIELD).toString(), item);
            columnsMap.forEach(item::put);

            if (i != size - 1) {
                Thread.sleep(sleepTime);
            }
            updateMessage("Added " + items.get(i));
            updateProgress(getPercentage(i, size), MAX_STEP);
        }
        // write the content into json file
        BufferedWriter writer = new BufferedWriter(new FileWriter(destinationPath));
        writer.write(jsonRoot.toString());
        writer.close();
        updateProgress(MAX_STEP, MAX_STEP);
        updateMessage("Done exporting " + size + " objects to json to file " + destinationPath);
        return true;
    }
}
