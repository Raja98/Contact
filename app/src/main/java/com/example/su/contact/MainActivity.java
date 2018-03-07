package com.example.su.contact;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tv_contact;
    RecyclerView rc_contactlist;
    ArrayList<Contactdetails> arrayList = null;
    String photouri = ContactsContract.CommonDataKinds.Phone.PHOTO_URI;
    Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
    String id, name, phoneNumber, email, image_uri,p1,p2,m1,m2;
    Contactdetailsadapter contactdetailsadapter;
    int MY_PERMISSIONS_REQUEST_READ_CONTACTS;
    ContentResolver cr;
    List<Contact> contactList;
    boolean flag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_contact = (TextView) findViewById(R.id.tv_contact);
        rc_contactlist = (RecyclerView) findViewById(R.id.rc_contactlist);
        cr = this.getContentResolver();
        arrayList = new ArrayList<>();
        contactList = new ArrayList<Contact>();
        rc_contactlist.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        tv_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        ArrayList<phone> vehicles = new ArrayList<phone>();
        ArrayList<Email> mail = new ArrayList<Email>();
        switch (reqCode) {
            case (1):
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.READ_CONTACTS},
                                MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                    } else {


                        Cursor c = managedQuery(contactData, null, null, null, null);

                        if (c.moveToFirst()) {
                            id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                            name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                        Log.d("name", name);
                        Log.d("id",id);
                     //   Log.d("email", email);

                            if (Integer.parseInt(c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                                while (phones.moveToNext()) {

                                    phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    phone ob = new phone();
                                    ob.setPhone(phoneNumber);
                                    vehicles.add(ob);
                                    Log.e("Number", phoneNumber);
                                    Log.d("phone arry", String.valueOf(vehicles.size()));
                                }
                                phones.close();
                            }

                            Cursor emailCur = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                            while (emailCur.moveToNext()) {
                                Email ob= new Email();
                                email = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                                ob.setEmail(email);
                                mail.add(ob);
                                 Log.e("Email",name+" "+email);
                            }
                            emailCur.close();

                            Cursor imageCursor = getContentResolver().query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[]{id}, null);
                            while (imageCursor.moveToNext()) {
                                image_uri = imageCursor.getString(imageCursor.getColumnIndex(photouri));
                            }
                            imageCursor.close();
                        }




                        Log.d("name1", name);
                        Log.d("id1", id);
                        Log.e("Number1", phoneNumber);
                        Log.d("phone arry", String.valueOf(vehicles.size()));
                        Log.e("Email1", name + " " + email);
                        System.out.println("imageuri" + image_uri);
                        for (int j=1;j<vehicles.size();j++)
                        {
                             p1=vehicles.get(j).getPhone();
                             p2=vehicles.get(j).getPhone();
                            Log.d("mynum",p1);
                            Log.d("mynum",p2);
                        }
                        for (int k=1;k<mail.size();k++)
                        {
                            m1=mail.get(k).getEmail();
                            m2=mail.get(k).getEmail();
                            Log.d("myn",m1);
                            Log.d("myn",m2);
                        }

                        if (arrayList.size()==0) {
                            Log.d("add 1st time","added");
                            Contactdetails ob = new Contactdetails();
                            ob.setId(id);
                            ob.setName(name);
                            ob.setEmail(email);
                            ob.setPhone(phoneNumber);
                            ob.setPhone1(p1);
                            ob.setPhone2(p2);
                            ob.setEmail1(m1);
                            ob.setEmail2(m2);
                            ob.setPhoto(image_uri);
                            arrayList.add(ob);
                            Log.d("arrlistmyval", String.valueOf(arrayList.size()));
                            contactdetailsadapter = new Contactdetailsadapter(MainActivity.this, arrayList);
                            rc_contactlist.setAdapter(contactdetailsadapter);
                        } else {
                            Log.d("arrlist", String.valueOf(arrayList.size()));

                            for (int i=0;i<arrayList.size();i++) {
                              if (arrayList.get(i).getId().equals(id))
                              {
                                  flag=false;
                              }
                            }
                                if (flag==false) {
                                    Toast.makeText(this, "user already added", Toast.LENGTH_SHORT).show();
                                    flag=true;
                                }
                                else
                                {
                              Contactdetails ob = new Contactdetails();
                                    ob.setId(id);
                                    ob.setName(name);
                                    ob.setEmail(email);
                                    ob.setPhone(phoneNumber);
                                    ob.setPhone1(p1);
                                    ob.setPhone2(p2);
                                    ob.setPhoto(image_uri);
                                    arrayList.add(ob);
                                    flag=true;
                                    contactdetailsadapter.notifyDataSetChanged();

                                }
                        }
                    }
                    break;
                }
        }
    }
}


