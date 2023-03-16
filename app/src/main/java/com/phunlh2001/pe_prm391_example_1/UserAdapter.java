package com.phunlh2001.pe_prm391_example_1;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phunlh2001.pe_prm391_example_1.data.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> mListUser;
    private IClickItemUser _clickItemUser;

    public interface IClickItemUser {
        void updateUser(User user);
    }

    public UserAdapter(IClickItemUser _clickItemUser) {
        this._clickItemUser = _clickItemUser;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<User> list) {
        this.mListUser = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = mListUser.get(position);
        if (user == null) return;

        holder.tvUsername.setText(user.getUsername());
        holder.tvAddress.setText(user.getAddress());

        holder.btnUpdate.setOnClickListener(view -> {
            _clickItemUser.updateUser(user);
        });
    }

    @Override
    public int getItemCount() {
        if (mListUser != null) return mListUser.size();
        return 0;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvUsername, tvAddress;
        private final Button btnUpdate;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUsername = itemView.findViewById(R.id.tv_name);
            tvAddress = itemView.findViewById(R.id.tv_address);
            btnUpdate = itemView.findViewById(R.id.btn_update);
        }
    }
}
