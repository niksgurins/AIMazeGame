# Artificial Intelligence project

## Ed Lasauskas & Niks Gurins
## 4th Year project
## College: Galway-Mayo Institute of Technology
## Module: Artificial Intelligence
## Lecturer: Dr. John Healy

### Introduction
This is a Java project which is a 2D maze game for Artificial Intelligence module. The game includes traversal algorithms, neural networks and fuzzy logic.

### About the game
This project is a maze game where a player uses the arrow keys to navigate through the maze looking for an exit to finish the game (killing all spiders ends the game as well).
The player who is a spartan warrior can encounter a variety of spider enemies in the maze. The player has stats such as health, sword
durability, anger, bomb and hydrogen-bomb. The maze contains sprites which the player can pick up such as a sword, bombs and health.
The enemy spiders have health and are configured with a traversal algorithm to search for the player.
Once the spiders find the player a neural network or fuzzy logic is used to decide should the spider attack or run from the player.
The spiders fight the player to the death. The player is also able to die which ends the game.

### Relevant features
#### Spiders
* The game is equipped with 8 different types of spiders, black, blue, brown, green, grey, red, orange, yellow.
* Each spider has their own traversal algorithm. In total, the game has 6 traversal algorithms, A*, Steepst Ascent Hillclimb, Best First, Random Walk and Brute Force (Depth-first and Breadth-first)
* Black, blue, brown and green spiders are configured with fuzzy logic. Once they find the player, the fuzzy inference system is used to evaluate whether to attack the player or run away, based on some of the player stats
* The grey, red, orange and yellow spiders on the other hand are configured with a back propogation neural network that has 3 input nodes (health, sword, anger of the player), 2 hidden layers and 2 output layers (attack & run)
* All the spiders run in their own thread, which is handled by an ExceutorService of size 50. This means that as soon as one spider dies, and a thread is freed up, the pool will check its queue for another spider and run its thread
* The spider threads work as follows: 
    1. The traversal algorithm is started and the spiders begin looking for you
    2. If the player was found, the spider calls engage() and checks whether to fight or run
    3. If the player wasn't found, the spider will run traverse() again

#### Spartan Warrior
* The spartan warrior starts off with 100 health, 0 anger, 0 sword, 0 bombs and 0 h-bombs
* The player can "walk into" a hedge with items in it to pick them up
* Damage of the spartan increases as his health goes down (anger)
* Each sword has a durability of 3 and when it goes to 0, the sword is destroyed
* The player can throw bombs with button 'B' and h-bombs with 'H'

#### Bombs
* The bombs, when thrown are threaded and explode within 1.5 seconds of being thrown
* The normal bombs destroy items in hedges and do damage to the player and spiders around it within a 2 square radius
* The h-bombs are similar, but instead of destroying items, they destroy the whole hedge and do more damage to spiders/player

### Final thoughts on the project
We couldn't think of different fuzzy systems or neural networks to implement other than the ones we already have (could duplicate spider fuzzy system + nn for player (auto-pilot)). We thought of adding a spider that would help the player instead of attack him, but decided it'd be too easy if that was the case (the help.fcl file is still there). After hooking up fuzzy logic with neural networks and traversal algorithms all in one game, we realized that none of them (in our implementation) were too difficult to use. We got a good idea of how the traversal algorithms that we used work and which ones are better. 

### Structure
Readme - file about the project and details.
The game.jar is a jar file which can be used for running the game.
ie.gmit.sw.ai directory contains the source code for the game.

### Runing this project
1. Download this repo
2. To run the game open a command prompt, navigate to the directory where the game.jar file is and enter the command
```cmd
java –cp ./game.jar;./jcommon-1.0.23.jar;./jFuzzyLogic.jar ie.gmit.sw.ai.GameRunner
```
to launch the game.
