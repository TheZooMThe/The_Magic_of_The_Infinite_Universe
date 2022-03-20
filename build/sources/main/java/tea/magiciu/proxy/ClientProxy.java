package tea.magiciu.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tea.magiciu.init.BlocksRegister;
import tea.magiciu.init.FluidRegister;

public class ClientProxy extends CommonProxy
{
    public void preInit(FMLPreInitializationEvent event)
    {

        super.preInit(event);






    }
    public void registerItemRenderer(Item item, int meta, String id)
    {

        ModelLoader.setCustomModelResourceLocation (item,meta,new ModelResourceLocation(item.getRegistryName(), id));

    }


    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
        BlocksRegister.registerRender();

    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
    }
}
