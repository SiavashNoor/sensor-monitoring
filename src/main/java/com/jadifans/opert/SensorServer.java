package com.jadifans.opert;
import java.net.*;
import java.io.*;

public class SensorServer {

        SensorServer(){

        }
        public  void connectToServer()throws Exception{
            Socket s=new Socket("192.168.40.2",3333);
            DataInputStream dataIn=new DataInputStream(s.getInputStream());
            DataOutputStream dout=new DataOutputStream(s.getOutputStream());
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

            String str="",str2;
            while(!str.equals("stop")){
                str=br.readLine();
                dout.writeUTF(str);
                dout.flush();
                str2=dataIn.readUTF();
                System.out.println("Server says: "+str2);
            }

            dout.close();
            s.close();
        }}



