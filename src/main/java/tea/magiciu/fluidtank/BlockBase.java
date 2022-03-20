package tea.magiciu.fluidtank;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockBase extends BlockContainer
{
    public BlockBase(String name, Material material)
    {
        super(material);

        this.setRegistryName(name);
        this.setUnlocalizedName(name);
    }

    @Override
    public TileEntityFluidTank createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityFluidTank();
    }
}