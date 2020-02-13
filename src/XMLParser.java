import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static java.lang.Integer.parseInt;

public class XMLParser {
    private final File cardsXML = new File("cards.xml");
    private final File boardXML = new File("board.xml");
    private final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

    public ArrayList<Card> readCards() {
        ArrayList<Card> deck = null;

        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document cards = dBuilder.parse(cardsXML);
            NodeList cardList = cards.getElementsByTagName("card");
            Node cardNode;
            Element cardElement;

            String cardName;
            int sceneNumber;
            String description;
            int budget;

            String partName;
            String line;
            int level;

            deck = new ArrayList<>();

            for (int i = 0; i < cardList.getLength(); i++) {
                cardNode = cardList.item(i);
                if (cardNode.getNodeType() == Node.ELEMENT_NODE) {
                    cardElement = (Element) cardNode;

                    cardName = cardElement.getAttribute("name");
                    sceneNumber = parseInt(cardElement.getElementsByTagName("scene").item(0).getAttributes().getNamedItem("number").getTextContent());
                    description = cardElement.getElementsByTagName("scene").item(0).getTextContent().strip();
                    budget = parseInt(cardElement.getAttribute("budget"));

                    Card card = new Card(cardName, sceneNumber, description, budget, new HashSet<>());
                    deck.add(card);

                    NodeList parts = cardElement.getElementsByTagName("part");
                    for (int j = 0; j < parts.getLength(); j++) {
                        Element partElement = (Element) parts.item(j);

                        partName = parts.item(j).getAttributes().getNamedItem("name").getTextContent();
                        line = partElement.getElementsByTagName("line").item(0).getTextContent();
                        level = parseInt(parts.item(j).getAttributes().getNamedItem("level").getTextContent());

                        card.addPart(new Part(partName, line, level));
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return deck;
    }

    public HashMap<String, Room> readRooms() {
        HashMap<String, Room> sets = null;

        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document board = dBuilder.parse(boardXML);
            NodeList setList = board.getElementsByTagName("set");
            Node setNode;
            Element setElement;
            Element takesElement;

            String setName;
            int defaultTakes;

            String partName;
            String line;
            int level;

            sets = new HashMap<>();

            for (int i = 0; i < setList.getLength(); i++) {
                setNode = setList.item(i);
                if (setNode.getNodeType() == Node.ELEMENT_NODE) {
                    setElement = (Element) setNode;

                    setName = setElement.getAttributes().getNamedItem("name").getTextContent();
                    takesElement = (Element) setElement.getElementsByTagName("takes").item(0);
                    defaultTakes = takesElement.getElementsByTagName("take").getLength();

                    Set set = new Set(setName, new HashSet<>(), defaultTakes, new HashSet<>());
                    sets.put(setName, set);

                    NodeList parts = setElement.getElementsByTagName("part");
                    for (int j = 0; j < parts.getLength(); j++) {
                        Element partElement = (Element) parts.item(j);

                        partName = parts.item(j).getAttributes().getNamedItem("name").getTextContent();
                        line = partElement.getElementsByTagName("line").item(0).getTextContent();
                        level = parseInt(parts.item(j).getAttributes().getNamedItem("level").getTextContent());

                        set.addPart(new Part(partName, line, level));
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return sets;
    }

    public HashSet<String> readNeighbors(Room room) {
        HashSet<String> neighbors = null;

        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document board = dBuilder.parse(boardXML);
            neighbors = new HashSet<>();

            if (room.getName().equals("Trailers")) {
                Node trailerNode = board.getElementsByTagName("trailer").item(0);
                Element trailerElement = (Element) trailerNode;
                Element neighborsElement = (Element) trailerElement.getElementsByTagName("neighbors").item(0);
                NodeList trailerNeighbors = neighborsElement.getElementsByTagName("neighbor");
                for (int i = 0; i < trailerNeighbors.getLength(); i++) {
                    neighbors.add(trailerNeighbors.item(i).getAttributes().item(0).getTextContent());
                }
                return neighbors;
            } else if (room.getName().equals("Casting Office")) {
                Node officeNode = board.getElementsByTagName("office").item(0);
                Element officeElement = (Element) officeNode;
                Element neighborsElement = (Element) officeElement.getElementsByTagName("neighbors").item(0);
                NodeList officeNeighbors = neighborsElement.getElementsByTagName("neighbor");
                for (int i = 0; i < officeNeighbors.getLength(); i++) {
                    neighbors.add(officeNeighbors.item(i).getAttributes().item(0).getTextContent());
                }
                return neighbors;
            } else {
                int i = 0;
                while (!board.getElementsByTagName("set").item(i).getAttributes().getNamedItem("name").getTextContent().equals(room.getName())) {
                    i++;
                }
                Node setNode = board.getElementsByTagName("set").item(i);
                Element setElement = (Element) setNode;
                Element neighborsElement = (Element) setElement.getElementsByTagName("neighbors").item(0);
                NodeList setNeighbors = neighborsElement.getElementsByTagName("neighbor");
                for (int j = 0; j < setNeighbors.getLength(); j++) {
                    neighbors.add(setNeighbors.item(j).getAttributes().item(0).getTextContent());
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return neighbors;
    }

    public int[] readPricing(String currency) {
        int[] pricing = null;

        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document board = dBuilder.parse(boardXML);
            pricing = new int[5];
            Node officeNode = board.getElementsByTagName("office").item(0);
            Element officeElement = (Element) officeNode;
            Element upgradesElement = (Element) officeElement.getElementsByTagName("upgrades").item(0);
            NodeList upgradesList = upgradesElement.getElementsByTagName("upgrade");
            int i;

            for (int j = 0; j < upgradesList.getLength(); j++) {
                if (upgradesList.item(j).getAttributes().getNamedItem("currency").getTextContent().equals(currency)) {
                    i = parseInt(upgradesList.item(j).getAttributes().getNamedItem("level").getTextContent());
                    pricing[i - 2] = parseInt(upgradesList.item(j).getAttributes().getNamedItem("amt").getTextContent());
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return pricing;
    }
}
