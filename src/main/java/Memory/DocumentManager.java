package Memory;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DocumentManager {
    private final String filePath = "memory.torch";
    private final File file = new File(filePath);
    private final XMLOutputter outputter = new XMLOutputter();

    public DocumentManager() {
        outputter.setFormat(Format.getPrettyFormat());
    }

    public void create() throws IOException {
        FileWriter writer = new FileWriter(file);

        Document doc = new Document();
        doc.setRootElement(new Element("memory"));

        Element test = new Element("test");
        test.setAttribute("id", String.valueOf(100));

        test.addContent(new Element("name").setText("mkyong"));
        test.addContent(new Element("role").setText("support"));
        test.addContent(new Element("salary")
                .setAttribute("current", "USD").setText("5000"));

        doc.getRootElement().addContent(test);

        outputter.output(doc, writer);
    }
}
