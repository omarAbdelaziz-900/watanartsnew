package com.WattanArt.ui.ContactUs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.Toast;


import com.WattanArt.Dagger.component.ActivityComponent;
import com.WattanArt.R;
import com.WattanArt.Utils.config.ValidationTool;
import com.WattanArt.Utils.widgets.CustomeButtonBold;
import com.WattanArt.model.Response.ContactUsModel;
import com.WattanArt.ui.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactInnerFragment extends BaseFragment implements ContactUsMvpView {


    @BindView(R.id.email_et)
    EditText emailEditText;

    @BindView(R.id.subject_et)
    EditText subjectEditText;

    @BindView(R.id.message_et)
    EditText messageEditText;

    @BindView(R.id.send_btn)
    CustomeButtonBold sendButton;

//    @BindView(R.id.mainLayout)
//    LinearLayout mainLayout;

    private ValidationTool validationTool;

    @Inject
    ContactUsPresenterImp<ContactUsMvpView> mPresenter;

    public ContactInnerFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_inner, container, false);
        ButterKnife.bind(this, view);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            mPresenter.onAttach(this);
        }
        ButterKnife.bind(this, view);
//        mainLayout.requestFocus();
        validationTool = new ValidationTool(getActivity());
        messageEditText.setMovementMethod(new ScrollingMovementMethod());
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid()) {
                    String subject = subjectEditText.getText().toString();
                    String email = emailEditText.getText().toString();
                    String message = messageEditText.getText().toString();

                    if (isNetworkConnected()) {
                        mPresenter.sendFeedBack(email, subject, message);

                    } else {
                        showMessage(getString(R.string.error_no_internet_connection));
                    }
                }


            }
        });

        return view;
    }

    void emptyFields() {
        subjectEditText.setHint(R.string.subject);
        emailEditText.setHint(R.string.email);
        messageEditText.setHint(R.string.message);
        subjectEditText.getText().clear();
        emailEditText.getText().clear();
        messageEditText.getText().clear();


    }


    private boolean isValid() {
        boolean vaildMail = validationTool.validateEmail(emailEditText, getString(R.string.invalid_email));
        boolean vaildMessage = validationTool.validateRequiredField(messageEditText, getString(R.string.messeage_hint));
        boolean vaildSubject = validationTool.validateRequiredField(subjectEditText, getString(R.string.messeage_hint));

        if (vaildMail && vaildMessage && vaildSubject) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void UpdateInfoUi(ContactUsModel contactUsResult) {

    }

    @Override
    public void flagSuccessfullySend() {
        emptyFields();
    }


}
