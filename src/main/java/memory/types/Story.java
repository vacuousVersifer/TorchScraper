package memory.types;

import org.jdom2.Element;

public class Story extends Data {
    private final int id;
    private final String title;
    private final Date date;

    private final Element element;

    public Story(int id, String title, Date date) {
        this.id = id;
        this.title = title;
        this.date = date;

        this.element = new Element("story");
        this.element.addContent(new Element("id").setText(String.valueOf(this.id)));
        this.element.addContent(new Element("name").setText(this.title));
        this.element.addContent(this.date.getElement());
    }

    public Story(Element element) {
        this.id = Integer.parseInt(element.getChild("id").getValue());
        this.title = element.getChild("name").getValue();
        this.date = new Date(element.getChild("date"));

        this.element = element;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public Element getElement() {
        return element;
    }
}