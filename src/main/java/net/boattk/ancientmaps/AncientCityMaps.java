package net.boattk.ancientmaps;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.StructureTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import net.minecraft.world.level.saveddata.maps.MapDecorationTypes;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.registries.*;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import java.util.List;
import java.util.function.Supplier;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(AncientCityMaps.MODID)
public class AncientCityMaps {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "ancientcitymaps";
    public static final Logger LOGGER = LogUtils.getLogger();
    private static final DeferredRegister<MapDecorationType> MAP_DECORATIONS = DeferredRegister.create(Registries.MAP_DECORATION_TYPE, MODID);

    public static final Supplier<MapDecorationType> ANCIENT_CITY = MAP_DECORATIONS.register("ancient_city",
            () -> new MapDecorationType(
                    ResourceLocation.withDefaultNamespace("ancient_city"),
                    true,
                    3827290,
                    false,
                    true
            )
    );

    public AncientCityMaps(IEventBus modEventBus, ModContainer modContainer) {
        MAP_DECORATIONS.register(modEventBus);
        NeoForge.EVENT_BUS.register(AncientCityMaps.class);
    }

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if(event.getType() == VillagerProfession.CARTOGRAPHER) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            MapDecorationType ancientCityType = ANCIENT_CITY.get();
            Holder<MapDecorationType> ancientCityHolder = BuiltInRegistries.MAP_DECORATION_TYPE.wrapAsHolder(ancientCityType);

            trades.get(5).add(new VillagerTrades.TreasureMapForEmeralds(
                    16,
                    TagKey.create(Registries.STRUCTURE, ResourceLocation.withDefaultNamespace("on_ancient_city_maps")),
                    "filled_map.ancient_city",
                    ancientCityHolder,
                    12,
                    10
            ));
        }
    }
}
