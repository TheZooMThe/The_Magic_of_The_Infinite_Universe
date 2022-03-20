package tea.magiciu.misc;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import tea.magiciu.init.InitItems;


public class MagicIU_Tab extends CreativeTabs
{
    public MagicIU_Tab(String label)
    {
        super(label);
    }
    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(InitItems.BUCKETMOONSHINE);
    }

}
