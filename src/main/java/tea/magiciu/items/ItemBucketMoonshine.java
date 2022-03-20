package tea.magiciu.items;

import net.minecraft.item.Item;
import tea.magiciu.MAGICIU;
import tea.magiciu.init.InitItems;
import tea.magiciu.utils.interfaces.IHasModel;

public class ItemBucketMoonshine extends Item implements IHasModel
{
    public ItemBucketMoonshine(String name)
    {
    setRegistryName(name);
    setUnlocalizedName(name);
    setCreativeTab(MAGICIU.MAGICIU_TAB);
    setMaxStackSize(1);


    InitItems.ITEMS.add(this);
}
    @Override
    public void registerModels()
    {
        MAGICIU.proxy.registerItemRenderer(this,0,"inventory");
    }




}
