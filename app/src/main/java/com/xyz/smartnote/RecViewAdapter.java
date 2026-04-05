package com.xyz.smartnote;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.transition.Hold;

import java.util.ArrayList;

public class RecViewAdapter extends RecyclerView.Adapter<RecViewAdapter.ViewHolder> {

    Context context;

    ArrayList<Notes> notes = new ArrayList<>();
    ArrayList<Notes> allNotes;


    public void search(ArrayList<Notes> filteredNotes){

        notes.clear();
        if(filteredNotes.isEmpty()){
            notes.addAll(allNotes);
        } else {
            notes.addAll(filteredNotes);
        }
        notifyDataSetChanged();
    }

    public RecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recview_note_list,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,  int position) {
        holder.txtTitle.setText(notes.get(position).getTitle());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FOR NOTE VIEWING
                //TODO:NOTE EDIT
                Intent intent = new Intent(context, NotesActivity.class);
                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        });

        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.btnDelete.setVisibility(View.VISIBLE);
                return true;
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getBindingAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    Notes note = notes.get(pos);

                    // Remove from data source
                    Utils.getInstance(context).removeNotes(note);

                    // Remove from adapter list
                    notes.remove(pos);
                    allNotes.remove(note);

                    // Notify RecyclerView properly
                    notifyItemRemoved(pos);
                    notifyItemRangeChanged(pos, notes.size());
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(ArrayList<Notes> notes) {
        this.notes = new ArrayList<>(notes);
        allNotes = new ArrayList<>(notes);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private TextView txtNote;

        private TextView txtTitleAN,txtNotesAN,btnDelete;
        private EditText edtTitleAN,edtNotesAN;
        private RelativeLayout relTxtNote,relEdtNote,parent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);

            txtTitle = itemView.findViewById(R.id.txtTitle);

            txtTitleAN = itemView.findViewById(R.id.txtTitleAN);
            txtNotesAN = itemView.findViewById(R.id.txtNotesAN);

            edtTitleAN = itemView.findViewById(R.id.edtTitleAN);
            edtNotesAN = itemView.findViewById(R.id.edtNotesAN);

            relTxtNote = itemView.findViewById(R.id.relTxtNote);
            relEdtNote = itemView.findViewById(R.id.relEdtNote);

            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
