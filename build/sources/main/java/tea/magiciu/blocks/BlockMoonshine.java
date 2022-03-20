package tea.magiciu.blocks;

import net.minecraft.block.Block;


import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import tea.magiciu.MAGICIU;


public class BlockMoonshine extends BlockFluidClassic
{
    /**
     * Это конструктор который передаст наши значения родителю.
     * @param fluid - это наша жидкость
     * @param  - это материал нашего блока. Существует два метериала, Water - ведёт себя как вода, быстро течёт
     * Lava - ведёт себя как лава, медленно течёт.
     */
    public BlockMoonshine(Fluid fluid)
    {
        super(fluid, Material.WATER);
        setCreativeTab(MAGICIU.MAGICIU_TAB);
        setRegistryName("moonshine");
        setUnlocalizedName("moonshine");

    }

    /**
     * Проверка на то, что блок был поставлен
     */
    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
        super.onBlockAdded(world, pos, state);
        mergerFluids(pos, world);
    }

    /**
     * Это проверка на соседние блоки.
     */



    private void mergerFluids(BlockPos pos, World world)
    {
        for(EnumFacing facing : EnumFacing.values())
        {
            Block block = world.getBlockState(pos.offset(facing)).getBlock();
            //Если вода, то ставим камень
            if(block == Blocks.WATER || block == Blocks.FLOWING_WATER)
            {
                world.setBlockState(pos.offset(facing), Blocks.STONE.getDefaultState());
            }
            //Если лава, ставим кирпичный блок
            else if(block == Blocks.LAVA || block == Blocks.FLOWING_LAVA)
            {
                world.setBlockState(pos.offset(facing), Blocks.BRICK_BLOCK.getDefaultState());
            }
        }
    }
}

