package Fragments;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guardnet_lite_gabrovo.R;

import java.io.File;
import java.util.ArrayList;

import Settings.Settings;

public class GalleryFragment extends Fragment {
    Settings settings;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        return inflater.inflate(R.layout.gallery_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        settings = new Settings(getContext());
        settings.RestoreLanguage();
        super.onCreate(savedInstanceState);
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(),3);
     //   RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<GalleryList> galleryLists = prepareData();
        GalleryAdapter adapter = new GalleryAdapter(view.getContext(), galleryLists);
        recyclerView.setAdapter(adapter);

//        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(GalleryFragment.this)
//                        .navigate(R.id.action_ViewFragment_to_AddFragment);
//            }
//        });


    }

    private File[] GetItems()
    {
        File f =  new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), getResources().getString(R.string.app_name));
        return f.listFiles();
    }

    private ArrayList<GalleryList> prepareData(){

        ArrayList<GalleryList> theimage = new ArrayList<>();

        File items[] = GetItems();

        if(items!=null)
        {
            for(int i = 0; i< items.length; i++){
                GalleryList galleryList = new GalleryList();
                String path = items[i].toString();
                String title= path.substring(path.lastIndexOf("/")+1);
                galleryList.setImage_title(title);
                galleryList.setImage_ID(path);
                theimage.add(galleryList);
            }
        }

        return theimage;
    }




}