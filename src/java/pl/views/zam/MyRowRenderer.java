/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.views.zam;


import org.zkoss.zul.*;

 
public class MyRowRenderer implements ListitemRenderer{
    public void render(Listitem listitem, Object data, int index) {
        Listcell cell = new Listcell();
        listitem.appendChild(cell);
        if (data instanceof String[]){
            cell.appendChild(new Label(((String[])data)[0].toString()));
        } else if (data instanceof String){
            cell.appendChild(new Label(data.toString()));
        } else {
            cell.appendChild(new Label("UNKNOW:"+data.toString()));
        }
    }
}