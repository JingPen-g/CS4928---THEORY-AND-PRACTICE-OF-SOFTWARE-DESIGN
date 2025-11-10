package com.cafepos;

import com.cafepos.common.Money;
import com.cafepos.menu.Menu;
import com.cafepos.menu.MenuComponent;
import com.cafepos.menu.MenuItem;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CompositeIteratorTests {
    @Test
    void depth_first_iteration_collects_all_nodes() {
        Menu root = new Menu("All menu");
        Menu a = new Menu("Breakfast menu");
        Menu b = new Menu("Lunch menu");
        root.add(a); root.add(b);
        a.add(new MenuItem("bacon", Money.of(2.0), false));
        b.add(new MenuItem("spaghetti", Money.of(4.5), false));
        b.add(new MenuItem("salad", Money.of(4.0), true));
        List<String> names =
                root.allItems().stream().map(MenuComponent::name).toList();
        // depth-first traversal order
        List<String> expected = List.of("Breakfast menu", "bacon", "Lunch menu", "spaghetti", "salad");
        assertTrue(names.contains("bacon"));
        assertTrue(names.contains("spaghetti"));
        assertEquals(expected, names);
    }
}
