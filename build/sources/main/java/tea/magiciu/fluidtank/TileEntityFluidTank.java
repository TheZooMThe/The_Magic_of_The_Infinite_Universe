package tea.magiciu.fluidtank;

import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;

public class TileEntityFluidTank extends TileEntity implements IFluidHandler
{
    public FluidTank tank;
    private int capacity;

    public TileEntityFluidTank()
    {
        // Устанавливаем вместимость цистерны. В данном случае вместимость в 5 вёдер
        capacity = Fluid.BUCKET_VOLUME * 10;
        tank = new FluidTank(capacity);
    }

    // Метод, которым можно будет уменьшить вместимость цистерны
    public void decreaseTankCapacity(int tankCapacityModifier)
    {
        if (tank.getCapacity() > Fluid.BUCKET_VOLUME * tankCapacityModifier)
        {
            tank.setCapacity(tank.getCapacity() - (Fluid.BUCKET_VOLUME * tankCapacityModifier));
        } else {
            tank.setCapacity(0);
        }
    }

    // Метод, которым можно будет увеличить вместимость цистерны
    public void increaseTankCapacity(int tankCapacityModifier)
    {
        tank.setCapacity(tank.getCapacity() + (Fluid.BUCKET_VOLUME * tankCapacityModifier));
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        this.tank.setCapacity(nbt.getInteger("TankCapacity") * Fluid.BUCKET_VOLUME);
        if (nbt.getBoolean("hasFluid"))
        {
            this.tank.setFluid(FluidRegistry.getFluidStack(nbt.getString("fluidName"), nbt.getInteger("fluidAmount")));
        } else {
            this.tank.setFluid(null);
        }
        super.readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        nbt.setBoolean("hasFluid", tank.getFluid() != null);

        if (tank.getFluid() != null)
        {
            nbt.setString("fluidName", tank.getFluid().getFluid().getName());
            nbt.setInteger("fluidAmount", tank.getFluidAmount());
        }

        nbt.setInteger("TankCapacity", tank.getCapacity() / Fluid.BUCKET_VOLUME);
        return super.writeToNBT(nbt);
    }

    @Override
    public int fill(FluidStack resource, boolean doFill)
    {
        int amount = tank.fill(resource, doFill);

        if (amount > 0 && doFill)
            world.markBlockRangeForRenderUpdate(pos, pos);

        return amount;
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain)
    {
        if (tank.getFluidAmount() > 0 && tank.getFluid().getFluid() != resource.getFluid())
        {
            return this.drain(resource.amount, doDrain);
        }

        return null;
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain)
    {
        FluidStack fluidStack = tank.drain(maxDrain, doDrain);

        if (fluidStack != null && doDrain)
            world.markBlockRangeForRenderUpdate(pos, pos);

        return fluidStack;
    }

    @Override
    public IFluidTankProperties[] getTankProperties()
    {
        FluidStack fluidStack = tank.getFluid();
        if (fluidStack != null)
        {
            return new IFluidTankProperties[]
                    {
                            new FluidTankProperties(fluidStack.copy(), tank.getCapacity())
                    };
        }
        return new IFluidTankProperties[] { null };
    }

    // Благодаря методам ниже - количество жидкости при перезаходе в мир или на сервер будет сохранено
    @Override
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager networkManager, SPacketUpdateTileEntity packet)
    {
        super.onDataPacket(networkManager, packet);
        this.handleUpdateTag(packet.getNbtCompound());
    }
}