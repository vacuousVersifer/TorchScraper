package Memory.Types;

import org.jdom2.Element;

public class Name extends Data {
    private final String first;
    private final String last;

    private final Element element;

    public Name(String first, String last) {
        this.first = first;
        this.last = last;

        this.element = new Element("name");
        this.element.addContent(new Element("first").setText(this.first));
        this.element.addContent(new Element("last").setText(this.last));
    }

    public Name(Element element) {
        this.first = element.getChild("first").getValue();
        this.last = element.getChild("last").getValue();

        this.element = new Element("name");
        this.element.addContent(new Element("first").setText(this.first));
        this.element.addContent(new Element("last").setText(this.last));
    }

    @Override
    public Element getElement() {
        return element;
    }
}
