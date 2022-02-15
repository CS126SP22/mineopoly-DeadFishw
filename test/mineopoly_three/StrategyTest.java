package mineopoly_three;

import mineopoly_three.action.TurnAction;
import mineopoly_three.game.Economy;
import mineopoly_three.game.GameBoard;
import mineopoly_three.game.GameEngine;
import mineopoly_three.item.InventoryItem;
import mineopoly_three.item.ItemType;
import mineopoly_three.strategy.MyStrategy;
import mineopoly_three.strategy.MinePlayerStrategy;
import mineopoly_three.strategy.PlayerBoardView;
import mineopoly_three.tiles.TileType;
import org.junit.Before;
import org.junit.Test;

import java.awt.Point;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class StrategyTest {
    private MinePlayerStrategy redStrategy;
    @Before
    public void setUp() {
        redStrategy = new MyStrategy();

    }

    @Test
    public void testGetTurnActionEmptyBoard() {
        TileType[][] boardTileTypes = new TileType[][]{
                {TileType.EMPTY, TileType.EMPTY},
                {TileType.EMPTY, TileType.EMPTY}
        };
        Map itemsOnGround = new HashMap<>();
        itemsOnGround.put(new Point(0,0), new ArrayList());
        itemsOnGround.put(new Point(1,0), new ArrayList());
        itemsOnGround.put(new Point(0,1), new ArrayList());
        itemsOnGround.put(new Point(1,1), new ArrayList());

        PlayerBoardView boardView = new PlayerBoardView(boardTileTypes, itemsOnGround, new Point(0 ,0), new Point(1, 1), 0);
        redStrategy.initialize(2, 5,80, 10, boardView, new Point(0,0), true,  new Random());
        assertEquals(null, redStrategy.getTurnAction(boardView, new Economy(new ItemType[]{}), 80, true));
    }

    @Test
    public void testGetTurnActionMoveLeftToClosestMine() {
        TileType[][] boardTileTypes = new TileType[][]{
                {TileType.EMPTY, TileType.EMPTY},
                {TileType.RESOURCE_DIAMOND, TileType.EMPTY}
        };
        Map itemsOnGround = new HashMap<>();
        itemsOnGround.put(new Point(0,0), new ArrayList());
        itemsOnGround.put(new Point(1,0), new ArrayList());
        itemsOnGround.put(new Point(0,1), new ArrayList());
        itemsOnGround.put(new Point(1,1), new ArrayList());

        PlayerBoardView boardView = new PlayerBoardView(boardTileTypes, itemsOnGround, new Point(1 ,0), new Point(1, 1), 0);
        redStrategy.initialize(2, 5,80, 10, boardView, new Point(0,0), true,  new Random());
        assertEquals(TurnAction.MOVE_LEFT, redStrategy.getTurnAction(boardView, new Economy(new ItemType[]{}), 80, true));
    }

    @Test
    public void testGetTurnActionMoveRightToClosestMine() {
        TileType[][] boardTileTypes = new TileType[][]{
                {TileType.EMPTY, TileType.EMPTY},
                {TileType.EMPTY, TileType.RESOURCE_DIAMOND}
        };
        Map itemsOnGround = new HashMap<>();
        itemsOnGround.put(new Point(0,0), new ArrayList());
        itemsOnGround.put(new Point(1,0), new ArrayList());
        itemsOnGround.put(new Point(0,1), new ArrayList());
        itemsOnGround.put(new Point(1,1), new ArrayList());

        PlayerBoardView boardView = new PlayerBoardView(boardTileTypes, itemsOnGround, new Point(0 ,0), new Point(1, 1), 0);
        redStrategy.initialize(2, 5,80, 10, boardView, new Point(0,0), true,  new Random());
        assertEquals(TurnAction.MOVE_RIGHT, redStrategy.getTurnAction(boardView, new Economy(new ItemType[]{}), 80, true));
    }

    @Test
    public void testGetTurnActionMoveUpToClosestMine() {
        TileType[][] boardTileTypes = new TileType[][]{
                {TileType.EMPTY, TileType.EMPTY},
                {TileType.EMPTY, TileType.RESOURCE_DIAMOND}
        };
        Map itemsOnGround = new HashMap<>();
        itemsOnGround.put(new Point(0,0), new ArrayList());
        itemsOnGround.put(new Point(1,0), new ArrayList());
        itemsOnGround.put(new Point(0,1), new ArrayList());
        itemsOnGround.put(new Point(1,1), new ArrayList());

        PlayerBoardView boardView = new PlayerBoardView(boardTileTypes, itemsOnGround, new Point(0 ,1), new Point(1, 1), 0);
        redStrategy.initialize(2, 5,80, 10, boardView, new Point(0,0), true,  new Random());
        assertEquals(TurnAction.MOVE_RIGHT, redStrategy.getTurnAction(boardView, new Economy(new ItemType[]{}), 80, true));
    }
    @Test
    public void testGetTurnActionMoveDownToClosestMine() {
        TileType[][] boardTileTypes = new TileType[][]{
                {TileType.EMPTY, TileType.EMPTY},
                {TileType.EMPTY, TileType.RESOURCE_DIAMOND}
        };
        Map itemsOnGround = new HashMap<>();
        itemsOnGround.put(new Point(0,0), new ArrayList());
        itemsOnGround.put(new Point(1,0), new ArrayList());
        itemsOnGround.put(new Point(0,1), new ArrayList());
        itemsOnGround.put(new Point(1,1), new ArrayList());

        PlayerBoardView boardView = new PlayerBoardView(boardTileTypes, itemsOnGround, new Point(1 ,1), new Point(0, 1), 0);
        redStrategy.initialize(2, 5,80, 10, boardView, new Point(0,0), true,  new Random());
        assertEquals(TurnAction.MOVE_DOWN, redStrategy.getTurnAction(boardView, new Economy(new ItemType[]{}), 80, true));
    }

    @Test
    public void testGetTurnActionMoveRightToCharge() {
        TileType[][] boardTileTypes = new TileType[][]{
                {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},
                {TileType.EMPTY, TileType.RECHARGE, TileType.RECHARGE, TileType.EMPTY},
                {TileType.EMPTY, TileType.RECHARGE, TileType.RECHARGE, TileType.EMPTY},
                {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}
        };
        Map itemsOnGround = new HashMap<>();
        itemsOnGround.put(new Point(0,0), new ArrayList());

        PlayerBoardView boardView = new PlayerBoardView(boardTileTypes, itemsOnGround, new Point(0 ,0), new Point(1, 1), 0);
        redStrategy.initialize(4, 5,80, 10, boardView, new Point(0,0), true,  new Random());
        assertEquals(TurnAction.MOVE_RIGHT, redStrategy.getTurnAction(boardView, new Economy(new ItemType[]{}), 1, true));
    }

    @Test
    public void testGetTurnActionMoveLeftToCharge() {
        TileType[][] boardTileTypes = new TileType[][]{
                {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},
                {TileType.EMPTY, TileType.RECHARGE, TileType.RECHARGE, TileType.EMPTY},
                {TileType.EMPTY, TileType.RECHARGE, TileType.RECHARGE, TileType.EMPTY},
                {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}
        };
        Map itemsOnGround = new HashMap<>();
        itemsOnGround.put(new Point(0,0), new ArrayList());

        PlayerBoardView boardView = new PlayerBoardView(boardTileTypes, itemsOnGround, new Point(3 ,2), new Point(1, 1), 0);
        redStrategy.initialize(4, 5,80, 10, boardView, new Point(0,0), true,  new Random());
        assertEquals(TurnAction.MOVE_LEFT, redStrategy.getTurnAction(boardView, new Economy(new ItemType[]{}), 1, true));
    }

    @Test
    public void testGetTurnActionMoveDownToCharge() {
        TileType[][] boardTileTypes = new TileType[][]{
                {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},
                {TileType.EMPTY, TileType.RECHARGE, TileType.RECHARGE, TileType.EMPTY},
                {TileType.EMPTY, TileType.RECHARGE, TileType.RECHARGE, TileType.EMPTY},
                {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}
        };
        Map itemsOnGround = new HashMap<>();
        itemsOnGround.put(new Point(0,0), new ArrayList());

        PlayerBoardView boardView = new PlayerBoardView(boardTileTypes, itemsOnGround, new Point(2 ,3), new Point(1, 1), 0);
        redStrategy.initialize(4, 5,80, 10, boardView, new Point(0,0), true,  new Random());
        assertEquals(TurnAction.MOVE_DOWN, redStrategy.getTurnAction(boardView, new Economy(new ItemType[]{}), 1, true));
    }

    @Test
    public void testGetTurnActionMoveUpToCharge() {
        TileType[][] boardTileTypes = new TileType[][]{
                {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},
                {TileType.EMPTY, TileType.RECHARGE, TileType.RECHARGE, TileType.EMPTY},
                {TileType.EMPTY, TileType.RECHARGE, TileType.RECHARGE, TileType.EMPTY},
                {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}
        };
        Map itemsOnGround = new HashMap<>();
        itemsOnGround.put(new Point(0,0), new ArrayList());

        PlayerBoardView boardView = new PlayerBoardView(boardTileTypes, itemsOnGround, new Point(2 ,0), new Point(1, 1), 0);
        redStrategy.initialize(4, 5,80, 10, boardView, new Point(0,0), true,  new Random());
        assertEquals(TurnAction.MOVE_UP, redStrategy.getTurnAction(boardView, new Economy(new ItemType[]{}), 1, true));
    }

    @Test
    public void testGetTurnActionChargeNoMove() {
        TileType[][] boardTileTypes = new TileType[][]{
                {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},
                {TileType.EMPTY, TileType.RECHARGE, TileType.RECHARGE, TileType.EMPTY},
                {TileType.EMPTY, TileType.RECHARGE, TileType.RECHARGE, TileType.EMPTY},
                {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}
        };
        Map itemsOnGround = new HashMap<>();
        itemsOnGround.put(new Point(0,0), new ArrayList());

        PlayerBoardView boardView = new PlayerBoardView(boardTileTypes, itemsOnGround, new Point(1 ,1), new Point(2, 2), 0);
        redStrategy.initialize(4, 5,80, 10, boardView, new Point(0,0), true,  new Random());
        assertEquals(null, redStrategy.getTurnAction(boardView, new Economy(new ItemType[]{}), 1, true));
    }

    @Test
    public void testGetTurnActionMoveRightToRedMarket() {
        TileType[][] boardTileTypes = new TileType[][]{
                {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},
                {TileType.EMPTY, TileType.EMPTY, TileType.RED_MARKET, TileType.EMPTY},
                {TileType.EMPTY, TileType.BLUE_MARKET, TileType.EMPTY, TileType.EMPTY},
                {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}
        };
        Map itemsOnGround = new HashMap<>();
        itemsOnGround.put(new Point(0,0), new ArrayList());

        PlayerBoardView boardView = new PlayerBoardView(boardTileTypes, itemsOnGround, new Point(1 ,2), new Point(1, 1), 0);
        redStrategy.initialize(4, 0,80, 10, boardView, new Point(0,0), true,  new Random());
        assertEquals(TurnAction.MOVE_RIGHT, redStrategy.getTurnAction(boardView, new Economy(new ItemType[]{}), 10, true));
    }

    @Test
    public void testGetTurnActionMoveLeftToRedMarket() {
        TileType[][] boardTileTypes = new TileType[][]{
                {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},
                {TileType.EMPTY, TileType.RED_MARKET, TileType.BLUE_MARKET, TileType.RESOURCE_EMERALD},
                {TileType.EMPTY, TileType.BLUE_MARKET, TileType.RED_MARKET, TileType.EMPTY},
                {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}
        };
        Map itemsOnGround = new HashMap<>();
        itemsOnGround.put(new Point(0,0), new ArrayList());

        PlayerBoardView boardView = new PlayerBoardView(boardTileTypes, itemsOnGround, new Point(3 ,2), new Point(1, 1), 0);
        redStrategy.initialize(4, 0,80, 10, boardView, new Point(0,0), true,  new Random());
        assertEquals(TurnAction.MOVE_LEFT, redStrategy.getTurnAction(boardView, new Economy(new ItemType[]{}), 10, true));
    }

    @Test
    public void testGetTurnActionMoveUpToRedMarket() {
        TileType[][] boardTileTypes = new TileType[][]{
                {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},
                {TileType.EMPTY, TileType.EMPTY, TileType.RED_MARKET, TileType.EMPTY},
                {TileType.EMPTY, TileType.BLUE_MARKET, TileType.EMPTY, TileType.EMPTY},
                {TileType.EMPTY, TileType.RESOURCE_EMERALD, TileType.EMPTY, TileType.EMPTY}
        };
        Map itemsOnGround = new HashMap<>();
        itemsOnGround.put(new Point(0,0), new ArrayList());

        PlayerBoardView boardView = new PlayerBoardView(boardTileTypes, itemsOnGround, new Point(2 ,1), new Point(1, 1), 0);
        redStrategy.initialize(4, 0,80, 10, boardView, new Point(0,0), true,  new Random());
        assertEquals(TurnAction.MOVE_UP, redStrategy.getTurnAction(boardView, new Economy(new ItemType[]{}), 10, true));
    }

    @Test
    public void testGetTurnActionMoveDownToRedMarket() {
        TileType[][] boardTileTypes = new TileType[][]{
                {TileType.EMPTY, TileType.RESOURCE_EMERALD, TileType.EMPTY, TileType.EMPTY},
                {TileType.EMPTY, TileType.BLUE_MARKET, TileType.EMPTY, TileType.EMPTY},
                {TileType.EMPTY, TileType.EMPTY, TileType.RED_MARKET, TileType.EMPTY},
                {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}
        };
        Map itemsOnGround = new HashMap<>();
        itemsOnGround.put(new Point(0,0), new ArrayList());

        PlayerBoardView boardView = new PlayerBoardView(boardTileTypes, itemsOnGround, new Point(2 ,2), new Point(1, 1), 0);
        redStrategy.initialize(4, 0,80, 10, boardView, new Point(0,0), true,  new Random());
        assertEquals(TurnAction.MOVE_DOWN, redStrategy.getTurnAction(boardView, new Economy(new ItemType[]{}), 80, true));
    }

    @Test
    public void testGetTurnActionMoveRightToBlueMarket() {
        TileType[][] boardTileTypes = new TileType[][]{
                {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},
                {TileType.EMPTY, TileType.RED_MARKET, TileType.EMPTY, TileType.EMPTY},
                {TileType.EMPTY, TileType.EMPTY, TileType.BLUE_MARKET, TileType.EMPTY},
                {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}
        };
        Map itemsOnGround = new HashMap<>();
        itemsOnGround.put(new Point(0,0), new ArrayList());

        PlayerBoardView boardView = new PlayerBoardView(boardTileTypes, itemsOnGround, new Point(1 ,1), new Point(1, 1), 0);
        redStrategy.initialize(4, 0,80, 10, boardView, new Point(0,0), false,  new Random());
        assertEquals(TurnAction.MOVE_RIGHT, redStrategy.getTurnAction(boardView, new Economy(new ItemType[]{}), 10, true));
    }

    @Test
    public void testGetTurnActionMoveLeftToBlueMarket() {
        TileType[][] boardTileTypes = new TileType[][]{
                {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},
                {TileType.EMPTY, TileType.BLUE_MARKET, TileType.EMPTY, TileType.EMPTY},
                {TileType.EMPTY, TileType.EMPTY, TileType.RED_MARKET, TileType.EMPTY},
                {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}
        };
        Map itemsOnGround = new HashMap<>();
        itemsOnGround.put(new Point(0,0), new ArrayList());

        PlayerBoardView boardView = new PlayerBoardView(boardTileTypes, itemsOnGround, new Point(2 ,2), new Point(1, 1), 0);
        redStrategy.initialize(4, 0,80, 10, boardView, new Point(0,0), false,  new Random());
        assertEquals(TurnAction.MOVE_LEFT, redStrategy.getTurnAction(boardView, new Economy(new ItemType[]{}), 10, true));
    }

    @Test
    public void testGetTurnActionMoveUpToBlueMarket() {
        TileType[][] boardTileTypes = new TileType[][]{
                {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},
                {TileType.EMPTY, TileType.BLUE_MARKET, TileType.EMPTY, TileType.EMPTY},
                {TileType.EMPTY, TileType.EMPTY, TileType.RED_MARKET, TileType.EMPTY},
                {TileType.EMPTY, TileType.RESOURCE_EMERALD, TileType.EMPTY, TileType.EMPTY}
        };
        Map itemsOnGround = new HashMap<>();
        itemsOnGround.put(new Point(0,0), new ArrayList());

        PlayerBoardView boardView = new PlayerBoardView(boardTileTypes, itemsOnGround, new Point(1 ,1), new Point(1, 1), 0);
        redStrategy.initialize(4, 0,80, 10, boardView, new Point(0,0), false,  new Random());
        assertEquals(TurnAction.MOVE_UP, redStrategy.getTurnAction(boardView, new Economy(new ItemType[]{}), 10, true));
    }

    @Test
    public void testGetTurnActionMoveDownToBlueMarket() {
        TileType[][] boardTileTypes = new TileType[][]{
                {TileType.EMPTY, TileType.RESOURCE_EMERALD, TileType.EMPTY, TileType.EMPTY},
                {TileType.EMPTY, TileType.EMPTY, TileType.RED_MARKET, TileType.EMPTY},
                {TileType.EMPTY, TileType.BLUE_MARKET, TileType.EMPTY, TileType.EMPTY},
                {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}
        };
        Map itemsOnGround = new HashMap<>();
        itemsOnGround.put(new Point(0,0), new ArrayList());

        PlayerBoardView boardView = new PlayerBoardView(boardTileTypes, itemsOnGround, new Point(1 ,2), new Point(1, 1), 0);
        redStrategy.initialize(4, 0,80, 10, boardView, new Point(0,0), false,  new Random());
        assertEquals(TurnAction.MOVE_DOWN, redStrategy.getTurnAction(boardView, new Economy(new ItemType[]{}), 80, true));
    }

    @Test
    public void testGetTurnActionPickUp() {
        TileType[][] boardTileTypes = new TileType[][]{
                {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},
                {TileType.EMPTY, TileType.BLUE_MARKET, TileType.RED_MARKET, TileType.EMPTY},
                {TileType.EMPTY, TileType.RED_MARKET, TileType.BLUE_MARKET, TileType.EMPTY},
                {TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}
        };
        Map itemsOnGround = new HashMap<>();
        List<InventoryItem> itemList = new ArrayList<InventoryItem>();
        itemList.add(new InventoryItem(ItemType.DIAMOND));
        itemsOnGround.put(new Point(0,0), itemList);

        PlayerBoardView boardView = new PlayerBoardView(boardTileTypes, itemsOnGround, new Point(0 ,0), new Point(1, 1), 0);
        redStrategy.initialize(4, 5,80, 10, boardView, new Point(0,0), true,  new Random());
        assertEquals(TurnAction.PICK_UP_RESOURCE, redStrategy.getTurnAction(boardView, new Economy(new ItemType[]{}), 10, true));
    }
}