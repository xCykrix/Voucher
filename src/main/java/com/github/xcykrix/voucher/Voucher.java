package com.github.xcykrix.voucher;

import com.github.xcykrix.plugincommon.PluginCommon;
import com.github.xcykrix.plugincommon.api.records.Resource;
import com.github.xcykrix.voucher.module.VoucherCommand;
import com.github.xcykrix.voucher.module.events.PlayerHandlerEvent;
import com.github.xcykrix.voucher.util.VoucherManager;

public final class Voucher extends PluginCommon {
    @Override
    protected void initialize() {
        // Register Configuration
        this.configurationAPI
                .register(new Resource("config.yml", null, this.getResource("config.yml")))
                .registerLanguageFile(this.getResource("language.yml"));

        // Initialize Voucher Manager
        VoucherManager voucherManager = new VoucherManager(this);

        // Register Commands
        this.commandAPI.register(new VoucherCommand(this, voucherManager));

        // Register Events
        this.getServer().getPluginManager().registerEvents(new PlayerHandlerEvent(this, voucherManager), this);
    }

    @Override
    public void shutdown() {
    }
}
