-Sanguine Card Game

--Overview
Sanguine is a two-player strategic card game played on a rectangular grid.
Players compete to control rows by placing cards that influence surrounding cells with pawns.
This game is inspired by Queen's blood.

--Key Assumptions--
-The game requires a rectangular board with positive rows and odd columns (greater than 1)
-Each deck must contain enough cards to fill every cell on the board
-A deck can have at most two copies of any card
-Players have complete information about the board state
-Card influence is deterministic based on placement position
-The blue player's grid is horizontally mirrored compared to red's

--Quick Start--
Here is an example of how to create and run a game

// Read deck configuration
List deck = DeckReader.readDeck("docs" + File.separator + "deck.config");

// Initialize a 3x5 game with same deck for both players, hand size 5
SanguineModel model = new SanguineModel(3, 5, deck, deck, 5);

// Create a text view
SanguineTextView view = new SanguineTextView(model.getBoard());

// Display initial board
view.render();

// Place a card (if legal)
boolean success = model.placeCard(0, 0, 0); // card index 0, position (0,0)

// Display updated board
view.render();

// Check game status
if (model.isGameOver()) {
PlayerColor winner = model.getWinner();
System.out.println("Winner: " + winner);
}

--Key Components--

--Model--
The model represents the game state and enforces all game rules. It is the "brain" of the game that:
-Maintains the board, decks, hands, and turn order
-Validates all player actions (card placement, passing)
-Applies card influence to the board
-Calculates scores and determines winners
-Drives the game flow by managing turns and game-over conditions

The model is driven by player actions

--View--
The view renders the game state for visualization. It is a passive component that:
-Observes the model's board state
-Formats the game state as text output
-Is driven by the model's current state

--Controller (Not Yet  fully Implemented)--
The controller will mediate between players and the model. It will:
- Accept player input (human or AI)
- Validate and translate input into model operations
- Update the view after each action
- DeckReader is in controller because it deals with FileReader and outsidefiles
- **DeckReader**: Reads card configurations from files
- Purpose: Converts external deck files into Card objects
- Responsibilities: File parsing, validation, card creation
- Key methods: `readDeck()`

--Key Subcomponents--
--model package (sanguine.model)
**SanguineModel**: The main game coordinator
-Purpose: manages the complete game state and enforces all rules
-Responsible for turn management, move validation, and score calculation
-Some key methods are placeCard(), pass(), isGameOver(), getWinner()
**Board**: Represents the game grid
- Purpose: Container for all cells in a rectangular layout
- Responsibilities: Cell access, dimension management, board initialization
- Key methods: `getCell()`, `getRows()`, `getCols()`

**Cell**: Represents a single space on the board
- Purpose: Stores content (empty, pawns, or card) and ownership
- Responsibilities: State management, content conversion, display formatting
- Key methods: `placeCard()`, `initPawn()`, `addPawn()`, `convertPawns()`

**Card**: Represents a playable card
- Purpose: Defines card attributes and influence pattern
- Responsibilities: Stores cost, value, name, and 5x5 influence grid
- Key methods: `getInfluenceGrid()`, `getMirroredInfluence()`, `getCost()`, `getValue()`



**PlayerColor**: Enumeration for player identification
- Purpose: Distinguishes between RED and BLUE players
- Includes helper method `opposite()` for turn switching

**CellContent**: Enumeration for cell states
- Purpose: Identifies what a cell contains (EMPTY, PAWNS, or CARD)

--View Package-- (sanguine.view)
**SanguineTextView**: Text-based board visualization
- Purpose: Renders the board state as ASCII text
- Responsibilities: Formatting board for console output

Source Organization
f25-hw06-group-khaki-canyon-4465/
├── src/
│   └── sanguine/
│       ├── Sanguine.java                    # Text-based game runner
│       ├── SanguineGame.java                # GUI game launcher
│       ├── controller/
│       │   ├── CardGameListener.java        # Interface for game event listeners
│       │   ├── DeckReader.java              # Reads deck configuration files
│       │   ├── SanguineController.java       
│       ├── model/
│       │   ├── Board.java                   # Game board representation
│       │   ├── Card.java                    # Card with cost, value, and influence
│       │   ├── Cell.java                    # Individual board cell
│       │   ├── CellContent.java             # Enum for cell contents
│       │   ├── PlayerColor.java             # RED or BLUE player
│       │   ├── ReadOnlySanguineModel.java   # Read-only model interface
│       │   ├── MutableSanguineModel.java    # Mutable model interface
│       │   └── SanguineModel.java           # Main game model implementation
│       ├── strategy/
│       │   ├── Move.java                    # Represents a game move
│       │   ├── SanguineStrategy.java        # Strategy interface
│       │   ├── FillFirstStrategy.java       # First legal move strategy
│       │   └── MaximizeRowScoreStrategy.java # Row score maximization strategy
│       └── view/
│           ├── SanguineView.java            # View interface
│           ├── SanguineTextView.java        # Text-based view
│           ├── SanguineGameView.java        # Main GUI view
│           ├── BoardPanel.java              # Board display panel
│           └── HandPanel.java               # Hand display panel
├── test/
│   └── sanguine/
│       ├── strategy/
│       │   ├── MockSanguineModel.java       # Mock model for testing
│       │   ├── StrategyMockTest.java        # Mock-based strategy test
│       ├── MockCard.java                    # Helper for creating test cards
│       ├── MockModel.java                   # Helper for creating test models
│       ├── CellTests.java                   # Tests for Cell class
│       ├── LegalMoveTests.java              # Tests for move validation
│       └── InfluenceTests.java              # Tests for card influence
├── docs/
│   └── deck.config                          # Deck configuration file
├── images/
│   ├── screenshot-1-
│   ├── screenshot-2
│   ├── screenshot-3-
│   └── screenshot-4
├── strategy-transcript-first.txt            # FillFirst strategy transcript
├── strategy-transcript-score.txt            # MaximizeRowScore strategy transcript
└── build.gradle

====Changes For Part 2=======
Model Refactoring
-The model interface was split into the ReadOnluSanguineModel and MutableSanguineModel
- this ensures that the view can only be observed by the game state, while mutation methods are restricted to the controller

GUI implementation
-the gui implementation uses java swing with MVC architechture
SanguineGameView
- Main window that hold all panels
- manages keyboard inputs
- tracks selected card and cell
HandPanel
- Displays player's hand horizontally
- shows card name, cost, value, and influence grid
- handles mouse clicks for card selection
Board Panel
- Displays the game board in a grid layout
- shows cells with pawns or cards
- displays row scores
- handles mouse clicks for cell selection
User Interaction
- Select a card
- Select a cell
- press enter to confirm move 
- press P key to pass turn 
Uses controller to aid switching between turns of red player and blue player

Strategy Implementation
- Added Strategy interface that analyze the game state and return Move 
  an object containing card index, target row, target column.
- FirstFillStrategy
- Chooses the first legal card and position by checking cards left to right 
- then cells top to bottom and left to right
- MaximalizeRowScoreStrategy
- Visit rows from top to bottom. For each row where the current player is losing
- or tied, try to find a card that would make their row score greater than the opponent's
- For each candidate move the calculation is 
- newScore= currentRowScore + cardValue
- Move is valid if newScore > opponentRowScore

Testing
-Added some more tests for my model to ensure that everything is working
CellTests.java
LegalMove.java
InfluenceTests.java
Startegy Tests with Strategy Mock

## Changes For Part 3
### Controller Implementation
- Created SanguineController that mediates between model, view, and player
- Each player has their own dedicated controller
- Controller enforces turn-based gameplay and validates all moves
- Implements three listener interfaces:
  - CardGameListener: Responds to view events (clicks, key presses)
  - PlayerActionListener: Responds to player actions (machine or human)
  - ModelStatusListener: Responds to model events (turn changes, game over)

### Asynchronous Event Handling
- GUI events (mouse clicks, key presses) can arrive at any time
- Model notifications (turn changes) trigger controller responses
- Controllers ensure synchronous gameplay despite asynchronous input
- Turn enforcement prevents players from acting when it's not their turn

### Player Architecture
- Split player responsibilities into separate Player implementations
- **HumanPlayer**: Passive player that waits for view input
  - Stores player color and listeners
  - View publishes events on behalf of human player
- **MachinePlayer**: Active player that uses strategies
  - Implements ModelStatusListener to respond to turn changes
  - Automatically computes and publishes moves when it's their turn
  - Handles empty hand gracefully by passing

### Listener Interfaces
**ModelStatusListener**
- Allows controllers and players to observe model state changes
- Methods: `onTurnStart(PlayerColor)`, `onGameOver(PlayerColor, int)`
- Model notifies all registered listeners on turn changes and game end

**PlayerActionListener**
- Allows controllers to receive player actions
- Methods: `cardSelected()`, `cellSelected()`, `confirmMove()`, `passTurn()`
- Both human (via view) and machine players publish these events

**CardGameListener**
- Allows controllers to receive view events
- Methods: `onCardSelected()`, `onCellSelected()`, `onConfirmMove()`, `onPass()`
- View publishes these events when user interacts with GUI

### Game Flow
1. **Initialization**: Controllers register as listeners to model, view, and player
2. **Start Game**: Model calls `startGame()`, notifying first player's turn
3. **Turn Notification**: Controllers receive `onTurnStart()` and update view titles
4. **Player Action**:
  - Human: User clicks card/cell, presses ENTER → view publishes events
  - Machine: Strategy computes move → player publishes events
5. **Controller Validation**: Checks if selections are valid and it's player's turn
6. **Model Update**: Controller calls `placeCard()` or `pass()` on model
7. **Next Turn**: Model switches players and notifies listeners
8. **Game Over**: Model notifies all listeners with winner and score

### Selection Management
- Views track selected card and cell indices
- Controllers coordinate between view selections and model state
- Visual feedback shows selected items with highlights
- Selections cleared after successful move or pass

### Error Handling
- Controllers catch IllegalArgumentException from invalid moves
- Error messages displayed via `showMessage()` dialog
- Invalid moves don't crash the game or change state
- User can correct mistakes and try again

### Command-Line Configuration
- Main method accepts 6 arguments: rows, cols, red_deck, blue_deck, red_player, blue_player
- Player types: "human", "strategy1", "strategy2", "strategy3"
- Example: `java -jar sanguine.jar 5 11 docs/red.config docs/blue.config human strategy2`
- Graceful error handling for invalid arguments

### Testing Additions
**Controller Tests**
- Tests for turn enforcement
- Tests for selection validation
- Tests for move confirmation logic
- Tests for all listener method implementations

**Player Tests**
- HumanPlayer tests for color and listener management
- MachinePlayer tests for automatic move generation
- Tests for handling empty hands and null moves

**Strategy Tests**
- MinimizeOpponentScoreStrategy tests
- FillFirstStrategy tests
- Tests with mock models for controlled scenarios

**Mock Classes**
- MockView: Tracks view method calls for testing
- MockSanguineModel: Minimal model implementation for isolated tests
- MockPlayerActionListener: Verifies player action events

### Design Improvements
- Clear separation of concerns: Model (rules), View (display), Controller (coordination)
- Flexible player architecture supports any mix of human/AI players
- Strategy pattern allows easy addition of new AI behaviors
- Observer pattern enables loose coupling between components
- Each player has isolated view and controller for true multi-player experience

### Key Design Decisions
1. **One controller per player**: Enables independent player actions and clean separation
2. **Separate listener interfaces**: Clear contracts for different event types
3. **Model drives game flow**: Controllers react to model state changes
4. **View is passive**: Only publishes events, doesn't directly modify model
5. **Players publish events**: Both human and machine use same event mechanism
6. **startGame() method**: Ensures all listeners registered before first turn notification