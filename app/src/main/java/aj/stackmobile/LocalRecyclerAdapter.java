package aj.stackmobile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class LocalRecyclerAdapter extends RecyclerView.Adapter<LocalRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<itemsave> is;

    public LocalRecyclerAdapterListener onClickListener;



    public interface LocalRecyclerAdapterListener{
        void onClick(View v,int position,String title,String link);
    }

    public LocalRecyclerAdapter(Context context, List<itemsave> is, LocalRecyclerAdapterListener onClickListener) {
        this.context = context;
        this.is = is;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.questionlocal,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(is.get(position).title);

    }

    @Override
    public int getItemCount() {
        return is.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ConstraintLayout cl;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView3);
            cl = itemView.findViewById(R.id.cl);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(v,getAdapterPosition(),is.get(getAdapterPosition()).title,is.get(getAdapterPosition()).link);
                }
            });
        }
    }


}
