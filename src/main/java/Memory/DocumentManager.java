package Memory;

import Memory.Types.*;
import Utils.Logger;
import Utils.SectionName;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import javax.xml.XMLConstants;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DocumentManager {
    private final String filePath = "memory.torch";
    private final File file = new File(filePath);
    private final Manager<Cookie> cookieManager = new Manager<>(ListType.COOKIELIST.type());
    private final Manager<Staff> staffManager = new Manager<>(ListType.STAFFLIST.type());

    private final XMLOutputter outputter = new XMLOutputter();
    private final Manager<Story> storyManager = new Manager<>(ListType.STORYLIST.type());
    private FileWriter fileWriter;
    private Document memoryDocument;

    public DocumentManager() throws IOException, JDOMException {
        outputter.setFormat(Format.getPrettyFormat());

        create();
    }

    public void create() throws IOException, JDOMException {
        if (file.createNewFile()) {
            Logger.log(SectionName.MEMORY, "Memory file created");

            memoryDocument = new Document();
            fileWriter = new FileWriter(file);

            Element root = new Element("memory");
            memoryDocument.setRootElement(root);

            cookieManager.add(new Cookie("Test Key", "Test Value"));
            staffManager.add(new Staff(119, new Name("Ollie", "Cockrum")));
            storyManager.add(new Story(119, "Test Story", new Date(11, 24)));

            memoryDocument.getRootElement().addContent(cookieManager.getElement());
            memoryDocument.getRootElement().addContent(staffManager.getElement());
            memoryDocument.getRootElement().addContent(storyManager.getElement());

            save();
        } else {
            Logger.log(SectionName.MEMORY, "Memory file already existed");

            SAXBuilder sax = new SAXBuilder();
            sax.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            sax.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

            memoryDocument = sax.build(file);
            fileWriter = new FileWriter(file);

            Element root = memoryDocument.getRootElement();

            Element cookieList = root.getChild(ListType.COOKIELIST.type());
            for (Element cookie : cookieList.getChildren("cookie")) {
                cookieManager.add(new Cookie(cookie));
            }

            Element storyList = root.getChild(ListType.STORYLIST.type());
            for (Element story : storyList.getChildren("story")) {
                storyManager.add(new Story(story));
            }

            Element staffList = root.getChild(ListType.STAFFLIST.type());
            for (Element staff : staffList.getChildren("staff")) {
                staffManager.add(new Staff(staff));
            }

            save();
        }
    }

    private void save() throws IOException {
        outputter.output(memoryDocument, fileWriter);
    }
}
