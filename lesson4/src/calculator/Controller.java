package calculator;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.Collections;



public class Controller  {

    @FXML
    private TextField textField;

    @FXML
    private GridPane gridPane;

    private StringBuilder value;
    private String operation;
    private String operationMem;


    private String opA;
    private String opB;
    private String opC;
    private boolean newInput;

    public Controller()
    {
        value = new StringBuilder();
        newInput = true;
        popOps();
        resetOperations();
    }


    public void clicked(MouseEvent mouseEvent) {
        Button btn = (Button)mouseEvent.getSource();
        String cmd = btn.getText();
        if ( cmd.matches("[0-9]" ) ) {
            if ( newInput ) {
                value.setLength(0);
                newInput = false;
            }
            value.append(cmd);
            updateText(value.toString(), newInput);
        } else {
            switch (cmd) {
                case ButtonNames.op_ce:
                    value.setLength(0);
                    newInput = true;
                    break;
                case ButtonNames.op_c:
                    newInput = true;
                    value.setLength(0);
                    popOps();
                    resetOperations();
                    break;
                case ButtonNames.op_backspace:
                    value.setLength(0);
                    value.append(textField.getText());
                    if (value.length() > 0) {
                        value.setLength(value.length() - 1);
                    }
                    break;
                case ButtonNames.op_fractional:
                    if (!value.toString().contains(cmd)) {
                        value.append('.');
                    }
                    break;
                case ButtonNames.op_plus_minus:
                    if (value.length() != 0) {
                        if (!value.toString().contains("-")) {
                            value.insert(0, '-');
                        } else {
                            value.delete(0,1);
                        }
                    }
                    break;
                case ButtonNames.op_plus:
                case ButtonNames.op_minus:
                    if ( value.length() != 0  ) {
                        if ( operation != null ) {
                            executeCalc();
                        }
                        if ( haveSavedOps() ) {
                            popOps();
                            executeCalc();
                        }
                        opA = value.toString();
                        operation = cmd;
                        newInput = true;
                    }
                    break;
                case ButtonNames.op_divide:
                case ButtonNames.op_multiply:
                    if ( value.length() != 0 ) {
                        if (( operation != null ) ) {
                            //Translate multiply/divide
                            if ( operation.equals(ButtonNames.op_multiply) || operation.equals(ButtonNames.op_divide)) {
                                executeCalc();
                            } else {
                                //Save previos data
                                saveOps();
                            }
                        }
                        opA = value.toString();
                        operation = cmd;
                        newInput = true;
                    }
                    break;
                case ButtonNames.op_equal:
                    executeCalc();
                    if ( operation == null & haveSavedOps() ) {
                        popOps();
                        executeCalc();
                    }
                    newInput = true;
                    break;
            };
            updateText(value.toString(), newInput);
        }
    }


    private void resetOperations() {
        opA = null;
        //opB = null;
        opC = null;
        operation = null;
        //operation_mem = null;
    }

    private void updateText(String text, boolean truncate) {
        String finalText = text;
        if ( truncate && text.length() > 2 && text.matches("-?[0-9]*\\.[0]+$") ) {
            finalText = text.substring(0,text.indexOf('.'));
        }
        textField.setText(finalText);
    }

    private String calulate( String a, String b, String op ) {
        double left_op;
        double right_op;
        if ( a.length() > 0  && a.charAt(a.length()-1) == '.' ) {
            a = a + "0";
        }
        if ( b.length() > 0  && b.charAt(b.length()-1) == '.' ) {
            b = b + "0";
        }

        try {
            left_op = Double.valueOf(a);
            right_op = Double.valueOf(b);
        } catch ( NumberFormatException e) {
            return null;
        }

        switch ( op ) {
            case ButtonNames.op_multiply:
                return Double.toString(left_op * right_op);
            case ButtonNames.op_minus:
                return Double.toString(left_op - right_op );
            case ButtonNames.op_plus:
                return Double.toString( left_op + right_op );
            case ButtonNames.op_divide:
                if ( right_op == 0 ) {
                    return "0";
                }
                return Double.toString(left_op / right_op);
        };
        return null;
    }

    private void saveOps() {
        operationMem = operation;
        opB = opA;
    }

    private void popOps() {
        operation = operationMem;
        opA = opB;
        operationMem = null;
        opB = null;
    }

    private boolean haveSavedOps() {
        return operationMem != null;
    }

    private void executeCalc() {
        if (    value.length() != 0 ) {
            if (  opA != null && operation != null ) {
                opC = calulate(opA, value.toString(), operation);
                value.setLength(0);
                if ( opC != null ) value.append(opC);
                resetOperations();
            }
        }
    }
}
