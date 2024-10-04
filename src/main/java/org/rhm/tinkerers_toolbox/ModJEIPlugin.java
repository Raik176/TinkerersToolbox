package org.rhm.tinkerers_toolbox;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import net.minecraft.util.Identifier;

@JeiPlugin
public class ModJEIPlugin implements IModPlugin {

    @Override
    public Identifier getPluginUid() {
        return Identifier.of("jei", ModMain.MOD_ID); //idk the naming convention, i hope this is valid
    }
}
