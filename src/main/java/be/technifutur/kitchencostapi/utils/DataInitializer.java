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
                            "chef@kc.com",
                            "chef",
                            bcrypt.hashToString(12, "chef".toCharArray()),
                            chef),

                    new User(
                            "cook@kc.com",
                            "cook",
                            bcrypt.hashToString(12, "cook".toCharArray()),
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

            Ingredient pates = new Ingredient("Pâtes", Unit.GRAM, new BigDecimal("0.004"), IngredientOrigin.PLANT);
            pates.addAllergen(Allergen.GLUTEN);

            Ingredient lait = new Ingredient("Lait", Unit.MILLILITER, new BigDecimal("0.001"), IngredientOrigin.DAIRY);
            lait.addAllergen(Allergen.MILK);

            Ingredient laitAvoine = new Ingredient("Lait d'avoine", Unit.MILLILITER, new BigDecimal("0.003"), IngredientOrigin.PLANT);
            laitAvoine.addAllergen(Allergen.GLUTEN);

            Ingredient oeuf = new Ingredient("Œuf", Unit.PIECE, new BigDecimal("0.30"), IngredientOrigin.EGG);
            oeuf.addAllergen(Allergen.EGGS);

            Ingredient sucre = new Ingredient("Sucre", Unit.GRAM, new BigDecimal("0.001"), IngredientOrigin.PLANT);

            Ingredient beurre = new Ingredient("Beurre", Unit.GRAM, new BigDecimal("0.01"), IngredientOrigin.DAIRY);
            beurre.addAllergen(Allergen.MILK);

            Ingredient steak = new Ingredient("Steak haché", Unit.GRAM, new BigDecimal("0.02"), IngredientOrigin.MEAT);

            Ingredient salade = new Ingredient("Salade", Unit.GRAM, new BigDecimal("0.003"), IngredientOrigin.PLANT);

            Ingredient tomate = new Ingredient("Tomate", Unit.GRAM, new BigDecimal("0.004"), IngredientOrigin.PLANT);

            Ingredient painBurger = new Ingredient("Pain burger", Unit.PIECE, new BigDecimal("0.80"), IngredientOrigin.PLANT);
            painBurger.addAllergen(Allergen.GLUTEN);

            Ingredient fromage = new Ingredient("Fromage", Unit.GRAM, new BigDecimal("0.015"), IngredientOrigin.DAIRY);
            fromage.addAllergen(Allergen.MILK);

            Ingredient pommeDeTerre = new Ingredient("Pomme de terre", Unit.GRAM, new BigDecimal("0.002"), IngredientOrigin.PLANT);

            Ingredient huile = new Ingredient("Huile d'olive", Unit.MILLILITER, new BigDecimal("0.005"), IngredientOrigin.PLANT);

            Ingredient chocolatNoir = new Ingredient("Chocolat noir", Unit.GRAM, new BigDecimal("0.02"), IngredientOrigin.PLANT);

            Ingredient banane = new Ingredient("Banane", Unit.GRAM, new BigDecimal("0.003"), IngredientOrigin.PLANT);

            Ingredient tofu = new Ingredient("Tofu", Unit.GRAM, new BigDecimal("0.01"), IngredientOrigin.PLANT);
            tofu.addAllergen(Allergen.SOY);

            Ingredient pesto = new Ingredient("Pesto", Unit.GRAM, new BigDecimal("0.025"), IngredientOrigin.DAIRY);
            pesto.addAllergen(Allergen.NUTS);
            pesto.addAllergen(Allergen.MILK);

            ingredientDao.saveAll(List.of(
                    farine,
                    pates,
                    lait,
                    laitAvoine,
                    oeuf,
                    sucre,
                    beurre,
                    steak,
                    salade,
                    tomate,
                    painBurger,
                    fromage,
                    pommeDeTerre,
                    huile,
                    chocolatNoir,
                    banane,
                    tofu,
                    pesto
            ));

            System.out.println("Ingredients initialized");
        }
    }

    private void initRecipes() {

        if (recipeDao.count() == 0) {

            Ingredient farine = ingredientDao.findByName("Farine").orElseThrow();
            Ingredient pates = ingredientDao.findByName("Pâtes").orElseThrow();
            Ingredient lait = ingredientDao.findByName("Lait").orElseThrow();
            Ingredient laitAvoine = ingredientDao.findByName("Lait d'avoine").orElseThrow();
            Ingredient oeuf = ingredientDao.findByName("Œuf").orElseThrow();
            Ingredient sucre = ingredientDao.findByName("Sucre").orElseThrow();
            Ingredient beurre = ingredientDao.findByName("Beurre").orElseThrow();
            Ingredient steak = ingredientDao.findByName("Steak haché").orElseThrow();
            Ingredient salade = ingredientDao.findByName("Salade").orElseThrow();
            Ingredient tomate = ingredientDao.findByName("Tomate").orElseThrow();
            Ingredient painBurger = ingredientDao.findByName("Pain burger").orElseThrow();
            Ingredient fromage = ingredientDao.findByName("Fromage").orElseThrow();
            Ingredient pommeDeTerre = ingredientDao.findByName("Pomme de terre").orElseThrow();
            Ingredient huile = ingredientDao.findByName("Huile d'olive").orElseThrow();
            Ingredient chocolatNoir = ingredientDao.findByName("Chocolat noir").orElseThrow();
            Ingredient banane = ingredientDao.findByName("Banane").orElseThrow();
            Ingredient tofu = ingredientDao.findByName("Tofu").orElseThrow();
            Ingredient pesto = ingredientDao.findByName("Pesto").orElseThrow();

            Recipe burger = new Recipe("Burger maison", "Assembler et cuire les ingrédients");

            burger.addIngredient(painBurger, new BigDecimal("1"));
            burger.addIngredient(steak, new BigDecimal("150"));
            burger.addIngredient(fromage, new BigDecimal("30"));
            burger.addIngredient(tomate, new BigDecimal("50"));
            burger.addIngredient(salade, new BigDecimal("30"));

            Recipe frites = new Recipe("Frites maison", "Couper puis frire les pommes de terre");

            frites.addIngredient(pommeDeTerre, new BigDecimal("300"));
            frites.addIngredient(huile, new BigDecimal("50"));

            Recipe saladeComposee = new Recipe("Salade composée", "Assembler les ingrédients");

            saladeComposee.addIngredient(salade, new BigDecimal("100"));
            saladeComposee.addIngredient(tomate, new BigDecimal("80"));
            saladeComposee.addIngredient(fromage, new BigDecimal("40"));

            Recipe veganBowl = new Recipe("Bowl vegan", "Assembler et assaisonner");

            veganBowl.addIngredient(tofu, new BigDecimal("120"));
            veganBowl.addIngredient(tomate, new BigDecimal("60"));
            veganBowl.addIngredient(salade, new BigDecimal("80"));
            veganBowl.addIngredient(huile, new BigDecimal("20"));

            Recipe brownie = new Recipe("Brownie chocolat", "Mélanger puis cuire au four");

            brownie.addIngredient(farine, new BigDecimal("150"));
            brownie.addIngredient(chocolatNoir, new BigDecimal("200"));
            brownie.addIngredient(beurre, new BigDecimal("100"));
            brownie.addIngredient(oeuf, new BigDecimal("2"));
            brownie.addIngredient(sucre, new BigDecimal("80"));

            Recipe pancakes = new Recipe("Pancakes", "Mélanger puis cuire à la poêle");

            pancakes.addIngredient(farine, new BigDecimal("200"));
            pancakes.addIngredient(lait, new BigDecimal("300"));
            pancakes.addIngredient(oeuf, new BigDecimal("2"));
            pancakes.addIngredient(sucre, new BigDecimal("50"));
            pancakes.addIngredient(beurre, new BigDecimal("20"));

            Recipe bananaCake = new Recipe("Banana cake vegan", "Mélanger puis cuire au four");

            bananaCake.addIngredient(banane, new BigDecimal("200"));
            bananaCake.addIngredient(farine, new BigDecimal("150"));
            bananaCake.addIngredient(laitAvoine, new BigDecimal("200"));
            bananaCake.addIngredient(sucre, new BigDecimal("80"));
            bananaCake.addIngredient(huile, new BigDecimal("40"));

            Recipe pestoPasta = new Recipe("Pâtes au pesto", "Cuire les pâtes puis ajouter le pesto");

            pestoPasta.addIngredient(pates, new BigDecimal("200"));
            pestoPasta.addIngredient(pesto, new BigDecimal("50"));

            Recipe amuseBouche = new Recipe("Amuse-bouche tomate", "Assembler et servir frais");

            amuseBouche.addIngredient(tomate, new BigDecimal("20"));
            amuseBouche.addIngredient(fromage, new BigDecimal("10"));
            amuseBouche.addIngredient(huile, new BigDecimal("5"));

            recipeDao.saveAll(List.of(
                    burger,
                    frites,
                    saladeComposee,
                    veganBowl,
                    brownie,
                    pancakes,
                    bananaCake,
                    pestoPasta,
                    amuseBouche
            ));

            System.out.println("Recipes initialized");
        }
    }

    private void initMenuItems() {

        if (menuItemDao.count() == 0) {

            Recipe burger = recipeDao.findByName("Burger maison").orElseThrow();
            Recipe frites = recipeDao.findByName("Frites maison").orElseThrow();
            Recipe saladeComposee = recipeDao.findByName("Salade composée").orElseThrow();
            Recipe veganBowl = recipeDao.findByName("Bowl vegan").orElseThrow();
            Recipe brownie = recipeDao.findByName("Brownie chocolat").orElseThrow();
            Recipe pancakes = recipeDao.findByName("Pancakes").orElseThrow();
            Recipe bananaCake = recipeDao.findByName("Banana cake vegan").orElseThrow();
            Recipe pestoPasta = recipeDao.findByName("Pâtes au pesto").orElseThrow();
            Recipe amuseBouche = recipeDao.findByName("Amuse-bouche tomate").orElseThrow();

            List<MenuItem> items = List.of(

                    new MenuItem(
                            "Burger classique",
                            "Notre burger signature",
                            new BigDecimal("14.50"),
                            MenuItemStatus.AVAILABLE,
                            MenuItemType.MAIN,
                            burger
                    ),

                    new MenuItem(
                            "Burger gourmet",
                            "Version premium du burger maison",
                            new BigDecimal("18.90"),
                            MenuItemStatus.AVAILABLE,
                            MenuItemType.MAIN,
                            burger
                    ),

                    new MenuItem(
                            "Burger promo",
                            "Offre spéciale du midi",
                            new BigDecimal("11.00"),
                            MenuItemStatus.AVAILABLE,
                            MenuItemType.MAIN,
                            burger
                    ),

                    new MenuItem(
                            "Burger test",
                            "Version non affichée au public",
                            new BigDecimal("15.00"),
                            MenuItemStatus.HIDDEN,
                            MenuItemType.MAIN,
                            burger
                    ),

                    new MenuItem(
                            "Frites maison",
                            "Pommes de terre fraîches",
                            new BigDecimal("5.50"),
                            MenuItemStatus.AVAILABLE,
                            MenuItemType.STARTER,
                            frites
                    ),

                    new MenuItem(
                            "Salade composée",
                            "Salade fraîche au fromage",
                            new BigDecimal("9.50"),
                            MenuItemStatus.AVAILABLE,
                            MenuItemType.STARTER,
                            saladeComposee
                    ),

                    new MenuItem(
                            "Bowl vegan",
                            "Tofu et légumes frais",
                            new BigDecimal("11.50"),
                            MenuItemStatus.AVAILABLE,
                            MenuItemType.MAIN,
                            veganBowl
                    ),

                    new MenuItem(
                            "Pâtes au pesto",
                            "Pesto au parmesan et basilic",
                            new BigDecimal("13.50"),
                            MenuItemStatus.AVAILABLE,
                            MenuItemType.MAIN,
                            pestoPasta
                    ),

                    new MenuItem(
                            "Brownie chocolat",
                            "Dessert chocolaté maison",
                            new BigDecimal("6.50"),
                            MenuItemStatus.AVAILABLE,
                            MenuItemType.DESSERT,
                            brownie
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
                            "Pancakes classiques",
                            "Petit déjeuner gourmand",
                            new BigDecimal("8.50"),
                            MenuItemStatus.AVAILABLE,
                            MenuItemType.DESSERT,
                            pancakes
                    ),

                    new MenuItem(
                            "Pancakes premium",
                            "Version brunch premium",
                            new BigDecimal("12.00"),
                            MenuItemStatus.HIDDEN,
                            MenuItemType.DESSERT,
                            pancakes
                    ),

                    new MenuItem(
                            "Amuse-bouche offert",
                            "Servi gratuitement avant le repas",
                            BigDecimal.ZERO,
                            MenuItemStatus.AVAILABLE,
                            MenuItemType.AMUSE_BOUCHE,
                            amuseBouche
                    )
            );

            menuItemDao.saveAll(items);

            System.out.println("MenuItems initialized");
        }
    }
}