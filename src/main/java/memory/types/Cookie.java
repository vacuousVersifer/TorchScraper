package memory.types;

import org.jdom2.Element;

public class Cookie extends Data {
    private final String key;
    private final String value;
    private final String expirationDate;

    private final Element element;

    public Cookie(String key, String value, String expirationDate) {
        this.key = key;
        this.value = value;
        this.expirationDate = expirationDate;

        this.element = new Element("cookie");
        this.element.addContent(new Element("key").setText(this.key));
        this.element.addContent(new Element("value").setText(this.value));
        this.element.addContent(new Element("expirationDate").setText(this.expirationDate));
    }

    public Cookie(Element element) {
        this.key = element.getChild("key").getValue();
        this.value = element.getChild("value").getValue();
        this.expirationDate = element.getChild("expirationDate").getValue();

        this.element = element;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    @Override
    public Element getElement() {
        return element;
    }
}
