package Memory.Types;

import org.jdom2.Element;

public class Story extends Data {
    private final int id;
    private final String name;
    private final Date date;

    private final Element element;

    public Story(int id, String name, Date date) {
        this.id = id;
        this.name = name;
        this.date = date;

        this.element = new Element("story");
        this.element.addContent(new Element("id").setText(String.valueOf(this.id)));
        this.element.addContent(new Element("name").setText(this.name));
        this.element.addContent(this.date.getElement());
    }

    public Story(Element element) {
        this.id = Integer.parseInt(element.getChild("id").getValue());
        this.name = element.getChild("name").getValue();
        this.date = new Date(element.getChild("date"));

        this.element = new Element("story");
        this.element.addContent(new Element("id").setText(String.valueOf(this.id)));
        this.element.addContent(new Element("name").setText(this.name));
        this.element.addContent(this.date.getElement());
    }

    @Override
    public Element getElement() {
        return element;
    }
}