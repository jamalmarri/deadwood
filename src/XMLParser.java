import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * A utility class that provides XML parsing of the game's XML data files, containing information
 * about the Cards, Rooms, and Parts in the game.
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
 * @see Card
 * @see Part
 * @see Room
 * @see Set
 */
public class XMLParser {
    /** Whether or not the XMLParser object has been initialized. */
    private static boolean objectExists = false;

    /** The one XMLParser object for this Singleton class. */
    private static XMLParser xmlParser;

    /** The Cards XML File. */
    private final File cardsXML;

    /** The Board XML File. */
    private final File boardXML;

    /** The DocumentBuilderFactory this XMLParser uses to get instances of DocumentBuilders. */
    private final DocumentBuilderFactory dbFactory;

    /** Class constructor. */
    private XMLParser() {
        cardsXML = new File("cards.xml");
        boardXML = new File("board.xml");
        dbFactory = DocumentBuilderFactory.newInstance();
    }

    /**
     * Ensures exactly one XMLParser exists and returns it.
     *
     * @return the XMLParser object.
     */
    public static XMLParser getInstance() {
        if (!objectExists) {
            xmlParser = new XMLParser();
            objectExists = true;
        }
        return xmlParser;
    }

    /**
     * Parses all Card information in 'cards.xml', and returns it in the form of an ArrayList.
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
            int budget;
            String pathToCard;
            // Initialize deck as new ArrayList
            deck = new ArrayList<>();
            // For every card node in the cards list
            for (int i = 0; i < cardList.getLength(); i++) {
                // Cast node as an element
                cardNode = cardList.item(i);
                cardElement = (Element) cardNode;
                // Initialize all card information variables using card attributes and child
                // nodes
                cardName = cardElement.getAttribute("name");
                budget = Integer.parseInt(cardElement.getAttribute("budget"));
                pathToCard = "img/cards/" + cardElement.getAttribute("img");
                // Declare and initialize new card using card information variables
                Card card = new Card(cardName, budget, pathToCard, new HashSet<>());
                deck.add(card);
                // Declare and initialize node list of parts
                NodeList parts = cardElement.getElementsByTagName("part");
                addParts(card, parts);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            // This should never happen, so long as the XML documents remain unaltered
            e.printStackTrace();
        }
        return deck;
    }

    /**
     * Parses all basic Room information in 'board.xml', and returns it in the form of a HashMap.
     * All of the Rooms read are actually Sets, since the only two Rooms in the game are initialized
     * before this method is executed.
     *
     * @return the map of Rooms for the game, represented by a HashMap.
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
            int setID;
            int defaultTakes;
            int x;
            int y;
            int cardX;
            int cardY;
            int[] takeXValues;
            int[] takeYValues;
            // Initialize sets as new HashMap
            sets = new HashMap<>();
            // For every set node in the set node list
            for (int i = 0; i < setList.getLength(); i++) {
                setNode = setList.item(i);
                // Cast node as element
                setElement = (Element) setNode;
                // Initialize set information variables using set attributes and child nodes
                setName = setElement.getAttribute("name");
                setID = Integer.parseInt(setElement.getAttribute("id"));
                Element areaElement = (Element) setElement.getElementsByTagName("area").item(0);
                x = Integer.parseInt(areaElement.getAttribute("x"));
                y = Integer.parseInt(areaElement.getAttribute("y"));
                cardX = Integer.parseInt(areaElement.getAttribute("cardX"));
                cardY = Integer.parseInt(areaElement.getAttribute("cardY"));
                takesElement = (Element) setElement.getElementsByTagName("takes").item(0);
                defaultTakes = takesElement.getElementsByTagName("take").getLength();
                takeXValues = new int[defaultTakes];
                takeYValues = new int[defaultTakes];
                // For every take node in the takes element
                for (int j = 0; j < defaultTakes; j++) {
                    // Cast node as element
                    Element takeElement =
                            (Element) takesElement.getElementsByTagName("take").item(j);
                    // Initialize take coordinate information variables using take attributes
                    int takeNumber = Integer.parseInt(takeElement.getAttribute("number")) - 1;
                    Element takeArea = (Element) takeElement.getElementsByTagName("area").item(0);
                    takeXValues[takeNumber] = Integer.parseInt(takeArea.getAttribute("x"));
                    takeYValues[takeNumber] = Integer.parseInt(takeArea.getAttribute("y"));
                }
                // Declare and initialize new set using set information variables
                Set set =
                        new Set(
                                setName,
                                x,
                                y,
                                new HashSet<>(),
                                setID,
                                defaultTakes,
                                cardX,
                                cardY,
                                takeXValues,
                                takeYValues,
                                new HashSet<>());
                sets.put(setName, set);
                // Declare and initialize node list of parts
                NodeList parts = setElement.getElementsByTagName("part");
                addParts(set, parts);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            // This should never happen, so long as the XML documents remain unaltered
            e.printStackTrace();
        }
        return sets;
    }

    /**
     * Adds all Parts found in the parts NodeList and adds them to the parent Object.
     *
     * @param parent the Set or Card to add the Parts to
     * @param parts the NodeList of all Parts belonging to the parent
     * @see Set
     * @see Card
     * @see Part
     */
    private void addParts(Object parent, NodeList parts) {
        // Declare part variables
        String partName;
        int level;
        int partX;
        int partY;
        int diceSize;
        // For every part node in parts node list
        for (int j = 0; j < parts.getLength(); j++) {
            // Cast node as an element if possible
            if (parts.item(j).getNodeType() == Node.ELEMENT_NODE) {
                Element partElement = (Element) parts.item(j);
                // Initialize all part information variables using part attributes and child nodes
                partName = partElement.getAttribute("name");
                level = Integer.parseInt(partElement.getAttribute("level"));
                Element partArea = (Element) partElement.getElementsByTagName("area").item(0);
                partX = Integer.parseInt(partArea.getAttribute("x"));
                partY = Integer.parseInt(partArea.getAttribute("y"));
                diceSize = Integer.parseInt(partArea.getAttribute("h"));
                // Add part to object's 'parts' HashSet
                if (parent instanceof Set) {
                    ((Set) parent).addPart(new Part(partName, level, partX, partY, diceSize));
                } else {
                    ((Card) parent).addPart(new Part(partName, level, partX, partY, diceSize));
                }
            }
        }
    }

    /**
     * Parses all neighbor information for the inputted Room in 'boards.xml', and returns it in the
     * form of a HashSet.
     *
     * @param roomName the name of the Room whose neighbors will be parsed and returned
     * @return the set of neighboring Rooms for this Room in the form of a HashSet of Room names
     * @see Room
     * @see HashSet
     */
    public HashSet<String> readNeighbors(String roomName) {
        // Declare and initialize neighbors as null by default
        HashSet<String> neighbors = null;
        try {
            // Attempt to declare and initialize builder and document
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document board = dBuilder.parse(boardXML);
            // Initialize neighbors as new HashSet
            neighbors = new HashSet<>();
            // Special case for 'Trailers' room
            if (roomName.equals("Trailers")) {
                // Locate, declare, and initialize the neighbors node list
                Node trailerNode = board.getElementsByTagName("trailer").item(0);
                Element trailerElement = (Element) trailerNode;
                addNeighbors(neighbors, trailerElement);
                return neighbors;
                // Special case for 'Casting Office' room
            } else if (roomName.equals("Casting Office")) {
                // Locate, declare, and initialize the neighbors node list
                Node officeNode = board.getElementsByTagName("office").item(0);
                Element officeElement = (Element) officeNode;
                addNeighbors(neighbors, officeElement);
                return neighbors;
                // Default case for all sets
            } else {
                // Locate the correct set based on name
                int i = 0;
                while (!((Element) board.getElementsByTagName("set").item(i))
                        .getAttribute("name")
                        .equals(roomName)) {
                    i++;
                }
                // Locate, declare, and initialize the neighbors node list
                Node setNode = board.getElementsByTagName("set").item(i);
                Element setElement = (Element) setNode;
                addNeighbors(neighbors, setElement);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            // This should never happen, so long as the XML documents remain unaltered
            e.printStackTrace();
        }
        return neighbors;
    }

    /**
     * Adds all Rooms found in the roomElement into the neighbors HashSet.
     *
     * @param neighbors the HashSet of neighbors
     * @param roomElement the Element of all neighboring Rooms
     * @see HashSet
     * @see Room
     */
    private void addNeighbors(HashSet<String> neighbors, Element roomElement) {
        Element neighborsElement = (Element) roomElement.getElementsByTagName("neighbors").item(0);
        NodeList roomNeighbors = neighborsElement.getElementsByTagName("neighbor");
        // For every child node of the neighbors node list, add it to the neighbors HashSet
        for (int i = 0; i < roomNeighbors.getLength(); i++) {
            neighbors.add(((Element) roomNeighbors.item(i)).getAttribute("name"));
        }
    }

    /**
     * Parses and stores all pricing information for the inputted currency, then returns it as an
     * integer array.
     *
     * @param currency the type of currency to parse the prices of
     * @return the pricing information in the form of an integer array
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
            Element upgradesElement =
                    (Element) officeElement.getElementsByTagName("upgrades").item(0);
            NodeList upgradesList = upgradesElement.getElementsByTagName("upgrade");
            // For every child node in the upgrades node list, if it is the correct currency,
            // then store its amount as a value in the prices array using the level for the index.
            int i;
            for (int j = 0; j < upgradesList.getLength(); j++) {
                if (((Element) upgradesList.item(j)).getAttribute("currency").equals(currency)) {
                    i = Integer.parseInt(((Element) upgradesList.item(j)).getAttribute("level"));
                    pricing[i - 2] =
                            Integer.parseInt(((Element) upgradesList.item(j)).getAttribute("amt"));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            // This should never happen, so long as the XML documents remain unaltered
            e.printStackTrace();
        }
        return pricing;
    }
}
