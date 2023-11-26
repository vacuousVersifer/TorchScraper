package Memory;

import Memory.Types.Data;
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
            element.addContent(data.getElement());
        }
        return element;
    }
}
