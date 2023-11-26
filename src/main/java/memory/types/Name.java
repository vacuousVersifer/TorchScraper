package memory.types;

import org.jdom2.Element;

public class Name extends Data {
    private final String first;
    private final String last;

    private final Element element;

    public Name(String full) {
        String[] parsed = full.split(" ");
        this.first = parsed[0];

        if (parsed.length > 1) {
            this.last = parsed[1];
        } else {
            this.last = "X";
        }

        this.element = new Element("name");
        this.element.addContent(new Element("first").setText(this.first));
        this.element.addContent(new Element("last").setText(this.last));
    }

    public Name(Element element) {
        this.first = element.getChild("first").getValue();
        this.last = element.getChild("last").getValue();

        this.element = element;
    }

    public String getFull() {
        return first + " " + last.charAt(0) + ".";
    }

    @Override
    public Element getElement() {
        return element;
    }
}
