package memory.types;

import org.jdom2.Element;

public class Date extends Data {
    private final int month;
    private final int year;

    private final Element element;

    public Date(int month, int year) {
        this.month = month;
        this.year = year;

        this.element = new Element("date");
        this.element.addContent(new Element("month").setText(String.valueOf(this.month)));
        this.element.addContent(new Element("year").setText(String.valueOf(this.year)));
    }

    public Date(Element element) {
        this.month = Integer.parseInt(element.getChild("month").getValue());
        this.year = Integer.parseInt(element.getChild("year").getValue());

        this.element = element;
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public String toString() {
        return month + "/" + year;
    }
}
