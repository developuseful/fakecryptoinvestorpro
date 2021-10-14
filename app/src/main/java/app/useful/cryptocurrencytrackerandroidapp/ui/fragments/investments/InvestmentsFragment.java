package app.useful.cryptocurrencytrackerandroidapp.ui.fragments.investments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import app.useful.cryptocurrencytrackerandroidapp.R;
import app.useful.cryptocurrencytrackerandroidapp.adapters.investmentsadapter.InvestmentAdapter;
import app.useful.cryptocurrencytrackerandroidapp.room.AddInvestmentViewModel;
import app.useful.cryptocurrencytrackerandroidapp.room.Investment;
import app.useful.cryptocurrencytrackerandroidapp.ui.fragments.investdetail.InvestDetailFragmentViewModel;


public class InvestmentsFragment extends Fragment {

    private AddInvestmentViewModel addInvestmentViewModel;
    private RecyclerView recyclerView;

    private InvestDetailFragmentViewModel investDetailFragmentViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_investments, container, false);

        initFunc(root);

        return root;
    }

    private void initFunc(View root) {



        final InvestmentAdapter adapter = new InvestmentAdapter(root.getContext());


        recyclerView = root.findViewById(R.id.rvInvestments);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(adapter);

        addInvestmentViewModel = ViewModelProviders.of(this).get(AddInvestmentViewModel.class);
        addInvestmentViewModel.getAllInvest().observe(getViewLifecycleOwner(), new Observer<List<Investment>>() {
            @Override
            public void onChanged(List<Investment> investments) {
                adapter.setInvestments(investments);

            }
        });


        investDetailFragmentViewModel = ViewModelProviders.of(this).get(InvestDetailFragmentViewModel.class);
        investDetailFragmentViewModel.getCourse().observe(getViewLifecycleOwner(), new Observer<HashMap<String, Double>>() {
            @Override
            public void onChanged(HashMap<String, Double> stringDoubleHashMap) {

            }
        });



        adapter.setOnItemClickListener(new InvestmentAdapter.OnItemClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onItemClick(Investment investment) {
                /*
                Intent intent = new Intent(getActivity(), AddInvestActivity.class);
                intent.putExtra("coin_name", investment.getInvestCoinName());
                intent.putExtra("symbol", investment.getInvestSymbol());
                intent.putExtra("invest_course", investment.getInvestCourse());
                startActivity(intent);

                FragmentManager manager=((AppCompatActivity)root.getContext()).getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                ft.replace(R.id.nav_host_fragment_activity_main, new InvestDetailFragment());

                ft.commit();
                try {
                    InvestmentsFragment.this.finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

                 */

                Bundle bundle = new Bundle();
                bundle.putString("coinSymbol", investment.getInvestSymbol());
                bundle.putString("coinName", investment.getInvestCoinName());
                bundle.putString("investName", investment.getInvestName());
                bundle.putLong("investDate", investment.getInvestDate());
                bundle.putDouble("investCourse", investment.getInvestCourse());
                bundle.putInt("investAmount", investment.getInvestAmount());

                Navigation.findNavController(root).navigate(R.id.investDetailFragment, bundle);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                addInvestmentViewModel.delete(adapter.getInvestmentAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }
}