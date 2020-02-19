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

/**
 * A utility class that provides XML parsing of the
 * game's XML data files, containing information about
 * the Cards, Rooms, and Parts in the game.
 *
 * @author Jamal Marri
 * @author Megan Theimer
 * @version 1.0
 * @see javax.xml.parsers
 * @see org.w3c.dom
 * @see org.xml.sax
 * @see ArrayList
 * @see HashMap
 * @see HashSet
 */
public class XMLParser {
    private final File cardsXML;
    private final File boardXML;
    private final DocumentBuilderFactory dbFactory;

    /**
     * Class constructor.
     */
    public XMLParser() {
        cardsXML = new File("cards.xml");
        boardXML = new File("board.xml");
        dbFactory = DocumentBuilderFactory.newInstance();
    }

    /**
     * Parses all Card information in 'cards.xml', and
     * returns it in the form of an ArrayList.
     *
     * @return the deck of Cards for the game, represented by an ArrayList.
     * @see Card
     * @see ArrayList
     */
    public ArrayList<Card> readCards() {
        // Declare and initialize deck as null by default
        ArrayList<Card> deck = null;

        try {
            // Attempt to declare and initialize builder, document and root node list
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document cards = dBuilder.parse(cardsXML);
            NodeList cardList = cards.getElementsByTagName("card");

            // Declare card node/element
            Node cardNode;
            Element cardElement;

            // Declare card variables
            String cardName;
            int sceneNumber;
            String description;
            int budget;

            // Declare card part variables
            String partName;
            String line;
            int level;

            // Initialize deck as new ArrayList
            deck = new ArrayList<>();

            // For every card node in the cards list
            for (int i = 0; i < cardList.getLength(); i++) {
                cardNode = cardList.item(i);

                // Cast node as an element if possible
                if (cardNode.getNodeType() == Node.ELEMENT_NODE) {
                    cardElement = (Element) cardNode;

                    // Initialize all card information variables using card attributes and child nodes
                    cardName = cardElement.getAttribute("name");
                    sceneNumber = parseInt(cardElement.getElementsByTagName("scene").item(0).getAttributes().getNamedItem("number").getTextContent());
                    description = cardElement.getElementsByTagName("scene").item(0).getTextContent().trim();
                    budget = parseInt(cardElement.getAttribute("budget"));

                    // Declare and initialize new card using card information variables
                    Card card = new Card(cardName, sceneNumber, description, budget, new HashSet<>());
                    deck.add(card);

                    // Declare and initialize node list of parts
                    NodeList parts = cardElement.getElementsByTagName("part");

                    // For every part node in parts node list
                    for (int j = 0; j < parts.getLength(); j++) {

                        // Cast node as an element if possible
                        if (parts.item(j).getNodeType() == Node.ELEMENT_NODE) {
                            Element partElement = (Element) parts.item(j);

                            // Initialize all part information variables using part attributes and child nodes
                            partName = parts.item(j).getAttributes().getNamedItem("name").getTextContent();
                            line = partElement.getElementsByTagName("line").item(0).getTextContent();
                            level = parseInt(parts.item(j).getAttributes().getNamedItem("level").getTextContent());

                            // Add part to card's 'parts' HashSet
                            card.addPart(new Part(partName, line, level));
                        }
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            // This should never happen, so long as the XML documents remain unaltered
            e.printStackTrace();
        }

        return deck;
    }

    /**
     * Parses all basic Room information in 'board.xml',
     * and returns it in the form of a HashMap.
     * All of the Rooms read are actually Sets, since
     * the only two Rooms in the game are initialized
     * before this method is executed.
     *
     * @return the map of Rooms for the game, represented by a HashMap.
     * @see Room
     * @see Set
     * @see HashMap
     */
    public HashMap<String, Room> readRooms() {
        // Declare and initialize rooms as null by default
        HashMap<String, Room> sets = null;

        try {
            // Attempt to declare and initialize builder, document and root node list
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document board = dBuilder.parse(boardXML);
            NodeList setList = board.getElementsByTagName("set");

            // Declare set node/element as well as the takes element
            Node setNode;
            Element setElement;
            Element takesElement;

            // Declare set variables
            String setName;
            int defaultTakes;

            // Declare part variables
            String partName;
            String line;
            int level;

            // Initialize sets as new HashMap
            sets = new HashMap<>();

            // For every set node in the set node list
            for (int i = 0; i < setList.getLength(); i++) {
                setNode = setList.item(i);

                // Cast node as element if possible
                if (setNode.getNodeType() == Node.ELEMENT_NODE) {
                    setElement = (Element) setNode;

                    // Initialize set information variables using set attributes and child nodes
                    setName = setElement.getAttributes().getNamedItem("name").getTextContent();
                    takesElement = (Element) setElement.getElementsByTagName("takes").item(0);
                    defaultTakes = takesElement.getElementsByTagName("take").getLength();

                    // Declare and initialize new set using set information variables
                    Set set = new Set(setName, new HashSet<>(), defaultTakes, new HashSet<>());
                    sets.put(setName, set);

                    // Declare and initialize node list of parts
                    NodeList parts = setElement.getElementsByTagName("part");

                    // For every part node in parts node list
                    for (int j = 0; j < parts.getLength(); j++) {

                        // Cast node as an element if possible
                        if (parts.item(j).getNodeType() == Node.ELEMENT_NODE) {
                            Element partElement = (Element) parts.item(j);

                            // Initialize all part information variables using part attributes and child nodes
                            partName = parts.item(j).getAttributes().getNamedItem("name").getTextContent();
                            line = partElement.getElementsByTagName("line").item(0).getTextContent();
                            level = parseInt(parts.item(j).getAttributes().getNamedItem("level").getTextContent());

                            // Add part to set's 'parts' HashSet
                            set.addPart(new Part(partName, line, level));
                        }
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            // This should never happen, so long as the XML documents remain unaltered
            e.printStackTrace();
        }

        return sets;
    }

    /**
     * Parses all neighbor information for the inputted
     * Room in 'boards.xml', and returns it in the form
     * of a HashSet.
     *
     * @param room the Room whose neighbors will be parsed and returned.
     * @return the set of neighboring Rooms for this Room in the form of a HashSet of Room names.
     * @see Room
     * @see HashSet
     */
    public HashSet<String> readNeighbors(Room room) {

        // Declare and initialize neighbors as null by default
        HashSet<String> neighbors = null;

        try {
            // Attempt to declare and initialize builder and document
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document board = dBuilder.parse(boardXML);

            // Initialize neighbors as new HashSet
            neighbors = new HashSet<>();

            // Special case for 'Trailers' room
            if (room.getName().equals("Trailers")) {

                // Locate, declare, and initialize the neighbors node list
                Node trailerNode = board.getElementsByTagName("trailer").item(0);
                Element trailerElement = (Element) trailerNode;
                Element neighborsElement = (Element) trailerElement.getElementsByTagName("neighbors").item(0);
                NodeList trailerNeighbors = neighborsElement.getElementsByTagName("neighbor");

                // For every child node of the neighbors node list, add it to the neighbors HashSet
                for (int i = 0; i < trailerNeighbors.getLength(); i++) {
                    neighbors.add(trailerNeighbors.item(i).getAttributes().item(0).getTextContent());
                }

                return neighbors;
                // Special case for 'Casting Office' room
            } else if (room.getName().equals("Casting Office")) {

                // Locate, declare, and initialize the neighbors node list
                Node officeNode = board.getElementsByTagName("office").item(0);
                Element officeElement = (Element) officeNode;
                Element neighborsElement = (Element) officeElement.getElementsByTagName("neighbors").item(0);
                NodeList officeNeighbors = neighborsElement.getElementsByTagName("neighbor");

                // For every child node of the neighbors node list, add it to the neighbors HashSet
                for (int i = 0; i < officeNeighbors.getLength(); i++) {
                    neighbors.add(officeNeighbors.item(i).getAttributes().item(0).getTextContent());
                }

                return neighbors;
                // Default case for all sets
            } else {

                // Locate the correct set based on name
                int i = 0;
                while (!board.getElementsByTagName("set").item(i).getAttributes().getNamedItem("name").getTextContent().equals(room.getName())) {
                    i++;
                }

                // Locate, declare, and initialize the neighbors node list
                Node setNode = board.getElementsByTagName("set").item(i);
                Element setElement = (Element) setNode;
                Element neighborsElement = (Element) setElement.getElementsByTagName("neighbors").item(0);
                NodeList setNeighbors = neighborsElement.getElementsByTagName("neighbor");

                // For every child node of the neighbors node list, add it to the neighbors HashSet
                for (int j = 0; j < setNeighbors.getLength(); j++) {
                    neighbors.add(setNeighbors.item(j).getAttributes().item(0).getTextContent());
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            // This should never happen, so long as the XML documents remain unaltered
            e.printStackTrace();
        }

        return neighbors;
    }

    /**
     * Parses and stores all pricing information for the
     * inputted currency, then returns it as an integer
     * array.
     *
     * @param currency the type of currency to parse the prices of.
     * @return the pricing information in the form of an integer array.
     */
    public int[] readPricing(String currency) {

        // Declare and initialize pricing as null by default
        int[] pricing = null;

        try {
            // Attempt to declare and initialize builder and document
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document board = dBuilder.parse(boardXML);

            // Initialize pricing as new integer array
            pricing = new int[5];

            // Locate, declare, and initialize the upgrades node list
            Node officeNode = board.getElementsByTagName("office").item(0);
            Element officeElement = (Element) officeNode;
            Element upgradesElement = (Element) officeElement.getElementsByTagName("upgrades").item(0);
            NodeList upgradesList = upgradesElement.getElementsByTagName("upgrade");

            // For every child node in the upgrades node list, if it is the correct currency,
            // then store its amount as a value in the prices array using the level for the index.
            int i;
            for (int j = 0; j < upgradesList.getLength(); j++) {
                if (upgradesList.item(j).getAttributes().getNamedItem("currency").getTextContent().equals(currency)) {
                    i = parseInt(upgradesList.item(j).getAttributes().getNamedItem("level").getTextContent());
                    pricing[i - 2] = parseInt(upgradesList.item(j).getAttributes().getNamedItem("amt").getTextContent());
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            // This should never happen, so long as the XML documents remain unaltered
            e.printStackTrace();
        }

        return pricing;
    }
}
