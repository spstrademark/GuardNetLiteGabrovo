package Fragments;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guardnet_lite_gabrovo.R;

import java.io.File;
import java.util.ArrayList;

import Common.FragmentsEnum;
import Common.Settings;
import GalleryTools.GalleryAdapter;
import GalleryTools.GalleryList;

public class GalleryFragment extends Fragment {
    Settings settings;
    RecyclerView recyclerView;

    private static final int GRID = 3;
    private static final int LIST = 1;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        return inflater.inflate(R.layout.gallery_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        settings = new Settings(getContext(), FragmentsEnum.GALLERY.ordinal());
        super.onViewCreated(view, savedInstanceState);
        InitRecycleView(view);
        ButtonEvents(view);
    }

    private File[] GetItems()
    {
        File f =  new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), getResources().getString(R.string.app_name));
        return f.listFiles();
    }

    private void InitRecycleView(@NonNull View view)
    {
        recyclerView = (RecyclerView)view.findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(),settings.GetGalleryView());
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<GalleryList> galleryLists = prepareData();
        if(galleryLists!=null){
            view.findViewById(R.id.EmptyGallery).setVisibility(View.INVISIBLE);
            GalleryAdapter adapter = new GalleryAdapter(view.getContext(), galleryLists);
            recyclerView.setHasFixedSize(false);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(adapter);
        }

    }

    private ArrayList<GalleryList> prepareData(){

        ArrayList<GalleryList> theimage = new ArrayList<>();
        File items[] = GetItems();

        if(items!=null && items.length>0)
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

    private void ButtonEvents(@NonNull View view)
    {
        view.findViewById(R.id.GridView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(),GRID);
                recyclerView.setLayoutManager(layoutManager);
                settings.SaveGalleryView(GRID);
            }
        });

        view.findViewById(R.id.ListView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(),LIST);
                recyclerView.setLayoutManager(layoutManager);
                settings.SaveGalleryView(LIST);
            }
        });

        view.findViewById(R.id.gallery_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                NavHostFragment.findNavController(GalleryFragment.this)
//                        .navigate(R.id.action_GalleryFragment_to_ViewFragment);
            }
        });
    }

}