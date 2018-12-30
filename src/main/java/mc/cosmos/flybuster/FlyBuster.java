package mc.cosmos.flybuster;

import com.google.inject.Inject;
import mc.cosmos.flybuster.listeners.WallRideListener;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

@Plugin(
        id = "flybuster",
        name = "FlyBuster",
        description = "Insect Swatting Minigame",
        url = "https://tobe.determined",
        authors = {
                "Smackzter"
        },
        version = "1.0"
)
public class FlyBuster {

    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        Sponge.getEventManager().registerListeners(this, new WallRideListener());
        Sponge.getServer().getConsole().sendMessage(Text.of("STARTED"));
    }

    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event) {
        Sponge.getServer().getConsole().sendMessage(Text.of("JOINED"));
    }
}
