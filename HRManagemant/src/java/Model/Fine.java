/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Phong Linh
 */
public class Fine {
    private int id;
    private int Fine;
    private String Desc;

    public Fine() {
    }

    public Fine(int id, int Fine, String Desc) {
        this.id = id;
        this.Fine = Fine;
        this.Desc = Desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFine() {
        return Fine;
    }

    public void setFine(int Fine) {
        this.Fine = Fine;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String Desc) {
        this.Desc = Desc;
    }
}
