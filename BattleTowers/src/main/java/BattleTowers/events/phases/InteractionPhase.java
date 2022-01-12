package BattleTowers.events.phases;

import BattleTowers.events.PhasedEvent;
import BattleTowers.util.Method;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.function.Consumer;

public class InteractionPhase extends EventPhase {
    private final InteractionHandler handler;

    public InteractionPhase(Method update, Consumer<SpriteBatch> render) {
        this.handler = new InteractionHandler() {
            @Override
            public void update() {
                update.execute();
            }

            @Override
            public void render(SpriteBatch sb) {
                render.accept(sb);
            }
        };
    }
    public InteractionPhase(InteractionHandler handler) {
        this.handler = handler;
    }

    @Override
    public void transition(PhasedEvent event) {
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.EVENT;
        AbstractDungeon.rs = AbstractDungeon.RenderScene.EVENT;
        event.resetCardRarity();
        event.allowRarityAltering = true;
        handler.begin(event);
    }

    @Override
    public void update() {
        handler.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        handler.render(sb);
    }

    @Override
    public void renderAboveTopPanel(SpriteBatch sb) {
        handler.renderAboveTopPanel(sb);
    }

    @Override
    public void hide(PhasedEvent event) {

    }

    public interface InteractionHandler {
        default void begin(PhasedEvent event) {};
        default void update() {};
        default void render(SpriteBatch sb) {};
        default void renderAboveTopPanel(SpriteBatch sb) {};
    }
}
