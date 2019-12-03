package com.WattanArt.Adapters;

import android.support.transition.TransitionManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.WattanArt.MyApplication;
import com.WattanArt.R;
import com.WattanArt.Utils.Localization;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.widgets.CustomeTextView;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.model.Response.FAQresponseModel;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import butterknife.BindView;

public class FAQCustomAdapter extends RecyclerView.Adapter<FAQCustomAdapter.MyViewHolder> {
    private List<FAQresponseModel.ResultEntity> resultEntityList;
    private RecyclerView recyclerView;
    private int mExpandedPosition = -1;
    private UserData userData;

    public FAQCustomAdapter(RecyclerView recyclerView, List<FAQresponseModel.ResultEntity> resultEntityList) {
        this.resultEntityList = resultEntityList;
        this.recyclerView = recyclerView;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_faq_item, parent, false);


        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return resultEntityList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout question_layout;
        CustomeTextView questionTitleTextView;
        CustomeTextView answerTitleTextView;
        LinearLayout expandable_layout;
        ImageView marrowImageView;
        int position;

        MyViewHolder(View itemView) {
            super(itemView);
            this.questionTitleTextView = itemView.findViewById(R.id.faq_title_text_view);
            this.answerTitleTextView = itemView.findViewById(R.id.faq_answer_text_view);
            this.question_layout = itemView.findViewById(R.id.question_layout);
            this.expandable_layout = itemView.findViewById(R.id.expandable_layout);
            this.marrowImageView = itemView.findViewById(R.id.arrow);

        }

        public void bind(int position) {
            userData = new UserData();
            this.position = position;
            questionTitleTextView.setText(resultEntityList.get(position).getTitle());
            answerTitleTextView.setText(resultEntityList.get(position).getDescrption());

            //expand
            final boolean isExpanded = position == mExpandedPosition;
            expandable_layout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            itemView.setActivated(isExpanded);
            if (!isExpanded) {
                marrowImageView.setRotation(0);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mExpandedPosition = isExpanded ? -1 : position;
                    TransitionManager.beginDelayedTransition(recyclerView);
                    notifyDataSetChanged();
                    if (userData.getLocalization(MyApplication.getAppContext()) == Localization.ARABIC_VALUE) {
                        //to rotate arrow to bottom
                        marrowImageView.setRotation(marrowImageView.getRotation() - 90);

                    } else {
                        marrowImageView.setRotation(marrowImageView.getRotation() + 90);

                    }
                }
            });

        }


    }
}
