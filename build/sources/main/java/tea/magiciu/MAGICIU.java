package tea.magiciu;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tea.magiciu.init.BlocksRegister;
import tea.magiciu.init.FluidRegister;
import tea.magiciu.misc.MagicIU_Tab;
import tea.magiciu.proxy.CommonProxy;
import tea.magiciu.utils.handlers.RegisterHandler;




@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_MINECRAFT_VERSION)


public class MAGICIU
{
    @Mod.Instance
    public static MAGICIU instance;

    @SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.COMMON)
    public static CommonProxy proxy;

    //CreativeTabs

    public static final CreativeTabs MAGICIU_TAB = new MagicIU_Tab("magiciu_tab");

    static {
        if (!FluidRegistry.isUniversalBucketEnabled()) FluidRegistry.enableUniversalBucket();
    }
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {


        // Преинициализация
        System.out.println("\u001B[32m" + "[Starting The_Magic_of_The_Infinite_Universe PRE-INITIALIZATION]" + "\u001B[0m");
        proxy.preInit(event);


    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {

        System.out.println("\u001B[32m" + "[Starting The_Magic_of_The_Infinite_Universe INITIALIZATION]" + "\u001B[0m");
        proxy.init(event);
        RegisterHandler.otherRegister();

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        // Постинициализация
        System.out.println("\u001B[32m" + "[Starting The_Magic_of_The_Infinite_Universe POST-INITIALIZATION]" + "\u001B[0m");
        proxy.postInit(event);
    }
}
