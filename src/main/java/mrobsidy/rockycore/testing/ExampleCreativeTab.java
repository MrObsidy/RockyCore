package mrobsidy.rockycore.testing;

import mrobsidy.rockycore.init.RockyCore;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class ExampleCreativeTab extends CreativeTabs {

	public ExampleCreativeTab(String lable) {
		super(lable);
	}

	@Override
	public Item getTabIconItem() {
		return new ItemBlock(RockyCore.mnb);
	}
}
