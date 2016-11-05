package shu.dbdealership;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Andrew Shubin on 11/4/16.
 */
@Entity
@Table(name = "inventory")
public class Vehicle implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    private String makeModel;
    private int year;
    private double retailPrice;

    public Vehicle() {
        this("EMPTY", 0, 0);
    }

    public Vehicle(String makeModel, int year, double retailPrice) {
        this.makeModel = makeModel;
        this.year = year;
        this.retailPrice = retailPrice;
    }

    @Override
    public String toString() {
        return this.getId() + ", " + this.makeModel
                + ", Year: " + this.year
                + ", Price: " + this.retailPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMakeModel() {
        return makeModel;
    }

    public void setMakeModel(String makeModel) {
        this.makeModel = makeModel;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }
}
