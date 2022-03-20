package tea.magiciu.init;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tea.magiciu.MAGICIU;
import tea.magiciu.Reference;

import tea.magiciu.items.FluidMoonshine;

import java.util.ArrayList;
import java.util.List;

public class FluidRegister
{
    public static final FluidMoonshine MOONSHINE = (FluidMoonshine) new FluidMoonshine("moonshine",
            new ResourceLocation(Reference.MODID, "fluid/moonshine_still"),
            new ResourceLocation(Reference.MODID, "fluid/moonshine_flow"))
            .setDensity(1100)  //Плотность. Влияет на скорость движения сущностей. По умолчанию 1000.
            .setGaseous(false) //Если true то течет вверх. По умолчанию false.
            .setLuminosity(5)  //Уровень светимости. По умолчанию 0.
            .setViscosity(900) //Влияет на скорость течения. Больше значение меньше скорость. По умолчанию 1000.
            .setTemperature(600) //Температура. По умолчанию 300. Влияет на скорость таинья блоков поблизости.
            .setUnlocalizedName("moonshine");

    public static void register()
    {
        registerFluid(MOONSHINE);
    }
    public static void registerFluid(Fluid fluid)
    {
        FluidRegistry.registerFluid(fluid);
        FluidRegistry.addBucketForFluid(fluid);
    }
}
