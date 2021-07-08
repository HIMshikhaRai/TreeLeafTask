package com.himshikha.realmcrud.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.himshikha.realmcrud.R;
import com.himshikha.realmcrud.models.User;

import io.realm.RealmResults;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder> {
    private Context mContext;
    private RealmResults<User> mUserList;
    private OnUserItemClickedListener onUserItemClickedListener;

    public UserListAdapter(Context context, RealmResults<User> users, OnUserItemClickedListener onUserItemClickedListener) {
        this.mContext = context;
        this.mUserList = users;
        this.onUserItemClickedListener = onUserItemClickedListener;
    }

    @NonNull
    @Override
    public UserListAdapter.UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_list, parent, false);
        UserListViewHolder userListViewHolder = new UserListViewHolder(view);
        return userListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.UserListViewHolder holder, final int position) {
        holder.tvName.setText(mUserList.get(position).getName());
        holder.tvAddress.setText(mUserList.get(position).getAddress());
        String gender;
        if (mUserList.get(position).getGender() == 1)
            gender = "Male";
        else if (mUserList.get(position).getGender() == 2)
            gender = "Female";
        else
            gender = "Other";
        holder.tvGender.setText(gender);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUserItemClickedListener.onUserItemCliked(mUserList.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mUserList != null ? mUserList.size() : 0);
    }

    public class UserListViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvGender, tvAddress;

        public UserListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvGender = itemView.findViewById(R.id.tv_gender);
            tvAddress = itemView.findViewById(R.id.tv_address);
        }
    }

    public interface OnUserItemClickedListener {
        void onUserItemCliked(int id);
    }
}
