package com.mvplugin.core.command;

import com.dumptruckman.minecraft.pluginbase.entity.BasePlayer;
import com.dumptruckman.minecraft.pluginbase.messaging.Message;
import com.dumptruckman.minecraft.pluginbase.permission.Perm;
import com.dumptruckman.minecraft.pluginbase.plugin.command.CommandInfo;
import com.mvplugin.core.api.MultiverseCore;
import com.mvplugin.core.api.Perms;
import com.sk89q.minecraft.util.commands.CommandContext;
import org.jetbrains.annotations.NotNull;

@CommandInfo(
        primaryAlias = "unload",
        desc = "Unloads a world managed by Multiverse.",
        usage = "{NAME}",
        directlyPrefixedAliases = "unload",
        min = 1,
        max = 1
)
public class UnloadCommand extends MultiverseCommand {

    public static final Message UNLOAD_HELP = new Message("command.unload.help",
            "Unloads a world that has previously been imported and is currently loaded."
            + "\nThis will remove the world from memory but not delete anything."
            + "\nExamples:"
            + "\n  /mv unload &6gargamel&a");

    @Override
    public Perm getPerm() {
        return Perms.CMD_UNLOAD;
    }

    @NotNull
    @Override
    public Message getHelp() {
        return UNLOAD_HELP;
    }

    @Override
    public boolean runCommand(@NotNull MultiverseCore core, @NotNull BasePlayer sender, @NotNull CommandContext context) {
        final String worldName = context.getString(0);
        core.getWorldManager().unloadWorld(worldName);
        return true;
    }
}
