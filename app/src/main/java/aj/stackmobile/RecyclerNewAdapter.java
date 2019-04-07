package aj.stackmobile;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerNewAdapter extends RecyclerView.Adapter<RecyclerNewAdapter.ViewHolder> {



    private Context context;
    private ArrayList<item> list;

    public RecyclerNewAdapterListener onClickListener;

    public interface RecyclerNewAdapterListener{
        void onClick(View v,int position,String title,String link);
        void onCheck(View v,int position,item i1);
        void onShare(View v,int position,String title,String link);
    }


    public RecyclerNewAdapter(Context context,ArrayList<item> list, RecyclerNewAdapterListener listener ){
        this.context = context;
        this.list = list;
        onClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclequestion,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(list.get(position).title);
        holder.imageView.setImageResource(android.R.drawable.stat_sys_download);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void addItem(List<item> ls){
        for(item a: ls){
            list.add(a);
        }
        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ConstraintLayout constraintLayout;
        ImageView imageView;
        ImageView imageView2;


        public ViewHolder(final View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.imageView2);
            imageView2 = itemView.findViewById(R.id.imageView4);

            constraintLayout = itemView.findViewById(R.id.rv2);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onCheck(v,getAdapterPosition(),list.get(getAdapterPosition()));
                }
            });

            imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onShare(v,getAdapterPosition(),list.get(getAdapterPosition()).title,list.get(getAdapterPosition()).link);
                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(v,getAdapterPosition(),list.get(getAdapterPosition()).title,list.get(getAdapterPosition()).link);
                }
            });

        }
    }

}