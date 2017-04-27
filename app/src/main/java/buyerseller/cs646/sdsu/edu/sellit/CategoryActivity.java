package buyerseller.cs646.sdsu.edu.sellit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class CategoryActivity extends AppCompatActivity{

    private static final String TAG = "CategoryActivity" ;
    private List<String> mCategoryList = new ArrayList<>();
    private ArrayAdapter<String> mArrayAdapter;
    private GridView mGridView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_grid_layout);
        mGridView = (GridView) findViewById(R.id.Category_GridView);

        // get list of categories from firebase
        getCategories();
     mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(CategoryActivity.this, "Category selected !!" + position,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getCategories()
    {
          FirebaseDatabase.getInstance().getReference().child("categories").addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
           Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren().iterator();
              while (dataSnapshots.hasNext()) {
                  DataSnapshot dataSnapshotChild = dataSnapshots.next();
                  String mCategoryName = dataSnapshotChild.child("name").getValue().toString();
                  mCategoryList.add(mCategoryName);
                  mArrayAdapter = new ArrayAdapter<String>(CategoryActivity.this, android.R.layout.simple_list_item_1, mCategoryList);
                  mGridView.setAdapter(mArrayAdapter);
              }
           }
          @Override
          public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(CategoryActivity.this, "failed to bring the data" , Toast.LENGTH_LONG).show();
            }
        });
    }

}