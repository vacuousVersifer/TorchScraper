package Memory.Types;

import org.jdom2.Element;

public class Staff extends Data {
    private final int id;
    private final Name name;

    private final Element element;

    public Staff(int id, Name name) {
        this.id = id;
        this.name = name;

        this.element = new Element("staff");
        this.element.addContent(new Element("id").setText(String.valueOf(this.id)));
        this.element.addContent(this.name.getElement());
    }

    public Staff(Element element) {
        this.id = Integer.parseInt(element.getChild("id").getValue());
        this.name = new Name(element.getChild("name"));

        this.element = new Element("staff");
        this.element.addContent(new Element("id").setText(String.valueOf(this.id)));
        this.element.addContent(this.name.getElement());
    }


    @Override
    public Element getElement() {
        return element;
    }
}
