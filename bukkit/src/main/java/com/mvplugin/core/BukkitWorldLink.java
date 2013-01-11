package com.mvplugin.core;

import com.dumptruckman.minecraft.pluginbase.entity.BasePlayer;
import com.dumptruckman.minecraft.pluginbase.minecraft.location.FacingCoordinates;
import com.dumptruckman.minecraft.pluginbase.util.BukkitTools;
import com.mvplugin.core.minecraft.Difficulty;
import com.mvplugin.core.minecraft.WorldType;
import com.mvplugin.core.util.Convert;
import com.mvplugin.core.world.WorldProperties;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

class BukkitWorldLink implements WorldLink {

    @NotNull
    private final WeakReference<World> worldRef;
    @NotNull
    private final WorldType worldType;

    BukkitWorldLink(@NotNull final World world) {
        this.worldRef = new WeakReference<World>(world);
        this.worldType = Convert.fromBukkit(world.getWorldType());
    }

    @Override
    public void setSpawnLocation(@NotNull final FacingCoordinates pos) {
        getWorld().setSpawnLocation((int) pos.getX(), (int) pos.getY(), (int) pos.getZ());
    }

    @Override
    public void setPVP(final boolean enablePVP) {
        getWorld().setPVP(enablePVP);
    }

    @Override
    public void setDifficulty(@NotNull final Difficulty difficulty) {
        getWorld().setDifficulty(Convert.toBukkit(difficulty));
    }

    @Override
    public void setEnableWeather(final boolean enableWeather) {
        if (!enableWeather) {
            final World world = getWorld();
            world.setWeatherDuration(0);
            world.setStorm(false);
            world.setThunderDuration(0);
            world.setThundering(false);
        }
    }

    @NotNull
    private World getWorld() {
        return this.worldRef.get();
    }

    @Override
    @NotNull
    public UUID getUID() {
        return getWorld().getUID();
    }

    @Override
    @NotNull
    public String getName() {
        return getWorld().getName();
    }

    @Override
    @NotNull
    public WorldType getType() {
        return this.worldType;
    }

    @Override
    @NotNull
    public Collection<BasePlayer> getPlayers() {
        final List<Player> players = getWorld().getPlayers();
        final List<BasePlayer> result = new ArrayList<BasePlayer>(players.size());
        for (final Player player : players) {
            result.add(BukkitTools.wrapPlayer(player));
        }
        return result;
    }
}