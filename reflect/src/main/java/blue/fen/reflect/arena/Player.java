package blue.fen.reflect.arena;

import java.io.Closeable;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.arena <br/>
 * 创建时间：2023/4/29 13:28 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：选手类
 */
public abstract class Player implements Closeable {
    /**
     * 得分
     */
    private int score;

    /**
     * 允许比赛轮数
     */
    private final int programNumber;

    /**
     * 当前表演轮数
     */
    private int programCount = 0;

    protected Player(int score, int programNumber) {
        this.score = score;
        this.programNumber = programNumber;
    }

    /**
     * 是否完成比赛
     */
    public boolean isComplete() {
        return programCount >= programNumber;
    }

    /**
     * 进行表演
     */
    public void perform() {
        if (!isComplete()) {
            programCount++;
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public int getParamLength() {
        return programNumber;
    }


    private boolean active = false;

    public boolean isActive() {
        return active;
    }

    /**
     * 参加比赛
     */
    public void attach() {
        active = true;
    }

    /**
     * 结束表演
     */
    @Override
    public void close() {
        active = false;
    }
}
