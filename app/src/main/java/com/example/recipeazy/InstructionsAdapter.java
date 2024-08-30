package com.example.recipeazy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InstructionsAdapter extends RecyclerView.Adapter<InstructionsAdapter.ViewHolder> {

    private List<String> instructions;
    private TextView instruction;

    public InstructionsAdapter(List<String> instructions) {
        this.instructions = instructions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.instruction_tem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String instruction = instructions.get(position);
        int index = position + 1;
        holder.instructionTextView.setText(index + ": " + instruction);
    }

    @Override
    public int getItemCount() {
        return instructions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView instructionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            instructionTextView = itemView.findViewById(R.id.instructionTextView);
        }
    }
}

