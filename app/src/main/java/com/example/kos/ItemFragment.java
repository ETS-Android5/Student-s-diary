package com.example.kos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ItemFragment extends Fragment {
    private ListView lvMain;
    private ArrayList<HashMap<String, String>> products = new ArrayList<>();
    private String ZvonOne, ZvonTwo, NameYrok, NumKab;
    private  HashMap<String,String> map;
    private String url;
    private Context context;
    public ItemFragment(String url) {
        this.url = url;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       final ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_item_pager, container,false);
        Start();
        lvMain = viewGroup.findViewById(R.id.listView);
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), products, R.layout.new_item,
                new String[]{"Times", "Kab"},
                new int[]{R.id.textView1,R.id.textView1_2});
        lvMain.setAdapter(adapter);
        lvMain.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final TextView textView = view.findViewById(R.id.textView1);
                AlertDialog.Builder deleted = new AlertDialog.Builder(getActivity());
                deleted.setCancelable(true).setPositiveButton(context.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuffer stringBuffer = new StringBuffer();

                        try {
                            FileInputStream read = getActivity().openFileInput(url);
                            InputStreamReader reader = new InputStreamReader(read);
                            BufferedReader bufferedReader = new BufferedReader(reader);
                            String temp_read;
                            String[] help ;
                            String delimeter = "=";
                            while ((temp_read = bufferedReader.readLine()) != null) {

                                help = temp_read.split(delimeter);


                                if  (!help[0].equals(textView.getText()))
                                    stringBuffer.append(temp_read).append("\n");
                            }

                            bufferedReader.close();
                            reader.close();
                            read.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }



                        try {
                            FileOutputStream write = getActivity().openFileOutput(url,getActivity().MODE_PRIVATE);

                            write.write(stringBuffer.toString().getBytes());
                            write.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }



                                Start();
                                lvMain = viewGroup.findViewById(R.id.listView);
                        SimpleAdapter adapter = new SimpleAdapter(getActivity(), products, R.layout.new_item,
                                new String[]{"Times", "Kab"},
                                new int[]{R.id.textView1,R.id.textView1_2});
                        lvMain.setAdapter(adapter);





                    }
                })
                        .setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertDialog = deleted.create();
                alertDialog.setTitle(context.getString(R.string.deleteLesson));
                alertDialog.show();
                return true;
            }
        });
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                TextView textViewZvon = view.findViewById(R.id.textView1);
                final TextView textViewKab = view.findViewById(R.id.textView1_2);
                final LayoutInflater li = LayoutInflater.from(getActivity());
                View promptsView = li.inflate(R.layout.prompt , null);
                final AlertDialog.Builder newzvonok = new AlertDialog.Builder(getActivity());
                newzvonok.setView(promptsView);
                final EditText zvonokone = promptsView.findViewById(R.id.timeStart);
                final EditText zvonoktwo = promptsView.findViewById(R.id.timeEnd);
                final TextView textView = promptsView.findViewById(R.id.textView2);
                final EditText Yrok = promptsView.findViewById(R.id.nameYrok);
                final EditText Kab = promptsView.findViewById(R.id.numKab);
                final String[] help,helpop,helpyrok;
                help = textViewZvon.getText().toString().split("-");
                zvonokone.setText(help[0].substring(0,5));
                zvonoktwo.setText(help[1].substring(1));
                helpop = textViewKab.getText().toString().split(",");
                Yrok.setText(helpop[0]);
                helpyrok = helpop[1].split("№");
                Kab.setText(helpyrok[1]);
                final Spinner spinner = promptsView.findViewById(R.id.spinner);
                List<String> choose = new ArrayList<String>();
                if(helpyrok[0].equals(" " + context.getString(R.string.classroomSchool) + " " )) {
                    textView.setText(context.getString(R.string.editLesson));
                    choose.add(context.getString(R.string.classroomSchool));
                    choose.add(context.getString(R.string.classroomUniversity));
                }else{
                    textView.setText(context.getString(R.string.editCouple));
                    choose.add(context.getString(R.string.classroomUniversity));
                    choose.add(context.getString(R.string.classroomSchool));
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>  (getActivity(),R.layout.spinner_list, choose);
                spinner.setAdapter(dataAdapter);
                newzvonok
                        .setCancelable(true)
                        .setPositiveButton(context.getString(R.string.save),
                                new DialogInterface.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.N)
                                    public void onClick(DialogInterface dialog, int id) {
                                        ZvonOne = zvonokone.getText().toString();
                                        ZvonTwo = zvonoktwo.getText().toString();
                                        NameYrok = Yrok.getText().toString();
                                        NumKab = Kab.getText().toString();
                                        if (ZvonOne.length() == 5 && ZvonTwo.length() == 5 && NameYrok.length() > 0 && NumKab.length() > 0){
                                            int ZvonOneOne = 666;
                                            int ZvonOneTwo = 666;
                                            int ZvonTwoOne = 666;
                                            int ZvonTwoTwo = 666;
                                            if (checkString(ZvonOne.substring(0,2)))
                                                ZvonOneOne = Integer.parseInt(ZvonOne.substring(0,2));
                                            if(checkString(ZvonOne.substring(3)))
                                                ZvonOneTwo = Integer.parseInt(ZvonOne.substring(3));
                                            if(checkString(ZvonTwo.substring(0,2)))
                                                ZvonTwoOne = Integer.parseInt(ZvonTwo.substring(0,2));
                                            if(checkString(ZvonTwo.substring(3)))
                                                ZvonTwoTwo = Integer.parseInt(ZvonTwo.substring(3));


                                            if(ZvonOneOne < 25 && ZvonOneTwo < 60 && ZvonOne.charAt(2) == ':' && ZvonTwoOne < 25 && ZvonTwoTwo < 60 && ZvonTwo.charAt(2) == ':') {
                                                if ((ZvonOneOne < ZvonTwoOne) || (ZvonOneOne == ZvonTwoOne && ZvonOneTwo < ZvonTwoTwo)) {
                                                    StringBuffer stringBuffer = new StringBuffer();
                                                    try {
                                                        boolean Zapic = true;

                                                        StringBuffer stringBuffered = new StringBuffer();

                                                        try {
                                                            FileInputStream read = getActivity().openFileInput(url);
                                                            InputStreamReader reader = new InputStreamReader(read);
                                                            BufferedReader bufferedReader = new BufferedReader(reader);
                                                            String temp_read;
                                                            String[] helpip ;
                                                            String delimeter = "=";
                                                            while ((temp_read = bufferedReader.readLine()) != null) {

                                                                helpip = temp_read.split(delimeter);


                                                                if  (!helpip[0].equals(help[0].substring(0,5) + " - " + help[1].substring(1)))
                                                                    stringBuffered.append(temp_read).append("~");
                                                            }

                                                            bufferedReader.close();
                                                            reader.close();
                                                            read.close();
                                                        } catch (FileNotFoundException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }

                                                        if(!stringBuffered.toString().equals("")) {
                                                            String[] mas = stringBuffered.toString().split("~");
                                                            for (int i = 0; i < mas.length; i++) {
                                                                String[] helping;
                                                                helping = mas[i].split("=");
                                                                if ((Integer.parseInt(helping[0].substring(0, 2)) == ZvonOneOne && Integer.parseInt(helping[0].substring(3, 5)) == ZvonOneTwo) || (Integer.parseInt(helping[0].substring(8, 10)) == ZvonTwoOne && Integer.parseInt(helping[0].substring(11)) == ZvonTwoTwo)) {
                                                                    throw new Povtor("Syko blyat", 1);
                                                                }
                                                                if (Integer.parseInt(helping[0].substring(0, 2)) > ZvonOneOne && Zapic) {
                                                                    stringBuffer.append(ZvonOne + " - " + ZvonTwo + "=" + NameYrok + ", " + spinner.getSelectedItem() + " №" + NumKab).append(("\n")).append(mas[i]).append(("\n"));
                                                                    Zapic = false;

                                                                } else
                                                                    stringBuffer.append(mas[i]).append(("\n"));
                                                            }

                                                        }
                                                        if (Zapic)
                                                            stringBuffer.append(ZvonOne + " - " + ZvonTwo + "=" + NameYrok + ", " + spinner.getSelectedItem() + " №" + NumKab);

                                                        try {
                                                            FileOutputStream write =  getActivity().openFileOutput(url, getActivity().MODE_PRIVATE);
                                                            String temp_write = stringBuffer.toString();

                                                            write.write(temp_write.getBytes());
                                                            write.close();
                                                        } catch (FileNotFoundException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }

                                                        Start();
                                                        lvMain = viewGroup.findViewById(R.id.listView);
                                                        SimpleAdapter adapter = new SimpleAdapter(getActivity(), products, R.layout.new_item,
                                                                new String[]{"Times", "Kab"},
                                                                new int[]{R.id.textView1, R.id.textView1_2});
                                                        lvMain.setAdapter(adapter);

                                                    } catch (Povtor povtor) {
                                                        Toast.makeText(getActivity(),context.getString(R.string.timeSpan),Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                                else
                                                    Toast.makeText(getActivity(), context.getString(R.string.timeSpanStartEnd), Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(
                                                        getActivity(), context.getString(R.string.FieldsNot), Toast.LENGTH_SHORT
                                                ).show();
                                            }
                                        }
                                        else {
                                            Toast.makeText(
                                                    getActivity(), context.getString(R.string.wrongFormat), Toast.LENGTH_SHORT
                                            ).show();
                                        }
                                    }
                                });

                //Создаем AlertDialog:
                AlertDialog alertDialog = newzvonok.create();

                //и отображаем его:
                // alertDialog.setTitle("Новый урок");
                alertDialog.show();


            }
        });
        return viewGroup;
    }

    public boolean checkString(String string) {
        try {
            Integer.parseInt(string);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void Start() {
        String[] help ;
        String delimeter = "=";
        products.clear();
        try {
            FileInputStream read = getActivity().openFileInput(url);
            InputStreamReader reader = new InputStreamReader(read);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String temp_read;
            while ((temp_read = bufferedReader.readLine()) != null) {
                help = temp_read.split(delimeter);
                map = new HashMap<>();
                map.put("Times", help[0]);
                map.put("Kab", help[1]);
                products.add(map);

            }
            bufferedReader.close();
            reader.close();
            read.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException ignore) {

        }

    }



}