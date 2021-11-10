package dtc.isw.controler;

import java.util.ArrayList;
import java.util.HashMap;

import dtc.isw.dao.CustomerDAO;
import dtc.isw.domain.Customer;

public class CustomerControler {

    public void getCustomer(ArrayList<Customer> lista) {
        CustomerDAO.getClientes(lista);
    }

    public boolean checkCustomer(String user, int column) {
        boolean b = CustomerDAO.checkCustomer(user, column);
        return b;
    }

    public HashMap<String,Object> getColumnCond(String table, String condicion, int column)
    {
        HashMap<String,Object> h = CustomerDAO.getColumnCond(table,condicion,column);
        return h;
    }

    public HashMap<String,Object> getColumn(String table, int column)
    {
        HashMap<String,Object> h = CustomerDAO.getColumn(table,column);
        return h;
    }
}