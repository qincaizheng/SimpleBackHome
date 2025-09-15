package com.qdd.sbh.event;

import com.qdd.sbh.cap.SBHData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import com.qdd.sbh.SBH;
import com.qdd.sbh.cap.SBHCapProvider;
import com.qdd.sbh.command.SBHCommand;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SBH.MODID)
public class SBHEvent {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        SBHCommand.register(event.getDispatcher());
    }
    @SubscribeEvent
    public static void entityAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(new ResourceLocation(SBH.MODID,"sbh"),new SBHCapProvider());
        }
    }
    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(SBHData.class);
    }
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            // 玩家死亡时保留数据
            event.getOriginal().reviveCaps();
            event.getOriginal().getCapability(SBHCapProvider.PLAYER_DATA).ifPresent(oldData -> {
                event.getEntity().getCapability(SBHCapProvider.PLAYER_DATA).ifPresent(newData -> {
                    newData.deserializeNBT(oldData.serializeNBT());
                });
            });
            event.getOriginal().invalidateCaps();
        }
    }
    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        // 玩家退出时保存数据
        if (event.getEntity() instanceof Player player) {
            player.getCapability(SBHCapProvider.PLAYER_DATA).ifPresent(data -> {
                data.setback(player.position());
            });
        }
    }
}
