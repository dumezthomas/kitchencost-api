package be.technifutur.kitchencostapi.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;
import be.technifutur.kitchencostapi.daos.*;
import be.technifutur.kitchencostapi.enums.*;
import be.technifutur.kitchencostapi.pojos.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class DataInitializer {

    @Inject
    private RoleDao roleDao;

    @Inject
    private UserDao userDao;

    @Inject
    private IngredientDao ingredientDao;

    @Inject
    private RecipeDao recipeDao;

    @Inject
    private MenuItemDao menuItemDao;

    public void initDB(@Observes @Initialized(ApplicationScoped.class) Object init) {

        initRoles();
        initUsers();
        initIngredients();
        initRecipes();
        initMenuItems();
    }

    private void initRoles() {

        if (roleDao.count() == 0) {

            roleDao.saveAll(List.of(
                    new Role("CHEF"),
                    new Role("COOK")));

            System.out.println("Roles initialized");
        }
    }

    private void initUsers() {

        if (userDao.count() == 0) {

            Role chef = roleDao.findByName("CHEF")
                    .orElseThrow(() -> new IllegalStateException("CHEF role not found"));

            Role cook = roleDao.findByName("COOK")
                    .orElseThrow(() -> new IllegalStateException("COOK role not found"));

            var bcrypt = BCrypt.withDefaults();

            List<User> users = List.of(
                    new User(
                            "etchebest@kc.com",
                            "etchebest",
                            bcrypt.hashToString(12, "chef123".toCharArray()),
                            chef),
                    new User(
                            "remy@kc.com",
                            "remy",
                            bcrypt.hashToString(12, "rat123".toCharArray()),
                            cook),
                    new User(
                            "lignac@kc.com",
                            "lignac",
                            bcrypt.hashToString(12, "cook123".toCharArray()),
                            cook)
            );

            userDao.saveAll(users);

            System.out.println("Users initialized");
        }
    }

    private void initIngredients() {

        if (ingredientDao.count() == 0) {

            Ingredient farine = new Ingredient("Farine", Unit.GRAM, new BigDecimal("0.002"), IngredientOrigin.PLANT);
            farine.addAllergen(Allergen.GLUTEN);

            Ingredient lait = new Ingredient("Lait", Unit.MILLILITER, new BigDecimal("0.001"), IngredientOrigin.DAIRY);
            lait.addAllergen(Allergen.MILK);

            Ingredient oeuf = new Ingredient("Œuf", Unit.PIECE, new BigDecimal("0.30"), IngredientOrigin.EGG);
            oeuf.addAllergen(Allergen.EGGS);

            Ingredient sucre = new Ingredient("Sucre", Unit.GRAM, new BigDecimal("0.001"), IngredientOrigin.PLANT);

            Ingredient beurre = new Ingredient("Beurre", Unit.GRAM, new BigDecimal("0.01"), IngredientOrigin.DAIRY);
            beurre.addAllergen(Allergen.MILK);

            Ingredient steak = new Ingredient("Steak haché", Unit.GRAM, new BigDecimal("0.02"), IngredientOrigin.MEAT);

            Ingredient salade = new Ingredient("Salade", Unit.GRAM, new BigDecimal("0.003"), IngredientOrigin.PLANT);

            Ingredient tomate = new Ingredient("Tomate", Unit.GRAM, new BigDecimal("0.004"), IngredientOrigin.PLANT);

            Ingredient miel = new Ingredient("Miel", Unit.GRAM, new BigDecimal("0.02"), IngredientOrigin.HONEY);

            Ingredient painBurger = new Ingredient("Pain burger", Unit.PIECE, new BigDecimal("0.80"), IngredientOrigin.PLANT);
            painBurger.addAllergen(Allergen.GLUTEN);

            Ingredient fromage = new Ingredient("Fromage", Unit.GRAM, new BigDecimal("0.015"), IngredientOrigin.DAIRY);
            fromage.addAllergen(Allergen.MILK);

            Ingredient poulet = new Ingredient("Poulet", Unit.GRAM, new BigDecimal("0.018"), IngredientOrigin.MEAT);

            Ingredient pommeDeTerre = new Ingredient("Pomme de terre", Unit.GRAM, new BigDecimal("0.002"), IngredientOrigin.PLANT);

            Ingredient huile = new Ingredient("Huile", Unit.MILLILITER, new BigDecimal("0.005"), IngredientOrigin.PLANT);

            Ingredient chocolat = new Ingredient("Chocolat", Unit.GRAM, new BigDecimal("0.02"), IngredientOrigin.PLANT);

            Ingredient farineAmande = new Ingredient("Farine d'amande", Unit.GRAM, new BigDecimal("0.03"), IngredientOrigin.PLANT);
            farineAmande.addAllergen(Allergen.NUTS);

            Ingredient banane = new Ingredient("Banane", Unit.GRAM, new BigDecimal("0.003"), IngredientOrigin.PLANT);

            Ingredient laitAmande = new Ingredient("Lait d'amande", Unit.MILLILITER, new BigDecimal("0.004"), IngredientOrigin.PLANT);
            laitAmande.addAllergen(Allergen.NUTS);

            Ingredient tofu = new Ingredient("Tofu", Unit.GRAM, new BigDecimal("0.01"), IngredientOrigin.PLANT);
            tofu.addAllergen(Allergen.SOY);

            ingredientDao.saveAll(List.of(
                    farine, lait, oeuf, sucre, beurre,
                    steak, salade, tomate, miel, painBurger,
                    fromage, poulet, pommeDeTerre, huile, chocolat,
                    farineAmande, banane, laitAmande, tofu));

            System.out.println("Ingredients initialized");
        }
    }

    private void initRecipes() {

        if (recipeDao.count() == 0) {

            Ingredient farine = ingredientDao.findByName("Farine").orElseThrow();
            Ingredient lait = ingredientDao.findByName("Lait").orElseThrow();
            Ingredient oeuf = ingredientDao.findByName("Œuf").orElseThrow();
            Ingredient sucre = ingredientDao.findByName("Sucre").orElseThrow();
            Ingredient beurre = ingredientDao.findByName("Beurre").orElseThrow();
            Ingredient steak = ingredientDao.findByName("Steak haché").orElseThrow();
            Ingredient salade = ingredientDao.findByName("Salade").orElseThrow();
            Ingredient tomate = ingredientDao.findByName("Tomate").orElseThrow();
            Ingredient miel = ingredientDao.findByName("Miel").orElseThrow();
            Ingredient painBurger = ingredientDao.findByName("Pain burger").orElseThrow();
            Ingredient fromage = ingredientDao.findByName("Fromage").orElseThrow();
            Ingredient poulet = ingredientDao.findByName("Poulet").orElseThrow();
            Ingredient pommeDeTerre = ingredientDao.findByName("Pomme de terre").orElseThrow();
            Ingredient huile = ingredientDao.findByName("Huile").orElseThrow();
            Ingredient chocolat = ingredientDao.findByName("Chocolat").orElseThrow();
            Ingredient farineAmande = ingredientDao.findByName("Farine d'amande").orElseThrow();
            Ingredient banane = ingredientDao.findByName("Banane").orElseThrow();
            Ingredient laitAmande = ingredientDao.findByName("Lait").orElseThrow();
            Ingredient tofu = ingredientDao.findByName("Tofu").orElseThrow();

            Recipe burger = new Recipe("Burger maison", "Assembler et cuire");
            burger.addIngredient(painBurger, new BigDecimal("1"));
            burger.addIngredient(steak, new BigDecimal("150"));
            burger.addIngredient(fromage, new BigDecimal("30"));
            burger.addIngredient(tomate, new BigDecimal("50"));
            burger.addIngredient(salade, new BigDecimal("30"));

            Recipe frites = new Recipe("Frites maison", "Couper et frire");
            frites.addIngredient(pommeDeTerre, new BigDecimal("300"));
            frites.addIngredient(huile, new BigDecimal("50"));

            Recipe saladeVeg = new Recipe("Salade composée", "Assembler les ingrédients");
            saladeVeg.addIngredient(salade, new BigDecimal("100"));
            saladeVeg.addIngredient(tomate, new BigDecimal("80"));
            saladeVeg.addIngredient(fromage, new BigDecimal("40"));

            Recipe veganBowl = new Recipe("Bowl vegan", "Assembler et assaisonner");
            veganBowl.addIngredient(tofu, new BigDecimal("120"));
            veganBowl.addIngredient(tomate, new BigDecimal("60"));
            veganBowl.addIngredient(salade, new BigDecimal("80"));
            veganBowl.addIngredient(huile, new BigDecimal("20"));

            Recipe brownie = new Recipe("Brownie", "Cuire au four");
            brownie.addIngredient(farine, new BigDecimal("150"));
            brownie.addIngredient(chocolat, new BigDecimal("200"));
            brownie.addIngredient(beurre, new BigDecimal("100"));
            brownie.addIngredient(oeuf, new BigDecimal("2"));

            Recipe pancakes = new Recipe("Pancakes", "Mélanger tous les ingrédients puis cuire à la poêle");
            pancakes.addIngredient(farine, new BigDecimal("200"));
            pancakes.addIngredient(lait, new BigDecimal("300"));
            pancakes.addIngredient(oeuf, new BigDecimal("2"));
            pancakes.addIngredient(sucre, new BigDecimal("50"));
            pancakes.addIngredient(beurre, new BigDecimal("20"));

            Recipe bananaCake = new Recipe("Banana cake vegan", "Mélanger et cuire");
            bananaCake.addIngredient(banane, new BigDecimal("200"));
            bananaCake.addIngredient(farine, new BigDecimal("150"));
            bananaCake.addIngredient(laitAmande, new BigDecimal("200"));
            bananaCake.addIngredient(sucre, new BigDecimal("80"));

            recipeDao.saveAll(List.of(
                    burger, frites, saladeVeg, veganBowl, brownie,
                    pancakes, bananaCake));

            System.out.println("Recipes initialized");
        }
    }

    private void initMenuItems() {

        if (menuItemDao.count() == 0) {

            Recipe pancakes = recipeDao.findByName("Pancakes").orElseThrow();
            Recipe burger = recipeDao.findByName("Burger maison").orElseThrow();
            Recipe salade = recipeDao.findByName("Salade composée").orElseThrow();
            Recipe veganBowl = recipeDao.findByName("Bowl vegan").orElseThrow();
            Recipe brownie = recipeDao.findByName("Brownie").orElseThrow();
            Recipe bananaCake = recipeDao.findByName("Banana cake vegan").orElseThrow();

            List<MenuItem> items = List.of(

                    new MenuItem(
                            "Burger classique",
                            "Recette signature",
                            new BigDecimal("14.50"),
                            MenuItemStatus.AVAILABLE,
                            MenuItemType.MAIN,
                            burger
                    ),

                    new MenuItem(
                            "Burger premium",
                            "Version gourmet",
                            new BigDecimal("18.90"),
                            MenuItemStatus.AVAILABLE,
                            MenuItemType.MAIN,
                            burger
                    ),

                    new MenuItem(
                            "Burger promo",
                            "Offre spéciale",
                            new BigDecimal("11.00"),
                            MenuItemStatus.AVAILABLE,
                            MenuItemType.MAIN,
                            burger
                    ),

                    new MenuItem(
                            "Burger test",
                            "Non affiché",
                            new BigDecimal("15.00"),
                            MenuItemStatus.HIDDEN,
                            MenuItemType.MAIN,
                            burger
                    ),

                    new MenuItem(
                            "Salade composée",
                            "Option végétarienne",
                            new BigDecimal("9.50"),
                            MenuItemStatus.AVAILABLE,
                            MenuItemType.STARTER,
                            salade
                    ),

                    new MenuItem(
                            "Bowl vegan",
                            "Plat équilibré",
                            new BigDecimal("11.50"),
                            MenuItemStatus.AVAILABLE,
                            MenuItemType.MAIN,
                            veganBowl
                    ),

                    new MenuItem(
                            "Banana cake vegan",
                            "Dessert sans produit animal",
                            new BigDecimal("7.00"),
                            MenuItemStatus.AVAILABLE,
                            MenuItemType.DESSERT,
                            bananaCake
                    ),

                    new MenuItem(
                            "Brownie chocolat",
                            "Dessert gourmand",
                            new BigDecimal("6.50"),
                            MenuItemStatus.AVAILABLE,
                            MenuItemType.DESSERT,
                            brownie
                    ),

                    new MenuItem(
                            "Pancakes classiques",
                            "Petit déjeuner",
                            new BigDecimal("8.50"),
                            MenuItemStatus.AVAILABLE,
                            MenuItemType.DESSERT,
                            pancakes
                    ),

                    new MenuItem(
                            "Pancakes premium",
                            "Version luxe",
                            new BigDecimal("12.00"),
                            MenuItemStatus.HIDDEN,
                            MenuItemType.DESSERT,
                            pancakes
                    )
            );

            menuItemDao.saveAll(items);

            System.out.println("MenuItems initialized");
        }
    }
}