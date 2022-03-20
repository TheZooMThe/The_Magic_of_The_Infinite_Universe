package tea.magiciu.fluidtank;

import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tea.magiciu.MAGICIU;
import tea.magiciu.init.BlocksRegister;
import tea.magiciu.utils.handlers.FluidHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class WoodenBurrel extends BlockTileEntity<TileEntityFluidTank>
{

    public static final PropertyEnum<EnumFacing> FACING = PropertyDirection.create("facing");
    public WoodenBurrel(String name, Material material)
    {
        super(name, material);
        setCreativeTab(MAGICIU.MAGICIU_TAB);
        setHardness(3.0F);
        this.setResistance(15.0F);
        setHarvestLevel("axe",0);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {

        // Если игрок приседает - возвращаем false (т.е. - жидкость разольётся в мир)
        if (player.isSneaking()) return false;

        // Проверка на сервер. Обязательно для Tile Entity
        if(!world.isRemote)
        {
            TileEntityFluidTank tile = this.getTileEntity(world, pos);

            if (tile != null)
            {
                ItemStack is = player.getHeldItemMainhand();

                // Если в руке есть предмет
                if (!is.isEmpty())
                {
                    // Получаем стак с жидкостью в нашей руке
                    FluidStack liquid = FluidHelper.getFluidForFilledItem(is);

                    if (liquid != null)
                    {
                        // Вызываем метод заполнения с аргументом false, чтобы получить количество жидкости, которое будет заполнено
                        int amount = tile.fill(liquid, false);

                        // Проверяем что количество заполняемой жидкости равное количеству жидкости в нашей руке
                        if (amount == liquid.amount)
                        {
                            // Вызываем метод заполнения с аргументом true, который заполнит нашу цистерну жидкостью
                            tile.fill(liquid, true);
                            if (!player.capabilities.isCreativeMode)
                            {
                                // Если игрок не в креативе - заменяем предмет в руке на пустое ведро
                                player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.BUCKET));
                            }
                            // Возвращаем true как успешное взаимодействие с блоком (его заполнение жидкостью)
                            return true;
                        }

                        else
                            return true;
                    }

                    // Проверяем что у нас в руке пустое ведро
                    if (FluidHelper.isBucket(is))
                    {
                        // Получаем жидкость внутри нашей цистерны (переменная tile)
                        IFluidTankProperties[] tanks = tile.getTankProperties();
                        if (tanks[0] != null)
                        {
                            // Получаем стак жидкости из цистерны
                            FluidStack fillFluid = tanks[0].getContents();
                            // Получаем заполненное ведро с этой жидкостью
                            ItemStack fillStack = FluidUtil.getFilledBucket(fillFluid);
                            if (fillStack != null)
                            {
                                // Осушаем нашу цистерну на количество, которое мы забрали ведром
                                tile.drain(FluidUtil.getFluidContained(fillStack).amount, true);
                                if (!player.capabilities.isCreativeMode)
                                {
                                    // Если ведро одно
                                    if (is.getCount() == 1)
                                    {
                                        is.shrink(1);
                                        player.inventory.setInventorySlotContents(player.inventory.currentItem, fillStack);
                                    } else {
                                        is.shrink(1);
                                        player.inventory.setInventorySlotContents(player.inventory.currentItem, is);

                                        // Выбрасываем наполненное ведро в мир если инвентарь заполнен
                                        if (!player.inventory.addItemStackToInventory(fillStack))
                                            player.dropItem(fillStack, false);
                                    }
                                }
                                // Возвращаем true как успешное взаимодействие с блоком (его заполнение жидкостью)
                                return true;
                            }
                            else
                                return true;
                        }
                    }
                } else {
                    // Если мы нажмём пустой рукой и в цистерне есть жидкость - отобразит её количество на экран
                    if (tile.tank.getInfo() != null && tile.tank.getInfo().fluid != null)
                        player.sendMessage(new TextComponentString("" + tile.tank.getInfo().fluid.getLocalizedName() + " / " + tile.tank.getInfo().fluid.amount));
                }
                return true;
            }
        }
        return true;
    }



    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        // Устанавливаем коллизии нашей цистерне
        return new AxisAlignedBB(2 / 16D, 0 / 16D, 2 / 16D, 14 / 16D, 16 / 16D, 14 / 16D);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        // Для того чтобы наша цистерна была прозрачной
        return false;
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public int quantityDropped(Random rnd)   //Отвечает за кол-во дропа
    {
        return 1; //Кол-во дропа
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return 0;
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
    {
        return world.setBlockToAir(pos);
    }

    @Override
    public void onBlockExploded(World world, BlockPos pos, Explosion explosion)
    {
        world.setBlockToAir(pos);
        onBlockDestroyedByExplosion(world, pos, explosion);
    }
    @Override
    public Class<TileEntityFluidTank> getTileEntityClass()
    {
        return TileEntityFluidTank.class;
    }

    @Override
    public TileEntityFluidTank createTileEntity(World world, IBlockState blockState)
    {
        return new TileEntityFluidTank();
    }
    //ОСнова закончена

}