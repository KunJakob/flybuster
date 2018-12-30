package mc.cosmos.flybuster.listeners;

import com.flowpowered.math.vector.Vector3d;
import mc.cosmos.flybuster.events.ToggleFlyEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.title.Title;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class WallRideListener {


    @Listener
    public void onPlayerMove(MoveEntityEvent e) {
        if (e.getTargetEntity() instanceof Player) {
            Player p = (Player) e.getTargetEntity();
            p.offer(Keys.CAN_FLY, true);
            Sponge.getServer().getConsole().sendMessage(Text.of("whaddafaq"));
            if (p.get(Keys.IS_SNEAKING).orElse(false)) {
                if (isNearWall(p)) {
                    //if player has WallRide passive
                    movePlayer(p);
                }
            }
        }
    }

    private void movePlayer(Player p) {
        p.sendTitle(Title.builder().actionBar(Text.of(TextColors.GREEN, Text.of("fuck nickimpact"))).stay(3).fadeIn(1).fadeOut(1).build());

        Vector3d vec = p.getTransform().getRotationAsQuaternion().getDirection();
        p.setVelocity(vec.mul(0.2D));
    }

    private boolean isNearWall(Player p) {
        Location<World> loc = p.getLocation();
        boolean isNear = false;
        if (!loc.add(1, 0, 0).getBlockType().equals(BlockTypes.AIR)) isNear = true;
        else if (!loc.add(-1, 0, 0).getBlockType().equals(BlockTypes.AIR)) isNear = true;
        else if (!loc.add(0, 0, 1).getBlockType().equals(BlockTypes.AIR)) isNear = true;
        else if (!loc.add(0, 0, -1).getBlockType().equals(BlockTypes.AIR)) isNear = true;
        return isNear;
    }

    @Listener
    public void onToggleFlyEvent(ToggleFlyEvent event) {
        if (event.isFlying()) {
            Player p = event.getCause().first(Player.class).get();
            p.offer(Keys.IS_FLYING, false);
            p.offer(Keys.CAN_FLY, false);
            Vector3d vec = p.getTransform().getRotationAsQuaternion().getDirection();
            p.setVelocity(vec.mul(0.5).add(0, 0.7   , 0));
        }

    }

    @Listener
    public void onMove(MoveEntityEvent event) {

        if (event.getTargetEntity() instanceof Player) {

            Player player = (Player) event.getTargetEntity();

            if (player.getLocation().add(0, -1, 0).getBlock().getType().equals(BlockTypes.AIR)) return;

        }
    }
}
