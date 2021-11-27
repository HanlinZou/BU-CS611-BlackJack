Name_1: Hanlin Zou
Email_1: hzou7@bu.edu
BU ID_1: U96634471

Name_2: Zhaoyu Yin
Email_2: zyyin@bu.edu
BU ID_2: U26267142

Compilation instructions:
For users using IDEs, directly compile(build) and run to check out the execution in the console.
For user using cmd or terminal, try Example:
> javac *.java
> java Main.java

Description:
1. Main: Main class to initiate the card games.
2. Card Class: Representative of a card in Card game.
3. Deck Class: Representative of a deck of cards
4. Hand Abstract Class: Represents a hand of cards of a gambler
5. BjHand Class: Represents a hand of cards of a gambler in BJ
6. TriHand Class: Represents a hand of cards of a gambler in TE
7. Player_Interface: Designed to satisfy potential scalability and extendability purpose. Convenient to build features such as playerId, teams, etc.
8. Player Abstract Class: An abstract class with implement on player interface. Which can be further designed. For example, gamblers and dealers in blackjack game. Provided with some supplement method to make player functional.
9. BjGambler Class: Representative of a gambler in blackjack.
10. Dealer Class: Representative of a dealer in blackjack.
11. TriGambler Class: Representative of a gambler in TriantaEna.
12. Blackjack_Game Class: Contains the logic of card game Blackjack. When user inputs 1, blackjack game will be initiated.
13. TriantaEna_Game Class: Contains the logic of card game TriantaEna. When user inputs 2, TriantaEna game will be initiated.
14. Menu Class: Representative of game center. After the game is run, the user would be prompted to give a feedback of which game he/she would like to play.
15. UI Class: The class that displays all necessary information on console during the whole game. Such as having lines, rules introduction, game results, etc.
16. Input_Check Class: The class that checks the validation of all inputs from user in different prompts.