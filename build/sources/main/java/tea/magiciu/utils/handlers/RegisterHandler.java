package tea.magiciu.utils.handlers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tea.magiciu.init.InitBlocks;
import tea.magiciu.init.InitItems;
import tea.magiciu.utils.FurnaceRecipes;
import tea.magiciu.utils.interfaces.IHasModel;

@Mod.EventBusSubscriber
public class RegisterHandler
{
    @SubscribeEvent
public static void onItemRegister(RegistryEvent.Register<Item> event)
{
    event.getRegistry().registerAll(InitItems.ITEMS.toArray(new Item[0]));
}

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().registerAll(InitBlocks.BLOCKS.toArray(new Block[0]));
    }



    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event)
    {
        for (Item item : InitItems.ITEMS)
        {
            if (item instanceof IHasModel)
            {
                ((IHasModel)item).registerModels();
            }
            for (Block block : InitBlocks.BLOCKS)
            {
            if (block instanceof IHasModel)
            {
                ((IHasModel)block).registerModels();
            }
            }

        }


    }
    public static void otherRegister()
    {
        FurnaceRecipes.registerRecipes();
    }
}
