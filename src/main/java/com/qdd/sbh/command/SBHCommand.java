package com.qdd.sbh.command;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import com.mojang.brigadier.CommandDispatcher;
import com.qdd.sbh.Config;
import com.qdd.sbh.cap.SBHCapProvider;

public class SBHCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("sbh")
                        .requires(source -> source.hasPermission(Config.op)) // 需要OP权限
                        .then(Commands.literal("sethome")
                                .executes(ctx -> {
                                    if (ctx.getSource().getEntity() instanceof ServerPlayer  player) {
                                        player.getCapability(SBHCapProvider.PLAYER_DATA).ifPresent(data -> {
                                            data.setHome(player.position());
                                            player.sendSystemMessage(ctx.getSource().getDisplayName().copy().append("已设置家").append(player.position().toString()));
                                        });
                                    }
                                    return 1;
                                }))
                        .then(Commands.literal("home")
                                .executes(ctx -> {
                                    if (ctx.getSource().getEntity() instanceof ServerPlayer  player) {
                                        player.getCapability(SBHCapProvider.PLAYER_DATA).ifPresent(data -> {
                                            if (data.getHome() != Vec3.ZERO) {
                                                data.setback(player.position());
                                                player.teleportTo(data.getHome().x, data.getHome().y, data.getHome().z);
                                            }
                                        });
                                    }
                                    return 1;
                                }))
                        .then(Commands.literal("back")
                                .executes(ctx -> {
                                    if (ctx.getSource().getEntity() instanceof ServerPlayer  player) {
                                        player.getCapability(SBHCapProvider.PLAYER_DATA).ifPresent(data -> {
                                            if (data.getback() != Vec3.ZERO) {
                                                player.teleportTo(data.getback().x, data.getback().y, data.getback().z);
                                            }
                                        });
                                    }
                                    return 1;
                                }))

        );
    }
}