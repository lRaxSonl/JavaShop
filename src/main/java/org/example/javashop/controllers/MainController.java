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
    private Button admin_panel_B;

    @FXML
    private AnchorPane anchor_main_profile;

    @FXML
    private AnchorPane anchor_products_main;

    @FXML
    private Label balance_main;

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
    private Label pa_product_rating;

    @FXML
    private Label pa_product_quantity;

    @FXML
    private ListView<String> pr_listView;

    @FXML
    private AnchorPane pr_options_panel;

    @FXML
    private Button products_B;

    @FXML
    private Button profile_B;

    @FXML
    private Label role_main;

    @FXML
    private Label user_product_list;

    @FXML
    private Label username1;

    @FXML
    private Label username_main;

    @FXML
    private ListView<String> showProducts_main;


    private static Product currentProduct;

    @FXML
    void initialize() {


        User user = new LoginController().getUser();
        username1.setText(user.getUsername());

        //Кнопка переключения на профиль
        profile_B.setOnAction(event -> {
            anchor_products_main.setVisible(false);
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


        admin_panel_B.setOnAction(event -> {
            
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
}
