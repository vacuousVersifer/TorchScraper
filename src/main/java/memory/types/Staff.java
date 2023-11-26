package memory.types;

import org.jdom2.Element;

public class Staff extends Data {
    private final int id;
    private final Name name;

    private final Element element;

    public Staff(Element element) {
        this.id = Integer.parseInt(element.getChild("id").getValue());
        this.name = new Name(element.getChild("name"));

        this.element = element;
    }

    public Staff(int id, String name) {
        this.id = id;
        this.name = new Name(name);

        this.element = new Element("staff");
        this.element.addContent(new Element("id").setText(String.valueOf(this.id)));
        this.element.addContent(this.name.getElement());
    }

    public Name getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public Element getElement() {
        return element;
    }
}
