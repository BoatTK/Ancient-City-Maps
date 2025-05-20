package net.boattk.ancientmaps;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.block.MapColor;
import net.minecraft.item.map.MapDecorationType;
import net.minecraft.item.map.MapDecorationTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.StructureTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.gen.structure.Structure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AncientCityMaps implements ModInitializer {
	public static final String MOD_ID = "ancient-city-maps";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		RegistryKey<MapDecorationType> registryKey = RegistryKey.of(RegistryKeys.MAP_DECORATION_TYPE, Identifier.ofVanilla("ancient_city"));
		MapDecorationType mapDecorationType = new MapDecorationType(Identifier.ofVanilla("ancient_city"), true, 3827290, true, false);
		RegistryEntry<MapDecorationType> ANCIENT_CITY =  Registry.registerReference(Registries.MAP_DECORATION_TYPE, registryKey, mapDecorationType);

		TradeOfferHelper.registerVillagerOffers(VillagerProfession.CARTOGRAPHER, 5, factories -> {
			factories.add(
						new TradeOffers.SellMapFactory(16, (TagKey<Structure>)TagKey.of(RegistryKeys.STRUCTURE, Identifier.ofVanilla("on_ancient_city_maps")), "filled_map.ancient_city", ANCIENT_CITY, 12, 10)
			);
		});
		LOGGER.info("Ancient City Maps Successfully Loaded");
	}
}