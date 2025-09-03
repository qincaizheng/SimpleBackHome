package com.qdd.sbh;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = SBH.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();



    private static final ForgeConfigSpec.IntValue OP = BUILDER
            .comment("command op (0~4)")
            .defineInRange("op", 0, 0, 4);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int op;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        op= OP.get();
    }
}
