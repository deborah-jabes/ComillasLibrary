package dtc.isw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import dtc.isw.domain.Customer;

public class CustomerDAO {



    public static void getClientes(ArrayList<Customer> lista) {
        Connection con=ConnectionDAO.getInstance().getConnection();
        try (PreparedStatement pst = con.prepareStatement("SELECT * FROM prueba");
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                lista.add(new Customer(rs.getString(0),rs.getString(1)));
            }

        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {


        ArrayList<Customer> lista=new ArrayList<Customer>();
        CustomerDAO.getClientes(lista);


        for (Customer customer : lista) {
            System.out.println("He leído el id: "+customer.getUser()+" con contrasenya: "+customer.getPassword());
        }


    }

    public static boolean checkCustomer(String user, int column) {
        Connection con = ConnectionDAO.getInstance().getConnection();
        try (PreparedStatement pst = con.prepareStatement("SELECT * FROM listausuarios");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                if (user.equals(rs.getString(column))) {
                    System.out.println("Encontrado");
                    return true;
                }
                /*else {
                    System.out.println("NO encontrado");
                    return false;
                }*/
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("NO encontrado");
        return false;
    }

    public static HashMap<String,Object> getColumnCond(String table, String condicion, int column)
    {
        Connection con = ConnectionDAO.getInstance().getConnection();
        HashMap<String,Object> res = new HashMap<String,Object>();
        Integer i = 0;
        try (PreparedStatement pst = con.prepareStatement("SELECT * FROM " + table + " WHERE " + condicion);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                res.put(i.toString(),rs.getString(column));
                i +=1 ;
            }
            return res;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Tabla NO encontrado");
        return res;


    }

    public static HashMap<String,Object> getColumn(String table, int column)
    {
        Connection con = ConnectionDAO.getInstance().getConnection();
        HashMap<String,Object> res = new HashMap<String,Object>();
        Integer i = 0;
        try (PreparedStatement pst = con.prepareStatement("SELECT * FROM " + table);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                res.put(i.toString(),rs.getString(column));
                i +=1 ;
            }
            return res;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Tabla NO encontrado");
        return res;
    }

}