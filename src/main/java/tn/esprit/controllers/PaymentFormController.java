package tn.esprit.controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.Token;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodCreateParams;
import com.stripe.param.TokenCreateParams;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class PaymentFormController {
    @FXML
    private TextField cardNumberField;
    @FXML
    private TextField expMonthField;
    @FXML
    private TextField expYearField;
    @FXML
    private TextField cvcField;
    @FXML
    private TextField clientNameField;
    @FXML
    private TextField emailField;

    @FXML
    public void processPayment() {
        if (cvcField.getText().length() != 3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer un code CVC valide");
            alert.showAndWait();
        } else if ((clientNameField == null) || (clientNameField.getText().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer votre nom");
            alert.showAndWait();
        } else if (!cardNumberField.getText().equals("4242424242424242")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer un numéro de carte valide");
            alert.showAndWait();
        } else if (expYearField.getText().length() != 2 || expYearField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer une date valide");
            alert.showAndWait();
        } else if (emailField == null || emailField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer votre email");
            alert.showAndWait();
        } else {
            try {
                Stripe.apiKey = "sk_test_51OqPMMFhMW4sZiUh3UHaW9EBbRTOO9qt1mR0thGB5g4o7FFIzFz7XMuDc0pAfop4lU0N7FtkdZ4b9ejQW8J5WP5y00wNohmpeh";

                // Create a Token
                TokenCreateParams tokenParams = TokenCreateParams.builder()
                        .setCard(TokenCreateParams.Card.builder()
                                .setNumber(cardNumberField.getText()) // Use the actual card number
                                .setExpMonth(expMonthField.getText()) // Pass as String
                                .setExpYear(expYearField.getText()) // Pass as String
                                .setCvc(cvcField.getText()) // Pass as String
                                .build())
                        .build();

                Token token = Token.create(tokenParams);

// Create a Charge with the Token
                ChargeCreateParams chargeParams = ChargeCreateParams.builder()
                        .setAmount(100L * 100) // Default amount set to 100
                        .setCurrency("usd")
                        .setSource(token.getId())
                        .setDescription("Example charge")
                        .build();

                Charge charge = Charge.create(chargeParams);
                if ("succeeded".equals(charge.getStatus())) {
                    System.out.println("Charge successful. Charge ID: " + charge.getId());
                } else {
                    System.out.println("Charge failed. Charge status: " + charge.getStatus());
                }
            } catch (StripeException e) {
                System.out.println("Charge failed. Error: " + e.getMessage());
            }
        }
    }
}