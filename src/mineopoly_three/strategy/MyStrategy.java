package mineopoly_three.strategy;

import mineopoly_three.action.TurnAction;
import mineopoly_three.game.Economy;
import mineopoly_three.item.InventoryItem;
import mineopoly_three.item.ItemType;
import mineopoly_three.tiles.Tile;
import mineopoly_three.tiles.TileType;
import mineopoly_three.util.DistanceUtil;

import java.awt.*;
import java.util.Random;

public class MyStrategy implements MinePlayerStrategy {
    private int boardSize;
    private int charge;
    private int inventorySize;
    private PlayerBoardView board;
    private Point currentTileLocation;
    private boolean isRedPlayer;
    private int maxCharge;
    private int maxInventorySize;

    /**
     * Called at the start of every round
     *
     * @param boardSize         The length and width of the square game board
     * @param maxInventorySize  The maximum number of items that your player can carry at one time
     * @param maxCharge         The amount of charge your robot starts with (number of tile moves before needing to recharge)
     * @param winningScore      The first player to reach this score wins the round
     * @param startingBoard     A view of the GameBoard at the start of the game. You can use this to pre-compute fixed
     *                          information, like the locations of market or recharge tiles
     * @param startTileLocation A Point representing your starting location in (x, y) coordinates
     *                          (0, 0) is the bottom left and (boardSize - 1, boardSize - 1) is the top right
     * @param isRedPlayer       True if this strategy is the red player, false otherwise
     * @param random            A random number generator, if your strategy needs random numbers you should use this.
     */
    @Override
    public void initialize(int boardSize, int maxInventorySize, int maxCharge, int winningScore, PlayerBoardView startingBoard, Point startTileLocation, boolean isRedPlayer, Random random) {
        this.boardSize = boardSize;
        this.board = startingBoard;
        this.currentTileLocation = startTileLocation;
        this.maxCharge = maxCharge;
        this.charge = maxCharge;
        this.winningScore = winningScore;
        this.inventorySize = maxInventorySize;
        this.maxInventorySize = maxInventorySize;
        this.isRedPlayer = isRedPlayer;
    }

    /**
     * Get the action to move to the specific point.
     * @param tile the location/tile to go to.
     * @return action to go to the tile.
     */
    private TurnAction moveToTile(Point tile) {
        if (currentTileLocation.x > tile.x) {
            return TurnAction.MOVE_LEFT;
        } else if (currentTileLocation.x < tile.x){
            return TurnAction.MOVE_RIGHT;
        }
        if (currentTileLocation.y > tile.y) {
            return TurnAction.MOVE_DOWN;
        }
        if (currentTileLocation.y < tile.y) {
            return TurnAction.MOVE_UP;
        }
        return null;
    }

    @Override
    public TurnAction getTurnAction(PlayerBoardView boardView, Economy economy, int currentCharge, boolean isRedTurn) {
        this.board = boardView;
        currentTileLocation = boardView.getYourLocation();
        charge = currentCharge;
        if (needCharging()) {
            return getRoute(TileType.RECHARGE);
        }
        if (inventorySize == 0) {
            if (isRedPlayer) {
                return getRoute(TileType.RED_MARKET);
            } else {
                return getRoute(TileType.BLUE_MARKET);
            }
        }
        if (!boardView.getItemsOnGround().get(currentTileLocation).isEmpty()) {
            return TurnAction.PICK_UP_RESOURCE;
        }
        if (isResource(currentTileLocation)) {
            return TurnAction.MINE;
        }
        return getRoute(TileType.EMPTY);
    }

    private Point getClosestResource() {
        Point nearest = new Point();
        int distance = 3 * boardSize;
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                if (isResource(new Point(x, y))) {
                    if (distance > Math.abs(currentTileLocation.x - x) + Math.abs(currentTileLocation.y - y)) {
                        nearest = new Point(x, y);
                        distance = Math.abs(currentTileLocation.x - x) + Math.abs(currentTileLocation.y - y);
                    }
                }
            }
        }
        return nearest;
    }

    private boolean isResource(Point location) {
        return board.getTileTypeAtLocation(location).equals(TileType.RESOURCE_DIAMOND) ||
                board.getTileTypeAtLocation(location).equals(TileType.RESOURCE_RUBY) ||
                board.getTileTypeAtLocation(location).equals(TileType.RESOURCE_EMERALD);
    }

    private boolean needCharging() {
        if (board.getTileTypeAtLocation(currentTileLocation).equals(TileType.RECHARGE)
                                && charge != maxCharge) {
            return true;
        }
        return charge <= Math.abs(currentTileLocation.x - boardSize / 2) +  Math.abs(currentTileLocation.y - boardSize / 2);
    }

    private TurnAction getRoute(TileType type) {
        if (type.equals(TileType.RECHARGE)) {
            return moveToTile(new Point(boardSize / 2, boardSize / 2));
        }
        if (type.equals(TileType.EMPTY)) {
            return moveToTile(getClosestResource());
        }
        if (type == TileType.RED_MARKET) {
            if (currentTileLocation.x < currentTileLocation.y) {
                return moveToTile(new Point(boardSize / 4, boardSize * 3 / 4));
            } else {
                return moveToTile(new Point(boardSize * 3 / 4, boardSize / 4));
            }
        }
        if (type == TileType.BLUE_MARKET) {
            if (currentTileLocation.x + currentTileLocation.y < boardSize) {
                return moveToTile(new Point(boardSize / 4, boardSize / 4));
            } else {
                return moveToTile(new Point(boardSize * 3 / 4, boardSize * 3 / 4));
            }
        }
        return TurnAction.MOVE_DOWN;
    }

    /**
     * Called when the player receives an item from performing a TurnAction that gives an item.
     * At the moment this is only from using PICK_UP on top of a mined resource
     *
     * @param itemReceived The item received from the player's TurnAction on their last turn
     */
    @Override
    public void onReceiveItem(InventoryItem itemReceived) {
        inventorySize--;
    }

    /**
     * Called when the player steps on a market tile with items to sell. Tells your strategy how much all
     * of the items sold for.
     *
     * @param totalSellPrice The combined sell price for all items in your strategy's inventory
     */
    @Override
    public void onSoldInventory(int totalSellPrice) {
        inventorySize = maxInventorySize;
    }

    /**
     * Gets the name of this strategy. The amount of characters that can actually be displayed on a screen varies,
     * although by default at screen size 750 it's about 16-20 characters depending on character size
     *
     * @return The name of your strategy for use in the competition and rendering the scoreboard on the GUI
     */
    @Override
    public String getName() {
        return "i11usion";
    }

    /**
     * Called at the end of every round to let players reset, and tell them how they did if the strategy does not
     * track that for itself
     *
     * @param pointsScored         The total number of points this strategy scored
     * @param opponentPointsScored The total number of points the opponent's strategy scored
     */
    @Override
    public void endRound(int pointsScored, int opponentPointsScored) {

    }
}
