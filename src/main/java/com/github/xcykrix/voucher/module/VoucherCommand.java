package com.github.xcykrix.voucher.module;

import com.github.xcykrix.plugincommon.PluginCommon;
import com.github.xcykrix.plugincommon.api.helper.configuration.LanguageFile;
import com.github.xcykrix.plugincommon.api.records.PlaceholderPair;
import com.github.xcykrix.voucher.util.VoucherManager;
import com.shaded._100.aikar.commands.BaseCommand;
import com.shaded._100.aikar.commands.CommandHelp;
import com.shaded._100.aikar.commands.annotation.*;
import com.shaded._100.net.kyori.adventure.text.Component;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("voucher|vouching|vouch")
public class VoucherCommand extends BaseCommand {
    private final PluginCommon pluginCommon;
    private final VoucherManager voucherManager;
    private final LanguageFile languageFile;

    public VoucherCommand(PluginCommon pluginCommon, VoucherManager voucherManager) {
        this.pluginCommon = pluginCommon;
        this.voucherManager = voucherManager;
        this.languageFile = this.pluginCommon.configurationAPI.getLanguageFile();
    }

    @Subcommand("add|grant")
    @CommandPermission("voucher.add")
    @Description("Grant a player access to the world.")
    public void add(CommandSender sender, OfflinePlayer player) {
        String name = player.getName();
        if (name == null) name = player.getUniqueId().toString();
        PlaceholderPair target = new PlaceholderPair("target", Component.text(name));

        if (this.voucherManager.index.containsKey(player.getUniqueId().toString())) {
            this.pluginCommon.adventureAPI.getAudiences().sender(sender).sendMessage(
                    this.languageFile.getComponentFromID(
                            "add-exists",
                            true,
                            target
                    )
            );
            return;
        }

        String uid = (sender instanceof Player ? ((Player) sender).getUniqueId().toString() : "CONSOLE");
        this.voucherManager.index.put(player.getUniqueId().toString(), uid);
        this.pluginCommon.adventureAPI.getAudiences().sender(sender).sendMessage(
                this.languageFile.getComponentFromID(
                        "add",
                        true,
                        target
                )
        );
    }

    @Subcommand("remove|revoke")
    @CommandPermission("voucher.remove")
    @Description("Revoke a player's access to the world.")
    public void remove(CommandSender sender, OfflinePlayer player) {
        String name = player.getName();
        if (name == null) name = player.getUniqueId().toString();
        PlaceholderPair target = new PlaceholderPair("target", Component.text(name));

        if (!this.voucherManager.index.containsKey(player.getUniqueId().toString())) {
            this.pluginCommon.adventureAPI.getAudiences().sender(sender).sendMessage(
                    this.languageFile.getComponentFromID(
                            "remove-unknown",
                            true,
                            target
                    )
            );
            return;
        }

        this.voucherManager.index.remove(player.getUniqueId().toString());
        this.pluginCommon.adventureAPI.getAudiences().sender(sender).sendMessage(
                this.languageFile.getComponentFromID(
                        "remove",
                        true,
                        target
                )
        );
    }

    @HelpCommand
    public void help(CommandSender sender, CommandHelp commandHelp) {
        commandHelp.showHelp();
    }
}
