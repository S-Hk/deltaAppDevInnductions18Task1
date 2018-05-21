package com.deltaappdev.inductions18.infinitystonequest;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

public class ListViewAdaptor extends RecyclerView.Adapter<ListViewAdaptor.MyViewHolder> {
    private List<stonesData> mDataList;


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView infinityStoneNameList;
        public ImageView infinityStoneImageList;

        public MyViewHolder(View view){
            super(view);
            infinityStoneNameList = (TextView) view.findViewById(R.id.infinityStoneNameList);
            infinityStoneImageList = (ImageView) view.findViewById(R.id.infinityStoneImageList);
        }
    }

    public ListViewAdaptor(List<stonesData> dataList){
        this.mDataList = dataList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        stonesData data = mDataList.get(position);
        holder.infinityStoneNameList.setText(data.getStoneName());
        holder.infinityStoneImageList.setImageResource(data.getStoneImage());
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}

