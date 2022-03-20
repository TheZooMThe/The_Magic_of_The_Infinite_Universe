package tea.magiciu.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tea.magiciu.init.BlocksRegister;
import tea.magiciu.init.FluidRegister;

public class CommonProxy
{

    public void preInit(FMLPreInitializationEvent event)
    {
        FluidRegister.register();
        BlocksRegister.register();

    }
    public void registerItemRenderer(Item item, int meta, String id)
    {

    }



    public void init(FMLInitializationEvent event)
    {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }

}
