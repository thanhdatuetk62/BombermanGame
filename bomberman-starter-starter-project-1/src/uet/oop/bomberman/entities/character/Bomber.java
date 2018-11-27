package uet.oop.bomberman.entities.character;

import java.util.Iterator;
import java.util.List;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.Sound.Action;
import uet.oop.bomberman.Sound.Sound;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.FlameSegment;
import uet.oop.bomberman.entities.character.ai.AI;
import uet.oop.bomberman.entities.character.ai.AIBomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.Item;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.level.Coordinates;

public class Bomber extends Character
{

    private final int time = 15;
    protected Keyboard _input;
    protected AI _ai;                           //TODO: Just for AI
    protected int _finalAnimation = 30;
    /**
     * nếu giá trị này < 0 thì cho phép đặt đối tượng Bomb tiếp theo,
     * cứ mỗi lần đặt 1 Bomb mới, giá trị này sẽ được reset về 0 và giảm dần trong mỗi lần update()
     */
    protected int _timeBetweenPutBombs = 0;
    private List<Bomb> _bombs;
    private boolean _auto = false;              //TODO: Just for AI
    private boolean isPlaceBomb = false;        //TODO: Just for AI
    private int countTime = 0;
    private boolean renderable = true;
    private int direction;
    private int maxSteps;
    private int steps;

    public Bomber(int x, int y, Board board)
    {
        super(x, y, board);
        _timeAfter = 250;
        _bombs = _board.getBombs();
        _input = _board.getInput();
        _sprite = Sprite.player_right;
        _ai = new AIBomber(_board, this);
        maxSteps = (int) Math.round(Game.TILES_SIZE / Game.getBomberSpeed());
        steps = maxSteps + 1;
    }

    @Override
    public void update()
    {
        animate();
        clearBombs();
        if (!_alive)
        {
            afterKill();
            return;
        }
        if (_timeBetweenPutBombs < -7500) _timeBetweenPutBombs = 0;
        else _timeBetweenPutBombs--;
        calculateMove();
        detectPlaceBomb();
    }

    @Override
    public void render(Screen screen)
    {
        calculateXOffset();

        if (_alive) chooseSprite();
        else
        {
            _sprite = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, _animate, 60);
        }
        if (renderable) screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);
    }

    public void calculateXOffset()
    {
        int xScroll = Screen.calculateXOffset(_board, this);
        Screen.setOffset(xScroll, 0);
    }

    public boolean isPlaceBomb()
    {
        return isPlaceBomb;
    }

    /**
     * Kiểm tra xem có đặt được bom hay không? nếu có thì đặt bom tại vị trí hiện tại của Bomber
     */
    private void detectPlaceBomb()
    {
        // TODO: kiểm tra xem phím điều khiển đặt bom có được gõ và giá trị _timeBetweenPutBombs, Game.getBombRate() có thỏa mãn hay không
        // TODO:  Game.getBombRate() sẽ trả về số lượng bom có thể đặt liên tiếp tại thời điểm hiện tại
        // TODO: _timeBetweenPutBombs dùng để ngăn chặn Bomber đặt 2 Bomb cùng tại 1 vị trí trong 1 khoảng thời gian quá ngắn
        // TODO: nếu 3 điều kiện trên thỏa mãn thì thực hiện đặt bom bằng placeBomb()
        // TODO: sau khi đặt, nhớ giảm số lượng Bomb Rate và reset _timeBetweenPutBombs về 0
        if (_auto)
        {
            if (isPlaceBomb && _timeBetweenPutBombs < 0 && Game.getBombRate() > 0)
            {
                if (Game.getBombRate() >= 1)
                {
                    _timeBetweenPutBombs = 30;
                } else _timeBetweenPutBombs = 0;
                placeBomb(Coordinates.pixelToTile(_x + Game.TILES_SIZE / 2), Coordinates.pixelToTile(_y - Game.TILES_SIZE / 2));
                isPlaceBomb = false;
            }
            return;
        }
        if (_input.space && _timeBetweenPutBombs < 0 && Game.getBombRate() > 0)
        {
            if (Game.getBombRate() >= 1)
            {
                _timeBetweenPutBombs = 30;
            } else _timeBetweenPutBombs = 0;
            placeBomb(Coordinates.pixelToTile(_x + Game.TILES_SIZE / 2), Coordinates.pixelToTile(_y - Game.TILES_SIZE / 2));
        }
    }

    protected void placeBomb(int x, int y)
    {
        // TODO: thực hiện tạo đối tượng bom, đặt vào vị trí (x, y)
        Entity downThere = _board.getEntity((double) x, (double) y, this);
        if (downThere instanceof LayeredEntity)
        {
            if (((LayeredEntity) downThere).getTopEntity() instanceof Portal) return;
        }
        if (downThere instanceof Bomb || downThere instanceof Enemy) return;
        _board.addBomb(new Bomb(x, y, _board));
        Thread t = new Thread(new Sound(Action.placeBomb, false));
        t.start();
        Game.addBombRate(-1);
    }
    public boolean canPlaceBomb() {
        Entity downthere = _board.getEntity(getXTile(), getYTile(), this);
        if(downthere instanceof LayeredEntity)
        {
            if(((LayeredEntity) downthere).getTopEntity() instanceof Portal)
                return false;
        }
        if(downthere instanceof Bomb||downthere instanceof Enemy)
            return false;
        if(_timeBetweenPutBombs>=0 || Game.getBombRate()<=0)
            return false;
        return true;
    }
    private void clearBombs()
    {
        Iterator<Bomb> bs = _bombs.iterator();

        Bomb b;
        while (bs.hasNext())
        {
            b = bs.next();
            if (b.isRemoved())
            {
                bs.remove();
                Game.addBombRate(1);
            }
        }

    }

    @Override
    public void kill()
    {
        if (!_alive) return;
        Thread t = new Thread(new Sound(Action.bomberDied, false));
        t.start();
        _alive = false;
    }

    @Override
    protected void afterKill()
    {
        if (_timeAfter > 0)
        {
            --_timeAfter;
            if (_finalAnimation > 0) _finalAnimation--;
            else renderable = false;
        } else
        {
            _board.endGame();
        }
    }

    @Override
    protected void calculateMove()
    {
        // TODO: xử lý nhận tín hiệu điều khiển hướng đi từ _input và gọi move() để thực hiện di chuyển
        // TODO: nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển
        if (_auto)
        {
            processAuto();
        } else
        {
            processManual();
        }
    }

    private void processAuto()
    {
        Thread t = new Thread(new Sound(Action.bomberWalk, false));
        if (steps == maxSteps + 1) direction = _ai.calculateDirection();
        steps--;
        if (steps == 0)
        {
            steps = maxSteps;
            direction = _ai.calculateDirection();
            System.out.println(direction);
        }

        switch (direction)
        {
            case 0:
                _moving = true;
                move(_x, _y + Game.getBomberSpeed());
                countTime--;
                if (countTime < 0)
                {
                    t.start();
                    countTime = time;
                }
                break;
            case 1:
                _moving = true;
                move(_x + Game.getBomberSpeed(), _y);
                countTime--;
                if (countTime < 0)
                {
                    t.start();
                    countTime = time;
                }
                break;
            case 2:
                _moving = true;
                move(_x - Game.getBomberSpeed(), _y);
                countTime--;
                if (countTime < 0)
                {
                    t.start();
                    countTime = time;
                }
                break;
            case 3:
                _moving = true;
                move(_x, _y - Game.getBomberSpeed());
                countTime--;
                if (countTime < 0)
                {
                    t.start();
                    countTime = time;
                }
                break;
            case 4:
                _moving = false;
                countTime = 0;
                isPlaceBomb = true;
                break;
            default:
                _moving = false;
                countTime = 0;
                break;
        }
    }

    private void processManual()
    {
        Thread t = new Thread(new Sound(Action.bomberWalk, false));
        if (_input.down)
        {
            _moving = true;
            move(_x, _y + Game.getBomberSpeed());
            countTime--;
            if (countTime < 0)
            {
                t.start();
                countTime = time;
            }
        } else if (_input.left)
        {
            _moving = true;
            move(_x - Game.getBomberSpeed(), _y);
            countTime--;
            if (countTime < 0)
            {
                t.start();
                countTime = time;
            }
        } else if (_input.right)
        {
            _moving = true;
            move(_x + Game.getBomberSpeed(), _y);
            countTime--;
            if (countTime < 0)
            {
                t.start();
                countTime = time;
            }
        } else if (_input.up)
        {
            _moving = true;
            move(_x, _y - Game.getBomberSpeed());
            countTime--;
            if (countTime < 0)
            {
                t.start();
                countTime = time;
            }
        } else
        {
            countTime = 0;
            _moving = false;
        }
    }

    @Override
    public boolean canMove(double x, double y)
    {
        // TODO: kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di chuyển tới đó hay không
        double loLy = y - 1;
        double loRy = y - 1;
        double upLy = y + 1 - Game.TILES_SIZE;
        double upRy = y + 1 - Game.TILES_SIZE;
        double upLx = x + 1;
        double loLx = x + 1;
        double upRx = x - 1 + Game.TILES_SIZE * 3 / 4;
        double loRx = x - 1 + Game.TILES_SIZE * 3 / 4;

        int tile_UpLx = Coordinates.pixelToTile(upLx);
        int tile_UpLy = Coordinates.pixelToTile(upLy);

        int tile_UpRx = Coordinates.pixelToTile(upRx);
        int tile_UpRy = Coordinates.pixelToTile(upRy);

        int tile_LoLx = Coordinates.pixelToTile(loLx);
        int tile_LoLy = Coordinates.pixelToTile(loLy);

        int tile_LoRx = Coordinates.pixelToTile(loRx);
        int tile_LoRy = Coordinates.pixelToTile(loRy);

        Entity entity_UpLeft = _board.getEntity(tile_UpLx, tile_UpLy, this);
        Entity entity_UpRight = _board.getEntity(tile_UpRx, tile_UpRy, this);
        Entity entity_LoLeft = _board.getEntity(tile_LoLx, tile_LoLy, this);
        Entity entity_LoRight = _board.getEntity(tile_LoRx, tile_LoRy, this);
        if (entity_LoLeft instanceof Wall || entity_LoRight instanceof Wall || entity_UpLeft instanceof Wall || entity_UpRight instanceof Wall)
        {
            return false;
        }
        if (entity_LoLeft instanceof LayeredEntity || entity_LoRight instanceof LayeredEntity || entity_UpLeft instanceof LayeredEntity || entity_UpRight instanceof LayeredEntity)
        {
            if (entity_LoLeft instanceof LayeredEntity)
            {
                Entity top = ((LayeredEntity) entity_LoLeft).getTopEntity();
                if (top instanceof Brick) return false;
                if (top instanceof Item)
                {
                    top.collide(this);
                    return true;
                }
                if (top instanceof Portal && _board.detectNoEnemies())
                {
                    if (top.collide(this)) _board.nextLevel();
                    return true;
                }
            }
            if (entity_LoRight instanceof LayeredEntity)
            {
                Entity top = ((LayeredEntity) entity_LoRight).getTopEntity();
                if (top instanceof Brick) return false;
                if (top instanceof Item)
                {
                    top.collide(this);
                    return true;
                }
                if (top instanceof Portal && _board.detectNoEnemies())
                {
                    if (top.collide(this)) _board.nextLevel();
                    return true;
                }
            }
            if (entity_UpLeft instanceof LayeredEntity)
            {
                Entity top = ((LayeredEntity) entity_UpLeft).getTopEntity();
                if (top instanceof Brick) return false;
                if (top instanceof Item)
                {
                    top.collide(this);
                    return true;
                }
                if (top instanceof Portal && _board.detectNoEnemies())
                {
                    if (top.collide(this)) _board.nextLevel();
                    return true;
                }
            }
            if (entity_UpRight instanceof LayeredEntity)
            {
                Entity top = ((LayeredEntity) entity_UpRight).getTopEntity();
                if (top instanceof Brick) return false;
                if (top instanceof Item)
                {
                    top.collide(this);
                    return true;
                }
                if (top instanceof Portal && _board.detectNoEnemies())
                {
                    if (top.collide(this)) _board.nextLevel();
                    return true;
                }
            }
        }
        if (collide(entity_LoLeft) || collide(entity_LoRight) || collide(entity_UpLeft) || collide(entity_UpRight))
            return false;
        return true;
    }

    private void soften(double xa, double ya)
    {
        if (xa != _x && _y == ya)
        {
            double near1 = ((int) ya / Game.TILES_SIZE) * Game.TILES_SIZE;
            double near2 = ((int) ya / Game.TILES_SIZE + 1) * Game.TILES_SIZE;
            if (ya - near1 <= 8)
            {
                if (canMove(xa, near1))
                {
                    _y--;
                    soften(xa, ya--);
                    if (xa > _x) _direction = 4;
                    else _direction = 3;
                }
            }
            if (near2 - ya <= 8)
            {
                if (canMove(xa, near2))
                {
                    _y++;
                    move(xa, ya++);
                    if (xa > _x) _direction = 4;
                    else _direction = 3;
                }
            }
        } else if (xa == _x && _y != ya)
        {
            double near1 = ((int) xa / Game.TILES_SIZE) * Game.TILES_SIZE;
            double near2 = ((int) xa / Game.TILES_SIZE + 1) * Game.TILES_SIZE;
            if (xa - near1 <= 8)
            {
                if (canMove(near1, ya))
                {
                    _x--;
                    soften(xa--, ya);
                }
            }
            if (near2 - xa <= 8)
            {
                if (canMove(near2, ya))
                {
                    _x++;
                    soften(xa++, ya);
                    _direction = 1;
                }
            }
        }
    }

    @Override
    public void move(double xa, double ya)
    {
        // TODO: sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không và thực hiện thay đổi tọa độ _x, _y
        // TODO: nhớ cập nhật giá trị _direction sau khi di chuyển
        if (_y < ya) _direction = 2;
        if (_y > ya) _direction = 0;
        if (_x > xa) _direction = 3;
        if (_x < xa) _direction = 4;
        if (canMove(xa, ya))
        {
            _x = xa;
            _y = ya;
        } else
        {
            soften(xa, ya);
        }

    }

    @Override
    public boolean collide(Entity e)
    {
        // TODO: xử lý va chạm với Flame
        // TODO: xử lý va chạm với Enemy
        if (e instanceof FlameSegment) e.collide(this);
        if (e instanceof Enemy)
        {
            if (getXTile() == e.getXTile() && getYTile() == e.getYTile()) kill();
        }
        if (e instanceof Bomb)
        {
            if (e.collide(this)) return true;
        }

        return false;
    }

    private void chooseSprite()
    {
        switch (_direction)
        {
            case 0:
                _sprite = Sprite.player_up;
                if (_moving)
                {
                    _sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, _animate, 20);
                }
                break;
            case 1:
                _sprite = Sprite.player_right;
                if (_moving)
                {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
            case 2:
                _sprite = Sprite.player_down;
                if (_moving)
                {
                    _sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, _animate, 20);
                }
                break;
            case 3:
                _sprite = Sprite.player_left;
                if (_moving)
                {
                    _sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, _animate, 20);
                }
                break;
            default:
                _sprite = Sprite.player_right;
                if (_moving)
                {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
        }
    }

    public void setAuto(boolean q)
    {
        _auto = q;
    }
    public void setPlaceBomb(boolean q) {
        isPlaceBomb = q;
    }
}
