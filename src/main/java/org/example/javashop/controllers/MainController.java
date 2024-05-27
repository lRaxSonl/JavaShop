package org.example.javashop.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.javashop.models.Product;
import org.example.javashop.models.User;
import org.example.javashop.repositories.ProductRepository;
import org.example.javashop.services.ProductService;
import org.example.javashop.services.UserService;

public class MainController {
    private static final UIHandler uiHandler = new UIHandler();
    private static final UserService userService = new UserService();
    private static final ProductService productService = new ProductService();
    private static final ProductRepository productRepository = new ProductRepository();


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> manager_list;

    @FXML
    private ListView<String> user_list;

    @FXML
    private Button add_manager_B;

    @FXML
    private Button add_product_B;

    @FXML
    private Button admin_panel_B;

    @FXML
    private AnchorPane anchor_admin_panel;

    @FXML
    private AnchorPane anchor_admin_panel_2;

    @FXML
    private AnchorPane anchor_main_profile;

    @FXML
    private AnchorPane anchor_products_main;

    @FXML
    private Label balance_main;

    @FXML
    private Button delete_manager_B;

    @FXML
    private TextField deposit_main_TF;

    @FXML
    private Button enter_deposit_B;

    @FXML
    private Button pa_buy_product;

    @FXML
    private Label pa_product_name;

    @FXML
    private Label pa_product_price;

    @FXML
    private Label pa_product_quantity;

    @FXML
    private Label pa_product_rating;

    @FXML
    private ListView<String> pr_listView;

    @FXML
    private AnchorPane pr_options_panel;

    @FXML
    private TextField product_name_TF;

    @FXML
    private TextField product_price_TF;

    @FXML
    private TextField product_quantity_TF;

    @FXML
    private Button products_B;

    @FXML
    private Button profile_B;

    @FXML
    private Label role_main;

    @FXML
    private ListView<String> showProducts_main;

    @FXML
    private Label user_product_list;

    @FXML
    private Label username1;

    @FXML
    private Label username_main;


    private static Product currentProduct;
    private static User currentUser, currentManager;

    @FXML
    void initialize() {


        User user = new LoginController().getUser();
        username1.setText(user.getUsername());

        //Кнопка переключения на профиль
        profile_B.setOnAction(event -> {
            anchor_products_main.setVisible(false);
            anchor_admin_panel.setVisible(false);
            anchor_admin_panel_2.setVisible(false);
            anchor_main_profile.setVisible(true);

            username_main.setText(user.getUsername());
            role_main.setText("Роль: " + user.getRole());
            balance_main.setText("Баланс: " + user.getBalance());
            updatePurchasedProductsList(user);
        });

        //Кнопка пополнения баланса
        enter_deposit_B.setOnAction(event -> {
            double deposit_sum;
            try {
                deposit_sum = Double.parseDouble(deposit_main_TF.getText().trim());
                userService.addBalance(user, deposit_sum);
                uiHandler.showAlert("Успешно", "Транзакция выполнена, баланс был пополнен.");
                balance_main.setText("Баланс: " + user.getBalance());

            }catch (NumberFormatException e) {
                uiHandler.showAlert("Ошибка", "Неправильный формат ввода, введите число.");
            }
        });


        //Кнопка переключения на список продуктов
        products_B.setOnAction(event -> {
            anchor_main_profile.setVisible(false);
            anchor_admin_panel.setVisible(false);
            anchor_admin_panel_2.setVisible(false);
            anchor_products_main.setVisible(true);

            List<String> product_names = productService.getAllProductNames();

            pr_listView.getItems().clear(); // Очищаем список перед добавлением новых элементов

            for (String productName : product_names) {
                if (!pr_listView.getItems().contains(productName)) {
                    pr_listView.getItems().add(productName);
                }
            }
        });

        //Обработка выбора продукта из списка
        pr_listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                currentProduct = productRepository.findByName(newValue);
                if (currentProduct != null) {
                    pr_options_panel.setVisible(true);

                    pa_product_name.setText(currentProduct.getName());
                    pa_product_price.setText("Цена: " + currentProduct.getPrice());
                    pa_product_quantity.setText("Кол-во: " + currentProduct.getQuantity());
                    pa_product_rating.setText("Рейтинг: " + productRepository.calculateRating(currentProduct));
                }
            }
        });

        //Кнопка покупки продукта
        pa_buy_product.setOnAction(event -> {
            if (currentProduct != null) {
                boolean purchaseSuccessful = productService.purchaseProduct(user, currentProduct);
                if (purchaseSuccessful) {
                    uiHandler.showAlert("Успешно", "Покупка выполнена успешно.");
                    balance_main.setText("Баланс: " + user.getBalance());
                    pa_product_quantity.setText("Кол-во: " + currentProduct.getQuantity());
                    pa_product_rating.setText("Рейтинг: " + productRepository.calculateRating(currentProduct));
                } else {
                    uiHandler.showAlert("Ошибка", "Покупка не удалась. Недостаточно средств или товара.");
                }
            } else {
                System.out.println(false);
            }
        });


        //Кнопка переключения на админ панель
        admin_panel_B.setOnAction(event -> {
            if(userService.isManager(user) || userService.isAdmin(user)){
                anchor_main_profile.setVisible(false);
                anchor_products_main.setVisible(false);
                anchor_admin_panel.setVisible(true);

                if(userService.isAdmin(user)) {
                    anchor_admin_panel_2.setVisible(true);

                    updateManagersList();
                    updateUsersList();
                }

            }else {
                uiHandler.showAlert("В доступе отказано.", "У вас нет прав на использования этой вкладки.");
            }
        });

        //Кнопка добавления продукта
        add_product_B.setOnAction(event -> {
            String product_name = product_name_TF.getText().trim();
            String product_price = product_price_TF.getText().trim();
            String product_quantity = product_quantity_TF.getText().trim();

            productService.addNewProduct(new Product(product_name, Double.parseDouble(product_price), Integer.parseInt(product_quantity)));
        });
    }


    private void updatePurchasedProductsList(User user) {
        List<String> purchasedProductNames = productService.getPurchasedProductNames(user);
        if(showProducts_main != null) {
            showProducts_main.getItems().clear();
            showProducts_main.getItems().addAll(purchasedProductNames);
        }else {
            showProducts_main.getItems().addAll(purchasedProductNames);
        }
    }

    private void updateManagersList(){
        List<String> managers = new ArrayList<>();

        for(User user : userService.getAllManagers()) {
            managers.add(user.getUsername());
        }

        manager_list.getItems().clear();
        manager_list.getItems().addAll(managers);
    }

    private void updateUsersList(){
        List<String> usernames = new ArrayList<>();

        for(User user : userService.getAllUsers()) {
            usernames.add(user.getUsername());
        }

        user_list.getItems().clear();
        user_list.getItems().addAll(usernames);
    }
}
