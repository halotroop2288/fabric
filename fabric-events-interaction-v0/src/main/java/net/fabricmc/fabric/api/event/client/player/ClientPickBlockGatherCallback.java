/*
 * Copyright (c) 2020 Legacy Fabric
 * Copyright (c) 2016 - 2020 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.fabric.api.event.client.player;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * This event is emitted at the beginning of the block picking process in
 * order to find any applicable ItemStack. The first non-empty ItemStack
 * will be returned, overriding vanilla behaviour.
 */
public interface ClientPickBlockGatherCallback {
	Event<ClientPickBlockGatherCallback> EVENT = EventFactory.createArrayBacked(ClientPickBlockGatherCallback.class,
			(listeners) -> (player, result) -> {
				for (ClientPickBlockGatherCallback event : listeners) {
					ItemStack stack = event.pick(player, result);

					if (!stack.equals(new ItemStack(Blocks.AIR)) && !stack.isEmpty()) {
						return stack;
					}
				}

				return new ItemStack(Blocks.AIR);
			}
	);

	ItemStack pick(PlayerEntity player, HitResult result);
}
