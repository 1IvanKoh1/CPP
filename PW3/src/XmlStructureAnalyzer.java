import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class XmlStructureAnalyzer {
    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                Set<String> tags = new HashSet<>();

                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    System.out.println("Тег: " + qName);
                    for (int i = 0; i < attributes.getLength(); i++) {
                        System.out.println("\tАтрибут: " + attributes.getQName(i) + " = " + attributes.getValue(i));
                    }
                    tags.add(qName);
                }

                public void endDocument() throws SAXException {
                    System.out.println("Перелік тегів:");
                    for (String tag : tags) {
                        System.out.println(tag);
                    }
                }
            };

            saxParser.parse("Popular_Baby_Names_NY.xml", handler);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
