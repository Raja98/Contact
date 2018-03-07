package com.example.su.contact;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by su on 11/1/18.
 */

public class Contactdetailsadapter extends RecyclerView.Adapter<Contactdetailsadapter.MyViewHolder> {
    Context context;
    ArrayList<Contactdetails>arrayList;
    String fullname;
    public Contactdetailsadapter(Context context, ArrayList<Contactdetails>arrayList){
        this.context=context;
        this.arrayList=arrayList;
        Log.d("adaptetrarrlidy", String.valueOf(arrayList.size()));
    }
    @Override
    public Contactdetailsadapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.design, parent, false));

    }


    @Override
    public void onBindViewHolder(Contactdetailsadapter.MyViewHolder holder, final int position) {
        Log.d("adaptetrarrlidy", String.valueOf(arrayList.size()));
        String name= arrayList.get(position).getName();
        char fist= name.charAt(0);
        int value=name.lastIndexOf(" ");

        char last;


        if (value==-1)
        {

        }
        else
        {
             last=name.charAt(value+1);
            fullname= String.valueOf(fist)+last;
            Log.d("lastname",String.valueOf(last));
        }

        Log.d("fastname", String.valueOf(fist));
           String fname= String.valueOf(fist);
//        fullname= String.valueOf(fist)+last;
//        Log.d("cutoffname", String.valueOf(fullname));

        //String photo=arrayList.get(position).getPhoto();
        Log.d("adaptetrarrlidy", name);
      // Log.d("adaptetrarrlidy", photo);
        holder.tv_name.setText(arrayList.get(position).getName());
        final Contactdetails Contactdetails=arrayList.get(position);
        if (Contactdetails.getPhoto()==null)
        {

            if (value==-1)
            {
                holder.myImageViewText.setVisibility(View.VISIBLE);
                holder.myImageViewText.setText(fname);
                holder.im_image.setVisibility(View.GONE);
            }
            else
            {
                holder.im_image.setVisibility(View.GONE);
                holder.myImageViewText.setVisibility(View.VISIBLE);
                holder.myImageViewText.setText(fullname);
            }


           // holder.im_image.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.myImageViewText.setVisibility(View.GONE);
            holder.im_image.setVisibility(View.VISIBLE);
            Picasso.with(context).load(arrayList.get(position).getPhoto()).into(holder.im_image);
        }
       // Picasso.with(context).load(arrayList.get(position).getPhoto()).into(holder.im_image);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);

        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setLayout((int) (900), (int) (900));

        TextView email1 = (TextView) dialog.findViewById(R.id.email1);
        TextView email2 = (TextView) dialog.findViewById(R.id.email2);
        TextView phone1 = (TextView) dialog.findViewById(R.id.phone);
        TextView phone2 = (TextView) dialog.findViewById(R.id.phone1);

        String email=arrayList.get(position).getEmail();
        if (email==null)
        {
           email1.setText("null");
        }
        else
        {
            email1.setText(arrayList.get(position).getEmail());
        }
        String email0=arrayList.get(position).getEmail1();

        if (email0==null)
        {
          email2.setText("null");
        }
        else
        {
            email2.setText(arrayList.get(position).getEmail1());
        }
        String phone=arrayList.get(position).getPhone();
        String ph=arrayList.get(position).getPhone1();
        if (phone==null)
        {
            phone1.setText("null");
        }
        else
        {
            phone1.setText(arrayList.get(position).getPhone());
        }
        if (ph== null)
        {
          phone2.setText("null");
        }
        else
        {
            phone2.setText(arrayList.get(position).getPhone1());
        }
        holder.li_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(context, "Email:"+arrayList.get(position).getEmail()+"\n"+"Email1:"+arrayList.get(position).getEmail1()+"\n"+"Email:"+arrayList.get(position).getEmail2()+"\n"+"phone:"+arrayList.get(position).getPhone()+"Home:"+arrayList.get(position).getPhone1()+"\n"+"office:"+arrayList.get(position).getPhone2()+"\n", Toast.LENGTH_SHORT).show();

                dialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        CircleImageView im_image;
        RelativeLayout li_list;
        TextView myImageViewText;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name=(TextView)itemView.findViewById(R.id.tv_name);
            myImageViewText=(TextView)itemView.findViewById(R.id.myImageViewText);
            im_image=(CircleImageView) itemView.findViewById(R.id.im_image);
            li_list=(RelativeLayout) itemView.findViewById(R.id.li_list);
        }
    }
}
