package com.example.jinhwan.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    boolean flag=true;
    ImageView menuView;
    TextView tvBottom;
    EditText editTime;
    myTask task ;
    int second= 0;
    int menu []= {R.drawable.chicken,R.drawable.ddeok,R.drawable.hamburger,R.drawable.kimbab,R.drawable.pasta,R.drawable.pizza};
    String[] menuName = {"치킨","떡볶이","햄버거","김밥","파스타","피자"};
    int time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }
    public void init(){
        menuView=(ImageView)findViewById(R.id.menu);
        editTime=(EditText)findViewById(R.id.edit);
        tvBottom=(TextView)findViewById(R.id.tvTime);

    }
    public void onClick(View v){
        if(v.getId() == R.id.menu){
            if(flag) {
                System.out.println("start");
                task = new myTask();
                task.execute(0);
                flag = false;
            }else
                task.cancel(true);
        }else if(v.getId() == R.id.button){
            menuView.setImageResource(R.mipmap.ic_launcher);
            editTime.setText("");
            tvBottom.setVisibility(View.INVISIBLE);
            flag=true;

        }

    }

    class myTask extends AsyncTask<Integer,Integer,Boolean>{

        @Override
        protected void onPreExecute() {
            menuView.setImageResource(menu[0]);
            tvBottom.setVisibility(View.VISIBLE);
            second=0;
            time = Integer.parseInt(editTime.getText().toString());
            tvBottom.setText("시작부터"+ second +"초");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tvBottom.setText("시작부터 "+values[0]+"초");
            menuView.setImageResource(menu[values[1]]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

        }

        int i=0;
        @Override
        protected Boolean doInBackground(Integer... params) {
            while(true){
                if(isCancelled()==true){
                    return null;
                }
                second++;
                try {
                    Thread.sleep(1000);
                    if(second%time==0){
                        publishProgress(second,i);
                        i=(i+1)%6;
                    }
                    publishProgress(second,i);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onCancelled() {
            tvBottom.setText(menuName[i]+"선택"+ "("+(second-1)+"초)");
            super.onCancelled();
        }
    }
}
