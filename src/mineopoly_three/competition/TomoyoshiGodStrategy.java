package mineopoly_three.competition; // This is the "competition" package

import mineopoly_three.action.TurnAction;
import mineopoly_three.game.Economy;
import mineopoly_three.item.InventoryItem;
import mineopoly_three.item.ItemType;
import mineopoly_three.strategy.MinePlayerStrategy;
import mineopoly_three.strategy.PlayerBoardView;
import mineopoly_three.tiles.TileType;
import mineopoly_three.util.DistanceUtil;

import java.awt.*;
import java.util.Random;

public class TomoyoshiGodStrategy implements MinePlayerStrategy {
    private int boardSize;
    private int currentCharge;
    private int availableItemSlot;
    private PlayerBoardView board;
    private Point currentTileLocation;
    private boolean isRedPlayer;
    private int maxCharge;
    private int maxInventorySize;

    @Override
    public void initialize(int boardSize, int maxInventorySize, int maxCharge, int winningScore, PlayerBoardView startingBoard, Point startTileLocation, boolean isRedPlayer, Random random) {
        this.boardSize = boardSize;
        this.board = startingBoard;
        this.currentTileLocation = startTileLocation;
        this.maxCharge = maxCharge;
        this.currentCharge = maxCharge;
        this.availableItemSlot = maxInventorySize;
        this.maxInventorySize = maxInventorySize;
        this.isRedPlayer = isRedPlayer;
    }

    @Override
    public TurnAction getTurnAction(PlayerBoardView boardView, Economy economy, int currentCharge, boolean isRedTurn) {
        this.board = boardView;
        currentTileLocation = boardView.getYourLocation();
        this.currentCharge = currentCharge;
        if (needCharging()) {
            return findRoute(TileType.RECHARGE);
        }
        if (availableItemSlot == 0) {
            if (isRedPlayer) {
                return findRoute(TileType.RED_MARKET);
            } else {
                return findRoute(TileType.BLUE_MARKET);
            }
        }
        if (!boardView.getItemsOnGround().get(currentTileLocation).isEmpty()) {
            if (boardView.getItemsOnGround().get(currentTileLocation).get(0).getItemType() != ItemType.AUTOMINER
                    || boardView.getItemsOnGround().get(currentTileLocation).size() == 1) {
                return TurnAction.PICK_UP_RESOURCE;
            }
        }
        if (isResource(currentTileLocation)) {
            return TurnAction.MINE;
        }
        return findRoute(TileType.EMPTY);
    }

    /**
     * Get the action to move to the specific point.
     * @param tile the location/tile to go to.
     * @return action to go to the tile.
     */
    private TurnAction moveToTile(Point tile) {
        if (currentTileLocation.y > tile.y) {
            return TurnAction.MOVE_DOWN;
        }
        if (currentTileLocation.y < tile.y) {
            return TurnAction.MOVE_UP;
        }
        if (currentTileLocation.x > tile.x) {
            return TurnAction.MOVE_LEFT;
        } else if (currentTileLocation.x < tile.x){
            return TurnAction.MOVE_RIGHT;
        }
        return null;
    }

    /**
     * Get the clostest resource
     * @return the position of closest resource
     */
    private Point findClosestResource() {
        Point nearest = new Point();
        int distance = 3 * boardSize;
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                if (isResource(new Point(x, y))) {
                    if (distance > DistanceUtil.getManhattanDistance(new Point(x, y), currentTileLocation)) {
                        nearest = new Point(x, y);
                        distance = DistanceUtil.getManhattanDistance(new Point(x, y), currentTileLocation);
                    }
                }
            }
        }
        return nearest;
    }

    /**
     * get if the location is a resource tile
     * @param location location to check if it is resource tile
     * @return true if the location is resource tile
     */
    private boolean isResource(Point location) {
        return board.getTileTypeAtLocation(location).equals(TileType.RESOURCE_DIAMOND) ||
                board.getTileTypeAtLocation(location).equals(TileType.RESOURCE_RUBY) ||
                board.getTileTypeAtLocation(location).equals(TileType.RESOURCE_EMERALD);
    }

    /**
     * get if the bot needs charging
     * @return true if the current charge is lower or equal to the number of moves to the station
     */
    private boolean needCharging() {
        if (board.getTileTypeAtLocation(currentTileLocation).equals(TileType.RECHARGE)
                && currentCharge != maxCharge) {
            return true;
        }
        return currentCharge <= Math.abs(currentTileLocation.x - boardSize / 2) +
                Math.abs(currentTileLocation.y - boardSize / 2);
    }

    /**
     * Find the TurnAction that goes towards the desired type of tile.
     * @param type The type of tile to go to
     * @return the TurnAction that goes towards the desired type of tile
     */
    private TurnAction findRoute(TileType type) {
        if (type.equals(TileType.RECHARGE)) {
            if (board.getTileTypeAtLocation(currentTileLocation).equals(TileType.RECHARGE)) {
                return null;
            }
            return moveToTile(new Point(boardSize / 2, boardSize / 2));
        }
        if (type.equals(TileType.EMPTY)) {
            return moveToTile(findClosestResource());
        }
        if (type == TileType.RED_MARKET || type == TileType.BLUE_MARKET) {
            return moveToTile(findCloserMarket());
        }
        return TurnAction.MOVE_DOWN;
    }

    /**
     * get the position of the closer market
     * @return the position of the closer market
     */
    private Point findCloserMarket() {
        Point nearest = new Point();
        int distance = 3 * boardSize;
        TileType marketType;
        if (isRedPlayer) {
            marketType = TileType.RED_MARKET;
        } else {
            marketType = TileType.BLUE_MARKET;
        }
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                if (board.getTileTypeAtLocation(new Point(x, y)) == marketType) {
                    if (board.getOtherPlayerLocation().getLocation().equals(new Point(x, y))) {
                        continue;
                    }
                    if (distance > DistanceUtil.getManhattanDistance(new Point(x, y), currentTileLocation)) {
                        nearest = new Point(x, y);
                        distance = DistanceUtil.getManhattanDistance(new Point(x, y), currentTileLocation);
                    }
                }
            }
        }
        return nearest;
    }

    @Override
    public void onReceiveItem(InventoryItem itemReceived) {
        availableItemSlot--;
    }

    @Override
    public void onSoldInventory(int totalSellPrice) {
        availableItemSlot = maxInventorySize;
    }

    @Override
    public String getName() {
        return "TonyIsGod";
    }

    @Override
    public void endRound(int pointsScored, int opponentPointsScored) {
        this.currentCharge = maxCharge;
        this.availableItemSlot = maxInventorySize;
    }
}
