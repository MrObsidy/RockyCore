package mrobsidy.rockycore.example;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ExampleCreativeTab extends CreativeTabs {

	public ExampleCreativeTab(String label) {
		super(label);
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(new ExampleBlockConsumer(Material.GRASS));
	}

}
