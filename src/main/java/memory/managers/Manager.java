package memory.managers;

import memory.types.Data;
import org.jdom2.Element;

import java.util.ArrayList;

public class Manager<T extends Data> {
    private final String title;

    private final ArrayList<T> elements = new ArrayList<>();

    public Manager(String title) {
        this.title = title;
    }

    public void add(T data) {
        elements.add(data);
    }

    public Element getElement() {
        Element element = new Element(title);
        for (Data data : elements) {
            Element dataElement = data.getElement();
            dataElement.detach();
            element.addContent(dataElement);
        }
        return element;
    }

    public ArrayList<T> getElements() {
        return elements;
    }

    public void clearElements() {
        elements.clear();
    }
}
