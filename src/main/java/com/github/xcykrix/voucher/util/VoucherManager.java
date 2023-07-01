package com.github.xcykrix.voucher.util;

import com.github.xcykrix.plugincommon.PluginCommon;
import com.github.xcykrix.plugincommon.extendables.Stateful;
import com.shaded._100.org.h2.mvstore.MVMap;

public class VoucherManager extends Stateful {

    // Local Database Map
    public final MVMap<String, String> index;

    public VoucherManager(PluginCommon pluginCommon) {
        super(pluginCommon);
        this.index = this.pluginCommon.h2MVStoreAPI.getStore().openMap("index");
    }
}
