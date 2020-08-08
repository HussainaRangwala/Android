package collection.anew.multiutilapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import static collection.anew.multiutilapp.Tab1SchedularCreate.rootView;

/**
 * Created by hussaina on 27-01-2017.
 */

public class Pocket_Expenser_Tab3View extends Fragment {

    private TextView tvPcCategoriesEmpty;
    private TextView tvBcCategoriesEmpty;

    int flag=0;
    ExpenseDatabase ed;
    public PieChart pcCategories;
    public BarChart bcCategories;
    String []date_split;
    int total=0;
    private ExpenseViewDateFragment selectDateFragment;
    public static ArrayList<String> mCategoryList;
    public static Pocket_Expenser_Tab3View newInstance() {
        return new Pocket_Expenser_Tab3View();
    }

    public Pocket_Expenser_Tab3View() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View rv;
    //Button exp_view_btnFwd,exp_view_btnBwd,btn;
    String mon_name1;
    int year1,month1;
    //TextView tvexpviewMonth;


    public void updateUI()
    {
        ExpenseDatabase ed=new ExpenseDatabase(getContext());
        Cursor res=ed.getAllData();
        while (res.moveToNext()) {
            date_split=res.getString(1).split("/");
            String n1=String.valueOf(ExpenseViewDateFragment.monthNumber1(ExpenseViewDateFragment.mon_name1)+1);

            if(date_split[1].equals(n1))
            {
                //Toast.makeText(getContext(),ExpenseViewDateFragment.mon_name1+"  In Activity",Toast.LENGTH_SHORT).show();
                String n2 = res.getString(2).toString();
                for(int j=0;j<mCategoryList.size();j++)
                {
                    if(mCategoryList.get(j).equals(n2))
                    {
                        flag=1;
                        break;
                    }
                }
                if(flag==1)
                {
                    flag=0;
                    continue;
                }
                mCategoryList.add(n2);
            }
        }
        setupCharts();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && isResumed())
        {
            updateUI();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!getUserVisibleHint())
        {
            return;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //View rootView = inflater.inflate(R.layout.pocket_expenser_tab3view, container, false);
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.pocket_expenser_tab3view, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }


       final ExpenseDatabase ed=new ExpenseDatabase(getContext());

        pcCategories = (PieChart) rootView.findViewById(R.id.pc_categories);
        bcCategories = (BarChart) rootView.findViewById(R.id.bc_categories);
        tvPcCategoriesEmpty = (TextView)rootView.findViewById(R.id.tv_bar_chart_category_empty);
        tvBcCategoriesEmpty = (TextView)rootView.findViewById(R.id.tv_pie_categories_chart_empty);
        mCategoryList=new ArrayList<String>();
        selectDateFragment = (ExpenseViewDateFragment) getChildFragmentManager().findFragmentById(R.id.select_date_fragment);
        //selectDateFragment.setSelectDateFragment(this);

        ExpenseViewDateFragment.exp_view_btnFwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=0;
                String n1;
                mCategoryList=new ArrayList<String>();
                ExpenseViewDateFragment.month1=ExpenseViewDateFragment.monthNumber1(ExpenseViewDateFragment.mon_name1);
                if((ExpenseViewDateFragment.month1+1)==12) {
                    ExpenseViewDateFragment.month1 = -1;
                    ExpenseViewDateFragment.year1=ExpenseViewDateFragment.year1+1;
                }
                ExpenseViewDateFragment.tvexpviewMonth.setText(ExpenseViewDateFragment.theMonth1(ExpenseViewDateFragment.month1+1)+"-"+ExpenseViewDateFragment.year1);
                ExpenseViewDateFragment.mon_name1=ExpenseViewDateFragment.theMonth1(ExpenseViewDateFragment.month1+1);
                updateUI();
            }
        });

        ExpenseViewDateFragment.exp_view_btnBwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n1;
                flag=0;
                mCategoryList=new ArrayList<String>();
                ExpenseViewDateFragment.month1=ExpenseViewDateFragment.monthNumber1(ExpenseViewDateFragment.mon_name1);
                if((ExpenseViewDateFragment.month1-1)==-1) {
                    ExpenseViewDateFragment.month1 = 12;
                    ExpenseViewDateFragment.year1=ExpenseViewDateFragment.year1-1;
                }
                ExpenseViewDateFragment.tvexpviewMonth.setText(ExpenseViewDateFragment.theMonth1(ExpenseViewDateFragment.month1-1)+"-"+ExpenseViewDateFragment.year1);

                ExpenseViewDateFragment.mon_name1=ExpenseViewDateFragment.theMonth1(ExpenseViewDateFragment.month1-1);
                updateUI();
            }
        });

        return rootView;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

       String date_split[];
        flag=0;
        //Toast.makeText(getContext(),"In Tab3view",Toast.LENGTH_SHORT).show();
        Log.i("In Poc_Exp_Tab3View","");
        updateUI();
    }
    private void setupCharts() {

        // set up pie chart
        pcCategories.setCenterText("");
        pcCategories.setCenterTextSize(10f);
        pcCategories.setHoleRadius(50f);
        pcCategories.setTransparentCircleRadius(55f);
        pcCategories.setUsePercentValues(true);
        pcCategories.setDescription("");
        pcCategories.setNoDataText("");

        Legend l = pcCategories.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);
        pcCategories.animateY(1500, Easing.EasingOption.EaseInOutQuad);
        updateData();
    }


    public void updateData() {
        // Bar Chart
        bcCategories.setDescription("");
        bcCategories.setNoDataText("");
        bcCategories.animateY(2000);
        bcCategories.setVisibleXRangeMaximum(5);
        bcCategories.getAxisLeft().setDrawGridLines(false);
        bcCategories.getXAxis().setDrawGridLines(false);
        bcCategories.getAxisRight().setDrawGridLines(false);
        bcCategories.getAxisRight().setDrawLabels(false);

        // Restarting chart views
        bcCategories.notifyDataSetChanged();
        bcCategories.invalidate();
        pcCategories.notifyDataSetChanged();
        pcCategories.invalidate();
        setCategoriesBarChart();
        setCategoriesPieChart();
    }

    int getCategoryTotalByDate(String catName)
    {
        ExpenseDatabase ed=new ExpenseDatabase(getContext());
        Cursor res=ed.getAllData();
        total=0;
        while(res.moveToNext())
        {
            date_split=res.getString(1).split("/");
            String cat_name=res.getString(2).toString();
            String n3=String.valueOf(ExpenseViewDateFragment.monthNumber1(ExpenseViewDateFragment.mon_name1)+1);
            if(date_split[1].equals(n3) && catName.equals(cat_name))
            {
                String n1=res.getString(3).toString();
                total+=Integer.parseInt(n1);

            }
        }

        return total;
    }

    public static List<Integer> getListColors(){
        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        return colors;
    }
    private void setCategoriesBarChart() {
        List<String> categoriesNames = new ArrayList<>();
        List<BarEntry> entryPerCategory = new ArrayList<>();

        for (int i=0; i < mCategoryList.size(); i++) {
            float value = getCategoryTotalByDate(mCategoryList.get(i));
            if (value > 0) {
                categoriesNames.add(mCategoryList.get(i));
                entryPerCategory.add(new BarEntry(value, categoriesNames.size()-1));
            }
        }
        if (categoriesNames.isEmpty()) {
            tvBcCategoriesEmpty.setVisibility(View.VISIBLE);
            bcCategories.setVisibility(View.GONE);
        } else {
            tvBcCategoriesEmpty.setVisibility(View.GONE);
            bcCategories.setVisibility(View.VISIBLE);
        }
        BarDataSet dataSet = new BarDataSet(entryPerCategory, getString(R.string.categories));
        dataSet.setColors(getListColors());
        BarData barData = new BarData(categoriesNames, dataSet);
        bcCategories.setData(barData);
        bcCategories.invalidate();
    }
    private void setCategoriesPieChart() {

        List<String> categoriesNames = new ArrayList<>();
        List<Entry> categoryPercentagesEntries = new ArrayList<>();

        for (int i=0; i < mCategoryList.size(); i++) {
            float percentage =getCategoryTotalByDate(mCategoryList.get(i));
            if( percentage > 0) {
                categoriesNames.add(mCategoryList.get(i).toString());
                Entry pieEntry = new Entry(percentage, categoriesNames.size()-1);
                categoryPercentagesEntries.add(pieEntry);
            }
        }
        if (categoriesNames.isEmpty()) {
            tvPcCategoriesEmpty.setVisibility(View.VISIBLE);
            bcCategories.setVisibility(View.GONE);
        } else {
            tvPcCategoriesEmpty.setVisibility(View.GONE);
            bcCategories.setVisibility(View.VISIBLE);
        }

        PieDataSet dataSet = new PieDataSet(categoryPercentagesEntries, "Categories");
        dataSet.setSliceSpace(1f);
        dataSet.setSelectionShift(5f);

        dataSet.setColors(getListColors());

        PieData data = new PieData(categoriesNames, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(getResources().getColor(R.color.colorPrimaryDark));
        pcCategories.setData(data);
        pcCategories.invalidate();

    }


}
