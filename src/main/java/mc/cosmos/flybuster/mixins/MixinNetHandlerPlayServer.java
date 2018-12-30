package mc.cosmos.flybuster.mixins;

import mc.cosmos.flybuster.events.ToggleFlyEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.client.CPacketPlayerAbilities;
import org.spongepowered.api.Sponge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayServer.class)
public abstract class MixinNetHandlerPlayServer {

    @Shadow
    public EntityPlayerMP player;
    private boolean currentFlyState = false;

    @Inject(method = "processPlayerAbilities", at = @At("HEAD"))
    public void onProcessPlayerAbilitiesHead(CPacketPlayerAbilities packetIn, CallbackInfo ci) {
        currentFlyState = this.player.capabilities.isFlying;
    }

    @Inject(method = "processPlayerAbilities", at = @At("RETURN"))
    public void onProcessPlayerAbilitiesReturn(CPacketPlayerAbilities packetIn, CallbackInfo ci) {
        if ((packetIn.isFlying() && this.player.capabilities.allowFlying) != currentFlyState) {
            Sponge.getCauseStackManager().pushCause(this.player);
            Sponge.getEventManager().post(new ToggleFlyEvent(!currentFlyState, Sponge.getCauseStackManager().getCurrentCause()));
            Sponge.getCauseStackManager().popCause();
        }
    }
}