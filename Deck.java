import java.util.Random;
import java.util.Scanner;

public class Deck {
    private String computerHand;
    private String playerHand;
    private String finalComputerHand;
    private String finalPlayerHand;
    private String[][] computerHandArray;
    private String[][] playerHandArray;
    private String[][] finalGameDeck;
    private String[] deck;
    
    private Scanner scanner = new Scanner(System.in);
    private Random random = new Random(System.currentTimeMillis());

    public Deck() {
        initializeDeck();
        shuffleDeck();
        dealHands();
        processHands();
        setupFinalGameDeck();
        displayHands();
    }

    private void initializeDeck() {
        String[] colors = {"R", "G", "B", "Y"};
        String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        deck = new String[40];
        
        int index = 0;
        for (String color : colors) {
            for (String number : numbers) {
                deck[index++] = number + color;
            }
        }
    }

    private void shuffleDeck() {
        for (int i = 0; i < deck.length; i++) {
            int randomIndex = random.nextInt(deck.length);
            String temp = deck[i];
            deck[i] = deck[randomIndex];
            deck[randomIndex] = temp;
        }
    }

    private void dealHands() {
        String[] signs = {"+", "-"};
        computerHand = "";
        playerHand = "";

        for (int i = 0; i < 5; i++) {
            String sign = signs[random.nextInt(signs.length)];
            computerHand += (i == 0 ? "" : ",") + sign + deck[i];
            playerHand += (i == 0 ? "" : ",") + sign + deck[deck.length - 1 - i];
        }

        addSpecialCards(computerHand);
        addSpecialCards(playerHand);
    }

    private void addSpecialCards(String hand) {
        String[] signs = {"+", "-"};
        String[] colors = {"R", "G", "B", "Y"};
        
        for (int i = 0; i < 3; i++) {
            String sign = signs[random.nextInt(signs.length)];
            String color = colors[random.nextInt(colors.length)];
            int value = random.nextInt(6) + 1;
            hand += "," + sign + value + color;
        }
    }

    private void processHands() {
        computerHandArray = new String[4][3];
        playerHandArray = new String[4][3];
        finalGameDeck = new String[30][2];
        
        finalComputerHand = selectFinalHand(computerHand.split(","), computerHandArray);
        finalPlayerHand = selectFinalHand(playerHand.split(","), playerHandArray);
    }

    private String selectFinalHand(String[] handArray, String[][] handMatrix) {
        String[] finalHand = new String[4];
        boolean[] usedIndices = new boolean[handArray.length]; // Kullanılan indeksleri takip etmek için

        for (int i = 0; i < 4; i++) {
            int randomIndex;
            do {
                randomIndex = random.nextInt(handArray.length);
            } while (usedIndices[randomIndex] || handArray[randomIndex] == null); // Daha önce seçilmiş mi kontrolü
            
            finalHand[i] = handArray[randomIndex];
            usedIndices[randomIndex] = true; // Bu indeksi artık kullanma
        }
        return String.join(",", finalHand);
    }

    private void setupFinalGameDeck() {
        for (int i = 0; i < 30; i++) {
            String card = deck[i + 5];
            finalGameDeck[i] = card.startsWith("10") ? new String[]{"10", card.substring(2)} : new String[]{String.valueOf(card.charAt(0)), String.valueOf(card.charAt(1))};
        }
    }

    private void displayHands() {
        System.out.println("Computer Hand: X, X, X, X");
        System.out.println("Player Hand: " + finalPlayerHand);
    }

    public String getComputerHand() {
        return computerHand;
    }

    public String getPlayerHand() {
        return playerHand;
    }

    public String getFinalComputerHand() {
        return finalComputerHand;
    }

    public String getFinalPlayerHand() {
        return finalPlayerHand;
    }

    public String[][] getComputerHandArray() {
        return computerHandArray;
    }

    public String[][] getPlayerHandArray() {
        return playerHandArray;
    }

    public String[][] getFinalGameDeck() {
        return finalGameDeck;
    }
}
