package com.WattanArt.Utils.config;

import android.content.Context;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;

import com.WattanArt.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationTool {

    private Context context;

    public ValidationTool(Context context) {
        this.context = context;
    }


    public static ValidationTool getInstance(Context context) {
        return new ValidationTool(context);
    }


    public boolean validateEmail(EditText editText, String errorMassage) {
        String email = editText.getText().toString();
        email = email.trim();
        if (isNotEmpty(email)) {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                return true;
            } else {
                editText.setError(errorMassage);
                return false;
            }
        } else {
            editText.setError(context.getResources().getString(R.string.email_hint));
            return false;
        }
    }

    public boolean validateEmailForgetPass(EditText editText, String errorMassage) {
        String email = editText.getText().toString();
        email = email.trim();
        if (isNotEmpty(email)) {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                return true;
            } else {
                editText.setError(errorMassage);
                return false;
            }
        } else {
            editText.setError(context.getResources().getString(R.string.invalid_email_forget_pass));
            return false;
        }
    }

    public boolean isPasswordMatch(String password, EditText confirmPasword, String errorMassage) {
        String confirmPasswrod = confirmPasword.getText().toString();
        if (isNotEmpty(confirmPasswrod)) {
            if (password.equals(confirmPasswrod)) {
                return true;
            } else {
                confirmPasword.setError(errorMassage);

            }
        } else {
            confirmPasword.setError(context.getResources().getString(R.string.invalid_confirm_pass_hint));
            return false;

        }
        return false;
    }


    public boolean validatePhone(EditText editText, String errorMassage) {
        String content = editText.getText().toString();
        String splitContent;
        if (isNotEmpty(content)) {

//            if (content.startsWith("0")) {
//            if (checkIsNumber(content)) {
                // true
                if (content.startsWith("+")) {
                    splitContent = content.replace("+", "");
                        if (checkIsNumber(splitContent)) {
                    // true
//                    if (splitContent.length() >= 4) {
                        if (splitContent.length() >= 11) {
                            return true;

                        } else {
                            editText.setError(errorMassage);
                            return false;
                        }

                    } else {
                        editText.setError(errorMassage);
                        return false;
                    }

                } else {

                    if (checkIsNumber(content)) {
                        if (content.length() >= 11) {
                            return true;

                        } else {
                            editText.setError(errorMassage);
                            return false;
                        }

                    } else {
                        editText.setError(errorMassage);
                        return false;
                    }

                }
            } else {
                editText.setError(errorMassage);
                return false;
            }


//        } else {
//            editText.setError(context.getResources().getString(R.string.phone_hint));
//            return false;
//        }
    }


    private static boolean checkIsNumber(String content) {
        String regex = "[0-9]+";
        if (content.matches(regex)) {
            if (content.length() > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    //    private static final String PASSWORD_PATTERN =
//            "((?=.*\\d).(?=.*[a-zA-Z]).{5,20})";
    private static final String PASSWORD_PATTERN =
            "((?=.*\\d).{6,20})";

    private Pattern pattern;
    private Matcher matcher;

    public boolean validatePassword(EditText editText, String errorMassage) {
        String password = editText.getText().toString();
        if (isNotEmpty(password)) {
            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(password);

            if (password.length()< 6) {
                editText.setError(context.getResources().getString(R.string.password_pattern2));

                return false;
            } else {
//
                return true;
            }
//            if (matcher.matches()) {
//                return true;
//            } else {
//                editText.setError(context.getResources().getString(R.string.password_pattern2));
////
//                return false;
//            }
        } else {
            editText.setError(errorMassage);
            return false;
        }
    }

    public boolean validWebsiteUrl_ifexist(EditText editText, String errorMassage) {
        String websiteUrl = editText.getText().toString();
        if (isNotEmpty(websiteUrl)) {
            if (Patterns.WEB_URL.matcher(websiteUrl).matches()) {
                return true;
            } else {
                editText.setError(errorMassage);
                return false;
            }
        } else {
            return true;
        }
    }


    public boolean isNotEmpty(String text) {
        if (text != null) {
            if (!text.trim().equalsIgnoreCase("")) {
                return true;
            }
        }
        return false;
    }


    private static boolean checkIsNational(String content) {
        String regex = "[0-9]+";
        if (content.matches(regex)) {
            if (content.toString().length() > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public boolean validateRequiredField(EditText editText, String error) {
        String content = editText.getText().toString();
        if (isNotEmpty(content)) {
            return true;
        } else {
            editText.setError(error);
            return false;
        }
    }

    public boolean validateName(EditText editText, String error) {
        String content = editText.getText().toString();
        if (content.isEmpty()) {
//            editText.setError(error);
            return true;
        } else {
            editText.setError(error);
            return false;
        }
    }

    public boolean validateImageField(EditText editText, String error) {
        String content = editText.getText().toString();
//        if (!CheckUtils.validString(content)) {

        if (content.trim().equalsIgnoreCase("")) {
            editText.setError(error);
            return false;
        } else {
            return true;
        }
    }

    public boolean validateField(EditText editText, String error) {
        String content = editText.getText().toString();
        if (isNotEmpty(content)) {

            return true;
        } else {
            editText.setError(error);
            return false;
        }
    }

    public boolean validateRequiredField(TextView textView) {
        String content = textView.getText().toString();
        if (content.trim().equalsIgnoreCase("")) {

            return false;
        } else {
            return true;
        }
    }

    public boolean validateRequiredField(String string, TextView textView, String error) {
        if (string.trim().equalsIgnoreCase("")) {
            textView.setError(error);
            return false;
        } else {
            return true;
        }


    }

}
