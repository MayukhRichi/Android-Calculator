package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

class node {
    String num;
    node next;
}
class linked{
    private node head;
    void stackPush(String num) {
        node now = head;
        if (now != null) {
            while (now.next != null) {
                now = now.next;
            }
            now.next = new node();
            now = now.next;
            now.num=num;
            now.next = null;
        } else {
            head = new node();
            head.num=num;
            head.next = null;
        }
    }
    void queuePush(String num) {
        node now = head;
        head = new node();
        head.num=num;
        head.next = now;
    }
    String stackPop() {
        node now = head;
        String k;
        if (now == null) {
            return "#";
        } else if (now.next == null) {
            k=now.num;
            head = null;
            return k;
        } else {
            while (now.next.next != null) {
                now = now.next;
            }
            k=now.next.num;
            now.next = null;
            return k;
        }
    }
    String queuePop(){
        if (head == null) {
            return "#";
        } else {
            String k = head.num;
            head = head.next;
            return k;
        }
    }
    void reset(){
        head=null;
    }
}
class calc {
    static boolean getNum(String c){
        try{
            Integer.parseInt(c);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    static boolean formatInfix(linked infix, String c, boolean attach, boolean closeBracket){
        if (c.equals("L")||c.equals("F")||c.equals("S")||c.equals("C")||c.equals("T")||c.equals("s")||c.equals("c")||c.equals("t")||c.equals("sec")||c.equals("cot")||c.equals("cosec")||c.equals("(")||c.equals("{")||c.equals("[")){
            if(!attach){return true;}
            else{
                infix.queuePush(c);
                infix.queuePush("*");
                return false;
            }
        } else if (c.equals("+")||c.equals("-")){
            if(attach||closeBracket){return true;}
            else{
                if (c.equals("-")){
                    String i=infix.queuePop();
                    if(i.equals("+")){infix.queuePush("-");}
                    else if (!i.equals("-")){infix.queuePush(Integer.toString(-Integer.parseInt(i)));}
                }
                return false;
            }
        } else if (c.equals(".")){
            if(attach){return true;}
            else{
                infix.queuePush(c);
                infix.queuePush("0");
                return false;
            }
        } else if (c.equals("^")||c.equals("*")||c.equals("/")){
            if (attach||closeBracket) return true;
            else {
                infix.queuePush("###");
                return false;
            }
        } else if (c.equals(")")||c.equals("}")||c.equals("]")){return true;}
        infix.queuePush("###");
        return false;
    }
    static int getPriority(String c){
        //in case of if; == tests for reference of classes also
        //but equals() check only the value & here better is switch
        switch (c) {
            case "+":
            case "-":
                return 1;
            case "*":
                return 2;
            case "/":
                return 3;
            case"^":
                return 4;
            case"F"://factorial
            case"S"://sin
            case"C"://cos
            case"T"://tan
            case"s"://asin
            case"c"://acos
            case"t"://atan
            case"L"://ln or log
            case"cosec":
            case"sec":
            case"cot":
                return 5;
            case ".":
                return 6;
            default:
                return 0;
        }
    }
    static void process(linked bin, String c){
        double a,b;
        switch (c) {
            case "+":
                b = Double.parseDouble(bin.stackPop());
                a = Double.parseDouble(bin.stackPop());
                bin.stackPush(Double.toString(a + b));
                break;
            case "-":
                b = Double.parseDouble(bin.stackPop());
                a = Double.parseDouble(bin.stackPop());
                bin.stackPush(Double.toString(a - b));
                break;
            case "*":
                b = Double.parseDouble(bin.stackPop());
                a = Double.parseDouble(bin.stackPop());
                bin.stackPush(Double.toString(a * b));
                break;
            case "/":
                b = Double.parseDouble(bin.stackPop());
                a = Double.parseDouble(bin.stackPop());
                bin.stackPush(Double.toString(a / b));
                break;
            case "^":
                b = Double.parseDouble(bin.stackPop());
                a = Double.parseDouble(bin.stackPop());
                bin.stackPush(Double.toString(Math.pow(a,b)));
                break;
            case "F":
                a = Double.parseDouble(bin.stackPop());
                int x = (int) a;
                if (x<0||(x-a)!=0){bin.stackPush("#");}
                else{
                    int ans=1;
                    for (int i = x; i > 0; i--) {
                        ans*=i;
                    }
                    bin.stackPush(Integer.toString(ans));
                }
                break;
            case "S":
                a = Double.parseDouble(bin.stackPop());
                bin.stackPush(Double.toString(Math.sin(a)));
                break;
            case "C":
                a = Double.parseDouble(bin.stackPop());
                bin.stackPush(Double.toString(Math.cos(a)));
                break;
            case "T":
                a = Double.parseDouble(bin.stackPop());
                bin.stackPush(Double.toString(Math.tan(a)));
                break;
            case "s":
                a = Double.parseDouble(bin.stackPop());
                bin.stackPush(Double.toString(Math.asin(a)));
                break;
            case "c":
                a = Double.parseDouble(bin.stackPop());
                bin.stackPush(Double.toString(Math.acos(a)));
                break;
            case "t":
                a = Double.parseDouble(bin.stackPop());
                bin.stackPush(Double.toString(Math.atan(a)));
                break;
            case "cosec":
                a = Double.parseDouble(bin.stackPop());
                bin.stackPush(Double.toString(1/Math.sin(a)));
                break;
            case "sec":
                a = Double.parseDouble(bin.stackPop());
                bin.stackPush(Double.toString(1/Math.cos(a)));
                break;
            case "cot":
                a = Double.parseDouble(bin.stackPop());
                bin.stackPush(Double.toString(1/Math.tan(a)));
                break;
            case "L":
                a = Double.parseDouble(bin.stackPop());
                bin.stackPush(Double.toString(Math.log(a)));
                break;
            case ".":
                b = Double.parseDouble(bin.stackPop());
                while(b>9){b/=10;}
                b--;//As we have added extra 1 to maintain 0.002 as 0.1002
                a = Double.parseDouble(bin.stackPop());
                bin.stackPush(Double.toString(a + b));
                break;
        }
    }
}

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button btn1 = (Button) findViewById(R.id.equalsTo);
        ImageButton hlp = (ImageButton) findViewById(R.id.hlp);
        //  help operation
        hlp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this,Main3Activity.class));
            }
        });
        //  equalsTo button operation
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText problem = (EditText) findViewById(R.id.editText1);
                TextView answer = (TextView) findViewById(R.id.textView);
                linked infix=new linked();
                linked postfix=new linked();
                linked bin=new linked();
                boolean attach=false;
                boolean error=false;
                boolean closeBracket=false;
                boolean detectedUniversalConstant=false;
                String c;
                String k;
                int priority_min=0;
                int priority_now;
                double ans;
                //  I N P U T a s L I N K E D L I S T
                String user=problem.getText().toString();
                infix.stackPush("(");
                int i = 0;
                while(i<user.length()){
                    try{
                        if ((user.charAt(i)=='c' && user.charAt(i+1)=='o' && user.charAt(i+2)=='s'&& user.charAt(i+3)=='e'&& user.charAt(i+4)=='c')){
                            infix.stackPush("cosec");
                            i += 5;
                        } else if ((user.charAt(i)=='s' && user.charAt(i+1)=='i' && user.charAt(i+2)=='n')||(user.charAt(i)=='c' && user.charAt(i+1)=='o' && user.charAt(i+2)=='s')||(user.charAt(i)=='t' && user.charAt(i+1)=='a' && user.charAt(i+2)=='n')||(user.charAt(i)=='l' && user.charAt(i+1)=='o' && user.charAt(i+2)=='g')){
                            infix.stackPush(Character.toString(user.charAt(i)).toUpperCase());
                            i+=3;
                        } else if ((user.charAt(i)=='a' && user.charAt(i+1)=='s' && user.charAt(i+2)=='i'&& user.charAt(i+3)=='n')||(user.charAt(i)=='a' && user.charAt(i+1)=='c' && user.charAt(i+2)=='o'&& user.charAt(i+3)=='s')||(user.charAt(i)=='a' && user.charAt(i+1)=='t' && user.charAt(i+2)=='a'&& user.charAt(i+3)=='n')){
                            infix.stackPush(Character.toString(user.charAt(i+1)));
                            i+=4;
                        } else if (user.charAt(i)=='l' && user.charAt(i+1)=='n'){
                            infix.stackPush(Character.toString(user.charAt(i)).toUpperCase());
                            i+=2;
                        } else if (user.charAt(i)=='f' && user.charAt(i+1)=='a'&& user.charAt(i+2)=='c'&& user.charAt(i+3)=='t'){
                            infix.stackPush("F");
                            i+=4;
                        } else if (user.charAt(i)=='p' && user.charAt(i+1)=='i'){
                            infix.stackPush("pi");
                            i+=2;
                        } else if (user.charAt(i)=='e'){
                            infix.stackPush("e");
                            i+=1;
                        } else if (user.charAt(i)=='s' && user.charAt(i+1)=='e' && user.charAt(i+2)=='c'){
                            infix.stackPush("sec");
                            i += 3;
                        } else if (user.charAt(i)=='c' && user.charAt(i+1)=='o' && user.charAt(i+2)=='t'){
                            infix.stackPush("cot");
                            i += 3;
                        } else{
                            infix.stackPush(Character.toString(user.charAt(i++)));
                        }
                    } catch (Exception e){
                        infix.stackPush(Character.toString(user.charAt(i++)));
                    }
                }
                infix.stackPush(")");
                //  C O M P L E T E D
                while(true){
                    c=infix.queuePop();
                    if (c.equals("###")){
                        answer.setText("SYNTAX ERROR");
                        error=true;
                    }
                    if (c.equals("#")||error){break;}
                    priority_now= calc.getPriority(c);
                    if (c.equals("pi")||c.equals("e")){
                        if(detectedUniversalConstant){
                            postfix.stackPush(c);
                            detectedUniversalConstant=false;
                        } else {
                            infix.queuePush(")");
                            infix.queuePush(c);
                            infix.queuePush("(");
                            detectedUniversalConstant=true;
                        }
                        continue;
                    }
                    if (!calc.getNum(c)){
                        if (!calc.formatInfix(infix,c,attach,closeBracket)){continue;}
                        if (c.equals("(") || c.equals("{") || c.equals("[") || (!c.equals(")") && !c.equals("}") && !c.equals("]") && priority_now > priority_min)){
                            closeBracket=false;
                            bin.stackPush(c);
                            priority_min=priority_now;
                        } else {
                            if (c.equals(")")||c.equals("}")||c.equals("]")){
                                closeBracket=true;
                                while(true){
                                    k=bin.stackPop();
                                    if (k.equals("(")||k.equals("{")||k.equals("[")){
                                        break;
                                    } else if (k.equals("#")) {
                                        answer.setText("LOGICAL ERROR");
                                        error=true;
                                        break;
                                    }
                                    postfix.stackPush(k);
                                }
                                k=bin.stackPop();
                                if(!k.equals("#")){
                                    bin.stackPush(k);
                                    priority_min=calc.getPriority(k);
                                }
                            } else{
                                closeBracket=false;
                                while(true){
                                    k=bin.stackPop();
                                    if ((calc.getPriority(k)!=0 && calc.getPriority(k)<priority_now)||(k.equals("(")||k.equals("{")||k.equals("["))||k.equals("#")){
                                        bin.stackPush(k);
                                        break;
                                    }
                                    postfix.stackPush(k);
                                }
                                bin.stackPush(c);
                                priority_min= calc.getPriority(c);
                            }
                        }
                        if (c.equals(".")){
                            postfix.stackPush("1");
                        } else{
                            attach=false;
                        }
                    } else{
                        if (attach){
                            postfix.stackPush(Integer.toString(Integer.parseInt(postfix.stackPop())*10+Integer.parseInt(c)));
                        } else {
                            postfix.stackPush(c);
                            attach = true;
                        }
                    }
                }
                if (!error){
                    try{
                        while (true){
                            c=postfix.queuePop();
                            if (c.equals("#")){break;}
                            if (c.equals("pi")){
                                bin.stackPush(Double.toString(3.141592654));
                            }else if (c.equals("e")){
                                bin.stackPush(Double.toString(2.718281828));
                            }else if (!calc.getNum(c)){
                                calc.process(bin,c);
                            } else {
                                bin.stackPush(c);
                            }
                        }
                        c=bin.queuePop();
                        if (bin.queuePop().equals("#")){
                            ans=Math.round(Double.parseDouble(c)*100000)/100000.0;
                            if (-1E9<ans && ans<1E9){
                                answer.setText(String.valueOf(ans));
                            }else {
                                answer.setText("MATH ERROR");
                            }
                        } else throw new Exception();
                    } catch (Exception e){
                        answer.setText("LOGICAL ERROR");
                    }
                }
                infix.reset();
                postfix.reset();
                bin.reset();
            }
        });
    }
}
