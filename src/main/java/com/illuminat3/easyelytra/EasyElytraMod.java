package com.illuminat3.easyelytra;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import com.illuminat3.easyelytra.events.ElytraControlHandler;

public class EasyElytraMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(ElytraControlHandler::onTick);
    }
}
