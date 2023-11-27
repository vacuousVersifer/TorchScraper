package memory;

import memory.managers.CookieManager;
import memory.managers.Manager;
import memory.types.Cookie;
import memory.types.Staff;
import memory.types.Story;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import utilities.Logger;
import utilities.SectionName;

import javax.xml.XMLConstants;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DocumentManager {
    private final String filePath = "memory.torch";
    private final File file = new File(filePath);
    private final CookieManager cookieManager = new CookieManager();
    private final Manager<Staff> staffManager = new Manager<>(ListType.STAFF_LIST.type());
    private final Manager<Story> storyManager = new Manager<>(ListType.STORY_LIST.type());
    private final XMLOutputter outputter = new XMLOutputter();
    private Element root;
    private Document memoryDocument;

    public DocumentManager() throws IOException, JDOMException {
        outputter.setFormat(Format.getPrettyFormat());

        create();
    }

    public void create() throws IOException, JDOMException {
        Element storyList;
        Element staffList;
        Element cookieList;
        if (file.createNewFile()) {
            Logger.log(SectionName.MEMORY, "Local memory file not found");

            memoryDocument = new Document();

            root = new Element("memory");
            memoryDocument.setRootElement(root);

            cookieList = cookieManager.getElement();
            staffList = staffManager.getElement();
            storyList = storyManager.getElement();

            memoryDocument.getRootElement().addContent(cookieList);
            memoryDocument.getRootElement().addContent(staffList);
            memoryDocument.getRootElement().addContent(storyList);

            save();
        } else {
            Logger.log(SectionName.MEMORY, "Local memory file found");

            SAXBuilder sax = new SAXBuilder();
            sax.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            sax.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

            memoryDocument = sax.build(file);

            root = memoryDocument.getRootElement();

            cookieList = root.getChild(ListType.COOKIE_LIST.type());
            for (Element cookie : cookieList.getChildren("cookie")) {
                cookieManager.add(new Cookie(cookie));
            }

            storyList = root.getChild(ListType.STORY_LIST.type());
            for (Element story : storyList.getChildren("story")) {
                storyManager.add(new Story(story));
            }

            staffList = root.getChild(ListType.STAFF_LIST.type());
            for (Element staff : staffList.getChildren("staff")) {
                staffManager.add(new Staff(staff));
            }

            save();
        }
    }

    private void save() throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        outputter.output(memoryDocument, fileWriter);
        fileWriter.close();
    }

    // Cookie Block
    public void addCookie(Cookie cookie) {
        cookieManager.add(cookie);
    }

    public String getCookieList() {
        return cookieManager.getCookie();
    }

    public void clearCookies() throws IOException {
        cookieManager.clearElements();
        save();
    }

    public void saveCookies() throws IOException {
        root.removeChild(ListType.COOKIE_LIST.type());
        root.addContent(cookieManager.getElement());
        save();
    }

    // Staff Block
    public void addStaffList(ArrayList<Staff> staffList) {
        for (Staff staff : staffList) {
            staffManager.add(staff);
        }
    }

    public ArrayList<Staff> getStaffList() {
        return staffManager.getElements();
    }

    public void clearStaff() throws IOException {
        staffManager.clearElements();
        save();
    }

    public void saveStaff() throws IOException {
        root.removeChild(ListType.STAFF_LIST.type());
        root.addContent(staffManager.getElement());
        save();
    }

    // Story Block
    public void addStoryList(ArrayList<Story> storyList) {
        for (Story story : storyList) {
            storyManager.add(story);
        }
    }

    public ArrayList<Story> getStoriesList() {
        return storyManager.getElements();
    }

    public void clearStories() throws IOException {
        storyManager.clearElements();
        save();
    }

    public void saveStories() throws IOException {
        root.removeChild(ListType.STORY_LIST.type());
        root.addContent(storyManager.getElement());
        save();
    }
}
