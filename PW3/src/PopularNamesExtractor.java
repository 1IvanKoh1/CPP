import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;

public class PopularNamesExtractor {
    public static void main(String[] args) {
        try {
            int topNamesCount = 5;
            File xmlFile = new File("Popular_Baby_Names_NY.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            NodeList rowList = doc.getElementsByTagName("row");

            Map<String, Map<String, Integer>> topNamesMap = new HashMap<>();

            for (int i = 0; i < rowList.getLength(); i++) {
                Element row = (Element) rowList.item(i);

                String ethnicGroup = row.getElementsByTagName("ethcty").item(0).getTextContent();

                String name = row.getElementsByTagName("nm").item(0).getTextContent();
                String gender = row.getElementsByTagName("gndr").item(0).getTextContent();
                int count = Integer.parseInt(row.getElementsByTagName("cnt").item(0).getTextContent());
                int rank = Integer.parseInt(row.getElementsByTagName("rnk").item(0).getTextContent());

                Map<String, Integer> ethnicTopNamesMap = topNamesMap.getOrDefault(ethnicGroup, new HashMap<>());

                ethnicTopNamesMap.put(name, ethnicTopNamesMap.getOrDefault(name, 0) + count);

                topNamesMap.put(ethnicGroup, ethnicTopNamesMap);
            }

            DocumentBuilderFactory newFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder newBuilder = newFactory.newDocumentBuilder();
            Document newDoc = newBuilder.newDocument();

            Element rootElement = newDoc.createElement("TopNames");
            newDoc.appendChild(rootElement);

            for (Map.Entry<String, Map<String, Integer>> entry : topNamesMap.entrySet()) {
                String ethnicGroup = entry.getKey();
                Map<String, Integer> ethnicTopNamesMap = entry.getValue();

                List<Map.Entry<String, Integer>> sortedTopNames = new ArrayList<>(ethnicTopNamesMap.entrySet());
                sortedTopNames.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

                sortedTopNames = sortedTopNames.subList(0, Math.min(topNamesCount, sortedTopNames.size()));

                for (Map.Entry<String, Integer> nameEntry : sortedTopNames) {
                    Element nameElement = newDoc.createElement("Name");
                    nameElement.setAttribute("EthnicGroup", ethnicGroup);
                    nameElement.setAttribute("Gender", getGenderForName(nameEntry.getKey(), doc));
                    nameElement.setAttribute("Count", Integer.toString(nameEntry.getValue()));
                    nameElement.setTextContent(nameEntry.getKey());
                    rootElement.appendChild(nameElement);
                }
            }
            javax.xml.transform.TransformerFactory transformerFactory = javax.xml.transform.TransformerFactory.newInstance();
            javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
            javax.xml.transform.dom.DOMSource source = new javax.xml.transform.dom.DOMSource(newDoc);
            javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(new File("TopNames.xml"));
            transformer.transform(source, result);

            System.out.println("Топові імена збережено у файл TopNames.xml");

            // Виведення даних зі збереженого файлу у консоль
            System.out.println("Дані з файлу TopNames.xml:");
            printTopNamesFromXML("TopNames.xml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getGenderForName(String name, Document doc) {
        NodeList rowList = doc.getElementsByTagName("row");
        for (int i = 0; i < rowList.getLength(); i++) {
            Element row = (Element) rowList.item(i);
            String currentName = row.getElementsByTagName("nm").item(0).getTextContent();
            if (currentName.equals(name)) {
                return row.getElementsByTagName("gndr").item(0).getTextContent();
            }
        }
        return "";
    }

    private static void printTopNamesFromXML(String filename) {
        try {
            File xmlFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            NodeList nameList = doc.getElementsByTagName("Name");

            for (int i = 0; i < nameList.getLength(); i++) {
                Element nameElement = (Element) nameList.item(i);
                String ethnicGroup = nameElement.getAttribute("EthnicGroup");
                String gender = nameElement.getAttribute("Gender");
                int count = Integer.parseInt(nameElement.getAttribute("Count"));
                String name = nameElement.getTextContent();

                System.out.println("Етнічна група: " + ethnicGroup + ", Ім'я: " + name + ", Стать: " + gender + ", Кількість: " + count);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
