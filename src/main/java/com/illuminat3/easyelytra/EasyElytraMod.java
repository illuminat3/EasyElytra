package com.illuminat3.easyelytra;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import com.illuminat3.easyelytra.events.ElytraControlHandler;

public class EasyElytraMod implements ModInitializer {
    @Override
    public void onInitialize() {
        ClientTickEvents.END_CLIENT_TICK.register(ElytraControlHandler::onTick);
    }
}
