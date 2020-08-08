package collection.anew.multiutilapp;

/**
 * Created by hussaina on 15-02-2017.
 */

public class ExpenseClass {

    private String exp_name;
    private int exp_amt;
    private String exp_date;
    public ExpenseClass(String exp_name,int exp_amt,String exp_date)
    {
        super();
        this.setExpense_name(exp_name);
        this.setExp_amt(exp_amt);
        this.setExp_date(exp_date);
    }
    public void setExpense_name(String exp_name)
    {
        this.exp_name=exp_name;

    }
    public void setExp_amt(int exp_amt)
    {
        this.exp_amt=exp_amt;

    }
    public void setExp_date(String exp_date)
    {
        this.exp_date=exp_date;
    }

    public String getExpense_name()
    {
        return this.exp_name;

    }
    public String getExp_amt()
    {
        String expamt=""+exp_amt;
        return expamt;

    }
    public String getExp_date()
    {
        return this.exp_date;
    }
}
