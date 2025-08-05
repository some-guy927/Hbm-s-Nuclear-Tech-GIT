package com.hbm.handler;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = RefStrings.MODID)
public class MissingMappingsHandler {
    @SubscribeEvent
    public static void onMissingItemMappings(RegistryEvent.MissingMappings<Item> event) {
        for (RegistryEvent.MissingMappings.Mapping<Item> mapping : event.getMappings()) {
            if (mapping.key.getNamespace().equals("hbm")) {
                String path = mapping.key.getPath();
                if (path.startsWith("wire_")) {
                    mapping.remap(ModItems.wire);
                } else if (path.startsWith("bolt_")) {
                    mapping.remap(ModItems.bolt);
                }
            }
        }
    }
}

