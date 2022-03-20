package tea.magiciu.init;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tea.magiciu.MAGICIU;
import tea.magiciu.blocks.BlockMoonshine;
import tea.magiciu.fluidtank.WoodenBurrel;

public class BlocksRegister
{
    public static Block MOONSHINE_BLOCK = new BlockMoonshine(FluidRegister.MOONSHINE);

    public static final Block WOODENBURREL = new WoodenBurrel("woodenburrel", Material.WOOD);

    public static void register()
    {
        setRegister(MOONSHINE_BLOCK);
        setRegister(WOODENBURREL);
    }

    @SideOnly(Side.CLIENT)
    public static void registerRender()
    {
    setRender(MOONSHINE_BLOCK);
    setRender(WOODENBURREL);
    }

    private static void setRegister(Block block) {
        ForgeRegistries.BLOCKS.register(block);
        ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }

    @SideOnly(Side.CLIENT)
    private static void setRender(Block block)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));

    }
}
