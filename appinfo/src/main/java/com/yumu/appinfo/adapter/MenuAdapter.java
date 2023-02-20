package com.yumu.appinfo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yumu.appinfo.R;
import com.yumu.appinfo.bean.MainMenu;

import java.util.List;

/**
 * Date :  2020-02-28.
 * Time :  11:39.
 * Created by sunan.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private List<MainMenu> mainMenus;
    private Context context;
    private MenuClickCallback callback;

    public MenuAdapter(Context context, List<MainMenu> albumList) {
        this.context = context;
        this.mainMenus = albumList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu_adapter, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MainMenu mainMenu = mainMenus.get(position);

        holder.tvTitle.setText(mainMenu.getTitle());
        holder.tvDescription.setText(mainMenu.getDescription());
        if (mainMenu.getRes_id() > 0) {
            holder.ivImage.setImageResource(mainMenu.getRes_id());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onItemClick(mainMenu);
                }
            }
        });
    }

    public interface MenuClickCallback {
        void onItemClick(MainMenu mainMenu);
    }

    public void setCallback(MenuClickCallback callback) {
        this.callback = callback;
    }

    @Override
    public int getItemCount() {
        return mainMenus.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivImage;
        private TextView tvTitle, tvDescription;

        public ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_title);
            tvDescription = view.findViewById(R.id.tv_description);
            ivImage = view.findViewById(R.id.iv_image);
        }
    }
}
