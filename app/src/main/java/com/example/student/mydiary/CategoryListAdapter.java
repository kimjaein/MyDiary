package com.example.student.mydiary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by student on 2018-01-19.
 */

public class CategoryListAdapter  extends ArrayAdapter<String> {
    private Context context;        // MainActiviy.this
    private int itemLayout;         // R.layout.my_item
    private List<String> categoryList;  // data 집합

    public CategoryListAdapter(@NonNull Context context, int resource, List<String> objects) {
        super(context,resource,objects);
        this.context = context;
        this.itemLayout = resource;
        this.categoryList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final MyItemHolder holder ;

        if(convertView == null) {
            Log.i("convert test", "null");
            // getView 메소드 처음 실행 시 inflate 함.
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // my_item.xml 파일 팽창해서 객체화.false
            convertView = inflater.inflate(itemLayout, parent, false);

            holder = new MyItemHolder();
            holder.category = convertView.findViewById(android.R.id.text1);

            // inflate결과 홀더에 기억시키기
            convertView.setTag(holder);
        }else{
            Log.i("convert test", "not null");
            // getView 메소드 첫실행 아닌경우 이전 inflate 재활용
            holder = (MyItemHolder)convertView.getTag();
        }

        holder.category.setText(categoryList.get(position));

        return convertView;
    }

    class MyItemHolder{
        TextView category;
    }
}
