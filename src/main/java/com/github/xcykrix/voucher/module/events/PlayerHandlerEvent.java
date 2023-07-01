package com.github.xcykrix.voucher.module.events;

import com.github.xcykrix.plugincommon.PluginCommon;
import com.github.xcykrix.plugincommon.api.helper.configuration.LanguageFile;
import com.github.xcykrix.plugincommon.api.records.PlaceholderPair;
import com.github.xcykrix.plugincommon.extendables.Stateful;
import com.github.xcykrix.voucher.util.VoucherManager;
import com.shaded._100.dev.dejvokep.boostedyaml.YamlDocument;
import com.shaded._100.net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;

import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.permissions.Permission;


public class PlayerHandlerEvent extends Stateful implements Listener {
    private final VoucherManager voucherManager;
    private final YamlDocument configuration;
    private final LanguageFile languageFile;

    private final Permission updateWorldBypass = new Permission(
            "voucher.bypass"
    );

    public PlayerHandlerEvent(PluginCommon pluginCommon, VoucherManager voucherManager) {
        super(pluginCommon);
        this.voucherManager = voucherManager;
        this.configuration = this.pluginCommon.configurationAPI.get("config.yml");
        this.languageFile = this.pluginCommon.configurationAPI.getLanguageFile();
    }

    private boolean checkEnabled(String id) {
        return this.configuration.getOptionalBoolean(id).orElse(true);
    }

    private void preventUpdateWorld(Entity entity, Cancellable event, boolean alert) {
        if (!(entity instanceof Player)) return;
        if (entity.hasPermission(this.updateWorldBypass)) return;
        if (!this.voucherManager.index.containsKey(entity.getUniqueId().toString())) {
            if (alert) {
                this.pluginCommon.adventureAPI.getAudiences().player((Player) entity).sendMessage(
                        this.languageFile.getComponentFromID(
                                "reject-interaction",
                                true,
                                new PlaceholderPair("target", Component.text(entity.getName()))
                        )
                );
            }
            event.setCancelled(true);
        }
    }

    /* Player */
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(this.checkEnabled("prevent-interact")) {
            preventUpdateWorld(event.getPlayer(), event, event.getHand() != EquipmentSlot.OFF_HAND || event.getMaterial() != Material.AIR);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if(this.checkEnabled("prevent-entity-interact")) {
            preventUpdateWorld(event.getPlayer(), event, true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
        if(this.checkEnabled("prevent-armor-stand-interact")) {
            preventUpdateWorld(event.getPlayer(), event, true);
        }
    }

    /* Blocks */
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        if(this.checkEnabled("prevent-block-place")) {
            preventUpdateWorld(event.getPlayer(), event, true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        if(this.checkEnabled("prevent-block-break")) {
            preventUpdateWorld(event.getPlayer(), event, true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityPickupEvent(EntityPickupItemEvent event) {
        if(this.checkEnabled("prevent-item-pickup")) {
            preventUpdateWorld(event.getEntity(), event, true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if(this.checkEnabled("prevent-item-drop")) {
            preventUpdateWorld(event.getPlayer(), event, true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        if(this.checkEnabled("prevent-damage")) {
            if (event instanceof EntityDamageByEntityEvent damageEvent) {
                preventUpdateWorld(damageEvent.getDamager(), event, true);
                preventUpdateWorld(damageEvent.getEntity(), event, false);
            } else {
                preventUpdateWorld(event.getEntity(), event, true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onFoodLevelChangeEvent(FoodLevelChangeEvent event) {
        if(this.checkEnabled("prevent-hunger")) {
            preventUpdateWorld(event.getEntity(), event, false);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerEditBook(PlayerEditBookEvent event) {
        if (this.checkEnabled("prevent-edit-book")) {
            preventUpdateWorld(event.getPlayer(), event, true);
        }
    }

    /* Hanging */
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onHangingPlace(HangingPlaceEvent event) {
        if(this.checkEnabled("prevent-hanging-place")) {
            preventUpdateWorld(event.getPlayer(), event, true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onHangingBreakByEntity(HangingBreakByEntityEvent event) {
        if(this.checkEnabled("prevent-hanging-break")) {
            preventUpdateWorld(event.getRemover(), event, true);
        }
    }
}
