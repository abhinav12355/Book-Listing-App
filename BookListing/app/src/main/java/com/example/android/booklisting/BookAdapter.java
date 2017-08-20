package com.example.android.booklisting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BookAdapter extends ArrayAdapter<BookItemsClass> {

    public BookAdapter(Context context, List<BookItemsClass> books) {
        super(context, 0, books);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_layout, parent, false);
        }

        BookItemsClass books = getItem(position);
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image_book);
        com.squareup.picasso.Picasso.with(getContext()).
                load(books.getimageid()).
                into(imageView);
        TextView author = (TextView) listItemView.findViewById(R.id.book_author);
        author.setText(books.getauthor());
        TextView title = (TextView) listItemView.findViewById(R.id.book_title);
        title.setText(books.gettitle());
        return listItemView;
    }
}
