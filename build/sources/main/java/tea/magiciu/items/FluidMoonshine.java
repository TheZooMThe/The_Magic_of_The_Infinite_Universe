package tea.magiciu.items;

import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import tea.magiciu.MAGICIU;
import tea.magiciu.utils.interfaces.IHasModel;

public class FluidMoonshine extends Fluid
{
    /**
     * Конструктор нашей жидкости
     * @param fluidName - Название жидкости
     * @param still - Название файла "стоячий" жидкости
     * @param flowing - Название файла "текучей" жидкости
     */
    public FluidMoonshine(String fluidName, ResourceLocation still, ResourceLocation flowing)
    {
        super(fluidName, still, flowing);
    }


}
