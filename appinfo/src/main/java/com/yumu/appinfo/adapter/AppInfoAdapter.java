package com.yumu.appinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yumu.appinfo.R;
import com.yumu.appinfo.bean.APPInfo;
import com.yumu.appinfo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Date :  2020-02-28.
 * Time :  11:39.
 * Created by sunan.
 */
public class AppInfoAdapter extends RecyclerView.Adapter<AppInfoAdapter.ViewHolder> {
    private List<APPInfo> appInfoList;
    private Context context;

    public AppInfoAdapter(Context context) {
        this.context = context;
        appInfoList = new ArrayList<>();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_appinfo, null);
        return new ViewHolder(view);
    }

    public void setAppInfoList(List<APPInfo> appInfoList) {
        this.appInfoList = appInfoList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        APPInfo appInfo = appInfoList.get(position);
        holder.tvAppPosition.setText("第 " + (position + 1) + "  个");
        holder.tvAppNameValue.setText(appInfo.getAppName());
        holder.tvAppPackageNameValue.setText(appInfo.getPackageName());
        holder.tvAppVersionNameValue.setText(appInfo.getVersionName());
        holder.tvAppVersionCodeValue.setText(String.valueOf(appInfo.getVersionCode()));
        holder.tvAppFirstInstallTimeValue.setText(appInfo.getFirstinstallation());
        holder.tvAppLastInstallTimeValue.setText(appInfo.getLastupdate());
        holder.tvAppMd5SignatureValue.setText(appInfo.getMd5info());
        holder.tvAppSha1SignatureValue.setText(appInfo.getSha1info());
        holder.tvAppSha256SignatureValue.setText(appInfo.getSha256());


        holder.tvAppSha256SignatureValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.copyString(context, holder.tvAppSha256SignatureValue.getText().toString());
                Toast.makeText(context, "SHA256签名 已复制", Toast.LENGTH_SHORT).show();
            }
        });

        holder.tvAppSha1SignatureValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.copyString(context, holder.tvAppSha1SignatureValue.getText().toString());
                Toast.makeText(context, "SHA1签名 已复制", Toast.LENGTH_SHORT).show();

            }
        });
        holder.tvAppMd5SignatureValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.copyString(context, holder.tvAppMd5SignatureValue.getText().toString());
                Toast.makeText(context, "MD5签名 已复制", Toast.LENGTH_SHORT).show();

            }
        });

        holder.tvAppPackageNameValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.copyString(context, holder.tvAppPackageNameValue.getText().toString());
                Toast.makeText(context, "APP包名 已复制", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return appInfoList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvAppNameValue, tvAppPackageNameValue, tvAppVersionNameValue,
                tvAppVersionCodeValue, tvAppFirstInstallTimeValue, tvAppLastInstallTimeValue,
                tvAppMd5SignatureValue, tvAppSha1SignatureValue, tvAppSha256SignatureValue, tvAppPosition;

        public ViewHolder(View view) {
            super(view);

            tvAppPosition = view.findViewById(R.id.tv_app_position);
            tvAppNameValue = view.findViewById(R.id.tv_app_name_value);
            tvAppPackageNameValue = view.findViewById(R.id.tv_app_packagename_value);
            tvAppVersionNameValue = view.findViewById(R.id.tv_app_version_name_value);
            tvAppVersionCodeValue = view.findViewById(R.id.tv_app_version_code_value);
            tvAppFirstInstallTimeValue = view.findViewById(R.id.tv_app_first_install_time_value);
            tvAppLastInstallTimeValue = view.findViewById(R.id.tv_app_last_install_time_value);
            tvAppMd5SignatureValue = view.findViewById(R.id.tv_app_md5_signature_value);
            tvAppSha1SignatureValue = view.findViewById(R.id.tv_app_sha1_signature_value);
            tvAppSha256SignatureValue = view.findViewById(R.id.tv_app_sha256_signature_value);

        }


    }
}
