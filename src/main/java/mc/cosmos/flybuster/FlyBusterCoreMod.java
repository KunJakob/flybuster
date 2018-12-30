package mc.cosmos.flybuster;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public class FlyBusterCoreMod implements IFMLLoadingPlugin {
    static File modFile;

    public FlyBusterCoreMod() {
        MixinBootstrap.init();

        //TODO debug options
        MixinEnvironment.getDefaultEnvironment().setOption(MixinEnvironment.Option.DEBUG_EXPORT_DECOMPILE, true);
        MixinEnvironment.getDefaultEnvironment().setOption(MixinEnvironment.Option.DEBUG_EXPORT, true);

        Mixins.addConfiguration("mixins.flybuster.json");
    }


    @Override
    public String[] getASMTransformerClass() {
        return new String[]{};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        modFile = (File) data.get("coremodLocation");
        if (modFile == null) {
            modFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        }
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}