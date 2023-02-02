package com.example.ui;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model_crawler.diadiem.DiaDiem;
import model_crawler.lehoi.LeHoi;
import model_crawler.nhanvat.NhanVat;
import model_crawler.sukien.SuKien;
import model_crawler.thoiky.ThoiKy;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ReadDataFromJson {
    public static ArrayList<LeHoi> readLeHoiData(){
        Gson gson = new Gson();
        ArrayList<LeHoi> listLeHoi = new ArrayList<>();
        try {
            FileReader reader = new FileReader("src/main/java/jsondata/LeHoi.json");
            Type type = new TypeToken<ArrayList<LeHoi>>(){}.getType();
            listLeHoi = gson.fromJson(reader, type);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return listLeHoi;
    }

    public static ArrayList<NhanVat> readNhanVatData(){
        Gson gson = new Gson();
        ArrayList<NhanVat> listNhanVat = new ArrayList<>();
        try {
            FileReader reader = new FileReader("src/main/java/jsondata/NhanVat.json");
            Type type = new TypeToken<ArrayList<NhanVat>>(){}.getType();
            listNhanVat = gson.fromJson(reader, type);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return listNhanVat;
    }

    public static ArrayList<SuKien> readSuKienData(){
        Gson gson = new Gson();
        ArrayList<SuKien> listSuKien = new ArrayList<>();
        try {
            FileReader reader = new FileReader("src/main/java/jsondata/SuKien.json");
            Type type = new TypeToken<ArrayList<SuKien>>(){}.getType();
            listSuKien = gson.fromJson(reader, type);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return listSuKien;
    }
    public static ArrayList<ThoiKy> readThoiKyData(){
        Gson gson = new Gson();
        ArrayList<ThoiKy> listThoiKy = new ArrayList<>();
        try {
            FileReader reader = new FileReader("src/main/java/jsondata/ThoiKy.json");
            Type type = new TypeToken<ArrayList<ThoiKy>>(){}.getType();
            listThoiKy = gson.fromJson(reader, type);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return listThoiKy;
    }

    public static ArrayList<DiaDiem> readDiaDiemData(){
        Gson gson = new Gson();
        ArrayList<DiaDiem> listDiaDiem = new ArrayList<>();
        try {
            FileReader reader = new FileReader("src/main/java/jsondata/DiaDiem.json");
            Type type = new TypeToken<ArrayList<DiaDiem>>(){}.getType();
            listDiaDiem = gson.fromJson(reader, type);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return listDiaDiem;
    }

    public static void main(String[] args) {
        System.out.println("Test DiaDiem");
        ArrayList<DiaDiem> listDiaDiem = ReadDataFromJson.readDiaDiemData();
        for (DiaDiem p : listDiaDiem){
            System.out.println(p.getTen());
        }
        System.out.println("Test LeHoi");
        ArrayList<LeHoi> listLeHoi = ReadDataFromJson.readLeHoiData();
        for (LeHoi p : listLeHoi){
            System.out.println(p.getTenLeHoi());
        }
        System.out.println("Test NhanVat");
        ArrayList<NhanVat> listNhanVat = ReadDataFromJson.readNhanVatData();
        for (NhanVat p : listNhanVat){
            System.out.println(p.getTen());
        }
        System.out.println("Test SuKien");
        ArrayList<SuKien> listSuKien = ReadDataFromJson.readSuKienData();
        for (SuKien p : listSuKien){
            System.out.println(p.getTenSuKien());
        }
        System.out.println("Test ThoiKy");
        ArrayList<ThoiKy> listThoiKy = ReadDataFromJson.readThoiKyData();
        for (ThoiKy p : listThoiKy){
            System.out.println(p.getTenTrieuDai() + " - " + p.getThoiKy());
        }
    }
}
