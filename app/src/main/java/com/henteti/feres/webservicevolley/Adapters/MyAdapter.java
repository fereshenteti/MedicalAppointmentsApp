package com.henteti.feres.webservicevolley.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.henteti.feres.webservicevolley.Models.Patient;
import com.henteti.feres.webservicevolley.Models.Rdv;
import com.henteti.feres.webservicevolley.R;

import java.util.List;

/**
 * Created by Feres on 12/14/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder> {



    public static class MyAdapterViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView patientName;
        TextView functionAge;
        TextView timing;

        MyAdapterViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            patientName = (TextView)itemView.findViewById(R.id.patientName);
            functionAge = (TextView)itemView.findViewById(R.id.function_age);
            timing = (TextView)itemView.findViewById(R.id.timing);
        }
    }




    List<Rdv> Rdvs;


    public MyAdapter(List<Rdv> Rdvs){
        this.Rdvs = Rdvs;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public MyAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        MyAdapterViewHolder pvh = new MyAdapterViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(MyAdapterViewHolder vh, int i) {
        vh.patientName.setText(Rdvs.get(i).getPatient().getPrenom() + " " +Rdvs.get(i).getPatient().getNom());
        vh.functionAge.setText(Rdvs.get(i).getPatient().getAge()+" ans, "+Rdvs.get(i).getPatient().getFonction());
        vh.timing.setText(Rdvs.get(i).getTrancheHeure());
    }

    @Override
    public int getItemCount() {
        return Rdvs.size();
    }
}