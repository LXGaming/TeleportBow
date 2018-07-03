/*
 * Copyright 2018 Alex Thomson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.lxgaming.teleportbow.util;

import org.apache.commons.lang3.StringUtils;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.enchantment.EnchantmentTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextAction;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Toolbox {
    
    public static Text getTextPrefix() {
        Text.Builder textBuilder = Text.builder();
        textBuilder.onHover(TextActions.showText(getPluginInformation()));
        textBuilder.append(Text.of(TextColors.BLUE, TextStyles.BOLD, "[", Reference.PLUGIN_NAME, "]"));
        return Text.of(textBuilder.build(), TextStyles.RESET, " ");
    }
    
    public static Text getPluginInformation() {
        Text.Builder textBuilder = Text.builder();
        textBuilder.append(Text.of(TextColors.BLUE, TextStyles.BOLD, Reference.PLUGIN_NAME, Text.NEW_LINE));
        textBuilder.append(Text.of("    ", TextColors.DARK_GRAY, "Version: ", TextColors.WHITE, Reference.PLUGIN_VERSION, Text.NEW_LINE));
        textBuilder.append(Text.of("    ", TextColors.DARK_GRAY, "Authors: ", TextColors.WHITE, Reference.AUTHORS, Text.NEW_LINE));
        textBuilder.append(Text.of("    ", TextColors.DARK_GRAY, "Source: ", TextColors.BLUE, getURLTextAction(Reference.SOURCE), Reference.SOURCE, Text.NEW_LINE));
        textBuilder.append(Text.of("    ", TextColors.DARK_GRAY, "Website: ", TextColors.BLUE, getURLTextAction(Reference.WEBSITE), Reference.WEBSITE));
        return textBuilder.build();
    }
    
    public static TextAction<?> getURLTextAction(String url) {
        try {
            return TextActions.openUrl(new URL(url));
        } catch (MalformedURLException ex) {
            return TextActions.suggestCommand(url);
        }
    }
    
    public static boolean isValidBow(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() != ItemTypes.BOW) {
            return false;
        }
        
        Optional<List<Enchantment>> enchantments = itemStack.get(Keys.ITEM_ENCHANTMENTS);
        if (!enchantments.isPresent() || enchantments.get().isEmpty()) {
            return false;
        }
        
        for (Enchantment enchantment : enchantments.get()) {
            if (enchantment.getType() == EnchantmentTypes.INFINITY && enchantment.getLevel() == 10) {
                return true;
            }
        }
        
        return false;
    }
    
    public static ItemStack createArrow() {
        ItemStack itemStack = ItemStack.of(ItemTypes.ARROW, 1);
        itemStack.offer(Keys.ITEM_LORE, Toolbox.newArrayList(Text.of(TextColors.GOLD, TextStyles.BOLD, "Don't lose it")));
        itemStack.offer(Keys.DISPLAY_NAME, Text.of(TextColors.GREEN, TextStyles.BOLD, "TeleportFuel"));
        return itemStack;
    }
    
    public static ItemStack createBow() {
        ItemStack itemStack = ItemStack.of(ItemTypes.BOW, 1);
        itemStack.offer(Keys.UNBREAKABLE, true);
        itemStack.offer(Keys.ITEM_LORE, Toolbox.newArrayList(Text.of(TextColors.GOLD, TextStyles.BOLD, "Fire the bow to teleport")));
        itemStack.offer(Keys.ITEM_ENCHANTMENTS, Toolbox.newArrayList(Enchantment.of(EnchantmentTypes.INFINITY, 10)));
        itemStack.offer(Keys.HIDE_UNBREAKABLE, true);
        itemStack.offer(Keys.HIDE_ENCHANTMENTS, true);
        itemStack.offer(Keys.HIDE_ATTRIBUTES, true);
        itemStack.offer(Keys.DISPLAY_NAME, Text.of(TextColors.GREEN, TextStyles.BOLD, "TeleportBow"));
        return itemStack;
    }
    
    /**
     * Removes non-printable characters (excluding new line and carriage return) in the provided {@link java.lang.String String}.
     *
     * @param string The {@link java.lang.String String} to filter.
     * @return The filtered {@link java.lang.String String}.
     */
    public static String filter(String string) {
        return StringUtils.replaceAll(string, "[^\\x20-\\x7E\\x0A\\x0D]", "");
    }
    
    public static boolean containsIgnoreCase(Collection<String> list, String targetString) {
        if (list == null || list.isEmpty()) {
            return false;
        }
        
        for (String string : list) {
            if (StringUtils.equalsIgnoreCase(string, targetString)) {
                return true;
            }
        }
        
        return false;
    }
    
    public static <T> Optional<T> newInstance(Class<? extends T> typeOfT) {
        try {
            return Optional.of(typeOfT.newInstance());
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
    
    @SafeVarargs
    public static <E> ArrayList<E> newArrayList(E... elements) {
        return Stream.of(elements).collect(Collectors.toCollection(ArrayList::new));
    }
    
    @SafeVarargs
    public static <E> HashSet<E> newHashSet(E... elements) {
        return Stream.of(elements).collect(Collectors.toCollection(HashSet::new));
    }
    
    @SafeVarargs
    public static <E> LinkedBlockingQueue<E> newLinkedBlockingQueue(E... elements) {
        return Stream.of(elements).collect(Collectors.toCollection(LinkedBlockingQueue::new));
    }
    
    @SafeVarargs
    public static <E> LinkedHashSet<E> newLinkedHashSet(E... elements) {
        return Stream.of(elements).collect(Collectors.toCollection(LinkedHashSet::new));
    }
}