import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static int playerSetScore = 0;
    private static int computerSetScore = 0;
    private static int gameDeckCounter = 0;
    
    public static void main(String[] args) {
        System.out.println("Welcome to BlueJack game");
        System.out.println("Game is starting!");
        Deck deck = new Deck();
        
        boolean isGamePlaying = true;
        while (isGamePlaying) {
            isGamePlaying = playSet(deck);
        }
        
        determineWinner();
        sc.close();
    }

    private static boolean playSet(Deck deck) {
        String[][] playerHandArray = deck.getPlayerHandArray();
        String[][] computerHandArray = deck.getComputerHandArray();
        String[][] finalGameDeck = deck.getFinalGameDeck();

        int playerScore = 0, computerScore = 0;
        boolean isSetPlaying = true, isPlayerStand = false;
        
        System.out.println("New set is starting");
        
        while (isSetPlaying) {
            if (!isPlayerStand) {
                playerScore = playerTurn(finalGameDeck, playerHandArray, playerScore);
                if (playerScore > 20) {
                    System.out.println("Player busted, computer won the set");
                    computerSetScore++;
                    return checkGameEnd();
                }
            }
            
            computerScore = computerTurn(finalGameDeck, computerHandArray, computerScore);
            if (computerScore > 20) {
                System.out.println("Computer busted, player won the set");
                playerSetScore++;
                return checkGameEnd();
            }
            
            if (isPlayerStand && computerScore >= 17) {
                isSetPlaying = false;
            }
        }
        
        return checkGameEnd();
    }
    
    private static int playerTurn(String[][] finalGameDeck, String[][] playerHandArray, int playerScore) {
        System.out.println("Player's turn");
        System.out.println(Arrays.deepToString(playerHandArray));
        System.out.println("1- Draw a card\n2- Play a card\n3- Stand");
        
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                int drawnCard = Integer.parseInt(finalGameDeck[gameDeckCounter][0]);
                playerScore += drawnCard;
                gameDeckCounter++;
                break;
            case 2:
                playerScore = playCard(playerHandArray, playerScore);
                break;
            case 3:
                System.out.println("Player chose to stand");
                break;
        }
        
        return playerScore;
    }

    private static int computerTurn(String[][] finalGameDeck, String[][] computerHandArray, int computerScore) {
        if (computerScore < 17) {
            int drawnCard = Integer.parseInt(finalGameDeck[gameDeckCounter][0]);
            System.out.println("Computer drew " + drawnCard);
            computerScore += drawnCard;
            gameDeckCounter++;
        }
        return computerScore;
    }

    private static int playCard(String[][] handArray, int score) {
        System.out.println("Choose a card to play (1-4):");
        int cardIndex = sc.nextInt() - 1;
        if (handArray[cardIndex][0] != null) {
            if (handArray[cardIndex][0].equals("x2")) {
                score *= 2;
            } else {
                score += Integer.parseInt(handArray[cardIndex][0]);
            }
            handArray[cardIndex][0] = null;
        }
        return score;
    }
    
    private static boolean checkGameEnd() {
        if (gameDeckCounter >= 29 || playerSetScore >= 3 || computerSetScore >= 3) {
            return false;
        }
        return true;
    }
    
    private static void determineWinner() {
        System.out.println("Final Score - Player: " + playerSetScore + " Computer: " + computerSetScore);
        if (playerSetScore > computerSetScore) {
            System.out.println("Player won the game!");
        } else if (computerSetScore > playerSetScore) {
            System.out.println("Computer won the game!");
        } else {
            System.out.println("Game is a tie!");
        }
    }
}
