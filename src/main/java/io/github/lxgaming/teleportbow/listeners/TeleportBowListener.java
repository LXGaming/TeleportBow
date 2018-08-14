/*
 * Copyright 2018 Alex Thomson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.lxgaming.teleportbow.listeners;

import com.flowpowered.math.vector.Vector3d;
import io.github.lxgaming.teleportbow.TeleportBow;
import io.github.lxgaming.teleportbow.configuration.Config;
import io.github.lxgaming.teleportbow.util.Toolbox;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.projectile.arrow.Arrow;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.action.CollideEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.world.World;

import java.util.Optional;

public class TeleportBowListener {
    
    @Listener
    public void onCollideImpact(CollideEvent.Impact event, @Root Arrow arrow) {
        if (event.isCancelled() || !(arrow.getShooter() instanceof Player)) {
            return;
        }
        
        Player player = (Player) arrow.getShooter();
        if (!player.hasPermission("teleportbow.use")) {
            return;
        }
        
        if (Toolbox.isValidBow(player.getItemInHand(HandTypes.MAIN_HAND).orElse(null)) || Toolbox.isValidBow(player.getItemInHand(HandTypes.OFF_HAND).orElse(null))) {
            arrow.remove();
            
            // https://github.com/NucleusPowered/Nucleus/blob/c55d92191741214b09a6952ca853723c27f640d0/src/main/java/io/github/nucleuspowered/nucleus/Util.java#L393
            World world = event.getImpactPoint().getExtent();
            long radius = (long) Math.floor(world.getWorldBorder().getDiameter() / 2.0);
            Vector3d displacement = event.getImpactPoint().getPosition().sub(world.getWorldBorder().getCenter()).abs();
            if (displacement.getX() > radius || displacement.getZ() > radius) {
                return;
            }
            
            player.setLocation(event.getImpactPoint());
            
            Optional<SoundType> soundType = TeleportBow.getInstance().getConfig().map(Config::getSound).flatMap(sound -> Sponge.getRegistry().getType(SoundType.class, sound));
            if (soundType.isPresent()) {
                player.playSound(soundType.get(), player.getLocation().getPosition(), 1.0F, 1.0F);
            } else {
                TeleportBow.getInstance().debugMessage("Failed to get SoundType {}", TeleportBow.getInstance().getConfig().map(Config::getSound).orElse("Unknown"));
            }
        }
    }
}