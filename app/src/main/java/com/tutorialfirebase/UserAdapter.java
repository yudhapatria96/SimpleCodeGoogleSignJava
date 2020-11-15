package com.tutorialfirebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<User> userResponse;
    private Context mActivity;

    public UserAdapter(Context mActivity){
        this.mActivity = mActivity;
        this.userResponse = new ArrayList<>();
//        this.eventResponse = eventResponse
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.item_user, parent, false);
        viewHolder = new ViewHolder(viewItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        User userRespond = userResponse.get(position);

        viewHolder.tvName.setText(userRespond.username);
        viewHolder.tvEmail.setText(userRespond.email);


    }

    @Override
    public int getItemCount() {
        return userResponse.size();
    }
    public void add(ArrayList<User> r) {
        userResponse.addAll(r);
        notifyItemInserted(userResponse.size() - 1);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName,tvEmail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);

        }
    }
}
