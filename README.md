Sanguine Card Game
A strategic two-player card game featuring territory control mechanics, AI opponents, and event-driven GUI built with Java Swing and MVC architecture.

🎮 Game Overview
Sanguine is a turn-based strategy game where two players compete to control rows on a rectangular grid by placing cards that influence surrounding cells with pawns. Inspired by Queen's Blood from Final Fantasy VII Rebirth, this implementation features a complete MVC architecture with support for human vs human, human vs AI, and AI vs AI gameplay.
✨ Features

🎯 Strategic Gameplay: Place cards to spread influence and control territory
🤖 Multiple AI Strategies: Three different AI opponents with varying difficulty
🎨 GUI Interface: Clean Java Swing interface with visual feedback
👥 Flexible Player Modes: Play against humans or AI in any combination
📝 Text Mode: Command-line interface for testing and automation
⚙️ Customizable: Configurable board sizes and custom card decks
🎲 Deck Building: Create custom decks via configuration files


🚀 Quick Start
Prerequisites



Running the Game
bash# Clone the repository
git clone https://github.com/giadapalazzo/Sanguine-Card-Game.git
cd Sanguine-Card-Game

# Build the project
gradle build

# Run with default configuration (5 rows, 11 cols, human vs AI)
java -jar build/libs/sanguine.jar 5 11 docs/red.config docs/blue.config human strategy2

# Run human vs human
java -jar build/libs/sanguine.jar 5 11 docs/red.config docs/blue.config human human

# Run AI vs AI (watch them battle!)
java -jar build/libs/sanguine.jar 5 11 docs/red.config docs/blue.config strategy1 strategy3
```

### Command-Line Arguments
```
java -jar sanguine.jar [rows] [cols] [red_deck] [blue_deck] [red_player] [blue_player]
```

**Parameters:**
- `rows`: Number of rows (must be positive)
- `cols`: Number of columns (must be odd and > 1)
- `red_deck`: Path to red player's deck configuration
- `blue_deck`: Path to blue player's deck configuration
- `red_player`: Player type - `human`, `strategy1`, `strategy2`, or `strategy3`
- `blue_player`: Player type - `human`, `strategy1`, `strategy2`, or `strategy3`

## 🎯 How to Play

### Objective
Control more rows than your opponent by placing cards that spread influence across the board.

### Gameplay
1. **Select a card** from your hand (click on it)
2. **Select a cell** on the board (must have enough of your pawns to cover card cost)
3. **Confirm move** (press ENTER) or **Pass turn** (press P)
4. Card spreads influence to surrounding cells based on its influence grid
5. Game ends when both players pass consecutively
6. Winner is determined by total row scores

### Card Mechanics
- **Cost**: Number of pawns required to place (1-3)
- **Value**: Points contributed to row score
- **Influence Grid**: 5×5 pattern showing how card affects surrounding cells
  - Adds pawns to empty cells
  - Increases pawn count on owned cells (max 3)
  - Converts opponent's pawns to your color

### Scoring
- Each row scores independently
- Sum card values in each row for both players
- Player with higher row score gains those points
- Tied rows give no points to either player
- Highest total score wins

## 🏗️ Architecture

### MVC Design Pattern
```
┌─────────────┐         ┌──────────────┐         ┌─────────────┐
│   Model     │ ←───────│  Controller  │────────→│    View     │
│  (Rules &   │ notifies│  (Mediator)  │ updates │ (Display)   │
│   State)    │         │              │         │             │
└─────────────┘         └──────────────┘         └─────────────┘
       ↑                        ↑                        │
       │                        │                        │
       └────────────────────────┴────────────────────────┘
              Observer Pattern (Listeners)
```

### Key Components

**Model** (`sanguine.model`)
- `SanguineModel`: Game state coordinator and rule enforcer
- `Board`: Rectangular grid container
- `Cell`: Individual board spaces (empty, pawns, or cards)
- `Card`: Playable cards with cost, value, and influence patterns
- Drives game flow through turn management

**View** (`sanguine.view`)
- `SanguineGameView`: Main GUI window
- `BoardPanel`: Grid display with cells and scores
- `HandPanel`: Card display with selection
- `SanguineTextView`: Console-based visualization
- Passive observer that reacts to model state

**Controller** (`sanguine.controller`)
- `SanguineController`: Mediates between model, view, and players
- One controller per player for independent control
- Validates moves and enforces turn-based gameplay
- `DeckReader`: Parses deck configuration files

**Players** (`sanguine.player`)
- `HumanPlayer`: Waits for GUI input
- `MachinePlayer`: Uses strategies to compute moves
- Both publish same action events for controller

**Strategies** (`sanguine.strategy`)
- `FillFirstStrategy`: Plays first legal move
- `MaximizeRowScoreStrategy`: Targets losing/tied rows
- `MinimizeOpponentScoreStrategy`: Blocks opponent's strong rows

## 🎲 AI Strategies

### Strategy 1: Fill First
- Simple greedy approach
- Plays first legal card in first available position
- Fast but not optimal

### Strategy 2: Maximize Row Score
- Targets rows where player is losing or tied
- Calculates best card to flip row advantage
- Balanced offense and defense

### Strategy 3: Minimize Opponent Score
- Defensive strategy
- Blocks opponent's strongest rows
- Forces opponent into suboptimal plays

## 📦 Project Structure
```
sanguine/
├── src/
│   └── sanguine/
│       ├── Sanguine.java                 # Text-mode runner
│       ├── SanguineGame.java             # GUI launcher
│       ├── controller/
│       │   ├── SanguineController.java   # Player controller
│       │   ├── DeckReader.java           # Deck file parser
│       │   └── *Listener.java            # Event interfaces
│       ├── model/
│       │   ├── SanguineModel.java        # Main game model
│       │   ├── Board.java                # Grid representation
│       │   ├── Cell.java                 # Board cell
│       │   └── Card.java                 # Card definition
│       ├── player/
│       │   ├── HumanPlayer.java          # Human player
│       │   └── MachinePlayer.java        # AI player
│       ├── strategy/
│       │   ├── SanguineStrategy.java     # Strategy interface
│       │   └── *Strategy.java            # AI implementations
│       └── view/
│           ├── SanguineGameView.java     # Main GUI
│           ├── BoardPanel.java           # Board display
│           └── HandPanel.java            # Hand display
├── docs/
│   ├── red.config                        # Red player deck
│   └── blue.config                       # Blue player deck
└── test/                                 # Comprehensive test suite
🧪 Testing
Comprehensive JUnit test suite covering:

✅ Card influence mechanics
✅ Move validation logic
✅ Score calculation
✅ Strategy behavior
✅ Controller turn enforcement
✅ View event publishing
✅ Player action handling

bash# Run all tests
gradle test
🎓 Design Highlights
Observer Pattern

Model publishes turn changes and game-over events
Views observe model state without direct coupling
Controllers coordinate between components

Strategy Pattern

AI behaviors encapsulated as interchangeable strategies
Easy to add new AI opponents
Clean separation from game logic

Event-Driven Architecture

Asynchronous GUI events (clicks, keypresses)
Synchronous game rules (turn-based)
Controllers bridge the gap elegantly

Multi-Player Support

Each player has dedicated controller and view
True simultaneous multi-player (two windows)
Any combination of human/AI players

📚 What I Learned

Advanced MVC: Coordinating multiple views and controllers
Observer Pattern: Event-driven programming with listeners
Strategy Pattern: Pluggable AI behaviors
Asynchronous Design: Reconciling async events with sync gameplay
Java Swing: Building responsive GUIs
File I/O: Parsing custom configuration formats
Testing: Mock objects and isolation testing


👤 Author
Giada Palazzo & Abhinav Talvidar
Computer Science & Philosophy @ Northeastern University
