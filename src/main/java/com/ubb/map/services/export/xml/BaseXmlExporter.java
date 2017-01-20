package com.ubb.map.services.export.xml;

import com.ubb.map.services.export.BaseExporter;
import com.ubb.map.services.export.ColumnsMapper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

/**
 * Created by marius on 1/20/2017.
 */
public class BaseXmlExporter<T> extends BaseExporter<T> {
    public BaseXmlExporter(List<T> items, String destinationPath) {
        super(items, destinationPath);
    }

    @Override
    protected Object call() throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement(getCollectionTagName());
        doc.appendChild(rootElement);

        int size = items.size();
        int sleepTime = getSleepTimePerStep(size);
        rootElement.setAttribute("size", String.valueOf(size));
        for (int i = 0; i < size; i++) {
            Element item = doc.createElement(getCollectionItemTagName());
            rootElement.appendChild(item);

            ColumnsMapper.getObjectMap(items.get(i)).forEach((s, s2) -> {
                if (s.equals(ColumnsMapper.ID_FIELD)) {
                    item.setAttribute(s, s2.toString());
                } else {
                    Element property = doc.createElement(s);
                    property.appendChild(doc.createTextNode(s2.toString()));
                    item.appendChild(property);
                }
            });

            if (i != size - 1) {
                Thread.sleep(sleepTime);
            }
            updateMessage("Added " + items.get(i));
            updateProgress(getPercentage(i, size), MAX_STEP);
        }
        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(destinationPath));
        transformer.transform(source, result);

        updateProgress(MAX_STEP, MAX_STEP);
        updateMessage("Done exporting " + size + " objects to xml to file " + destinationPath);
        return true;
    }

    protected String getCollectionTagName() {
        return "items";
    }

    protected String getCollectionItemTagName() {
        return "item";
    }
}
